package Modele.deplacements;

import Modele.plateau.Entite;
import Modele.plateau.EntiteDynamique;

public class Gravite extends RealisateurDeplacement {

    private static Gravite gravite;

    public static Gravite getInstance(){
        if(gravite == null){
            gravite = new Gravite();
        }
        return gravite;
    }

    public static void resetSingletion()
    {
        gravite = null;
    }

    @Override
    protected boolean realiserDeplacement() {
        boolean realiserDeplacement = false;
        for (EntiteDynamique entite : lstEntitesDynamiques) {
            Entite entiteObservee = entite.regarderDansLaDirection(Direction.Bas);
            entite.setFalling(false);
            if (entiteObservee != null && !entiteObservee.peutServirDeSupport() && !entite.getCasePrecedente().peutPermettreDeMonterDescendre()) {
                entite.setFalling(true);
                if (entite.avancerDirectionChoisie(Direction.Bas)) {
                    realiserDeplacement = true;
                }
            }
        }
        return realiserDeplacement;
    }
}