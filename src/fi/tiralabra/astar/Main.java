/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import fi.tiralabra.astar.pino.Pino;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author merte_000
 */
public class Main {    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Tervetuloa etsimään polkuja. Haluatko syöttää "
                + "karttatiedoston nimen (1) vai antaa kartan koon koordinaatteina (jokin muu numero)? ");
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Kartta kartta;
        String xAlku;
        String yAlku;
        String xLoppu;                
        String yLoppu;        

      try {
          String arvo = br.readLine();
          if ("1".equals(arvo)) {
              System.out.println("Anna suoritushakemiston juuressa sijaitsevan "
                      + "kuvatiedoston nimi tiedostopäätteineen: ");
              kartta = new Kartta<Noodi>(br.readLine());              
          } else {
              System.out.println("Anna kartan x-koordinaattien määrä: ");
              String x = br.readLine();
              System.out.println("Anna kartan y-koordinaattien määrä: ");
              String y = br.readLine();
              //Lisätään annettuihin koordinaattimääriin yksi koska kartan 
              //indeksit alkavat nollasta
              kartta = new Kartta<Noodi>(Integer.parseInt(x)+1, Integer.parseInt(y)+1);
          }
          System.out.println("Anna aloituspisteen x-koordinaatti: ");
          xAlku = br.readLine();
          System.out.println("Anna aloituspisteen y-koordinaatti: ");
          yAlku = br.readLine();
          
          System.out.println("Anna lopetuspisteen x-koordinaatti: ");
          xLoppu = br.readLine();
          System.out.println("Anna lopetuspisteen y-koordinaatti: ");
          yLoppu = br.readLine();  
          
          //Loppupisteistä vähennetään yksi koska oikeasti indeksit alkavat nollasta.
          Pino polku = kartta.etsiPolku(Integer.parseInt(xAlku), Integer.parseInt(yAlku),
          Integer.parseInt(xLoppu)-1, Integer.parseInt(yLoppu)-1, "");
                  
          
        if (polku.size() == 0) {
            System.out.println("Polkua ei löytynyt!");
        } else {
            System.out.println("Läpikuljettujen noodien määrä:" + polku.size());
            for (int i = 0; i < polku.size(); i++) {
             System.out.print("(" + polku.poista().getxPositio() + ", " + polku.poista().getyPositio() + ") -> ");
            }       
        }
        
          
      } catch (IOException ioe) {
         System.out.println("IO-virhe vastauksen tulkinnassa");
         System.exit(1);
      } catch (Exception ex){
          throw ex;
      }
                             
    }
}
