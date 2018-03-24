package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CoCreationCompte implements ActionListener {

  private CreationCompte cc;

  public CoCreationCompte(CreationCompte cc) {
    this.cc = cc;
  }

  public void actionPerformed(ActionEvent e) {
    String login = this.cc.getLogin();
    String mdp = this.cc.getMdp();
    Jeu jeu = this.cc.getJeu();

    boolean syntaxeOk = false;
    int i = 0;
    int cmp = 0;
    while (i<mdp.length() && !syntaxeOk) {
      if (Character.isDigit(mdp.charAt(i))) {
        cmp++;
      }
      if (cmp >= 2) {
        syntaxeOk = true;
      }
      i++;
    }

    if (login.equals("")) {
      if (mdp.equals("")) {
        JOptionPane.showMessageDialog(this.cc, "Le login et le mot de passe doivent être spécifiés", "Erreur", JOptionPane.ERROR_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this.cc, "Le login n'est pas spécifié", "Erreur", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      if (mdp.equals("")) {
        JOptionPane.showMessageDialog(this.cc, "Le mot de passe n'est pas spécifié", "Erreur", JOptionPane.ERROR_MESSAGE);
      } else {
        if (login.length() < 2) {
          JOptionPane.showMessageDialog(this.cc, "un login doit comporter au minimum 2 caractères", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else if (mdp.length() < 6) {
          JOptionPane.showMessageDialog(this.cc, "un mot de passe doit comporter au minimum 6 caractères", "Erreur", JOptionPane.ERROR_MESSAGE);
        }  else if (login.length() > 25) {
          JOptionPane.showMessageDialog(this.cc, "un login doit comporter au maximum 25 caractères", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else if (!syntaxeOk) {
          JOptionPane.showMessageDialog(this.cc, "un mot de passe doit comporter au minimum 2 chiffres", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
          if (jeu.loginExiste(login) != -1) {
            JOptionPane.showMessageDialog(this.cc, "Ce login existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
          } else if (jeu.mdpExiste(mdp) != -1) {
            JOptionPane.showMessageDialog(this.cc, "Ce mot de passe existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
          } else {
            jeu.creerCompte(login, mdp);
            new MessageCreationCompte(this.cc);
          }
        }
      }
    }
  }
}
