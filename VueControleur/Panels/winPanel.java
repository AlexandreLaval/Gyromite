package VueControleur.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class winPanel extends JPanel {

    private BufferedImage imageMur;
    private BufferedImage imageCorde;
    private BufferedImage imagePlateforme;

    private int score;


    public winPanel(int _score){
        score = _score;
        this.setPreferredSize(new Dimension(500, 300));
        this.setBackground(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            imageMur = ImageIO.read(new File("Images/mur.png"));
            imageCorde = ImageIO.read(new File("Images/corde.png"));
            imagePlateforme = ImageIO.read(new File("Images/plateformeHoriz.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(imageMur,0,10,null);
        g.drawImage(imageMur,30,10,null);
        g.drawImage(imageMur,60,10,null);
        g.drawImage(imageMur,90,10,null);
        g.drawImage(imageMur,120,10,null);
        g.drawImage(imageMur,300,10,null);
        g.drawImage(imageMur,330,10,null);
        g.drawImage(imageMur,360,10,null);
        g.drawImage(imageMur,390,10,null);
        g.drawImage(imageMur,420,10,null);
        g.drawImage(imageMur,450,10,null);
        g.drawImage(imageMur,480,10,null);

        g.drawImage(imageCorde,90,40,null);
        g.drawImage(imageCorde,90,70,null);
        g.drawImage(imageCorde,90,100,null);
        g.drawImage(imageCorde,90,130,null);
        g.drawImage(imageCorde,90,160,null);
        g.drawImage(imageCorde,330,40,null);
        g.drawImage(imageCorde,330,70,null);
        g.drawImage(imageCorde,330,100,null);
        g.drawImage(imageCorde,330,130,null);
        g.drawImage(imageCorde,330,160,null);

        g.drawImage(imagePlateforme,0,400,null);
        g.drawImage(imagePlateforme,30,400,null);
        g.drawImage(imagePlateforme,60,400,null);
        g.drawImage(imagePlateforme,90,400,null);
        g.drawImage(imagePlateforme,120,400,null);
        g.drawImage(imagePlateforme,150,400,null);
        g.drawImage(imagePlateforme,180,400,null);
        g.drawImage(imagePlateforme,210,400,null);
        g.drawImage(imagePlateforme,240,400,null);
        g.drawImage(imagePlateforme,270,400,null);
        g.drawImage(imagePlateforme,300,400,null);
        g.drawImage(imagePlateforme,330,400,null);
        g.drawImage(imagePlateforme,360,400,null);
        g.drawImage(imagePlateforme,390,400,null);
        g.drawImage(imagePlateforme,420,400,null);
        g.drawImage(imagePlateforme,450,400,null);
        g.drawImage(imagePlateforme,480,400,null);

        Font fontSCore = new Font("Courier", Font.BOLD, 40);
        g.setFont(fontSCore);
        g.setColor(Color.green);
        g.drawString(String.valueOf(score), 160, 37);

        Font fontGameOver = new Font("Courier", Font.BOLD, 60);
        g.setFont(fontGameOver);
        g.setColor(Color.red);
        g.drawString("GAME WIN", 60, this.getHeight()/2);

    }
}
