package com.techelevator.dao;

import com.techelevator.model.CakeDetailsDto;
import com.techelevator.model.CakeOptions;
import com.techelevator.model.ExtrasDto;
import com.techelevator.model.StandardCakes;
import jakarta.validation.constraints.NotEmpty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcStandardCakesDaoTest extends BaseDaoTest {
    protected static final StandardCakes STANDARD_CAKE_1 = new StandardCakes(1, 1, "Vanilla Cake", new BigDecimal("14.99"), "A vanilla cake", true);
    protected static final StandardCakes STANDARD_CAKE_2 = new StandardCakes(2, 2, "Chocolate Cake", new BigDecimal("10.99"), "A Chocolate cake", true);
    protected static final StandardCakes STANDARD_CAKE_3 = new StandardCakes(3, 3, "Strawberry Cake", new BigDecimal("23.99"), "A Strawberry cake", false);

    private JdbcStandardCakesDao sut;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcStandardCakesDao(jdbcTemplate, new JdbcCakesDao(jdbcTemplate));
    }

    @Test
    public void deleteStandardCakeById_deletes_cake_option(){
        int rowsEffected = sut.deleteStandardCakeById(1);
        boolean thrown = false;

        try {
            sut.getStandardCakeById(1);
        } catch (Exception e) {
            thrown = true;
        }

        assertEquals(1, rowsEffected);
        assertTrue(thrown);
    }

    @Test
    public void updateStandardCakeById_updates_standard_cake_and_returns_proper_standard_cake_option(){
        StandardCakes updated = sut.updateStandardCakeById(STANDARD_CAKE_3, 2);

        assertStandardCakesMatch(STANDARD_CAKE_3, updated);
    }

    @Test
    public void createStandardCake_creates_standard_cake_and_returns_proper_cake(){
        List<ExtrasDto> extras = new ArrayList<>();
        extras.add(new ExtrasDto("Sprinkles"));

        CakeDetailsDto cake = new CakeDetailsDto();
        cake.setStyle("Cheesecake");
        cake.setSize("M");
        cake.setFlavor("Raspberry");
        cake.setFilling("Fudge");
        cake.setExtras(extras);

        StandardCakes standardCake = new StandardCakes();
        standardCake.setName("Raspberry Cheesecake");
        standardCake.setPrice(new BigDecimal("9.99"));
        standardCake.setDescription("A classic raspberry cheesecake");
        standardCake.setAvailable(true);

        StandardCakes cakeReturned = sut.createStandardCake(cake, standardCake);
        assertStandardCakesMatch(new StandardCakes(4, 6, "Raspberry Cheesecake", new BigDecimal("9.99"), "A classic raspberry cheesecake", true), cakeReturned);
    }

    @Test
    public void getStandardCakes_returns_all_cake_options(){
        List<StandardCakes> cakes = sut.getStandardCakes();

        assertEquals(3, cakes.size());
    }

    @Test
    public void getStandardCakeById_returns_correct_standard_cake(){
        StandardCakes cake = sut.getStandardCakeById(2);

        assertStandardCakesMatch(STANDARD_CAKE_2, cake);
    }

    @Test
    public void getStandardCakeById_given_invalid_id_throws_exception() {
        boolean thrown = false;

        try {
            sut.getStandardCakeById(0);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    private void assertStandardCakesMatch(StandardCakes expected, StandardCakes actual){
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCakeId(), actual.getCakeId());
        assertEquals(expected.getId(), actual.getId());
    }
}
