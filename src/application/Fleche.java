package application;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import application.*;
import personnage.*;

public class Fleche extends JPanel {

  private int i;

  public Fleche(int i) {
    super();
    // this.setBackground(Color.decode("#C3C1BF"));
    this.setBackground(Color.RED);
    this.setPreferredSize(new Dimension(100, 100));
    this.i = i;
  }

  public void setI(int i) {
    this.i = i;
  }

  public void paintComponent(Graphics g) {
    g.setColor(Color.decode("#FF6600"));
    g.fillRect(50, 50, 23, 16);
    if (this.i == 1 || this.i == 2) {
      int[] x = new int[3];
      x[0] = 73;
      x[1] = 73;
      x[2] = 85;

      int[] y = new int[3];
      y[0] = 43;
      y[1] = 73;
      y[2] = 58;
      g.fillPolygon(x, y, 3);
      g.setColor(Color.decode("#B34E00"));
      g.drawLine(49, 50, 71, 50);
      g.drawLine(49, 49, 72, 49);

      g.drawLine(50, 65, 50, 50);
      g.drawLine(49, 64, 49, 50);

      g.drawLine(72, 50, 72, 44);
      g.drawLine(71, 50, 71, 45);

      g.drawLine(72, 66, 72, 71);
      g.drawLine(71, 66, 71, 70);
    } else {
      int[] x = new int[3];
      x[0] = 50;
      x[1] = 50;
      x[2] = 31;

      int[] y = new int[3];
      y[0] = 43;
      y[1] = 72;
      y[2] = 58;
      g.fillPolygon(x, y, 3);
      g.setColor(Color.decode("#B34E00"));
      g.drawLine(50, 50, 73, 50);
      g.drawLine(73, 65, 73, 50);
      g.drawLine(50, 50, 50, 44);

      g.drawLine(50, 49, 74, 49);
      g.drawLine(74, 64, 74, 50);
      g.drawLine(51, 50, 51, 45);

      g.drawLine(50, 66, 50, 71);
      g.drawLine(51, 66, 51, 70);
    }
  }
}
