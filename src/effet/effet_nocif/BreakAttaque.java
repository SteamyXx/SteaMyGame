package effet.effetnocif.nocifmalus;
import effet.effetnocif.nocifmalus.NocifMalus;
import personnage.*;

public class BreakAttaque extends NocifMalus {

  public BreakAttaque(int n) {
    super(n);
    this.etiquette = "Brk_A";
  }

  public void effetPerso(Personnage p) {
    if (p.getAttaque() >= p.getAttaqueMax()) {
      p.setAttaque(p.getAttaque()-p.getAttaqueMax()*30/100);
    }
  }

  public void supprEffetPerso(Personnage p) {
    p.setAttaque(p.getAttaque()+p.getAttaqueMax()*30/100);
  }
}
