/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar.laskenta;

import fi.tiralabra.astar.keko.Keko;
import fi.tiralabra.astar.Noodi;
import fi.tiralabra.astar.pino.Pino;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Jukka Koskelin
 */
public class LaskentaKeko<T extends Noodi> {
    /** Lista jossa läpikäymättömät viereiset Noodit */
    private Keko kaymattomatNoodit;
    /** Läpikäydyt Noodit */
    private List<T> kaydytNoodit;
    /** Onko polku löytynyt? */
    private boolean valmis = false;
    
    /** säilöö noodit. Ensimmäisessä sarakkeessa x-, toisessa y-koordinaatit. */
    private T[][] noodit;
    
        /** Kartan leveys (x-akseli) */
    protected int leveys;
    
    /** Kartan korkeus (y-akseli) */
    protected int korkeus;

    
    public LaskentaKeko(T[][] noodit, int leveys, int korkeus){
        this.noodit = noodit;
        this.korkeus = korkeus;
        this.leveys = leveys;        
    }
    /**
     * Etsii (lyhimmän) polun annettujen x,y -koordinaattien välillä
     * @param alkuX
     * @param alkuY
     * @param loppuX
     * @param loppuY
     * @return 
     */
    public final Pino etsiPolku(int alkuX, int alkuY, int loppuX, int loppuY) {      
      
      kaymattomatNoodit = new Keko(11);                
      kaydytNoodit = new LinkedList<T>();
      kaymattomatNoodit.lisaa(noodit[alkuX][alkuY]);

      valmis = false;
      T valittu;
      while (!valmis) {
          /** Haetaan läpikäymättömistä noodeista lyhimmän matka-arvion omaava */
          valittu = (T)lyhinMatkaLapikaymattomissa(); 
          /** Lisätään noodi läpikäytyihin ja poistetaan -käymättömistä */
          kaydytNoodit.add(valittu);           
          kaymattomatNoodit.poistaPienin();

          /** Onko piste loppupiste? */
          if ((valittu.getxPositio() == loppuX)
                  && (valittu.getyPositio() == loppuY)) { 
              return laskePolku(noodit[alkuX][alkuY], valittu);
          }

          // Käytään läpi kaikki viereiset Noodit
          List<T> viereisetNoodit = getViereinen(valittu);
          for (int i = 0; i < viereisetNoodit.size(); i++) {
              T valittuViereinen = viereisetNoodit.get(i);
              if (!kaymattomatNoodit.sisaltaakoSaman(valittuViereinen)) {
                  asetaKaymatonNoodi(kaymattomatNoodit, valittuViereinen, valittu, loppuX, loppuY);
              } else {
                  /** Jos matka käsittelyssä olevan Noodin kautta on lyhyempi kuin Noodiin aiemmin tallennettu matka,
                   aseta käsittelyssä oleva Noodi edeltäjäksi ja päivitä matkaa */                  
                  asetaUusiLyhinMatka(valittuViereinen, valittu);
              }
          }

          if (kaymattomatNoodit.onkoTyhja()) { 
              return new Pino(10); 
          }
      }
      return null;
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
    
    /**
     * laskee polun annetun alun ja maalin välillä.
     *
     * @param alku
     * @param maali
     * @return
     */
    protected Pino laskePolku(T alku, T maali) {

        Pino polku = new Pino(10);

        T valittu = maali;
        boolean valmis = false;
        while (!valmis) {
            polku.tyonna(maali);
            valittu = (T) valittu.getEdellinenNoodi();

            if (valittu.equals(alku)) {
                valmis = true;
            }
        }
        return polku;
    }        
    
   /**
     * returns LinkedList jossa annetun noodin viereiset noodin,
     * jos ne ovat kuljettavissa eikä niitä ole vielä läpikäyty.
     */
    private List<T> getViereinen(T noodi) {

        int x = noodi.getxPositio();
        int y = noodi.getyPositio();
        List<T> viereinen = new LinkedList<T>();

        T temp;
        if (x > 0) {
            temp = this.getNoodi((x - 1), y);
            if (temp.getKuljettava()&& !kaydytNoodit.contains(temp)) {
                viereinen.add(temp);
            }
        }

        if (x < leveys) {
            temp = this.getNoodi((x + 1), y);
            if (temp.getKuljettava()&& !kaydytNoodit.contains(temp)) {
                viereinen.add(temp);
            }
        }

        if (y > 0) {
            temp = this.getNoodi(x, (y - 1));
            if (temp.getKuljettava() && !kaydytNoodit.contains(temp)) {
                viereinen.add(temp);
            }
        }

        if (y < korkeus) {
            temp = this.getNoodi(x, (y + 1));
            if (temp.getKuljettava() && !kaydytNoodit.contains(temp)) {                
                viereinen.add(temp);
            }
        }

        return viereinen;
    }            
    
    /**
     * Noodi ei ole läpikäymättömissä, asetetaan käsittelyssä oleva Noodi tämän 
     * edeltäjäksi ja lasketaan matka-arvot, sekä lisätään Noodi läpikäymättömiin
     * @param kaymattomatNoodit
     * @param valittuViereinen
     * @param valittu
     * @param loppuX
     * @param loppuY 
     */
    private void asetaKaymatonNoodi(Keko kaymattomatNoodit, 
        Noodi valittuViereinen, Noodi valittu, int loppuX, int loppuY){        
        valittuViereinen.setEdellinenNoodi(valittu); 
        valittuViereinen.setMatkaJaljella(noodit[loppuX][loppuY]); 
        valittuViereinen.setTehtyMatka(valittu); 
        kaymattomatNoodit.lisaa(valittuViereinen);
    }
    
    /**
     * Jos matka käsittelyssä olevan Noodin kautta on lyhyempi kuin 
     * Noodiin aiemmin tallennettu matka,
     * aseta käsittelyssä oleva Noodi edeltäjäksi ja päivitä matkaa
     * @param valittuViereinen
     * @param valittu 
     */
    private void asetaUusiLyhinMatka(Noodi valittuViereinen, Noodi valittu){
        if (valittuViereinen.getTehtyMatka()> valittuViereinen.laskeTehtyMatka(valittu)) { 
            valittuViereinen.setEdellinenNoodi(valittu); 
            valittuViereinen.setTehtyMatka(valittu);
        }
    }                
    
    /**
     * palauttaa läpikäymättömistä noodeista sen jolla on pienin arvo
     * lopulle kuljettavalle matkalle
     *
     * @return
     */
    private Noodi lyhinMatkaLapikaymattomissa() {
        //Palauttaa PriorityQueuen pienemmimmän arvon (haetaan vertailijalla,
        //joka vertailee Noodien MatkaJaljella -arvoja), mutta ei poista sitä
        //keosta.        
        return kaymattomatNoodit.naytaPienin();
    }  
    
    /**
     * Palauttaa Noodin annetuista koordinaateista
     * @param x
     * @param y
     * @return Noodi
     */
    public final T getNoodi(int x, int y) {
        if (x >= 0 && x <= this.leveys && y >= 0 && y <= this.korkeus){
            return noodit[x][y];
        } else {
            return null;
        }
        
    }    
}
