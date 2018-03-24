/*
*Cette classe est une classe de Okeanos
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import java.util.*;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import java.util.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;
import application.*;

import effet.effettour.*;
import effet.*;


public class Okeanos extends Personnage {

  /**Constructeur de la classe Okeanos qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Okeanos
  *(les autres paramètres sont prédéfinis)
  */
  public Okeanos(String n) {
    super(355, 128, 117, 70, n, "Poussée de Force", "Lance de Dévastation", "Pluie de pierre", "Terre Sacrée", 3, 4, 4, false, false, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 6;
    this.type_sort[2] = 1;
    this.type_sort[3] = 3;
  }

  /**Méthode qui correspond au sort n°1 de Okeanos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    EffetNocif.transfere(this, cibles[0], 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Okeanos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int clean;
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*80/100 - cibles[0].getDefense()*30/100;
    cibles[0].cooldownPlusPlus();
    EffetBenefique.clean(cibles[0], 100);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Okeanos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    Random r = new Random();
    int i = 0;
    boolean etourdis = false;
    boolean save = false;
    int nbPersoEtourdi = 0;
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*80/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*80/100 - cibles[1].getDefense()*30/100;
    for (int j = 0; j<2; j++) {
      etourdis = false;
      save = false;
      if (!cibles[j].possedeEffet(new Immunite(0))) {
        if (!(cibles[j] instanceof Poutch)) {
          while (i<4 && !etourdis) {
            if (r.nextInt(100) < 25) {
              etourdis = cibles[j].appliquerEffet(new Etourdissement(1), 100);
              if (etourdis) {
                save = etourdis;
              }
            }
            i++;
          }
        }
      } else {
        Combat.ajouterCommentaire("-"+cibles[j].getClass().getName().substring(11)+" est immunisé contre les effets nocifs !");
      }
      if (!(cibles[j] instanceof Nephthys) && !(cibles[j] instanceof Poutch)) {
        if (!save) {
          Combat.ajouterCommentaire("-"+cibles[j].getClass().getName().substring(11)+" a résisté aux étourdissements");
        } else {
          nbPersoEtourdi++;
        }
      }
    }
    this.appliquerEffet(new BuffVitesse(nbPersoEtourdi+1));
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Okeanos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].cleanToutLesEffetNocif();
    cibles[1].cleanToutLesEffetNocif();
    cibles[0].appliquerEffet(new Immunite(1));
    cibles[1].appliquerEffet(new Immunite(1));
    cibles[0].appliquerEffet(new Invincibilite(1));
    cibles[1].appliquerEffet(new Invincibilite(1));
    this.setCooldown(3, this.cooldown_max4);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet b = new Bombe(0, 0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (this.cooldown[1] == 0 && ennemi.possedeEffetBenefique()) {
      res = 2;
    } else if ((ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) || ennemi.possedeEffet(imm) || this.possedeEffet(b)) {
      res = 1;
    } else if (this.cooldown[2] == 0) {
      res = 3;
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else {
      res = 1;
    }

    return res;
  }


  public int[] cibleSortIa(Personnage allieLanceur, Personnage[] ennemis) {
    int[] cibleSort = new int[2];
    Effet sil = new Silence(0);
    boolean ko = false;
    Random r = new Random();
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }

    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (this.cooldown[3] == 0) {
      cibleSort[0] = 3;
      cibleSort[1] = 4;
    } else if (this.possedeEffet(new Bombe(0, 0))) {
      cibleSort[1] = 1;
    } else if (this.cooldown[2] == 0 && !(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0)))) {
      cibleSort[1] = 3;
    } else if (this.cooldown[1] == 0 && ennemis[cibleSort[0]-1].nbrEffetsBenefiques() > 0) {
      cibleSort[1] = 2;
    } else if (this.cooldown[1] == 0 && ennemis[this.changementCible(cibleSort[0])-1].nbrEffetsBenefiques() > 0) {
      cibleSort[0] = this.changementCible(cibleSort[0]);
      cibleSort[1] = 2;
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else {
      cibleSort[1] = 1;
    }

    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 2) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Okeanos
  *@return la description de Okeanos
  */
  public String toString() {
    return "Votre personnage est un Okeanos et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Okeanos
  *@return la description du sort n°1 de Okeanos
  */
  public String descriptionSort1() {
    return "Inflige des dégats et transfère un de tes effets nocifs à l'ennemi. Ce dernier a 25% de chance de résisté à l'effet transferé (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Okeanos
  *@return la description du sort n°1 de Okeanos
  */
  public String descriptionSort2() {
    return "Attaque l'ennemi et lui retire un effet bénéfique et retarde les cooldowns de l'ennemi de 1 tour (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Okeanos
  *@return la description du sort n°1 de Okeanos
  */
  public  String descriptionSort3() {
    return "Fait tombé une rafale de 4 météores sur l'ennemi pour lui infliger des dégats. Chaque rafale a 25% de chance d'étourdir <Stun> l'ennemi. Si l'ennemi est etourdi, vous obtenez un buff de vitesse <Buff_Spd> de 2 tours (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Okeanos
  *@return la description du sort n°1 de Okeanos
  */
  public String descriptionSort4() {
    return "Si l'ennemi possède un effet nocif, augmente de 25% les dégats de votre prochaine attaque. De plus, si l'ennemi possède un effet bénéfique, augmente de 25% les dégats de votre prochaine attaque (cumulable)";
  }
}
