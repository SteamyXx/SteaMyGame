/*
*Cette classe est une classe de Erwin
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
import java.util.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import application.*;
import effet.effettour.*;
import effet.*;

import java.util.*;


public class Erwin extends Personnage {

/**Constructeur de la classe Erwin qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Erwin
*(les autres paramètres sont prédéfinis)
*/
  public Erwin(String n) {
    super(327, 146, 111, 81, n, "Mitraille", "Tire à la Cheville", "Tornade de Flèches", "Chemin de la Forêt", 3, 4, 5);
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 1;
    this.type_sort[3] = 3;
  }

/**Méthode qui correspond au sort n°1 de Erwin
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*37/100 - cibles[0].getDefense()*15/100;
    degatseffectue[0] += this.getAttaque()*38/100 - cibles[0].getDefense()*15/100;
    Random r = new Random();
    if (r.nextInt(100) < this.getTxCrit()) {
      Combat.ajouterCommentaire("-Erwin ajoute un troisième coup !");
      degatseffectue[0] += this.getAttaque()*19/100 - cibles[0].getDefense()*15/100;
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Erwin
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*82/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new Etourdissement(1), 30);
    if (cibles[0].possedeEffet(new Etourdissement(0))) {
      Combat.ajouterCommentaire("-Erwin ajoute une Mitraille !");
      degatseffectue[0] += this.sort1(cibles, ennemisCibles)[0]*2/3;
    }
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Erwin
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 0;
    degatseffectue[1] = 0;
    Random r = new Random();
    int cible = 0;
    for (int i = 0; i<4; i++) {
      cible = r.nextInt(2);
      if (cibles[cible].getPdv() <= 0) {
        cible = (cible == 0) ? 1 : 0;
      }
      if (cibles[1] instanceof Poutch) {
        cible = 0;
      }
      degatseffectue[cible] += this.getAttaque()*23/100 - cibles[cible].getDefense()*13/100;
      cibles[cible].appliquerEffet(new DegatContinu(r.nextInt(3)+1), 30);
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Erwin
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    cibles[0].appliquerEffet(new BuffCrit(3));
    cibles[1].appliquerEffet(new BuffCrit(3));
    cibles[0].appliquerEffet(new BuffVitesse(3));
    cibles[1].appliquerEffet(new BuffVitesse(3));
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
    } else if (this.cooldown[3] == 0) {
      res = 4;
    } else if (this.possedeEffet(imm) || this.possedeEffet(inv)) {
      res = 1;
    } else if (this.cooldown[1] == 0 && this.getPdv() < this.getPdvMax()*20/100) {
      res = 2;
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
    Effet sil = new Silence(0);
    int[] cibleSort = new int[2];
    boolean ko = false;
    Random r = new Random();
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }


    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (this.cooldown[1] == 0 && this.getPdv() < this.getPdvMax()*25/100) {
      cibleSort[1] = 2;
    } else if (this.cooldown[3] == 0) {
      cibleSort[0] = 3;
      cibleSort[1] = 4;
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if (this.cooldown[2] == 0) {
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

  /**Méthode qui permet de décrire un Erwin
  *@return la description de Erwin
  */
  public String toString() {
    return "Votre personnage est un Erwin et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Erwin
  *@return la description du sort n°1 de Erwin
  */
  public String descriptionSort1() {
    return "Attaque 2 fois l'ennemi rapidement et tire un coup supplémentaire en fonction de ton taux de critique (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Erwin
  *@return la description du sort n°1 de Erwin
  */
  public String descriptionSort2() {
    return "Attaque l'ennemi avec 30% de chance de l'étourdir. Si l'étourdissement est un succès, lance le sort Mitraille en supplément (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Erwin
  *@return la description du sort n°1 de Erwin
  */
  public  String descriptionSort3() {
    return "Envoie aléatoirement une tornade de 4 flèches sur les ennemis qui vont infliger des dégats continus avec 30% de chance chacune. La durée de chaque dégat continu appliqué est au hazare entre 1, 2 et 3 tours (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Erwin
  *@return la description du sort n°1 de Erwin
  */
  public String descriptionSort4() {
    return "Utilise la puissance de la forêt pour augmenter les taux de critiques et la vitesse des alliés pendant 3 tours (cooldown = "+this.cooldown_max4+")";
  }
}
