package com.techelevator.dao;

import com.techelevator.model.CakeDetailsDto;
import com.techelevator.model.StandardCakes;

import java.util.List;

public interface StandardCakesDao {
    List<StandardCakes> getStandardCakes();
    StandardCakes getStandardCakeById(int id);
    StandardCakes createStandardCake(CakeDetailsDto cakeToAdd, StandardCakes cake);
    StandardCakes updateStandardCakeById(StandardCakes cake, int standardCakeId);
    int deleteStandardCakeById(int id);
}