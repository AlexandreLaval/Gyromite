package Modele.plateau;

import Modele.deplacements.Direction;

import java.awt.*;
import java.util.HashMap;
import java.util.Observable;

public class Jeu  extends Observable implements Runnable {

    public static final int SIZE_X = 40;
    public static final int SIZE_Y = 20;

    private int pause = 200; // période de rafraichissement

    private Heros heros;

    private HashMap<Entite, Point> carte = new HashMap<>();
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y];

    public Jeu() {
        initialisationDesEntites();
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
        }

        addEntite(new Mur(this), 2, 6);
        addEntite(new Mur(this), 3, 6);

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntites[x][y] == null) {
                    grilleEntites[x][y] = new CaseVide(this);
                }
            }
        }
    }

    public boolean deplacerEntite(Entite entite, Direction direction){
        int px = carte.get(entite).x;
        int py = carte.get(entite).y;
       // grilleEntitesStatiques[px][py];
        return false;
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

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
    }

}
