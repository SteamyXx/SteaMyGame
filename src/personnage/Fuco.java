/*
*Cette classe est une classe de Fuco
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import java.util.*;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import java.util.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import application.*;
import effet.effettour.*;
import effet.*;


public class Fuco extends Personnage {

  /**Constructeur de la classe Fuco qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Fuco
  *(les autres paramètres sont prédéfinis)
  */
  public Fuco(String n) {
    super(359, 110, 136, 79, n, "Invocation d'âme", "Doline", "Touché Mortel", "Mur des Morts", 2, 4, 0, true, true, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 1;
    this.type_sort[3] = 0;
  }


  /**Méthode qui correspond au sort n°1 de Fuco
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Ralentissement(2), 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Fuco
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = (cibles[1] instanceof Poutch) ? 0 : this.getAttaque()*75/100 - cibles[1].getDefense()*30/100;
    this.volDeVie(degatseffectue[0] + degatseffectue[1], cibles[0], 50);
    this.heal(this.getPdvMax()*3/100);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Fuco
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 0;
    degatseffectue[1] = 0;
    Random r = new Random();
    boolean[] immun = new boolean[2];
    immun[0] = false;
    immun[1] = false;
    int cible = 0;
    for (int i = 0; i<4; i++) {
      cible = r.nextInt(2);
      if (cibles[1] instanceof Poutch) {
        cible = 0;
      }
      degatseffectue[cible] += this.getAttaque()*26/100 - cibles[cible].getDefense()*8/100;
      cibles[cible].appliquerEffet(new BreakDefense(2));
      if (cibles[cible].possedeEffet(new Ralentissement(0))) {
        if (!cibles[cible].possedeEffet(new Immunite(0))) {
          if (r.nextInt(100) < 40 && !(cibles[cible] instanceof Nephthys)) {
            cibles[cible].remplacerEffetParUnAutre(new Ralentissement(0), new Etourdissement(1));
          }
        } else {
          if (!immun[cible]) {
            Combat.ajouterCommentaire("-"+cibles[cible].getClass().getName().substring(11)+" est immunisé contre les effets nocifs !");
          }
          immun[cible] = true;
        }
      }
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Fuco
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    if (this.getShield() > 0) {
      this.appliquerEffet(new BuffAttaque(1), 100);
    }
    this.setShield(this.getPdvMax()*7/100);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet ah = new AntiHeal(0);
    Effet slow = new Ralentissement(0);
    Effet ba = new BuffAttaque(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[1] == 0 && this.pdv < (this.pdv_max * 75 / 100)) {
      res = 2;
    } else if (ennemi.possedeEffet(imm) && this.pdv >= (this.pdv_max * 75 / 100)) {
      res = 1;
    } else if (this.possedeEffet(ba) && !this.possedeEffet(ah) && this.pdv < (this.pdv_max * 75 / 100) && this.cooldown[1] == 0) {
      res = 2;
    } else if (!ennemi.possedeEffet(slow) && ennemi.getVitesse() > this.vitesse) {
      res = 1;
    } else if (this.pdv < (this.pdv_max * 50 / 100) && this.cooldown[1] == 0 && !this.possedeEffet(ah)) {
      res  = 2;
    } else if (!ennemi.possedeEffet(slow)) {
      res = 1;
    } else if (this.cooldown[2] == 0) {
      res = 3;
    } else {
      res = 1;
    }

    return res;
  }


  public int[] cibleSortIa(Personnage allieLanceur, Personnage[] ennemis) {
    int[] cibleSort = new int[2];
    boolean ko = false;
    Effet sil = new Silence(0);
    Random r = new Random();
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }

    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (this.getPdv() < this.getPdvMax()*70/100 && !this.possedeEffet(new AntiHeal(0)) && this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if ((ennemis[0].possedeEffet(new Ralentissement(0)) || ennemis[1].possedeEffet(new Ralentissement(0))) && this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Fuco
  *@return la description de Fuco
  */
  public String toString() {
    return "Votre personnage est un Fuco et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Fuco
  *@return la description du sort n°1 de Fuco
  */
  public String descriptionSort1() {
    return "Attaque l'ennemi avec l'âme des morts et ralenti l'ennemi pendant 2 tours avec 75% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Fuco
  *@return la description du sort n°1 de Fuco
  */
  public String descriptionSort2() {
    return "Inflige des dégats aux ennemis avec un trou qui consome les âmes et récupère 50% des dégats infligés aux deux ennemis + 3% de tes points de vie maximums (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Fuco
  *@return la description du sort n°1 de Fuco
  */
  public  String descriptionSort3() {
    return "Attaque aléatoirement 4 fois les ennemis avec pour chaque attaque, 35% de chance de réduire leur défense pendant 2 tours. De plus, à chaque coup, si l'ennemi possède un effet de ralentissement, alors cet effet aura 40% de chance d'être remplacé par un effet d'étourdissement (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Fuco
  *@return la description du sort n°1 de Fuco
  */
  public String descriptionSort4() {
    return "Au début de chaque tour, si vous posseder un bouclier, augment votre attaque pendant 1 tour. Ensuite, vous confère un bouclier équivalent à 7% de vos points de vie maximums. (non cumulable)";
  }
}
