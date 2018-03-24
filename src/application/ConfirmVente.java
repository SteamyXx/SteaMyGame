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

public class ConfirmVente extends JFrame {

  private Jeu jeu;
  private Rune r;
  private DescriptionRune dr;

  public ConfirmVente(Jeu jeu, Rune r, DescriptionRune dr) {
    super("Vente");
    this.jeu = jeu;
    this.r = r;
    this.dr = dr;
    Font font = new Font("Arial", Font.BOLD , 15);
    JPanel principal = new JPanel();
    principal.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    principal.setLayout(new BorderLayout());
    JPanel haut = new JPanel();
    haut.setPreferredSize(new Dimension(50, 50));
    haut.setBackground(Color.decode("#F2E5CF"));
    haut.setLayout(new GridLayout(3, 1));
    int res = 0;
    res += 1000*r.getNiveau()+10*r.getMain().getValeur();
    for (int i = 0; i<r.getNbSub(); i++) {
      res += r.getSub(i+1).getValeur();
    }
    JLabel jl = new JLabel("        Vendre cette rune et recevoir "+res+" $teamy en échange ?");
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
    JButton vendre = new JButton("Vendre");
    vendre.setBackground(Color.decode("#C3C1BF"));
    CoVendreRune cvr = new CoVendreRune(this.jeu, this.jeu.getInv(), this.r, this.dr);
    vendre.addActionListener(cvr);
    vendre.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        current.dispose();
        dr.nonVente();
        dr.dispose();
      }
    });
    JButton annuler = new JButton("Annuler");
    annuler.setBackground(Color.decode("#C3C1BF"));
    annuler.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        current.dispose();
      }
    });

    b1.add(vendre);
    b2.add(annuler);
    centre.add(b1);
    centre.add(b2);
    principal.add(centre, BorderLayout.CENTER);

    this.setAlwaysOnTop(true);
    this.setPreferredSize(new Dimension(jeu.getPreferredSize().width*45/100, jeu.getPreferredSize().height*24/100));
    this.setLocation((jeu.getPreferredSize().width - this.getPreferredSize().width)/2+(int)jeu.getLocation().getX(),(jeu.getPreferredSize().height - this.getPreferredSize().height)/2+(int)jeu.getLocation().getY());
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }
}
