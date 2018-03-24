/*
*Cette classe est une classe de Icar
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

import application.*;
import effet.effettour.*;
import effet.*;
import java.util.*;


public class Icaru extends Personnage {


/**Constructeur de la classe Icar qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Icar
*(les autres paramètres sont prédéfinis)
*/
  public Icaru(String n) {
    super(399, 124, 126, 85, n, "Eraflure", "Saisir", "Riposte", "Mobiliser", 3, 4, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 2;
    this.type_sort[3] = 3;
  }

/**Méthode qui correspond au sort n°1 de Icar
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 50);
    Random r = new Random();
    if (r.nextInt(100) < 25) {
      degatseffectue[0] += this.sort1(cibles, ennemisCibles)[0]*75/100;
      Combat.ajouterCommentaire("-Icaru rejoue !");
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Icaru
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()/2 + this.getPdv()*15/100 - cibles[0].getDefense()*30/100;
    EffetBenefique.transfere(cibles[0], this, 100);
    cibles[0].cleanToutLesEffetsBenefiques(75);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Icaru
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    this.appliquerEffet(new ContreAttaque(2));
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Icaru
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new BuffVitesse(2));
    cibles[1].appliquerEffet(new BuffVitesse(2));
    cibles[0].heal(cibles[0].getPdvMax()*30/100);
    cibles[1].heal(cibles[1].getPdvMax()*30/100);
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
    } else if (ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm) && this.cooldown[1] == 0) {
      res = 2;
    } else if (ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm) && this.cooldown[1] != 0 && this.cooldown[3] == 0 && this.pdv < this.pdv_max*60/100 && !this.possedeEffet(ah)) {
      res = 4;
    } else if ((ennemi.possedeEffet(inv) || ennemi.possedeEffet(imm)) && this.cooldown[1] == 0) {
      res = 2;
    } else if (ennemi.possedeEffet(inv) && this.cooldown[1] != 0) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && !ennemi.possedeEffet(inv) && this.cooldown[2] == 0) {
      res = 3;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] != 0) {
      res = 1;
    } else if (this.cooldown[3] == 0 && this.pdv < this.pdv_max*60/100 && !this.possedeEffet(ah)) {
      res = 4;
    } else if (this.cooldown[1] == 0 && ennemi.possedeEffetBenefique()) {
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
    } else if (this.getPdv() < this.getPdvMax()*60/100 && !this.possedeEffet(new AntiHeal(0)) && this.cooldown[3] == 0) {//si moins 60% des hp max et pas d'anti-heal
      cibleSort[0] = 3;
      cibleSort[1] = 4;//sort 4
    } else if (allieLanceur.getPdv() < allieLanceur.getPdvMax()*60/100 && allieLanceur.getPdv() != 0 && !allieLanceur.possedeEffet(new AntiHeal(0)) && this.cooldown[3] == 0) {//si allié moins 60% des hp max, pas KO et pas d'anti-heal
      cibleSort[0] = 3;
      cibleSort[1] = 4;//sort 4
    } else if (ennemis[cibleSort[0]-1].nbrEffetsBenefiques() > 0 && this.cooldown[3] == 0) {
      cibleSort[1] = 2;//sort 2
    } else if (this.cooldown[3] == 0) {
      cibleSort[1] = 3;//sort 3
    } else {
      cibleSort[1] = 1;//sort 1
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 2) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Icaru
  *@return la description de Icaru
  */
  public String toString() {
    return "Votre personnage est un Icaru et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Icaru
  *@return la description du sort n°1 de Icaru
  */
  public String descriptionSort1() {
    return "Attaque l'ennemi avec des griffes géantes. Diminue la défense <Brk_D> de l'ennemi pendant 2 tours avec 50% de chance et relance ce sort avec 25% de chance (les dégats des relances correspondent à 75% des dégats normaux) (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Icaru
  *@return la description du sort n°1 de Icaru
  */
  public String descriptionSort2() {
    return "Attaque l'ennemi et lui vole un effet bénéfique. Après le vole, retire tous les autres effets bénéfiques de l'ennemi. (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Icaru
  *@return la description du sort n°1 de Icaru
  */
  public  String descriptionSort3() {
    return "Attaque l'ennemi et contre-attaque pendant 2 tours (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Icaru
  *@return la description du sort n°1 de Icaru
  */
  public String descriptionSort4() {
    return "Soigne les alliés de 30% de leurs points de vie maximum et augmente leur vitesse pendant 2 tours (cooldown = "+this.cooldown_max4+")";
  }
}
