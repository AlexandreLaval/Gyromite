package Modele.plateau;

public abstract class Entite {

    protected Jeu jeu;

    public Entite(Jeu _jeu) { jeu = _jeu;}

    public abstract boolean traversable();
    public abstract boolean peutEtreEcrase();
    public abstract boolean peutServirDeSupport();
    public abstract boolean peutPermettreDeMonterDescendre();

    public boolean checkSiEntiteDessousPeutServirDeSupport(){
        return jeu.checkSiEntiteDessousPeutServirDeSupport(this);
    }
}