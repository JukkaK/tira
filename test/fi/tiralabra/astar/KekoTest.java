/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
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
        System.out.println("lisaa");
        Keko instance = new Keko(2);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        Noodi noodi2 = new Noodi(0,1);
        noodi2.setMatkaJaljella(6);
        Noodi noodi3 = new Noodi(0,2);
        noodi3.setMatkaJaljella(2);
        instance.lisaa(noodi);
        instance.lisaa(noodi2);
        instance.lisaa(noodi3);
        //Lisääminen ei kaatanut ohjelmaa vaikka maksimikoko ylitettiin
        assertEquals(true, true);
        
    }
    
    /**
     * Test of poistaPienin method, of class Keko.
     */
    @Test
    public void testPoistaPienin() {
        System.out.println("poistaPienin");
        Keko instance = new Keko(2);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        Noodi noodi2 = new Noodi(0,1);
        noodi2.setMatkaJaljella(6);
        Noodi noodi3 = new Noodi(0,2);
        noodi3.setMatkaJaljella(2);
        instance.lisaa(noodi);
        instance.lisaa(noodi2);
        instance.lisaa(noodi3);
        Noodi paluu = instance.poistaPienin();
        assertEquals(paluu.getMatkaaJaljella(), 2);
        Noodi noodi4 = new Noodi(0,3);
        noodi4.setMatkaJaljella(7);
        Noodi noodi5 = new Noodi(0,4);
        noodi5.setMatkaJaljella(5);   
        instance.lisaa(noodi4);
        instance.lisaa(noodi5);
        paluu = instance.poistaPienin();
        assertEquals(paluu.getMatkaaJaljella(), 4);
        instance.lisaa(noodi3);
        paluu = instance.poistaPienin();
        assertEquals(paluu.getMatkaaJaljella(), 2);                        
    }    
    
    /**
     * Test of poistaPienin method, of class Keko.
     */
    @Test
    public void testPoistaPienin2() {        
        System.out.println("poistaPienin2");
        Keko instance = new Keko(100);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        Noodi noodi2 = new Noodi(0,1);
        noodi2.setMatkaJaljella(6);
        Noodi noodi3 = new Noodi(0,2);
        noodi3.setMatkaJaljella(2);
        Noodi noodi4 = new Noodi(0,3);
        noodi4.setMatkaJaljella(7);
        Noodi noodi5 = new Noodi(0,4);
        noodi5.setMatkaJaljella(5);   
        instance.lisaa(noodi4);
        instance.lisaa(noodi5);        
        instance.lisaa(noodi);
        instance.lisaa(noodi2);
        instance.lisaa(noodi3);        
        Noodi temp = instance.poistaPienin();
        Noodi paluu = instance.poistaPienin();
        assertEquals(paluu.getMatkaaJaljella(), 4);     
    }   
    
    /**
     * Test of onkoTyhja method, of class Keko.
     */
    @Test
    public void testOnkoTyhja2() {
        System.out.println("onkoTyhja");
        Keko instance = new Keko(100);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        instance.lisaa(noodi);
        Noodi temp = instance.poistaPienin();
        assertEquals(true, instance.onkoTyhja());       
    }     
    
    @Test 
    public void testToString(){
      System.out.println("toString");
        Keko instance = new Keko(100);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        Noodi noodi2 = new Noodi(0,1);
        noodi2.setMatkaJaljella(6);
        Noodi noodi3 = new Noodi(0,2);
        noodi3.setMatkaJaljella(2);
        Noodi noodi4 = new Noodi(0,3);
        noodi4.setMatkaJaljella(7);
        Noodi noodi5 = new Noodi(0,4);
        noodi5.setMatkaJaljella(5);   
        instance.lisaa(noodi4);
        instance.lisaa(noodi5);        
        instance.lisaa(noodi);
        instance.lisaa(noodi2);
        instance.lisaa(noodi3);      
        
        System.out.println(instance.toString());
        
        assertEquals(true, true);   
    }
    
    @Test
    public void testNaytaPienin(){
        System.out.println("naytaPienin");
        Keko instance = new Keko(100);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        Noodi noodi2 = new Noodi(0,1);
        noodi2.setMatkaJaljella(6);
        Noodi noodi3 = new Noodi(0,2);
        noodi3.setMatkaJaljella(2);
        Noodi noodi4 = new Noodi(0,3);
        noodi4.setMatkaJaljella(7);
        Noodi noodi5 = new Noodi(0,4);
        noodi5.setMatkaJaljella(5);   
        instance.lisaa(noodi4);
        instance.lisaa(noodi5);        
        instance.lisaa(noodi);
        instance.lisaa(noodi2);
        instance.lisaa(noodi3);              
        Noodi temp = instance.poistaPienin();
        Noodi paluu = instance.naytaPienin();
        assertEquals(paluu.getMatkaaJaljella(), 4);
        temp = instance.poistaPienin();
        assertEquals(temp.getMatkaaJaljella(), 4);  
        paluu = instance.naytaPienin();
        assertEquals(paluu.getMatkaaJaljella(), 5);
    }    
    
    /**
     * Testaa sisaltaakoSaman -metodin positiivisen tuloksen
     */
    @Test
    public void testSisaltaakoSaman(){
        System.out.println("sisaltaakoSaman");
        Keko instance = new Keko(100);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        Noodi noodi2 = new Noodi(0,1);
        noodi2.setMatkaJaljella(6);
        Noodi noodi3 = new Noodi(0,2);
        noodi3.setMatkaJaljella(2);
        Noodi noodi4 = new Noodi(0,3);
        noodi4.setMatkaJaljella(7);
        Noodi noodi5 = new Noodi(0,4);
        noodi5.setMatkaJaljella(5);   
        instance.lisaa(noodi4);
        instance.lisaa(noodi5);        
        instance.lisaa(noodi);
        instance.lisaa(noodi2);
        instance.lisaa(noodi3);              
        assertTrue(instance.sisaltaakoSaman(noodi));
    }
    
    /**
     * Testaa sisaltaakoSaman -metodin negatiivisen tuloksen
     */
    @Test 
    public void testSisaltaakoSaman2(){
        System.out.println("sisaltaakoSaman2");
        Keko instance = new Keko(100);
        Noodi noodi = new Noodi(0,0);
        noodi.setMatkaJaljella(4);
        Noodi noodi2 = new Noodi(0,1);
        instance.lisaa(noodi);
        assertFalse(instance.sisaltaakoSaman(noodi2));
    }
    
    
//Suorituskykytestit
//------------------
    
    private PriorityQueue<Noodi> valmistaPQ(int maara){
        PriorityQueue<Noodi> pq = new PriorityQueue<Noodi>(11, noodiVertailija);
        
        for (int i = 0; i < maara; i++) {
            Noodi noodi = new Noodi(0,0);
            int rndm = 1 + (int)(Math.random() * ((10 - 1) + 1));
            noodi.setMatkaJaljella(rndm);
            pq.add(noodi);
        }        
        return pq;
    }
    
    private Keko valmistaKeko(int maara){
        
        Keko keko = new Keko(11);
        for (int i = 0; i < maara; i++) {
            Noodi noodi = new Noodi(0,0);
            int rndm = 1 + (int)(Math.random() * ((10 - 1) + 1));
            noodi.setMatkaJaljella(rndm);
            keko.lisaa(noodi);
        }        
        return keko;        
    }
    
    @Test
    public void testSuoritusLisaa(){
        
        PriorityQueue<Noodi> pq = new PriorityQueue<Noodi>(11, noodiVertailija);
    

        long alkuAika = System.nanoTime();
        for (int i = 0; i < 5000; i++) {
            Noodi noodi = new Noodi(0,0);
            int rndm = 1 + (int)(Math.random() * ((10 - 1) + 1));
            noodi.setMatkaJaljella(rndm);
            pq.add(noodi);
        }  
        long loppuAika = System.nanoTime();
        long suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
        System.out.println("Suoritusaika pq: " + suoritusAika + " ms");
        Keko keko = new Keko(11);
        alkuAika = System.nanoTime();

        for (int i = 0; i < 5000; i++) {
            Noodi noodi = new Noodi(0,0);
            int rndm = 1 + (int)(Math.random() * ((10 - 1) + 1));
            noodi.setMatkaJaljella(rndm);
            keko.lisaa(noodi);
        }             
        
        loppuAika = System.nanoTime();
        suoritusAika = TimeUnit.NANOSECONDS.toMillis(loppuAika - alkuAika);
        System.out.println("Suoritusaika keko: " + suoritusAika + " ms");        
    }
    
    @Test
    public void testSuoritusPoistaPienin(){
        PriorityQueue<Noodi> pq = valmistaPQ(5000);
        long alkuAika = System.nanoTime();
        pq.poll();
        long loppuAika = System.nanoTime();        
        System.out.println("Suoritusaika pq: " + (loppuAika-alkuAika) + " ns");

        
        Keko keko = valmistaKeko(5000);
        alkuAika = System.nanoTime();
        keko.poistaPienin();
        loppuAika = System.nanoTime();
        System.out.println("Suoritusaika keko: " + (loppuAika-alkuAika) + " ns");        
    }
    
    /**
     * Vertailija Noodi -luokalle. 
     */
    public static Comparator<Noodi> noodiVertailija = new Comparator<Noodi>(){
 
        @Override
        /**
         * Vertailee annettuja Noodeja, palauttaa n1:n ja n2:n erotuksen
         */
        public int compare(Noodi n1, Noodi n2) {
            return (int) (n1.getMatkaaJaljella() - n2.getMatkaaJaljella());
        }
    };        
}
