package effet.effetbenefique.benefiqueprofit;
import effet.effetbenefique.benefiqueprofit.BenefiqueProfit;
import personnage.*;

public class BuffAttaque extends BenefiqueProfit {

  public BuffAttaque(int n) {
    super(n);
    this.etiquette = "Buff_A";
  }

  public void effetPerso(Personnage p) {
    if (p.getAttaque() <= p.getAttaqueMax()) {
      p.setAttaque(p.getAttaque()+p.getAttaqueMax()*30/100);
    }
  }

  public void supprEffetPerso(Personnage p) {
    p.setAttaque(p.getAttaque()-p.getAttaqueMax()*30/100);
  }
}
