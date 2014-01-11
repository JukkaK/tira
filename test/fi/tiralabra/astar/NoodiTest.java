/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author merte_000
 */
public class NoodiTest {
    
    public NoodiTest() {
    }

    /**
     * Test of AsetaKoordinaatit method, of class Noodi.
     */
    @Test
    public void testAsetaKoordinaatit() {
        Noodi noodi = new Noodi(10, 10);
        noodi.AsetaKoordinaatit(20, 30);
        assertEquals(noodi.getxPositio(), 20);
        assertEquals(noodi.getyPositio(), 30);
        
    }

    /**
     * Test of getKuljettava method, of class Noodi.
     */
    @Test
    public void testGetKuljettava() {
        Noodi noodi = new Noodi(10, 10);
        noodi.setKuljettava(false);
        assertEquals(noodi.getKuljettava(), false);
    }

    /**
     * Test of getEdellinenNoodi method, of class Noodi.
     */
    @Test
    public void testGetEdellinenNoodi() {
        Noodi noodi1 = new Noodi(10, 10);
        Noodi noodi2 = new Noodi(10, 9);
        noodi1.setEdellinenNoodi(noodi2);
        assertEquals(noodi1.getEdellinenNoodi(), noodi2);
        
    }

    /**
     * Test of getKokoMatka method, of class Noodi.
     */
    @Test
    public void testGetKokoMatka() {
        Noodi noodi = new Noodi(10, 10);
        noodi.setTehtyMatka(10);
        noodi.setMatkaJaljella(5);
        assertEquals(noodi.getKokoMatka(), 15);
    }
    
    /**
     * Test of getKokoMatka method, of class Noodi.
     */
    @Test
    public void testGetKokoMatka2() {
        Noodi noodi = new Noodi(0, 1);
        Noodi noodi3 = new Noodi(0, 3);        
        noodi.setMatkaJaljella(noodi3);
        assertEquals(noodi.getKokoMatka(), 20);
    }    

    /**
     * Test of getTehtyMatka method, of class Noodi.
     */
    @Test
    public void testGetTehtyMatka() {
        Noodi noodi = new Noodi(0,1);
        Noodi noodi2 = new Noodi(0,0);
        noodi2.setTehtyMatka(20);
        noodi.setEdellinenNoodi(noodi2);
        noodi.setTehtyMatka(noodi2);
        assertEquals(noodi.getTehtyMatka(), 30);
    }

    /**
     * Test of getMatkaaJaljella method, of class Noodi.
     */
    @Test
    public void testGetMatkaaJaljella() {
        Noodi noodi = new Noodi(10, 10);
        noodi.setMatkaJaljella(11);
        assertEquals(noodi.getMatkaaJaljella(), 11);
    }

    /**
     * Test of absolute method, of class Noodi.
     */
    @Test
    public void testAbsolute() {
        Noodi noodi = new Noodi(10, 10);
        assertEquals(noodi.absolute(-2), 2);
    }

    /**
     * Test of laskeTehtyMatka method, of class Noodi.
     */
    @Test
    public void testLaskeTehtyMatka() {
        Noodi noodi = new Noodi(0,1);
        Noodi noodi2 = new Noodi(0, 0);
        noodi2.setTehtyMatka(12);
        assertEquals(noodi.laskeTehtyMatka(noodi2), 22);
    }

    /**
     * Test of equals method, of class Noodi.
     */
    @Test
    public void testEquals() {
        Noodi noodi = new Noodi(10, 10);
        Noodi noodi2 = new Noodi(10, 10);
        assertEquals(noodi.equals(noodi2), true);
    }
    
    /**
     * Test of equals method, of class Noodi.
     */
    @Test
    public void testEqualsVirhe() {
        Noodi noodi = new Noodi(10, 10);
        Noodi noodi2 = new Noodi(10, 11);
        assertEquals(noodi.equals(noodi2), false);
    }   
    
    @Test
    public void testHash(){
        Noodi noodi = new Noodi(0,1);
        noodi.setMatkaJaljella(10);
        noodi.setTehtyMatka(5);
        
        Noodi noodi2 = new Noodi(0,1);
        noodi2.setMatkaJaljella(10);
        noodi2.setTehtyMatka(5);
        
        assertTrue(noodi.equals(noodi2));
        
        Noodi noodi3 = new Noodi(0,1);
        noodi3.setMatkaJaljella(10);
        noodi3.setTehtyMatka(4);
        
        assertFalse(noodi.equals(noodi3));

    }
}
