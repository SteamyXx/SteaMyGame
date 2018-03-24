package stats;
import java.util.*;
import personnage.*;

public abstract class Stat {

  protected boolean main;
  protected int upgrade_pair;
  protected int upgrade_impair;
  protected int valeur;
  protected int id;

  public Stat(boolean main) {
    this.main = main;
  }

  public Stat() {

  }

  public abstract void appliquerStat(Personnage p);

  public void ameliorer(int niveau) {
    if (niveau % 2 == 0) {
      this.valeur += this.upgrade_pair;
    } else {
      this.valeur += this.upgrade_impair;
    }
  }

  public void bonusLvlVingt() {
    this.valeur += (this.valeur / 2);
  }

  public int getPair() {
    return this.upgrade_pair;
  }

  public int getImpair() {
    return this.upgrade_impair;
  }

  public int getID() {
    return this.id;
  }

  public int getValeur() {
    return this.valeur;
  }

  public String toString() {
    return "";
  }
}
