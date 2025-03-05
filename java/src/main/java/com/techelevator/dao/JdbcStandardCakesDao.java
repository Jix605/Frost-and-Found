package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CakeDetailsDto;
import com.techelevator.model.StandardCakes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class JdbcStandardCakesDao implements StandardCakesDao {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcCakesDao cakesDao;

    public JdbcStandardCakesDao(JdbcTemplate jdbcTemplate, JdbcCakesDao cakesDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.cakesDao = cakesDao;
    }

    @Override
    public List<StandardCakes> getStandardCakes(){
        List<StandardCakes> standardCakes = new ArrayList<>();
        String sql = "SELECT * FROM standard_cakes";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                StandardCakes standardCake = mapRowToStandardCake(results);
                standardCakes.add(standardCake);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (Exception e) {
            throw new DaoException("Something went wrong", e);
        };

        return standardCakes;
    }

    @Override
    public StandardCakes getStandardCakeById(int id){
        StandardCakes cake = null;
        String sql = "SELECT * FROM standard_cakes WHERE id = ?";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()){
                cake = mapRowToStandardCake(result);
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

        return cake;
    }

    @Override
    public StandardCakes createStandardCake(CakeDetailsDto cakeToAdd, StandardCakes cake){
        StandardCakes newStandardCake = null;
        String sqlStandardCake = "INSERT INTO standard_cakes (name, price, description, available, cake_id) VALUES(?, ?, ?, ?, ?) RETURNING id;";

        try {
            int createdCakeId = cakesDao.createCake(cakeToAdd).getId();
            Integer standardCakeId = jdbcTemplate.queryForObject(sqlStandardCake, Integer.class, cake.getName(), cake.getPrice(), cake.getDescription(), cake.isAvailable(), createdCakeId);
            newStandardCake = getStandardCakeById(standardCakeId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return newStandardCake;
    }

    @Override
    public StandardCakes updateStandardCakeById(StandardCakes cake, int standardCakeId){
        cake.setId(standardCakeId);

        StandardCakes newStandardCake = null;
        String sql = "UPDATE standard_cakes SET name = ?, price = ?, description = ?, available = ?, cake_id = ? WHERE id = ?;";

        try {
            int rowsEffected = jdbcTemplate.update(sql, cake.getName(), cake.getPrice(), cake.getDescription(), cake.isAvailable(), cake.getCakeId(), standardCakeId);

            if (rowsEffected == 0){
                throw new EmptyResultDataAccessException(1);
            }

            newStandardCake = getStandardCakeById(cake.getId());
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

        return newStandardCake;
    }

    @Override
    public int deleteStandardCakeById(int id){
        int rowsEffected = 0;
        String sql = "DELETE FROM standard_cakes WHERE id = ?;";

        try {
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

    private StandardCakes mapRowToStandardCake(SqlRowSet row){
        StandardCakes cake = new StandardCakes();

        cake.setId(row.getInt("id"));
        cake.setName(row.getString("name"));
        cake.setPrice(row.getBigDecimal("price"));
        cake.setDescription(row.getString("description"));
        cake.setAvailable(row.getBoolean("available"));
        cake.setCakeId(row.getInt("cake_id"));

        return cake;
    }
}
