package environnement.robot;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.Timer;

public class Robot {

	Point2D.Double emplacement;
	double orientation;
	double taille;
	double vitesseLin;
	double vitesseRot;
	double lastTick;
	
	public Robot() {
		taille = 30.0d;
		vitesseLin = 30.0d;
		vitesseRot = 0.01d;
		emplacement = new Point2D.Double(-100, -100);
		orientation = 0.0d;
	}

	//////////////////////////////////// Accesseurs /////////////////////////////////////

	public void setLocation(int x, int y) {
		emplacement.setLocation(x, y);
	}

	//calcule une nouvelle orientation pour pointer vers le lieu du click
	public void setOrientation(int x, int y) {
		double alpha = x - emplacement.getX();
		double beta = y - emplacement.getY();
		orientation = Math.atan2(beta, alpha);
	}
	
	public Point2D.Double getEmplacement() {
		return emplacement;
	}
	
	public double getOrientation() {
		return orientation;
	}
	
	public double getTaille() {
		return taille;
	}
	
	////////////////////////////////////// Méthodes //////////////////////////////////////
	
	public void lancer() {

		// Initialisation du Timer pour les Tick du robot
		Timer timer = new Timer(50, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Tick();
			}
		});
		timer.start();
		lastTick = System.currentTimeMillis();
	}
	
	private void Tick() {
		double delta = getDeltaTime();
		
		// Mise à jour de l'IA
		DecisionUpdate(delta);
		
		// Mise à jour de l'emplacement
		PhysicsUpdate(delta);
	}
	
	//Calcul du temps écoulé depuis le dernier tick, retour de cette valeur et mise à jour
	private double getDeltaTime() {
		double currentTick = System.currentTimeMillis();
		double delta = currentTick - lastTick;
		lastTick = currentTick;
		return delta;
	}
	
	// Prise de décision du robot (vitesse linéaire et de rotation)
	// C'est la fonction principale de ce projet
	protected void DecisionUpdate(double delta) {
		// TODO
	}
	
	// Calcul du nouvel emplacement du robot
	protected void PhysicsUpdate(double delta) {
		
		// On commence par appliquer la rotation
		orientation += vitesseRot;
		
		// On finis en appliquant la translation
		double newX = emplacement.getX() + Math.cos(orientation) * vitesseLin * delta / 1000;
		double newY = emplacement.getY() + Math.sin(orientation) * vitesseLin * delta / 1000;
		emplacement.setLocation(newX, newY);
	}
}
