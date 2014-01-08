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
    
    @Test
    public void testInsert(){
        AvlPuu puu = new AvlPuu();
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(10);
        noodi.setTehtyMatka(0);
        puu.insert(noodi);
        Noodi noodi2 = new Noodi(1,0);
        noodi2.setMatkaJaljella(9);
        noodi2.setTehtyMatka(noodi);
        puu.insert(noodi2);
        Noodi noodi3 = new Noodi(2,0);
        noodi3.setMatkaJaljella(12);
        noodi3.setTehtyMatka(noodi2);
        puu.insert(noodi3);
        Noodi noodi4 = new Noodi(3,0);
        noodi4.setMatkaJaljella(7);
        noodi4.setTehtyMatka(noodi3);
        puu.insert(noodi4);
        Noodi noodi5 = new Noodi(4,0);
        noodi5.setMatkaJaljella(20);
        noodi5.setTehtyMatka(noodi4);
        puu.insert(noodi5);
        Noodi noodi6 = new Noodi(5,0);
        noodi6.setMatkaJaljella(2);
        noodi6.setTehtyMatka(noodi5);
        puu.insert(noodi6);
        
        
        puu.tulostaJarjestyksessa();
        System.out.println("-----------------");        
        puu.tulostaTasot();
        
        assertEquals(puu.laskeSolmut(), 6);

        
    }
    
    @Test
    public void testRemove(){
        System.out.println("Testataan noodin poistoa puusta");
        AvlPuu puu = new AvlPuu();
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(10);
        noodi.setTehtyMatka(0);
        puu.insert(noodi);
        Noodi noodi2 = new Noodi(1,0);
        noodi2.setMatkaJaljella(9);
        noodi2.setTehtyMatka(noodi);
        puu.insert(noodi2);
        Noodi noodi3 = new Noodi(2,0);
        noodi3.setMatkaJaljella(12);
        noodi3.setTehtyMatka(noodi2);
        puu.insert(noodi3);
        Noodi noodi4 = new Noodi(3,0);
        noodi4.setMatkaJaljella(7);
        noodi4.setTehtyMatka(noodi3);
        puu.insert(noodi4);
        Noodi noodi5 = new Noodi(4,0);
        noodi5.setMatkaJaljella(20);
        noodi5.setTehtyMatka(noodi4);
        puu.insert(noodi5);
        Noodi noodi6 = new Noodi(5,0);
        noodi6.setMatkaJaljella(2);
        noodi6.setTehtyMatka(noodi5);
        puu.insert(noodi6);
                
        puu.tulostaJarjestyksessa();
        System.out.println("-----------------");        
        
        System.out.println("Poistetaan pienin solmu, solmuja ennen poistoa " + puu.laskeSolmut());
        
        Noodi foo = puu.poistaPieninNoodi();

        puu.tulostaJarjestyksessa();
        System.out.println("-----------------");        
        System.out.println("Solmuja poiston jälkeen " + puu.laskeSolmut());

        System.out.println("Poistetaan pienin solmu, solmuja ennen poistoa " + puu.laskeSolmut());
        
        Noodi tulos = puu.poistaPieninNoodi();

        puu.tulostaJarjestyksessa();
        System.out.println("-----------------");        
        System.out.println("Solmuja poiston jälkeen " + puu.laskeSolmut());
                
        assertEquals(tulos, noodi4);
        
    }            
}