package Modele.plateau;

public class Bot extends EntiteDynamique{

    public Bot(Jeu _jeu, int _x, int _y){
        super(_jeu, _x,_y);

    }

    @Override
    public boolean peutEtreEcrase() {
        return true;
    }

    @Override
    public boolean peutServirDeSupport() {
        return false;
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
