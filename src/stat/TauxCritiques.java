package stats;
import java.util.*;
import personnage.*;

public class TauxCritiques extends Stat {

  public TauxCritiques(boolean main) {
    super(main);
    this.upgrade_pair = 1;
    this.upgrade_impair = 2;
    int valeur = 0;
    Random r = new Random();
    if (main) {
      this.valeur = 5;
    } else {
      this.valeur = r.nextInt(7)+6;
    }
    this.id = 7;
  }

  public TauxCritiques(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 1;
    this.upgrade_impair = 2;
    this.id = 7;
  }

  public void appliquerStat(Personnage p) {
    p.setStatsSuppl(4, p.getStatsSuppl(4)+this.valeur);
    if (p.getTxCrit()+this.valeur <= 100) {
      p.setTxCrit(p.getTxCrit()+this.valeur);
    } else {
      p.setTxCrit(100);
    }
  }

  public String toString() {
    return "Tx crit. +"+this.valeur+"%";
  }
}
