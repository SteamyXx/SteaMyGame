/*
*Cette classe est une classe de Woonhak
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import effet.effetnocif.*;
import java.util.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import application.*;
import effet.effettour.*;
import effet.*;


public class Woonhak extends Personnage {

/**Constructeur de la classe Woonhak qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Woonhak
*(les autres paramètres sont prédéfinis)
*/
  public Woonhak(String n) {
    super(367, 140, 110, 83, n, "Fausse Attaque", "Vague Colérique", "Destruction du Mal", "Visite Secrète", 3, 4, 5);
    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 2;
    this.type_sort[3] = 4;
  }

/**Méthode qui correspond au sort n°1 de Woonhak
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakAttaque(1), 75);
    if (CoGestionSorts.getCrit()) {
      cibles[0].appliquerEffet(new Ralentissement(1), 75);
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Woonhak
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*65/100 + this.getPdv()*10/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*65/100 + this.getPdv()*10/100 - cibles[1].getDefense()*30/100;
    for (int i = 0; i<2; i++) {
      cibles[i].appliquerEffet(new Ralentissement(2), 50);
      if (cibles[i].possedeEffet(new Ralentissement(0))) {
        cibles[i].appliquerEffet(new Etourdissement(1), 25);
      }
      cibles[i].appliquerEffet(new Ralentissement(2), 50);
      if (cibles[i].possedeEffet(new Ralentissement(0))) {
        cibles[i].appliquerEffet(new Etourdissement(1), 25);
      }
    }

    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Woonhak
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*22/100 - cibles[0].getDefense()*8/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 25);
    degatseffectue[0] += this.getAttaque()*22/100 - cibles[0].getDefense()*8/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 25);
    degatseffectue[0] += this.getAttaque()*22/100 - cibles[0].getDefense()*7/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 25);
    degatseffectue[0] += this.getAttaque()*22/100 - cibles[0].getDefense()*7/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 25);
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Woonhak
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    int cible = 0;
    if (ennemisCibles[1].getVitesse() > ennemisCibles[0].getVitesse()) {
      cible = 1;
    }
    ennemisCibles[cible].appliquerEffet(new Etourdissement(1), 75);
    cibles[0].cleanToutLesEffetNocif();
    cibles[0].heal(cibles[0].getPdvMax()*15/100);
    cibles[0].appliquerEffet(new BuffVitesse(2));
    cibles[0].appliquerEffet(new Regeneration(2));
    this.setCooldown(3, this.cooldown_max4);
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
    } else if (ennemi.possedeEffet(inv) && this.cooldown[3] == 0 && this.pdv < (this.pdv_max*60/100) && !this.possedeEffet(ah)) {
      res = 4;
    } else if (ennemi.possedeEffet(inv) && (this.cooldown[3] != 0 || this.pdv >= (this.pdv_max*60/100) || this.possedeEffet(ah))) {
      res = 1;
    } else if (ennemi.possedeEffet(imm)) {
      res = 1;
    } else if (this.cooldown[1] == 0 && this.pdv > (this.pdv_max*60/100)) {
      res = 2;
    } else if (this.cooldown[3] == 0 && this.pdv < (this.pdv_max*60/100) && !this.possedeEffet(ah)) {
      res = 4;
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
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if (this.cooldown[3] == 0 && allieLanceur.getPdv() < allieLanceur.getPdvMax()*50/100 && allieLanceur.getPdv() != 0) {
      cibleSort[0] = 4;
      cibleSort[1] = 4;
    } else if (this.cooldown[3] == 0 && this.getPdv() < this.getPdvMax()*50/100) {
      cibleSort[0] = 3;
      cibleSort[1] = 4;
    } else if (this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }


  /**Méthode qui permet de décrire un Woonhak
  *@return la description de Woonhak
  */
  public String toString() {
    return "Votre personnage est un Woonhak et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Woonhak
  *@return la description du sort n°1 de Woonhak
  */
  public String descriptionSort1() {
    return "Une attaque dûr à prévoir inflige des dégats à l'ennemi et réduit son attaque pendant 1 tour avec 75% de chance. Wi cette attaque est un coup critique, réduit aussi sa vitesse pendant 1 tour avec 75% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Woonhak
  *@return la description du sort n°1 de Woonhak
  */
  public String descriptionSort2() {
    return "Une vague déferle sur les ennemi et les attaque 2 fois. Une partie de dégats dépends de vos points de vie actuels. De plus, chaque attaque réduit leur vitesse avec 50% de chance puis si l'ennemi possède un effet de ralentissement, l'étourdit avec 25% de chance (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Woonhak
  *@return la description du sort n°1 de Woonhak
  */
  public  String descriptionSort3() {
    return "Roue l'ennemi de 4 coups fatals infligeant beucoup de dégats et dont chaque coup à 25% de chance de diminuer la défense de l'ennemi pendant 2 tours (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Woonhak
  *@return la description du sort n°1 de Woonhak
  */
  public String descriptionSort4() {
    return "Passe derrière l'ennemi le plus rapide pour l'étourdir avec 75% de chance. De plus enlève tous les effets nocifs d'un allié, le soigne de 15% de ses points de vie maximums, augmente sa vitesse et lui confère un effet de régénération pendant 2 tours (cooldown = "+this.cooldown_max4+")";
  }
}
