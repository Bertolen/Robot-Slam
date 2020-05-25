package fenetre;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import carte.PCarte;
import environnement.Environnement;

public class FCarte extends JFrame {

	public FCarte() throws HeadlessException {
		super("Carte");
		init();
	}

	public FCarte(GraphicsConfiguration gc) {
		super("Carte",gc);
		init();
	}

	public FCarte(String title) throws HeadlessException {
		super(title);
		init();
	}

	public FCarte(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}

	private void init() {
		//Définit la taille de la fenêtre
		this.setSize(800, 600);
		
		// Création du panneau carte
		PCarte pc = new PCarte();
		//Ajout du panneau carte à la fenêtre
		this.add(pc);
		
		//Rends la fenêtre visible
		this.setVisible(true);
	}
}
