/*
*Cette classe est une classe de Oberon
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

import effet.effettour.*;
import effet.*;
import application.*;
import java.util.*;


public class Oberon extends Personnage {

  private boolean isDernierSortUtilise1;


/**Constructeur de la classe Oberon qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Oberon
*(les autres paramètres sont prédéfinis)
*/
  public Oberon(String n) {
    super(351, 132, 109, 76, n, "Sérénité", "Maîtrise", "Lumière du Jugement", "Retour de la Colère", 3, 4, 0, true, false, true);
    this.isDernierSortUtilise1 = false;

    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 2;
    this.type_sort[3] = 0;
  }

/**Méthode qui correspond au sort n°1 de Oberon
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*70/100 - cibles[0].getDefense()*30/100;
    EffetBenefique.clean(cibles[0], 30);
    EffetBenefique.clean(cibles[0], 30);
    if (CoGestionSorts.getCrit()) {
      this.appliquerEffet(new BuffCrit(1));
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Oberon
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*80/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*80/100 - cibles[1].getDefense()*30/100;
    cibles[0].cooldownPlusPlus();
    cibles[1].cooldownPlusPlus();
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Oberon
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*85/100;
    if (CoGestionSorts.getCrit()) {
      this.appliquerEffet(new Invincibilite(1));
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Oberon
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    this.setPdv(this.getPdvMax()*30/100);
    ennemisCibles[0].setPdv(ennemisCibles[0].getPdv() - ennemisCibles[0].getPdvMax()*15/100);
    ennemisCibles[1].setPdv(ennemisCibles[1].getPdv() - ennemisCibles[1].getPdvMax()*15/100);
    Combat.ajouterCommentaire("-"+ennemisCibles[0].getClass().getName().substring(11)+" subit "+ennemisCibles[0].getPdvMax()*15/100+" points de dégat !");
    if (!(ennemisCibles[1] instanceof Poutch)) {
      Combat.ajouterCommentaire("-"+ennemisCibles[1].getClass().getName().substring(11)+" subit "+ennemisCibles[1].getPdvMax()*15/100+" points de dégat !");
      ennemisCibles[1].appliquerEffet(new Etourdissement(1), 35);
    }
    if (ennemisCibles[0].getPdv() < 0) {
      ennemisCibles[0].setPdv(0);
    }
    if (ennemisCibles[1].getPdv() < 0) {
      ennemisCibles[1].setPdv(0);
    }
    ennemisCibles[0].appliquerEffet(new Etourdissement(1), 35);
    this.setPassifRevive(false);
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet ah = new AntiHeal(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);
    Effet buffcrit = new BuffCrit(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv) || ennemi.possedeEffet(imm)) {
      res = 1;
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else if (!this.possedeEffet(buffcrit) && ennemi.getPdv() > ennemi.getPdvMax()*50/100) {
      res = 1;
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
    } else if (!this.isDernierSortUtilise1 && this.getTxCrit() < 50) {
      cibleSort[1] = 1;
    } else if (this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else {
      cibleSort[1] = 1;
    }

    if (cibleSort[1] == 1) {
      this.isDernierSortUtilise1 = true;
    } else {
      this.isDernierSortUtilise1 = false;
    }

    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Oberon
  *@return la description de Oberon
  */
  public String toString() {
    return "Votre personnage est un Oberon et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Oberon
  *@return la description du sort n°1 de Oberon
  */
  public String descriptionSort1() {
    return "Attaque 2 fois l'ennemi et retire un effet bénéfique avec 30% de chance pour chaque coup. Si le sort est un coup critique, augmente ton taux de critique pendant 1 tours (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Oberon
  *@return la description du sort n°1 de Oberon
  */
  public String descriptionSort2() {
    return "Inflige des dégats aux ennemis et augmente le cooldown de leurs sorts de 1 tour (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Oberon
  *@return la description du sort n°1 de Oberon
  */
  public  String descriptionSort3() {
    return "Le pouvoir de la lumière s'abat sur l'ennemi et lui inflige des dégats en ignorant sa défense. Si le sort est un coup critique, vous devenez invincible pendant 1 tour (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Oberon
  *@return la description du sort n°1 de Oberon
  */
  public String descriptionSort4() {
    return "Si vous mourrez, vous réanime avec 30% de vos points de vie maximums et infliger des dégats aux ennemis équivalent à 15% de leurs points de vie maximums passant à travers les boucliers. De plus vous avez 35% de chance de l'étourdir (Activable 1 seul fois)";
  }
}
