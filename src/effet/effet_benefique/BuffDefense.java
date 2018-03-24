package effet.effetbenefique.benefiquemalusprofit;
import effet.effetbenefique.benefiquemalusprofit.BenefiqueMalusProfit;
import personnage.*;

public class BuffDefense extends BenefiqueMalusProfit {

  public BuffDefense(int n) {
    super(n);
    this.etiquette = "Buff_D";
  }

  public void effetPerso(Personnage p) {
    if (p.getDefense() <= p.getDefenseMax()) {
      p.setDefense(p.getDefense()+p.getDefenseMax()*30/100);
    }
  }

  public void supprEffetPerso(Personnage p) {
    p.setDefense(p.getDefense()-p.getDefenseMax()*30/100);
  }
}
