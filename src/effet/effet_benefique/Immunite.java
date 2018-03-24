package effet.effetbenefique.benefiquemalus;
import effet.effetbenefique.benefiquemalus.BenefiqueMalus;
import personnage.Personnage;

public class Immunite extends BenefiqueMalus {

  public Immunite(int n) {
    super(n);
    this.etiquette = "Immun";
  }

  public void effetPerso(Personnage p) {

  }

  public void supprEffetPerso(Personnage p) {

  }
}
