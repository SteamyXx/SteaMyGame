/*
*Cette classe est une classe de Imleryth
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

import effet.effettour.*;
import effet.*;
import application.*;


public class Imleryth extends Personnage {

  /**Constructeur de la classe Imleryth qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Imleryth
  *(les autres paramètres sont prédéfinis)
  */
  public Imleryth(String n) {
    super(400, 120, 115, 85, n, "Massue de Glace", "Froid Bénéfique", "Blizzard Eternel", "Criogénisation", 3, 4, 5);
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 1;
    this.type_sort[3] = 5;
  }

  /**Méthode qui correspond au sort n°1 de Imleryth
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 50);
    cibles[0].appliquerEffet(new BreakAttaque(1), 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Imleryth
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Ralentissement(2), 75);
    ennemisCibles[0].appliquerEffet(new BuffAttaque(2));
    ennemisCibles[1].appliquerEffet(new BuffAttaque(2));
    ennemisCibles[0].appliquerEffet(new BuffCrit(2));
    ennemisCibles[1].appliquerEffet(new BuffCrit(2));
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Imleryth
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*60/100 + cibles[0].getPdv()*15/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*60/100 + cibles[1].getPdv()*15/100 - cibles[1].getDefense()*30/100;
    cibles[0].appliquerEffet(new Silence(2), 35);
    cibles[1].appliquerEffet(new Silence(2), 35);
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Imleryth
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new Regeneration(3));
    cibles[1].appliquerEffet(new Regeneration(3));
    this.cleanToutLesEffetNocif();
    this.heal((this.getPdvMax() - this.getPdv())*40/100);
    this.appliquerEffet(new Etourdissement(2));
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
    } else if (this.pdv < (this.pdv_max * 35 / 100) && this.cooldown[3] == 0) {
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
    boolean ko = false;
    Effet sil = new Silence(0);
    int[] cibleSort = new int[2];
    Random r = new Random();
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }

    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (this.getPdv() < this.getPdvMax()*30/100 && this.cooldown[3] == 0 && !this.possedeEffet(new AntiHeal(0))) {
      cibleSort[1] = 3;//sort 3
      cibleSort[0] = 3;
    } else if (this.cooldown[2] == 0 && !(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0)))) {
      cibleSort[1] = 3;//sort 3
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;//sort 2
    } else {
      cibleSort[1] = 1;//sort 1
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 2) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Imleryth
  *@return la description de Imleryth
  */
  public String toString() {
    return "Votre personnage est un Imleryth et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Imleryth
  *@return la description du sort n°1 de Imleryth
  */
  public String descriptionSort1() {
    return "Charge l'ennemi avec votre massue et lui inflige des dégats. Réduit l'attaque <Brk_A> de l'ennemi pendant 1 tours avec 75% de chance et sa défense pendant 2 tours avec 50% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Imleryth
  *@return la description du sort n°1 de Imleryth
  */
  public String descriptionSort2() {
    return "Attaque un ennemi et réduit sa vitesse <Slow> pendant 3 tours avec 75% de chance puis augmente la défense des alliés pendant 2 tour. (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Imleryth
  *@return la description du sort n°1 de Imleryth
  */
  public  String descriptionSort3() {
    return "Un blizzard s'abat sur les ennemis leur infligeant des dégats dont une partie correspond à 15% de leurs points de vie actuels. De plus, les réduit au silence <Sil> pendant 2 tours avec 35% de chance (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Imleryth
  *@return la description du sort n°1 de Imleryth
  */
  public String descriptionSort4() {
    return "Vous rentrez en criogénisation ce qui vous étourdis pendant 1 tour. Ce sort vous soigne en fonction de vos points de vie manquants, supprime tous vos effets nocifs puis confère aux alliés un effet de régénération <Regen> de 3 tours (cooldown = "+this.cooldown_max4+")";
  }
}
