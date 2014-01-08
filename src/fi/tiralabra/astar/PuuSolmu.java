/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tiralabra.astar;

/**
 * Binääripuu, josta olisi tarkoitus laajentaa avl-puu.
 * @author Jukka Koskelin
 */
public class PuuSolmu {
    
    PuuSolmu vasen;
    PuuSolmu oikea;
    Noodi noodi;
    int korkeus;

    PuuSolmu(Noodi noodi) {
      vasen = null;
      oikea = null;
      this.noodi = noodi;
    }  
    
    public void setKorkeus(int korkeus){
        this.korkeus = korkeus;
    }
    
    public int getKorkeus(){
        return this.korkeus;
    }
    
}
