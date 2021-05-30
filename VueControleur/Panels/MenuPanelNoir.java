package VueControleur.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanelNoir extends JPanel {

    private BufferedImage image;
    private BufferedImage imageRobot;

    public MenuPanelNoir(){
        this.setBackground(Color.black);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        try {
            image = ImageIO.read(new File("Images/Gyromite_Smick.png"));
            imageRobot = ImageIO.read(new File("Images/Robot-Series.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(image,600,60,null);
        g.drawImage(imageRobot, 0, 340, null);

        Font fontGyro = new Font("Courier", Font.BOLD, 80);
        g.setFont(fontGyro);
        g.setColor(Color.blue);
        g.drawString("GYROMITE", 100, 55);

        Font fontNom = new Font("Courier", Font.ITALIC, 20);
        g.setFont(fontNom);
        g.setColor(Color.red);
        g.drawString("LAVAL Alexandre", 50, 200);
        g.drawString("THORAL Anaïs", 50, 220);
        g.drawRoundRect(45, 180, 170,50,30,30);

        Font fontPoly = new Font("Courier", Font.ITALIC, 40);
        g.setFont(fontPoly);
        g.setColor(Color.YELLOW);
        g.drawString("Polytech", 500, 220);

    }
}
