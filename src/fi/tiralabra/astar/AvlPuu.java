/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tiralabra.astar;

/**
 *
 * @author Jukka Koskelin
 */
public class AvlPuu {
    
private PuuSolmu juuri;

  public void AvlPuu() {
    juuri = null;
  }

  public boolean lookup(Noodi noodi) {
    return(lookup(juuri, noodi));
  }

  private boolean lookup(PuuSolmu solmu, Noodi noodi) {
    if (solmu==null) {
      return(false);
    }

    if (noodi==solmu.noodi) {
      return(true);
    }
    else if (noodi.getMatkaaJaljella()<solmu.noodi.getMatkaaJaljella()) {
      return(lookup(solmu.vasen, noodi));
    }
    else {
      return(lookup(solmu.oikea, noodi));
    }
  }

  public void insert(Noodi noodi) {
    juuri = insert(juuri, noodi);
  }

  private PuuSolmu insert(PuuSolmu solmu, Noodi noodi) {
    if (solmu==null) {
      solmu = new PuuSolmu(noodi);
    }
    else {
      if (noodi.getMatkaaJaljella() <= solmu.noodi.getMatkaaJaljella()) {
        solmu.vasen = insert(solmu.vasen, noodi);
      }
      else {
        solmu.oikea = insert(solmu.oikea, noodi);
      }
    }

    return(solmu);
  }

    public void tulostaJarjestyksessa(){
    tulostaJarjestyksessa( juuri );
    }
    private void tulostaJarjestyksessa(PuuSolmu solmu) {
        if ( solmu == null ) return;
        tulostaJarjestyksessa(solmu.vasen);
        System.out.println( solmu.noodi.getMatkaaJaljella() );
        tulostaJarjestyksessa(solmu.oikea);
    }

    public int laskeSolmut(){
        return laskeSolmut(juuri, 0);
        

    }
    //Aikavaatimus: N, missä N on solmujen määrä
    //Tilavaatimus: 2N
    private int laskeSolmut(PuuSolmu solmu, int summa){
        if (solmu == null) return summa;
        summa = laskeSolmut(solmu.vasen, summa);
        summa++;
        summa = laskeSolmut(solmu.oikea, summa);
        return summa;
    }

    public boolean onkoTasapainossa(){

        if (juuri == null || (juuri.oikea == null && juuri.vasen == null)) {
            return true;
        }

        if (laskeKorkeus(juuri.oikea, 0) == laskeKorkeus(juuri.vasen, 0)) {
            return true;
        }
        return false;
    }

    private int laskeKorkeus(PuuSolmu solmu, int korkeus){

        if (solmu == null) {
            return 0;
        }
        if (solmu.oikea == null && solmu.vasen == null) {
            return korkeus;
        }

        int OikeanKorkeus = laskeKorkeus(solmu.oikea, korkeus+1);
        int VasemmanKorkeus = laskeKorkeus(solmu.vasen, korkeus+1);
        if (OikeanKorkeus > VasemmanKorkeus) {
            return OikeanKorkeus;
        } else {
            return VasemmanKorkeus;
        }
    }

    public void tulostaTasot(){
        System.out.println(juuri.noodi.getMatkaaJaljella());
        tulostaTasot(juuri);
    }

    private void tulostaTasot(PuuSolmu solmu){
        if (solmu == null) return;
        if (solmu.vasen != null) System.out.println(solmu.vasen.noodi.getMatkaaJaljella() + ", isä: " + solmu.noodi.getMatkaaJaljella());
        if (solmu.oikea != null) System.out.println(solmu.oikea.noodi.getMatkaaJaljella() + ", isä: " + solmu.noodi.getMatkaaJaljella());
        tulostaTasot(solmu.vasen);
        tulostaTasot(solmu.oikea);
    }

    public boolean SamaPuu(AvlPuu toinenPuu){
       return SamaPuu(juuri, toinenPuu.juuri);
    }

    private boolean SamaPuu(PuuSolmu a, PuuSolmu b){
        if (a == null && b == null) return true;

        else if (a!=null && b!=null) {
            return(
                a.noodi == b.noodi &&
                SamaPuu(a.vasen, b.vasen) &&
                SamaPuu(a.oikea, b.oikea)
            );
  }

  else return false;

    }    
    
}
