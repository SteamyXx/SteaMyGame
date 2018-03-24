package application;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Exit extends JFrame {

  private Jeu jeu;

  public Exit(Jeu jeu) {
    super("Exit");
    this.jeu = jeu;
    Font font = new Font("Arial", Font.BOLD , 15);
    JPanel principal = new JPanel();
    principal.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    principal.setLayout(new BorderLayout());
    JPanel haut = new JPanel();
    haut.setBackground(Color.decode("#F2E5CF"));
    haut.setLayout(new GridLayout(3, 1));
    JLabel jl = new JLabel("        Que voulez vous faire ? (Une sauvegarde sera faite dans les deux cas)");
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
    JButton deco = new JButton("Se Déconnecter");
    deco.setBackground(Color.decode("#C3C1BF"));
    deco.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jeu.enregistrerDonneesCompte();
        // jeu.getCombat().setText("");
        // jeu.getCombat().setSort4Active();
        current.dispose();
        jeu.fenetreN(1);
      }
    });
    JButton exit = new JButton("Quitter");
    exit.setBackground(Color.decode("#C3C1BF"));
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jeu.enregistrerDonneesCompte();
        System.exit(0);
      }
    });
    b1.add(deco);
    b2.add(exit);
    centre.add(b1);
    centre.add(b2);
    principal.add(centre, BorderLayout.CENTER);

    this.setPreferredSize(new Dimension(600, 160));
    this.setLocation((jeu.getPreferredSize().width - this.getPreferredSize().width)/2+(int)jeu.getLocation().getX(),(jeu.getPreferredSize().height - this.getPreferredSize().height)/2+(int)jeu.getLocation().getY());
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }
}
