package Modele.plateau;

public  class Bombe extends EntiteStatique{
    public Bombe(Jeu _jeu){ super(_jeu);}

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

    @Override
    public boolean traversable() {
        return false;
    }


}
