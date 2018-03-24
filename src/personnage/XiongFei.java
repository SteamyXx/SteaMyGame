/*
*Cette classe est une classe de XiongFei
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

import java.util.*;


public class XiongFei extends Personnage {

/**Constructeur de la classe XiongFei qui fait appel au contructeur de la classe Personnage
*@param n qui représente le nom de XiongFei
*(les autres paramètres sont prédéfinis)
*/
  public XiongFei(String n) {
    super(325, 119, 146, 79, n, "Attaque Séquentielle", "Esprit Calme", "Suprématie du Panda", "Force Surpuissante", 3, 4, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 5;
    this.type_sort[2] = 2;
    this.type_sort[3] = 1;
  }

/**Méthode qui correspond au sort n°1 de XiongFei
*@param ennemi qui représente le Personnage sur lequel le sort est lancé
*/
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    boolean applique = false;
    Random r = new Random();
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*25/100 - cibles[0].getDefense()*10/100;
    if (r.nextInt(100) < 30) {
      applique = cibles[0].appliquerEffet(new BreakDefense(2));
    }
    degatseffectue[0] += this.getAttaque()*25/100 - cibles[0].getDefense()*10/100;
    if (r.nextInt(100) < 30) {
      applique = cibles[0].appliquerEffet(new BreakDefense(2));
    }
    degatseffectue[0] += this.getAttaque()*25/100 - cibles[0].getDefense()*10/100;
    if (r.nextInt(100) < 30) {
      applique = cibles[0].appliquerEffet(new BreakDefense(2));
    }
    if (this.getCooldown(3) > 0) {
      degatseffectue[0] += degatseffectue[0]*25/100;
    }
    if (!applique) {
        Combat.ajouterCommentaire("-"+cibles[0].getClass().getName().substring(11)+" a résisté aux 3 effets nocifs");
    }
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de XiongFei
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = 10000;
    this.cleanToutLesEffetNocif();
    this.appliquerEffet(new ContreAttaque(2));
    cibles[0].appliquerEffet(new Regeneration(2));
    cibles[1].appliquerEffet(new Regeneration(2));
    this.setCooldown(1, this.cooldown_max2);
    return degatseffectue;
  }

 /**Méthode qui correspond au sort n°3 de XiongFei
 *@param ennemi qui représente le Personnage sur lequel le sort est lancé
 */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    Random r = new Random();
    int n = 0;
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getDefense()*15/100 - cibles[0].getDefense()*8/100;
    if (r.nextInt(100) < 25) {
      cibles[0].appliquerEffet(new Silence(2));
    } else {
      n++;
    }
    degatseffectue[0] += this.getDefense()*15/100 - cibles[0].getDefense()*8/100;
    if (r.nextInt(100) < 25) {
      cibles[0].appliquerEffet(new BreakAttaque(2));
    } else {
      n++;
    }
    degatseffectue[0] += this.getDefense()*15/100 - cibles[0].getDefense()*7/100;
    if (r.nextInt(100) < 25) {
      cibles[0].appliquerEffet(new Ralentissement(2));
    } else {
      n++;
    }
    degatseffectue[0] += this.getDefense()*15/100 - cibles[0].getDefense()*7/100;
    if (r.nextInt(100) < 25) {
      cibles[0].appliquerEffet(new AntiHeal(2));
    } else {
      n++;
    }
    if (n == 1) {
      Combat.ajouterCommentaire("-"+cibles[0].getClass().getName().substring(11)+" a résisté à un effet nocif");
    } else if (n == 4) {
      Combat.ajouterCommentaire("-"+cibles[0].getClass().getName().substring(11)+" a résisté à tous les effets nocifs");
    } else if (n == 2 || n == 3) {
      Combat.ajouterCommentaire("-"+cibles[0].getClass().getName().substring(11)+" a résisté à "+n+" des effets nocifs");
    }
    this.setCooldown(2, this.cooldown_max3);
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de XiongFei
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    degatseffectue[0] = this.getAttaque()*70/100 - cibles[0].getDefense()*30/100;
    degatseffectue[1] = this.getAttaque()*70/100 - cibles[1].getDefense()*30/100;
    if (cibles[0].nbrEffetsNocifs() >= 2) {
      degatseffectue[0] = this.getAttaque()*70/100;
    }
    if (cibles[1].nbrEffetsNocifs() >= 2) {
      degatseffectue[1] = this.getAttaque()*70/100;
    }
    this.setShield((degatseffectue[0]+degatseffectue[1])*30/100);
    this.setCooldown(3, this.cooldown_max4);
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
    } else if (ennemi.possedeEffet(inv) && this.cooldown[1] != 0) {
      res = 1;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[3] == 0) {
      res = 4;
    } else if (ennemi.possedeEffet(imm) && this.cooldown[3] != 0) {
      res = 1;
    } else if (this.cooldown[3] == 0 && ennemi.nbrEffetsNocifs() > 1) {
      res = 4;
    } else if (this.cooldown[1] == 0 && this.nbrEffetsNocifs() > 0) {
      res = 2;
    } else if (this.cooldown[2] == 0) {
      res = 3;
    } else if (this.cooldown[1] == 0) {
      res = 2;
    } else if (this.cooldown[3] == 0) {
      res = 4;
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
    } else if (this.cooldown[3] == 0 && nbrEffetsNocifsCible+nbrEffetsNocifsAutre >= 3) {
      cibleSort[1] = 4;
    } else if (this.cooldown[1] == 0 && this.nbrEffetsNocifs() > 0) {
      cibleSort[1] = 2;
      cibleSort[0] = 3;
    } else if (this.cooldown[3] == 0) {
      cibleSort[1] = 4;
    } else if (this.cooldown[2] == 0) {
      cibleSort[1] = 3;
    } else if (this.cooldown[1] == 0) {
      cibleSort[1] = 2;
      cibleSort[0] = 3;
    } else {
      cibleSort[1] = 1;
    }


    /*** Optimisation du choix de la cible dans le cas d'une attaque à cible unique sur un ennemi si l'allié de la cible n'est pas KO ***/
    if (!ko) {
      if (cibleSort[1] == 1) {
        cibleSort[0] = this.choixIaPreferenceDegats(cibleSort[0], ennemis);
      } else if (cibleSort[1] == 3) {
        cibleSort[0] = this.choixIaPreferenceEffets(cibleSort[0], ennemis);
      }
    }

    return cibleSort;
  }

  /**Méthode qui permet de décrire un XiongFei
  *@return la description de XiongFei
  */
  public String toString() {
    return "Votre personnage est un XiongFei et s'appelle "+this.getNom()+", il possède actuellement "+this.getPdv()+" points de vie, "+this.getAttaque()+" d'attaque, "+this.getDefense()+" de défense et "+this.getVitesse()+" de vitesse.";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiongFei
  *@return la description du sort n°1 de XiongFei
  */
  public String descriptionSort1() {
    return "Attaque 3 fois l'ennemi avec pour chaque coup 30% de réduire la défense de l'ennemi pendant 2 tours. Si 'Force Surpuissante' est en cooldown, les dégats sont augmentés de 25% (cooldown = "+this.cooldown_max1+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiongFei
  *@return la description du sort n°1 de XiongFei
  */
  public String descriptionSort2() {
    return "Le calme interieur vous permet de supprimer tous vos effets nocifs et de contre-attaquer pendant 2 tours. De plus vous procurez un effet de régénération pendant 2 tours aux alliés. (cooldown = "+this.cooldown_max2+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiongFei
  *@return la description du sort n°1 de XiongFei
  */
  public  String descriptionSort3() {
    return "Attaque l'ennemi 4 fois. Ce sort va succéssivement réduire au silence, diminuer l'attaque, ralentir et empêcher les soins de l'ennemi avec pour chaque effets 25% de chance d'être appliqué. Les dégats de cette attaque dépendent de vote défense (cooldown = "+this.cooldown_max3+")";
  }

  /**Méthode qui permet de décrire le sort n°1 de XiongFei
  *@return la description du sort n°1 de XiongFei
  */
  public String descriptionSort4() {
    return "Effectue une attaque surpuissante sur l'ennemi qui ignorera sa défense si il possède 2 effets nocifs ou plus. 30% des dégats effectués vous sont ensuite converti en un bouclier (cooldown = "+this.cooldown_max4+")";
  }
}
