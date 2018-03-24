/*
*Cette classe est une classe de Ritesh
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import java.util.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import effet.effettour.*;
import effet.*;
import application.*;


public class Ritesh extends Personnage {

  /**Constructeur de la classe Ritesh qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Ritesh
  *(les autres paramètres sont prédéfinis)
  */
  public Ritesh(String n) {
    super(412, 102, 135, 80, n, "Charge Lourde", "Armure Gluante", "Coup Ecrasant", "Revitalisation", 3, 2, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 4;
    this.type_sort[2] = 2;
    this.type_sort[3] = 4;
  }

  /**Méthode qui correspond au sort n°1 de Ritesh
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakAttaque(1), 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Ritesh
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new BuffDefense(2), 100);
    ennemisCibles[0].appliquerEffet(new Ralentissement(3), 50);
    ennemisCibles[1].appliquerEffet(new Ralentissement(3), 50);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Ritesh
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getPdvMax()*25/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Silence(1), 50);
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Ritesh
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new Regeneration(2));
    cibles[0].heal((cibles[0].getPdvMax() - cibles[0].getPdv())*35/100 + this.getPdvMax()/10);
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
    } else if (this.pdv < (this.pdv_max * 30 / 100) && this.cooldown[3] == 0 && !this.possedeEffet(ah)) {
      res = 4;
    } else if (ennemi.possedeEffet(imm) && this.pdv < (this.pdv_max * 30 / 100) && this.cooldown[1] == 0 && this.cooldown[3] != 0) {
      res = 2;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[3] != 0) {
      res = 1;
    } else if (ennemi.possedeEffet(inv) && this.cooldown[1] == 0) {
      res = 2;
    } else if (ennemi.possedeEffet(inv) && this.cooldown[1] != 0) {
      res = 1;
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
    } else if (this.cooldown[3] == 0 && allieLanceur.getPdv() != 0 && allieLanceur.getPdv() < allieLanceur.getPdvMax()*35/100 && !allieLanceur.possedeEffet(new AntiHeal(0))) {
      cibleSort[0] = 4;
      cibleSort[1] = 4;
    } else if (this.cooldown[3] == 0 && this.getPdv() < this.getPdvMax()*35/100 && !this.possedeEffet(new AntiHeal(0))) {
      cibleSort[0] = 3;
      cibleSort[1] = 4;
    } else if (this.cooldown[1] == 0 && allieLanceur.getPdv() != 0) {
      cibleSort[0] = 4;
      cibleSort[1] = 2;
    } else if (this.cooldown[1] == 0) {
      cibleSort[0] = 3;
      cibleSort[1] = 2;
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

  /**Méthode qui permet de décrire un Ritesh
  *@return la description de Ritesh
  */
  public String toString() {
    return "Votre personnage est un Ritesh et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ritesh
  *@return la description du sort n°1 de Ritesh
  */
  public String descriptionSort1() {
    return "Charge l'ennemi et lui inflige des dégats. Réduit l'attaque <Brk_A> de l'ennemi pendant 1 tours avec 75% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ritesh
  *@return la description du sort n°1 de Ritesh
  */
  public String descriptionSort2() {
    return "Augmente la défense <Buff_D> d'un allié pendant 2 tours et réduit la vitesse <Slow> des ennemis pendant 3 tours avec 50% de chance. (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ritesh
  *@return la description du sort n°1 de Ritesh
  */
  public  String descriptionSort3() {
    return "Inflige des dégats équivalents à 25% de vos points de vie maximums. Réduit au Silence <Sil> l'ennemi pendant 1 tours avec 50% de chance (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Ritesh
  *@return la description du sort n°1 de Ritesh
  */
  public String descriptionSort4() {
    return "Invoque un sort de revitalisation qui soigne un allié en fonction de ses points de vie manquants et de vos points de vie maximums. De plus, lui confère un effet de régénération <Regen> de 2 tours (cooldown = "+this.cooldown_max4+")";
  }
}
