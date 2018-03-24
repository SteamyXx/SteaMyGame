package runes;
import stats.*;
import personnage.*;

public class Fatale extends Rune {

  public Fatale() {
    super("F");
  }

  public Fatale(int pos, int niveau, Stat main, Stat[] sub, int nb_sub) {
    super("F", pos, niveau, main, sub, nb_sub);
  }

  public Fatale(int pos, int niveau, Stat main) {
    super("F", pos, niveau, main);
  }

  public void appliquerBonus(Personnage p) {
    p.rajouterBonusRune(0, p.getAttaque() * 20 / 100);
    p.setStatsSuppl(0, p.getStatsSuppl(0) + (p.getAttaque() * 20 / 100));
    p.setAttaqueMax(p.getAttaqueMax() + (p.getAttaque() * 20 / 100));
  }

  public void desappliquerBonus(Personnage p) {
    p.enleverBonusRune(0, p.getAttaque() * 20 / 100);
    p.setStatsSuppl(0, p.getStatsSuppl(0) - (p.getAttaque() * 20 / 100));
    p.setAttaqueMax(p.getAttaqueMax() - (p.getAttaque() * 20 / 100));
  }
}
