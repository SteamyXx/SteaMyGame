/*
*Cette classe est une classe de Stella
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
import java.util.*;

import application.*;
import effet.effettour.*;
import effet.*;


public class Stella extends Personnage {

/**Constructeur de la classe Stella qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Stella
*(les autres paramètres sont prédéfinis)
*/
  public Stella(String n) {
    super(335, 151, 113, 92, n, "Point Faible", "Faucheuse Silencieuse", "Lames de la Faucheuse", "Fourberie éclaire", 3, 3, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 2;
    this.type_sort[3] = 2;
  }

/**Méthode qui correspond au sort n°1 de Stella
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Marque(2), 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Stella
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*80/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Silence(1), 50);
    cibles[0].appliquerEffet(new Silence(1), 50);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Stella
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new DegatContinu(2), 75);
    int i = 0;
    if (this.getVitesse() > 110) {
      i = 1;
      degatseffectue[0] += this.getAttaque()*8/100 - cibles[0].getDefense()*10/100;
    }
    if (this.getVitesse() > 125) {
      i = 2;
      degatseffectue[0] += this.getAttaque()*8/100 - cibles[0].getDefense()*10/100;
    }
    if (this.getVitesse() > 140) {
      i = 3;
      degatseffectue[0] += this.getAttaque()*8/100 - cibles[0].getDefense()*10/100;
    }
    for (int j = 0; j<i; j++) {
      cibles[0].appliquerEffet(new DegatContinu(1), 75);
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Stella
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*60/100 + this.getVitesse()/2 - cibles[0].getDefense()*30/100;
    this.volDeVie(degatseffectue[0], cibles[0], 50);
    if (this.getPdvMax() == this.getPdv()) {
      this.appliquerEffet(new BuffAttaque(2));
    }
    this.setCooldown(3, this.cooldown_max4);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet ah = new AntiHeal(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    boolean trouve = false;
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm)) {
      if (this.cooldown[3] == 0 && !this.possedeEffet(ah) && this.getPdv() < this.getPdvMax()*75/100) {
        res = 4;
      } else if (this.cooldown[2] == 0) {
        res = 3;
      } else {
        res = 1;
      }
    } else if (this.cooldown[3] == 0 && !this.possedeEffet(ah) && this.getPdv() < this.getPdvMax()*75/100) {
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
    } else if (this.possedeEffetNTour(new BuffVitesse(0), 1) && this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else if (this.cooldown[3] == 0) {
      cibleSort[1] = 4;
    } else if (this.cooldown[2] == 0 && (!this.possedeEffetNTour(new Ralentissement(0), 1) || (this.possedeEffetNTour(new Ralentissement(0), 1) && this.cooldown[3] > 1))) {
      cibleSort[1] = 3;
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 2) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 4) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Stella
  *@return la description de Stella
  */
  public String toString() {
    return "Votre personnage est un Stella et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Stella
  *@return la description du sort n°1 de Stella
  */
  public String descriptionSort1() {
    return "Trouve la faiblesse de l'ennemi et attaque le pour lui appliquer une marque pendant 2 tours avec 50% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Stella
  *@return la description du sort n°1 de Stella
  */
  public String descriptionSort2() {
    return "Attaque l'ennemi 2 fois. Chaque attaque a 50% de chance de réduire l'ennemi au silence pendant 1 tour. (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Stella
  *@return la description du sort n°1 de Stella
  */
  public  String descriptionSort3() {
    return "Attaque la cible une fois pour lui infliger des dégats ainsi qu'un dégat continu de 2 tours avec 75% de chance puis ré-attaque jusqu'a 3 fois (chacune de ces 3 attaques supplémentaire applique un dégat continu de 1 tour avec 50%). Le nombre d'attaque supplémentaire augmente selon votre vitesse. (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Stella
  *@return la description du sort n°1 de Stella
  */
  public String descriptionSort4() {
    return "Fonce sur l'ennemi à une vitesse incroyable et lui inflige de lourds dégats dont une grande partie dépend de votre vitesse. Cette attaque vous soigne de la moitié des dégats infligés. Si vous avez la totalité de vos points de vie après cette attaque, votre attaque augmente pendant 2 tours (cooldown = "+this.cooldown_max4+")";
  }
}
