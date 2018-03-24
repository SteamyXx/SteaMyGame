/*
*Cette classe est une classe de Haken
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
import java.util.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;
import application.*;

import effet.effettour.*;
import effet.*;


public class Haken extends Personnage {

  /**Constructeur de la classe Haken qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Haken
  *(les autres paramètres sont prédéfinis)
  */
  public Haken(String n) {
    super(417, 107, 131, 78, n, "Uppercut", "Cri de Guerre", "Attaque Haut-Bas", "Puissance de Broyage", 3, 3, 0, true, true, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 4;
    this.type_sort[2] = 1;
    this.type_sort[3] = 0;
  }

  /**Méthode qui correspond au sort n°1 de Haken
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new DegatContinu(1));
    if (CoGestionSorts.getCrit()) {
      ennemisCibles[0].heal(this.getPdvMax()*12/100);
      ennemisCibles[1].heal(this.getPdvMax()*12/100);
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Haken
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new BuffCrit(2));
    cibles[0].appliquerEffet(new BuffAttaque(2));
    EffetNocif.clean(cibles[0]);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Haken
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*65/100 + this.getPdvMax()*7/100 - cibles[0].getDefense()*30/100;
    degatseffectue[0] += (degatseffectue[0]*((this.getPdvMax()-417)/50)*5/100);
    degatseffectue[1] = this.getAttaque()*65/100 + this.getPdvMax()*7/100 - cibles[1].getDefense()*30/100;
    degatseffectue[1] += (degatseffectue[1]*((this.getPdvMax()-417)/50)*5/100);
    cibles[0].appliquerEffet(new Etourdissement(1), 30);
    cibles[0].appliquerEffet(new Etourdissement(1), 30);
    cibles[1].appliquerEffet(new Etourdissement(1), 30);
    cibles[1].appliquerEffet(new Etourdissement(1), 30);
    if (CoGestionSorts.getCrit()) {
      ennemisCibles[0].heal(this.getPdvMax()*12/100);
      ennemisCibles[1].heal(this.getPdvMax()*12/100);
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Haken
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    this.remplacerEffetParUnAutre(new BreakDefense(0), new BuffDefense(2));
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else if (ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm)) {
      res = 1;
    } else if (this.cooldown[2] == 0) {
      res = 3;
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
    } else if (this.cooldown[1] == 0 && this.getTxCrit() <= 50) {
      cibleSort[0] = 3;
      cibleSort[1] = 2;
    } else if (this.cooldown[1] == 0 && this.getTxCrit() > 50) {
      cibleSort[0] = 4;
      cibleSort[1] = 2;
    } else if (this.cooldown[2] == 0 && !(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0)))) {
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

  /**Méthode qui permet de décrire un Haken
  *@return la description de Haken
  */
  public String toString() {
    return "Votre personnage est un Haken et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Haken
  *@return la description du sort n°1 de Haken
  */
  public String descriptionSort1() {
    return "Frappe l'ennemi et lui inflige un dégat continu pendant 1 tour (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Haken
  *@return la description du sort n°1 de Haken
  */
  public String descriptionSort2() {
    return "Augmente l'attaque et les taux de critique d'un allié pendant 2 tours puis supprime un de ses effets nocifs (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Haken
  *@return la description du sort n°1 de Haken
  */
  public  String descriptionSort3() {
    return "Donne deux coups à chaque ennemi. Un coup a 30% de chance d'étourdir la cible. De plus, une partie des dégats dépend de tes points de vie maximums et pour chaque 50 points de vie supplémentaires (ajouté au point de vie de base), les dégats de cette attaque sont augmentés de 5% (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Haken
  *@return la description du sort n°1 de Haken
  */
  public String descriptionSort4() {
    return "Si tu donnes un coup critique tu soignes les alliés de 12% de tes points de vie maximums. De plus, à chaque tour, converti un effet de réduction de défense (si possédé) en un boost de défense de 2 tour";
  }
}
