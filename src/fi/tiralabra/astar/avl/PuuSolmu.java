/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tiralabra.astar.avl;

import fi.tiralabra.astar.Noodi;

/**
 * Binääripuu, josta olisi tarkoitus laajentaa avl-puu.
 * @author Jukka Koskelin
 */
public class PuuSolmu {
    
    PuuSolmu vasen;
    PuuSolmu oikea;
    Noodi noodi;
    int korkeus;

    public PuuSolmu(Noodi noodi) {
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
    
    /**
     * HashCoden ylikirjoitus valituilla Noodin arvoilla.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + this.noodi.getMatkaaJaljella();
        hash = hash * 31 + this.noodi.getxPositio();
        hash = hash * 13 + this.noodi.getyPositio();        
        return hash;
    }
    
    
}
