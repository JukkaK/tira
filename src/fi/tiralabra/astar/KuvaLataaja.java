/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tiralabra.astar;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Kuvan lataamiseen tarkoitettu apuluokka.
 * @author Jukka Koskelin
 */
public class KuvaLataaja {
    
    /**
     * Luo uuden KuvaLataajan
     */
    public void KuvaLataaja(){
        
    }
    
    /**
     * Lataa kuvan annetulla tiedoston nimellä.
     * @param kuvanNimi
     * @return BufferedImage
     */
    public BufferedImage lataaKuva(String kuvanNimi){
        try {
           BufferedImage kuva = ImageIO.read(new File(kuvanNimi));
           
            int x = 10;
            int y = 10;
        
            int rgb = kuva.getRGB(x, y);
        
            //vihreä -16711936
            System.out.println("Kuvan leveys: " + kuva.getWidth());
            System.out.println("Kuvan korkeus: " + kuva.getHeight());
            System.out.println("Pikseli pisteessä [" + x + "," + y + "] RGB : " + rgb);
            
            x = 1;
            y = 1;
            rgb = kuva.getRGB(x, y);
            //valkoinen -1
            System.out.println("Pikseli pisteessä [" + x + "," + y + "] RGB : " + rgb);
            return kuva;
           
       } catch (Exception ex) {
           System.out.println("Kuvan lataaminen epäonnistui: " + ex.getMessage());
           throw new IllegalArgumentException("Kuvaa: " + kuvanNimi + " ei löydy");
           
       }          
    }
    
}
