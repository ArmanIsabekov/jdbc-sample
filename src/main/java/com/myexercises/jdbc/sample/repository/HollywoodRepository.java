/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myexercises.jdbc.sample.repository;

import com.myexercises.jdbc.sample.data.Director;
import com.myexercises.jdbc.sample.data.Film;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface HollywoodRepository {
    void saveDirector(Director director);
    Director findDirector(Integer id);
    Director findDirector(String name, String surname);
    void deleteDirector(Integer id);
    void saveFilm(Film film);
    Film findFilm(Integer id);
    void deleteFilm(Integer id);
    List<Film> findFilmsForDirector(Integer directorId);
    Director findDirectorForFilm(Integer filmId);
}
