package effet.effetnocif;
import effet.Effet;
import effet.effetnocif.nocifmalus.Etourdissement;
import effet.effettour.DegatContinu;
import effet.effetbenefique.benefiquemalus.Immunite;
import effet.effetbenefique.benefiquemalus.Bombe;
import personnage.*;
import java.util.*;


public abstract class EffetNocif extends Effet {

  public EffetNocif(int n) {
    super(n);
  }

  public abstract void effetPerso(Personnage p);
  public abstract void supprEffetPerso(Personnage p);

  public static void tourEffetsMoinsMoins(Personnage p) {
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof EffetNocif) {
        p.getListeEffet().get(i).setNbrTour(p.getListeEffet().get(i).getNbrTour()-1);
      }
      if (p.getListeEffet().get(i).getNbrTour() == 0) {
        p.getListeEffet().get(i).supprEffetPerso(p);
        p.getListeEffet().remove(i);
        i--;
      }
    }
  }

  public static boolean clean(Personnage p) {
    boolean clean = false;
    Random r = new Random();
    ArrayList<Effet> liste_nocif = new ArrayList<Effet>();
    List<Integer> liste_i = new LinkedList<Integer>();
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if ((p.getListeEffet().get(i) instanceof EffetNocif || p.getListeEffet().get(i) instanceof Bombe || p.getListeEffet().get(i) instanceof DegatContinu) && !(p.getListeEffet().get(i) instanceof Etourdissement)) {
        liste_nocif.add(p.getListeEffet().get(i));
        liste_i.add(i);
      }
    }
    int random;
    if (liste_nocif.size() > 0) {
      random = r.nextInt(liste_nocif.size());
      p.getListeEffet().get(liste_i.get(random)).supprEffetPerso(p);
      p.getListeEffet().remove(liste_nocif.get(random));
      clean = true;
    }
    return clean;
  }

  public static boolean transfere(Personnage possesseur, Personnage cible, int pourcentage) {
    boolean transfere = false;
    Random r = new Random();
    ArrayList<Effet> liste_nocif = new ArrayList<Effet>();
    List<Integer> liste_i = new LinkedList<Integer>();
    for (int i = 0; i<possesseur.getListeEffet().size(); i++) {
      if ((possesseur.getListeEffet().get(i) instanceof EffetNocif || possesseur.getListeEffet().get(i) instanceof DegatContinu || possesseur.getListeEffet().get(i) instanceof Bombe) && !(possesseur.getListeEffet().get(i) instanceof Etourdissement)) {
        liste_nocif.add(possesseur.getListeEffet().get(i));
        liste_i.add(i);
      }
    }
    int random;
    if (liste_nocif.size() > 0) {
      random = r.nextInt(liste_nocif.size());
      cible.appliquerEffet(liste_nocif.get(random), pourcentage);
      liste_nocif.get(random).supprEffetPerso(possesseur);
      possesseur.getListeEffet().remove(liste_nocif.get(random));
      transfere = true;
    }
    return transfere;
  }
}
