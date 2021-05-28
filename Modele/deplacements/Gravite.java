package Modele.deplacements;

import Modele.plateau.Entite;
import Modele.plateau.EntiteDynamique;

public  class Gravite extends RealisateurDeplacement{

    @Override
    protected boolean isOkToRealiserDeplacement() {
        return false;
    }
}
