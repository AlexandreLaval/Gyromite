/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.plateau;

import Modele.plateau.enums.EntiteDynamiqueState;

/**
 * Héros du jeu
 */
public class Heros extends EntiteDynamique{
    private int x;
    private int y;

    private int herosLife = 3;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosXY(int x, int y){
        this.x = x;
        this.y = y;
    }


    public Heros(Jeu _jeu, int _x, int _y, Entite casePrecedente) {
        super(_jeu);
        x = _x;
        y = _y;
        this.setCasePrecedente(casePrecedente);
        this.setEntiteDynamiqueState(EntiteDynamiqueState.Idle);
    }

    @Override
    public boolean traversable() {
        return true;
    }

    @Override
    public boolean peutEtreEcrase() { return true; }

    @Override
    public boolean peutServirDeSupport() {
        return false;
    }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }

    public int getHerosLife() {
        return herosLife;
    }

    public void setHerosLife(int herosLife) {
        this.herosLife = herosLife;
    }
}
