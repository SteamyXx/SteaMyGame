package comptes;
import java.io.*;
import java.util.*;
import runes.*;
import stats.*;
import personnage.*;

public class Compte {

  private String login;
  private String mdp;
  private int etageCourant;
  private int argent;
  private Personnage[] persos;
  private int nbrPersos;
  private List<Rune> inventaire;
  private TourCeleste tour;


  public Compte(String login, String mdp) {
    this.login = login;
    this.mdp = mdp;
    this.etageCourant = 1;
    this.argent = 0;
    this.persos = new Personnage[4];
    this.nbrPersos = 0;
    this.inventaire = new ArrayList<Rune>();
    this.tour = new TourCeleste();
  }


  public void etagePlusPlus() {
    this.etageCourant++;
  }

  public void setEtageFin() {
    this.etageCourant = -35;
  }


  public void vendreRune(Rune r) {
    this.inventaire.remove(r);
    int res = 0;
    res += 1000*r.getNiveau()+10*r.getMain().getValeur();
    for (int i = 0; i<r.getNbSub(); i++) {
      res += r.getSub(i+1).getValeur();
    }
    this.argent += res;
  }


  public Rune dropRune() {
    Random r = new Random();
    Rune rune = new Fatale();
    switch (r.nextInt(6)) {
      case 0:
      rune = new Fatale();
      break;

      case 1:
      rune = new Swift();
      break;

      case 2:
      rune = new Gardien();
      break;

      case 3:
      rune = new Energie();
      break;

      case 4:
      rune = new Lame();
      break;

      case 5:
      rune = new Rage();
      break;
    }
    return rune;
  }


  public void ajouterRuneInventaire(Rune r) {
    this.inventaire.add(r);
  }


  public static int typeRuneToInt(Rune r) {
    int type = 0;
    if (r instanceof Swift) {
      type = 1;
    } else if (r instanceof Gardien) {
      type = 2;
    } else if (r instanceof Energie) {
      type = 3;
    } else if (r instanceof Lame) {
      type = 4;
    } else if (r instanceof Rage) {
      type = 5;
    }
    return type;
  }


  public static Rune intToRune(int type) {
    Rune r = new Fatale();
    if (type == 1) {
      r = new Swift();
    } else if (type == 2) {
      r = new Gardien();
    } else if (type == 3) {
      r = new Energie();
    } else if (type == 4) {
      r = new Lame();
    } else if (type == 5) {
      r = new Rage();
    }
    return r;
  }

  public int posPersonnage(Personnage p) {
    int i = 0;
    int res = -1;
    boolean stop = false;
    while (i < this.persos.length && !stop) {
      if (this.persos[i].getClass().equals(p.getClass())) {
        stop = true;
        res = i;
      }
      i++;
    }
    return res;
  }

  public void equiperRune(Personnage p, Rune r) {
    int pos = this.posPersonnage(p);
    int type = Compte.typeRuneToInt(r);
    this.persos[pos].typeRunePlusPlus(type);//incrémente la valeur désignant le nombre de rune du même type équipé par le personnage
    if (this.persos[pos].getRune(r.getPosition()) != null) {
      this.desequiperRune(p, r.getPosition());
    }
    this.persos[pos].equiperRune(r);
    this.inventaire.remove(r);
  }


  public void desequiperRune(Personnage p, int pos) {
    int posp = this.posPersonnage(p);
    Rune r = this.persos[posp].getRune(pos);
    int type = Compte.typeRuneToInt(r);
    this.persos[posp].typeRuneMoinsMoins(type);
    this.inventaire.add(r);
    this.persos[posp].supprimerRune(pos);
  }


  public void ameliorerRune(Rune r) {
    r.ameliorer();
  }


  public void ajouterPersonnage(int i) {
    switch (i) {
      case 0:
      this.persos[this.nbrPersos] = new Kaito("");
      break;

      case 1:
      this.persos[this.nbrPersos] = new Ritesh("");
      break;

      case 2:
      this.persos[this.nbrPersos] = new Zaiross("");
      break;

      case 3:
      this.persos[this.nbrPersos] = new Veromos("");
      break;

      case 4:
      this.persos[this.nbrPersos] = new Okeanos("");
      break;

      case 5:
      this.persos[this.nbrPersos] = new Fuco("");
      break;

      case 6:
      this.persos[this.nbrPersos] = new Sian("");
      break;

      case 7:
      this.persos[this.nbrPersos] = new Zeratu("");
      break;

      case 8:
      this.persos[this.nbrPersos] = new Seara("");
      break;

      case 9:
      this.persos[this.nbrPersos] = new Cuivre("");
      break;

      case 10:
      this.persos[this.nbrPersos] = new Eludia("");
      break;

      case 11:
      this.persos[this.nbrPersos] = new Thrain("");
      break;

      case 12:
      this.persos[this.nbrPersos] = new Woonhak("");
      break;

      case 13:
      this.persos[this.nbrPersos] = new XiongFei("");
      break;

      case 14:
      this.persos[this.nbrPersos] = new Icaru("");
      break;

      case 15:
      this.persos[this.nbrPersos] = new XiaoLin("");
      break;

      case 16:
      this.persos[this.nbrPersos] = new Oberon("");
      break;

      case 17:
      this.persos[this.nbrPersos] = new Nephthys("");
      break;

      case 18:
      this.persos[this.nbrPersos] = new Basalt("");
      break;

      case 19:
      this.persos[this.nbrPersos] = new Tablo("");
      break;

      case 20:
      this.persos[this.nbrPersos] = new Eshir("");
      break;

      case 21:
      this.persos[this.nbrPersos] = new Stella("");
      break;

      case 22:
      this.persos[this.nbrPersos] = new Ethna("");
      break;

      case 23:
      this.persos[this.nbrPersos] = new Thebae("");
      break;

      case 24:
      this.persos[this.nbrPersos] = new Cadiz("");
      break;

      case 25:
      this.persos[this.nbrPersos] = new Erwin("");
      break;

      case 26:
      this.persos[this.nbrPersos] = new Nicki("");
      break;

      case 27:
      this.persos[this.nbrPersos] = new Sige("");
      break;

      case 28:
      this.persos[this.nbrPersos] = new Haken("");
      break;

      case 29:
      this.persos[this.nbrPersos] = new Cichlid("");
      break;

      case 30:
      this.persos[this.nbrPersos] = new Chasun("");
      break;

      case 31:
      this.persos[this.nbrPersos] = new Artamiel("");
      break;

      //Rajouter persos ICI

      case -1:
      System.out.println("nop");
      break;
    }
    this.nbrPersos++;
  }


  public void gainArgent(int gain) {
    this.argent += gain;
  }


  public void perteArgent(int perte) {
    this.argent -= perte;
    if (this.argent < 0) {
      this.argent = 0;
    }
  }


  public TourCeleste getTour() {
    return this.tour;
  }

  public String getLogin() {
    return this.login;
  }

  public String getMdp() {
    return this.mdp;
  }

  public int getEtage() {
    return this.etageCourant;
  }

  public void setEtage(int etage) {
    this.etageCourant = etage;
  }

  public int getArgent() {
    return this.argent;
  }

  public void setArgent(int argent) {
    this.argent = argent;
  }

  public List<Rune> getInventaire() {
    return this.inventaire;
  }

  public Personnage getPerso(int pos) {
    return this.persos[pos];
  }

  public Personnage[] getPersos() {
    return this.persos;
  }

  public int getNbrPerso() {
    return this.nbrPersos;
  }

  public void setNbrPerso(int nbrPersos) {
    this.nbrPersos = nbrPersos;
  }

  public String toStringInventaire() {
    String str = "";
    for (Rune r : this.inventaire) {
      str += r.toString()+"\n";
    }
    return str;
  }


  //String s doit être de la forme : "Type Pos Niveau MainStat"
  public Rune createRuneSimple(String s) {
    List<String> list = new LinkedList<String>();
    StringTokenizer st = new StringTokenizer(s);
    while (st.hasMoreTokens()) {
      list.add(st.nextToken());
    }
    Stat main = null;
    Rune rune = null;
    switch (list.get(3)) {
      case "Att":
      main = new AttaqueFlat(true);
      break;

      case "Att%":
      main = new Attaque(true);
      break;

      case "Hp":
      main = new PointsDeVieFlat(true);
      break;

      case "Hp%":
      main = new PointsDeVie(true);
      break;

      case "Def":
      main = new DefenseFlat(true);
      break;

      case "Def%":
      main = new Defense(true);
      break;

      case "Spd":
      main = new Vitesse(true);
      break;

      case "Tc":
      main = new TauxCritiques(true);
      break;

      case "Dc":
      main = new DegatsCritiques(true);
      break;

      default:
      System.out.println("Erreur1 !");
      break;
    }

    switch (list.get(0)) {
      case "Fatale":
      rune = new Fatale(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), main);
      break;

      case "Swift":
      rune = new Swift(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), main);
      break;

      case "Gardien":
      rune = new Gardien(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), main);
      break;

      case "Energie":
      rune = new Energie(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), main);
      break;

      case "Lame":
      rune = new Lame(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), main);
      break;

      case "Rage":
      rune = new Rage(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), main);
      break;

      default:
      System.out.println("Erreur !");
      break;
    }
    return rune;
  }


  public String toString() {
    String perso = "";
    if (this.nbrPersos > 0) {
      for (int i = 0; i<this.nbrPersos; i++) {
        perso += this.persos[i].toString() + "\n";
      }
    }
    return "login : " + this.login + "\nmot de passe : " + this.mdp + "\nétage courant : " + this.etageCourant + "\nargent : " + this.argent + "\npersonnages : "
    + perso + "\nnombre de personage : " + this.nbrPersos + "\ninventaire : " + this.inventaire;
  }
}
