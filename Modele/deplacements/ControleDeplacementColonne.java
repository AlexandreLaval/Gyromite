package Modele.deplacements;

import Modele.plateau.EntiteDynamique;

public class ControleDeplacementColonne extends RealisateurDeplacement{

    /*----ATTRIBUTS-----*/

    private Direction directionCourante;
    private int nbDeplacementCourant  = 0;
    private static final int NB_MOUV_COL = 2; //les colonnes montent de deux cases
    private boolean estEnHaut = false; //position de la colonne

    //design pattern Singleton

    private static ControleDeplacementColonne contDepCol;

    /*----METHODES------*/
    @Override
    protected boolean isOkToRealiserDeplacement() {
        boolean isOkDeplacement = false;

        for(EntiteDynamique entite : lstEntitesDynamiques) {
            int nbColonne = lstEntitesDynamiques.size();

            if (directionCourante != null && nbDeplacementCourant < NB_MOUV_COL * nbColonne) {
                isOkDeplacement = entite.avancerDirectionChoisie(directionCourante);
                if (isOkDeplacement)
                    nbDeplacementCourant++;
            } else if (nbDeplacementCourant >= NB_MOUV_COL * nbColonne) {
                annulDirection();;
                nbDeplacementCourant = 0;
                estEnHaut = !estEnHaut;
            }
        }
        return isOkDeplacement;
    }

    public void annulDirection(){
        directionCourante = null;
    }

    public void setDirectionCourante(){
        directionCourante = estEnHaut ? Direction.Bas: Direction.Haut;
    }

    //Singleton
    public static ControleDeplacementColonne getInstace(){
        if(contDepCol == null)
            contDepCol = new ControleDeplacementColonne();

        return contDepCol;
    }

    public static ControleDeplacementColonne reset(){
        contDepCol = new ControleDeplacementColonne();
        return contDepCol;
    }
}
