package VueControleur;

import Modele.plateau.Jeu;
import VueControleur.Panels.gameOverPanel;
import VueControleur.Panels.winPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class endGame extends JFrame implements MouseListener {

    public boolean isWin;
    public int score;
    public int niveau;

    public endGame(boolean _win, int _niveau){
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
            btnJouer.setBackground(Color.white);
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
        if(isWin && niveau!=2){
            Jeu jeu = new Jeu(2);

            VueControleur vc = new VueControleur(jeu);

            jeu.getOrdonnanceur().addObserver(vc);

            vc.setVisible(true);

            jeu.getOrdonnanceur().start();
            this.dispose();
        }
        else if (!isWin && niveau==1)
            new Menu(1);

        else if (!isWin && niveau==2)
            new Menu(2);
        this.dispose();
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
