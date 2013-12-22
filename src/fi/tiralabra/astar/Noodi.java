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
public class Noodi {
    
    /* Liikkumisen kustannus */
    protected static final int LIIKKUMINEN = 10;
    
    private int xPositio;
    private int yPositio;
    
    //Voidaanko tämän Noodin läpi kulkea?
    private boolean kuljettava;
    
    //Mistä Noodista tähän on tultu.
    private Noodi edellinenNoodi;
    
    //Minkä verran on matkattu aloitusnoodista tähän Noodiin.
    private int matkaTehty;

    //Arvioitu matkan määrä tästä Noodista lopetusnoodiin.
    private int matkaJaljella;
    
    /**
     * Luo Noodin annetuilla koordinaateilla ja asettaa sen kuljettavaksi.
     * @param xPositio
     * @param yPositio 
     */
    public Noodi(int xPositio, int yPositio){
        this.xPositio = xPositio;
        this.yPositio = yPositio;
        this.kuljettava = true;
    }
    
    /**
     * Asettaa Noodille annetut koordinaatit.
     * @param xPositio
     * @param yPositio 
     */
    public void AsetaKoordinaatit(int xPositio, int yPositio){
        this.xPositio = xPositio;
        this.yPositio = yPositio;
    }
    
    /**
     * 
     * @return xPositio
     */
    public int getxPositio(){
        return this.xPositio;
    }
    
    /**
     * 
     * @return yPositio
     */
    public int getyPositio(){
        return this.yPositio;
    }
    
    /**
     * 
     * @return Jos Noodi on kuljettava, palauttaa true
     */
    public boolean getKuljettava(){
        return this.kuljettava;
    }
    
    /**
     * Asettaa Noodin kuljettavaksi tai ei-kuljettavaksi
     * @param kuljettava 
     */
    public void setKuljettava(boolean kuljettava){
        this.kuljettava = kuljettava;
    }
    
    /**
     * 
     * @return edellinen Noodi
     */
    public Noodi getEdellinenNoodi(){
        return this.edellinenNoodi;
    }
    
    /**
     * Asettaa edelliseksi Noodiksi annetun Noodin
     * @param noodi 
     */
    public void setEdellinenNoodi(Noodi noodi){
        this.edellinenNoodi = noodi;
    }
    
    /**
     * 
     * @return tehdyn matkan ja jäljellä olevan matkan arvioitu summa
     */
    public int getKokoMatka () {
        return this.matkaJaljella + this.matkaTehty;
    }
    
    /**
     * 
     * @return matkaTehty
     */
    public int getTehtyMatka () {
        return this.matkaTehty;
    }
    
    /**
     * 
     * @return matkaJaljella
     */
    public int getMatkaaJaljella() {
        return this.matkaJaljella;
    }
    
    /**
     * Asettaa tehdyn matkan.
     * @param matka 
     */
    public void setTehtyMatka (int matka) {
        this.matkaTehty = matka;
    }
    
    /**
     * Asettaa jäljellä olevan matkan
     *
     * @param matka joka asetetaan
     */
    protected void setMatkaJaljella(int matka) {
        this.matkaJaljella = matka;
    }    
    
    /**
     * Laskee jäljellä olevan matkan annettuun Noodiin ns. Manhattanin metodilla:
     * H = 10*(abs(currentX-targetX) + abs(currentY-targetY))
     * @param viimeinenNoodi 
     */
    public void setMatkaJaljella(Noodi viimeinenNoodi) {
        this.setMatkaJaljella((absolute(this.getxPositio() - viimeinenNoodi.getxPositio())
                + absolute(this.getyPositio() - viimeinenNoodi.getyPositio()))
                * LIIKKUMINEN);
        }

    /**
     * Palauttaa positiivisen luvun jos a on suurempi kuin nolla, negatiivisen jos ei
     * @param a
     * @return 
     */
    private int absolute(int a) {
        return a > 0 ? a : -a;
    }   
                
    /**
     * 
     * @param edellinenNoodi
     */
    public void setTehtyMatka(Noodi edellinenNoodi) {

        setTehtyMatka(edellinenNoodi.getTehtyMatka() + LIIKKUMINEN);
    }     
    
    /**
     * Laskee tehdyn matkan annettuun Noodiin asti
     * @param previousAbstractNode
     * @return 
     */
    public int laskeTehtyMatka(Noodi previousAbstractNode) {        
            return (previousAbstractNode.getTehtyMatka()
                    + LIIKKUMINEN);        
    }    
        
     /**
     * palauttaa true mikä tällä ja annetulla Noodilla ovat samat koordinaatit
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Noodi other = (Noodi) obj;
        if (this.xPositio != other.xPositio) {
            return false;
        }
        if (this.yPositio != other.yPositio) {
            return false;
        }
        return true;
    }
}
