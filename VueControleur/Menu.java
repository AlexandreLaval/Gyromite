package VueControleur;

import Modele.plateau.Jeu;
import VueControleur.Panels.MenuPanelBleu;
import VueControleur.Panels.MenuPanelNoir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JFrame implements MouseListener {

    private int niveau = 1;

    public Menu(int _niveau){

        niveau = _niveau;

        //bases de la fenetre
        this.setTitle("Menu du jeu");
        this.setSize(800,800);
        this.setLocationRelativeTo(null);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel bouton
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(Color.yellow);

        JButton btnJouer = new JButton("JOUER");
        btnJouer.setForeground(Color.black);
        btnJouer.setBackground(Color.white);
        btnJouer.setFocusPainted(false);
        btnJouer.addMouseListener(this);

        panelBtn.add(btnJouer);

        //ajout dans la fenetre
        this.setLayout(new BorderLayout());
        this.getContentPane().add(new MenuPanelBleu(), BorderLayout.NORTH);
        this.getContentPane().add(new MenuPanelNoir(), BorderLayout.CENTER);
        this.getContentPane().add(panelBtn, BorderLayout.SOUTH);

        this.setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Jeu jeu = new Jeu(niveau);

        VueControleur vc = new VueControleur(jeu);

        jeu.getOrdonnanceur().addObserver(vc);

        vc.setVisible(true);

        jeu.getOrdonnanceur().start();

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
