/*
*Cette classe est une classe de Sige
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
import java.util.*;


public class Sige extends Personnage {

  private boolean isDernierSortUtilise1;

/**Constructeur de la classe Sige qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Sige
*(les autres paramètres sont prédéfinis)
*/
  public Sige(String n) {
    super(322, 145, 104, 91, n, "Taillade", "Slash des Ténèbres", "Maniement du Samouraï", "Epée du Loup Suprême", 3, 4, 0);
    this.isDernierSortUtilise1 = false;
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 2;
    this.type_sort[3] = 1;
  }

/**Méthode qui correspond au sort n°1 de Sige
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    this.sort4active = 0;
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 65);
    Random r = new Random();
    if (r.nextInt(100) < 10) {
      Combat.ajouterCommentaire("-Sige active Epée du Loup Suprême !");
      this.sort4active = 2;
      this.setShield(this.getAttaqueMax()*25/100);
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Sige
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    this.sort4active = 0;
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    Random r = new Random();
    if (CoGestionSorts.getCrit()) {
      this.appliquerEffet(new BuffVitesse(2));
    }
    this.appliquerEffet(new BuffCrit(2));
    if (r.nextInt(100) < 30) {
      Combat.ajouterCommentaire("-Sige active Epée du Loup Suprême !");
      this.sort4active = 2;
      this.setShield(this.getAttaqueMax()*25/100);
    }
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Sige
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    this.sort4active = 0;
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100  + this.getVitesse()*25/100 - cibles[0].getDefense()*30/100;
    Random r = new Random();
    cibles[0].cooldownPlusPlus();
    if (r.nextInt(100) < 50) {
      Combat.ajouterCommentaire("-Sige active Epée du Loup Suprême !");
      this.sort4active = 2;
      this.setShield(this.getAttaqueMax()*25/100);
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Sige
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    this.sort4active = 0;
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*95/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*95/100 - cibles[1].getDefense()*30/100;
    if (cibles[0].coupFatal(degatseffectue[0]) && cibles[0].getPassifRevive()) {
      cibles[0].setPassifRevive(false);
      Combat.ajouterCommentaire("-Sige a empêché "+cibles[0].getClass().getName().substring(11)+" de revenir à la vie !");
    }
    if (cibles[1].coupFatal(degatseffectue[1]) && cibles[1].getPassifRevive()) {
      cibles[1].setPassifRevive(false);
      Combat.ajouterCommentaire("-Sige a empêché "+cibles[1].getClass().getName().substring(11)+" de revenir à la vie !");
    }
    if (CoGestionSorts.getCrit()) {
      this.volDeVie(degatseffectue[0], cibles[0], 50);
      this.volDeVie(degatseffectue[1], cibles[1], 50);
    }
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (this.sort4active > 0) {
      res = 4;
    } else if (ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm)) {
      if (this.cooldown[2] == 0) {
        res = 3;
      } else if (this.cooldown[1] == 0) {
        res = 2;
      } else {
        res = 1;
      }
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else if (this.cooldown[2] == 0) {
      res = 3;
    } else {
      res = 1;
    }

    return res;
  }


  public int[] cibleSortIa(Personnage allieLanceur, Personnage[] ennemis) {
    int[] cibleSort = new int[2];
    Random r = new Random();
    Effet sil = new Silence(0);
    boolean ko = false;
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }


    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (this.sort4active > 0) {
      cibleSort[1] = 4;
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if (!this.isDernierSortUtilise1 && (this.possedeEffetNTour(new BuffVitesse(0), 2) || this.possedeEffetNTour(new BuffVitesse(0), 3))) {
      cibleSort[1] = 1;
    } else if (this.cooldown[2] == 0) {
      cibleSort[1] = 3;
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
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 2) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Sige
  *@return la description de Sige
  */
  public String toString() {
    return "Votre personnage est un Sige et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sige
  *@return la description du sort n°1 de Sige
  */
  public String descriptionSort1() {
    return "Taille l'ennemi et réduit sa défense pendant 2 tours avec 65% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sige
  *@return la description du sort n°1 de Sige
  */
  public String descriptionSort2() {
    return "Envoie un slash ténébreux sur l'ennemi lui infligeant des dégats et augmentant tes taux de critiques pendant 2 tours. Si cette attaque est un coup critique tu gagne un bonus de vitesse pendant 2 tours (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sige
  *@return la description du sort n°1 de Sige
  */
  public  String descriptionSort3() {
    return "Attaque l'ennemi avec la rapidité d'un samouraï pour augmenter les cooldowns de l'ennemi de 1 tour. Une partie des dégats dépend de ta vitesse (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sige
  *@return la description du sort n°1 de Sige
  */
  public String descriptionSort4() {
    return "Attaque les ennemis avec ta compétence secrète en ignorant toute invincibilité et empêche chaque ennemi de revenir à la vie. De plus, tu te soignes de la moitié des dégats infligés si l'attaque est un coup critique. (cooldown = "+this.cooldown_max4+")";
  }
}
