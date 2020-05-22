package fenetre;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import environnement.Environnement;

public class PButonsEnv extends JPanel implements ActionListener {

	JButton bouttonMurs;
	JButton bouttonRobot;
	JButton bouttonLancer;
	
	///////////////////////////////////// Creators ///////////////////////////////////////
	
	public PButonsEnv() {
		init();
	}

	public PButonsEnv(LayoutManager layout) {
		super(layout);
		init();
	}

	public PButonsEnv(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public PButonsEnv(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}

	private void init() {
		//Les élements vont s'afficher de haut en bas
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Création du bouton murs
		bouttonMurs = new JButton("Murs");
		//Branchement du bouton
		bouttonMurs.addActionListener(this);
		//Ajout du bouton au panneau
		this.add(bouttonMurs);
		
		//Création du bouton murs
		bouttonRobot = new JButton("Robot");
		//Branchement du bouton
		bouttonRobot.addActionListener(this);
		//Ajout du bouton au panneau
		this.add(bouttonRobot);
		
		//Création du bouton de lancement
		bouttonLancer = new JButton("Lancer");
		//Branchement du bouton
		bouttonLancer.addActionListener(this);
		//Ajout du bouton au panneau
		this.add(bouttonLancer);
	}

	/////////////////////// Implementation ActionListener /////////////////////////////////
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(bouttonMurs.equals(e.getSource())) {
			Environnement.getControl().gestionClickBoutonMur();
		}
		if(bouttonRobot.equals(e.getSource())) {
			Environnement.getControl().gestionClickBoutonRobot();
		}
		if(bouttonLancer.equals(e.getSource())) {
			Environnement.getControl().gestionClickBoutonLancer();
		}
	}

}