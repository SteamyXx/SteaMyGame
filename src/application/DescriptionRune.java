package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import stats.*;
import java.awt.event.*;

public class DescriptionRune extends JFrame {

  private Jeu jeu;
  private Rune r;
  private Inventaire inv;
  private JLabel niveau;
  private JLabel sub1;
  private JLabel sub2;
  private JLabel sub3;
  private JLabel sub4;
  private JLabel mainstat;
  private JButton vendre;
  public static int nbOuverte = 0;

  public DescriptionRune(Jeu jeu, Rune r, String bouton2, Inventaire inv) {
    super("Description Rune");
    this.jeu = jeu;
    this.r = r;
    this.inv = inv;
    DescriptionRune.nbOuverte++;
    Font font18 = new Font("Arial", Font.BOLD , 18);
    Font font14 = new Font("Arial", Font.BOLD , 14);
    JPanel principal = new JPanel();
    principal.setLayout(new BorderLayout());
    principal.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    principal.setBackground(Color.decode("#F2E5CF"));
    JPanel haut = new JPanel();
    haut.setBackground(Color.decode("#F2E5CF"));
    haut.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#C3C1BF")));
    JLabel type = new JLabel(r.getClass().getName().substring(6, r.getClass().getName().length()));
    JLabel pos = new JLabel("   ("+this.r.getPosition()+")");
    this.niveau = new JLabel("lvl "+this.r.getNiveau());
    type.setFont(font18);
    pos.setFont(font18);
    this.niveau.setFont(font18);
    haut.add(type);
    haut.add(pos);
    haut.add(new JLabel("       "));
    haut.add(this.niveau);
    principal.add(haut, BorderLayout.NORTH);

    JPanel centre = new JPanel();
    centre.setBackground(Color.decode("#F2E5CF"));
    centre.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.decode("#C3C1BF")));
    centre.setLayout(new BorderLayout());
    JPanel nort = new JPanel();
    nort.setBackground(Color.decode("#F2E5CF"));
    nort.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#C3C1BF")));
    this.mainstat = new JLabel(this.r.getMain().toString());
    this.mainstat.setFont(font14);
    nort.add(this.mainstat);
    centre.add(nort, BorderLayout.NORTH);

    JPanel center = new JPanel();
    center.setBackground(Color.decode("#F2E5CF"));
    center.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("#C3C1BF")));
    center.setLayout(new GridLayout(4, 1));
    this.sub1 = new JLabel("    "+this.r.getSub(1).toString());
    this.sub1.setFont(font14);
    this.sub2 = new JLabel();
    this.sub2.setFont(font14);
    if (this.r.getSub(2) != null) {
      this.sub2.setText("    "+this.r.getSub(2).toString());
    }
    this.sub3 = new JLabel();
    this.sub3.setFont(font14);
    if (this.r.getSub(3) != null) {
      this.sub3.setText("    "+this.r.getSub(3).toString());
    }
    this.sub4 = new JLabel();
    this.sub4.setFont(font14);
    if (this.r.getSub(4) != null) {
      this.sub4.setText("    "+this.r.getSub(4).toString());
    }
    center.add(this.sub1);
    center.add(this.sub2);
    center.add(this.sub3);
    center.add(this.sub4);
    centre.add(center, BorderLayout.CENTER);
    principal.add(centre, BorderLayout.CENTER);

    JPanel bas = new JPanel();
    bas.setBackground(Color.decode("#F2E5CF"));
    bas.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.decode("#C3C1BF")));
    this.vendre = new JButton("Vendre");
    DescriptionRune current = this;
    this.vendre.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new ConfirmVente(jeu, r, current);
      }
    });
    this.vendre.setBackground(Color.decode("#C3C1BF"));
    bas.add(this.vendre);
    if (!bouton2.equals("42")) {
      JButton retirer = new JButton(bouton2);
      CoRetirerRune crr = new CoRetirerRune(this.jeu, this.inv, this.r, this, bouton2);
      retirer.addActionListener(crr);
      retirer.setBackground(Color.decode("#C3C1BF"));
      JButton ameliorer = new JButton("Ameliorer");
      ameliorer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new ConfirmAmelioration(jeu, r, current);
        }
      });
      ameliorer.setBackground(Color.decode("#C3C1BF"));
      bas.add(retirer);
      bas.add(ameliorer);
    }
    principal.add(bas, BorderLayout.SOUTH);

    CoCloseDescriptionRune ccdr = new CoCloseDescriptionRune();
    this.addWindowListener(ccdr);
    this.setPreferredSize(new Dimension(jeu.getPreferredSize().width*32/100, jeu.getPreferredSize().height*42/100));
    if (DescriptionRune.nbOuverte > 1) {
      this.setLocation((int)jeu.getLocation().getX()+15, (int)jeu.getLocation().getY()+this.getPreferredSize().height+2);
    } else {
      this.setLocation((int)jeu.getLocation().getX()+15, (int)jeu.getLocation().getY()+5);
    }
    this.setAlwaysOnTop(true);
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }

  public void nonVente() {
    Window[] liste = JFrame.getWindows();
    //itération sur ces composants
    for (int i = 0; i < liste.length; i++){
      if (liste[i] instanceof FinCombat){
        FinCombat tmp = (FinCombat)liste[i];
        tmp.nonVente(this.r);
      } else if (liste[i] instanceof FinCombatDuo){
        FinCombatDuo tmp = (FinCombatDuo)liste[i];
        tmp.nonVente(this.r);
      }
    }
  }

  public void rafraichissement(Jeu jeu, Rune r, Inventaire inv) {
    this.jeu = jeu;
    this.r = r;
    this.inv = inv;
    this.niveau.setText("lvl "+this.r.getNiveau());
    this.mainstat.setText(this.r.getMain().toString());
    if (this.r.getSub(1) != null) {
      this.sub1.setText("    "+this.r.getSub(1).toString());
    }
    if (this.r.getSub(2) != null) {
      this.sub2.setText("    "+this.r.getSub(2).toString());
    }
    if (this.r.getSub(3) != null) {
      this.sub3.setText("    "+this.r.getSub(3).toString());
    }
    if (this.r.getSub(4) != null) {
      this.sub4.setText("    "+this.r.getSub(4).toString());
    }
  }
}
