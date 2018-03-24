package stats;
import java.util.*;
import personnage.*;

public class PointsDeVieFlat extends Stat {

  public PointsDeVieFlat(boolean main) {
    super(main);
    this.upgrade_pair = 3;
    this.upgrade_impair = 5;
    int valeur = 0;
    Random r = new Random();
    if (main) {
      this.valeur = 20;
    } else {
      this.valeur = r.nextInt(17)+16;
    }
    this.id = 2;
  }

  public PointsDeVieFlat(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 3;
    this.upgrade_impair = 5;
    this.id = 2;
  }

  public void appliquerStat(Personnage p) {
    p.setStatsSuppl(3, p.getStatsSuppl(3)+this.valeur);
    p.setPdvMax(p.getPdvMax()+this.valeur);
  }

  public String toString() {
    return "Hp +"+this.valeur;
  }
}
