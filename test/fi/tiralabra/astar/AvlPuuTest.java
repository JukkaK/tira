/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tiralabra.astar;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author koskelin
 */
public class AvlPuuTest {
    
    public AvlPuuTest() {
    }

    /**
     * Test of AvlPuu method, of class AvlPuu.
     */
    @Test
    public void testAvlPuu() {
        AvlPuu puu = new AvlPuu();
        assertEquals(puu.laskeSolmut(), 0);
    }

    /**
     * Test of lookup method, of class AvlPuu.
     */
    @Test
    public void testLookup() {
        AvlPuu puu = new AvlPuu();
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(10);
        noodi.setTehtyMatka(0);
        PuuSolmu solmu = new PuuSolmu(noodi);
        puu.insert(noodi);
        assertTrue(puu.lookup(noodi));                
    }

    /**
     * Test of laskeSolmut method, of class AvlPuu.
     */
    @Test
    public void testLaskeSolmut() {
        AvlPuu puu = new AvlPuu();
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(10);
        noodi.setTehtyMatka(0);
        PuuSolmu solmu = new PuuSolmu(noodi);
        puu.insert(noodi);
        Noodi noodi2 = new Noodi(1,0);
        noodi2.setMatkaJaljella(9);
        noodi2.setTehtyMatka(noodi);
        PuuSolmu solmu2 = new PuuSolmu(noodi2);
        puu.insert(noodi2);
        assertEquals(puu.laskeSolmut(), 2);
    }

    /**
     * Test of onkoTasapainossa method, of class AvlPuu.
     */
    @Test
    public void testOnkoTasapainossa() {
        AvlPuu puu = new AvlPuu();
        assertTrue(puu.onkoTasapainossa());
    }

    /**
     * Test of SamaPuu method, of class AvlPuu.
     */
    @Test
    public void testSamaPuu() {
        AvlPuu puu = new AvlPuu();
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(10);
        noodi.setTehtyMatka(0);
        PuuSolmu solmu = new PuuSolmu(noodi);
        puu.insert(noodi);
        Noodi noodi2 = new Noodi(1,0);
        noodi2.setMatkaJaljella(9);
        noodi2.setTehtyMatka(noodi);
        PuuSolmu solmu2 = new PuuSolmu(noodi2);
        puu.insert(noodi2);
        
        AvlPuu puu2 = new AvlPuu();
        puu2.insert(noodi);
        puu2.insert(noodi2);
        assertTrue(puu.SamaPuu(puu2));

    }
}