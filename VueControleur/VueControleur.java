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
    private final Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private final int sizeX; // taille de la grille affichée
    private final int sizeY;

    private final int sizeImg = 28;

    // icones affichées dans la grille
    private HashMap<String,ImageIcon>  imgIcons;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        sizeX = Jeu.SIZE_X;
        sizeY = Jeu.SIZE_Y;
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
        imgIcons.put("HerosIdle",chargerIcone("Images/herosIdle.png"));
        imgIcons.put("HerosClimb",chargerIcone("Images/herosClimb.png"));
        imgIcons.put("HerosDead",chargerIcone("Images/herosDead.png"));
        imgIcons.put("Mur",chargerIcone("Images/mur.png"));
        imgIcons.put("Corde",chargerIcone("Images/corde.png"));
        imgIcons.put("Bombe",chargerIcone("Images/bombe.png"));
        imgIcons.put("Ennemi",chargerIcone("Images/enemyIdle.png"));
        imgIcons.put("Navet",chargerIcone("Images/navet.png"));
        imgIcons.put("ColonneBas",chargerIcone("Images/colonneBas.png"));
        imgIcons.put("ColonneMilieu",chargerIcone("Images/colonneMilieu.png"));
        imgIcons.put("ColonneHaut",chargerIcone("Images/colonneHaut.png"));
        imgIcons.put("PlateformeHoriz",chargerIcone("Images/plateformeHoriz.png"));
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
        setSize(sizeX*sizeImg, sizeY*sizeImg);
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
                } else if (e instanceof CaseVide) {
                    tabJLabel[x][y].setIcon(imgIcons.get("CaseVide"));
                }else if (e instanceof Navet) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Navet"));
                }else if (e instanceof Corde) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Corde"));
                }else if (e instanceof Bombe) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Bombe"));
                }else if (e instanceof PoutreHorizontal) {
                    tabJLabel[x][y].setIcon(imgIcons.get("PlateformeHoriz"));
                }else if (e instanceof PoutreHorizontal) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Ennemi"));
                }
            }
        }



        tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(imgIcons.get("HerosIdle"));

    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
    }
}
