package application;
import javax.swing.*;
import java.awt.*;
import application.*;
import personnage.*;
import java.awt.event.*;
import javax.swing.border.MatteBorder;


public class CoChoixPersoCombat implements MouseListener {

  private ProchainCombat pc;

  public CoChoixPersoCombat(ProchainCombat pc) {
    this.pc = pc;
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
    JPanel source = (JPanel) e.getSource();
    MatteBorder mb = (MatteBorder) source.getBorder();
    Color c = mb.getMatteColor();
    if (c.equals(Color.RED)) {
      source.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
    } else {
      source.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
    }
  }

  public void mouseExited(MouseEvent e) {
    JPanel source = (JPanel) e.getSource();
    MatteBorder mb = (MatteBorder) source.getBorder();
    Color c = mb.getMatteColor();
    if (c.equals(Color.RED)) {
      source.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
    } else {
      source.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
    }
  }

  public void mousePressed(MouseEvent e) {
    JPanel source = (JPanel) e.getSource();
    MatteBorder mb = (MatteBorder) source.getBorder();
    Color c = mb.getMatteColor();
    if (c.equals(Color.RED)) {
      source.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
    } else {
      source.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
    }
  }

  public void mouseReleased(MouseEvent e) {
    JPanel source = (JPanel) e.getSource();
    MatteBorder mb = (MatteBorder) source.getBorder();
    Color c = mb.getMatteColor();
    if (c.equals(Color.RED)) {//rouge
      if (mb.getBorderInsets().bottom != 2) {
        if (!pc.isSolo()) {
          pc.retirerPersoChoisis(source);
          source.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        }
      }
    } else {//noir
      if (mb.getBorderInsets().bottom != 2) {
        if (pc.isSolo()) {
          pc.changerSelectionPanel(source);
          source.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
        } else {
          if (pc.choixDuoPossible()) {
            pc.ajouterPersoChoisis(source);
            source.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
          }
        }
      }
    }
  }
}
