/*
*Cette classe est une classe de Veromos
*
*@author CHATEAU Julien
*@version 1.0
*/

package personnage;
import java.util.*;
import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import java.util.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import effet.effettour.*;
import effet.*;
import application.*;


public class Veromos extends Personnage {

  /**Constructeur de la classe Veromos qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Veromos
  *(les autres paramètres sont prédéfinis)
  */
  public Veromos(String n) {
    super(346, 112, 137, 89, n, "Mégas-fracas", "Super Crush", "Mach Crush", "Conversion Magique", 3, 3, 0, true, true, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 1;
    this.type_sort[2] = 4;
    this.type_sort[3] = 0;
  }

  /**Méthode qui correspond au sort n°1 de Veromos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*65/100 + this.getVitesse()*25/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new DegatContinu(2), 75);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Veromos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*45/100 + this.getPdvMax()*20/100  - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*45/100 + this.getPdvMax()*20/100  - cibles[1].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 50);
    cibles[1].appliquerEffet(new BreakDefense(2), 50);
    cibles[0].appliquerEffet(new Etourdissement(1), 25);
    cibles[1].appliquerEffet(new Etourdissement(1), 25);
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Veromos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - ennemisCibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*75/100 - ennemisCibles[1].getDefense()*30/100;
    degatseffectue[0] *= ((100+25*ennemisCibles[0].nbrEffetsNocifs())/100);
    degatseffectue[1] *= ((100+25*ennemisCibles[1].nbrEffetsNocifs())/100);
    cibles[0].appliquerEffet(new Regeneration(2));
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Veromos
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    boolean n = false;
    int n1 = 0;
    Random r = new Random();
    n = EffetNocif.clean(cibles[0]);
    if (n) {
      n1++;
    }
    n = EffetNocif.clean(cibles[1]);
    if (n) {
      n1++;
    }
    if (n1 > 0) {
      this.heal(this.getPdvMax()*n1*5/100);
      Combat.ajouterCommentaire(" ");
    }
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet bd = new BreakDefense(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] == 0 && ennemi.getListeEffet().size() >= 1) {
      res = 3;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] != 0) {
      res = 1;
    } else if (this.cooldown[2] == 0 && ennemi.getListeEffet().size() >= 1 && !ennemi.possedeEffetNTour(bd, 2)) {
      res = 3;
    } else if (this.cooldown[2] == 0 && ennemi.possedeEffetNTour(bd, 2) && ennemi.getListeEffet().size() == 1) {
      res = 1;
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

    int nbrEffetsNocifsCible = ennemis[cibleSort[0]-1].nbrEffetsNocifs();
    int nbrEffetsNocifsAutre = ennemis[this.changementCible(cibleSort[0])-1].nbrEffetsNocifs();

    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (!(ennemis[0].possedeEffet(new Immunite(0)) && ennemis[1].possedeEffet(new Immunite(0))) && this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else if (nbrEffetsNocifsCible+nbrEffetsNocifsAutre >= 3 && this.cooldown[2] == 0 && (this.getPdv()/this.getPdvMax()) > (allieLanceur.getPdv()/allieLanceur.getPdvMax())) {
      cibleSort[0] = 4;
      cibleSort[1] = 3;
    } else if (nbrEffetsNocifsCible+nbrEffetsNocifsAutre >= 3 && this.cooldown[2] == 0) {
      cibleSort[0] = 3;
      cibleSort[1] = 3;
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un Veromos
  *@return la description de Veromos
  */
  public String toString() {
    return "Votre personnage est un Veromos et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Veromos
  *@return la description du sort n°1 de Veromos
  */
  public String descriptionSort1() {
    return "Attaque l'ennemi avec des pouvoirs magiques et lui applique un Degat Continu <Dgt_Cnt> de 2 tours avec 75% de chance. Les dégats augmentent en fonction de votre vitesse (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Veromos
  *@return la description du sort n°1 de Veromos
  */
  public String descriptionSort2() {
    return "Saute sur les ennemis et leur inflige des dégats dont une partie dépend de vos points de vie maximums. De plus, les étourdis <Stun> avec 25% de chance et diminue leur défense <Brk_D> pendant 2 tours avec 50% de chance (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Veromos
  *@return la description du sort n°1 de Veromos
  */
  public  String descriptionSort3() {
    return "Charge de l'énergie magique pour procurer une immunité à un allié pendant 2 tours puis relâche cette énergie pour attaquer les ennemis. Les dégats augmentent de 25% pour chaque effets nocifs de l'ennemi (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de Veromos
  *@return la description du sort n°1 de Veromos
  */
  public String descriptionSort4() {
    return "Chaque tour, supprime aléatoirement un de tes effets nocifs avec 75% de chance. Si un effet est supprimé, récupère 5% de tes points de vie maximums.";
  }
}
