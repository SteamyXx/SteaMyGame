package effet.effettour;
import effet.effetnocif.nocifmalus.AntiHeal;
import effet.effettour.EffetTour;
import personnage.*;
import application.*;

public class Regeneration extends EffetTour {

  public Regeneration(int n) {
    super(n);
    this.etiquette = "Regen";
  }

  public void effetPerso(Personnage p) {
    if (!p.possedeEffet(new AntiHeal(0))) {
      if (p.getPdv() < p.getPdvMax()) {
        Combat.ajouterCommentaire("-La régénération de "+p.getClass().getName().substring(11)+" lui à restauré "+p.getPdvMax()*7/100+" pdv");
      }
      p.setPdv(p.getPdv()+p.getPdvMax()*7/100);
      if (p.getPdv() > p.getPdvMax()) {
        p.setPdv(p.getPdvMax());
      }
    } else {
      Combat.ajouterCommentaire("-"+p.getClass().getName().substring(11)+" ne peut pas se soin");
    }
  }

  public void supprEffetPerso(Personnage p) {

  }
}
