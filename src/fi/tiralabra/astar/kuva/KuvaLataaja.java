/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tiralabra.astar.kuva;

import fi.tiralabra.astar.Noodi;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Kuvan lataamiseen tarkoitettu apuluokka.
 * @author Jukka Koskelin
 */
public class KuvaLataaja<T extends Noodi> {
    
    protected final int VIHREA = -16711936;
    
    protected final int VALKOINEN = -1;

    
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
    
    /**
     * Muodostaa uuden png-kuvan annetuilla parametreillä
     * @param noodit kartta, joka piirretään
     * @param polku polku, joka piirretään kartan päälle
     * @param leveys kuvan leveys
     * @param korkeus  kuvan korkeus
     */
    public void muodostaKuva( List<Noodi> polku, BufferedImage kuva){
        Graphics2D graphics = kuva.createGraphics();
        System.out.println("polku size: " + polku.size());
        for(int i = 1; i <= polku.size()-1; i++){
            graphics = kuva.createGraphics();            
            graphics.setColor(Color.BLUE);
            graphics.drawOval(polku.get(i).getxPositio(), polku.get(i).getyPositio(), 0, 0);            
        }
        
        File outputfile = new File("tulos.png");
        try {
            ImageIO.write(kuva, "png", outputfile);
        } catch (IOException ex) {
            System.out.println("Kuvan kirjoittaminen epäonnistui: " + ex.getMessage());
        }
        
    }
    
}
