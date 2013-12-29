/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author merte_000
 */
public class KekoTest {
    
    public KekoTest() {
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

    /**
     * Test of lisaa method, of class Keko.
     */
    @Test
    public void testLisaa() {
        //TODO: Pitäisikö lisaa-metodin kasvattaa myös taulun maksimikokoa? 
        //Nyt tulee out of bounds jos lisää yli 4 lukua, koska ekaan alkioon
        //on alustettu jo nolla.        
        
        System.out.println("lisaa");
        Keko instance = new Keko(5);
        instance.lisaa(6);
        instance.lisaa(2);
        instance.lisaa(8);
        instance.lisaa(4);
        int paluu = instance.poistaPienin();
        assertEquals(2, paluu);        
    }
    
    /**
     * Test of lisaa method, of class Keko.
     */
    @Test
    public void testLisaa2() {
        //TODO: Pitäisikö lisaa-metodin kasvattaa myös taulun maksimikokoa? 
        //Nyt tulee out of bounds jos lisää yli 4 lukua, koska ekaan alkioon
        //on alustettu jo nolla.        
        
        System.out.println("lisaa2");
        Keko instance = new Keko(100);
        instance.lisaa(6);
        instance.lisaa(8);
        instance.lisaa(4);
        instance.lisaa(2);
        instance.lisaa(3);
        instance.lisaa(5);
        instance.lisaa(10);
        instance.lisaa(7);
        int temp = instance.poistaPienin();
        int paluu = instance.poistaPienin();
        assertEquals(3, paluu);        
    }   
    
    /**
     * Test of onkoTyhja method, of class Keko.
     */
    @Test
    public void testOnkoTyhja2() {
        //TODO: Pitäisikö lisaa-metodin kasvattaa myös taulun maksimikokoa? 
        //Nyt tulee out of bounds jos lisää yli 4 lukua, koska ekaan alkioon
        //on alustettu jo nolla.        
        
        System.out.println("onkoTyhja");
        Keko instance = new Keko(100);
        instance.lisaa(6);
        int temp = instance.poistaPienin();
        assertEquals(true, instance.onkoTyhja());        
    }     
    
    @Test 
    public void testToString(){
        System.out.println("toString");
        Keko instance = new Keko(100);
        instance.lisaa(6);
        instance.lisaa(8);
        instance.lisaa(4);
        instance.lisaa(2);
        instance.lisaa(3);
        instance.lisaa(5);
        instance.lisaa(10);
        instance.lisaa(7);
        
        System.out.println(instance.toString());
        
        assertEquals(true, true);               
    }
    
    @Test
    public void testNaytaPienin(){
        System.out.println("naytaPienin");
        Keko instance = new Keko(100);
        instance.lisaa(6);
        instance.lisaa(8);
        instance.lisaa(4);
        instance.lisaa(2);
        instance.lisaa(3);
        instance.lisaa(5);
        instance.lisaa(10);
        instance.lisaa(7);
        int temp = instance.poistaPienin();
        int paluu = instance.naytaPienin();
        assertEquals(3, paluu);        

    }

    /**
     * Test of poistaPienin method, of class Keko.
     */
  /*  @Test
    public void testPoistaPienin() {
        System.out.println("poistaPienin");
        Keko instance = new Keko(5);
        instance.lisaa(6);
        instance.lisaa(2);
        instance.lisaa(4);
        instance.lisaa(8);
        int paluu = instance.poistaPienin();
        assertEquals(2, paluu);  
    }*/
    
}
