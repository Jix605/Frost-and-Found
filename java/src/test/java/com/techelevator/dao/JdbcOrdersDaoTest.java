package com.techelevator.dao;

import com.techelevator.model.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcOrdersDaoTest extends BaseDaoTest {
    protected static final Customer CUSTOMER_1 = new Customer(1, "Customer", "Phone", "Email", 1);
    protected static final Customer CUSTOMER_2 = new Customer(2, "Customer 2", "Phone 2", "Email 2", 2);
    protected static final Customer CUSTOMER_3 = new Customer(3, "Customer 3", "Phone 3", "Email 3", 3);

    protected static final ExtrasDto extra_1 = new ExtrasDto("Sprinkles");
    protected static final ExtrasDto extra_2 = new ExtrasDto("Strawberries");
    protected static final ExtrasDto extra_3 = new ExtrasDto("Blueberries");

    protected static List<ExtrasDto> EXTRAS_1 = new ArrayList<>();
    protected static List<ExtrasDto> EXTRAS_2 = new ArrayList<>();
    protected static List<ExtrasDto> EXTRAS_3 = new ArrayList<>();

    private CakeDetailsDto DETAILS_1;
    private CakeDetailsDto DETAILS_2;
    private CakeDetailsDto DETAILS_3;

    protected static final PlacedOrders ORDER_DETAILS_1 = new PlacedOrders(1, LocalDateTime.parse("2025-02-20T08:36:10.152882"), null, 1, "Pending", new BigDecimal("5.99"));
    protected static final PlacedOrders ORDER_DETAILS_2 = new PlacedOrders(2, LocalDateTime.parse("2025-02-20T08:36:10.152882"), null, 1, "Completed", new BigDecimal("3.99"));
    protected static final PlacedOrders ORDER_DETAILS_3 = new PlacedOrders(3, LocalDateTime.parse("2025-02-20T08:36:10.152882"), null, 1, "Pending", new BigDecimal("14.99"));

    protected PlacedOrderDetailsDto ORDER_1;
    protected PlacedOrderDetailsDto ORDER_2;
    protected PlacedOrderDetailsDto ORDER_3;

    private JdbcOrdersDao sut;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcOrdersDao(new JdbcCakesDao(jdbcTemplate), jdbcTemplate);

        EXTRAS_1 = new ArrayList<>();
        EXTRAS_2 = new ArrayList<>();
        EXTRAS_3 = new ArrayList<>();

        EXTRAS_1.add(extra_1);
        EXTRAS_2.add(extra_2);
        EXTRAS_3.add(extra_3);

        DETAILS_1 = new CakeDetailsDto(1, "style", "size", "flavor", "frosting", "filling", EXTRAS_1);
        DETAILS_2 = new CakeDetailsDto(2, "style", "size", "flavor", "frosting", "filling", EXTRAS_2);
        DETAILS_3 = new CakeDetailsDto(3, "style", "size", "flavor", "frosting", "filling", EXTRAS_3);

        ORDER_1 = new PlacedOrderDetailsDto(CUSTOMER_1, DETAILS_1, ORDER_DETAILS_1);
        ORDER_2 = new PlacedOrderDetailsDto(CUSTOMER_2, DETAILS_2, ORDER_DETAILS_2);
        ORDER_3 = new PlacedOrderDetailsDto(CUSTOMER_3, DETAILS_3, ORDER_DETAILS_3);
    }

    @Test
    public void deleteOrder_deletes_order(){
        int rowsEffected = sut.deleteOrder(1);
        boolean thrown = false;

        try {
            sut.getPlacedOrderByOrderId(1);
        } catch (Exception e) {
            thrown = true;
        }

        assertEquals(1, rowsEffected);
        assertTrue(thrown);
    }

    @Test
    public void updateOrder_updates_order_and_returns_proper_PlacedOrders_option(){
        PlacedOrders order = sut.getPlacedOrders().get(1);
        order.setStatus("Cancelled");

        PlacedOrders updated = sut.updateOrder(order);

        assertEquals("Cancelled", updated.getStatus());
    }

    @Test
    public void getPlacedOrders_returns_all_orders(){
        List<PlacedOrders> orders = sut.getPlacedOrders();

        assertEquals(3, orders.size());
    }

    @Test
    public void getPlacedOrderByOrderId_returns_correct_order(){
        PlacedOrderDetailsDto order = sut.getPlacedOrderByOrderId(2);

        assertOrdersMatch(ORDER_2, order);
    }

    @Test
    public void getPlacedOrderByOrderId_given_invalid_id_throws_exception() {
        boolean thrown = false;

        try {
            sut.getPlacedOrderByOrderId(0);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void createOrder_creates_order_and_returns_the_proper_order(){
        OrderDto dto = new OrderDto();
        dto.setCustomer(CUSTOMER_1);
        dto.setPickupAt(LocalDateTime.parse("2025-02-20T08:36:10.152882"));
        dto.setCakeDetails(DETAILS_1);

        PlacedOrderDetailsDto created = sut.createOrder(dto);
        PlacedOrderDetailsDto expected = new PlacedOrderDetailsDto(new Customer(1, "Customer", "Phone", "Email", 1), DETAILS_1, ORDER_DETAILS_3);
        assertOrdersMatch(expected, created);
    }

    private void assertOrdersMatch(PlacedOrderDetailsDto expected, PlacedOrderDetailsDto actual){
        assertEquals(expected.getOrderDetails().getPickupAt(), actual.getOrderDetails().getPickupAt());
        assertEquals(expected.getOrderDetails().getStatus(), actual.getOrderDetails().getStatus());
        assertEquals(expected.getOrderDetails().getMessage(), actual.getOrderDetails().getMessage());

        assertEquals(expected.getCustomer().getName(), actual.getCustomer().getName());
        assertEquals(expected.getCustomer().getPhoneNumber(), actual.getCustomer().getPhoneNumber());
        assertEquals(expected.getCustomer().getEmail(), actual.getCustomer().getEmail());

        assertEquals(expected.getCakeDetails().getStyle(), actual.getCakeDetails().getStyle());
        assertEquals(expected.getCakeDetails().getSize(), actual.getCakeDetails().getSize());
        assertEquals(expected.getCakeDetails().getFlavor(), actual.getCakeDetails().getFlavor());
        assertEquals(expected.getCakeDetails().getFrosting(), actual.getCakeDetails().getFrosting());
        assertEquals(expected.getCakeDetails().getFilling(), actual.getCakeDetails().getFilling());
    }
}
