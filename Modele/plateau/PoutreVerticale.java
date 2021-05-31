package Modele.plateau;

/**
 * Entite Poutre verticale
 */
public class PoutreVerticale extends EntiteStatique{

    public PoutreVerticale(Jeu _jeu){super(_jeu);}

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
}
