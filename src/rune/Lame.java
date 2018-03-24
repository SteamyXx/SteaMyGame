package runes;
import stats.*;
import personnage.*;

public class Lame extends Rune {

  public Lame() {
    super("L");
  }

  public Lame(int pos, int niveau, Stat main, Stat[] sub, int nb_sub) {
    super("L", pos, niveau, main, sub, nb_sub);
  }

  public Lame(int pos, int niveau, Stat main) {
    super("L", pos, niveau, main);
  }

  public void appliquerBonus(Personnage p) {
    p.rajouterBonusRune(4, 12);
    p.setStatsSuppl(4, p.getStatsSuppl(4) + 12);
    p.setTxCrit(p.getTxCrit() + 12);
  }

  public void desappliquerBonus(Personnage p) {
    p.enleverBonusRune(4, 12);
    p.setStatsSuppl(4, p.getStatsSuppl(4) - 12);
    p.setTxCrit(p.getTxCrit() - 12);
  }
}
