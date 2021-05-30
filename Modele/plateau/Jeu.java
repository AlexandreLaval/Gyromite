package Modele.plateau;

import Modele.deplacements.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Jeu {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 15;

    String niveauReader;

    private final Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    private Heros heros;
    private Point orgPos;

    private HashMap<Entite, Point> carte = new HashMap<>();
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y];

    private boolean isGameOver = false;
    private boolean isGameWin = false;

    private int score;

    private ArrayList<ColonneEntiere> lstColEntiere = new ArrayList<>();

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }

    public Heros getHeros() {
        return heros;
    }

    private int compteurBombe = 0;

    public Entite[][] getGrille() {
        return grilleEntites;
    }

    public Jeu() {
        chargerNiveau();
    }


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
                    case 'A':
                        heros = new Heros(this, x, y, new CaseVide(this));
                        addEntite(heros, x, y);
                        orgPos = new Point(x, y);
                        Controle4Directions.getInstance().addEntiteDynamique(heros);
                        Gravite.getInstance().addEntiteDynamique(heros);
                        break;
                    case 'S':
                        Smick smick = new Smick(this, Direction.Droite, new CaseVide(this));
                        addEntite(smick, x, y);
                        Gravite.getInstance().addEntiteDynamique(smick);
                        IA.getInstance().addEntiteDynamique(smick);
                        break;
                    case 'W':
                        addEntite(new Mur(this), x, y);
                        break;
                    case 'E':
                        addEntite(new Bombe(this), x, y);
                        compteurBombe++;
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
                        Colonne colH = new Colonne(this, ColonneType.Haut);
                        addEntite(colH, x, y);
                        ColonneEntiere colEntiere = new ColonneEntiere(this);
                        colEntiere.addCol(colH);
                        lstColEntiere.add(colEntiere);
                        break;
                    case 'M':
                        Colonne colM = new Colonne(this, ColonneType.Milieu);
                        addEntite(colM, x, y);

                        for (int i = 0; i < lstColEntiere.size(); i++) {
                            if (lstColEntiere.get(i).colonnes.size() == 1)
                                lstColEntiere.get(i).addCol(colM);
                        }

                        break;
                    case 'B':
                        Colonne colB = new Colonne(this, ColonneType.Bas);
                        addEntite(colB, x, y);

                        for (int i = 0; i < lstColEntiere.size(); i++) {
                            if (lstColEntiere.get(i).colonnes.size() == 2) {
                                lstColEntiere.get(i).addCol(colB);
                                ColonneControle.getInstance().addEntiteDynamique(lstColEntiere.get(i));
                            }
                        }
                        break;
                }

            }
        }
        getOrdonnanceur().addDep(Controle4Directions.getInstance());
        getOrdonnanceur().addDep(ColonneControle.getInstance());
        getOrdonnanceur().addDep(Gravite.getInstance());
        getOrdonnanceur().addDep(IA.getInstance());
    }

    public Entite regarderDansLaDirection(Entite entite, Direction direction) {
        Point positionEntite = carte.get(entite);

        Entite entiteRegardee = null;
        Point coordEntiteRegardee = new Point(0, 0);
        if (positionEntite != null) {
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
        }

        return entiteRegardee;
    }

    public boolean checkSiEntiteDessousPeutServirDeSupport(Entite entite) {
        boolean isEntitePlateforme = false;

        Point coordEntiteRegardee = new Point(carte.get(entite).x, carte.get(entite).y + 1);
        if (estDansGrille(coordEntiteRegardee)) {
            isEntitePlateforme = grilleEntites[coordEntiteRegardee.x][coordEntiteRegardee.y].peutServirDeSupport() ||
                    grilleEntites[coordEntiteRegardee.x][coordEntiteRegardee.y].peutPermettreDeMonterDescendre();
        }
        return isEntitePlateforme;
    }


    public boolean deplacerEntite(Entite entite, Direction direction) {
        int px = carte.get(entite).x;
        int py = carte.get(entite).y;

        boolean deplacementOK = false;
        if (entite instanceof EntiteDynamique) {
            EntiteDynamique eD = (EntiteDynamique) entite;
            switch (direction) {
                case Droite:
                    remetCasePrecedente(eD, px, py);
                    replaceEntite(eD, px + 1, py);
                    deplacementOK = true;
                    break;
                case Gauche:
                    remetCasePrecedente(eD, px, py);
                    replaceEntite(eD, px - 1, py);
                    deplacementOK = true;

                    break;
                case Haut:
                    remetCasePrecedente(eD, px, py);
                    replaceEntite(eD, px, py - 1);
                    deplacementOK = true;
                    break;
                case Bas:
                    remetCasePrecedente(eD, px, py);
                    replaceEntite(eD, px, py + 1);
                    deplacementOK = true;
                    break;
            }
        }
        return deplacementOK;
    }

    public void ecraseEntite(EntiteDynamique colonne, EntiteDynamique entiteEcrasee) {
        if (entiteEcrasee instanceof Heros) {
            colonne.setCasePrecedente(new CaseVide(this));
            grilleEntites[heros.getX()][heros.getY()] = heros.getCasePrecedente();
            playerLooseLife();
        } else if (entiteEcrasee instanceof Smick) {
            remetCasePrecedente(entiteEcrasee, carte.get(entiteEcrasee).x, carte.get(entiteEcrasee).y);
            IA.getInstance().removeEntiteDynamique(entiteEcrasee);
            Gravite.getInstance().removeEntiteDynamique(entiteEcrasee);
        }
    }

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        carte.put(e, new Point(x, y));
    }

    private void remetCasePrecedente(EntiteDynamique e, int x, int y) {
        carte.remove(e);
        grilleEntites[x][y] = e.getCasePrecedente();
        carte.put(e.getCasePrecedente(), new Point(x, y));
    }

    private void replaceEntite(EntiteDynamique e, int x, int y) {
        if (e instanceof Smick) {
            if (grilleEntites[x][y] instanceof Heros) {
                e.setCasePrecedente(((Heros) grilleEntites[x][y]).getCasePrecedente());
                playerLooseLife();
                grilleEntites[x][y] = e;
                carte.put(e, new Point(x, y));
            } else {
                e.setCasePrecedente(grilleEntites[x][y]);
                carte.remove(grilleEntites[x][y]);
                grilleEntites[x][y] = e;
                carte.put(e, new Point(x, y));
            }
        }
        if (e instanceof Heros) {
            if (grilleEntites[x][y] instanceof Smick) {
                e.setCasePrecedente(((Smick) grilleEntites[x][y]).getCasePrecedente());
                playerLooseLife();
            } else {
                if (grilleEntites[x][y] instanceof Bombe) {
                    e.setCasePrecedente(new CaseVide(this));
                    this.setScore(this.getScore() + 1);
                    this.compteurBombe--;
                } else {
                    e.setCasePrecedente(grilleEntites[x][y]);
                }
                carte.remove(grilleEntites[x][y]);
                grilleEntites[x][y] = e;
                carte.put(e, new Point(x, y));
            }
        }
        if (e instanceof Colonne) {
            e.setCasePrecedente(grilleEntites[x][y]);
            this.carte.remove(grilleEntites[x][y]);
            this.grilleEntites[x][y] = e;
            this.carte.put(e, new Point(x, y));
        }
    }

    public void playerLooseLife() {
        Controle4Directions.getInstance().resetControle4Directions();
        this.heros.setHerosLife(this.heros.getHerosLife() - 1);
        if (this.heros.getHerosLife() <= 0) {
            isGameOver = true;
        }
        remetCasePrecedente( this.heros,  this.carte.get(heros).x,  this.carte.get(heros).y);
        this.carte.put( this.heros, orgPos);
        this.grilleEntites[orgPos.x][orgPos.y] =  this.heros;
        this.heros.setCasePrecedente(new CaseVide(this));
        this.heros.setDirectionCourante(Direction.Droite);
    }

    public void checkIsWin() {
        if (this.compteurBombe <= 0) {
            isGameWin = true;
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isGameWin() {
        return isGameWin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
