/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import java.util.List;
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
     * Test of setKuljettava method, of class Kartta.
     */
  /*  @Test
    public void testSetKuljettava() {
        System.out.println("setKuljettava");
        int x = 0;
        int y = 0;
        boolean bool = false;
        Kartta instance = null;
        instance.setKuljettava(x, y, bool);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of getNoodi method, of class Kartta.
     */
  /*  @Test
    public void testGetNoodi() {
        System.out.println("getNoodi");
        int x = 0;
        int y = 0;
        Kartta instance = null;
        Noodi expResult = null;
        Noodi result = instance.getNoodi(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    } */

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
        int expResult = 80;
        List<Noodi> result = instance.etsiPolku(oldX, oldY, newX, newY);

        for (int i = 0; i < result.size(); i++) {
            System.out.print("(" + result.get(i).getxPositio() + ", " + result.get(i).getyPositio() + ") -> ");
        }
        
        System.out.println("Tuloksessa noodeja yhteensä: " + result.size());
        
        assertEquals(expResult, result.size());
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
     * Testataan kartan luomista kuvatiedostosta, ja polun etsintää
     */
    @Test
    public void testLuoKarttaKuvastaJaEtsiPolku(){
        Kartta instance = new Kartta<Noodi>("testi.png");
        int oldX = 7;
        int oldY = 0;
        int newX = 7;
        int newY = 49;
        int expResult = 51;
        List<Noodi> result = instance.etsiPolku(oldX, oldY, newX, newY);

        for (int i = 0; i < result.size(); i++) {
            System.out.print("(" + result.get(i).getxPositio() + ", " + result.get(i).getyPositio() + ") -> ");
        }
        
        System.out.println("Tuloksessa noodeja yhteensä: " + result.size());
        
        assertEquals(expResult, result.size());
                
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

    
}
