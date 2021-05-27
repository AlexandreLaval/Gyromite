package Modele.plateau;
import java.util.ArrayList;
import Modele.deplacements.Direction;

public class ColonneEntiere extends Entite{

    public ArrayList<Colonne> colonnes;

    public static final int NB_COLONNE = 3; //trois bouts de colonne par colonne entière

    public ColonneEntiere(Jeu _jeu){
        super(_jeu);
        colonnes = new ArrayList<Colonne>();
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

    @Override
    public boolean traversable() {
        return false;
    }

    //ajouter une colonne à colonne entière
    public void addCol(Colonne col) {
        colonnes.add(col);
    }

    //va déplacer la colonne entière
   public void deplacerColonne(Direction direction){

        //faire colonne avant AvancerDirectionChoisi
   }

   //va vérifier si on peut déplacer la colonne entière vers le haut ou vers le bas (vérification si quelque chose bloque)
   public boolean isOkToDeplace(){


        return true;
   }


}
