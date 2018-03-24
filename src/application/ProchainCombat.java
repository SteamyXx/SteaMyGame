package application;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProchainCombat extends JFrame {

  private Jeu jeu;
  private int etage;
  private java.util.List<JPanel> listePanelChoixPerso;
  private java.util.List<Integer> persoChoisis;
  private boolean solo;

  public ProchainCombat(Jeu jeu) {
    super("Prochain Combat");
    this.jeu = jeu;
    this.etage = jeu.getInv().getEtage();
    this.solo = this.jeu.getCompte().getTour().getEtage(this.etage).isEtageSolo();
    this.listePanelChoixPerso = new ArrayList<JPanel>();
    this.persoChoisis = new ArrayList<Integer>();
    if (this.solo) {
      this.persoChoisis.add(0);
    } else {
      this.persoChoisis.add(0);
      this.persoChoisis.add(1);
    }
    Combat.setEtage(this.etage);
    JPanel principal = new JPanel();
    principal.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#C3C1BF")));
    principal.setLayout(new BorderLayout());

    Font font18 = new Font("Arial", Font.BOLD , 18);
    Font font16 = new Font("Arial", Font.BOLD , 16);
    Font font14 = new Font("Arial", Font.BOLD , 14);
    Font fontImleryth = new Font("Special Elite", Font.BOLD, 20);

    JPanel titre = new JPanel();
    titre.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.decode("#C3C1BF")));
    titre.setBackground(Color.decode("#F2E5CF"));
    titre.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5,0,5,0);
    JLabel jl = new JLabel("Etage "+this.etage);
    if (this.etage == 35) {
      jl.setText("Etage Final");
    }
    jl.setFont(font18);
    titre.add(jl, c);
    principal.add(titre, BorderLayout.NORTH);
    JPanel salles = new JPanel();
    salles.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.decode("#F2E5CF")));
    salles.setBackground(Color.decode("#F2E5CF"));
    salles.setLayout(new GridLayout(1, 3));

    JPanel salle1 = new JPanel();
    JPanel salle2 = new JPanel();
    JPanel salle3 = new JPanel();
    salle1.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
    salle2.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
    salle3.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
    salle1.setBackground(Color.decode("#F2E5CF"));
    salle2.setBackground(Color.decode("#F2E5CF"));
    salle3.setBackground(Color.decode("#F2E5CF"));
    salle1.setLayout(new BorderLayout());
    salle2.setLayout(new BorderLayout());
    salle3.setLayout(new BorderLayout());

    JPanel titresalle1 = new JPanel();
    JPanel titresalle2 = new JPanel();
    JPanel titresalle3 = new JPanel();
    titresalle1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
    titresalle2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
    titresalle3.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
    titresalle1.setBackground(Color.decode("#F2E5CF"));
    titresalle2.setBackground(Color.decode("#F2E5CF"));
    titresalle3.setBackground(Color.decode("#F2E5CF"));
    titresalle1.setLayout(new GridBagLayout());
    titresalle2.setLayout(new GridBagLayout());
    titresalle3.setLayout(new GridBagLayout());

    JLabel s1 = new JLabel("Salle 1 : ");
    JLabel s2 = new JLabel("Salle 2 : ");
    JLabel s3 = new JLabel("Salle 3 : ");
    s1.setFont(font14);
    s2.setFont(font14);
    s3.setFont(font14);

    titresalle1.add(s1);
    titresalle2.add(s2);
    titresalle3.add(s3);
    salle1.add(titresalle1, BorderLayout.NORTH);
    salle2.add(titresalle2, BorderLayout.NORTH);
    salle3.add(titresalle3, BorderLayout.NORTH);

    JPanel persosalle1 = null;
    JPanel persosalle2 = null;
    JPanel persosalle3 = null;
    if (!this.solo) {
      persosalle1 = new JPanel();
      persosalle2 = new JPanel();
      persosalle3 = new JPanel();
      persosalle1.setPreferredSize(new Dimension(30, 30));
      persosalle2.setPreferredSize(new Dimension(30, 30));
      persosalle3.setPreferredSize(new Dimension(30, 30));
      persosalle1.setBackground(Color.decode("#F2E5CF"));
      persosalle2.setBackground(Color.decode("#F2E5CF"));
      persosalle3.setBackground(Color.decode("#F2E5CF"));
      persosalle1.setLayout(new GridLayout(2, 1));
      persosalle2.setLayout(new GridLayout(2, 1));
      persosalle3.setLayout(new GridLayout(2, 1));
    }
    JPanel perso1salle1 = new JPanel();
    JPanel perso1salle2 = new JPanel();
    JPanel perso1salle3 = new JPanel();
    perso1salle1.setBackground(Color.decode("#F2E5CF"));
    perso1salle2.setBackground(Color.decode("#F2E5CF"));
    perso1salle3.setBackground(Color.decode("#F2E5CF"));
    perso1salle1.setLayout(new GridBagLayout());
    perso1salle2.setLayout(new GridBagLayout());
    perso1salle3.setLayout(new GridBagLayout());

    JLabel ps1 = new JLabel(this.jeu.getCompte().getTour().getPerso(this.etage, 1).getClass().getName().substring(11));
    JLabel ps2 = new JLabel(this.jeu.getCompte().getTour().getPerso(this.etage, 2).getClass().getName().substring(11));
    JLabel ps3 = new JLabel(this.jeu.getCompte().getTour().getPerso(this.etage, 3).getClass().getName().substring(11));
    ps1.setFont(font16);
    ps2.setFont(font16);
    ps3.setFont(font16);


    perso1salle1.add(ps1);
    perso1salle2.add(ps2);
    perso1salle3.add(ps3);

    if (!this.solo) {

      persosalle1.add(perso1salle1);
      persosalle2.add(perso1salle2);
      persosalle3.add(perso1salle3);
      JPanel perso1salle4 = new JPanel();
      JPanel perso1salle5 = new JPanel();
      JPanel perso1salle6 = new JPanel();
      perso1salle4.setBackground(Color.decode("#F2E5CF"));
      perso1salle5.setBackground(Color.decode("#F2E5CF"));
      perso1salle6.setBackground(Color.decode("#F2E5CF"));
      perso1salle4.setLayout(new GridBagLayout());
      perso1salle5.setLayout(new GridBagLayout());
      perso1salle6.setLayout(new GridBagLayout());

      JLabel ps4 = new JLabel(this.jeu.getCompte().getTour().getPerso(this.etage, 4).getClass().getName().substring(11));
      JLabel ps5 = new JLabel(this.jeu.getCompte().getTour().getPerso(this.etage, 5).getClass().getName().substring(11));
      JLabel ps6 = new JLabel(this.jeu.getCompte().getTour().getPerso(this.etage, 6).getClass().getName().substring(11));
      ps4.setFont(font16);
      ps5.setFont(font16);
      if (ps6.getText().equals("Imleryth")) {
        ps6.setText("<html><font color = #"+Color.RED+" >Imleryth</font></html>");
        ps6.setFont(fontImleryth);
      } else {
        ps6.setFont(font16);
      }


      perso1salle4.add(ps4);
      perso1salle5.add(ps5);
      perso1salle6.add(ps6);

      persosalle1.add(perso1salle4);
      persosalle2.add(perso1salle5);
      persosalle3.add(perso1salle6);
    }

    if (!this.solo) {
      salle1.add(persosalle1, BorderLayout.CENTER);
      salle2.add(persosalle2, BorderLayout.CENTER);
      salle3.add(persosalle3, BorderLayout.CENTER);
    } else {
      salle1.add(perso1salle1, BorderLayout.CENTER);
      salle2.add(perso1salle2, BorderLayout.CENTER);
      salle3.add(perso1salle3, BorderLayout.CENTER);
    }

    salles.add(salle1);
    salles.add(salle2);
    salles.add(salle3);

    principal.add(salles, BorderLayout.CENTER);
    // }


    JPanel bas = new JPanel();
    bas.setLayout(new BorderLayout());
    bas.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.decode("#C3C1BF")));
    bas.setBackground(Color.decode("#F2E5CF"));

    JPanel titreChoixPerso = new JPanel();
    titreChoixPerso.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.decode("#F2E5CF")));
    titreChoixPerso.setBackground(Color.decode("#F2E5CF"));
    titreChoixPerso.setLayout(new GridBagLayout());
    JLabel labelChoixPerso = null;
    if (this.solo) {
      labelChoixPerso = new JLabel("Choix du personnage");
    } else {
      labelChoixPerso = new JLabel("Choix des personnages");
    }
    labelChoixPerso.setFont(font16);
    labelChoixPerso.setBackground(Color.decode("#F2E5CF"));
    titreChoixPerso.add(labelChoixPerso);
    bas.add(titreChoixPerso, BorderLayout.NORTH);


    JPanel choixPerso = new JPanel();
    choixPerso.setBorder(BorderFactory.createMatteBorder(15, 5, 0, 5, Color.decode("#F2E5CF")));
    choixPerso.setBackground(Color.decode("#F2E5CF"));
    choixPerso.setLayout(new GridLayout(1, 4));
    for (int i = 0; i<4; i++) {
      JPanel perso = new JPanel();
      perso.setPreferredSize(new Dimension(50, 30));
      Personnage p = this.jeu.getCompte().getPerso(i);
      CoChoixPersoCombat ccpc = new CoChoixPersoCombat(this);
      if (p != null) {
        perso.addMouseListener(ccpc);
      }
      if (this.persoChoisis.contains(i)) {
        perso.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
      } else {
        perso.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
      }
      perso.setBackground(Color.decode("#F2E5CF"));
      perso.setLayout(new GridBagLayout());
      JLabel nomPerso = new JLabel((p != null) ? p.getClass().getName().substring(11) : "");
      nomPerso.setBackground(Color.decode("#F2E5CF"));
      nomPerso.setFont(font16);
      perso.add(nomPerso);
      this.listePanelChoixPerso.add(perso);
      choixPerso.add(perso);
    }
    bas.add(choixPerso, BorderLayout.CENTER);

    JPanel combattre = new JPanel();
    combattre.setBackground(Color.decode("#F2E5CF"));
    combattre.setLayout(new GridBagLayout());
    JButton combat = new JButton("Combattre");
    ProchainCombat current = this;
    int etagebis = this.etage;
    boolean solobis = this.solo;
    combat.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        if (solobis) {//combat solo
          jeu.enregistrerDonneesCompte();
          Personnage choisis = jeu.getCompte().getPerso(current.getPersosChoisis().get(0));
          choisis.reinitialiserBoostRunes();
          choisis.reajusteCarac();
          jeu.getCombatSolo().setP(choisis);
          jeu.getCombatSolo().setIA(jeu.getCompte().getTour().getPerso(etagebis, 1));
          jeu.getCombatSolo().rafraichirTout();
          jeu.getCombatSolo().resetTour();
          jeu.fenetreN(6);
          current.dispose();
        } else {
          if (current.getPersosChoisis().size() != 2) {
            JOptionPane.showMessageDialog(current, "Vous devez choisir 2 compagnons !", "Erreur", JOptionPane.ERROR_MESSAGE);
          } else {//combat duo
            jeu.fenetreN(7);
            jeu.enregistrerDonneesCompte();
            jeu.getCompte().getPerso(current.getPersosChoisis().get(0)).reinitialiserBoostRunes();
            jeu.getCompte().getPerso(current.getPersosChoisis().get(1)).reinitialiserBoostRunes();
            jeu.getCompte().getPerso(current.getPersosChoisis().get(0)).reajusteCarac();
            jeu.getCompte().getPerso(current.getPersosChoisis().get(1)).reajusteCarac();
            jeu.initCombatDuo(current.getPersosChoisis().get(0), current.getPersosChoisis().get(1));
            jeu.placerFleche();
            jeu.getCombatDuo().setCible(1);
            jeu.getCombatDuo().setAllBoutonPBA(false);

            Personnage nouveauLanceur = jeu.getCombatDuo().getJoueurCourant();
            Personnage[] nouveauTabEnnemisLanceur = jeu.getCombatDuo().getEnnemisLanceur();
            Personnage nouvelAllieLanceur = jeu.getCombatDuo().getAllieLanceur();
            Personnage[] nouveauTabAlliesLanceur = new Personnage[2];
            nouveauTabAlliesLanceur[0] = nouvelAllieLanceur;
            nouveauTabAlliesLanceur[1] = nouveauLanceur;

            System.out.println(nouveauTabAlliesLanceur[0].getClass().getName() + nouveauTabAlliesLanceur[0].getVitesse());
            System.out.println(nouveauTabAlliesLanceur[1].getClass().getName() + nouveauTabAlliesLanceur[1].getVitesse());
            System.out.println(nouveauTabEnnemisLanceur[0].getClass().getName() + nouveauTabEnnemisLanceur[0].getVitesse());
            System.out.println(nouveauTabEnnemisLanceur[1].getClass().getName() + nouveauTabEnnemisLanceur[1].getVitesse());


            if (nouveauLanceur.getPossedePassif() && nouveauLanceur.getPassifActivable()) {
              jeu.getCombatDuo().afficherPassif(nouveauLanceur);
              Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  Combat.ajouterCommentaire(" ");
                  jeu.getCombatDuo().tirets();
                  Combat.ajouterCommentaire(" ");
                  nouveauLanceur.sort4(nouveauTabAlliesLanceur, nouveauTabEnnemisLanceur);
                  jeu.getCombatDuo().miseAJourPBA();
                  jeu.getCombatDuo().miseAJourPC();
                  if (jeu.getCombatDuo().getIntJoueurCourant() > 2) {
                    jeu.getCombatDuo().setCible(0);
                    Timer timer = new Timer(1000, new CoGestionSorts(jeu.getCombatDuo()));
                    timer.setRepeats(false);
                    timer.start();
                  } else {
                    jeu.getCombatDuo().setAllBoutonPBA(true);
                  }
                }
              });
              timer.setRepeats(false);
              timer.start();
            } else {
              jeu.getCombatDuo().miseAJourPBA();
              jeu.getCombatDuo().miseAJourPC();
              if (jeu.getCombatDuo().getIntJoueurCourant() > 2) {
                jeu.getCombatDuo().setCible(0);
                Timer timer = new Timer(2000, new CoGestionSorts(jeu.getCombatDuo()));
                timer.setRepeats(false);
                timer.start();
              } else {
                jeu.getCombatDuo().setAllBoutonPBA(true);
              }
            }


            current.dispose();
          }
        }

        // jeu.getCompte().getPerso(0).reinitialiserBoostRunes();
        // jeu.getCompte().getPerso(0).reajusteCarac();
        // jeu.setP(jeu.getCompte().getPerso(0));
        // jeu.setIA(jeu.getCompte().getTour().getPerso(etagebis, 1));
        // jeu.rafraichirCombat();
        // jeu.fenetreN(5);
      }
    });
    combat.setBackground(Color.decode("#C3C1BF"));
    c.insets = new Insets(10,0,10,0);
    combattre.add(combat, c);
    bas.add(combattre, BorderLayout.SOUTH);

    principal.add(bas, BorderLayout.SOUTH);

    int height = 52;
    if (this.solo) {
      height = 48;
    }

    this.setAlwaysOnTop(true);
    this.setPreferredSize(new Dimension(jeu.getPreferredSize().width*45/100, jeu.getPreferredSize().height*height/100));
    this.setLocation((jeu.getPreferredSize().width - this.getPreferredSize().width)/2+(int)jeu.getLocation().getX(),(jeu.getPreferredSize().height - this.getPreferredSize().height)/2+(int)jeu.getLocation().getY());
    this.setContentPane(principal);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }

  public java.util.List<Integer> getPersosChoisis() {
    return this.persoChoisis;
  }

  public boolean isSolo() {
    return this.solo;
  }

  public void changerSelectionPanel(JPanel selectionne) {
    this.listePanelChoixPerso.get(this.persoChoisis.get(0)).setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
    this.persoChoisis.remove(0);
    this.persoChoisis.add(this.listePanelChoixPerso.indexOf(selectionne));
  }

  public void retirerPersoChoisis(JPanel selectionne) {
    this.persoChoisis.remove(this.persoChoisis.indexOf(this.listePanelChoixPerso.indexOf(selectionne)));
  }

  public void ajouterPersoChoisis(JPanel selectionne) {
    this.persoChoisis.add(this.listePanelChoixPerso.indexOf(selectionne));
  }

  public boolean choixDuoPossible() {
    return !this.solo && this.persoChoisis.size() < 2;
  }
}
