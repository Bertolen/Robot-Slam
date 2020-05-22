package fenetre;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import environnement.Environnement;

public class FEnvironnement extends JFrame {
	
	public FEnvironnement() throws HeadlessException {
		super("Environnement");
		init();
	}

	public FEnvironnement(GraphicsConfiguration gc) {
		super("Environnement", gc);
		init();
	}

	public FEnvironnement(String title) throws HeadlessException {
		super(title);
		init();
	}

	public FEnvironnement(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}

	protected void init() {
		//Définit la taille de la fenêtre
		this.setSize(800, 600);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Termine le processus lorsqu'on ferme la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Les élements vont s'afficher côte à côte
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));
		
		//Création de l'environnement
		Environnement.creerEnvironnement();
		//Ajout du panneau à la fenêtre
		this.add(Environnement.getVue());
		
		//Création du panneau de boutons
		PButonsEnv pb = new PButonsEnv();
		//Ajout du panneau à la fenêtre
		this.add(pb);
		
		//Rends la fenêtre visible
		this.setVisible(true);
	}
}
