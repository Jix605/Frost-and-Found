package com.techelevator.dao;

import com.techelevator.model.OrderDto;
import com.techelevator.model.PlacedOrderDetailsDto;
import com.techelevator.model.PlacedOrders;

import java.util.List;

public interface OrdersDao {
    List<PlacedOrders> getPlacedOrders();
    PlacedOrderDetailsDto getPlacedOrderByOrderId(int id);
    PlacedOrderDetailsDto createOrder(OrderDto orderToPlace);
    PlacedOrders updateOrder(PlacedOrders placedOrder);
    int deleteOrder(int id);
}