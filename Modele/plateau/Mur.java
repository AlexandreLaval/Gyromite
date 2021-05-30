package Modele.plateau;

/**
 * Entite Mur
 */
public class Mur extends EntiteStatique {
    public Mur(Jeu _jeu) { super(_jeu); }

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
