package runes;
import stats.*;
import personnage.*;

public class Energie extends Rune {

  public Energie() {
    super("E");
  }

  public Energie(int pos, int niveau, Stat main, Stat[] sub, int nb_sub) {
    super("E", pos, niveau, main, sub, nb_sub);
  }

  public Energie(int pos, int niveau, Stat main) {
    super("E", pos, niveau, main);
  }

  public void appliquerBonus(Personnage p) {
    p.rajouterBonusRune(3, p.getPdv() * 20 / 100);
    p.setStatsSuppl(3, p.getStatsSuppl(3) + (p.getPdv() * 20 / 100));
    p.setPdvMax(p.getPdvMax() + (p.getPdv() * 20 / 100));
  }

  public void desappliquerBonus(Personnage p) {
    p.enleverBonusRune(3, p.getPdv() * 20 / 100);
    p.setStatsSuppl(3, p.getStatsSuppl(3) - (p.getPdv() * 20 / 100));
    p.setPdvMax(p.getPdvMax() - (p.getPdv() * 20 / 100));
  }
}
