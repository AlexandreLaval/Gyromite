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

    public endGame(boolean _win, int _niveau, int _score){
        this.isWin = _win;
        this.niveau = _niveau;
        this.score = _score * 100;

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
        else {
            this.getContentPane().add(new winPanel(score), BorderLayout.CENTER);
            JButton btnJouer;
            if(this.niveau != Jeu.NB_MAX_OF_LVL) {
                btnJouer  = new JButton("NIVEAU SUIVANT");
            } else{
                btnJouer  = new JButton("QUITTER");
            }
            btnJouer.setForeground(Color.black);
            btnJouer.setBackground(Color.white);
            btnJouer.setFocusPainted(false);
            btnJouer.addMouseListener(this);

            panelBtn.add(btnJouer);
        }


        this.getContentPane().add(panelBtn, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void nextLevel(int niveau){
        Jeu jeu = new Jeu(niveau);

        VueControleur vc = new VueControleur(jeu);

        jeu.getOrdonnanceur().addObserver(vc);

        vc.setVisible(true);

        jeu.getOrdonnanceur().start();
        this.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isWin && niveau==1){
            nextLevel(2);
        } else if(isWin && niveau == 2){
            System.exit(0);
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
