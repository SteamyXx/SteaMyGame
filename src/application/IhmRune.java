package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import java.awt.event.*;


public class IhmRune extends JButton {

  private Rune r;
  private Jeu jeu;
  private ActionListener al;
  private String bouton2;
  private Inventaire inv;

  public IhmRune(Rune r, Jeu jeu, String bouton2, Inventaire inv) {
    super();
    Font font = new Font("Arial", Font.BOLD ,17);
    this.r = r;
    this.bouton2 = bouton2;
    this.jeu = jeu;
    this.setFont(font);
    this.setBackground(Color.decode("#C3C1BF"));
    this.setPreferredSize(new Dimension(60, 35));
    if (r != null) {
      this.setText(this.r.getEtiquette()+this.r.getPosition());
      this.al = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new DescriptionRune(jeu, r, bouton2, inv);
        }
      };
      this.addActionListener(this.al);
    } else {
      this.setText("/");
      this.setEnabled(false);
    }
  }

  public Rune getRune() {
    return this.r;
  }

  public void changerRune(Rune r, Jeu jeu, Inventaire inv) {
    this.jeu = jeu;
    this.r = r;
    this.inv = inv;
    String b = this.bouton2;
    if (r != null) {
      this.setText(this.r.getEtiquette()+this.r.getPosition());
      this.removeActionListener(this.al);
      this.al = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new DescriptionRune(jeu, r, b, inv);
        }
      };
      this.addActionListener(this.al);
      this.setEnabled(true);
    } else {
      this.setText("/");
      this.setEnabled(false);
    }
  }

}
