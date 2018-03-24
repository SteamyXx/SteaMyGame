package effet.effetnocif.nocifmalusprofit;
import effet.effetnocif.nocifmalusprofit.NocifMalusProfit;
import personnage.*;

public class BreakDefense extends NocifMalusProfit {

  public BreakDefense(int n) {
    super(n);
    this.etiquette = "Brk_D";
  }

  public void effetPerso(Personnage p) {
    if (p.getDefense() >= p.getDefenseMax()) {
      p.setDefense(p.getDefense()-p.getDefenseMax()*30/100);
    }
  }

  public void supprEffetPerso(Personnage p) {
    p.setDefense(p.getDefense()+p.getDefenseMax()*30/100);
  }
}
