/*
*Cette classe est une classe de Basalt
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
import effet.effetbenefique.benefiqueprofit.*;

import effet.effettour.*;
import effet.*;
import application.*;
import java.util.*;


public class Basalt extends Personnage {

  /**Constructeur de la classe Basalt qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Basalt
  *(les autres paramètres sont prédéfinis)
  */
  public Basalt(String n) {
    super(392, 101, 140, 76, n, "Charger au Combat", "Rugissement Polaire", "Piétiner l'Enfer", "Impact Lourd", 3, 4, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 3;
    this.type_sort[2] = 2;
    this.type_sort[3] = 1;
  }

  /**Méthode qui correspond au sort n°1 de Basalt
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Silence(1), 50);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Basalt
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].heal(cibles[0].getPdvMax()*30/100);
    cibles[1].heal(cibles[1].getPdvMax()*30/100);
    cibles[0].appliquerEffet(new BuffDefense(2));
    cibles[1].appliquerEffet(new BuffDefense(2));
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Basalt
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getPdvMax()*15/100 + cibles[0].getPdvMax()*20/100 - cibles[0].getDefense()*30/100;
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Basalt
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*80/100 + this.getDefense()*15/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*80/100 + this.getDefense()*15/100 - cibles[1].getDefense()*30/100;
    cibles[0].appliquerEffet(new Ralentissement(2), 75);
    cibles[1].appliquerEffet(new Ralentissement(2), 75);
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
    } else if (this.pdv < (this.pdv_max * 50 / 100) && this.cooldown[1] == 0 && !this.possedeEffet(ah)) {
      res = 2;
    } else if (ennemi.possedeEffet(imm) && ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] == 0) {
      res = 3;
    } else if (ennemi.possedeEffet(inv) && this.cooldown[1] == 0) {
      res = 1;
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
  Effet sil = new Silence(0);
  int[] cibleSort = new int[2];
  Random r = new Random();
  boolean ko = false;
  cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

  if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
    cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
    ko = true;//la cible initiale est KO donc on a changé
  }

  if (this.possedeEffet(sil)) {//si silence
    cibleSort[1] = 1;//sort 1
  } else if (this.getPdv() < this.getPdvMax()*60/100 && !this.possedeEffet(new AntiHeal(0)) && this.cooldown[1] == 0) {//si moins 60% des hp max et pas d'anti-heal
    cibleSort[0] = 3;
    cibleSort[1] = 2;//sort 2
  } else if (allieLanceur.getPdv() < allieLanceur.getPdvMax()*60/100 && allieLanceur.getPdv() != 0 && !allieLanceur.possedeEffet(new AntiHeal(0)) && this.cooldown[1] == 0) {//si allié moins 60% des hp max, pas KO et pas d'anti-heal
    cibleSort[0] = 3;
    cibleSort[1] = 2;//sort 2
  } else if ((!ennemis[0].possedeEffet(new Immunite(0)) || !ennemis[1].possedeEffet(new Immunite(0))) && this.cooldown[3] == 0) {//si un des deux ennemis pas immunisé
    cibleSort[1] = 4;//sort 4
  } else if (this.cooldown[2] == 0) {//sinon
    cibleSort[1] = 3;//sort 3
  } else {//si vraiment
    cibleSort[1] = 1;//sort 1
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


  /**Méthode qui permet de décrire un Basalt
  *@return la description de Basalt
  */
  public String toString() {
    return "Votre personnage est un Basalt et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Basalt
  *@return la description du sort n°1 de Basalt
  */
  public String descriptionSort1() {
    return "Charge vers l'ennemi et lui inflige des dégats. Le réduit au silence avec 50% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Basalt
  *@return la description du sort n°1 de Basalt
  */
  public String descriptionSort2() {
    return "Pousse un cri energisant qui soigne les alliés et augmente leurs défense pendant 2 tours (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Basalt
  *@return la description du sort n°1 de Basalt
  */
  public  String descriptionSort3() {
    return "Inflige de grands dégats en t'écrasant sur l'ennemi dont un partie dépend de tes points de vie maximums et l'autre partie de ceux de l'ennemi. (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Basalt
  *@return la description du sort n°1 de Basalt
  */
  public String descriptionSort4() {
    return "Inflige des dégats aux ennemis dont une partie en fonction de ta défense et ralenti les pendant 3 tours avec 75% de chance (cooldown = "+this.cooldown_max4+")";
  }
}
