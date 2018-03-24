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

public class SuitePassifUn implements ActionListener {

  private Personnage j1;
  private Personnage j2;
  private Personnage poutch;
  private Combat combat;
  private JButton source;
  private int change;

  public SuitePassifUn(Personnage j1, Personnage j2, Personnage poutch, Combat combat, JButton source, int change) {
    this.j1 = j1;
    this.j2 = j2;
    this.poutch = poutch;
    this.combat = combat;
    this.source = source;
    this.change = change;
  }

  public void actionPerformed(ActionEvent e) {
    Random r = new Random();
    boolean revive1 = false;
    boolean revive2 = false;
    boolean etourdis = false;
    boolean crit = false;
    boolean marque = false;

    String nom1 = "";
    String nom2 = "";

    int plus = 0;
    int degatseffectue = 0;
    int shield = 0;
    int coupcrit = r.nextInt(100);

    Boolean stop = CoGestionSortsSolo.deroulementTour(j1, j2, poutch, change, source, this.combat);

    /*
    *	FIN DU TOUR DE J1
    */

    if (!stop) {
      change = -change;//on inverse la variable pour savoir qu'on passe à la deuxième partie du tour
      PauseEntreDeuxTours pedt = new PauseEntreDeuxTours(j1, j2, poutch, combat, source, change, stop);
      Timer timer = new Timer(1500, pedt);
      timer.setRepeats(false);
      timer.start();
    }

  }//actionPerformed



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
