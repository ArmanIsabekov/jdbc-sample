/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myexercises.jdbc.sample.repository;

import com.myexercises.jdbc.sample.data.Director;
import com.myexercises.jdbc.sample.data.Film;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Acer
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class HollywoodRepositoryTest {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private HollywoodRepository repository;
    
    public HollywoodRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSaveFindDeleteDirector() throws Exception {
        Director director = new Director();
        director.setName("Peter");
        director.setSurname("Jackson");
        director.setBirthDate(sdf.parse("1961-10-31"));
        repository.saveDirector(director);
        assertNotNull(director.getId());
        Director foundDirector = repository.findDirector(director.getId());
        assertEquals(director, foundDirector);
        repository.deleteDirector(foundDirector.getId());
    }

    @Test
    public void testSaveFilm() {
        
    }

    @Test
    public void testFindFilm() {
    }

    @Test
    public void testFindFilmsForDirector() {
    }

    @Test
    public void testFindDirectorForFilm() {
    }
}
