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
    
    private Noodi[] Keko;
    private int maxKoko;
    private int koko;
    
    public Keko (int maxKoko){
        
        this.maxKoko = maxKoko;
        koko = 0;
        this.Keko = new Noodi[maxKoko];
        Keko[0] = new Noodi(0, 0);
    }
    
    public void lisaa(int arvo){
        
    }
    
}
