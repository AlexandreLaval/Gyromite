package Modele.plateau;

public class Navet extends EntiteStatique {

    public Navet(Jeu _jeu){super(_jeu);}

    @Override
    public boolean traversable() {
        return true;
    }


}
