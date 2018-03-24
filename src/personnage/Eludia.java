/*
*Cette classe est une classe de Eludia
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
import java.util.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import effet.effettour.*;
import effet.*;
import java.util.*;
import application.*;


public class Eludia extends Personnage {

/**Constructeur de la classe Eludia qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Eludia
*(les autres paramètres sont prédéfinis)
*/
  public Eludia(String n) {
    super(318, 154, 103, 76, n, "Souffle Arcane", "Nova de Flamme", "Chemin de Feu", "Eternité", 3, 4, 0, true, true, true);
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 1;
    this.type_sort[3] = 0;
  }

/**Méthode qui correspond au sort n°1 de Eludia
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Etourdissement(1), 18);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Eludia
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = cibles[0].getPdv()*30/100;
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Eludia
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*85/100 - cibles[1].getDefense()*30/100;
    cibles[0].appliquerEffet(new Marque(3), 75);
    cibles[0].appliquerEffet(new AntiHeal(3), 75);
    cibles[1].appliquerEffet(new Marque(3), 75);
    cibles[1].appliquerEffet(new AntiHeal(3), 75);
    if (cibles[0].coupFatal(degatseffectue[0]) && cibles[0].getPassifRevive()) {
      cibles[0].setPassifRevive(false);
      Combat.ajouterCommentaire("-Eludia a empêché "+cibles[0].getClass().getName().substring(11)+" de revenir à la vie !");
    }
    if (cibles[1].coupFatal(degatseffectue[1]) && cibles[1].getPassifRevive()) {
      cibles[1].setPassifRevive(false);
      Combat.ajouterCommentaire("-Eludia a empêché "+cibles[1].getClass().getName().substring(11)+" de revenir à la vie !");
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Eludia
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    if (this.getPdv() == 0) {
      this.setPdv(this.getPdvMax()*50/100);
      this.setPassifRevive(false);
    }
    if (this.getPassifRevive()) {
      this.heal(this.getPdvMax() * 5 / 100);
      cibles[0].heal(this.getPdvMax() * 5 / 100);
      Combat.ajouterCommentaire(" ");
    }
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
    } else if ((ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) || ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] == 0 && ennemi.getPdv() < (ennemi.getPdvMax() * 25 / 100) && ennemi.getPassifRevive()) {
      res = 3;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[1] == 0) {
      res = 2;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[1] != 0) {
      res = 1;
    } else if (this.cooldown[2] == 0 && ennemi.getPdv() < (ennemi.getPdvMax() * 25 / 100) && ennemi.getPassifRevive()) {
      res = 3;
    } else if (this.cooldown[1] == 0 && ennemi.getPdv() > (ennemi.getPdvMax() * 75 / 100)) {
      res = 2;
    } else if (this.cooldown[2] == 0 && !ennemi.getPassifRevive()) {
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
    } else if (ennemis[cibleSort[0]-1].getPdv() > ennemis[cibleSort[0]-1].getPdvMax()*70/100 && this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if (ennemis[this.changementCible(cibleSort[0])-1].getPdv() > ennemis[this.changementCible(cibleSort[0])-1].getPdvMax()*70/100 && this.cooldown[1] == 0) {
      cibleSort[0] = this.changementCible(cibleSort[0]);
      cibleSort[1] = 2;
    } else if (this.cooldown[2] == 0 && ennemis[cibleSort[0]-1].getPdv() < (ennemis[cibleSort[0]-1].getPdvMax()*30/100) && ennemis[cibleSort[0]-1].getPassifRevive()) {
      cibleSort[1] = 3;
    } else if (this.cooldown[2] == 0 && !ko && ennemis[this.changementCible(cibleSort[0])-1].getPdv() < (ennemis[this.changementCible(cibleSort[0])-1].getPdvMax()*30/100) && ennemis[this.changementCible(cibleSort[0])-1].getPassifRevive()) {
      cibleSort[1] = 3;
    } else if (!ennemis[cibleSort[0]-1].getPassifRevive() && !ennemis[this.changementCible(cibleSort[0])-1].getPassifRevive() && this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 2) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Eludia
  *@return la description de Eludia
  */
  public String toString() {
    return "Votre personnage est un Eludia et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Eludia
  *@return la description du sort n°1 de Eludia
  */
  public String descriptionSort1() {
    return "Attaque l'ennemi avec une puissance mysterieuse et étourdis le avec 18% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Eludia
  *@return la description du sort n°1 de Eludia
  */
  public String descriptionSort2() {
    return "Fait exploser une énergie comprimée pour infliger des dégats à l'ennemi en fonction de ses points de vie actuel (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Eludia
  *@return la description du sort n°1 de Eludia
  */
  public  String descriptionSort3() {
    return "Brûle l'âme des ennemis avec le feu éternel pour leur infliger des dégats et les empêcher de revenir à la vie si cette attaque leur est fatale. De plus, leur applique un effet perturbant la récupération et une marque pendant 3 tours avec 75% de chance (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Eludia
  *@return la description du sort n°1 de Eludia
  */
  public String descriptionSort4() {
    return "Si vous mourrez, vous fait renaître de vos cendre avec la totalité de vos points de vie (Activable 1 seul fois). De plus, récupère 5% de vos points de vie maximum tant que vous n'avez pas utilisé votre réanimation";
  }
}
