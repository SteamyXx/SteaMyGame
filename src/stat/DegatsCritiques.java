package stats;
import personnage.*;
import java.util.*;

public class DegatsCritiques extends Stat {

  public DegatsCritiques(boolean main) {
    super(main);
    this.upgrade_pair = 3;
    this.upgrade_impair = 3;
    int valeur = 0;
    Random r = new Random();
    if (main) {
      this.valeur = 8;
    } else {
      this.valeur = r.nextInt(7)+8;
    }
    this.id = 8;
  }

  public DegatsCritiques(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 3;
    this.upgrade_impair = 3;
    this.id = 8;
  }

  public void appliquerStat(Personnage p) {
    p.setStatsSuppl(5, p.getStatsSuppl(5)+this.valeur);
    p.setDgtsCrit(p.getDgtsCrit()+this.valeur);
  }

  public String toString() {
    return "Dgts crit. +"+this.valeur+"%";
  }
}
