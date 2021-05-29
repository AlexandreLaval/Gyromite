package Modele.deplacements;

import Modele.plateau.Entite;
import Modele.plateau.EntiteDynamique;
import Modele.plateau.Heros;

import java.util.Random;

public class IA extends RealisateurDeplacement {

    private static IA ia;

    public static IA getInstance() {
        if (ia == null) {
            ia = new IA();
        }
        return ia;
    }

    public static Direction randomDirection() {
        int rand = new Random().nextInt(4);
        switch (rand) {
            case 1:
                return Direction.Haut;
            case 2:
                return Direction.Bas;
            case 3:
                return Direction.Gauche;
            default:
                return Direction.Droite;
        }
    }

    public void changeDirection(EntiteDynamique entiteDynamique) {
        Direction temp = randomDirection();
        if (temp != entiteDynamique.getDirectionCourante()) {
            entiteDynamique.setDirectionCourante(temp);
        } else {
            changeDirection(entiteDynamique);
        }
    }

    @Override
    protected boolean realiserDeplacement() {
        boolean realiserDeplacement = false;
        for (EntiteDynamique entite : lstEntitesDynamiques) {
            Direction directionCourante = entite.getDirectionCourante();
            Entite entiteObservee = entite.regarderDansLaDirection(directionCourante);
            if (entiteObservee != null && entiteObservee.traversable()) {
                switch (directionCourante) {
                    case Haut, Bas:
                        if (entiteObservee.peutPermettreDeMonterDescendre()) {
                            if (entite.avancerDirectionChoisie(directionCourante)) {
                                realiserDeplacement = true;
                            }
                        }
                        else {
                            changeDirection(entite);
                        }
                        break;
                    case Droite, Gauche:
                        if (entiteObservee.checkSiEntiteDessousPeutServirDeSupport()) {
                            if (entite.avancerDirectionChoisie(directionCourante)) {
                                realiserDeplacement = true;
                            }
                        }else if (entiteObservee.peutPermettreDeMonterDescendre()) {
                            if (entite.avancerDirectionChoisie(directionCourante)) {
                                realiserDeplacement = true;
                            }
                        }
                        else {
                            changeDirection(entite);
                        }
                        break;
                }
            } else {
                changeDirection(entite);
            }
        }
        return realiserDeplacement;
    }
}
