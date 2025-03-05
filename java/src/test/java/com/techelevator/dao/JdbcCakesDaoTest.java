package com.techelevator.dao;

import com.techelevator.model.CakeDetailsDto;
import com.techelevator.model.ExtrasDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcCakesDaoTest extends BaseDaoTest {
    protected static List<ExtrasDto> EXTRAS_1 = new ArrayList<>();
    protected static List<ExtrasDto> EXTRAS_2 = new ArrayList<>();
    protected static List<ExtrasDto> EXTRAS_3 = new ArrayList<>();

    protected static final ExtrasDto extra_1 = new ExtrasDto("Sprinkles");
    protected static final ExtrasDto extra_2 = new ExtrasDto("Strawberries");
    protected static final ExtrasDto extra_3 = new ExtrasDto("Blueberries");

    private JdbcCakesDao sut;
    private CakeDetailsDto DETAILS_1;
    private CakeDetailsDto DETAILS_2;
    private CakeDetailsDto DETAILS_3;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcCakesDao(jdbcTemplate);

        EXTRAS_1 = new ArrayList<>();
        EXTRAS_2 = new ArrayList<>();
        EXTRAS_3 = new ArrayList<>();

        EXTRAS_1.add(extra_1);
        EXTRAS_2.add(extra_2);
        EXTRAS_3.add(extra_3);

        DETAILS_1 = new CakeDetailsDto(1, "style", "size", "flavor", "frosting", "filling", EXTRAS_1);
        DETAILS_2 = new CakeDetailsDto(2, "style", "size", "flavor", "frosting", "filling", EXTRAS_2);
        DETAILS_3 = new CakeDetailsDto(3, "style", "size", "flavor", "frosting", "filling", EXTRAS_3);
    }

    @Test
    public void getCakeById_returns_valid_CakeDetailsDto_object(){
        CakeDetailsDto storedDetails = sut.getCakeById(1);

        assertDtosMatch(DETAILS_1, storedDetails);
    }

    @Test
    public void getCakeById_throws_exception_with_invalid_id(){
        boolean thrown = false;

        try {
            CakeDetailsDto storedDetails = sut.getCakeById(-1);
        } catch (Exception e){
            thrown = true;
        }


        assertTrue(thrown);
    }

    @Test
    public void createCake_returns_created_cakeDetailsDto(){
        List<ExtrasDto> EXTRAS_4 = new ArrayList<>();

        CakeDetailsDto newCakeDetails = new CakeDetailsDto(4, "style", "size", "flavor", "frosting", "filling", EXTRAS_4);

        sut.createCake(newCakeDetails);

        assertDtosMatch(sut.getCakeById(4), newCakeDetails);
    }

    private void assertDtosMatch(CakeDetailsDto expected, CakeDetailsDto actual){
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getStyle(), actual.getStyle());
        assertEquals(expected.getSize(), actual.getSize());
        assertEquals(expected.getFlavor(), actual.getFlavor());
        assertEquals(expected.getFrosting(), actual.getFrosting());
        assertEquals(expected.getFilling(), actual.getFilling());

        List<ExtrasDto> expectedExtras = expected.getExtras();
        List<ExtrasDto> actualExtras = actual.getExtras();

        for (int i = 0; i < expectedExtras.size(); i++){
            assertEquals(expectedExtras.get(i).getName(), actualExtras.get(i).getName());
        }
    }
}
