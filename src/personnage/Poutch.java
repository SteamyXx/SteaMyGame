/*
*Cette classe est une classe de Poutch
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

import effet.effettour.*;
import effet.*;
import application.*;


public class Poutch extends Personnage {

  /**Constructeur de la classe Poutch qui fait appel au contructeur de la classe Personnage
  *@param n qui représente le nom de Poutch
  *(les autres paramètres sont prédéfinis)
  */
  public Poutch(String n) {
    super(200, 101, 200, 0, n, "Charger au Combat", "Rugissement Polaire", "Piétiner l'Enfer", "Impact Lourd", 3, 4, 4);
    this.type_sort[0] = 2;
    this.type_sort[1] = 3;
    this.type_sort[2] = 2;
    this.type_sort[3] = 1;
  }

  /**Méthode qui correspond au sort n°1 de Poutch
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort1(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°2 de Poutch
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort2(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°3 de Poutch
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort3(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    return degatseffectue;
  }

  /**Méthode qui correspond au sort n°4 de Poutch
  *@param ennemi qui représente le Personnage sur lequel le sort est lancé
  */
  public int[] sort4(Personnage[] cibles, Personnage[] ennemisCibles) {
    int[] degatseffectue = new int[2];
    return degatseffectue;
  }

  public int iaChoixSort(Personnage ennemi) {
    int res = 0;
    return res;
  }

  public int[] cibleSortIa(Personnage allieLanceur, Personnage[] ennemis) {
    return null;
  }

  /**Méthode qui permet de décrire un Poutch
  *@return la description de Poutch
  */
  public String toString() {
    return "Votre personnage est un Poutch.";
  }

  /**Méthode qui permet de décrire le sort n°1 de Poutch
  *@return la description du sort n°1 de Poutch
  */
  public String descriptionSort1() {
    return "";
  }

  /**Méthode qui permet de décrire le sort n°1 de Poutch
  *@return la description du sort n°1 de Poutch
  */
  public String descriptionSort2() {
    return "";
  }

  /**Méthode qui permet de décrire le sort n°1 de Poutch
  *@return la description du sort n°1 de Poutch
  */
  public  String descriptionSort3() {
    return "";
  }

  /**Méthode qui permet de décrire le sort n°1 de Poutch
  *@return la description du sort n°1 de Poutch
  */
  public String descriptionSort4() {
    return "";
  }
}
