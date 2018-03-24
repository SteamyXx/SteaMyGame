package application;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import application.*;
import personnage.*;
import runes.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CoAffichageCarac implements ActionListener {

	private Jeu jeu;

	public CoAffichageCarac(Jeu jeu) {
		this.jeu = jeu;
	}

	public void actionPerformed(ActionEvent e) {
		new DescriptionCarac(jeu);
	}

}
