package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import comptes.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoAmeliorerRune implements ActionListener {

  private Jeu jeu;
  private Rune r;
  private DescriptionRune dr;

  public CoAmeliorerRune(Jeu jeu, Rune r, DescriptionRune dr) {
    this.jeu = jeu;
    this.r = r;
    this.dr = dr;
  }

  public void actionPerformed(ActionEvent e) {
    Compte c = this.jeu.getCompte();
    Personnage p = this.jeu.getInv().getPerso();
    // Personnage p = c.getPerso(c.posPersonnage(this.jeu.getInv().getPerso()));
    if (this.r.getNiveau() == 20) {
      JOptionPane.showMessageDialog(this.dr, "Cette rune est déjà lvl 20", "Erreur", JOptionPane.ERROR_MESSAGE);
    } else {
      int cout = 1000 + this.r.getNiveau()*50;
      if (c.getArgent() >= cout) {
        c.setArgent(c.getArgent() - cout);
        c.ameliorerRune(this.r);
        this.dr.rafraichissement(this.jeu, this.r, this.jeu.getInv());
        this.jeu.getInv().rafraichirArgent(this.jeu);
        this.jeu.getInv().rafraichirCarac(this.jeu);
      } else {
        JOptionPane.showMessageDialog(this.dr, "Vous n'avez pas assez de $teamy ("+cout+") pour améliorer cette rune ", "Erreur", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
