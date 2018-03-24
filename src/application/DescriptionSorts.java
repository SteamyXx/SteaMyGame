package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DescriptionSorts extends JFrame {

  private Jeu jeu;

  public DescriptionSorts(Jeu jeu) {
    super("Description sorts");
    this.jeu = jeu;
    Font font = new Font("Arial", Font.BOLD , 14);
    JPanel principal = new JPanel();
    principal.setLayout(null);
    principal.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    principal.setBackground(Color.decode("#F2E5CF"));

    JTextArea sorts = null;
    if (jeu.isEcran("CombatDuo")) {
      sorts = new JTextArea(this.jeu.getCombatDuo().getJoueurCourant().descriptionSorts());
    } else if (jeu.isEcran("CombatSolo")) {
      sorts = new JTextArea(this.jeu.getCombatSolo().getP().descriptionSorts());//a modifier
    } else {
      sorts = new JTextArea(this.jeu.getInv().getPerso().descriptionSorts());
    }
    sorts.setSize(new Dimension(jeu.getPreferredSize().width*59/100, jeu.getPreferredSize().height*60/100));
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

    this.setPreferredSize(new Dimension(jeu.getPreferredSize().width*63/100, jeu.getPreferredSize().height*70/100));
    this.setLocation((jeu.getPreferredSize().width - this.getPreferredSize().width)/2+(int)jeu.getLocation().getX(),(jeu.getPreferredSize().height - this.getPreferredSize().height)/2+(int)jeu.getLocation().getY());
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }
}
