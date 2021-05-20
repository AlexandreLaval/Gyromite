package Modele.plateau;

public class Bombe extends EntiteStatique{
    public Bombe(Jeu _jeu){ super(_jeu);}

    @Override
    public boolean traversable() {
        return false;
    }
}
