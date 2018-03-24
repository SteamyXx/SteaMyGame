package effet.effetbenefique.benefiqueprofit;
import effet.effetbenefique.EffetBenefique;
import personnage.*;

public abstract class BenefiqueProfit extends EffetBenefique {

  public BenefiqueProfit(int n) {
    super(n);
  }

  public abstract void effetPerso(Personnage p);
  public abstract void supprEffetPerso(Personnage p);

  public static void tourEffetsMoinsMoins(Personnage p) {
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof BenefiqueProfit) {
        p.getListeEffet().get(i).setNbrTour(p.getListeEffet().get(i).getNbrTour()-1);
      }
      if (p.getListeEffet().get(i).getNbrTour() == 0) {
        p.getListeEffet().get(i).supprEffetPerso(p);
        p.getListeEffet().remove(i);
        i--;
      }
    }
  }

  public static void siNonJusteApplique_TourEffetMoinsMoins(Personnage p) {
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof BenefiqueProfit) {
        if (!p.getListeEffet().get(i).getJusteApplique()) {
          p.getListeEffet().get(i).setNbrTour(p.getListeEffet().get(i).getNbrTour()-1);
        }
      }
      if (p.getListeEffet().get(i).getNbrTour() == 0) {
        p.getListeEffet().get(i).supprEffetPerso(p);
        p.getListeEffet().remove(i);
        i--;
      }
    }
  }

  public static void appliquerEffet(Personnage p) {
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof BenefiqueProfit) {
        p.getListeEffet().get(i).effetPerso(p);
      }
    }
  }
}
