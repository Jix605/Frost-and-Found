package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('USER')")
//@PreAuthorize("isAuthenticated()")
public class EmployeeController {

    @Autowired
    private StandardCakesDao standardCakesDao;
    @Autowired
    private CakesDao cakesDao;
    @Autowired
    private CakeOptionsDao cakeOptionsDao;
    @Autowired
    private OrdersDao ordersDao;


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/standard-cakes", method = RequestMethod.POST)
    public StandardCakes createStandardCake(@Valid @RequestBody CreateStandardCakeDto dto) {
        try {
            return standardCakesDao.createStandardCake(dto.getCakeToAdd(), dto.getCakeBlueprint());
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(path = "/api/standard-cakes/{id}", method = RequestMethod.PUT)
    public StandardCakes updateStandardCakeById(@Valid @RequestBody StandardCakes cake,
                                                @PathVariable int id) {
        try {
            return standardCakesDao.updateStandardCakeById(cake, id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/api/standard-cakes/{id}", method = RequestMethod.DELETE)
    public int deleteStandardCakeById(@PathVariable int id) {
        try {
            return standardCakesDao.deleteStandardCakeById(id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/cakes-options", method = RequestMethod.POST)
    public CakeOptions createCakeOption(@Valid @RequestBody CakeOptions options) {
        try {
            return cakeOptionsDao.createCakeOption(options);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(path = "/api/cakes-options/{name}", method = RequestMethod.PUT)
    public CakeOptions updateCakeOptionById(@Valid @RequestBody CakeOptions options,
                                                @PathVariable String name) {
        try {
            options.setName(name);
            return cakeOptionsDao.updateCakeOption(options);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/api/cakes-options/{name}", method = RequestMethod.DELETE)
    public int deleteCakeOptionByName(@PathVariable String name) {
        try {
            return cakeOptionsDao.deleteCakeOptionByName(name);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(path = "/api/placed-orders", method = RequestMethod.GET)
    public List<PlacedOrders> getAllPlacedOrders() {
        try {
            return ordersDao.getPlacedOrders();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(path = "/api/placed-orders/{id}", method = RequestMethod.GET)
    public PlacedOrderDetailsDto getPlacedOrderByOrderId(@PathVariable int id) {
        try {
            return ordersDao.getPlacedOrderByOrderId(id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @RequestMapping(path = "/api/placed-orders/{id}", method = RequestMethod.PUT)
    public PlacedOrders updateOrderById(@Valid @RequestBody PlacedOrders placedOrder,
                                                @PathVariable int id) {
        try {
            placedOrder.setId(id);
            return ordersDao.updateOrder(placedOrder);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/api/placed-orders/{id}", method = RequestMethod.DELETE)
    public int deleteOrderById(@PathVariable int id) {
        try {
            return ordersDao.deleteOrder(id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



}
