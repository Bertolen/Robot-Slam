package environnement.robot;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.swing.Timer;

public class Robot {

	Point2D.Double emplacement;
	double orientation;
	double taille;
	double vitesseLin;
	double vitesseRot;
	double lastTick;
	
	ArrayList<CapteurProximite> capteursProx;
	
	public Robot() {
		taille = 30.0d;
		emplacement = new Point2D.Double(-100, -100);
		orientation = 0.0d;
		
		// Initialisation de la liste de capteurs de proximité
		capteursProx = new ArrayList<CapteurProximite>();
		// Temp : on ajoute un unique capteur au robot
		capteursProx.add(new CapteurProximite(this, 50.0d, 0.0d));
		capteursProx.add(new CapteurProximite(this, 50.0d, Math.PI/4));
		capteursProx.add(new CapteurProximite(this, 50.0d, -Math.PI/4));
	}

	//////////////////////////////////// Accesseurs /////////////////////////////////////

	public void setLocation(int x, int y) {
		emplacement.setLocation(x, y);
	}
	
	public ArrayList<CapteurProximite> getCapteursProx() {
		return capteursProx;
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
		double mesureMoyenne = 0.0d;
		double mesureMin = -1.0d;
		double mesureMax = 0.0d;
		
		for (int i = 0 ; i < capteursProx.size() ; i++) {
			mesureMoyenne += capteursProx.get(i).mesure();
			
			if (mesureMin < 0 || capteursProx.get(i).mesure() < mesureMin) {
				mesureMin = capteursProx.get(i).mesure();
			}
			
			if ( capteursProx.get(i).mesure() > mesureMax) {
				mesureMax = capteursProx.get(i).mesure();
			}
		}
		
		mesureMoyenne /= capteursProx.size();
		System.out.println("mesureMoyenne : " + mesureMoyenne);
		System.out.println("mesureMin : " + mesureMin);
		System.out.println("mesureMax : " + mesureMax);
		
		if (mesureMoyenne >= this.taille && mesureMin >= this.taille / 4) {
			vitesseLin = mesureMoyenne;
			vitesseRot = 0.0d;			
		} else {
			vitesseLin = 0.0d;
			vitesseRot = 0.02d;
		}
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
