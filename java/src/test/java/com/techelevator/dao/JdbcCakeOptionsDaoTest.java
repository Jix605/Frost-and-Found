package com.techelevator.dao;

import com.techelevator.model.CakeOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcCakeOptionsDaoTest extends BaseDaoTest {
    protected static final CakeOptions OPTIONS_1 = new CakeOptions("Cupcake", "style", true, new BigDecimal("1.99"));
    protected static final CakeOptions OPTIONS_2 = new CakeOptions("Sheet", "style", true, new BigDecimal("2.99"));
    protected static final CakeOptions OPTIONS_3 = new CakeOptions("Layer", "style", false, new BigDecimal("3.99"));

    private JdbcCakeOptionsDao sut;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcCakeOptionsDao(jdbcTemplate);
    }

    @Test
    public void createCakeOption_creates_cake_option_and_returns_proper_cake_option(){
        CakeOptions OPTIONS_4 = new CakeOptions("Flat", "style", true, new BigDecimal("3.99"));
        CakeOptions created = sut.createCakeOption(OPTIONS_4);

        assertCakeOptionsMatch(OPTIONS_4, sut.getCakeOptionByName("Flat"));
        assertCakeOptionsMatch(OPTIONS_4, created);
    }

    @Test
    public void updateCakeOption_updates_cake_option_and_returns_proper_cake_option(){
        CakeOptions OPTIONS_2_EXPECTED = new CakeOptions("Sheet", "flavor", true, new BigDecimal("2.99"));
        CakeOptions updated = sut.updateCakeOption(OPTIONS_2_EXPECTED);

        assertCakeOptionsMatch(OPTIONS_2_EXPECTED, sut.getCakeOptionByName("Sheet"));
        assertCakeOptionsMatch(OPTIONS_2_EXPECTED, updated);
    }

    @Test
    public void deleteCakeOptionByName_deletes_cake_option(){
        int rowsEffected = sut.deleteCakeOptionByName("Cupcake");
        boolean thrown = false;

        try {
            sut.getCakeOptionByName("Cupcake");
        } catch (Exception e) {
            thrown = true;
        }

        assertEquals(1, rowsEffected);
        assertTrue(thrown);
    }

    @Test
    public void getCakeOptions_returns_all_cake_options(){
        List<CakeOptions> options = sut.getCakeOptions();

        assertEquals(15, options.size());
    }

    @Test
    public void getCakeOptionByName_returns_correct_cake_option(){
        CakeOptions option = sut.getCakeOptionByName("Cupcake");

        assertCakeOptionsMatch(OPTIONS_1, option);
    }

    @Test
    public void getCakeOptionByName_given_null_throws_exception() {
        boolean thrown = false;

        try {
            sut.getCakeOptionByName(null);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    private void assertCakeOptionsMatch(CakeOptions expected, CakeOptions actual){
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.isAvailable(), actual.isAvailable());
        assertEquals(expected.getPrice(), actual.getPrice());
    }
}
