package runes;
import stats.*;
import personnage.*;

public class Swift extends Rune {

  public Swift() {
    super("S");
  }

  public Swift(int pos, int niveau, Stat main, Stat[] sub, int nb_sub) {
    super("S", pos, niveau, main, sub, nb_sub);
  }

  public Swift(int pos, int niveau, Stat main) {
    super("S", pos, niveau, main);
  }

  public void appliquerBonus(Personnage p) {
    p.rajouterBonusRune(1, p.getVitesse() * 22 / 100);
    p.setStatsSuppl(1, p.getStatsSuppl(1) + (p.getVitesse() * 22 / 100));
    p.setVitesseMax(p.getVitesseMax() + (p.getVitesse() * 22 / 100));
  }

  public void desappliquerBonus(Personnage p) {
    p.enleverBonusRune(1, p.getVitesse() * 22 / 100);
    p.setStatsSuppl(1, p.getStatsSuppl(1) - (p.getVitesse() * 22 / 100));
    p.setVitesseMax(p.getVitesseMax() - (p.getVitesse() * 22 / 100));
  }
}
