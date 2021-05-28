package Modele.plateau;

import Modele.deplacements.Direction;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 15;

    private int pause = 200; // période de rafraichissement

    String niveauReader;

    private Heros heros;

    private HashMap<Entite, Point> carte = new HashMap<>();
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y];

    public Jeu() {
        chargerNiveau();
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

    public void chargerNiveau() {
        niveauReader = "";
        try {
            String path = new File(".").getCanonicalPath();

            File carte = new File(path.replace("\\", "\\\\") + "\\Niveaux\\Niveau1.csv");
            FileReader fr = new FileReader(carte);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            niveauReader = sb.toString();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialisationDesEntites();
    }


    private void initialisationDesEntites() {

        for (int y = 0; y < Jeu.SIZE_Y; y++) {
            for (int x = 0; x < Jeu.SIZE_X; x++) {
                int n = niveauReader.charAt(y * Jeu.SIZE_X + x);
                switch (n) {
                    case 'W':
                        addEntite(new Mur(this), x, y);
                        break;
                    case 'E':
                        addEntite(new Bombe(this), x, y);
                        break;
                    case 'V':
                        addEntite(new CaseVide(this), x, y);
                        break;
                    case 'C':
                        addEntite(new Corde(this), x, y);
                        break;
                    case 'N':
                        addEntite(new Navet(this), x, y);
                        break;
                    case 'P':
                        addEntite(new PoutreHorizontal(this), x, y);
                        break;
                    case 'H':
                        addEntite(new Colonne(this, ColonneType.Haut), x, y);
                        break;
                    case 'M':
                        addEntite(new Colonne(this, ColonneType.Milieu), x, y);
                        break;
                    case 'B':
                        addEntite(new Colonne(this, ColonneType.Bas), x, y);
                        break;
                }
            }
        }
        heros = new Heros(this, 4, 4);
    }

    public boolean deplacerEntite(Entite entite, Direction direction) {
        int px = carte.get(entite).x;
        int py = carte.get(entite).y;

        boolean deplacementOK = false;

        if (entite instanceof Heros) {
            switch (direction) {
                case Droite:
                    if (px + 1 < SIZE_X && grilleEntites[px+1][py].traversable()) {
                        setCaseVide(entite, px, py);
                        replaceEntite(entite, px+1, py );
                        deplacementOK = true;
                    }
                    break;
                case Gauche:
                    if (px - 1 < SIZE_X && grilleEntites[px-1][py].traversable()) {
                        setCaseVide(entite, px, py);
                        replaceEntite(entite, px+1, py );
                        deplacementOK = true;
                    }
                    break;
                case Haut:
                    if (py - 1 < SIZE_Y && grilleEntites[px][py - 1].traversable()) {
                        setCaseVide(entite, px, py);
                        replaceEntite(entite, px, py - 1);
                        deplacementOK = true;
                    }
                    break;
                case Bas:
                    if (py + 1 < SIZE_Y && grilleEntites[px][py + 1].traversable()) {
                        setCaseVide(entite, px, py);
                        replaceEntite(entite, px, py + 1);
                        deplacementOK = true;
                    }
                    break;
            }
        }
        if(entite instanceof Colonne){
            if(direction == Direction.Bas && py + 1 < SIZE_Y && grilleEntites[px][py - 1].traversable()){
                setCaseVide(entite, px, py);
                replaceEntite(entite, px, py + 1);
                deplacementOK = true;
            }
            else if(direction == Direction.Haut && py - 1 < SIZE_Y && grilleEntites[px][py - 1].traversable()){
                setCaseVide(entite, px, py);
                replaceEntite(entite, px, py - 1);
                deplacementOK = true;
            }

        }
        return deplacementOK;

    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {

        while (true) {

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
        carte.put(e, new Point(x, y));
    }

    private void setCaseVide(Entite e, int x, int y) {
        carte.remove(e);
        grilleEntites[x][y] = new CaseVide(this);
        carte.put(new CaseVide(this), new Point(x, y));
    }

    private void replaceEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        carte.put(e, new Point(x, y));
    }

}
