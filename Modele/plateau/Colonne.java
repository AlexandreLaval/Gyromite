package Modele.plateau;

import Modele.plateau.enums.ColonneType;

/**
 * Entite colonne composant une ColonneEntiere
 */
public class Colonne extends EntiteDynamique{
    ColonneType colType;

    public Colonne(Jeu _jeu, ColonneType colonneType) {
        super(_jeu);
        colType = colonneType;
        setCasePrecedente(new CaseVide(jeu));
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

    public ColonneType getColonneType(){
        return colType;
    }


}
