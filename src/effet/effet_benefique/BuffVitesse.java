package effet.effetbenefique.benefiquemalusprofit;
import effet.effetbenefique.benefiquemalusprofit.BenefiqueMalusProfit;
import personnage.*;

public class BuffVitesse extends BenefiqueMalusProfit {

  public BuffVitesse(int n) {
    super(n);
    this.etiquette = "Buff_Spd";
  }

  public void effetPerso(Personnage p) {
    if (p.getVitesse() <= p.getVitesseMax()) {
      p.setVitesse(p.getVitesse()+p.getVitesseMax()*30/100);
    }
  }

  public void supprEffetPerso(Personnage p) {
    p.setVitesse(p.getVitesse()-p.getVitesseMax()*30/100);
  }
}
