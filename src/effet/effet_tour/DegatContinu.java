package effet.effettour;
import effet.effettour.EffetTour;
import personnage.*;
import application.*;

public class DegatContinu extends EffetTour {

  public DegatContinu(int n) {
    super(n);
    this.etiquette = "Dg_Cnt";
  }

  public void effetPerso(Personnage p) {
    p.setPdv(p.getPdv()-(p.getPdvMax()*5/100));
    if (p.getPdv() < 0) {
      p.setPdv(0);
    }
    Combat.ajouterCommentaire("-Les dégats continus ont infligé "+p.getPdvMax()*5/100+" dégats à "+p.getClass().getName().substring(11));
  }

  public void supprEffetPerso(Personnage p) {

  }
}
