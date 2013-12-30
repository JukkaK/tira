/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tiralabra.astar;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


/**
 *
 * @author Jukka Koskelin
 */
public class LataaKuva {
    boolean kuvaLadattu = false;
    
    public LataaKuva(){
        
        ImageObserver tarkkailija = new ImageObserver() {

            @Override
            public boolean imageUpdate(Image image, int i, int i1, int i2, int i3, int i4) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
          
        String kuvanNimi = "testi.PNG";
        
        Image lahde = Toolkit.getDefaultToolkit().getImage(kuvanNimi);
        lahde.getWidth(tarkkailija);
 
        while (!kuvaLadattu) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
        
        BufferedImage kuva = graphicsConfiguration.createCompatibleImage(lahde.getWidth(null), 
        lahde.getHeight(null), Transparency.BITMASK);

        Graphics2D graphics = kuva.createGraphics();
        
        graphics.drawImage(lahde, 0, 0, null);
        
        graphics.dispose();

        int x = 10;
        int y = 10;
        
        int rgb = kuva.getRGB(x, y);
        
        System.out.println("Pikseli pisteessä [" + x + "," + y + "] RGB : " + rgb);
        
        int w = kuva.getWidth(null);

        int h = kuva.getHeight(null);
        
        int[] rgbs = new int[w*h];

        kuva.getRGB(0, 0, w, h, rgbs, 0, w);
        
        rgb = 0xFF00FF00; // vihreä
        
        kuva.setRGB(x, y, rgb);
                
    }
}
