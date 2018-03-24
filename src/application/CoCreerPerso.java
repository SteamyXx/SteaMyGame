package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class CoCreerPerso implements ActionListener {

  private CreationPerso cp;
  private Jeu jeu;

  public CoCreerPerso(CreationPerso cp, Jeu jeu) {
    this.cp = cp;
    this.jeu = jeu;
  }


  public void actionPerformed(ActionEvent e) {
    jeu.initialisationCompte(jeu.getCompte().getLogin(), jeu.getCompte().getMdp());
    jeu.getCompte().ajouterPersonnage(this.cp.getI());
    String categorie1 = Jeu.categoriePerso.get(this.cp.getI());
    System.out.println("catégorie1 avant la boucle : "+categorie1);
    Random r = new Random();
    int rand = r.nextInt(32);
    String categorie2 = Jeu.categoriePerso.get(rand);
    System.out.println("catégorie2 avant la boucle : "+categorie2);
    while (categorie2.equals(categorie1)) {
      rand = r.nextInt(32);
      categorie2 = Jeu.categoriePerso.get(rand);
      System.out.println("catégorie dans la boucle : "+categorie2);
    }
    jeu.getCompte().ajouterPersonnage(rand);
    jeu.ajouterPersoRandHistoire();
    jeu.fenetreSuivante();
  }
}
