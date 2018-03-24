package stats;
import java.util.*;
import personnage.*;

public class DefenseFlat extends Stat {

  public DefenseFlat(boolean main) {
    super(main);
    this.upgrade_pair = 1;
    this.upgrade_impair = 3;
    int valeur = 0;
    Random r = new Random();
    if (main) {
      this.valeur = 5;
    } else {
      this.valeur = r.nextInt(7)+6;
    }
    this.id = 4;
  }

  public DefenseFlat(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 1;
    this.upgrade_impair = 3;
    this.id = 4;
  }

  public void appliquerStat(Personnage p) {
    p.setStatsSuppl(2, p.getStatsSuppl(2)+this.valeur);
    p.setDefenseMax(p.getDefenseMax()+this.valeur);
  }

  public String toString() {
    return "Def +"+this.valeur;
  }
}
