/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

/**
 *
 * @author Jukka Koskelin
 */
public class Keko {
    
    private Noodi[] Taulu;
    private int maxKoko;
    private int koko;
    
    /**
     * Konstruktori luokalle joka toteuttaa minimikeon.
     * @param maxKoko Keon suurin koko aloitettaessa
     */
    public Keko (int maxKoko){
        
        this.maxKoko = maxKoko;
        koko = 0;
        Taulu = new Noodi[maxKoko];        
    }
    
    /**
     * Lisää kekoon annetun arvon ja järjestää keon
     * @param arvo 
     */
    public void lisaa(Noodi arvo){
        //Taulun maksimiarvon ylitys: luo uusi taulukko joka on
        //tuplasti suurempi kuin alkuperäinen. Kopioi alkuperäisen taulukon
        //sisältö uuteen taulukkoon.        
        if (koko+1 == this.maxKoko) {
            Noodi[] tempTaulu = this.Taulu.clone();
            this.maxKoko = this.maxKoko*2;
            this.Taulu = new Noodi[this.maxKoko];
            for (int i = 0; i < tempTaulu.length; i++) {
                this.Taulu[i] = tempTaulu[i];
            }
        }
        
        //Kasvatetaan kokoa ennen lisäystä, eli käytännössä taulun iteraattori
        //alkaa arvosta 1. Tämä pitää ottaa huomioon kaikissa metodeissa, eli
        //unohdetaan "oikea" ensimmäinen alkio kokonaan, sitä ei edes alusteta.
        koko++;
        this.Taulu[koko] = arvo;
        tyonnaYlos(koko);                
    }
    
    /**
     * Suorittaa keon järjestämisen alkaen annetusta pisteestä. Niin kauan kuin 
     * lisätty arvo on pienempi kuin vanhempansa, siirretään lisättyä arvoa 
     * kohti puun juurta (jossa on siis pienin arvo). Lopetetaan kun vanhempi 
     * on pienempi kuin lisätty arvo, jolloin 
     * puu toteuttaa minimikeon ehdon.
     * @param nykyinen 
     */
    private void tyonnaYlos(int nykyinen){
        
        if (nykyinen > 1) {                            
            while(Taulu[nykyinen].getMatkaaJaljella() < Taulu[vanhempi(nykyinen)].getMatkaaJaljella()){
                vaihda(nykyinen, vanhempi(nykyinen));
                nykyinen = vanhempi(nykyinen);
                if (nykyinen < 2) {
                    break;
                }
            }
        }        
    }
    
    /**
     * Onko keko tyhjä?
     * @return tosi, jos keko on tyhjä
     */
    public boolean onkoTyhja(){
        return koko == 0;
    }
    
   /**
     * Returns a String representation of BinaryHeap with values stored with 
     * heap structure and order properties.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < koko; i++) {
            sb.append(" ");
            sb.append(Taulu[i]);
        }                      
        return sb.toString();
    } 
    
    /**
     * Tarkastetaan löytyykö keosta annettua Noodia vastaavaa Noodia
     * @param noodi
     * @return 
     */
    public boolean sisaltaakoSaman(Noodi noodi){
        for (int i = 1; i < koko; i++) {
            if (noodi.getxPositio() == Taulu[i].getxPositio() &&
                    noodi.getyPositio() == Taulu[i].getyPositio() &&
                    noodi.getKuljettava() == Taulu[i].getKuljettava() &&
                    noodi.getMatkaaJaljella() == Taulu[i].getMatkaaJaljella()) {
                return true;
            }
        }
        return false;
    }    
    
    /**
     * palauttaa annetun pointterin vanhemman paikan (i/2)
     * @param paikka
     * @return 
     */
    private int vanhempi(int paikka){
        return paikka / 2;
    }
    
    /**
     * Palauttaa annetun pointterin vasemman lapsen, eli taulukossa 2i.
     * @param paikka
     * @return 
     */
    private int vasenLapsi(int paikka){
        return 2 * paikka;
    }
    
    /**
     * Palauttaa annetun pointterin oikean lapsen, eli tauluossa 2i+1.
     * @param paikka
     * @return 
     */
    private int oikeaLapsi(int paikka){
        return 2* paikka +1;
    }    
    
    /**
     * Tutkii onko annettu paikka lehti. Palauttaa tosi, jos annettu paikka sijaitsee
     * taulun puolen välin alapuolella.
     * @param paikka
     * @return 
     */
    private boolean onkoLehti(int paikka){
        if ((paikka > koko/2) && (paikka <= koko)) {         
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Vaihtaa kahden annetun kohdan (ts. parametrit ovat pointtereita keon 
     * toteuttavaan taulukkoon) paikkaa.
     * @param eka
     * @param toka 
     */
    private void vaihda(int eka, int toka){
        Noodi temp;
        temp = Taulu[eka];
        Taulu[eka] = Taulu[toka];
        Taulu[toka] = temp;        
    }
    
    /**
     * 
     * @param paikka 
     */
    private void painaAlas(int paikka) {
    int pieninLapsi;
    //Käydään läpi niin kauan kun ei olla puun lehtitasolla
        while (!onkoLehti(paikka)) {
            //Vasemmalla on aina pienempi lapsi
            pieninLapsi = vasenLapsi(paikka);
            //
            if ((pieninLapsi < koko) && (Taulu[pieninLapsi].getMatkaaJaljella() > Taulu[pieninLapsi+1].getMatkaaJaljella()))
                pieninLapsi = pieninLapsi + 1;
            //
            if (Taulu[paikka].getMatkaaJaljella() <= Taulu[pieninLapsi].getMatkaaJaljella()) 
                return;
            //
            vaihda(paikka,pieninLapsi);
            paikka = pieninLapsi;
        }        
    }
    
    /**
     * Palauttaa keon pieinimmän arvon poistamatta sitä keosta.
     * @return 
     */
    public Noodi naytaPienin(){
        return Taulu[1];    
    }
    
    /**
     * Poistaa pienimmän alkion keosta
     * @return 
     */
    public Noodi poistaPienin() {        
        vaihda(1,koko);
        koko--;
        if (koko != 0)
            painaAlas(1);
        return Taulu[koko+1];
    }
    
}
