package Modele.plateau;
import java.util.ArrayList;
import Modele.deplacements.Direction;

public class ColonneEntiere extends EntiteDynamique{

    public ArrayList<Colonne> colonnes;

    public static final int NB_COLONNE = 3; //trois bouts de colonne par colonne entière

    public ColonneEntiere(Jeu _jeu){
        super(_jeu);
        colonnes = new ArrayList<Colonne>();
    }

    @Override
    public boolean traversable() {
        return false;
    }

    @Override
    public boolean peutEtreEcrase() {
        return false;
    }

    @Override
    public boolean peutServirDeSupport() {
        return true;
    }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }

    //ajouter une colonne à colonne entière
    public void addCol(Colonne col) {
        colonnes.add(col);
    }

    //va déplacer la colonne entière
    public void deplacerColonne(Direction direction){

        //faire colonne.vanacerDirectionChoisir avant
   }
}
