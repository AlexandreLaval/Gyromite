package Modele.deplacements;

import Modele.plateau.Entite;
import Modele.plateau.EntiteDynamique;

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


    @Override
    protected boolean realiserDeplacement() {
        boolean realiserDeplacement = false;
        for (EntiteDynamique entite : lstEntitesDynamiques) {
            Direction directionCourante = entite.getDirectionCourante();
            Entite entiteObservee = entite.regarderDansLaDirection(directionCourante);
            if (entiteObservee != null && entiteObservee.checkSiEntiteDessousPeutServirDeSupport()) {
                if (entiteObservee.traversable()) {
                    switch (directionCourante) {
                        case Haut, Bas:
                            if (entiteObservee.traversable()) {
                                if (entiteObservee.peutPermettreDeMonterDescendre()) {
                                    if (entite.avancerDirectionChoisie(directionCourante)) {
                                        realiserDeplacement = true;
                                    }
                                }
                            }
                            break;
                        case Droite, Gauche:
                            if (entiteObservee.traversable()) {
                                if (entite.avancerDirectionChoisie(directionCourante)) {
                                    realiserDeplacement = true;
                                }
                            }
                            break;
                    }
                } else {
                    changeDirection(entite);
                }
            } else {
                changeDirection(entite);
            }
        }

        return realiserDeplacement;
    }

    public void changeDirection(EntiteDynamique entiteDynamique) {
        Direction temp = randomDirection();
        if (temp != entiteDynamique.getDirectionCourante()) {
            entiteDynamique.setDirectionCourante(temp);
        } else {
            changeDirection(entiteDynamique);
        }
    }
}
