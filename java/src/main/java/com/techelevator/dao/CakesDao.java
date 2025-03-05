package com.techelevator.dao;

import com.techelevator.model.CakeDetailsDto;

public interface CakesDao {
    CakeDetailsDto createCake(CakeDetailsDto cake);
    CakeDetailsDto getCakeById(int id);
}
