/*
*Cette classe est une classe de Zaiross
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import java.util.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import effet.effettour.*;
import effet.*;


public class Zaiross extends Personnage {

/**Constructeur de la classe Zaiross qui fait appel au contructeur de la classe Personnage
*@param n qui représente le pseudo de Zaiross
*(les autres paramètres sont prédéfinis)
*/
  public Zaiross(String n) {
    super(326, 160, 106, 88, n, "Eclair de feu", "Embrasement corporel", "Déflagration", "Respiration de feu", 3, 2, 5);
    this.type_sort[0] = 2;
    this.type_sort[1] = 5;
    this.type_sort[2] = 1;
    this.type_sort[3] = 1;
  }

/**Méthode qui correspond au sort n°1 de Zaiross
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*80/100 - cibles[0].getDefense()*30/100;
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Zaiross
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    this.appliquerEffet(new BuffAttaque(3), 100);
    this.setCooldown(1,  this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Zaiross
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*85/100 - cibles[1].getDefense()*30/100;
    for (int i = 0; i<3; i++) {
      cibles[0].appliquerEffet(new DegatContinu(2), 50);
      cibles[1].appliquerEffet(new DegatContinu(2), 50);
    }
    this.setCooldown(2,  this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Zaiross
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*90/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*90/100 - cibles[1].getDefense()*30/100;
    cibles[0].cooldownPlusPlus();
    cibles[1].cooldownPlusPlus();
    this.setCooldown(3, this.cooldown_max4);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet bd = new BreakDefense(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if ((ennemi.possedeEffet(inv) || ennemi.possedeEffet(imm)) && this.cooldown[1] == 0) {
      res = 2;
    } else if ((ennemi.possedeEffet(inv) || ennemi.possedeEffet(imm)) && this.cooldown[1] != 0) {
      res = 1;
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else if (this.cooldown[3] == 0) {
      res = 4;
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
    } else if (!this.possedeEffet(new BuffAttaque(0)) && this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if (this.cooldown[3] == 0) {
      cibleSort[1] = 4;
    } else if (this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Zaiross
  *@return la description de Zaiross
  */
  public String toString() {
    return "Votre personnage est un Zaiross et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zaiross
  *@return la description du sort n°1 de Zaiross
  */
  public String descriptionSort1() {
    return "Tire une flêche de feu sur l'ennemi et lui inflige des dégats (cooldown = 0)";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zaiross
  *@return la description du sort n°1 de Zaiross
  */
  public String descriptionSort2() {
    return "Ton corps s'enflamme pour augmenter ton attaque <Buff_A> pendant 3 tours. (cooldown = 3)";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zaiross
  *@return la description du sort n°1 de Zaiross
  */
  public  String descriptionSort3() {
    return "3 météores enflamés s'abattent sur les ennemis pour leur infliger des dégats. Chaque météores applique un dégat continu de 2 tours avec 50% de chance (cooldown = 2)";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zaiross
  *@return la description du sort n°1 de Zaiross
  */
  public String descriptionSort4() {
    return "Attaque les ennemis avec un souffle ardent pour leur infliger d'énorme dégats et retarder le cooldown de leurs compétences de 1 tour (cooldown = 5)";
  }
}
