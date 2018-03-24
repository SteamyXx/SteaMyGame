package effet.effetbenefique;
import effet.Effet;
import effet.effetbenefique.benefiquemalus.Bombe;
import effet.effettour.Regeneration;
import personnage.*;
import java.util.*;

public abstract class EffetBenefique extends Effet {

  public EffetBenefique(int n) {
    super(n);
    this.justeapplique = true;
  }

  public abstract void effetPerso(Personnage p);
  public abstract void supprEffetPerso(Personnage p);

  public static void tourEffetsMoinsMoins(Personnage p) {
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof EffetBenefique && !p.getListeEffet().get(i).getJusteApplique()) {
        p.getListeEffet().get(i).setNbrTour(p.getListeEffet().get(i).getNbrTour()-1);
      }
      if (p.getListeEffet().get(i).getNbrTour() == 0) {
        p.getListeEffet().get(i).supprEffetPerso(p);
        p.getListeEffet().remove(i);
        i--;
      }
    }
  }

  public static int clean(Personnage p, int pourcentage) {
    int clean = 2;
    Random r = new Random();
    int j = 0;
    ArrayList<Effet> liste_benef = new ArrayList<Effet>();
    List<Integer> liste_i = new LinkedList<Integer>();
    for (int i = 0; i<p.getListeEffet().size(); i++) {
      if (p.getListeEffet().get(i) instanceof EffetBenefique && !(p.getListeEffet().get(i) instanceof Bombe)) {
        liste_benef.add(p.getListeEffet().get(i));
        liste_i.add(j);
        j++;
      }
    }
    int random;
    if (liste_benef.size() > 0) {
      random = r.nextInt(liste_benef.size());
      if (random < pourcentage) {
        p.getListeEffet().get(liste_i.get(random)).supprEffetPerso(p);
        p.getListeEffet().remove(liste_benef.get(liste_i.get(random)));
        clean = 1;
      } else {
        clean = 0;
      }
    }
    return clean;
  }

  public static boolean transfere(Personnage possesseur, Personnage cible, int pourcentage) {
    boolean transfere = false;
    Random r = new Random();
    int j = 0;
    ArrayList<Effet> liste_nocif = new ArrayList<Effet>();
    List<Integer> liste_i = new LinkedList<Integer>();
    for (int i = 0; i<possesseur.getListeEffet().size(); i++) {
      if (((possesseur.getListeEffet().get(i) instanceof EffetBenefique) && !(possesseur.getListeEffet().get(i) instanceof Bombe)) || (possesseur.getListeEffet().get(i) instanceof Regeneration)) {
        liste_nocif.add(possesseur.getListeEffet().get(i));
        liste_i.add(j);
        j++;
      }
    }
    int random;
    if (liste_nocif.size() > 0) {
      random = r.nextInt(liste_nocif.size());
      cible.appliquerEffet(liste_nocif.get(liste_i.get(random)), pourcentage);
      possesseur.getListeEffet().get(liste_i.get(random)).supprEffetPerso(possesseur);
      possesseur.getListeEffet().remove(liste_nocif.get(liste_i.get(random)));
      transfere = true;
    }
    return transfere;
  }
}
