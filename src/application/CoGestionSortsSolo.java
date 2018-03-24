package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

import effet.*;
import effet.effettour.*;

import effet.effetnocif.*;
import effet.effetnocif.nocifmalus.*;
import effet.effetnocif.nocifmalusprofit.*;
import effet.effetnocif.nocifprofit.*;

import effet.effetbenefique.*;
import effet.effetbenefique.benefiquemalus.*;
import effet.effetbenefique.benefiquemalusprofit.*;
import effet.effetbenefique.benefiqueprofit.*;

import personnage.*;
import application.*;
import runes.*;

public class CoGestionSortsSolo implements ActionListener {

  private Combat combat;
  private static boolean crit = false;
  private static int compteur = 0;

  public CoGestionSortsSolo(Combat combat) {
    this.combat = combat;
  }

  public void actionPerformed(ActionEvent e) {
    JButton source = (JButton)e.getSource();
    if (this.combat.getIA().getPdv() != 0 && this.combat.getP().getPdv() != 0) {//si aucun des perso n'est K.O
    if (this.combat.getAction().equals("lancer")) {
      boolean cooldown = false;
      String str = source.getText();
      int it = 0;
      while (str.charAt(it) != '(') {
        it++;
      }
      if (str.charAt(it+1) == '0') {//si le coooldown du sort choisi est = 0

        //revient au choix d'une action
        this.combat.fenetreSuivante();

        Random r = new Random();

        Personnage j1 = this.combat.getP();
        Personnage j2 = this.combat.getIA();
        // j2.setPdv(0);
        Personnage poutch = new Poutch(" ");


        final Combat c = this.combat;

        int change = 1;//De base les personnages n'ont pas changés donc cette variable prend la valeur 1
        boolean stop = false;


        if (j1.possedeEffet(new Silence(0)) && !source.getText().substring(0, source.getText().length()-4).equals(j1.getNomSort(1))) {//si le joeuur est silence et à choisis un sort != du sort 1
          this.combat.tirets();
          Combat.ajouterCommentaire(" ");
          Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" est silence !!");
          Combat.ajouterCommentaire(" ");
          this.combat.afficherCommentaires();
        } else {//sinon le tour peut se dérouler
          if (j1.possedeEffet(new Ralentissement(0))) {
            NocifMalusProfit.appliquerEffet(j1);
          }

          if (j2.possedeEffet(new Ralentissement(0))) {
            NocifMalusProfit.appliquerEffet(j2);
          }

          //choix joueur donnant la première attaque (le plus rapide)
          if (j2.getVitesse() > j1.getVitesse()) {
            j2 = this.combat.getP();
            j1 = this.combat.getIA();
            change = 2;//si il y a un changement cette variable prend la valeur 2
          }

          Personnage[] joueur = new Personnage[2];
          joueur[0] = j1;
          joueur[1] = poutch;
          Personnage[] ennemi = new Personnage[2];
          ennemi[0] = j2;
          ennemi[1] = poutch;

          this.combat.unableAllButonPBA(false);

          //Effectue le passif du personnage qui doit jouer si il en possède un activable
          if (j1.getPossedePassif() && j1.getPassifActivable()) {
            this.combat.afficherPassif(j1);
            j1.sort4(ennemi, joueur);
            SuitePassifUn spu = new SuitePassifUn(j1, j2, poutch, combat, source, change);
            Timer timer = new Timer(1000, spu);
            timer.setRepeats(false);
            timer.start();
          } else {
            stop = CoGestionSortsSolo.deroulementTour(j1, j2, poutch, change, source, this.combat);
            /*
            *	FIN DU TOUR DE J1
            */
            if (!stop) {
              change = -change;
              PauseEntreDeuxTours pedt = new PauseEntreDeuxTours(j1, j2, poutch, combat, source, change, stop);
              Timer timer = new Timer(1500, pedt);
              timer.setRepeats(false);
              timer.start();
            }
          }//else passif 2
        }//!stop
      } else {
        this.combat.tirets();
        Combat.ajouterCommentaire(" ");
        Combat.ajouterCommentaire("-"+source.getText().substring(0, source.getText().length()-4)+" est en cooldown !!");
        Combat.ajouterCommentaire(" ");
        this.combat.afficherCommentaires();
      }
    }
  }
}

















public static boolean deroulementTour(Personnage j1, Personnage j2, Personnage poutch, int change, JButton source, Combat c) {
  Random r = new Random();
  boolean revive1 = false;
  boolean revive2 = false;
  boolean etourdis = false;
  boolean marque = false;
  boolean sige = false;
  boolean coold = false;
  Personnage[] joueur = new Personnage[2];
  joueur[0] = j1;
  joueur[1] = poutch;
  Personnage[] ennemi = new Personnage[2];
  ennemi[0] = j2;
  ennemi[1] = poutch;


  String nom1 = "";
  String nom2 = "";

  int sortChoisis = 0;
  int degatseffectue = 0;
  int shield = 0;
  int coupcrit = r.nextInt(100);

  Combat.ajouterCommentaire("");

  //traitement des effets activés avant le tour du joueur qui doit jouer
  CoGestionSortsSolo.appliquerBombe(j1);


  //traitement des effets activés chaque tour
  EffetTour.appliquerEffet(j1);
  EffetTour.tourEffetsMoinsMoins(j1);
  j1.desappliqueJuste();

  // //affiche les commentaires en attente sur le panel de droite
  Combat.ajouterCommentaire("");
  c.afficherCommentaires();
  Combat.ajouterCommentaire("");

  if (change == 1 || change == -2) {//si il n'y a pas eu de changement et qu'on est à la première partie du tour
                                    //ou si il y a eu un changement et qu'on est à la deuxieme partie du tour
                                    //ce qui signifie 'si je suis j1 et l'ia est j2'
    c.miseAJourLabel(j1, j2);//pour cette méthode j1 DOIT être le joueur et j2 DOIT être l'ia
  } else {
    c.miseAJourLabel(j2, j1);
  }

  //activation du revive si possedé
  if (j1.getPassifRevive() && j1.getCooldown(3) == 0) {
    if (j1.getPdv() == 0) {
      j1.sort4(joueur, ennemi);
      revive1 = true;
      nom1 = j1.getClass().getName().substring(11);
    }
  }
  if (j2.getPassifRevive() && j2.getCooldown(3) == 0) {
    if (j2.getPdv() == 0) {
      j2.sort4(ennemi, joueur);
      revive2 = true;
      nom2 = j2.getClass().getName().substring(11);
    }
  }

  boolean stop = false;
  final int change_bis = change;
  final Combat combat_bis = c;
  if (j1.getPdv() == 0) {//on teste si le perso qui va jouer n'a pas perdu à cause d'effets nocifs
    ActionListener listener = new ActionListener(){
      public void actionPerformed(ActionEvent event){
        if (change == -1 || change == 2) {//savoir si j1 est le joueur ou l'ia
          new FinCombat(combat_bis, true);//true => victoire
        } else {
          new FinCombat(combat_bis, false);//false => défaite
        }
      }
    };
    Timer timer = new Timer(1500, listener);
    timer.setRepeats(false);
    timer.start();
    stop = true;
  }

  // System.out.println(j1.toStringRunes());
  // System.out.println();
  // System.out.println(j2.toStringRunes());

  String tmp2 = Combat.a_afficher;
  Combat.a_afficher = "";

  if (!stop) {
    if (!j1.possedeEffet(new Etourdissement(0))) {//si pas étourdis
      if (coupcrit < j1.getTxCrit()) {//on vois si le sort sera un coup critique
        CoGestionSorts.setCrit(true);
      } else {
        CoGestionSorts.setCrit(false);
      }
      if (j2.possedeEffet(new Marque(0))) {//si avant de jouer l'adversaire possède une marque
        marque = true;//on le sauvegarde
      }


      if (change == 1 || change == -2) {//si j1 est le joueur, alors on lance le sort correspondant au bouton déclanché
        if (j1.possedeEffet(new Silence(0)) && !source.getText().substring(0, source.getText().length()-4).equals(j1.getNomSort(1))) {
          Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" est silence !");
          degatseffectue = j1.sort1(ennemi, joueur)[0];
          sortChoisis = 1;
        } else {
          if (source.getText().substring(0, source.getText().length()-4).equals(j1.getNomSort(1))) {
            if (j1.getCooldown(0) == 0) {
              sortChoisis = 1;
              degatseffectue = j1.sort1(ennemi, joueur)[0];
            } else {
              coold = true;
            }
          } else if (source.getText().substring(0, source.getText().length()-4).equals(j1.getNomSort(2))) {
            if (j1.getCooldown(1) == 0) {
              sortChoisis = 2;
              if (j1.getTypeSort(2) <= 2) {
                degatseffectue = j1.sort2(ennemi, joueur)[0];
              } else {
                degatseffectue = j1.sort2(joueur, ennemi)[0];
              }
            } else {
              coold = true;
            }
          } else if (source.getText().substring(0, source.getText().length()-4).equals(j1.getNomSort(3))) {
            if (j1.getCooldown(2) == 0) {
              sortChoisis = 3;
              if (j1.getTypeSort(3) <= 2) {
                degatseffectue = j1.sort3(ennemi, joueur)[0];
              } else {
                degatseffectue = j1.sort3(joueur, ennemi)[0];
              }
            } else {
              coold = true;
            }
          } else if (source.getText().substring(0, source.getText().length()-4).equals(j1.getNomSort(4))) {
            if (j1.getCooldown(3) == 0) {
              sortChoisis = 4;
              if (j1.getTypeSort(4) <= 2) {
                degatseffectue = j1.sort4(ennemi, joueur)[0];
              } else {
                degatseffectue = j1.sort4(joueur, ennemi)[0];
              }
              sige = true;
            } else {
              coold = true;
            }
          }
        }
      } else {//sinon on choisi le sort que va lancer l'ia
        int iasort = j1.iaChoixSort(j2);
        sortChoisis = iasort;
        degatseffectue = CoGestionSortsSolo.sortIA(j1, j2, poutch, iasort);
      }
      if (coold) {//si le perso jouant en deuxieme a vu ses cooldowns augmenter par le perso jouant en premier
        Combat.ajouterCommentaire("-Le cooldown des sorts de "+j1.getClass().getName().substring(11)+" a été augmenté !");
        sortChoisis = 1;
        degatseffectue = j1.sort1(ennemi, joueur)[0];//il lance le sort 1
      }

      if (CoGestionSorts.getCrit() && degatseffectue != 10000) {
        String tmp = Combat.a_afficher;
        Combat.a_afficher = "";
        Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" utilise "+j1.getNomSort(sortChoisis));
        Combat.ajouterCommentaire("-Coup Critique !!");
        Combat.ajouterCommentaire(" ");
        Combat.a_afficher = tmp2 + Combat.a_afficher + tmp;
      } else {
        String tmp = Combat.a_afficher;
        Combat.a_afficher = "";
        Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" utilise "+j1.getNomSort(sortChoisis));
        if (degatseffectue != 10000) {
          Combat.ajouterCommentaire(" ");
        }
        Combat.a_afficher = tmp2 + Combat.a_afficher + tmp;
      }

    } else {
      Combat.ajouterCommentaire(" ");
      Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" est étourdis !!");
      etourdis = true;
      j1.setSort4Active(0);
    }


    if (!etourdis) {
      if ((degatseffectue != 10000 && !j2.possedeEffet(new Invincibilite(0))) || (j1 instanceof Sige && sige)) {//10000 points de dégats signifie que le sort lancé était un boost
        if (marque) {//si l'adversaire avait une marque avant de lancer le sort, on augmente les dégats
          degatseffectue += (degatseffectue*25/100);
        }
        if (CoGestionSorts.getCrit()) {//si le sort est un coup critique on augmente les dégats en fonction des dégats crit
          degatseffectue += (degatseffectue * j1.getDgtsCrit() / 100);
        }
        if (j1 instanceof Sige && sige && j2.possedeEffet(new Invincibilite(0))) {
          Combat.ajouterCommentaire("-Sige passe à travers l'invincibilité !!");
        }
        if (degatseffectue < j1.getAttaque()/10) {//on minimise les dégats à 10% de l'attaque du joueur
          degatseffectue = j1.getAttaque()/10;
        }
        if (j2.getShield() != 0) {
          if (degatseffectue <= j2.getShield()) {
            j2.setShield(j2.getShield()-degatseffectue);
            Combat.ajouterCommentaire("-"+j2.getClass().getName().substring(11)+" a perdu "+degatseffectue+" points de bouclier");
          } else {
            shield = j2.getShield();
            j2.setShield(0);
            j2.setPdv(j2.getPdv() - (degatseffectue-shield));
            Combat.ajouterCommentaire("-"+j2.getClass().getName().substring(11)+" a perdu "+shield+" points de bouclier et "+(degatseffectue-shield)+" points de vie");
          }
        } else {
          Combat.ajouterCommentaire("-"+j2.getClass().getName().substring(11)+" a perdu "+degatseffectue+" points de vie");
          j2.setPdv(j2.getPdv() - degatseffectue);
        }
      } else if (degatseffectue != 10000 && j2.possedeEffet(new Invincibilite(0))) {
        Combat.ajouterCommentaire("-"+j2.getClass().getName().substring(11)+" est invincible et ne subit aucun dégat !");
      }


      if (j2.possedeEffet(new ContreAttaque(0)) && degatseffectue != 10000 && j2.getPdv() > 0) {//si contre attaque
        if (!j2.possedeEffet(new Etourdissement(0))) {
          Combat.ajouterCommentaire("-"+j2.getClass().getName().substring(11)+" contre-attaque en utilisant "+j2.getNomSort(1));
          degatseffectue = j2.sort1(joueur, ennemi)[0]*75/100;
          if (!j1.possedeEffet(new Invincibilite(0))) {
            if (j1.possedeEffet(new Marque(0))) {
              degatseffectue += (degatseffectue*25/100);
            }
            if (degatseffectue < j2.getAttaque()/10) {
              degatseffectue = j2.getAttaque()/10;
            }
            if (j1.getShield() != 0) {
              if (degatseffectue <= j1.getShield()) {
                j1.setShield(j1.getShield()-degatseffectue);
                Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" a perdu "+degatseffectue+" points de bouclier");
              } else {
                shield = j1.getShield();
                j1.setShield(0);
                j1.setPdv(j1.getPdv() - (degatseffectue-shield));
                Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" a perdu "+shield+" points de bouclier et "+(degatseffectue-shield)+" points de vie");
              }
            } else {
              Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" a perdu "+degatseffectue+" points de vie");
              j1.setPdv(j1.getPdv() - degatseffectue);
            }
          } else {
            Combat.ajouterCommentaire("-"+j1.getClass().getName().substring(11)+" est invincible et ne subit aucun dégat !");
          }
        } else {
          Combat.ajouterCommentaire("-"+j2.getClass().getName().substring(11)+" est étourdis et ne peut pas contre-attaquer");
        }
      }

      //réajustement des points de vies des 2 joueurs si ceux si sont inferieur à 0
      if (j1.getPdv() < 0) {
        j1.setPdv(0);
      }
      if (j2.getPdv() < 0) {
        j2.setPdv(0);
      }
    }



    //reduis les cooldown de chaques sort du joueur qui doit jouer de 1
    j1.cooldownmoinsmoins();

    //affiche les commentaires en attente sur le panel de droite
    // Combat.ajouterCommentaire("");
    c.afficherCommentaires();

    //traitement des effets activés après le tour du joueur qui viens de jouer
    EffetNocif.tourEffetsMoinsMoins(j1);
    EffetBenefique.tourEffetsMoinsMoins(j1);

    //activation du revive si possedé
    if (j1.getPassifRevive() && j1.getCooldown(3) == 0) {
      if (j1.getPdv() == 0) {
        j1.sort4(joueur, ennemi);
        revive1 = true;
        nom1 = j1.getClass().getName().substring(11);
      }
    }
    if (j2.getPassifRevive() && j2.getCooldown(3) == 0) {
      if (j2.getPdv() == 0) {
        j2.sort4(ennemi, joueur);
        revive2 = true;
        nom2 = j2.getClass().getName().substring(11);
      }
    }

    //affiche les commentaires en attente sur le panel de droite
    c.afficherCommentaires();

    //affichage message revive
    if (revive1) {
      Combat.ajouterCommentaire("-"+nom1+" est revenu à la vie !!");
      c.afficherCommentaires();
    }
    if (revive2) {
      Combat.ajouterCommentaire("-"+nom2+" est revenu à la vie !!");
      c.afficherCommentaires();
    }

    //si un des joueurs a perdu, on affiche la fenetre de fin de combat
    if (j1.getPdv() == 0) {
      ActionListener listener = new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if (change == -1 || change == 2) {
            new FinCombat(combat_bis, true);
          } else {
            new FinCombat(combat_bis, false);
          }
        }
      };
      Timer timer = new Timer(1500, listener);
      timer.setRepeats(false);
      timer.start();
      stop = true;
    } else if (j2.getPdv() == 0) {
      ActionListener listener = new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if (change == 1 || change == -2) {
            new FinCombat(combat_bis, true);
          } else {
            new FinCombat(combat_bis, false);
          }
        }
      };
      Timer timer = new Timer(1500, listener);
      timer.setRepeats(false);
      timer.start();
      stop = true;
    }

    //on rafraichi la fenetre
    if (change == -1 || change == 2) {
      c.miseAJourLabel(j2, j1);
      c.miseAJourPBA(j2);
      c.miseAJourPBS(j2);
    } else {
      c.miseAJourPBA(j1);
      c.miseAJourPBS(j1);
      c.miseAJourLabel(j1, j2);
    }

    //Dans le cas de Sige: on limite le nombre d'utilisation du sort 4 une fois activé de 1 tours
    if (j1.getSort4Active() > 0) {//si le
      j1.setSort4Active(j1.getSort4Active()-1);
    }

  }
  return stop;
}













public static void setCompteur(int cmp) {
  CoGestionSortsSolo.compteur = cmp;
}

public static int getCompteur() {
  return CoGestionSortsSolo.compteur;
}

public static void setCrit(boolean crit) {
  CoGestionSortsSolo.crit = crit;
}

public static boolean getCrit() {
  return CoGestionSortsSolo.crit;
}

public static int sortIA(Personnage ia, Personnage p, Personnage poutch, int i) {
  Personnage[] ias = new Personnage[2];
  ias[0] = ia;
  ias[1] = poutch;
  Personnage[] ps = new Personnage[2];
  ps[0] = p;
  ps[1] = poutch;
  int degatseffectue = 0;
  if (i == 1) {
    degatseffectue = ia.sort1(ps, ias)[0];
  } else if (i == 2) {
    if (ia.getTypeSort(i) <= 2) {
      degatseffectue = ia.sort2(ps, ias)[0];
    } else {
      degatseffectue = ia.sort2(ias, ps)[0];
    }
  } else if (i == 3) {
    if (ia.getTypeSort(i) <= 2) {
      degatseffectue = ia.sort3(ps, ias)[0];
    } else {
      degatseffectue = ia.sort3(ias, ps)[0];
    }
  } else if (i == 4) {
    if (ia.getTypeSort(i) <= 2) {
      degatseffectue = ia.sort4(ps, ias)[0];
    } else {
      degatseffectue = ia.sort4(ias, ps)[0];
    }
  }
  return degatseffectue;
}

public static void appliquerBombe(Personnage p) {
  for (int i = 0; i<p.getListeEffet().size(); i++) {
    if (p.getListeEffet().get(i) instanceof Bombe && p.getListeEffet().get(i).getNbrTour() == 1) {
      Bombe b = (Bombe)p.getListeEffet().get(i);
      b.effetBombe(p);
      p.getListeEffet().remove(p.getListeEffet().get(i));
    }
  }
}
}
