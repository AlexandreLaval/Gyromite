package VueControleur;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


import Modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private HashMap<String,ImageIcon>  imgIcons;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;
        imgIcons = new HashMap<String,ImageIcon>();
        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : jeu.getHeros().gauche(); break;
                    case KeyEvent.VK_RIGHT : jeu.getHeros().droite();break;
                    case KeyEvent.VK_DOWN : jeu.getHeros().bas(); break;
                    case KeyEvent.VK_UP : jeu.getHeros().haut(); break;

                }
            }
        });
    }


    private void chargerLesIcones() {
        imgIcons.put("ProfIdle",chargerIcone("Images/profIdle01.png"));
       // imgIcons.put("ProfClimb",chargerIcone("Images/profClimb.png"));
       // imgIcons.put("ProfDead",chargerIcone("Images/profDead.png"));
        imgIcons.put("Mur",chargerIcone("Images/mur.png"));
        imgIcons.put("Corde",chargerIcone("Images/corde.png"));
      //  imgIcons.put("Bombe",chargerIcone("Images/bombe02.png"));
      //  imgIcons.put("Ennemi",chargerIcone("Images/ennemiIdle01.png"));
      //  imgIcons.put("Navet",chargerIcone("Images/navet.png"));
        imgIcons.put("BasColonne",chargerIcone("Images/basColonne.png"));
        imgIcons.put("MilieuColonne",chargerIcone("Images/milieuColonne.png"));
        imgIcons.put("HautColonne",chargerIcone("Images/hautColonne.png"));
        imgIcons.put("PlateformeHoriz",chargerIcone("Images/plateformeHorizontal.png"));
        imgIcons.put("CaseVide",chargerIcone("Images/caseVide.png"));
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }


    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Entite e = jeu.getEntite(x, y);

                if (e instanceof Mur) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Mur"));
                } else if (e instanceof CaseNormale) {
                    tabJLabel[x][y].setIcon(imgIcons.get("CaseVide"));
                }else if(e instanceof  Colonne){
                    if(! (jeu.getEntite(x,y-1) instanceof  Colonne))
                        tabJLabel[x][y].setIcon((imgIcons.get("HautColonne")));
                    else if(!(jeu.getEntite(x,y+1) instanceof  Colonne))
                        tabJLabel[x][y].setIcon((imgIcons.get("BasColonne")));
                    else
                        tabJLabel[x][y].setIcon((imgIcons.get("MilieuColonne")));
                }
            }
        }



        tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(imgIcons.get("ProfIdle"));

    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                });
        */

    }
}