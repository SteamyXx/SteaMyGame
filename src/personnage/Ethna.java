/*
*Cette classe est une classe de Ethna
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;
import java.util.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import application.*;
import effet.effettour.*;
import effet.*;


public class Ethna extends Personnage {

  private boolean isDernierSortUtilise1;

/**Constructeur de la classe Ethna qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Ethna
*(les autres paramètres sont prédéfinis)
*/
  public Ethna(String n) {
    super(312, 151, 110, 81, n, "Feu Follet", "Mots Glaçants", "Voleur d'Âme", "Ombre Portée", 3, 4, 4);
    this.isDernierSortUtilise1 = false;
    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 2;
    this.type_sort[3] = 2;
  }

/**Méthode qui correspond au sort n°1 de Ethna
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*78/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new DegatContinu(3), 15);
    cibles[0].appliquerEffet(new DegatContinu(3), 15);
    cibles[0].appliquerEffet(new DegatContinu(3), 15);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Ethna
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*75/100 - cibles[1].getDefense()*30/100;
    cibles[0].cleanToutLesEffetsBenefiques(100);
    cibles[1].cleanToutLesEffetsBenefiques(100);
    cibles[0].appliquerEffet(new BreakDefense(2), 50);
    cibles[1].appliquerEffet(new BreakDefense(2), 50);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Ethna
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    int plus = degatseffectue[0]-(this.getPdvMax()-this.getPdv());
    this.volDeVie(degatseffectue[0], cibles[0], 100);
    if (plus > 0 && !this.possedeEffet(new AntiHeal(0))) {
      this.setShield(plus/2);
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Ethna
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*82/100 - cibles[0].getDefense()*30/100;
    if (cibles[0].possedeEffet(new DegatContinu(0))) {
      CoGestionSorts.setCrit(true);
    }
    if (CoGestionSorts.getCrit()) {
      cibles[0].appliquerEffet(new Etourdissement(1), 75);
    }
    this.setCooldown(3, this.cooldown_max4);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet ah = new AntiHeal(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    boolean trouve = false;
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm)) {
      if (!this.possedeEffet(ah) && this.cooldown[1] == 0) {
        res = 2;
      } else {
        res = 1;
      }
    } else if (ennemi.possedeEffet(inv)) {
      if (this.cooldown[3] == 0 && ennemi.possedeEffet(new DegatContinu(0))) {
        res = 4;
      } else {
        res = 1;
      }
    } else if (this.cooldown[1] == 0 && !this.possedeEffet(ah)) {
      res = 2;
    } else if (this.cooldown[2] == 0) {
      res = 3;
    } else if (this.cooldown[3] == 0 && ennemi.possedeEffet(new DegatContinu(0))) {
      res = 4;
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
    } else if (this.cooldown[2] == 0 && !this.possedeEffet(new AntiHeal(0))) {
      cibleSort[1] = 3;
    } else if (ennemis[cibleSort[0]-1].nbrEffetsBenefiques() >= 2 && this.cooldown[2] == 0) {
      cibleSort[1] = 2;
    } else if (!ko && ennemis[this.changementCible(cibleSort[0])-1].nbrEffetsBenefiques() >= 2 && this.cooldown[2] == 0) {
      cibleSort[1] = 2;
    } else if (!this.isDernierSortUtilise1 && ennemis[cibleSort[0]-1].nbrDegatsContinus() > 0) {
      cibleSort[1] = 1;
    } else if (this.cooldown[3] == 0) {
      cibleSort[1] = 4;
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else {
      cibleSort[1] = 1;
    }

    if (cibleSort[1] == 1) {
      this.isDernierSortUtilise1 = true;
    } else {
      this.isDernierSortUtilise1 = false;
    }

    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 4) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Ethna
  *@return la description de Ethna
  */
  public String toString() {
    return "Votre personnage est un Ethna et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ethna
  *@return la description du sort n°1 de Ethna
  */
  public String descriptionSort1() {
    return "Attaque l'ennemi 3 fois avec une lumière chattoyante avec pour chaque coup 15% de chance d'appliquer un dégat continu de 3 tours sur l'ennemi (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ethna
  *@return la description du sort n°1 de Ethna
  */
  public String descriptionSort2() {
    return "Attaque les ennemis, réduit leur défense pendant 2 tours avec 50% de chance et supprime tous leurs effets bénéfiques (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ethna
  *@return la description du sort n°1 de Ethna
  */
  public  String descriptionSort3() {
    return "Lance un sort volant l'âme de l'ennemi pour infliger des dégats et récupérer la totalité des dégats infligés. En plus, l'excès de points de vie soignés est converti en un bouclier d'un montant équivalent à la moitié de l'excès (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ethna
  *@return la description du sort n°1 de Ethna
  */
  public String descriptionSort4() {
    return "Une ombre maléfique inflige des dégats à l'ennemi et l'étourdis avec 75% de chance si cette attaque est un coup critique. Si l'ennemi souffre d'au moins un dégats continu, l'attaque sera un coup critique à coup sûr (cooldown = "+this.cooldown_max4+")";
  }
}
