package Modele.plateau;

public class Colonne extends EntiteDynamique{

    public Colonne(Jeu _jeu) { super(_jeu);}

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
