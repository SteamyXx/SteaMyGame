/*
*Cette classe est une classe de Zeratu
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import java.util.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import application.*;
import effet.effettour.*;
import effet.*;


public class Zeratu extends Personnage {

/**Constructeur de la classe Zeratu qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Zeratu
*(les autres paramètres sont prédéfinis)
*/
  public Zeratu(String n) {
    super(318, 143, 119, 87, n, "Piétiner", "Fureur Libérée", "Bourrasque", "Puissance Interdite", 5, 3, 0, true, false, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 5;
    this.type_sort[2] = 1;
    this.type_sort[3] = 0;
  }

/**Méthode qui correspond au sort n°1 de Zeratu
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    if (CoGestionSorts.getCrit()) {
      Combat.ajouterCommentaire("-Coup Critique !!");
      degatseffectue[0] += (degatseffectue[0] * this.getDgtsCrit() / 100);
      CoGestionSorts.setCrit(false);
      int plus = this.sort1(cibles, ennemisCibles)[0]/2 + this.sort1(cibles, ennemisCibles)[0]/2;
      degatseffectue[0] += plus;
      Combat.ajouterCommentaire("-Le passif de Zeratu lui a permis d'infligé "+plus+" points de dégats supplémentaires");
    }
    cibles[0].appliquerEffet(new DegatContinu(1), 100);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Zeratu
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    this.appliquerEffet(new BuffAttaque(2));
    this.appliquerEffet(new Immunite(2));
    if (!(ennemisCibles[1] instanceof Poutch)) {
      CoGestionSorts.setRejouer(true);
      Combat.ajouterCommentaire("-Zeratu rejoue !");
      Combat.ajouterCommentaire("");
    }
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Zeratu
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*60/100 + this.getVitesse()*10/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*60/100 + this.getVitesse()*10/100 - cibles[1].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakAttaque(2), 50);
    cibles[1].appliquerEffet(new BreakAttaque(2), 50);
    cibles[0].appliquerEffet(new Etourdissement(1), 35);
    cibles[1].appliquerEffet(new Etourdissement(1), 35);
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Zeratu
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if ((ennemi.possedeEffet(inv) || ennemi.possedeEffet(imm)) && this.cooldown[1] == 0) {
      res = 2;
    } else if ((ennemi.possedeEffet(inv) || ennemi.possedeEffet(imm)) && this.cooldown[1] != 0) {
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
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if (!(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0))) && this.cooldown[2] == 0) {
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

  /**Méthode qui permet de décrire un Zeratu
  *@return la description de Zeratu
  */
  public String toString() {
    return "Votre personnage est un Zeratu et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zeratu
  *@return la description du sort n°1 de Zeratu
  */
  public String descriptionSort1() {
    return "Ecrase l'ennemi lui infligeant des dégats et lui applique un dégat continu pendant 1 tour (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zeratu
  *@return la description du sort n°1 de Zeratu
  */
  public String descriptionSort2() {
    return "Augmente l'attaque pendant 3 tours et vous immunise contre les effets nocifs pendant la même durée. Vous rejouer instantanément après cette attaque si vous participez à un combat en duo (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zeratu
  *@return la description du sort n°1 de Zeratu
  */
  public  String descriptionSort3() {
    return "Frappe l'ennemi avec la vitesse de l'éclair puis augmente votre vitesse pendant 2 tours si cette attaque est un coup critique. Les dégats augmentent en fonction de votre vitesse (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Zeratu
  *@return la description du sort n°1 de Zeratu
  */
  public String descriptionSort4() {
    return "lorsque le sort n°1 est utilisé et qu'il est un coup critique, alors ce dernier sera relancé 2 fois et en occasionnant 3 fois moins des dégats";
  }
}
