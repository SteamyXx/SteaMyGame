package application;
import javax.swing.*;
import java.awt.*;
import personnage.*;

public class BarreDeVie extends JProgressBar {

  private int v;

  public BarreDeVie() {
    super(0, 100);
    this.v = 100;
    this.setValue(100);
    this.setForeground(Color.GREEN);
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  public void couleurBarre(Personnage j) {
    if (j.getShield() > 0) {
      this.setForeground(Color.decode("#73BCE1"));
    } else {
      if (this.v > 50) {
        this.setForeground(Color.GREEN);
      } else if (this.v > 15) {
        this.setForeground(Color.ORANGE);
      } else {
        this.setForeground(Color.RED);
      }
    }
  }

  public void setV(Personnage j) {
    this.v = j.getPdv()*100/j.getPdvMax();
    if (j.getPdv() == 0) {
      this.setValue(this.v);
    } else {
      this.setValue(this.v+1);
    }
    this.couleurBarre(j);
  }

  public int getValue() {
    return this.v;
  }
}
