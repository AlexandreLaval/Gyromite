package Modele.deplacements;

import Modele.plateau.Entite;
import Modele.plateau.EntiteDynamique;

public class Controle4Directions extends RealisateurDeplacement{

    private static Direction lastDirection;
    private static Direction directionCourante;
    //design pattern Singleton
    private static Controle4Directions cont4Dir;

    public boolean isOkToRealiserDeplacement(){
        boolean isOkRealiserDep = false;
        for(EntiteDynamique entite : lstEntitesDynamiques){
            if(directionCourante != null){
                switch (directionCourante){
                    case Gauche, Droite, Q,D:
                        lastDirection = directionCourante;
                        if(entite.avancerDirectionChoisie(directionCourante))
                            isOkRealiserDep = true;
                        break;
                    case Haut, Z:
                        //pour monter ou descendre (corde par exemple)
                        Entite entiteBas = entite.regarderDansLaDirection(Direction.Bas);
                        Entite entiteHaut = entite.regarderDansLaDirection(Direction.Haut);

                        if(entiteBas!= null && (entiteBas.peutServirDeSupport() || entiteHaut.peutPermettreDeMonterDescendre())){
                            if(entite.avancerDirectionChoisie(Direction.Haut))
                                isOkRealiserDep = true;
                        }
                        break;
                }
            }
        }
        return isOkRealiserDep;
    }

    public void resetDirection(){
        directionCourante = null;
    }

    public static Direction getLastDirection(){
        return lastDirection;
    }

    public void setDirectionCourante(Direction dir){
        directionCourante = dir;
    }

    //Singleton

    public static Controle4Directions reset(){
        cont4Dir = new Controle4Directions();
        return cont4Dir;
    }

    public static Controle4Directions getInstance(){
        if(cont4Dir == null)
            cont4Dir = new Controle4Directions();
        return cont4Dir;
    }

}
