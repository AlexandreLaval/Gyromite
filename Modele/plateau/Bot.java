package Modele.plateau;

public class Bot extends EntiteDynamique{

    public Bot(Jeu _jeu){
        super(_jeu);

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
