package effet.effetbenefique.benefiqueprofit;
import effet.effetbenefique.benefiqueprofit.BenefiqueProfit;
import personnage.*;

public class BuffCrit extends BenefiqueProfit {

  private boolean applique;

  public BuffCrit(int n) {
    super(n);
    this.applique = false;
    this.etiquette = "Buff_Crit";
  }

  public void effetPerso(Personnage p) {
    if (!applique) {
      this.applique = true;
      p.setTxCrit(p.getTxCrit()+25);
    }
    if (p.getTxCrit() > 100) {
      p.setTxCrit(100);
    }
  }

  public void supprEffetPerso(Personnage p) {
    p.setTxCrit(p.getTxCrit()-25);
  }
}
