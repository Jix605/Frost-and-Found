package com.techelevator.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.techelevator.dao.CakeOptionsDao;
import com.techelevator.dao.CakesDao;
import com.techelevator.dao.OrdersDao;
import com.techelevator.dao.StandardCakesDao;
import com.techelevator.model.*;
import com.techelevator.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    private StandardCakesDao standardCakesDao;
    @Autowired
    private CakesDao cakesDao;
    @Autowired
    private CakeOptionsDao cakeOptionsDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private EmailService emailService;

    @RequestMapping(path = "/api/standard-cakes", method = RequestMethod.GET)
    public List<StandardCakes> getAllStandardCakes() {
        try {
            return standardCakesDao.getStandardCakes();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(path = "/api/standard-cakes/{id}", method = RequestMethod.GET)
    public StandardCakes getStandardCakeById(@PathVariable int id) {
        try {
            return standardCakesDao.getStandardCakeById(id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    /*@ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/cakes", method = RequestMethod.POST)
    public CakeDetailsDto createCake(@Valid @RequestBody CakeDetailsDto cake) {
        return cakesDao.createCake(cake);
    }*/

    @RequestMapping(path = "/api/cakes/{id}", method = RequestMethod.GET)
    public CakeDetailsDto getCakeById(@PathVariable int id) {
        try {
            return cakesDao.getCakeById(id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(path = "/api/cakes-options", method = RequestMethod.GET)
    public List<CakeOptions> getAllCakeOptions() {
        try {
            return cakeOptionsDao.getCakeOptions();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(path = "/api/cakes-options/{name}", method = RequestMethod.GET)
    public CakeOptions getCakeOptionByName(@PathVariable String name) {
        try {
            return cakeOptionsDao.getCakeOptionByName(name);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/placed-orders", method = RequestMethod.POST)
    public PlacedOrderDetailsDto createOrder(@Valid @RequestBody OrderDto orderToPlace) throws UnirestException {
        PlacedOrderDetailsDto createdOrder = ordersDao.createOrder(orderToPlace);

        try {
            EmailDto email = new EmailDto();
            email.setTo(createdOrder.getCustomer().getName() + " <" + createdOrder.getCustomer().getEmail() + ">");
            email.setSubject(createdOrder.getCustomer().getEmail() + " Order Confirmation Of Cake");
            email.setText(createdOrder.toString());

            emailService.sendSimpleMessage(email);
        } catch (UnirestException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return createdOrder;
    }


}
