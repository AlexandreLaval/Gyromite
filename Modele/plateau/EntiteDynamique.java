package Modele.plateau;


import Modele.deplacements.Direction;

public abstract class EntiteDynamique extends Entite {

    private Direction faceDirection = Direction.Droite;

    private Entite casePrecedente;

    private Direction directionCourante;

    private boolean isFalling = false;

    public EntiteDynamique(Jeu _jeu) {
        super(_jeu);
    }

    public Direction getDirectionCourante() {
        return this.directionCourante;
    }

    public void setDirectionCourante(Direction directionCourante) {
        this.directionCourante = directionCourante;

        //Pour gerer l'affichage coté vue
        if (directionCourante == Direction.Droite) {
            faceDirection = Direction.Droite;
        } else if (directionCourante == Direction.Gauche) {
            faceDirection = Direction.Gauche;
        }
    }

    public Entite getCasePrecedente() {
        return casePrecedente;
    }

    public void setCasePrecedente(Entite entite) {
        casePrecedente = entite;
    }

    public Direction getFaceDirection() {
        return faceDirection;
    }

    public boolean avancerDirectionChoisie(Direction direction) {
        return jeu.deplacerEntite(this, direction);
    }

    public Entite regarderDansLaDirection(Direction d) {
        return jeu.regarderDansLaDirection(this, d);
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}
