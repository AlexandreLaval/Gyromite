package Modele.plateau;

import java.awt.*;
import java.util.ArrayList;

import Modele.deplacements.Controle4Directions;
import Modele.deplacements.Direction;
import Modele.deplacements.IA;

import javax.naming.ldap.Control;

public class ColonneEntiere extends EntiteDynamique {

    public ArrayList<Colonne> colonnes;

    public static final int NB_COLONNE = 3; //trois bouts de colonne par colonne entière

    public ColonneEntiere(Jeu _jeu) {
        super(_jeu);
        colonnes = new ArrayList<Colonne>();
    }

    @Override
    public boolean traversable() {
        return false;
    }

    @Override
    public boolean peutEtreEcrase() {
        return false;
    }

    @Override
    public boolean peutServirDeSupport() {
        return true;
    }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }

    //ajouter une colonne à colonne entière
    public void addCol(Colonne col) {
        colonnes.add(col);
    }

    //va déplacer la colonne entière
    @Override
    public boolean avancerDirectionChoisie(Direction dir) {
        boolean deplacerColonne = false;

        if (dir == Direction.Haut) {
            for (int i = 0; i < NB_COLONNE; i++) {
                Entite entiteAuDessus = colonnes.get(i).regarderDansLaDirection(dir);
                if (entiteAuDessus instanceof EntiteDynamique) {
                    if (((EntiteDynamique) entiteAuDessus).regarderDansLaDirection(dir).traversable()) {
                        jeu.deplacerEntite(entiteAuDessus, Direction.Haut);
                    } else {
                        jeu.ecraseEntite(colonnes.get(i),(EntiteDynamique) entiteAuDessus);
                    }
                }
                deplacerColonne = colonnes.get(i).avancerDirectionChoisie(dir);
            }
        } else if (dir == Direction.Bas) {
            for (int i = NB_COLONNE - 1; i >= 0; i--) {
                Entite entiteAuDessous = colonnes.get(i).regarderDansLaDirection(dir);
                if (entiteAuDessous instanceof EntiteDynamique) {
                    if (!((EntiteDynamique) entiteAuDessous).regarderDansLaDirection(dir).traversable()) {
                        jeu.ecraseEntite(colonnes.get(i),(EntiteDynamique) entiteAuDessous);
                    }
                }
                deplacerColonne = colonnes.get(i).avancerDirectionChoisie(dir);
            }
        }
        return deplacerColonne;

    }
}
