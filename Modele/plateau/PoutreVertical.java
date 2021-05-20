package Modele.plateau;

public class PoutreVertical extends EntiteStatique{

    public PoutreVertical(Jeu _jeu){super(_jeu);}

    @Override
    public boolean traversable() {
        return false;
    }
}
