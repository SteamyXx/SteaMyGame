package effet.effetnocif.nocifmalusprofit;
import effet.effetnocif.nocifmalusprofit.NocifMalusProfit;
import personnage.*;

public class Ralentissement extends NocifMalusProfit {

  public Ralentissement(int n) {
    super(n);
    this.etiquette = "Slow";
  }

  public void effetPerso(Personnage p) {
    if (p.getVitesse() >= p.getVitesseMax()) {
      p.setVitesse(p.getVitesse()-p.getVitesseMax()*30/100);
    }
  }

  public void supprEffetPerso(Personnage p) {
    p.setVitesse(p.getVitesse()+p.getVitesseMax()*30/100);
  }

  public static void appliquerEffet(Personnage p) {
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof Ralentissement) {
        p.getListeEffet().get(i).effetPerso(p);
      }
    }
  }
}
