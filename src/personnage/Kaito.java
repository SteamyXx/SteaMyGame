/*
*Cette classe est une classe de Kaito
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


public class Kaito extends Personnage {

/**Constructeur de la classe Kaito qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de Kaito
*(les autres paramètres sont prédéfinis)
*/
  public Kaito(String n) {
    super(320, 153, 102, 90, n, "Coup d'Estoc", "Double Slash", "Guide de Bravoure", "Epée Céleste", 3, 3, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 5;
    this.type_sort[3] = 2;
  }

/**Méthode qui correspond au sort n°1 de Kaito
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*22/100 - cibles[1].getDefense()*10/100;
    cibles[0].appliquerEffet(new BreakDefense(1), 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Kaito
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    this.setPdv(this.getPdv() - this.getPdvMax()*10/100);
    int[] degatseffectue = new int[2];
    degatseffectue[0] = cibles[0].getPdvMax()*20/100;
    degatseffectue[1] = cibles[1].getPdvMax()*20/100;
    cibles[0].appliquerEffet(new DegatContinu(2), 50);
    cibles[0].appliquerEffet(new DegatContinu(2), 50);
    cibles[1].appliquerEffet(new DegatContinu(2), 50);
    cibles[1].appliquerEffet(new DegatContinu(2), 50);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de Kaito
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    this.appliquerEffet(new BuffAttaque(2), 100);
    this.appliquerEffet(new BuffVitesse(1), 100);
    Random r = new Random();
    if (r.nextInt(100) < this.getVitesse()-90 && !(cibles[1] instanceof Poutch)) {
      CoGestionSorts.setRejouer(true);
      Combat.ajouterCommentaire("-Kaito rejoue !");
      Combat.ajouterCommentaire("");
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Kaito
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*50/100 + this.getVitesse()*25/100;
    cibles[0].appliquerEffet(new Etourdissement(1), 25);
    this.volDeVie(degatseffectue[0], cibles[0], 50);
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
      trouve = true;
    } else if (ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) {
      res = 1;
      trouve = true;
    } else if ((ennemi.possedeEffet(inv) || ennemi.possedeEffet(imm)) && this.cooldown[2] == 0) {
      res = 3;
      trouve = true;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] != 0) {
      res = 1;
      trouve = true;
    }



    if ((this.cooldown[3] == 0 || this.cooldown[3] == 1) && this.cooldown[2] == 0 && !trouve) {
      if (this.pdv < (this.pdv_max * 65 / 100) && this.pdv > (this.pdv_max * 50 / 100)) {
        if ((!this.possedeEffet(ah) || this.possedeEffetNTour(ah, 1))) {
          res = 3;
          trouve = true;
        }
      }
    }

    if (this.pdv < (this.pdv_max * 50 / 100) && this.cooldown[3] == 0 && !trouve) {
      if (!this.possedeEffet(ah)) {
        res = 4;
        trouve = true;
      }
    }

    if (this.pdv > (this.pdv_max * 85 / 100) && this.cooldown[2] == 0 && !trouve) {
      res = 3;
      trouve = true;
    }

    if (this.cooldown[1] == 0 && !trouve) {
      res = 2;
      trouve = true;
    }

    if (!trouve) {
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
    } else if (this.cooldown[2] == 0) {
      cibleSort[0] = 3;
      cibleSort[1] = 3;//sort 3
    } else if (this.cooldown[1] == 0 && this.getPdv() > this.getPdvMax()*11/100 && !(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0)))) {
      cibleSort[1] = 2;//sort 2
    } else if (this.getPdv() < this.getPdvMax()*50/100 && this.cooldown[3] == 0 && !this.possedeEffet(new AntiHeal(0))) {
      cibleSort[1] = 4;//sort 4
    } else {
      cibleSort[1] = 1;//sort 1
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 4) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Kaito
  *@return la description de Kaito
  */
  public String toString() {
    return "Votre personnage est un Kaito et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Kaito
  *@return la description du sort n°1 de Kaito
  */
  public String descriptionSort1() {
    return "Dégaine rapidement l'épée et lacère 2 fois l'ennemi ciblé puis une troisième fois tous les ennemis. Diminue la défense de l'ennemi ciblé pendant 1 tour avec 75% de chance (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Kaito
  *@return la description du sort n°1 de Kaito
  */
  public String descriptionSort2() {
    return "Charge ton épée de 10% de tes points de vie puis relache la puissance pour infliger des dégats aux ennemis équivalant à 20% de leurs points de vie maximum. Applique 2 dégats continus de 2 tours avec 50% de chance sur chaque ennemi (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Kaito
  *@return la description du sort n°1 de Kaito
  */
  public  String descriptionSort3() {
    return "Augmente ton attaque pendant 2 tours et ta vitesse pendant 1 tour. De plus après ce sort, si tu participes à un combat duo tu a des chances de rejouer, chances qui sont équivalentes à ta vitesse supplémentaire. (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Kaito
  *@return la description du sort n°1 de Kaito
  */
  public String descriptionSort4() {
    return "Fait tomber l'épée céleste sur l'ennemi pour lui infliger des dégats (une partie dépend de votre vitesse) en ignorant sa défense. Ce sort vous soigne de la moitié des dégats infligés et étourdis l'ennemi pendant 1 tour avec 25% de chance (cooldown = "+this.cooldown_max4+")";
  }
}
