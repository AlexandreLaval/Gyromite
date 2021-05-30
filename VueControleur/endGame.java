package VueControleur;

import VueControleur.Panels.gameOverPanel;
import VueControleur.Panels.winPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class endGame extends JFrame implements MouseListener {

    public boolean isWin;
    public int score;

    public endGame(boolean _win){
        this.isWin = _win;
        this.setTitle("END GAME");
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        //panel bouton
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(Color.black);



        //si perdu
        if(!isWin) {
            this.getContentPane().add(new gameOverPanel(score), BorderLayout.CENTER);

            JButton btnJouer = new JButton("MENU");
            btnJouer.setForeground(Color.black);
            btnJouer.setBackground(Color.blue);
            btnJouer.setFocusPainted(false);
            btnJouer.addMouseListener(this);

            panelBtn.add(btnJouer);
        }
        else if(isWin) {
            this.getContentPane().add(new winPanel(score), BorderLayout.CENTER);

            JButton btnJouer = new JButton("NIVEAU SUIVANT");
            btnJouer.setForeground(Color.black);
            btnJouer.setBackground(Color.blue);
            btnJouer.setFocusPainted(false);
            btnJouer.addMouseListener(this);

            panelBtn.add(btnJouer);
        }


        this.getContentPane().add(panelBtn, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Menu menu = new Menu();
        this.setVisible(false);
        menu.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
