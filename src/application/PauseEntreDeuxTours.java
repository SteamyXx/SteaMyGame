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

public class PauseEntreDeuxTours implements ActionListener {

  private Personnage j1;
  private Personnage j2;
  private Personnage poutch;
  private Combat combat;
  private JButton source;
  private int change;
  private boolean stop;

  public PauseEntreDeuxTours(Personnage j1, Personnage j2, Personnage poutch, Combat combat, JButton source, int change, boolean stop) {
    this.j1 = j1;
    this.j2 = j2;
    this.poutch = poutch;
    this.combat = combat;
    this.source = source;
    this.change = change;
  }

  public void actionPerformed(ActionEvent e) {

    if (!stop) {

      Random r = new Random();
      boolean revive1 = false;
      boolean revive2 = false;
      boolean etourdis = false;
      boolean crit = false;
      boolean marque = false;

      Personnage[] joueur = new Personnage[2];
      joueur[0] = j2;
      joueur[1] = poutch;
      Personnage[] ennemi = new Personnage[2];
      ennemi[0] = j1;
      ennemi[1] = poutch;

      String nom1 = "";
      String nom2 = "";

      int plus = 0;
      int degatseffectue = 0;
      int shield = 0;
      int coupcrit = r.nextInt(100);

      //Effectue le passif du personnage qui doit jouer si il en possède un activable
      if (j2.getPossedePassif() && j2.getPassifActivable()) {
        this.combat.afficherPassif(j2);
        j2.sort4(ennemi, joueur);
        SuitePassifDeux spd = new SuitePassifDeux(j1, j2, poutch, combat, source, change);
        Timer timer = new Timer(1000, spd);
        timer.setRepeats(false);
        timer.start();
      } else {
        CoGestionSortsSolo.deroulementTour(j2, j1, poutch, change, source, this.combat);
        this.combat.unableAllButonPBA(true);
        this.combat.tirets();
        this.combat.afficherCommentaires();
        this.combat.tourPlusPlus();
        /*
        *	FIN DU TOUR DE J2
        */
      }
    }
  }

  public void appliquerBombe(Personnage p) {
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof Bombe && p.getListeEffet().get(i).getNbrTour() == 1) {
        Bombe b = (Bombe)p.getListeEffet().get(i);
        b.effetBombe(p);
        p.getListeEffet().remove(p.getListeEffet().get(i));
      }
    }
  }

}
