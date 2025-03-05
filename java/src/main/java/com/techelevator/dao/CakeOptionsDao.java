package com.techelevator.dao;

import com.techelevator.model.CakeOptions;

import java.util.List;

public interface CakeOptionsDao {
    List<CakeOptions> getCakeOptions();
    CakeOptions getCakeOptionByName(String name);
    CakeOptions createCakeOption(CakeOptions options);
    CakeOptions updateCakeOption(CakeOptions options);
    int deleteCakeOptionByName(String name);
}