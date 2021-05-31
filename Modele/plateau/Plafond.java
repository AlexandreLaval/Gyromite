package Modele.plateau;

/**
 * Entite Plafond, représente la couche supérieur d'un niveau
 */
public class Plafond extends EntiteStatique{
    public Plafond(Jeu _jeu) {
        super(_jeu);
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
        return false;
    }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }
}
