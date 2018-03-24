package runes;
import stats.*;
import personnage.*;

public class Rage extends Rune {

  public Rage() {
    super("R");
  }

  public Rage(int pos, int niveau, Stat main, Stat[] sub, int nb_sub) {
    super("R", pos, niveau, main, sub, nb_sub);
  }

  public Rage(int pos, int niveau, Stat main) {
    super("R", pos, niveau, main);
  }

  public void appliquerBonus(Personnage p) {
    p.rajouterBonusRune(5, 26);
    p.setStatsSuppl(5, p.getStatsSuppl(5) + 26);
    p.setDgtsCrit(p.getDgtsCrit() + 26);
  }

  public void desappliquerBonus(Personnage p) {
    p.enleverBonusRune(5, 26);
    p.setStatsSuppl(5, p.getStatsSuppl(5) - 26);
    p.setDgtsCrit(p.getDgtsCrit() - 26);
  }
}
