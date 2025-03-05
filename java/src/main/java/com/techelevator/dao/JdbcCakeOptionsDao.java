package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CakeOptions;
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
public class JdbcCakeOptionsDao implements CakeOptionsDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCakeOptionsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CakeOptions> getCakeOptions() {
        List<CakeOptions> cakeOptions = new ArrayList<>();
        String sql = "SELECT * FROM cake_options";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                CakeOptions cakeOption = mapRowToCakeOptions(results);
                cakeOptions.add(cakeOption);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (Exception e) {
            throw new DaoException("Something went wrong", e);
        };

        return cakeOptions;
    }

    @Override
    public CakeOptions getCakeOptionByName(String name) {
        CakeOptions cakeOption = null;
        String sql = "SELECT * FROM cake_options WHERE name = ?";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
            if (result.next()){
                cakeOption = mapRowToCakeOptions(result);
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

        return cakeOption;
    }

    @Override
    public CakeOptions createCakeOption(CakeOptions options) {
        CakeOptions newOptions = null;

        String sql = "INSERT INTO cake_options (name, category, available, price) VALUES (?, ?, ?, ?)";

        try {
            int rowsEffected = jdbcTemplate.update(sql, options.getName(), options.getCategory(), options.isAvailable(), options.getPrice());
            if (rowsEffected > 0){
                newOptions = getCakeOptionByName(options.getName());
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation has occured");
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return newOptions;
    }

    @Override
    public CakeOptions updateCakeOption(CakeOptions options) {
        CakeOptions newOptions = null;
        String sql = "UPDATE cake_options SET name = ?, category = ?, available = ?, price = ? WHERE name = ?;";

        try {
            int rowsEffected = jdbcTemplate.update(sql, options.getName(), options.getCategory(), options.isAvailable(), options.getPrice(), options.getName());

            if (rowsEffected == 0){
                throw new EmptyResultDataAccessException(1);
            }

            newOptions = getCakeOptionByName(options.getName());
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        } catch (EmptyResultDataAccessException e){
            throw new DaoException("No results came back");
        }
        catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return newOptions;
    }

    @Override
    public int deleteCakeOptionByName(String name) {
        int rowsEffected = 0;
        String sql = "DELETE FROM cake_options WHERE name = ?;";

        try {
            rowsEffected = jdbcTemplate.update(sql, name);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database.", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        } catch (Exception e){
            throw new DaoException("Something went wrong", e);
        }

        return rowsEffected;
    }

    private CakeOptions mapRowToCakeOptions(SqlRowSet row){
        CakeOptions options = new CakeOptions();

        options.setName(row.getString("name"));
        options.setCategory(row.getString("category"));
        options.setAvailable(row.getBoolean("available"));
        options.setPrice(row.getBigDecimal("price"));

        return options;
    }
}
