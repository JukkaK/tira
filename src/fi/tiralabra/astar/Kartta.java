/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;


/**
 *
 * @author Jukka Koskelin
 */
public class Kartta<T extends Noodi> {
    
    /** säilöö noodit. Ensimmäisessä sarakkeessa x-, toisessa y-koordinaatit. */
    private T[][] noodit;
    
    /** Kartan leveys (x-akseli) */
    protected int leveys;
    
    /** Kartan korkeus (y-akseli) */
    protected int korkeus;
    
    
    /**
     * Luo uuden kartan annetulla leveydellä ja pituudella
     * @param leveys
     * @param pituus 
     */
    public Kartta(int leveys, int korkeus) {
        if (leveys > 0 && korkeus > 0){
            noodit = (T[][]) new Noodi[leveys][korkeus];
            this.leveys = leveys - 1; //-1, koska indeksit lähtee nollasta
            this.korkeus = korkeus - 1; //-1, koska indeksit lähtee nollasta
            AlustaNoodit();        
        } else {
            throw new IllegalArgumentException("Arvot eivät voi olla pienempiä kuin yksi");
        }       
    }
    
    /**
     * Alustaa Kartalle tyhjiä Noodeja
     */
    private void AlustaNoodit() {
        //Kaksinkertainen looppi, eli täytetään kolumni kerrallaan
        for (int i = 0; i <= leveys; i++) {
            for (int j = 0; j <= korkeus; j++) {
                noodit[i][j] = (T) new Noodi(i,j);
            }
        }
    }
    
    /**
     * Asettaa annettujen koordinaattien Noodille arvon Kuljettava totuusarvon mukaisesti
     * @param x
     * @param y
     * @param bool 
     */
    public void setKuljettava(int x, int y, boolean bool) {
        //TODO: varmaan pitäisi testata ettei ole nollaa pienempi arvoja
        if (x <= this.leveys && y <= this.korkeus) {
            noodit[x][y].setKuljettava(bool);
        } else {
            throw new IllegalArgumentException("Annetut koordinaatit eivät löydy kartalta");
        }       
              
    }
    
    /**
     * Palauttaa Noodin annetuista koordinaateista
     * @param x
     * @param y
     * @return Noodi
     */
    public final T getNoodi(int x, int y) {
        //TODO: Korjattava tarkistukset, 0,0 -piste kaatuu näihin tarkistuksiin
        //if (x > 0 && x <= this.leveys && y > 0 && y <= this.pituus){
            return noodit[x][y];
        //} else {
            //return null;
        //}
        
    }
    
    /** Lista jossa läpikäymättömät viereiset Noodit */
    private PriorityQueue<Noodi> kaymattomatNoodit;
    /** Läpikäydyt Noodit */
    private List<T> kaydytNoodit;
    /** Onko polku löytynyt? */
    private boolean valmis = false;   
    
    /**
     * Etsii (lyhimmän) polun annettujen x,y -koordinaattien välillä
     * @param alkuX
     * @param alkuY
     * @param loppuX
     * @param loppuY
     * @return 
     */
    public final List<T> etsiPolku(int alkuX, int alkuY, int loppuX, int loppuY) {
      // TODO: Tarkistukset?      
      kaymattomatNoodit = new PriorityQueue<Noodi>(11, noodiVertailija);
      kaydytNoodit = new LinkedList<T>();
      kaymattomatNoodit.add(noodit[alkuX][alkuY]);

      valmis = false;
      T valittu;
      while (!valmis) {
          /** Haetaan läpikäymättömistä noodeista lyhimmän matka-arvion omaava */
          valittu = (T)lyhinMatkaLapikaymattomissa(); 
          /** Lisätään noodi läpikäytyihin ja poistetaan -käymättömistä */
          kaydytNoodit.add(valittu); 
          kaymattomatNoodit.remove(valittu);

          /** Onko piste loppupiste? */
          if ((valittu.getxPositio() == loppuX)
                  && (valittu.getyPositio() == loppuY)) { 
              return laskePolku(noodit[alkuX][alkuY], valittu);
          }

          // Käytään läpi kaikki viereiset Noodit
          List<T> viereisetNoodit = getViereinen(valittu);
          for (int i = 0; i < viereisetNoodit.size(); i++) {
              T valittuViereinen = viereisetNoodit.get(i);
              /** Noodi ei ole läpikäymättömissä, asetetaan käsittelyssä oleva Noodi tämän edeltäjäksi ja lasketaan matka-arvot, sekä
              lisätään Noodi läpikäymättömiin*/
              if (!kaymattomatNoodit.contains(valittuViereinen)) {
                  valittuViereinen.setEdellinenNoodi(valittu); 
                  valittuViereinen.setMatkaJaljella(noodit[loppuX][loppuY]); 
                  valittuViereinen.setTehtyMatka(valittu); 
                  kaymattomatNoodit.add(valittuViereinen);
              /** Noodi on läpikäymättömissä*/
              } else {
                  /** Jos matka käsittelyssä olevan Noodin kautta on lyhyempi kuin Noodiin aiemmin tallennettu matka,
                   aseta käsittelyssä oleva Noodi edeltäjäksi ja päivitä matkaa */                  
                  if (valittuViereinen.getTehtyMatka()> valittuViereinen.laskeTehtyMatka(valittu)) { 
                      valittuViereinen.setEdellinenNoodi(valittu); 
                      valittuViereinen.setTehtyMatka(valittu);
                  }
              }
          }

          if (kaymattomatNoodit.isEmpty()) { 
              return new LinkedList<T>(); 
          }
      }
      return null;
  }
    
    /**
     * laskee polun annetun alun ja maalin välillä.
     *
     * @param alku
     * @param maali
     * @return
     */
    private List<T> laskePolku(T alku, T maali) {

        LinkedList<T> polku = new LinkedList<T>();

        T valittu = maali;
        boolean valmis = false;
        while (!valmis) {
            polku.addFirst(valittu);
            valittu = (T) valittu.getEdellinenNoodi();

            if (valittu.equals(alku)) {
                valmis = true;
            }
        }
        return polku;
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
        return kaymattomatNoodit.poll();
    }    
    
   /**
     * returns LinkedList jossa annetun noodin viereiset noodin,
     * jos ne ovat kuljettavissa eikä niitä ole vielä läpikäyty.
     */
    private List<T> getViereinen(T node) {

        int x = node.getxPositio();
        int y = node.getyPositio();
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
