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
		capteursProx.add(new CapteurProximite(this, 50.0d, Math.PI/2));
		capteursProx.add(new CapteurProximite(this, 50.0d, -Math.PI/2));
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
	
	private void setVitLin(double vl) {
		if (vl < -this.taille) vl = -this.taille;
		if (vl > this.taille) vl = this.taille;
		
		this.vitesseLin = vl;
	}
	
	private void setVitRot(double vr) {
		if (vr < -Math.PI / 4) vr = -Math.PI / 4;
		if (vr > Math.PI / 4) vr = Math.PI / 4;
		
		this.vitesseRot = vr;
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
		Point2D.Double moyenneVecteursMesures = new Point2D.Double();
		Point2D.Double vecteurMesure = new Point2D.Double();
		
		for (int i = 0 ; i < capteursProx.size() ; i++) {
			double mesure = capteursProx.get(i).mesure();
			
			mesureMoyenne += mesure;
			
			if (mesureMin < 0 || mesure < mesureMin) {
				mesureMin = mesure;
			}
			
			if (mesure > mesureMax) {
				mesureMax = mesure;
			}
			
			vecteurMesure.setLocation(mesure * Math.cos(capteursProx.get(i).getOffsetOrientation()), mesure * Math.sin(capteursProx.get(i).getOffsetOrientation()));
			moyenneVecteursMesures.setLocation(moyenneVecteursMesures.getX() + vecteurMesure.getX(), moyenneVecteursMesures.getY() + vecteurMesure.getY());
		}
		
		mesureMoyenne /= capteursProx.size();
		moyenneVecteursMesures.setLocation(moyenneVecteursMesures.getX() / capteursProx.size(), moyenneVecteursMesures.getY() / capteursProx.size());
		
		/*
		if (mesureMin >= this.taille / 4) {
			vitesseLin = mesureMoyenne;
			vitesseRot = 0.0d;			
		} else {
			vitesseLin = 0.0d;
			vitesseRot = 0.02d;
		}
		*/

		double x = moyenneVecteursMesures.getX();
		double y = moyenneVecteursMesures.getY();
		
		setVitLin(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
		setVitRot(Math.atan2(y, x));
		
		System.out.println("vitesseLin : " + vitesseLin);
		System.out.println("vitesseRot : " + vitesseRot);
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
