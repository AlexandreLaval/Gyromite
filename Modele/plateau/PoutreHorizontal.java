package Modele.plateau;

public class PoutreHorizontal extends EntiteStatique{

    public PoutreHorizontal(Jeu _jeu){super(_jeu);}

    @Override
    public boolean traversable() {
        return false;
    }
}
