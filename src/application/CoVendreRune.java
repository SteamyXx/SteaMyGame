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

public class CoVendreRune implements ActionListener {

  private Jeu jeu;
  private Rune r;
  private Inventaire inv;
  private DescriptionRune dr;

  public CoVendreRune(Jeu jeu, Inventaire inv, Rune r, DescriptionRune dr) {
    this.jeu = jeu;
    this.r = r;
    this.inv = inv;
    this.dr = dr;
  }

  public void actionPerformed(ActionEvent e) {
    Compte c = this.jeu.getCompte();
    Personnage p = this.jeu.getInv().getPerso();
    // Personnage p = c.getPerso(c.posPersonnage(this.jeu.getInv().getPerso()));
    int i = 0;
    boolean equipee = false;
    while (i < 5 && !equipee) {
      if (p.getRune(i) != null) {
        if (p.getRune(i).equals(this.r)) {
          equipee = true;
          c.desequiperRune(p, i);
          inv.rafraichirRunesEquipees(this.jeu);
        }
      }
      i++;
    }
    c.vendreRune(this.r);
    inv.rafraichirArgent(this.jeu);
    inv.rafraichirInventaire(this.jeu);
    DescriptionRune.nbOuverte--;
    this.dr.dispose();
  }
}
