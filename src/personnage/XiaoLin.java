/*
*Cette classe est une classe de XiaoLin
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

import application.*;
import effet.effettour.*;
import effet.*;


public class XiaoLin extends Personnage {

  /**Constructeur de la classe XiaoLin qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de XiaoLin
  *(les autres paramètres sont prédéfinis)
  */
  public XiaoLin(String n) {
    super(323, 142, 101, 81, n, "Balle d'Energie", "Kick Tourné", "Attaque du Dragon d'Eau", "Savoir Suprême", 3, 4, 0, true, true, false);
    this.type_sort[0] = 2;
    this.type_sort[1] = 2;
    this.type_sort[2] = 1;
    this.type_sort[3] = 0;
  }


  /**Méthode qui correspond au sort n°1 de XiaoLin
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*70/100 - cibles[0].getDefense()*30/100;
    cibles[0].appliquerEffet(new BreakDefense(2), 50);
    if (this.getPdv() < this.getPdvMax()*33/100) {
      this.volDeVie(degatseffectue[0], cibles[0], 30);
    }
    Random r = new Random();
    if (r.nextInt(100) < 20) {
      int cd = this.getCooldown(1);
      Combat.ajouterCommentaire("-"+this.getClass().getName().substring(11)+" ajoute un Kick Tourné !");
      degatseffectue[0] += this.sort2(cibles, ennemisCibles)[0];
      this.setCooldown(1, cd);//car sinon sort 2 baisse en cooldown
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de XiaoLin
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*75/100 - cibles[0].getDefense()*30/100;
    if (cibles[0].possedeEffet(new BreakDefense(0))) {
      cibles[0].appliquerEffet(new Etourdissement(1), 50);
    }
    cibles[0].appliquerEffet(new Ralentissement(2), 50);
    if (this.getPdv() < this.getPdvMax()*33/100) {
      this.volDeVie(degatseffectue[0], cibles[0], 30);
    }
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de XiaoLin
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
      if (cibles[1] instanceof Poutch) {
        cible = 0;
      }
      degatseffectue[cible] += this.getAttaque()*23/100 - cibles[cible].getDefense()*8/100;
      if (this.getPdv() < this.getPdvMax()*33/100) {
        this.volDeVie(degatseffectue[cible], cibles[cible], 30);
      }
    }
    degatseffectue[0] += (degatseffectue[0] * 10 * cibles[0].nbrEffetsNocifs() / 100);
    degatseffectue[1] += (degatseffectue[1] * 10 * cibles[1].nbrEffetsNocifs() / 100);
    this.appliquerEffet(new ContreAttaque(1));
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de XiaoLin
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    if (this.getPdv() < this.getPdvMax()*33/100) {
      this.appliquerEffet(new BuffDefense(1));
    }
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    Effet ah = new AntiHeal(0);
    Effet slow = new Ralentissement(0);
    Effet ba = new BuffAttaque(0);
    Effet inv = new Invincibilite(0);
    Effet imm = new Immunite(0);
    Effet sil = new Silence(0);

    if (this.possedeEffet(sil)) {
      res = 1;
    } else if (ennemi.possedeEffet(inv) && ennemi.possedeEffet(imm)) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] == 0) {
      res = 3;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[2] != 0) {
      res = 1;
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
    Random r = new Random();
    boolean ko = false;
    Effet sil = new Silence(0);
    cibleSort[0] = r.nextInt(2)+1;//choix d'une cible ennemis au hazare

    if (ennemis[cibleSort[0]-1].getPdv() == 0) {//si la cible choisis est KO
      cibleSort[0] = this.changementCible(cibleSort[0]);//change la cible
      ko = true;//la cible initiale est KO donc on a changé
    }

    int nbrEffetsNocifsCible = ennemis[cibleSort[0]-1].nbrEffetsNocifs();
    int nbrEffetsNocifsAutre = ennemis[this.changementCible(cibleSort[0])-1].nbrEffetsNocifs();

    if (this.possedeEffet(sil)) {//si silence
      cibleSort[1] = 1;//sort 1
    } else if (this.cooldown[2] == 0 && nbrEffetsNocifsCible+nbrEffetsNocifsAutre >= 2) {
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
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un XiaoLin
  *@return la description de XiaoLin
  */
  public String toString() {
    return "Votre personnage est un XiaoLin et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiaoLin
  *@return la description du sort n°1 de XiaoLin
  */
  public String descriptionSort1() {
    return "Attaque l'ennemi avec une énergie condensée et baisse sa défense pendant 2 tours avec 75% de chance. Ce sort à 30% de chance d'activer Kick Tourné (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiaoLin
  *@return la description du sort n°1 de XiaoLin
  */
  public String descriptionSort2() {
    return "Attaque l'ennemi avec un kick tournoyant pour le blessé et le ralentir pendant 2 tours avec 50% de chance. De plus, l'étourdit avec 50% de chance si l'ennemi possède un break défense (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiaoLin
  *@return la description du sort n°1 de XiaoLin
  */
  public  String descriptionSort3() {
    return "Attaque aléatoirement les ennemis 4 fois avec la puissance de l'eau. Les dégats de cette compétence sont augmentés de 10% par effets nocifs de l'ennemi. Vous contre-attaquez ensuite pendant 1 tour (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiaoLin
  *@return la description du sort n°1 de XiaoLin
  */
  public String descriptionSort4() {
    return "Si vous avez moins d'un tiers de vos points de vie maximums, vos attaques vous soigne de 30% des dégats infligés et chaque tours votre défense augmente pendant 1 tour.";
  }
}
