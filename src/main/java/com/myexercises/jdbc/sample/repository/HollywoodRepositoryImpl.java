/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myexercises.jdbc.sample.repository;

import com.myexercises.jdbc.sample.data.Director;
import com.myexercises.jdbc.sample.data.Film;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Acer
 */
@Repository
public class HollywoodRepositoryImpl implements HollywoodRepository {

    private final String SAVE_DIRECTOR_TEMPLATE = "insert into directors (surname, name, birth_date) values (?,?,?)";
    private final String FIND_DIRECTOR_TEMPLATE = "select surname, name, birth_date, id from directors where id = ?";
    private final String FIND_DIRECTOR_BY_NAME_TEMPLATE = "select surname, name, birth_date, id from directors where surname = ? and name = ?";
    private final String DELETE_DIRECTOR_TEMPLATE = "delete from directors where id = ?";
    private final String FIND_FILM_TEMPLATE = "select director_id, name, release_date, id from films where id = ?";
    private final String SAVE_FILM_TEMPLATE = "insert into films (director_id, name, release_date) values (?,?,?)";
    private final String DELETE_FILM_TEMPLATE = "delete from films where id = ?";
    private final String FIND_FILMS_BY_DIRECTOR_TEMPLATE = "select director_id, name, release_date, id from films where director_id = ?";
    private final String FIND_DIRECTOR_BY_FILM_TEMPLATE = "select d.surname, d.name, d.birth_date, d.id from directors d, films f "
            + "where d.id = f.director_id and f.id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HollywoodRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDirector(final Director director) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement stmt = con.prepareStatement(SAVE_DIRECTOR_TEMPLATE, new String[]{"id"});
                stmt.setString(1, director.getSurname());
                stmt.setString(2, director.getName());
                stmt.setDate(3, director.getBirthDate() == null ? null : new java.sql.Date(director.getBirthDate().getTime()));
                return stmt;
            }
        }, keyHolder);
        director.setId(keyHolder.getKey().intValue());
    }

    @Override
    public Director findDirector(final Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_DIRECTOR_TEMPLATE, new DirectorRowMapper(), id);
    }

    @Override
    public void saveFilm(final Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement stmt = con.prepareStatement(SAVE_FILM_TEMPLATE, new String[]{"id"});
                stmt.setInt(1, film.getDirectorId());
                stmt.setString(2, film.getName());
                stmt.setDate(3, film.getReleaseDate() == null ? null : new java.sql.Date(film.getReleaseDate().getTime()));
                return stmt;
            }
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
    }

    @Override
    public Film findFilm(Integer id) {
        return this.jdbcTemplate.query(FIND_FILM_TEMPLATE, new FilmResultSetExtractor(), id);
    }

    @Override
    public List<Film> findFilmsForDirector(final Integer directorId) {
        return this.jdbcTemplate.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement stmt = con.prepareStatement(FIND_FILMS_BY_DIRECTOR_TEMPLATE);
                stmt.setInt(1, directorId);
                return stmt;
            }
        }, new PreparedStatementCallback<List<Film>>() {
            @Override
            public List<Film> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                List<Film> result = new ArrayList<>();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Film film = new Film();
                        film.setDirectorId(rs.getInt(1));
                        film.setName(rs.getString(2));
                        film.setReleaseDate(rs.getDate(3));
                        film.setId(rs.getInt(4));
                        result.add(film);
                    }
                }
                return result;
            }
        });
    }

    @Override
    public Director findDirectorForFilm(Integer filmId) {
        return this.jdbcTemplate.queryForObject(FIND_DIRECTOR_BY_FILM_TEMPLATE, new Object[filmId], new DirectorRowMapper());
    }

    @Override
    public void deleteDirector(final Integer id) {
        this.jdbcTemplate.update(DELETE_DIRECTOR_TEMPLATE, new IdPreparedStatementSetter(id));
    }

    @Override
    public void deleteFilm(final Integer id) {
        this.jdbcTemplate.update(DELETE_FILM_TEMPLATE, new IdPreparedStatementSetter(id));
    }

    @Override
    public Director findDirector(String name, String surname) {
        return this.jdbcTemplate.queryForObject(FIND_DIRECTOR_BY_NAME_TEMPLATE, new DirectorRowMapper(), surname, name);
    }

    private class DirectorRowMapper implements RowMapper<Director> {

        @Override
        public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
            Director director = new Director();
            director.setSurname(rs.getString(1));
            director.setName(rs.getString(2));
            director.setBirthDate(rs.getDate(3));
            director.setId(rs.getInt(4));
            return director;
        }
    }

    private class FilmResultSetExtractor implements ResultSetExtractor<Film> {

        @Override
        public Film extractData(ResultSet rs) throws SQLException, DataAccessException {
            Film film = new Film();
            film.setDirectorId(rs.getInt(1));
            film.setName(rs.getString(2));
            film.setReleaseDate(rs.getDate(3));
            film.setId(rs.getInt(4));
            return film;
        }
    }

    private class IdPreparedStatementSetter implements PreparedStatementSetter {

        private final Integer id;

        public IdPreparedStatementSetter(Integer id) {
            this.id = id;
        }

        @Override
        public void setValues(PreparedStatement ps) throws SQLException {
            ps.setInt(1, id);
        }

    }
}
