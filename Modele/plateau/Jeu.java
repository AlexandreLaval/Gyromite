package Modele.plateau;

import Modele.deplacements.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class Jeu  extends Observable implements Runnable {

    /*------ATTRIBUTS--------*/

    //taille de la map
    public static final int SIZE_X = 40;
    public static final int SIZE_Y = 20;

    private int pause = 200; // période de rafraichissement

    private Heros heros;

    //carte du plateau
    private HashMap<Entite, Point> carte = new HashMap<>();

    //tableau contenant les entites presentent sur le plateau
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y];

    /*------METHODES--------*/

    public Jeu() {
        initialisationDesEntites();
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {

        while(true) {

            setChanged();
            notifyObservers();

            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void initialisationDesEntites() {
        heros = new Heros(this, 4, 4);

        // murs extérieurs horizontaux
        for (int x = 0; x < 16; x++) {
            addEntite(new Mur(this), x, 0);
            addEntite(new Mur(this), x, 9);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < 9; y++) {
            addEntite(new Mur(this), 0, y);
            addEntite(new Mur(this), 19, y);
            addEntite(new Mur(this), 0, y);
            addEntite(new Mur(this), 19, y);
        }

        addEntite(new Mur(this), 2, 6);
        addEntite(new Mur(this), 3, 6);

        //colonnes

        ArrayList<Colonne> colonnes = new ArrayList<>();
        colonnes.add(new Colonne(this,10,17));
        colonnes.add(new Colonne(this,10,18));
        colonnes.add(new Colonne(this,10,19));

        for (Colonne col:colonnes) {
            addColonne(col);
        }
        ColonneEntiere colonneEntiere1 = new ColonneEntiere(this);

        int index = 0;
        colonneEntiere1.addCol(colonnes.get(index));
        colonneEntiere1.addCol(colonnes.get(index+1));
        colonneEntiere1.addCol(colonnes.get(index+2));

        index += 3;

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntites[x][y] == null) {
                    grilleEntites[x][y] = new CaseVide(this);
                }
            }
        }
    }

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
    }

    private void addColonne(Colonne col){
        grilleEntites[col.getX()][col.getY()] = col;
    }

    //va vérifier si nous pouvons déplacer l'entité, si c'est le cas on déplace
    public boolean isOkToDeplacerEntite(Entite entite, Direction direction){
        boolean deplacementOK = false;
        Point cible = new Point(0,0);

        if(entite instanceof  ColonneEntiere){
            cible.x = carte.get(entite).x;
            cible.y = carte.get(entite).y+1;

            if(grilleEntites[cible.x][cible.y] instanceof CaseVide)
                deplacementOK = true;
        }
        deplacerEntite(entite, cible);
        return deplacementOK;

    }

    //déplacement de l'entité
    public void deplacerEntite(Entite entite, Point cible){

        grilleEntites[carte.get(entite).x][carte.get(entite).y] = null;
        grilleEntites[cible.x][cible.y] = entite;
        carte.put(entite, cible);

    }


    public Heros getHeros() {
        return heros;
    }

    public Entite[][] getGrille() {
        return grilleEntites;
    }

    public Entite getEntite(int x, int y) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
            // L'entité demandée est en-dehors de la grille
            return null;
        }
        return grilleEntites[x][y];
    }







}
