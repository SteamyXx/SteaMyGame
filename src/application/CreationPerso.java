package application;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationPerso extends JPanel {

  private Jeu jeu;
  private int i;
  private int nb_perso;
  private JTextArea bas;
  private JTextArea carac;

  public CreationPerso(Jeu jeu, int i2) {
    super();
    this.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    this.setLayout(new BorderLayout());
    this.jeu = jeu;
    this.nb_perso = 32;
    //this.nb_perso++ quand rajout de perso
    this.i = 0;
    Font font = new Font("Arial", Font.BOLD , 13);

    JPanel haut = new JPanel();
    haut.setBackground(Color.decode("#F2E5CF"));
    haut.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5,0,5,0);
    haut.add(new JLabel("Choisir un personnage : "), c);
    this.add(haut, BorderLayout.NORTH);

    JPanel centre = new JPanel();
    centre.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, Color.decode("#F2E5CF")));
    centre.setLayout(new BorderLayout());
    JButton droite = new JButton(">>>");
    CoPersoDroite copd = new CoPersoDroite(this);
    droite.addActionListener(copd);
    JButton gauche = new JButton("<<<");
    CoPersoGauche copg = new CoPersoGauche(this);
    gauche.addActionListener(copg);
    droite.setBackground(Color.decode("#C3C1BF"));
    gauche.setBackground(Color.decode("#C3C1BF"));
    centre.add(gauche, BorderLayout.WEST);
    centre.add(droite, BorderLayout.EAST);
    this.carac = new JTextArea(Jeu.liste_perso.get(i).descriptionCaracteristiques());
    this.carac.setFont(font);
    this.carac.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, Color.decode("#F2E5CF")));
    this.carac.setEditable(false);
    this.carac.setRows(12);
    this.carac.setBackground(Color.decode("#C3C1BF"));
    centre.add(this.carac, BorderLayout.CENTER);
    JPanel bas2 = new JPanel();
    bas2.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, Color.decode("#F2E5CF")));
    bas2.setBackground(Color.decode("#F2E5CF"));
    bas2.setLayout(new GridBagLayout());
    c.insets = new Insets(0,0,10,0);
    JButton creer = new JButton("Choisir");
    CreationPerso current = this;
    if (i2 == 1) {
      CoCreerPerso ccp = new CoCreerPerso(this, this.jeu);
      creer.addActionListener(ccp);
    } else {
      creer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jeu.getCompte().ajouterPersonnage(current.getI());
          jeu.ajouterPersoInventaire(jeu.getCompte().getPerso(2));
          jeu.getInv().rafraichirListPerso();
          jeu.fenetreN(10);
        }
      });
    }
    creer.setBackground(Color.decode("#C3C1BF"));
    bas2.add(creer, c);
    centre.add(bas2, BorderLayout.SOUTH);
    this.add(centre, BorderLayout.CENTER);

    this.bas = new JTextArea(Jeu.liste_perso.get(i).descriptionSorts());
    this.bas.setFont(font);
    this.bas.setLineWrap(true);
    this.bas.setWrapStyleWord(true);
    this.bas.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Color.decode("#F2E5CF")));
    this.bas.setEditable(false);
    this.bas.setRows(18);
    this.bas.setBackground(Color.decode("#C3C1BF"));
    this.add(bas, BorderLayout.SOUTH);
  }

  public int getI() {
    return this.i;
  }

  public void setI(int i) {
    this.i = i;
  }

  public int getNbPerso() {
    return this.nb_perso;
  }

  public boolean isEcran(String str) {
    return this.jeu.isEcran(str);
  }

  public boolean comptePossedePersoCourant() {
    return this.jeu.comptePossedePerso(this.getPerso(this.i));
  }

  public Personnage getPerso(int i) {
    return Jeu.liste_perso.get(i);
  }

  public void changerCarac(Personnage p) {
    this.carac.setText(p.descriptionCaracteristiques());
  }

  public void changerDescription(Personnage p) {
    this.bas.setText(p.descriptionSorts());
  }
}
