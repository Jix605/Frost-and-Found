package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Component
public class JdbcOrdersDao implements OrdersDao {
    private final JdbcCakesDao cakesDao;

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrdersDao(JdbcCakesDao cakesDao, JdbcTemplate jdbcTemplate) {
        this.cakesDao = cakesDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PlacedOrders> getPlacedOrders() {
        List<PlacedOrders> orders = new ArrayList<>();
        String sql = "SELECT * FROM placed_orders";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                PlacedOrders order = mapRowToPlacedOrder(results);
                orders.add(order);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (Exception e) {
            throw new DaoException("Something went wrong", e);
        };

        return orders;
    }

    @Override
    public PlacedOrderDetailsDto getPlacedOrderByOrderId(int id) {
        PlacedOrderDetailsDto order = null;
        String sql = "SELECT * FROM placed_orders WHERE id = ?";
        String sqlCustomer = "SELECT * FROM customer WHERE placed_order_id = ?";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

            if (result.next()){
                order = new PlacedOrderDetailsDto();
                order.setOrderDetails(mapRowToPlacedOrder(result));

                SqlRowSet customerResult = jdbcTemplate.queryForRowSet(sqlCustomer, order.getOrderDetails().getId());
                if (customerResult.next()){
                    order.setCustomer(mapRowToCustomer(customerResult));
                } else {
                    throw new EmptyResultDataAccessException(1);
                }

                order.setCakeDetails(cakesDao.getCakeById(order.getOrderDetails().getCakeId()));
            } else {
                throw new EmptyResultDataAccessException(1);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server", e);
        } catch (EmptyResultDataAccessException e){
            throw new DaoException("No results returned by query", e);
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return order;
    }

    @Override
    public PlacedOrderDetailsDto createOrder(OrderDto orderToPlace) {
        PlacedOrderDetailsDto newOrder = null;
        String sql = "INSERT INTO placed_orders (pickup_at, message, cake_id, status, total_price) VALUES (?, ?, ?, ?, ?) RETURNING id;";
        String customerSql = "INSERT INTO customer (name, phone_number, email, placed_order_id) VALUES (?, ?, ?, ?)";
        String optionsPrice = "SELECT SUM(price) FROM cake_options WHERE name IN(?, ?, ?, ?, ?);";
        String extrasPrice = "SELECT SUM(co.price) FROM cake_options as co JOIN cake_extras as ce ON ce.name = co.name WHERE cake_id = ?;";
        String standardCakePrice = "SELECT price FROM standard_cakes WHERE cake_id = ?;";

        try {
            boolean standardCake = true;
            CakeDetailsDto cake = orderToPlace.getCakeDetails();

            if (cake.getId() == 0){
                standardCake = false;
            }

            int oldCakeId = cake.getId();
            cake = cakesDao.createCake(cake);
            int createdCakeId = cake.getId();

            // implement getting a price based on options + extras
            BigDecimal calculatedPrice = new BigDecimal("0");
            if (!standardCake) {
                calculatedPrice = calculatedPrice.add(jdbcTemplate.queryForObject(optionsPrice, BigDecimal.class, cake.getStyle(), cake.getSize(), cake.getFlavor(), cake.getFrosting(), cake.getFilling()));
            } else {
                calculatedPrice = calculatedPrice.add(jdbcTemplate.queryForObject(standardCakePrice, BigDecimal.class, oldCakeId));
            }

            BigDecimal extrasCost = jdbcTemplate.queryForObject(extrasPrice, BigDecimal.class, createdCakeId);
            if (extrasCost == null){
                extrasCost = new BigDecimal("0");
            }
            calculatedPrice = calculatedPrice.add(extrasCost);

            int orderId = jdbcTemplate.queryForObject(sql, Integer.class, orderToPlace.getPickupAt(), orderToPlace.getMessage(), createdCakeId, "Pending", calculatedPrice);

            Customer customer = orderToPlace.getCustomer();
            jdbcTemplate.update(customerSql, customer.getName(), customer.getPhoneNumber(), customer.getEmail(), orderId);

            newOrder = getPlacedOrderByOrderId(orderId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return newOrder;
    }

    @Override
    public PlacedOrders updateOrder(PlacedOrders placedOrder) {
        PlacedOrders newPlacedOrder = null;
        String sql = "UPDATE placed_orders SET pickup_at = ?, message = ?, cake_id = ?, status = ?, total_price = ? WHERE id = ?;";

        try {
            int rowsEffected = jdbcTemplate.update(sql, placedOrder.getPickupAt(), placedOrder.getMessage(), placedOrder.getCakeId(), placedOrder.getStatus(), placedOrder.getTotalPrice(), placedOrder.getId());

            if (rowsEffected == 0){
                throw new EmptyResultDataAccessException(1);
            }

            newPlacedOrder = getPlacedOrderByOrderId(placedOrder.getId()).getOrderDetails();
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        } catch (EmptyResultDataAccessException e){
            throw new DaoException("No results came back");
        }
        catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return newPlacedOrder;
    }

    @Override
    public int deleteOrder(int id) {
        int rowsEffected = 0;
        String sql = "DELETE FROM placed_orders WHERE id = ?";
        String customerSql = "DELETE FROM customer WHERE placed_order_id = ?";

        try {
            jdbcTemplate.update(customerSql, id);
            rowsEffected = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return rowsEffected;
    }

    private PlacedOrders mapRowToPlacedOrder(SqlRowSet row){
        PlacedOrders order = new PlacedOrders();

        order.setId(row.getInt("id"));
        order.setPickupAt(Objects.requireNonNull(row.getTimestamp("pickup_at")).toLocalDateTime());
        order.setCakeId(row.getInt("cake_id"));
        order.setStatus(row.getString("status"));
        order.setMessage(row.getString("message"));
        order.setTotalPrice(row.getBigDecimal("total_price"));

        return order;
    }

    private Customer mapRowToCustomer(SqlRowSet row){
        Customer customer = new Customer();

        customer.setId(row.getInt("id"));
        customer.setName(row.getString("name"));
        customer.setPhoneNumber(row.getString("phone_number"));
        customer.setEmail(row.getString("email"));
        customer.setPlacedOrderId(row.getInt("placed_order_id"));

        return customer;
    }
}
