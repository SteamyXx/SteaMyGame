/*
*Cette classe est une classe de Cadiz
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import java.util.*;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import java.util.*;
import application.*;
import effet.effettour.*;
import effet.*;


public class Cadiz extends Personnage {

  /**Constructeur de la classe Cadiz qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Cadiz
  *(les autres paramètres sont prédéfinis)
  */
  public Cadiz(String n) {
    super(361, 124, 116, 83, n, "Chauve-Souris Vampire", "Affaiblissement", "Accord Noble", "Festin de Sang", 3, 4, 0, true, false, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 2;
    this.type_sort[3] = 0;
  }


  /**Méthode qui correspond au sort n°1 de Cadiz
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    ennemisCibles[1].volDeVie(degatseffectue[0], cibles[0], 35);
    ennemisCibles[0].volDeVie(degatseffectue[0], cibles[0], 35);
    this.sort4(cibles, ennemisCibles);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Cadiz
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Ralentissement(2), 75);
    cibles[0].appliquerEffet(new BreakAttaque(2), 75);
    ennemisCibles[1].volDeVie(degatseffectue[0], cibles[0], 35);
    ennemisCibles[0].volDeVie(degatseffectue[0], cibles[0], 35);
    this.sort4(cibles, ennemisCibles);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Cadiz
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*80/100 - cibles[0].getDefense()*30/100;
    Random r = new Random();
    if (r.nextInt(100) < this.getTxCrit()*2) {
      CoGestionSorts.setCrit(true);
    } else {
      CoGestionSorts.setCrit(false);
    }
    int suppl = degatseffectue[0]*15/100;
    for (int i = 0; i<cibles[0].nbrEffetsNocifs(); i++) {
      degatseffectue[0] += suppl;
    }
    ennemisCibles[1].volDeVie(degatseffectue[0], cibles[0], 35);
    ennemisCibles[0].volDeVie(degatseffectue[0], cibles[0], 35);
    this.sort4(cibles, ennemisCibles);
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Cadiz
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new Marque(2), 50);
    if (CoGestionSorts.getCrit()) {
      this.appliquerEffet(new BuffVitesse(1));
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
    } else if (ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm)) {
      if (ennemi.nbrEffetsNocifs() > 0 && this.cooldown[2] == 0) {
        res = 3;
      } else {
        res = 1;
      }
    } else if (ennemi.nbrEffetsNocifs() > 0 && this.cooldown[2] == 0) {
      res = 3;
    } else if (this.cooldown[1] == 0) {
      res = 2;
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

    int nbrEffetsNocifsCible = ennemis[cibleSort[0]-1].nbrEffetsNocifs();
    int nbrEffetsNocifsAutre = ennemis[this.changementCible(cibleSort[0])-1].nbrEffetsNocifs();

    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (nbrEffetsNocifsCible >= 2 && nbrEffetsNocifsCible >= nbrEffetsNocifsAutre && this.cooldown[2] == 0) {//si la cible a plus de 2 effets nocifs et plus que l'autre
      cibleSort[1] = 3;//sort 3
    } else if (nbrEffetsNocifsCible >= 2 && nbrEffetsNocifsCible < nbrEffetsNocifsAutre && !ko && this.cooldown[2] == 0) {//si la cible a plus de 2 effets nocifs mais moins que l'autre
      cibleSort[0] = this.changementCible(cibleSort[0]);//change de cible
      cibleSort[1] = 3;//sort 3
    } else if (nbrEffetsNocifsCible < 2 && this.cooldown[1] == 0) {//si la cible a moins de 2 effets nocifs ou que sort 3 en cooldown
      cibleSort[1] = 2;//sort 2
    } else if (this.cooldown[2] == 0) {//sinon si sort 2 en cooldown
      cibleSort[1] = 3;//sort 3
    } else {
      cibleSort[1] = 1;
    }

    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 2) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Cadiz
  *@return la description de Cadiz
  */
  public String toString() {
    return "Votre personnage est un Cadiz et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cadiz
  *@return la description du sort n°1 de Cadiz
  */
  public String descriptionSort1() {
    return "Attaque l'ennemi avec des chauve-souris vampires et lui inflige des dégats (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cadiz
  *@return la description du sort n°1 de Cadiz
  */
  public String descriptionSort2() {
    return "Inflige des dégats à l'ennemi et l'affaibli gravement. Ce sort applique à l'ennemi un ralentissement et baisse son attaque pendant 2 tours avec 75% de chance chacun (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cadiz
  *@return la description du sort n°1 de Cadiz
  */
  public  String descriptionSort3() {
    return "Attaque l'ennemi violemment pour lui infliger de lourds dégats avec une chance de critique doublée. Plus l'ennemi possède d'effets nocifs, plus cette attaque est puissante (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Cadiz
  *@return la description du sort n°1 de Cadiz
  */
  public String descriptionSort4() {
    return "Vos sorts convertissent 35% des dégats effectués à l'ennemi en points de vie. De plus, lui applique une marque pendant 2 tours avec 50% de chance. Pour finir, si vous effecter un coup critique, votre vitesse augmente pendant 1 tour";
  }
}
