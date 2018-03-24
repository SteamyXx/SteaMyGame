package stats;
import personnage.*;
import java.util.*;

public class AttaqueFlat extends Stat {

  public AttaqueFlat(boolean main) {
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
    this.id = 0;
  }

  public AttaqueFlat(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 1;
    this.upgrade_impair = 3;
    this.id = 0;
  }

  public void appliquerStat(Personnage p) {
    p.setStatsSuppl(0, p.getStatsSuppl(0)+this.valeur);
    p.setAttaqueMax(p.getAttaqueMax()+this.valeur);
  }

  public String toString() {
    return "Atq +"+this.valeur;
  }
}
