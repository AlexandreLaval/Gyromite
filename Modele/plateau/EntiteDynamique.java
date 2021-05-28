package Modele.plateau;


import Modele.deplacements.Direction;

public abstract class EntiteDynamique extends Entite{
    private int x;
    private int y;

    public EntiteDynamique(Jeu _jeu, int _x, int _y){
        super(_jeu);
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void droite() {
        if (traversable(x+1, y)) {
            x ++;
        }
    }

    public void gauche() {
        if (traversable(x-1, y)) {
            x --;
        }
    }

    public void bas() {
        if (traversable(x, y+1)) {
            y ++;
        }
    }

    public void haut() {
        if (traversable(x, y-1)) {
            y --;
        }
    }
    private boolean traversable(int x, int y) {

        if (x >0 && x < jeu.SIZE_X && y > 0 && y < jeu.SIZE_Y) {
            return jeu.getEntite(x, y).traversable();
        } else {
            return false;
        }
    }

    public boolean avancerDirectionChoisie(Direction direction){
        return jeu.isOkToDeplacerEntite(this,direction);
    }

   //public Entite regarderDansLaDirection(Direction d) {return Entite;}
}
