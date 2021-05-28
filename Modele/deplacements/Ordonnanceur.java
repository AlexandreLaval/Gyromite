package Modele.deplacements;

import java.util.ArrayList;
import java.util.Observable;

import static java.lang.Thread.*;

import Modele.plateau.Jeu;

public class Ordonnanceur extends Observable implements  Runnable {

    private Jeu jeu;
    private ArrayList<RealisateurDeplacement> lstRealDeplacement = new ArrayList<>();

    public Ordonnanceur(Jeu _jeu){
        jeu = _jeu;
    }

    public void add(RealisateurDeplacement realDep){
        lstRealDeplacement.add(realDep);
    }

    public void remove(RealisateurDeplacement realDep){
        lstRealDeplacement.remove(realDep);
    }

    public static void clear(){
        lstRealDeplacement.clear();
    }

    public void start(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean isUpdate = false;

    }
}
