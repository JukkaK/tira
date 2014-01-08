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
private PuuSolmu vanhempi;

/**Binääripuun alkuperäinen toteutus */

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

  /*public void insert(Noodi noodi) {
    juuri = insert(juuri, noodi);
  }*/

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
    
    
    /** AVL-puun toteutus **/
    
        /**
         * Lisää noodin puuhun
         * @param noodi Lisättävä Noodi
         */
       public void insert(Noodi noodi)
        {
            PuuSolmu solmu = new PuuSolmu(noodi);
            juuri = insert(solmu, juuri);
        }
    
        /**
         * Uuden solmun lisäys puuhun, sekä puun tasapainoitus. Tehdään
         * siis rekursiivinen lisäys, eli metodi kutsuu tarvittaessa itseään.
         * @param solmu lisättävä PuuSolmu
         * @param juuri annettu juuri PuuSolmu
         * @return uusi juuri PuuSolmu
         */
        private PuuSolmu insert(PuuSolmu solmu, PuuSolmu juuri)
        {
            if( juuri == null )
                juuri = new PuuSolmu(solmu.noodi);
           else if(vertaaSolmuja(solmu, juuri) < 0)
            {
                //Jos annettu solmu on pienempi kuin juurisolmu,
                //lisätään se vasemmalle puolelle
                juuri.vasen = insert(solmu, juuri.vasen);
                
                //Tarkistetaan tasapaino
                if (laskeKorkeus(juuri.vasen) - laskeKorkeus(juuri.oikea) == 2) {
                    if (vertaaSolmuja(solmu, juuri.vasen) < 0) {
                        //Lisätty on pienempi kuin juuren vasen solmu
                        juuri = kaannaOikealle( juuri );
                    } else {
                        //Lisätty on suurempi kuin juuren vasen solmu
                       juuri = vasenOikeaKaannos( juuri );                   
                    }                    
                }                                
            }
            else if(vertaaSolmuja(solmu, juuri) > 0 )
            {
                //jos annettu solmu on suurempi kuin juurisolmu,
                //lisätään se oikealle puolelle
                juuri.oikea = insert(solmu, juuri.oikea);
                //Tarkistetaan tasapaino
                if( laskeKorkeus(juuri.oikea) - laskeKorkeus(juuri.vasen) == 2 )
                    if(vertaaSolmuja(solmu, juuri.oikea) > 0 )
                        //Lisätty on suurempi kuin juuren oikea solmu
                        juuri = kaannaVasemmalle(juuri);
                    else
                        //Lisätty on pienempi kuin juuren oikea solmu
                        juuri = oikeaVasenKaannos(juuri);
            }
            else{
                //Jos annettu solmu on tismalleen sama kuin juurisolmu,
                //TODO: pitäisi varmaan tehdä jotain? Kait noodeilla voi olla
                //sama jäljellä oleva matka?
                System.out.println("Yritettiin lisätä solmua joka löytyy jo!");
            }
            
            //Päivitetään juuren korkeutta
            juuri.korkeus = max(laskeKorkeus(juuri.vasen), laskeKorkeus(juuri.oikea)) + 1;
            return juuri;
        }    
        
        /**
         * Palauttaa puun pienimmän Noodin ja poistaa sen.
         * @return Noodi
         */
        public Noodi poistaPieninNoodi(){
            PuuSolmu pienin = etsiPienin(juuri);
            remove(pienin, juuri);
            return pienin.noodi;
        }
                
        /**
         * Internal method to remove from a subtree.
         * @param x the item to remove.
         * @param t the node that roots the subtree.
         * @return the new root of the subtree.
         */
        private PuuSolmu remove( PuuSolmu solmu, PuuSolmu juuri )
        {            
            if( juuri == null )
                return juuri;

            int vertailu = vertaaSolmuja(solmu, juuri);

            //Jos poistettava on pienempi kuin juuri, poistetaan vasemmalla
            if(vertailu < 0){                                
                juuri.vasen = remove(solmu, juuri.vasen);                
            }
            //Jos poistettava on suurempi kuin juuri, poistetaan oikealta                                                
            else if(vertailu > 0) {
                juuri.oikea = remove(solmu, juuri.oikea);                                                        
            }
            // Jos vertailun tulos 0, on löydetty poistettava solmu            
            //Jos solmulla on kaksi lasta            
            else if( juuri.vasen != null && juuri.oikea != null )
            {                
                //Asetetaan löydettyyn solmuun arvoksi oikean puolen pienin
                //arvo, ts. korvataan se pienimmällä
                juuri.noodi = etsiPienin(juuri.oikea).noodi;
                //Sitten poistetaan noodi jolla on korvattu, eli duplikaatti
                juuri.oikea = remove(solmu, juuri.oikea);                
            } else {
              //Jos solmulla on vain yksi lapsi, asetetaan se solmun tilalle  
              juuri = (juuri.vasen != null) ? juuri.vasen : juuri.oikea;
            } 
            //Lopuksi tasapainotetaan koko puu            
            return tasapainotaPuu(juuri);
        }  
   
        /**
         * Tasapainottaa puun annetusta solmusta alaspäin.
         * @param solmu
         * @return Asennetun solmun korvannut solmu
         */
        private PuuSolmu tasapainotaPuu(PuuSolmu solmu)
         {
             if( solmu == null )
                 return solmu;
             //Jos puu on kallellaan vasemmalle
             if( laskeKorkeus(solmu.vasen) - laskeKorkeus(solmu.oikea) > 1)
                 if( laskeKorkeus(solmu.vasen.vasen) >= laskeKorkeus(solmu.vasen.oikea))
                     solmu = kaannaOikealle(solmu);
                 else
                     solmu = vasenOikeaKaannos(solmu);
             else
             //Jos puu on kallellaan oikealle    
             if( laskeKorkeus(solmu.oikea) - laskeKorkeus(solmu.vasen) > 1)
                 if(laskeKorkeus(solmu.oikea.oikea) >= laskeKorkeus(solmu.oikea.vasen))
                     solmu = kaannaVasemmalle(solmu);
                 else
                     solmu = oikeaVasenKaannos(solmu);
             //Päivitetään solmun korkeutta
             solmu.korkeus = Math.max(laskeKorkeus(solmu.vasen), laskeKorkeus(solmu.oikea))+1;
             return solmu;
         }        
    
        /**
         * Etsii puun pienimmän solmun, eli vasemmanpuoleisimman lehden
         * @param solmu
         * @return PuuSolmu
         */
        private PuuSolmu etsiPienin(PuuSolmu solmu)
        {
            if( solmu == null )
                return solmu;

            while( solmu.vasen != null )
                solmu = solmu.vasen;
            return solmu;
        }    
    

        /**
         * Palauta solmun korkeus, tai -1 jos korkeutta ei ole
         */
        private int laskeKorkeus(PuuSolmu solmu)
        {
            return solmu == null ? -1 : solmu.korkeus;
        }    

        /**
        * Palauttaa suuremman annetuista parametreistä
        */
        private static int max( int a, int b )
        {
            return a > b ? a : b;
        }

        /**
         * Kääntää annetun solmun oikealle, eli tekee annetusta solmusta
         * sen vasemman solmun oikean lapsen, ja vasemman solmun oikeasta lapsesta
         * annetun solmun vasemman lapsen.
         * @param solmu
         * @return PuuSolmu, joka ottaa annetun solmun paikan paikallisen puun
         * ylimpänä solmuna
         */
        private PuuSolmu kaannaOikealle( PuuSolmu k2 )
        {
            PuuSolmu k1 = k2.vasen;
            k2.vasen = k1.oikea;
            k1.oikea = k2;
            k2.korkeus = max(laskeKorkeus(k2.vasen), laskeKorkeus(k2.oikea)) + 1;
            k1.korkeus = max(laskeKorkeus(k1.vasen), k1.korkeus) + 1;
            return k1;
        }

        /**
         * Kääntää annetun solmun vasemmalle, eli tekee annetusta solmusta
         * sen oikean solmun vasemman lapsen, ja oikean solmun vasemmasta
         * lapsesta annetun solmun oikean lapsen.
         * @param k1
         * @return PuuSolmu, joka ottaa annetun solmun paikan paikallisen puun
         * ylimpänä solmuna
         */
        private PuuSolmu kaannaVasemmalle (PuuSolmu k1){
            PuuSolmu k2 = k1.oikea;
            k1.oikea = k2.vasen;
            k2.vasen = k1;
            k1.korkeus = max(laskeKorkeus(k1.vasen), laskeKorkeus(k1.oikea) ) + 1;
            k2.korkeus = max(laskeKorkeus(k2.vasen), k2.korkeus) + 1;
            return k2;
        }
    
        /**
         * Kääntää ensin annetun solmun vasemmalle, ja sitten oikealle,
         * jolloin solmun vasemman lapsen oikea lapsi nousee solmun paikalle
         * ja solmu putoaa tämän oikeaksi lapseksi
         * @param k3
         * @return PuuSolmu, joka ottaa annetun solmun paikan paikallisen puun
         * ylimpänä solmuna
         */
        private PuuSolmu vasenOikeaKaannos(PuuSolmu k3)
        {
            k3.vasen = kaannaVasemmalle(k3.vasen);
            return kaannaOikealle(k3);
        }    

        /**
         * Kääntää ensin annetun solmun oikealle, ja sitten vasemmalle,
         * jolloin solmun oikean lapsen vasen lapsi nousee solmun paikalle,
         * ja solmu putoaa tämän vasemmaksi lapseksi
         * @param k1
         * @return PuuSolmu, joka ottaa annetun solmun paikan paikallisen puun
         * ylimpänä solmuna
         */
        private PuuSolmu oikeaVasenKaannos(PuuSolmu k1){
           k1.oikea = kaannaOikealle(k1.oikea);
           return kaannaVasemmalle(k1);
        }
        
        private int vertaaSolmuja (PuuSolmu x, PuuSolmu y){
            return (x.noodi.getMatkaaJaljella() - y.noodi.getMatkaaJaljella());
        }
            
}
