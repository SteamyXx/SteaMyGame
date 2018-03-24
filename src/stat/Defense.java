package stats;
import java.util.*;
import personnage.*;

public class Defense extends Stat {

  public Defense(boolean main) {
    super(main);
    this.upgrade_pair = 2;
    this.upgrade_impair = 3;
    int valeur = 0;
    Random r = new Random();
    if (main) {
      this.valeur = 8;
    } else {
      this.valeur = r.nextInt(6)+5;
    }
    this.id = 5;
  }

  public Defense(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 2;
    this.upgrade_impair = 3;
    this.id = 5;
  }

  public void appliquerStat(Personnage p) {
    int ajout = p.getDefense()*this.valeur/100;
    p.setStatsSuppl(2, p.getStatsSuppl(2)+ajout);
    p.setDefenseMax(p.getDefenseMax()+ajout);
  }

  public String toString() {
    return "Def +"+this.valeur+"%";
  }
}
