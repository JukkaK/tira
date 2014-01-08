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
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


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
    
    protected int VIHREA = -16711936;
    
    protected int VALKOINEN = -1;
    
    protected BufferedImage kuva;
    
    
    /**
     * Luo uuden kartan annetulla leveydellä ja pituudella
     * @param leveys
     * @param pituus 
     */
    public Kartta(int leveys, int korkeus) {
        luoKartta(leveys, korkeus, false);
    }
    
    /**
     * Luo Kartan kovakoodatusta kuvatiedostosta
     */
    public Kartta(String kuvanNimi){
        try {
           kuva = ImageIO.read(new File(kuvanNimi));
           
            int x = 10;
            int y = 10;
        
            int rgb = kuva.getRGB(x, y);
        
            //vihreä -16711936
            System.out.println("Kuvan leveys: " + kuva.getWidth());
            System.out.println("Kuvan korkeus: " + kuva.getHeight());
            System.out.println("Pikseli pisteessä [" + x + "," + y + "] RGB : " + rgb);
            
            x = 1;
            y = 1;
            rgb = kuva.getRGB(x, y);
            //valkoinen -1
            System.out.println("Pikseli pisteessä [" + x + "," + y + "] RGB : " + rgb);

           
       } catch (Exception ex) {
           System.out.println("Kuvan lataaminen epäonnistui: " + ex.getMessage());
           throw new IllegalArgumentException("Kuvaa: " + kuvanNimi + " ei löydy");
           
       }  
        
       this.kuva = kuva;
       luoKartta(kuva.getWidth(), kuva.getHeight(), true);
    }
    
    /**
     * 
     * @param leveys
     * @param korkeus 
     */
    protected void luoKartta(int leveys, int korkeus, boolean onkoKuva){
        if (leveys > 0 && korkeus > 0){
            noodit = (T[][]) new Noodi[leveys][korkeus];
            this.leveys = leveys - 1; //-1, koska indeksit lähtee nollasta
            this.korkeus = korkeus - 1; //-1, koska indeksit lähtee nollasta
            AlustaNoodit(onkoKuva);                    
        } else {
            throw new IllegalArgumentException("Arvot eivät voi olla pienempiä kuin yksi");
        }               
    }    
    
    /**
     * Alustaa Kartalle tyhjiä Noodeja
     */
    protected void AlustaNoodit(boolean onkoKuva) {
        //Kaksinkertainen looppi, eli täytetään kolumni kerrallaan
        for (int i = 0; i <= leveys; i++) {
            for (int j = 0; j <= korkeus; j++) {
                noodit[i][j] = (T) new Noodi(i,j);
                if (onkoKuva) {
                    //Jos on annettu kuvatiedosto, tulkitaan siitä onko pisteessä (x,y)
                    //valkoista vai vihreää väriä. Jos vihreäää, asetetaan pisteessä
                    //sijaitsevaan noodiin este.
                    int rgb = this.kuva.getRGB(i, j);
                    //TODO: Testausta varten, poistettava lopullisesta versiosta.
                    //if (rgb == VALKOINEN){
                        //System.out.println("x:" + i + " y: " + j + " väri on: " + rgb);
                    //}
                    if (rgb == VIHREA) {
                        //System.out.println("x:" + i + " y: " + j + " väri on: " + rgb);
                        noodit[i][j].setKuljettava(false);
                    }

                }
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
        if (x >= 0 && x <= this.leveys && y >= 0 && y <= this.korkeus){
            return noodit[x][y];
        } else {
            return null;
        }
        
    }
    
    /** Lista jossa läpikäymättömät viereiset Noodit */
    //private PriorityQueue<Noodi> kaymattomatNoodit;
    private Keko kaymattomatNoodit;
    //private AvlPuu kaymattomatNoodit;
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
      //kaymattomatNoodit = new PriorityQueue<Noodi>(11, noodiVertailija);
        kaymattomatNoodit = new Keko(11);
        //kaymattomatNoodit = new AvlPuu();
      kaydytNoodit = new LinkedList<T>();
      //kaymattomatNoodit.add(noodit[alkuX][alkuY]);
      kaymattomatNoodit.lisaa(noodit[alkuX][alkuY]);
      //kaymattomatNoodit.insert(noodit[alkuX][alkuY]);

      valmis = false;
      T valittu;
      while (!valmis) {
          /** Haetaan läpikäymättömistä noodeista lyhimmän matka-arvion omaava */
          valittu = (T)lyhinMatkaLapikaymattomissa(); 
          /** Lisätään noodi läpikäytyihin ja poistetaan -käymättömistä */
          kaydytNoodit.add(valittu); 
          //kaymattomatNoodit.remove(valittu);
          kaymattomatNoodit.poistaPienin();
          //kaymattomatNoodit.poistaPieninNoodi();

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

          //if (kaymattomatNoodit.isEmpty()) { 
          //if (kaymattomatNoodit.onkoTyhja()) { 
          if (kaymattomatNoodit.onkoTyhja()) { 
              return new LinkedList<T>(); 
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
    protected List<T> laskePolku(T alku, T maali) {

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
//    private void asetaKaymatonNoodi(PriorityQueue<Noodi> kaymattomatNoodit, 
//        Noodi valittuViereinen, Noodi valittu, int loppuX, int loppuY){        
    private void asetaKaymatonNoodi(Keko kaymattomatNoodit, 
        Noodi valittuViereinen, Noodi valittu, int loppuX, int loppuY){        
 //private void asetaKaymatonNoodi(AvlPuu kaymattomatNoodit, 
 //       Noodi valittuViereinen, Noodi valittu, int loppuX, int loppuY){            
        valittuViereinen.setEdellinenNoodi(valittu); 
        valittuViereinen.setMatkaJaljella(noodit[loppuX][loppuY]); 
        valittuViereinen.setTehtyMatka(valittu); 
        //kaymattomatNoodit.add(valittuViereinen);
        kaymattomatNoodit.lisaa(valittuViereinen);
        //kaymattomatNoodit.insert(valittuViereinen);
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
        //return kaymattomatNoodit.poll();
        return kaymattomatNoodit.naytaPienin();
        //return kaymattomatNoodit.naytaPieninNoodi();
    }                       
}
