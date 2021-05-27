package Modele.plateau;

public class Colonne extends EntiteDynamique{

    public Colonne(Jeu _jeu, int _x, int _y) { super(_jeu,_x,_y);}

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

    @Override
    public boolean traversable() {
        return false;
    }




}
