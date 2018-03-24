package stats;
import java.util.*;
import personnage.*;

public class Vitesse extends Stat {

  public Vitesse(boolean main) {
    super(main);
    this.upgrade_pair = 1;
    this.upgrade_impair = 2;
    int valeur = 0;
    Random r = new Random();
    if (main) {
      this.valeur = 3;
    } else {
      this.valeur = r.nextInt(6)+3;
    }
    this.id = 6;
  }

  public Vitesse(boolean main, int valeur) {
    this.valeur = valeur;
    this.main = main;
    this.upgrade_pair = 1;
    this.upgrade_impair = 2;
    this.id = 6;
  }

  public void appliquerStat(Personnage p) {
    p.setStatsSuppl(1, p.getStatsSuppl(1)+this.valeur);
    p.setVitesseMax(p.getVitesseMax()+this.valeur);
  }

  public String toString() {
    return "Vit +"+this.valeur;
  }
}
