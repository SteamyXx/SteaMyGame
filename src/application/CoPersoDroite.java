package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CoPersoDroite implements ActionListener {

  private CreationPerso cp;

  public CoPersoDroite(CreationPerso cp) {
    this.cp = cp;
  }

  public void actionPerformed(ActionEvent e) {
    if (this.cp.isEcran("CreationPerso")) {
      if (this.cp.getI() == this.cp.getNbPerso()-1) {
        this.cp.setI(0);
      } else {
        this.cp.setI(this.cp.getI()+1);
      }
    } else {
      do {
        if (this.cp.getI() == this.cp.getNbPerso()-1) {
          this.cp.setI(0);
        } else {
          this.cp.setI(this.cp.getI()+1);
        }
      } while (this.cp.comptePossedePersoCourant());
    }
    this.cp.changerCarac(this.cp.getPerso(this.cp.getI()));
    this.cp.changerDescription(this.cp.getPerso(this.cp.getI()));
  }
}
