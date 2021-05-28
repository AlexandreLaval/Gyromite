package Modele.deplacements;
import Modele.plateau.EntiteDynamique;

import java.util.ArrayList;

public abstract class RealisateurDeplacement {

    protected ArrayList<EntiteDynamique> lstEntitesDynamiques = new ArrayList<>();

    protected abstract boolean realiserDeplacement();

    public void addEntiteDynamique(EntiteDynamique ed) {lstEntitesDynamiques.add(ed);};

    public void removeEntiteDynamique(EntiteDynamique ed) {lstEntitesDynamiques.remove(ed);};
}