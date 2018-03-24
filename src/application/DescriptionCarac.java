package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DescriptionCarac extends JFrame {

  private Jeu jeu;

  public DescriptionCarac(Jeu jeu) {
    super("Caractéristiques");
    this.jeu = jeu;
    Font font = new Font("Arial", Font.BOLD , 14);
    JPanel principal = new JPanel();
    principal.setLayout(null);
    principal.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    principal.setBackground(Color.decode("#F2E5CF"));

    JTextArea sorts = null;
    if (jeu.isEcran("CombatDuo")) {
      sorts = new JTextArea(this.jeu.getCombatDuo().getJoueurCourant().descriptionCaracteristiques());
    } else if (jeu.isEcran("CombatSolo")) {
      sorts = new JTextArea(this.jeu.getCombatSolo().getP().descriptionCaracteristiques());//a modifier
    } else {
      sorts = new JTextArea(this.jeu.getInv().getPerso().descriptionCaracteristiques());
    }
    sorts.setSize(new Dimension(jeu.getPreferredSize().width*21/100, jeu.getPreferredSize().height*30/100));
    Dimension size = sorts.getSize();
    sorts.setBounds(16, 16, size.width, size.height);

    sorts.setFont(font);
    // sorts.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#F2E5CF")));
    sorts.setBackground(Color.decode("#F2E5CF"));
    sorts.setLineWrap(true);
    sorts.setWrapStyleWord(true);
    sorts.setEditable(false);
    sorts.setRows(16);

    principal.add(sorts);

    this.setPreferredSize(new Dimension(jeu.getPreferredSize().width*26/100, jeu.getPreferredSize().height*45/100));
    this.setLocation((jeu.getPreferredSize().width - this.getPreferredSize().width)/2+(int)jeu.getLocation().getX(),(jeu.getPreferredSize().height - this.getPreferredSize().height)/2+(int)jeu.getLocation().getY());
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }
}
