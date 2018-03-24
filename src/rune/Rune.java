package runes;
import stats.*;
import java.util.*;
import personnage.*;
import stats.*;

public class Rune {

  private Stat main;
  private Stat[] sub;
  private int nb_sub;
  private int niveau;
  private int position;
  private String etiquette;

  public Rune(String etiquette, int pos, int niveau, Stat main, Stat[] sub, int nb_sub) {
    this.etiquette = etiquette;
    this.position = pos;
    this.niveau = niveau;
    this.main = main;
    this.sub = sub;
    this.nb_sub = nb_sub;
  }

  public Rune(String etiquette, int pos, int niveau, Stat main) {
    this.sub = new Stat[4];
    this.position = pos;
    this.main = main;
    this.sub[0] = this.statAuPif(false);
    this.nb_sub = 1;
    this.etiquette = etiquette;
    for (int i = 0; i<niveau; i++) {
      this.ameliorer();
    }
  }

  public Rune(String etiquette) {
    this.niveau = 1;
    this.sub = new Stat[4];
    Random r = new Random();
    this.position = r.nextInt(5);
    int possibilites = 0;
    Stat smain = new Attaque(true);
    switch (this.position) {
      case 0:
      smain = this.statAuPif(true);
      break;

      case 1:
      switch (r.nextInt(3)) {
        case 0:
        smain = new AttaqueFlat(true);
        break;

        case 1:
        smain = new PointsDeVieFlat(true);
        break;

        case 2:
        smain = new DefenseFlat(true);
        break;
      }
      break;

      case 2:
      switch (r.nextInt(4)) {
        case 0:
        smain = new Attaque(true);
        break;

        case 1:
        smain = new PointsDeVie(true);
        break;

        case 2:
        smain = new Defense(true);
        break;

        case 3:
        smain = new Vitesse(true);
        break;
      }
      break;

      case 3:
      switch (r.nextInt(5)) {
        case 0:
        smain = new Attaque(true);
        break;

        case 1:
        smain = new PointsDeVie(true);
        break;

        case 2:
        smain = new Defense(true);
        break;

        case 3:
        smain = new TauxCritiques(true);
        break;

        case 4:
        smain = new DegatsCritiques(true);
        break;
      }
      break;

      case 4:
      switch (r.nextInt(6)) {
        case 0:
        smain = new AttaqueFlat(true);
        break;

        case 1:
        smain = new PointsDeVieFlat(true);
        break;

        case 2:
        smain = new DefenseFlat(true);
        break;

        case 3:
        smain = new Attaque(true);
        break;

        case 4:
        smain = new PointsDeVie(true);
        break;

        case 5:
        smain = new Defense(true);
        break;
      }
      break;
    }
    this.main = smain;
    this.sub[0] = this.statAuPif(false);
    this.nb_sub = 1;
    this.etiquette = etiquette;
  }

  public void appliquerBonus(Personnage p) {

  }

  public void desappliquerBonus(Personnage p) {

  }

  public Stat statAuPif(boolean main) {
    Random r = new Random();
    Stat ssub = new Attaque(true);
    switch (r.nextInt(9)) {
      case 0:
      ssub = new AttaqueFlat(main);
      break;

      case 1:
      ssub = new Attaque(main);
      break;

      case 2:
      ssub = new PointsDeVieFlat(main);
      break;

      case 3:
      ssub = new PointsDeVie(main);
      break;

      case 4:
      ssub = new DefenseFlat(main);
      break;

      case 5:
      ssub = new Defense(main);
      break;

      case 6:
      ssub = new Vitesse(main);
      break;

      case 7:
      ssub = new TauxCritiques(main);
      break;

      case 8:
      ssub = new DegatsCritiques(main);
      break;
    }
    return ssub;
  }


  public void appliquerStats(Personnage p) {
    this.main.appliquerStat(p);
    for (int i = 0; i<nb_sub; i++) {
      this.sub[i].appliquerStat(p);
    }
  }

  public void bonusLvlVingt() {
    for (int i = 0; i<4; i++) {
      this.sub[i].bonusLvlVingt();
    }
  }

  public void ameliorer() {
    this.niveau++;
    if (this.niveau % 5 == 0 && this.niveau != 20) {
      this.sub[this.nb_sub++] = this.statAuPif(false);
    }
    if (this.niveau == 20) {
      this.bonusLvlVingt();
    }
    this.main.ameliorer(this.niveau);
  }

  public Stat getMain() {
    return this.main;
  }

  public Stat getSub(int i) {
    return this.sub[i-1];
  }

  public int getNbSub() {
    return this.nb_sub;
  }

  public int getPosition() {
    return this.position;
  }

  public int getNiveau() {
    return this.niveau;
  }

  public String getEtiquette() {
    return this.etiquette;
  }

  public String toString() {
    String sub = "";
    for (int i = 0; i<this.nb_sub; i++) {
      sub += "-"+this.sub[i].getID()+this.sub[i].getValeur();
    }
    return this.position+"-"+this.etiquette+"-"+this.niveau+"-"+this.main.getID()+this.main.getValeur()+sub;
  }
}
