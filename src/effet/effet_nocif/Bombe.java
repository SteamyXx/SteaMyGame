package effet.effetbenefique.benefiquemalus;
import effet.effetbenefique.benefiquemalus.BenefiqueMalus;
import effet.effetnocif.nocifmalus.Etourdissement;
import personnage.Personnage;
import application.*;

public class Bombe extends BenefiqueMalus {
  private int degats;

  public Bombe(int n, int degats) {
    super(n);
    this.etiquette = "Bomb";
    this.degats = degats;
  }

  public void effetPerso(Personnage p) {

  }

  public void supprEffetPerso(Personnage p) {

  }

  public void effetBombe(Personnage p2) {
    p2.setPdv(p2.getPdv()-degats);
    if (p2.getPdv() < 0) {
      p2.setPdv(0);
    }
    p2.appliquerEffet(new Etourdissement(1), 35);
    Combat.ajouterCommentaire("-"+p2.getClass().getName().substring(11)+" à subit "+degats+" dégats de la bombe");
  }
}
