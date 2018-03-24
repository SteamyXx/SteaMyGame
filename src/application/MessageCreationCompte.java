package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;

public class MessageCreationCompte extends JFrame {

  private CreationCompte cc;

  public MessageCreationCompte(CreationCompte cc) {
    this.cc = cc;
    JPanel principal = new JPanel();
    Font font = new Font("Arial", Font.BOLD , 14);
    principal.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, Color.decode("#C3C1BF")));
    principal.setLayout(new GridLayout(2, 1));
    JPanel un = new JPanel();
    JPanel deux = new JPanel();
    un.setBackground(Color.decode("#F2E5CF"));
    deux.setBackground(Color.decode("#F2E5CF"));
    un.setLayout(new GridBagLayout());
    deux.setLayout(new GridBagLayout());
    JLabel jl = new JLabel("Votre compte a été créé avec succès !");
    jl.setFont(font);
    JButton ok = new JButton("Ok");
    ok.setBackground(Color.decode("#C3C1BF"));
    JFrame f = this;
    JFrame ccc = this.cc;
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        f.dispose();
        ccc.dispose();
      }
    });
    un.add(jl);
    deux.add(ok);
    principal.add(un);
    principal.add(deux);

    this.setUndecorated(true);
    this.setPreferredSize(new Dimension(this.cc.getJeu().getPreferredSize().width*37/100, this.cc.getJeu().getPreferredSize().height*14/100));
    this.setLocation((this.cc.getJeu().getPreferredSize().width - this.getPreferredSize().width)/2+(int)this.cc.getJeu().getLocation().getX(),(this.cc.getJeu().getPreferredSize().height - this.getPreferredSize().height)/2+(int)this.cc.getJeu().getLocation().getY());
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }


}
