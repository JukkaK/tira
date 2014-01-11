/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar.pino;

import fi.tiralabra.astar.Noodi;

/**
 *
 * @author merte_000
 */
public class Pino {
    
    private Noodi[] Taulu;
    private int korkeus;
    private int koko;
    
    public Pino(int koko){
        this.koko = koko;
        this.Taulu = new  Noodi[this.koko];
        this.korkeus =-1;
    }
    
    /**
     * Sijoittaa annetun Noodin pinon päälimmäiseksi.
     * @param noodi 
     */
    public void tyonna(Noodi noodi){
        if(korkeus+1 == this.koko){
            Noodi[] tempTaulu = this.Taulu.clone();
            this.koko = this.koko*2;
            this.Taulu = new Noodi[this.koko];
            for (int i = 0; i < tempTaulu.length; i++) {
                this.Taulu[i] = tempTaulu[i];
            }

        }        
        this.korkeus++;
        this.Taulu[this.korkeus] = noodi;                        
    }
    
    /**
     * Poistaa pinon päälimmäisen alkion ja palauttaa sen.
     * @return Noodi
     */
    public Noodi poista(){
       Noodi paluu = this.Taulu[this.korkeus];
       this.korkeus--;
       return paluu;
    }
    
    /**
     * Palauttaa tosi, mikäli pino on tyhjä
     * @return boolean
     */
    public boolean onkoTyhja(){
        return this.korkeus==-1;
    }
    
    /**
     * Palauttaa pinon korkeuden
     * @return 
     */
    public int size(){
        return this.korkeus;
    }
    
    /**
     * Palauttaa Pinosta uuden instanssin
     * @return 
     */
    public Pino teeKopio(){
        Pino uusiPino = new Pino(koko);
        uusiPino.koko = this.koko;
        uusiPino.Taulu = this.Taulu.clone();
        uusiPino.korkeus = this.korkeus;        
        return uusiPino;
    }
}
