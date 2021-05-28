package Modele.plateau;

import Modele.deplacements.Controle4Directions;
import Modele.deplacements.Direction;
import Modele.deplacements.Ordonnanceur;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Jeu {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 15;


    String niveauReader;

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    private Heros heros;

    private HashMap<Entite, Point> carte = new HashMap<>();
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y];

    public Jeu() {
        chargerNiveau();
    }

    public Ordonnanceur getOrdonnanceur() { return ordonnanceur; }

    public Heros getHeros() {
        return heros;
    }

    public Entite[][] getGrille() {
        return grilleEntites;
    }

    public Entite casePrecedente = new CaseVide(this);

    public Entite getEntite(int x, int y) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
            // L'entité demandée est en-dehors de la grille
            return null;
        }
        return grilleEntites[x][y];
    }

    private boolean estDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
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
        addEntite(heros,4,4);
        Controle4Directions.getInstance().addEntiteDynamique(heros);
        getOrdonnanceur().addDep(Controle4Directions.getInstance());
    }

    public Entite regarderDansLaDirection(Entite entite, Direction direction) {
        Point positionEntite = carte.get(entite);// new Point(getHeros().getX(),getHeros().getY());

        Entite entiteRegardee = null;
        Point coordEntiteRegardee = new Point(0,0);
        switch (direction) {
            case Droite:
                coordEntiteRegardee.x = positionEntite.x + 1;
                coordEntiteRegardee.y = positionEntite.y;
                break;
            case Gauche:
                coordEntiteRegardee.x = positionEntite.x - 1;
                coordEntiteRegardee.y = positionEntite.y;
                break;
            case Haut:
                coordEntiteRegardee.x = positionEntite.x;
                coordEntiteRegardee.y = positionEntite.y - 1;
                break;
            case Bas:
                coordEntiteRegardee.x = positionEntite.x;
                coordEntiteRegardee.y = positionEntite.y + 1;
                break;
        }

        if (estDansGrille(coordEntiteRegardee)) {
            entiteRegardee = grilleEntites[coordEntiteRegardee.x][coordEntiteRegardee.y];
        }
        return entiteRegardee;
    }


    public boolean deplacerEntite(Entite entite, Direction direction) {
        int px = carte.get(entite).x;
        int py = carte.get(entite).y;

        boolean deplacementOK = false;

        if (entite instanceof Heros) {
            switch (direction) {
                case Droite:
                        setCasePrecedente(entite, px, py);
                        replaceEntite(entite, px + 1, py);
                        deplacementOK = true;
                    break;
                case Gauche:
                        setCasePrecedente(entite, px, py);
                        replaceEntite(entite, px - 1, py);
                        deplacementOK = true;

                    break;
                case Haut:
                        setCasePrecedente(entite, px, py);
                        replaceEntite(entite, px, py - 1);
                        deplacementOK = true;

                    break;
                case Bas:
                        setCasePrecedente(entite, px, py);
                        replaceEntite(entite, px, py + 1);
                        deplacementOK = true;
                    break;
            }
        }
        if (entite instanceof Colonne) {
            if (direction == Direction.Bas && py + 1 < SIZE_Y && grilleEntites[px][py - 1].traversable()) {
                setCasePrecedente(entite, px, py);
                replaceEntite(entite, px, py + 1);
                deplacementOK = true;
            } else if (direction == Direction.Haut && py - 1 < SIZE_Y && grilleEntites[px][py - 1].traversable()) {
                setCasePrecedente(entite, px, py);
                replaceEntite(entite, px, py - 1);
                deplacementOK = true;
            }

        }
        return deplacementOK;
    }

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        carte.put(e, new Point(x, y));
    }

    private void setCasePrecedente(Entite e, int x, int y) {
        carte.remove(e);
        grilleEntites[x][y] = casePrecedente;
        carte.put(casePrecedente, new Point(x, y));
    }

    private void replaceEntite(Entite e, int x, int y) {
        casePrecedente = grilleEntites[x][y];
        carte.remove(grilleEntites[x][y]);
        grilleEntites[x][y] = e;
        carte.put(e, new Point(x, y));
    }

    public Entite getCasePrecedente(){
        return casePrecedente;
    }
}
