package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConnexionCompte implements ActionListener {

  private Accueil acc;

  public ConnexionCompte(Accueil acc) {
    this.acc = acc;
  }

  public void actionPerformed(ActionEvent e) {
    String login = this.acc.getLogin();
    String mdp = this.acc.getMdp();
    Jeu jeu = this.acc.getJeu();
    if (login.equals("")) {
      if (mdp.equals("")) {
        JOptionPane.showMessageDialog(this.acc, "Le login et le mot de passe doivent être spécifiés", "Erreur", JOptionPane.ERROR_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this.acc, "Le login n'est pas spécifié", "Erreur", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      if (mdp.equals("")) {
        JOptionPane.showMessageDialog(this.acc, "Le mot de passe n'est pas spécifié", "Erreur", JOptionPane.ERROR_MESSAGE);
      } else {
        if (jeu.loginExiste(login) == -1 || !jeu.loginEtMdpCoincide(login, mdp)) {
          JOptionPane.showMessageDialog(this.acc, "Mot de passe ou login incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            this.acc.rafraichirFields();
            jeu.initialisationCompte(login, mdp);
            if (Integer.parseInt(jeu.getInfosCompte(login).get(4)) <= 0) {
              jeu.fenetreSuivante();
            } else {
              jeu.ajouterInventaire();
              jeu.ajouterPersoInventaire(jeu.getCompte().getPerso(0));
              jeu.fenetreN(5);
            }
          }
        }
      }
    }
  }
