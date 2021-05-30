package VueControleur;

import java.awt.*;
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


import Modele.deplacements.ColonneControle;
import Modele.deplacements.Controle4Directions;
import Modele.deplacements.Direction;
import Modele.plateau.*;
import Modele.plateau.enums.ColonneType;
import Modele.plateau.enums.SupportColonneType;


/**
 * Cette classe a deux fonctions :
 * (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 * (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction, etc.))
 */
public class VueControleur extends JFrame implements Observer {
    private final Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private final int sizeX; // taille de la grille affichée
    private final int sizeY;

    private final int sizeImg = 28;

    // icones affichées dans la grille
    private HashMap<String, ImageIcon> imgIcons;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {

        sizeX = Jeu.SIZE_X;
        sizeY = Jeu.SIZE_Y;
        jeu = _jeu;
        imgIcons = new HashMap<String, ImageIcon>();
        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }


    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.Gauche);
                        break;
                    case KeyEvent.VK_RIGHT:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.Droite);
                        break;
                    case KeyEvent.VK_DOWN:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.Bas);
                        break;
                    case KeyEvent.VK_UP:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.Haut);
                        break;
                    case KeyEvent.VK_A:
                        ColonneControle.getInstance().setDirectionCourante();
                        break;
                }
            }
        });
    }


    private void chargerLesIcones() {
        imgIcons.put("HerosIdleD", chargerIcone("Images/herosIdleDroite.png"));
        imgIcons.put("HerosIdleG", chargerIcone("Images/herosIdleGauche.png"));
        imgIcons.put("HerosClimb", chargerIcone("Images/herosClimb.png"));
        imgIcons.put("HerosDead", chargerIcone("Images/herosDead.png"));
        imgIcons.put("Mur", chargerIcone("Images/mur.png"));
        imgIcons.put("Corde", chargerIcone("Images/corde.png"));
        imgIcons.put("Bombe", chargerIcone("Images/bombe.png"));
        imgIcons.put("Navet", chargerIcone("Images/navet.png"));
        imgIcons.put("ColonneBas", chargerIcone("Images/colonneBas.png"));
        imgIcons.put("ColonneMilieu", chargerIcone("Images/colonneMilieu.png"));
        imgIcons.put("ColonneHaut", chargerIcone("Images/colonneHaut.png"));
        imgIcons.put("PlateformeHoriz", chargerIcone("Images/plateformeHoriz.png"));
        imgIcons.put("CaseVide", chargerIcone("Images/caseVide.png"));
        imgIcons.put("SmickClimb", chargerIcone("Images/smickClimb.png"));
        imgIcons.put("SmickIdleD", chargerIcone("Images/smickIdleDroite.png"));
        imgIcons.put("SmickIdleG", chargerIcone("Images/smickIdleGauche.png"));
        imgIcons.put("PlateformeVert", chargerIcone("Images/plateformeVerticale.png"));
        imgIcons.put("SupportColonneG", chargerIcone("Images/supportColonneG.png"));
        imgIcons.put("SupportColonneD", chargerIcone("Images/supportColonneD.png"));
        imgIcons.put("BordureSup", chargerIcone("Images/bordureSuperieure.png"));
        imgIcons.put("HerosJumpD", chargerIcone("Images/herosJumpD.png"));
        imgIcons.put("HerosJumpG", chargerIcone("Images/herosJumpG.png"));
        imgIcons.put("SmickJumpD", chargerIcone("Images/smickJumpD.png"));
        imgIcons.put("SmickJumpG", chargerIcone("Images/smickJumpG.png"));
        imgIcons.put("Vie", chargerIcone("Images/life.png"));
        imgIcons.put("Zero", chargerIcone("Images/zero.png"));
        imgIcons.put("Un", chargerIcone("Images/un.png"));
        imgIcons.put("Deux", chargerIcone("Images/deux.png"));
        imgIcons.put("Trois", chargerIcone("Images/trois.png"));
        imgIcons.put("Quatre", chargerIcone("Images/quatre.png"));
        imgIcons.put("Cinq", chargerIcone("Images/cinq.png"));
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
        setSize(sizeX * sizeImg, sizeY * sizeImg);
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
                } else if (e instanceof Plafond) {
                    tabJLabel[x][y].setIcon(imgIcons.get("BordureSup"));
                } else if (e instanceof Navet) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Navet"));
                } else if (e instanceof Corde) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Corde"));
                } else if (e instanceof Bombe) {
                    tabJLabel[x][y].setIcon(imgIcons.get("Bombe"));
                } else if (e instanceof PoutreHorizontal) {
                    tabJLabel[x][y].setIcon(imgIcons.get("PlateformeHoriz"));
                } else if (e instanceof PoutreVertical) {
                    tabJLabel[x][y].setIcon(imgIcons.get("PlateformeVert"));
                } else if (e instanceof SupportColonne) {
                    if(((SupportColonne) e).getSupportColonneType().equals(SupportColonneType.Droite)){
                        tabJLabel[x][y].setIcon(imgIcons.get("SupportColonneD"));
                    } else if( ((SupportColonne) e).getSupportColonneType().equals(SupportColonneType.Gauche)){
                        tabJLabel[x][y].setIcon(imgIcons.get("SupportColonneG"));
                    }
                } else if (e instanceof Heros) {
                    if (((Heros) e).getCasePrecedente() instanceof Corde) {
                        tabJLabel[x][y].setIcon(imgIcons.get("HerosClimb"));
                    }
                    else {
                        if (((Heros) e).getFaceDirection().equals(Direction.Droite)) {
                            if(((Heros) e).isFalling()){
                                tabJLabel[x][y].setIcon(imgIcons.get("HerosJumpD"));
                            }else {
                                tabJLabel[x][y].setIcon(imgIcons.get("HerosIdleD"));
                            }
                        } else if (((Heros) e).getFaceDirection().equals(Direction.Gauche)) {
                            if(((Heros) e).isFalling()){
                                tabJLabel[x][y].setIcon(imgIcons.get("HerosJumpG"));
                            }else{
                                tabJLabel[x][y].setIcon(imgIcons.get("HerosIdleG"));
                            }
                        }
                    }
                } else if (e instanceof Smick) {
                    if (((Smick) e).getCasePrecedente() instanceof Corde) {
                        tabJLabel[x][y].setIcon(imgIcons.get("SmickClimb"));
                    } else {
                        if (((Smick) e).getFaceDirection().equals(Direction.Droite)) {
                            if(((Smick) e).isFalling()){
                                tabJLabel[x][y].setIcon(imgIcons.get("SmickJumpD"));
                            }else{
                                tabJLabel[x][y].setIcon(imgIcons.get("SmickIdleD"));
                            }
                        } else if (((Smick) e).getFaceDirection().equals(Direction.Gauche)) {
                            if(((Smick) e).isFalling()){
                                tabJLabel[x][y].setIcon(imgIcons.get("SmickJumpG"));
                            }else
                            {
                                tabJLabel[x][y].setIcon(imgIcons.get("SmickIdleG"));
                            }
                        }
                    }
                } else if (e instanceof Colonne) {
                    if (((Colonne) e).getColonneType().equals(ColonneType.Haut)) {
                        tabJLabel[x][y].setIcon(imgIcons.get("ColonneHaut"));
                    } else if (((Colonne) e).getColonneType().equals(ColonneType.Milieu)) {
                        tabJLabel[x][y].setIcon(imgIcons.get("ColonneMilieu"));
                    } else if (((Colonne) e).getColonneType().equals(ColonneType.Bas)) {
                        tabJLabel[x][y].setIcon(imgIcons.get("ColonneBas"));
                    }
                }
            }
        }
    }

    public void affichageVieEtScoreJoueur() {
        if (jeu.getHeros().getHerosLife() > 0) {
            tabJLabel[sizeX - 4][0].setIcon(imgIcons.get("Vie"));
        }
        if (jeu.getHeros().getHerosLife() > 1) {
            tabJLabel[sizeX - 3][0].setIcon(imgIcons.get("Vie"));
        }
        if (jeu.getHeros().getHerosLife() > 2) {
            tabJLabel[sizeX - 2][0].setIcon(imgIcons.get("Vie"));
        }
        switch (jeu.getScore()) {
            case 0:
                tabJLabel[2][0].setIcon(imgIcons.get("Zero"));
                break;
            case 1:
                tabJLabel[2][0].setIcon(imgIcons.get("Un"));
                break;
            case 2:
                tabJLabel[2][0].setIcon(imgIcons.get("Deux"));
                break;
            case 3:
                tabJLabel[2][0].setIcon(imgIcons.get("Trois"));
                break;
            case 4:
                tabJLabel[2][0].setIcon(imgIcons.get("Quatre"));
                break;
            case 5:
                tabJLabel[2][0].setIcon(imgIcons.get("Cinq"));
                break;
        }

        tabJLabel[3][0].setIcon(imgIcons.get("Zero"));
        tabJLabel[4][0].setIcon(imgIcons.get("Zero"));
    }

    public void affichageGameOver() {
            new endGame(false, jeu.getNiveau());
            jeu.setIsUpdate(true);
            this.dispose();
    }

    public void affichageGameWin() {
        new endGame(true, jeu.getNiveau());
        jeu.setIsUpdate(true);
        this.dispose();
    }


    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        affichageVieEtScoreJoueur();

        if(jeu.isGameWin())
            affichageGameWin();

        else if (jeu.isGameOver())
            affichageGameOver();
    }
}
