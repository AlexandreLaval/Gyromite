package Modele.plateau;

import Modele.plateau.enums.SupportColonneType;

/**
 * Entite SupportColonne
 */
public class SupportColonne extends EntiteStatique{

    private final SupportColonneType supportColonneType;

    public SupportColonne(Jeu _jeu,SupportColonneType supportColonneType) {
        super(_jeu);
        this.supportColonneType = supportColonneType;
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

    public SupportColonneType getSupportColonneType() {
        return supportColonneType;
    }
}
