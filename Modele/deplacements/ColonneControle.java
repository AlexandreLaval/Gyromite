package Modele.deplacements;

import Modele.plateau.EntiteDynamique;

public class ColonneControle extends RealisateurDeplacement {

    private Direction directionCourante;

    //Design pattern singleton
    private static ColonneControle colCont;
    private boolean enHaut = true;
    private static final int NB_DEPLACEMENT = 2;
    private int nbDeplacement = 0;

    //singleton
    public static ColonneControle getInstance() {
        if (colCont == null) {
            colCont = new ColonneControle();
        }
        return colCont;
    }

    public static ColonneControle reset() {
        colCont = new ColonneControle();
        return colCont;
    }

    @Override
    protected boolean realiserDeplacement() {
        boolean realiserDeplacement = false;
        if (directionCourante != null) {
            for (EntiteDynamique entite : lstEntitesDynamiques) {
                int nbColonne = lstEntitesDynamiques.size();
                if (directionCourante != null && nbDeplacement < NB_DEPLACEMENT * nbColonne) {
                    if(entite.avancerDirectionChoisie(directionCourante));
                    {
                        realiserDeplacement = true;
                        nbDeplacement++;
                       // this.realiserDeplacement();
                    }
                } else if (nbDeplacement >= NB_DEPLACEMENT * nbColonne) {
                    enHaut = !enHaut;
                    nbDeplacement = 0;
                }
            }
        }
        return realiserDeplacement;
    }

    public void setDirectionCourante() {
        directionCourante = enHaut ? Direction.Bas : Direction.Haut;
    }

    public void resetDirection() {
        directionCourante = null;
    }


}