package environnement.robot;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.Timer;

public class Robot {

	Point2D.Double location;
	Point2D.Double orientation;
	double taille;
	double vitesseLin;
	double vitesseRot;
	double lastTick;
	
	public Robot() {
		taille = 30.0d;
		vitesseLin = 30.0d;
		vitesseRot = 0.01d;
		location = new Point2D.Double(-100, -100);
		orientation = new Point2D.Double(0, 1);
	}

	//////////////////////////////////// Accesseurs /////////////////////////////////////

	public void setLocation(int x, int y) {
		location.setLocation(x, y);
	}

	//calcule une nouvelle orientation pour pointer vers le lieu du click
	public void setOrientation(int x, int y) {
		double alpha = x - location.getX();
		double beta = y - location.getY();
		double norme = Math.sqrt(Math.pow(alpha, 2) + Math.pow(beta, 2));
		orientation.setLocation(alpha / norme, beta / norme);
	}
	
	public Point2D.Double getLocation() {
		return location;
	}
	
	public Point2D.Double getOrientation() {
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
		double newAngle = Math.atan2(orientation.getY(), orientation.getX()) + vitesseRot;
		orientation.setLocation(Math.cos(newAngle), Math.sin(newAngle));
		
		// On finis en appliquant la translation
		double newX = location.getX() + orientation.getX() * vitesseLin * delta / 1000;
		double newY = location.getY() + orientation.getY() * vitesseLin * delta / 1000;
		location.setLocation(newX, newY);
	}
}
