/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar.pino;

import fi.tiralabra.astar.Noodi;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author merte_000
 */
public class PinoTest {
    
    public PinoTest() {
    }

    /**
     * Test of Pino method, of class Pino.
     */
    @Test
    public void testPino() {
        Pino pino = new Pino(10);
        assertTrue(pino.onkoTyhja());
    }

    /**
     * Test of tyonna method, of class Pino.
     */
    @Test
    public void testTyonna() {
        Pino pino = new Pino(10);
        Noodi noodi = new Noodi(0, 0);
        pino.tyonna(noodi);
        assertFalse(pino.onkoTyhja());
    }

    /**
     * Test of poista method, of class Pino.
     */
    @Test
    public void testPoista() {
        Pino pino = new Pino(10);
        Noodi noodi = new Noodi(5, 0);        
        pino.tyonna(noodi);
        assertFalse(pino.onkoTyhja());
        Noodi paluu = pino.poista();
        assertEquals(paluu.getxPositio(), noodi.getxPositio());
        assertEquals(paluu.getyPositio(), noodi.getyPositio());
    }    
    
    /**
     * Testaan että maksimikoko kasvaa
     */
    @Test
    public void testKoko(){
        Pino pino = new Pino(2);
        Noodi noodi = new Noodi(5, 0);        
        pino.tyonna(noodi);
        Noodi noodi2 = new Noodi(6, 0);        
        pino.tyonna(noodi2);
        Noodi noodi3 = new Noodi(7, 0);        
        pino.tyonna(noodi3);
        Noodi noodi4 = new Noodi(8, 0);        
        pino.tyonna(noodi4);
        assertEquals(pino.size(), 3);
        assertFalse(pino.onkoTyhja());
    }    
}
