package Modele.deplacements;

import Modele.plateau.Jeu;

import java.util.ArrayList;
import java.util.Observable;

public class Ordonnanceur extends Observable implements Runnable {

    private int pause = 300; // période de rafraichissement
    private Jeu jeu;
    private static ArrayList<RealisateurDeplacement> lstRealDeplacement = new ArrayList<>();

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
            for (RealisateurDeplacement deplacement : lstRealDeplacement) {
                deplacement.realiserDeplacement();
            }
            Controle4Directions.getInstance().resetControle4Directions();
            ColonneControle.getInstance().resetDirection();
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
