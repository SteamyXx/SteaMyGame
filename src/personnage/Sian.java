/*
*Cette classe est une classe de Sian
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
import java.util.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import effet.effettour.*;
import effet.*;
import java.util.*;


public class Sian extends Personnage {

/**Constructeur de la classe Sian qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Sian
*(les autres paramètres sont prédéfinis)
*/
  public Sian(String n) {
    super(336, 159, 104, 76, n, "Carte Volante", "Bombe Surprise", "Boîte Surprise", "Magie Régénérante", 3, 3, 0, true, false, true);
    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 1;
    this.type_sort[3] = 0;
  }

/**Méthode qui correspond au sort n°1 de Sian
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new AntiHeal(2), 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Sian
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new Bombe(2, this.getAttaque()*50/100), 100);
    cibles[1].appliquerEffet(new Bombe(2, this.getAttaque()*50/100), 100);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Sian
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*85/100 - cibles[1].getDefense()*30/100;
    Random r = new Random();
    int random = r.nextInt(3);
    if (random == 0) {
      cibles[0].appliquerEffet(new Etourdissement(1), 25);
    } else if (random == 1) {
      cibles[0].appliquerEffet(new BreakAttaque(3), 50);
    } else if (random == 2) {
      cibles[0].appliquerEffet(new Ralentissement(2), 75);
    }
    random = r.nextInt(3);
    if (random == 0) {
      cibles[1].appliquerEffet(new Etourdissement(1), 25);
    } else if (random == 1) {
      cibles[1].appliquerEffet(new BreakAttaque(3), 50);
    } else if (random == 2) {
      cibles[1].appliquerEffet(new Ralentissement(2), 75);
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Sian
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    this.setPdv(this.getPdvMax()*30/100);
    this.appliquerEffet(new Immunite(1));
    this.appliquerEffet(new Invincibilite(1));
    this.setPassifRevive(false);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet ah = new AntiHeal(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv) && this.cooldown[1] == 0) {
      res = 2;
    } else if (ennemi.possedeEffet(inv) && this.cooldown[1] != 0) {
      res = 1;
    } else if (this.cooldown[1] == 0 && this.passif_revive) {
      res = 2;
    } else if (this.cooldown[1] == 0 && !this.passif_revive && ennemi.getVitesse() < this.vitesse) {
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
    } else if (!(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0))) && this.cooldown[1] == 0) {
      cibleSort[1] = 2;
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

  /**Méthode qui permet de décrire un Sian
  *@return la description de Sian
  */
  public String toString() {
    return "Votre personnage est un Sian et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sian
  *@return la description du sort n°1 de Sian
  */
  public String descriptionSort1() {
    return "Lance une carte aiguisé pour infligé des dégats à l'ennemi. Perturbe le soin <Anti-H> de l'ennemi pendant 2 tour avec 75% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sian
  *@return la description du sort n°1 de Sian
  */
  public String descriptionSort2() {
    return "Applique sur l'ennemi à coup sûr une bombe qui explosera dans 2 tours. L'explosion infligera des dégats à l'ennemi et l'étourdira <Stun> avec 25% de chance (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sian
  *@return la description du sort n°1 de Sian
  */
  public  String descriptionSort3() {
    return "Attaque les ennemis avec une boîte surprise. Cette dernière pourra aléatoirement soit étourdir <Stun> l'ennemi, soit réduire son attaque <Brk-A> pendant 3 tours, soit réduire sa vitesse <Slow> pendant 2 tours avec respectivement 25, 50 et 75% de chance de réussite (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Sian
  *@return la description du sort n°1 de Sian
  */
  public String descriptionSort4() {
    return "Si vous mourrez, vous réanime avec 30% de vos points de vie maximums et êtes immunisé et invincible pendant 1 tour (Activable 1 seul fois)";
  }
}
