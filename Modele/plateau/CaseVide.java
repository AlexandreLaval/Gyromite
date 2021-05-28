package Modele.plateau;

public class CaseVide extends EntiteStatique {
    public CaseVide(Jeu _jeu) { super(_jeu); }

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
