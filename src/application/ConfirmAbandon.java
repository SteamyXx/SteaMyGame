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

public class ConfirmAbandon extends JFrame {

  private Jeu jeu;

  public ConfirmAbandon(Jeu jeu) {
    super("Abandon");
    this.jeu = jeu;
    Font font = new Font("Arial", Font.BOLD , 15);
    JPanel principal = new JPanel();
    principal.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    principal.setLayout(new BorderLayout());
    JPanel haut = new JPanel();
    haut.setPreferredSize(new Dimension(50, 50));
    haut.setBackground(Color.decode("#F2E5CF"));
    haut.setLayout(new GridLayout(3, 1));
    JLabel jl = new JLabel("        Abandonner le combat et perdre 300 $teamy ?");
    if (jeu.isEcran("CombatDuo")) {
      jl.setText("        Abandonner le combat et perdre 600 $teamy ?");
    }
    jl.setFont(font);
    haut.add(new JLabel(" "));
    haut.add(jl);
    haut.add(new JLabel(" "));
    principal.add(haut, BorderLayout.NORTH);

    JPanel centre = new JPanel();
    centre.setLayout(new GridLayout(1, 2));

    JPanel b1 = new JPanel();
    b1.setLayout(new GridBagLayout());
    b1.setBackground(Color.decode("#F2E5CF"));
    JPanel b2 = new JPanel();
    b2.setLayout(new GridBagLayout());
    b2.setBackground(Color.decode("#F2E5CF"));

    JFrame current = this;
    JButton abandonner = new JButton("Abandonner");
    abandonner.setBackground(Color.decode("#C3C1BF"));;
    abandonner.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (jeu.isEcran("CombatDuo")) {
          jeu.getCompte().perteArgent(600);
          CoGestionSorts.setCompteur(0);
          Combat.a_afficher = "";
          jeu.getCombatDuo().resetAjoue();
          jeu.getCombatDuo().setText("");
          jeu.getCombatDuo().fenetreN(1);
          jeu.getCombatDuo().getP(0).reajusteCaracFinEtage();
          jeu.getCombatDuo().getP(1).reajusteCaracFinEtage();
          jeu.getCompte().getTour().resetEtage(CombatDuo.getEtage());
          jeu.getInv().rafraichirTout(jeu);
          jeu.getInv().setSelectedEtage(jeu.getCompte().getEtage()-1);
          jeu.fenetreN(5);
          current.dispose();
        } else {
          current.dispose();
          jeu.getCompte().perteArgent(300);
          CoGestionSorts.setCompteur(0);
          jeu.getCombatSolo().getP().reajusteCaracFinEtage();
          jeu.getCombatSolo().resetTour();
          jeu.getCombatSolo().fenetreN(1);
          jeu.getCompte().getTour().resetEtage(jeu.getInv().getEtage());
          jeu.getInv().rafraichirArgent(jeu);
          jeu.getInv().rafraichirCarac(jeu);
          jeu.fenetreN(5);
        }
      }
    });
    JButton annuler = new JButton("Annuler");
    annuler.setBackground(Color.decode("#C3C1BF"));
    annuler.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        current.dispose();
      }
    });

    b1.add(abandonner);
    b2.add(annuler);
    centre.add(b1);
    centre.add(b2);
    principal.add(centre, BorderLayout.CENTER);
    this.setPreferredSize(new Dimension(jeu.getPreferredSize().width*38/100, jeu.getPreferredSize().height*24/100));
    this.setLocation((jeu.getPreferredSize().width - this.getPreferredSize().width)/2+(int)jeu.getLocation().getX(),(jeu.getPreferredSize().height - this.getPreferredSize().height)/2+(int)jeu.getLocation().getY());
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }
}
