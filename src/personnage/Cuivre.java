/*
*Cette classe est une classe de Cuivre
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import java.util.*;
import effet.effetbenefique.benefiqueprofit.*;

import application.*;
import effet.effettour.*;
import effet.*;


public class Cuivre extends Personnage {

/**Constructeur de la classe Cuivre qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Cuivre
*(les autres paramètres sont prédéfinis)
*/
  public Cuivre(String n) {
    super(330, 104, 157, 78, n, "Ecrasement", "Marteau Tournoyant", "Coup de Tonnerre", "Armure de Glace", 3, 3, 0, true, true, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 2;
    this.type_sort[3] = 0;
  }

/**Méthode qui correspond au sort n°1 de Cuivre
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*60/100 + this.getDefense()*15/100 - cibles[0].getDefense()*30/100;
    if (cibles[0].possedeEffet(new BreakDefense(0))) {
      cibles[0].appliquerEffet(new Ralentissement(2), 50);
    }
    cibles[0].appliquerEffet(new Etourdissement(1), 15);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Cuivre
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*60/100 + this.getDefense()*40/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*60/100 + this.getDefense()*40/100 - cibles[1].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakDefense(3), 75);
    cibles[1].appliquerEffet(new BreakDefense(3), 75);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Cuivre
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getDefense()*80/100 - cibles[0].getDefense()*30/100;
    if (cibles[0].getDefense() < this.getDefense() / 2) {
      degatseffectue[0] = this.getDefense()*80/100;
      Combat.ajouterCommentaire("-Cuivre a ignoré la défense de "+cibles[0].getClass().getName().substring(11));
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Cuivre
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    int shield = this.cleanDegatsContinus();
    this.setShield(shield * this.getPdvMax() * 7 / 100);
    this.setCooldown(3, this.cooldown_max4);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if ((ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) || ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] == 0) {
      res = 3;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] != 0) {
      res = 1;
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else if (!ennemi.possedeEffet(new Ralentissement(0)) && ennemi.possedeEffet(new BreakDefense(0))) {
      res = 1;
    } else if (this.cooldown[2] == 0) {
      res = 3;
    } else {
      res = 1;
    }

    return res;
  }


  public int[] cibleSortIa(Personnage allieLanceur, Personnage[] ennemis) {
    Effet sil = new Silence(0);
    int[] cibleSort = new int[2];
    boolean ko = false;
    Random r = new Random();
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }


    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (this.cooldown[1] == 0 && !(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0)))) {
      cibleSort[1] = 2;
    } else if (ennemis[cibleSort[0]-1].getDefense() < this.getDefense() / 2 && this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else if (ennemis[this.changementCible(cibleSort[0])-1].getDefense() < this.getDefense() / 2 && !ko && this.cooldown[2] == 0) {
      cibleSort[0] = this.changementCible(cibleSort[0]);
      cibleSort[1] = 3;
    } else if (this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else if (ennemis[cibleSort[0]-1].possedeEffet(new BreakDefense(0))) {
      cibleSort[1] = 1;
    } else if (ennemis[this.changementCible(cibleSort[0])-1].possedeEffet(new BreakDefense(0)) && !ko) {
      cibleSort[0] = this.changementCible(cibleSort[0]);
      cibleSort[1] = 1;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Cuivre
  *@return la description de Cuivre
  */
  public String toString() {
    return "Votre personnage est un Cuivre et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cuivre
  *@return la description du sort n°1 de Cuivre
  */
  public String descriptionSort1() {
    return "Ecrase l'ennemi avec un marteau dont une partie des dégats dépends de votre défense et l'étourdis avec 15% de chance. Si la défense de l'ennemi a été réduite alors cette attaque lui baissera sa vitesse pendant 2 tours avec 50% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cuivre
  *@return la description du sort n°1 de Cuivre
  */
  public String descriptionSort2() {
    return "Tourbillonne pour frapper les ennemis et réduire leur défense pendant 2 tours avec 75% de chance. Une partie des dégats correspond à un pourcentage de votre défense (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cuivre
  *@return la description du sort n°1 de Cuivre
  */
  public  String descriptionSort3() {
    return "Un coup de tonnerre s'abat sur l'ennemi. l'intégralité des dégats de cette attaque dépende de votre défense. Si la défense de l'ennemi est inférieure à la moitié de la votre, cette attaque ignore la défense de l'ennemi (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cuivre
  *@return la description du sort n°1 de Cuivre
  */
  public String descriptionSort4() {
    return "Chaque tour, converti tous les dégats continus que vous possédés en un bouclier dont le montant correspond à un pourcentage de vos points de vie (les gains du bouclier se cumulent si plusieurs dégats continus sont supprimés)";
  }
}
