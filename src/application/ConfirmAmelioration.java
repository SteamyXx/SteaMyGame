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

public class ConfirmAmelioration extends JFrame {

  private Jeu jeu;
  private Rune r;
  private DescriptionRune dr;

  public ConfirmAmelioration(Jeu jeu, Rune r, DescriptionRune dr) {
    super("Amélioration");
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
    int cout = 1000 + r.getNiveau()*100;
    if (this.r.getNiveau() == 19) {
      cout *= 3;
    }
    JLabel jl = new JLabel("        Améliorer cette rune pour un coût de "+cout+" $teamy ?");
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
    JButton ameliorer = new JButton("Améliorer");
    ameliorer.setBackground(Color.decode("#C3C1BF"));
    CoAmeliorerRune car = new CoAmeliorerRune(this.jeu, this.r, this.dr);
    ameliorer.addActionListener(car);
    ameliorer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        current.dispose();
      }
    });
    JButton annuler = new JButton("Annuler");
    annuler.setBackground(Color.decode("#C3C1BF"));
    annuler.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        current.dispose();
      }
    });

    b1.add(ameliorer);
    b2.add(annuler);
    centre.add(b1);
    centre.add(b2);
    principal.add(centre, BorderLayout.CENTER);

    this.setAlwaysOnTop(true);
    this.setPreferredSize(new Dimension(jeu.getPreferredSize().width*79/200, jeu.getPreferredSize().height*24/100));
    this.setLocation((jeu.getPreferredSize().width - this.getPreferredSize().width)/2+(int)jeu.getLocation().getX(),(jeu.getPreferredSize().height - this.getPreferredSize().height)/2+(int)jeu.getLocation().getY());
    // this.setLocation(375, 290);
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }
}
