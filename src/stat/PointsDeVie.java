package stats;
import java.util.*;
import personnage.*;

public class PointsDeVie extends Stat {

  public PointsDeVie(boolean main) {
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
    this.id = 3;
  }

  public PointsDeVie(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 2;
    this.upgrade_impair = 3;
    this.id = 3;
  }

  public void appliquerStat(Personnage p) {
    int ajout = p.getPdv()*this.valeur/100;
    p.setStatsSuppl(3, p.getStatsSuppl(3)+ajout);
    p.setPdvMax(p.getPdvMax()+ajout);
  }

  public String toString() {
    return "Hp +"+this.valeur+"%";
  }
}
