package Modele.plateau;


import Modele.deplacements.Direction;

public abstract class EntiteDynamique extends Entite{

    public EntiteDynamique(Jeu _jeu){super(_jeu);}

    public boolean avancerDirectionChoisie(Direction direction){
        return jeu.deplacerEntite(this,direction);
    }
/*
    public Entite regarderDansLaDirection(Direction direction){

    }*/
}
