package stats;
import java.util.*;
import personnage.*;

public class Attaque extends Stat {

  public Attaque(boolean main) {
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
    this.id = 1;
  }

  public Attaque(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 2;
    this.upgrade_impair = 3;
    this.id = 1;
  }

  public void appliquerStat(Personnage p) {
    int ajout = p.getAttaque()*this.valeur/100;
    p.setStatsSuppl(0, p.getStatsSuppl(0)+ajout);
    p.setAttaqueMax(p.getAttaqueMax()+ajout);
  }

  public String toString() {
    return "Atq +"+this.valeur+"%";
  }
}
