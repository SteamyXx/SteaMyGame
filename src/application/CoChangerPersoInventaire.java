package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class CoChangerPersoInventaire implements ListSelectionListener {

  private Jeu jeu;

  public CoChangerPersoInventaire(Jeu jeu) {
    this.jeu = jeu;
  }

  public void valueChanged(ListSelectionEvent e) {
    java.util.List<String> selection = this.jeu.getInv().getSelectedPerso();
    if (selection.size() == 1) {
      String selected = selection.get(0);
      this.jeu.ajouterPersoInventaire(this.jeu.getCompte().getPerso(this.jeu.getInv().indexOfString(selected)));
    }
  }
}
