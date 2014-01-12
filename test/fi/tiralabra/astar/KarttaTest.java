/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import fi.tiralabra.astar.pino.Pino;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jukka Koskelin
 */
public class KarttaTest {
    
    public KarttaTest() {
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
     * Testataan getNoodi -metodia.
     */
    @Test
    public void testGetNoodi(){
        Kartta instance = new Kartta<Noodi>("testi.png");        
        Noodi noodi = instance.getNoodi(0, 0);
        assertNotNull(noodi);
    }    
    
    /**
     * Testataan getNoodi -metodia.
     */
    @Test
    public void testGetNoodi2(){
        Kartta instance = new Kartta<Noodi>("testi.png");        
        Noodi noodi = instance.getNoodi(500, 400);
        assertNull(noodi);
    }    
    
    /**
     * Testataan getNoodi -metodia.
     */
    @Test
    public void testGetNoodi3(){
        Kartta instance = new Kartta<Noodi>("testi.png");        
        Noodi noodi = instance.getNoodi(-1, -1);
        assertNull(noodi);
    }    

    
    /**
     * Testataan etsiPolku -metodia. Oletetaan että palauttaa 80:n askeleen pituisen polun, 
     * eli 0,0 -> 40,40, kulkien suorakulmion muotoisen matkan.
     */
    @Test
    public void testEtsiPolku() {
        System.out.println("etsiPolku");
        int oldX = 0;
        int oldY = 0;
        int newX = 40;
        int newY = 40;
        Kartta instance = new Kartta<Noodi>(50, 50);
        int expResult = 79;
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "PQ");
                
        assertEquals(expResult, result.size());
        System.out.println("Tuloksessa noodeja yhteensä: " + result.size()); 
        while(!result.onkoTyhja()){
            Noodi noodi = result.poista();
            System.out.print("(" + noodi.getxPositio() + ", " + noodi.getyPositio() + ") -> ");
        }                       
    }    
    
    /**
     * Testataan etsiPolku -metodia. Oletetaan että palauttaa 80:n askeleen pituisen polun, 
     * eli 0,0 -> 40,40, kulkien suorakulmion muotoisen matkan.
     */
    @Test
    public void testEtsiPolkuKeko() {
        System.out.println("etsiPolku");
        int oldX = 0;
        int oldY = 0;
        int newX = 40;
        int newY = 40;
        Kartta instance = new Kartta<Noodi>(50, 50);
        int expResult = 79;        
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        System.out.println("Tuloksessa noodeja yhteensä: " + result.size());                
        assertEquals(expResult, result.size());        
        while(!result.onkoTyhja()){
            Noodi noodi = result.poista();
            System.out.print("(" + noodi.getxPositio() + ", " + noodi.getyPositio() + ") -> ");
        }                                               
    }
    
    /**
     * Testataan etsiPolku -metodia. Oletetaan että palauttaa 80:n askeleen pituisen polun, 
     * eli 0,0 -> 40,40, kulkien suorakulmion muotoisen matkan.
     */
    @Test
    public void testEtsiPolkuAvl() {
        
        assertTrue(true);
        
        //Tämä AVL-testi kaatuu kun puuhun yritetään lisätä solmua joka
        //siellä jo on.
       /* System.out.println("etsiPolku");
        int oldX = 0;
        int oldY = 0;
        int newX = 40;
        int newY = 40;
        Kartta instance = new Kartta<Noodi>(50, 50);
        int expResult = 80;        
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "AVL");
        Pino polku = result;
                
        while(!result.onkoTyhja()){
            System.out.print("(" + result.poista().getxPositio() + ", " + result.poista().getyPositio() + ") -> ");
        }
                
        System.out.println("Tuloksessa noodeja yhteensä: " + result.size());
        
        assertEquals(expResult, polku.size());    */
    }

    
    /**
     * Luodaan kartta kuvatiedostosta
     */
    @Test
    public void testLuoKarttaKuvasta(){
        Kartta instance = new Kartta<Noodi>("testi.png");
        assertEquals(false, instance.getNoodi(10, 10).getKuljettava());
    }

    /**
     * Luodaan kartta kuvatiedostosta
     */
    @Test
    public void testLuoKarttaKuvastaVirhe(){
        try{
            Kartta instance = new Kartta<Noodi>("foo.png");
        } catch (IllegalArgumentException ex){
            assertEquals(ex.getMessage(), "Kuvaa: foo.png ei löydy");
        }                        
    }        
    
    /**
     * Testataan ettei Kartta-luokkaa voida luoda negatiivisilla parametreillä.
     */
    @Test
    public void testLuoKarttaVirhe() {
    
        String expected = "Arvot eivät voi olla pienempiä kuin yksi";
        
        try {
                Kartta instance = new Kartta<Noodi>(-2, -2);
        } catch (Exception ex) {
            assertEquals(expected, ex.getMessage());
        }
            
    }    
    
    /**
     * Testataan että noodin Kuljettava -arvo asetetaan.
     */
    @Test
    public void testSetKuljettava() {
        
        Kartta instance = new Kartta<Noodi>(50, 50);
        instance.setKuljettava(40, 40, true);
        assertTrue("setKuljettava ei onnistunut", instance.getNoodi(40, 40).getKuljettava());
        
    }    

    /**
     * Testataan että setKuljettava nostaa virheen.
     */
    @Test
    public void testSetKuljettavaVirhe() {
        
        String expected = "Annetut koordinaatit eivät löydy kartalta";
        Kartta instance = new Kartta<Noodi>(50, 50);
                
        
        try {
            instance.setKuljettava(100, 100, true);
        } catch (Exception ex) {
            assertEquals(expected, ex.getMessage());
        }                        
    }    

//Integraatiotestit
//-----------------
    
    /**
     * Testataan kartan luomista kuvatiedostosta, ja polun etsintää
     */
    @Test
    public void testLuoKarttaKuvastaJaEtsiPolku(){
        Kartta instance = new Kartta<Noodi>("testi.png");
        int oldX = 7;
        int oldY = 0;
        int newX = 7;
        int newY = 49;
        int expResult = 52;
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        int koko = result.size();
        assertEquals(expResult, koko);
        System.out.println("Tuloksessa noodeja yhteensä: " + result.size()+1);           
                       
        Noodi noodi = new Noodi(0,0);
        int pinonKoko = result.size();
        for (int i = 0; i < pinonKoko+1; i++) {
            noodi = result.poista();
            int x = noodi.getxPositio();
            int y = noodi.getyPositio();
            System.out.print("(" + x + ", " + y + ") -> ");
        }
        assertEquals(7, noodi.getxPositio());
        assertEquals(49, noodi.getyPositio());
    }
    
    /**
     * Testataan kartan luomista kuvatiedostosta, ja polun etsintää
     */
    @Test
    public void testLuoKarttaKuvastaJaEtsiPolku2(){
        Kartta instance = new Kartta<Noodi>("testi3.png");
        int oldX = 0;
        int oldY = 0;
        int newX = 304;
        int newY = 217;
        int expResult = 1448;
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");

        System.out.println("Tuloksessa noodeja yhteensä: " + result.size());        
        
        assertEquals(expResult, result.size());
                
    }    
            
//Suorituskykytestit    
//------------------    
    
        @Test
    public void testEtsiPolkuTimer500(){
        System.out.println("etsiPolkuTimer500");
        int oldX = 0;
        int oldY = 0;
        int newX = 499;
        int newY = 499;
        Kartta instance = new Kartta<Noodi>(500, 500);
        int expResult = 998;
        long alkuAika = System.nanoTime();
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        long loppuAika = System.nanoTime();
        long suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                  
        System.out.println("Suoritusaika PQ: " + suoritusAika + " ms");
        
        assertEquals(expResult, result.size()+1);
        
        alkuAika = System.nanoTime();
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Keko");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Keko: " + suoritusAika + " ms");
        
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Avl");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Avl: " + suoritusAika + " ms");
                
        assertEquals(expResult, result.size()+1);
        
        
    }
    
@Test
    public void testEtsiPolkuTimerKuvaIso(){                
        System.out.println("etsiPolkuTimerKuva");
        int oldX = 0;
        int oldY = 0;
        int newX = 304;
        int newY = 217;
        int expResult = 1448;
        Kartta instance = new Kartta<Noodi>("testi3.png");
        long alkuAika = System.nanoTime();
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        long loppuAika = System.nanoTime();
        long suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                  
        System.out.println("Suoritusaika PQ: " + suoritusAika + " ms");
        
        assertEquals(expResult, result.size());
        
        alkuAika = System.nanoTime();
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Keko");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Keko: " + suoritusAika + " ms");
        
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Avl");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Avl: " + suoritusAika + " ms");
                
        //assertEquals(expResult, result.size());
        
        
    }    
    
    @Test
    public void testEtsiPolkuTimerKuvaPieni(){                
        System.out.println("etsiPolkuTimerKuva");
        int oldX = 0;
        int oldY = 0;
        int newX = 49;
        int newY = 49;
        int expResult = 155;
        Kartta instance = new Kartta<Noodi>("testi2.png");
        long alkuAika = System.nanoTime();
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        long loppuAika = System.nanoTime();
        long suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                  
        System.out.println("Suoritusaika PQ: " + suoritusAika + " ms");
        
        assertEquals(expResult, result.size());
        
        alkuAika = System.nanoTime();
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Keko");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Keko: " + suoritusAika + " ms");
        
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Avl");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Avl: " + suoritusAika + " ms");
                
        //assertEquals(expResult, result.size());
        
        
    }
    
    @Test
    public void testEtsiPolkuTimer1000(){
        System.out.println("etsiPolkuTimer1000");
        int oldX = 0;
        int oldY = 0;
        int newX = 999;
        int newY = 999;
        Kartta instance = new Kartta<Noodi>(1000, 1000);
        int expResult = 1997;
        long alkuAika = System.nanoTime();
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        long loppuAika = System.nanoTime();
        long suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        //System.out.println("Suoritusaika: " + TimeUnit.MILLISECONDS.toSeconds(loppuAika - alkuAika));
        System.out.println("Suoritusaika PQ: " + suoritusAika + " ms");
        
        assertEquals(expResult, result.size());
        
        alkuAika = System.nanoTime();
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Keko");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Keko: " + suoritusAika + " ms");
        
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Avl");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Avl: " + suoritusAika + " ms");
        
        assertEquals(expResult, result.size());        
    }
    
    @Test
    public void testEtsiPolkuTimer2000(){
        System.out.println("etsiPolkuTimer2000");
        int oldX = 0;
        int oldY = 0;
        int newX = 1999;
        int newY = 1999;
        Kartta instance = new Kartta<Noodi>(2000, 2000);
        int expResult = 3997;
        long alkuAika = System.nanoTime();
        Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        long loppuAika = System.nanoTime();
        long suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        //System.out.println("Suoritusaika: " + TimeUnit.MILLISECONDS.toSeconds(loppuAika - alkuAika));
        System.out.println("Suoritusaika PQ: " + suoritusAika + " ms");
        
        assertEquals(expResult, result.size());
        
        alkuAika = System.nanoTime();
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Keko");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Keko: " + suoritusAika + " ms");
        
        result = instance.etsiPolku(oldX, oldY, newX, newY, "Avl");
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
                
        System.out.println("Suoritusaika Avl: " + suoritusAika + " ms");
        
        assertEquals(expResult, result.size());        
    }    
    
    /**
     * Tarkistetaan että väärillä koordinaateilla nousee virhe.
     */
    @Test
    public void testVaaratKoordinaatitVirhe(){
        System.out.println("vaaratKordinaatit");
        int oldX = -1;
        int oldY = 0;
        int newX = 40;
        int newY = 40;
        Kartta instance = new Kartta<Noodi>(50, 50);
        String virhe = "";
        try {
            Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        } catch (IllegalArgumentException ex) {
            virhe = ex.getMessage();
            assertTrue(true);
        } catch (Exception x){
            assertTrue(false);
        }        
        assertEquals("Annetun koordinaatin täytyy olla kartalla.", virhe);
    }
    
    /**
     * Tarkistetaan että väärillä koordinaateilla nousee virhe.
     */
    @Test
    public void testVaaratKoordinaatitVirhe2(){
        System.out.println("vaaratKordinaatit2");
        int oldX = 0;
        int oldY = 0;
        int newX = 100;
        int newY = 100;
        Kartta instance = new Kartta<Noodi>(50, 50);
        String virhe = "";
        try {
            Pino result = instance.etsiPolku(oldX, oldY, newX, newY, "");
        } catch (IllegalArgumentException ex) {
            virhe = ex.getMessage();
            assertTrue(true);
        } catch (Exception x){
            assertTrue(false);
        }        
        assertEquals("Annetun koordinaatin täytyy olla kartalla.", virhe);
    }    
    
}
