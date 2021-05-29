package VueControleur.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanelBleu extends JPanel {

    private BufferedImage image;

    public MenuPanelBleu() {

        this.setPreferredSize(new Dimension(750, 250));
        this.setBackground(Color.blue);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            image = ImageIO.read(new File("Images/menu.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image,100,0,null);

        Font font = new Font("Courier", Font.BOLD, 60);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("BIENVENUE !", 250, 150);
    }
}
