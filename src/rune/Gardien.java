package runes;
import stats.*;
import personnage.*;

public class Gardien extends Rune {

  public Gardien() {
    super("G");
  }

  public Gardien(int pos, int niveau, Stat main, Stat[] sub, int nb_sub) {
    super("G", pos, niveau, main, sub, nb_sub);
  }

  public Gardien(int pos, int niveau, Stat main) {
    super("G", pos, niveau, main);
  }

  public void appliquerBonus(Personnage p) {
    p.rajouterBonusRune(2, p.getDefense() * 20 / 100);
    p.setStatsSuppl(2, p.getStatsSuppl(2) + (p.getDefense() * 20 / 100));
    p.setDefenseMax(p.getDefenseMax() + (p.getDefense() * 20 / 100));
  }

  public void desappliquerBonus(Personnage p) {
    p.enleverBonusRune(2, p.getDefense() * 20 / 100);
    p.setStatsSuppl(2, p.getStatsSuppl(2) - (p.getDefense() * 20 / 100));
    p.setDefenseMax(p.getDefenseMax() - (p.getDefense() * 20 / 100));
  }
}
