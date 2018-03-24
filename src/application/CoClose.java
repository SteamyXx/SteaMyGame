package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import java.awt.event.*;

public class CoClose implements WindowListener {

  private Jeu jeu;

  public CoClose(Jeu jeu) {
    this.jeu = jeu;
  }

  public void windowActivated(WindowEvent e) {

  }

  public void windowClosed(WindowEvent e) {

  }

  public void windowClosing(WindowEvent e) {
    if (this.jeu.isEcran("Accueil")) {
      this.jeu.dispose();
    } else if (this.jeu.isEcran("CombatSolo") || this.jeu.isEcran("CombatDuo")) {
      new ConfirmAbandon(this.jeu);
    } else {
      new Exit(this.jeu);
    }
  }

  public void windowDeiconified(WindowEvent e) {

  }

  public void windowDeactivated(WindowEvent e) {

  }

  public void windowIconified(WindowEvent e) {

  }

  public void windowOpened(WindowEvent e) {

  }

}
