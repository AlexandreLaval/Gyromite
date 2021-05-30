package Modele.plateau;

/**
 * Entite Bombe est un collectable
 */
public class Bombe extends EntiteStatique{
    public Bombe(Jeu _jeu){ super(_jeu);}

    @Override
    public boolean traversable() {
        return true;
    }

    @Override
    public boolean peutEtreEcrase() {
        return false;
    }

    @Override
    public boolean peutServirDeSupport() {
        return false;
    }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }
}
