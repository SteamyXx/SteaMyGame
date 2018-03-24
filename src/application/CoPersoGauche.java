package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CoPersoGauche implements ActionListener {

  private CreationPerso cp;

  public CoPersoGauche(CreationPerso cp) {
    this.cp = cp;
  }

  public void actionPerformed(ActionEvent e) {
    if (this.cp.isEcran("CreationPerso")) {
      if (this.cp.getI() == 0) {
        this.cp.setI(this.cp.getNbPerso()-1);
      } else {
        this.cp.setI(this.cp.getI()-1);
      }
    } else {
      do {
        if (this.cp.getI() == 0) {
          this.cp.setI(this.cp.getNbPerso()-1);
        } else {
          this.cp.setI(this.cp.getI()-1);
        }
      } while (this.cp.comptePossedePersoCourant());
    }
    this.cp.changerCarac(this.cp.getPerso(this.cp.getI()));
    this.cp.changerDescription(this.cp.getPerso(this.cp.getI()));
  }
}
