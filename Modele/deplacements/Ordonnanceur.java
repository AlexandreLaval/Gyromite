package Modele.deplacements;

import Modele.plateau.Jeu;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Ordonnanceur déclenche les instances de RealisateurDeplacements de manière séquentielle
 */
public class Ordonnanceur extends Observable implements Runnable {

    private int pause = 250; // période de rafraichissement
    private Jeu jeu;
    private static ArrayList<RealisateurDeplacement> lstRealDeplacement = new ArrayList<>();
    private int compteurColMouv = 0;

    public Ordonnanceur(Jeu _jeu) {
        jeu = _jeu;
    }

    public void addDep(RealisateurDeplacement realDep) {
        lstRealDeplacement.add(realDep);
    }

    public void removeDep(RealisateurDeplacement realDep) {
        lstRealDeplacement.remove(realDep);
    }

    public static void clearDep() {
        lstRealDeplacement.clear();
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!jeu.getIsUpdate()) {
            jeu.checkIsWin();
            setChanged();
            notifyObservers();
            //Faire descendre les colonnes par tick de temps
            if(ColonneControle.getInstance().getDirectionCourante() != null && compteurColMouv >= ColonneControle.NB_DEPLACEMENT) {
                compteurColMouv = 0;
                ColonneControle.getInstance().resetDirection();
            }else if(ColonneControle.getInstance().getDirectionCourante() != null ){
                compteurColMouv++;
            }

            for (RealisateurDeplacement deplacement : lstRealDeplacement) {
                deplacement.realiserDeplacement();
            }
            Controle4Directions.getInstance().resetControle4Directions();

            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Permet de reset nos singletons après la réussite d'un niveau
        Controle4Directions.resetSingletion();
        IA.resetSingletion();
        Gravite.resetSingletion();
        ColonneControle.resetSingletion();
    }
}
