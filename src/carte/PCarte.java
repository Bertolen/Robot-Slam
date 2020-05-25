package carte;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import carte.ZoneCarte.ElementCarte;
import environnement.Environnement;
import environnement.robot.Robot;

public class PCarte extends JPanel {

	public PCarte() {
		init();
	}

	public PCarte(LayoutManager layout) {
		super(layout);
		init();
	}

	public PCarte(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public PCarte(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}
	
	private void init() {
		this.setBackground(Color.darkGray);
		
		// Initialisation du Timer pour rafraichir le panneau
		Timer timer = new Timer(50, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				repaint();
			}
		});
		timer.start();		
	}
	
	//////////////////////////// Méthodes ///////////////////////////////////////////////
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Robot robot = Environnement.getRobot();
		Carte carte = robot.getCarte();
		
		// on dessine la carte
		dessineCarte(g, carte);
		
		// on dessine le robot
		dessineRobot(g, robot);
	}

	//Dessin du robot
	private void dessineRobot(Graphics g, Robot r) {
		// Socle du robot
		g.setColor(Color.gray);
		int x = (int) (r.getEmplacement().getX() - r.getTaille() / 2);
		int y = (int) (r.getEmplacement().getY() - r.getTaille() / 2);
		g.fillOval(x, y, (int) r.getTaille(), (int) r.getTaille());
		
		// trait d'orientation
		g.setColor(Color.red);
		int x2 = (int) (r.getEmplacement().getX() + Math.cos(r.getOrientation()) * r.getTaille() / 2);
		int y2 = (int) (r.getEmplacement().getY() + Math.sin(r.getOrientation()) * r.getTaille() / 2);
		g.drawLine((int) r.getEmplacement().getX(), (int) r.getEmplacement().getY(), x2, y2);
	}

	//Dessin de la carte
	private void dessineCarte(Graphics g, Carte c) {
		
		// On parcours chaque pixel du panneau
		for (int i = 0 ; i < this.getWidth() ; i++) {
			for (int j = 0 ; j < this.getHeight() ; j++) {
				
				// ici on placera un offset pour le déplacement de la caméra
				int x = i;
				int y = j;
				
				// On vérifie le contenu de la carte à l'endroit indiqué
				ElementCarte ec = c.get(x, y);
				switch(ec) {
				// Si on est dans l'inconnu on ne dessine rien
				default:
				case INCONNU:
					break;

				// quand il y a un obstacle on dessine un pixel noir
				case OBSTACLE:
					g.setColor(Color.black);
					g.drawLine(x, y, x, y);
					break;

				// quand il n'y a rien on dessine un pixel blanc
				case RIEN:
					g.setColor(Color.white);
					g.drawLine(x, y, x, y);
					break;
				}
			}
		}
	}

}
