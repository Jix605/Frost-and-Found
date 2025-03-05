package com.techelevator.model;

public class PlacedOrderDetailsDto {
    private Customer customer;
    private CakeDetailsDto cakeDetails;
    private PlacedOrders orderDetails;

    public PlacedOrderDetailsDto(Customer customer, CakeDetailsDto cakeDetails, PlacedOrders orderDetails) {
        this.customer = customer;
        this.cakeDetails = cakeDetails;
        this.orderDetails = orderDetails;
    }

    public PlacedOrderDetailsDto() {}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CakeDetailsDto getCakeDetails() {
        return cakeDetails;
    }

    public void setCakeDetails(CakeDetailsDto cakeDetails) {
        this.cakeDetails = cakeDetails;
    }

    public PlacedOrders getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(PlacedOrders orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString(){
        String strToReturn = customer.getName() + " "
                + customer.getEmail()
                + "\nThank you for ordering from Frost and Found!"
                + "\nOrder Confirmation\nTotal Price: "
                + orderDetails.getTotalPrice() + "\n"
                + "Pickup At: " + orderDetails.getPickupAt() + "\n"
                + "Order Id: " + orderDetails.getId() + "\n"
                + "Cake Details:\n" + cakeDetails.toString();

        if (orderDetails.getMessage() != null || orderDetails.getMessage().isEmpty()){
            strToReturn += "\nRequested Message: " + orderDetails.getMessage();
        }

        return strToReturn;
    }
}
