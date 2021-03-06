/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import fi.tiralabra.astar.keko.Keko;
import fi.tiralabra.astar.kuva.KuvaLataaja;
import fi.tiralabra.astar.laskenta.LaskentaAvl;
import fi.tiralabra.astar.laskenta.LaskentaKeko;
import fi.tiralabra.astar.laskenta.LaskentaPQ;
import fi.tiralabra.astar.pino.Pino;
import java.util.List;
import java.awt.image.BufferedImage;


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
    
    protected final int VIHREA = -16711936;
    
    protected final int VALKOINEN = -1;
    
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
       KuvaLataaja lataaja = new KuvaLataaja();
       this.kuva = lataaja.lataaKuva(kuvanNimi);
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
            this.leveys = leveys-1; //-1, koska indeksit lähtee nollasta
            this.korkeus = korkeus-1; //-1, koska indeksit lähtee nollasta
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
     * Piirtää kartasta kuvan ja sen päälle annetun polun
     * @param polku 
     */
    private void piirraKuva(Pino polku){
        KuvaLataaja lataaja = new KuvaLataaja();
        lataaja.muodostaKuva(polku, kuva);
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
    private Keko kaymattomatNoodit;
    /** Läpikäydyt Noodit */
    private List<T> kaydytNoodit;
    /** Onko polku löytynyt? */
    private boolean valmis = false;   
    
    public final Pino etsiPolku(int alkuX, int alkuY, int loppuX, int loppuY, String tietorakenne) {
        if (!tarkistaX(alkuX) || !tarkistaY(alkuY) || !tarkistaX(loppuX) || !tarkistaY(loppuY)) {
            throw new IllegalArgumentException("Annetun koordinaatin täytyy olla kartalla.");
        }
        Pino polku;    
        //Ohjelmaa voidaan ajaa kolmella eri tietorakenteella joka tallentaa 
        //käymättömät noodit. Keko-toteutus on oletusarvona.
        if (tietorakenne.contains("PQ")) {
            LaskentaPQ laskenta = new LaskentaPQ(this.noodit, this.leveys, this.korkeus);
            polku = laskenta.etsiPolku(alkuX, alkuY, loppuX, loppuY);       
        } else if (tietorakenne.contains("AVL")) {
            LaskentaAvl laskenta = new LaskentaAvl(this.noodit, this.leveys, this.korkeus);
            polku = laskenta.etsiPolku(alkuX, alkuY, loppuX, loppuY);            
        }         
            LaskentaKeko laskenta = new LaskentaKeko(this.noodit, this.leveys, this.korkeus);
            polku = laskenta.etsiPolku(alkuX, alkuY, loppuX, loppuY);                                    
        
        
        
        if(this.kuva != null){
            piirraKuva(polku.teeKopio());
        }
        
        return polku;
    }   
    
    /**
     * Tarkistaa onko annettu leveyskoordinaatti kartalla.
     * @param x
     * @return boolean
     */
    private boolean tarkistaX(int x){
        if (x < 0 || x > this.leveys) {
            return false;
        }
        return true;
    }
    
    /**
     * Tarkistaa onko annettu korkeuskoordinaatti kartalla.
     * @param y
     * @return boolean
     */
    private boolean tarkistaY(int y){
        if (y < 0 || y > this.korkeus) {
            return false;
        }
        return true;
    }
        
}
