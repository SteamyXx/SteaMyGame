package comptes;
import personnage.*;

public class Etage {

  private Personnage[] salles;

  public Etage(Personnage p1, Personnage p2, Personnage p3) {
    this.salles = new Personnage[6];
    this.salles[0] = p1;
    this.salles[1] = p2;
    this.salles[2] = p3;
    this.salles[3] = null;
    this.salles[4] = null;
    this.salles[5] = null;
  }


  public Etage(Personnage p1, Personnage p2, Personnage p3, Personnage p4, Personnage p5, Personnage p6) {
    this.salles = new Personnage[6];
    this.salles[0] = p1;
    this.salles[1] = p2;
    this.salles[2] = p3;
    this.salles[3] = p4;
    this.salles[4] = p5;
    this.salles[5] = p6;
  }

  public boolean isEtageSolo() {
    return this.salles[3] == null;
  }

  public Personnage getPersoSalle(int salle) {
    return this.salles[salle-1];
  }

  public void reset() {
    for (int i = 0; i<6; i++) {
      if (this.salles[i] != null) {
        this.salles[i].reajusteCaracDefaite();
      }
    }
  }
}
