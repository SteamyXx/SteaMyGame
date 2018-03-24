package effet.effetnocif.nocifmalus;
import effet.effetnocif.nocifmalus.NocifMalus;
import personnage.Personnage;

public class Etourdissement extends NocifMalus {

  public Etourdissement(int n) {
    super(n);
    this.etiquette = "Stun";
  }

  public void effetPerso(Personnage p) {

  }

  public void supprEffetPerso(Personnage p) {

  }
}
