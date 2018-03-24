package effet;
import personnage.Personnage;

public abstract class Effet {
  protected int nbr_tour;
  protected String etiquette;
  protected boolean justeapplique;

  public Effet(int n) {
    this.nbr_tour = n;
  }

  public boolean getJusteApplique() {
    return this.justeapplique;
  }

  public void setJusteApplique(boolean n) {
    this.justeapplique = n;
  }

  public int getNbrTour() {
    return this.nbr_tour;
  }

  public void setNbrTour(int n) {
    this.nbr_tour = n;
  }

  public String getEtiquette() {
    return this.etiquette;
  }

  public String toString() {
    String tour = null;
    if (this.nbr_tour > 1) {
      tour = " tours";
    } else {
      tour = " tour";
    }
    return this.etiquette+" de "+this.nbr_tour+tour;
  }

  public abstract void effetPerso(Personnage p);
  public abstract void supprEffetPerso(Personnage p);

}
