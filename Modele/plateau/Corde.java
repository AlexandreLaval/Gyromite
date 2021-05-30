package Modele.plateau;

/**
 * Entite corde, permet de monter ou de descendre
 */
public class Corde extends EntiteStatique{

    public Corde(Jeu _jeu){super(_jeu);}

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
        return true;
    }
}
