package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class Histoire extends JPanel {

  private Jeu jeu;
  private JTextArea findujeu;

  public Histoire(Jeu jeu, int i) {
    super();
    this.jeu = jeu;
    this.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    this.setLayout(new BorderLayout());
    Font font25 = new Font("Arial", Font.BOLD ,25);
    Font font20 = new Font("Arial", Font.BOLD ,20);
    this.findujeu = new JTextArea();
    this.findujeu.setFont(font20);
    this.findujeu.setText("\n\n"+Application.getTextFromFile("data/histoire"+i+".txt"));
    this.findujeu.setBackground(Color.decode("#F2E5CF"));
    this.findujeu.setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, Color.decode("#C3C1BF")));
    this.findujeu.setLineWrap(true);
    this.findujeu.setWrapStyleWord(true);
    this.findujeu.setEditable(false);
    this.findujeu.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
    JPanel tmp = new JPanel();
    tmp.setPreferredSize(new Dimension(this.jeu.getPreferredSize().width, 75));
    tmp.setLayout(new GridBagLayout());
    tmp.setBackground(Color.decode("#F2E5CF"));
    JButton continuer = null;
    if (i == 1) {
      continuer = new JButton("J'ai choisis ...");
    } else if (i == 2 || i == 4 || i == 5 || i == 6) {
      continuer = new JButton("Continuez");
    } else if (i == 3) {
      continuer = new JButton("J'aimerais avoir ...");
    }
    continuer.setFont(font25);
    continuer.setPreferredSize(new Dimension(500, 50));
    continuer.setBackground(Color.decode("#C3C1BF"));
    if (i == 1) {
      continuer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jeu.fenetreSuivante();
        }
      });
    } else if (i == 2) {
      continuer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jeu.ajouterInventaire();
          jeu.ajouterPersoInventaire(jeu.getCompte().getPerso(0));
          jeu.fenetreN(5);
        }
      });
    } else if (i == 3) {
      continuer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jeu.fenetreN(9);
        }
      });
    } else if (i == 4 || i == 5 || i == 6) {
      continuer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jeu.fenetreN(5);
        }
      });
    }
    tmp.add(continuer);
    this.add(this.findujeu, BorderLayout.CENTER);
    this.add(tmp, BorderLayout.SOUTH);
  }

  public void ajouterQuatriemePerso(String str) {
    String texte = this.findujeu.getText();
    texte = texte.replace("*", str);
    texte = texte.replace("*", str);
    this.findujeu.setText(texte);
  }

  public void ajouterPerso(String str) {
    this.findujeu.append(" "+str);
  }
}
