package Modele.deplacements;

import Modele.plateau.Entite;
import Modele.plateau.EntiteDynamique;

public class Controle4Directions extends RealisateurDeplacement {
    private static Direction directionCourante;

    //design pattern Singleton
    private static Controle4Directions cont4Dir;

    public static Controle4Directions getInstance() {
        if (cont4Dir == null) {
            cont4Dir = new Controle4Directions();
        }
        return cont4Dir;
    }

    public static void resetSingletion() {
        cont4Dir = null;
    }

    public void setDirectionCourante(Direction direction) {
        directionCourante = direction;
    }

    @Override
    protected boolean realiserDeplacement() {
        boolean realiserDeplacement = false;
        if (directionCourante != null) {
            for (EntiteDynamique entite : lstEntitesDynamiques) {
                //pour gerer l'affichage
                entite.setDirectionCourante(directionCourante);
                // On récupère l'entite qui est stocké sur la case que l'on regarde
                Entite entiteObservee = entite.regarderDansLaDirection(directionCourante);

                if (entiteObservee != null && entiteObservee.traversable()) {
                    switch (directionCourante) {
                        case Haut, Bas:
                            //Si l'entiteObserve est une case vide et que l'on est sur une corde on peut se laisser tomber
                            if (entiteObservee.peutPermettreDeMonterDescendre() ||
                                    (directionCourante.equals(Direction.Bas) && entiteObservee.traversable())) {
                                if (entite.avancerDirectionChoisie(directionCourante)) {
                                    realiserDeplacement = true;
                                }
                            }
                            break;
                        case Droite, Gauche:
                            if (entite.avancerDirectionChoisie(directionCourante)) {
                                realiserDeplacement = true;
                            }
                            break;
                    }
                }
            }
        }
        return realiserDeplacement;
    }

    public void resetControle4Directions() {
        directionCourante = null;
    }
}
