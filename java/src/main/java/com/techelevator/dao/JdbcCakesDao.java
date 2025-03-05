package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CakeDetailsDto;
import com.techelevator.model.Extras;
import com.techelevator.model.ExtrasDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class JdbcCakesDao implements  CakesDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCakesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CakeDetailsDto getCakeById(int id){
        CakeDetailsDto cake = null;
        String sql = "SELECT * FROM cakes WHERE id = ?";
        String extrasSql = "SELECT * FROM cake_extras WHERE cake_id = ?";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()){
                cake = mapRowToCake(result);

                List<ExtrasDto> listOfExtras = new ArrayList<>();
                SqlRowSet results = jdbcTemplate.queryForRowSet(extrasSql, id);

                while (results.next()) {
                    ExtrasDto extra = mapRowToExtra(results);
                    listOfExtras.add(extra);
                }

                cake.setExtras(listOfExtras);
            } else {
                throw new EmptyResultDataAccessException(1);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server", e);
        } catch (EmptyResultDataAccessException e){
            throw new DaoException("No results returned by query", e);
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return cake;
    }

    @Override
    public CakeDetailsDto createCake(CakeDetailsDto cake){
        CakeDetailsDto newCake = null;
        String sql = "INSERT INTO cakes (style, size, flavor, frosting, filling) VALUES (?, ?, ?, ?, ?) RETURNING id;";
        String extrasSql = "INSERT INTO cake_extras (name, cake_id) VALUES (?, ?)";

        try {
            Integer cakeId = jdbcTemplate.queryForObject(sql, Integer.class, cake.getStyle(), cake.getSize(), cake.getFlavor(), cake.getFrosting(), cake.getFilling());

            if (cake.getExtras() != null) {
                for (ExtrasDto extra : cake.getExtras()) {
                    jdbcTemplate.update(extrasSql, extra.getName(), cakeId);
                }
            }

            newCake = getCakeById(cakeId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation has occured");
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return newCake;
    }

    private CakeDetailsDto mapRowToCake(SqlRowSet row){
        CakeDetailsDto cake = new CakeDetailsDto();

        cake.setId(row.getInt("id"));
        cake.setStyle(row.getString("style"));
        cake.setSize(row.getString("size"));
        cake.setFlavor(row.getString("flavor"));
        cake.setFrosting(row.getString("frosting"));
        cake.setFilling(row.getString("filling"));

        return cake;
    }

    private ExtrasDto mapRowToExtra(SqlRowSet row){
        ExtrasDto extra = new ExtrasDto();

        extra.setName(row.getString("name"));

        return extra;
    }
}
