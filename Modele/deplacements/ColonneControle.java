package Modele.deplacements;

import Modele.plateau.EntiteDynamique;

public class ColonneControle extends RealisateurDeplacement {

    private Direction directionCourante;

    //Design pattern singleton
    private static ColonneControle colCont;
    private boolean enHaut = true;
    public static final int NB_DEPLACEMENT = 2;
    private int nbDeplacement = 0;

    //singleton
    public static ColonneControle getInstance() {
        if (colCont == null) {
            colCont = new ColonneControle();
        }
        return colCont;
    }

    @Override
    protected boolean realiserDeplacement() {
        boolean realiserDeplacement = false;
        if (directionCourante != null) {
            for (EntiteDynamique entite : lstEntitesDynamiques) {
                if(entite.avancerDirectionChoisie(directionCourante))
                {
                    realiserDeplacement = true;
                }
            }
        }
        return realiserDeplacement;
    }

    public void setDirectionCourante() {
        directionCourante = enHaut ? Direction.Bas : Direction.Haut;
    }

    public Direction getDirectionCourante() {
        return directionCourante;
    }

    public void resetDirection() {
        enHaut = !enHaut;
        directionCourante = null;
    }


}