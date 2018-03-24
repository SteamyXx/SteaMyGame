/*
*Cette classe est une classe de Cichlid
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

import application.*;
import java.util.*;
import effet.effettour.*;
import effet.*;

import java.util.*;


public class Cichlid extends Personnage {

/**Constructeur de la classe Cichlid qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Cichlid
*(les autres paramètres sont prédéfinis)
*/
  public Cichlid(String n) {
    super(387, 126, 109, 80, n, "Bulle de Mana", "Bouclier des Profondeurs", "Espoirs Écrasée", "Sérénité", 3, 4, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 4;
    this.type_sort[2] = 2;
    this.type_sort[3] = 3;
  }

/**Méthode qui correspond au sort n°1 de Cichlid
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*60/100 + this.getPdvMax()*5/100 - cibles[0].getDefense()*30/100;
    EffetBenefique.clean(cibles[0], 50);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Cichlid
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    if (cibles[0].cleanToutLesEffetNocif() == 0) {
      cibles[0].appliquerEffet(new BuffDefense(2));
    }
    cibles[0].setShield(this.getPdvMax()*20/100);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Cichlid
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    EffetBenefique.clean(cibles[0], 100);
    cibles[0].appliquerEffet(new BreakDefense(3), 65);
    cibles[0].cooldownPlusPlus();
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Cichlid
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].heal(cibles[0].getPdvMax()*20/100 + (cibles[0].getPdvMax()-cibles[0].getPdv()) * 10 / 100);
    cibles[1].heal(cibles[1].getPdvMax()*20/100 + (cibles[1].getPdvMax()-cibles[1].getPdv()) * 10 / 100);
    cibles[0].appliquerEffet(new Immunite(2));
    cibles[1].appliquerEffet(new Immunite(2));
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
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else if (this.pdv < (this.pdv_max * 40 / 100) && this.cooldown[3] == 0 && !this.possedeEffet(ah)) {
      res = 4;
    } else if (this.possedeEffet(inv) && this.possedeEffet(imm)) {
      res = 1;
    } else if ((this.possedeEffet(inv) || this.possedeEffet(imm)) && this.cooldown[2] == 0) {
      res = 3;
    } else if (this.cooldown[2] == 0) {
      res = 3;
    } else {
      res = 1;
    }

    return res;
  }


  public int[] cibleSortIa(Personnage allieLanceur, Personnage[] ennemis) {
    int[] cibleSort = new int[2];
    Effet sil = new Silence(0);
    Random r = new Random();
    boolean ko = false;
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }


    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (allieLanceur.nbrEffetsNocifs() >= 2 && allieLanceur.getPdv() != 0 && this.cooldown[1] == 0) {//si allié possède plus de 2 effets nocifs
      cibleSort[0] = 4;
      cibleSort[1] = 2;//sort 2
    } else if (this.nbrEffetsNocifs() >= 2 && this.cooldown[1] == 0) {//si possède plus de 2 effets nocifs
      cibleSort[0] = 3;
      cibleSort[1] = 2;//sort 2
    } else if (allieLanceur.getPdv() < allieLanceur.getPdvMax()*50/100 && allieLanceur.getPdv() != 0 && !allieLanceur.possedeEffet(new AntiHeal(0)) && this.cooldown[3] == 0) {//si allié a moins de 50% de ses Hp max et pas KO et pas anti-heal
      cibleSort[0] = 3;
      cibleSort[1] = 4;//sort 4
    } else if (this.getPdv() < this.getPdvMax()*50/100 && !this.possedeEffet(new AntiHeal(0)) && this.cooldown[3] == 0) {//si moins de 50% des Hp max et pas anti-heal
      cibleSort[0] = 3;
      cibleSort[1] = 4;//sort 4
    } else if (this.cooldown[1] == 0) {//sinon
      cibleSort[0] = 4;
      cibleSort[1] = 2;//sort 2
    } else if (this.cooldown[3] == 0) {//sinon
      cibleSort[0] = 3;
      cibleSort[1] = 4;//sort 4
    } else if (this.cooldown[2] == 0) {//sinon
      cibleSort[1] = 3;//sort 3
    } else {//si vraiment
      cibleSort[1] = 1;//sort 1
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Cichlid
  *@return la description de Cichlid
  */
  public String toString() {
    return "Votre personnage est un Cichlid et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cichlid
  *@return la description du sort n°1 de Cichlid
  */
  public String descriptionSort1() {
    return "Éclate une bulle de mana sur l'ennemi avec 50% de chance de lui enlever un de ces effets nocifs. Une partie des dégats dépend de vos points de vie maximums (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cichlid
  *@return la description du sort n°1 de Cichlid
  */
  public String descriptionSort2() {
    return "Entoure l'allié ciblé d'un bouclier d'air pour supprimer tout ses effets nocifs et lui procurer un bouclier équivalent à 20% de tes points de vie maximums. Si la cible n'avait aucun effet nocif, augmente sa défense pendant 2 tours (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cichlid
  *@return la description du sort n°1 de Cichlid
  */
  public  String descriptionSort3() {
    return "Attaque trois fois l'ennemi, les attaques vont respectivement : enlever un des effets nocifs de l'ennemi, réduire sa défense pendant 3 tours avec 65% de chance et augmenter ses cooldowns de un tour. (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cichlid
  *@return la description du sort n°1 de Cichlid
  */
  public String descriptionSort4() {
    return "Entre dans un état de sérénité total pour redonner aux alliés 15% de tes points de vies maximums. Une partie du soin dépend des points de vie manquant des alliés. De plus ce sort leur procure l'immunité pendant 2 tours (cooldown = "+this.cooldown_max4+")";
  }
}
