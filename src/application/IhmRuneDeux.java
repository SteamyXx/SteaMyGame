package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import java.awt.event.*;

public class IhmRuneDeux extends JPanel {

  private IhmRune r;

  public IhmRuneDeux(IhmRune r) {
    super();
    this.r = r;
    this.setLayout(new GridBagLayout());
    this.setPreferredSize(new Dimension(100, 50));
    this.setBackground(Color.decode("#F2E5CF"));
    this.add(this.r);
  }
}
