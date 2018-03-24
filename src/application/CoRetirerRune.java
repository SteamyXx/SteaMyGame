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

public class CoRetirerRune implements ActionListener {

  private Jeu jeu;
  private Rune r;
  private Inventaire inv;
  private DescriptionRune dr;
  private String b;

  public CoRetirerRune(Jeu jeu, Inventaire inv, Rune r, DescriptionRune dr, String b) {
    this.jeu = jeu;
    this.r = r;
    this.inv = inv;
    this.dr = dr;
    this.b = b;
  }

  public void actionPerformed(ActionEvent e) {
    Compte c = this.jeu.getCompte();
    Personnage p = this.jeu.getInv().getPerso();
    // Personnage p = c.getPerso(c.posPersonnage(this.jeu.getInv().getPerso()));
    if (this.b.equals("Retirer")) {
      c.desequiperRune(p, this.r.getPosition());
    } else {
      c.equiperRune(p, this.r);
    }
    this.inv.rafraichirInventaire(this.jeu);
    this.inv.rafraichirCarac(this.jeu);
    DescriptionRune.nbOuverte--;
    this.inv.rafraichirRunesEquipees(this.jeu);
    this.dr.dispose();
  }
}
