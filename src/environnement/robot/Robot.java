package environnement.robot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.swing.Timer;

import carte.Carte;
import carte.ZoneCarte.ElementCarte;

public class Robot {

	Point2D.Double emplacement;
	double orientation;
	double taille;
	double vitesseLin;
	double vitesseRot;
	double lastTick;
	
	ArrayList<CapteurProximite> capteursProx;
	Carte carte;
	
	public Robot() {
		taille = 30.0d;
		emplacement = new Point2D.Double(-100, -100);
		orientation = 0.0d;
		
		// Initialisation de la liste de capteurs de proximité
		capteursProx = new ArrayList<CapteurProximite>();
		// On ajoute des capteurs au robot
		capteursProx.add(new CapteurProximite(this, 50.0d, 0.0d));
		capteursProx.add(new CapteurProximite(this, 50.0d, Math.PI / 8));
		capteursProx.add(new CapteurProximite(this, 50.0d, -Math.PI / 8));
		capteursProx.add(new CapteurProximite(this, 50.0d, Math.PI / 4));
		capteursProx.add(new CapteurProximite(this, 50.0d, -Math.PI / 4));
		capteursProx.add(new CapteurProximite(this, 50.0d, Math.PI * 3 / 8));
		capteursProx.add(new CapteurProximite(this, 50.0d, -Math.PI * 3 / 8));
		capteursProx.add(new CapteurProximite(this, 50.0d, Math.PI / 2));
		capteursProx.add(new CapteurProximite(this, 50.0d, -Math.PI / 2));
		
		// Création d'une carte vierge
		carte = new Carte((int)taille);
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
	
	public Carte getCarte() {
		return carte;
	}
	
	private void setVitLin(double vl) {
		if (vl < -this.taille) vl = -this.taille;
		if (vl > this.taille) vl = this.taille;
		
		this.vitesseLin = vl;
	}
	
	private void setVitRot(double vr) {
		if (vr < -Math.PI / 2) vr = -Math.PI / 2;
		if (vr > Math.PI / 2) vr = Math.PI / 2;
		
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
		double mesureMoyenne = 0.0d;
		double mesureMin = -1.0d;
		double mesureMax = 0.0d;
		Point2D.Double vecteurMesure = new Point2D.Double();
		Point2D.Double moyenneVecteursMesures = new Point2D.Double();
		
		// Récupération des mesures des capteurs de proximité
		for (int i = 0 ; i < capteursProx.size() ; i++) {
			double mesure = capteursProx.get(i).mesure();
			mesureMoyenne += mesure;
			
			if (mesureMin < 0 || mesure < mesureMin) {
				mesureMin = mesure;
			}
			
			if (mesure > mesureMax) {
				mesureMax = mesure;
			}
			
			//Récupère la mesure sous forme de vecteur dans le référentiel local au robot
			double orientationCapteur = capteursProx.get(i).getOffsetOrientation();
			double cos = Math.cos(orientationCapteur);
			double sin = Math.sin(orientationCapteur);
			vecteurMesure.setLocation(mesure * cos, mesure * sin);
			moyenneVecteursMesures.setLocation(moyenneVecteursMesures.getX() + vecteurMesure.getX(), moyenneVecteursMesures.getY() + vecteurMesure.getY());
			
			// mémorisation de la carte
			if(mesure != capteursProx.get(i).getPortee()) {
				Point2D.Double P = capteursProx.get(i).getPointIntersection();
				carte.set((int) P.getX(), (int) P.getY(), ElementCarte.OBSTACLE);
			}
		}
		
		mesureMoyenne /= capteursProx.size();
		moyenneVecteursMesures.setLocation(moyenneVecteursMesures.getX() / capteursProx.size(), moyenneVecteursMesures.getY() / capteursProx.size());

		// Pour un comportement où on évite juste les murs il suffit de suivre la moyenne des directions possibles
		double x = moyenneVecteursMesures.getX();
		double y = moyenneVecteursMesures.getY();

		// Pour un comportement où on cherche à longer les murs on ajoute un vecteur sur l'axe des y
		//y -= 5.0d; // pour longer les murs à gauche
		//y += 5.0d; // pour longer les murs à droite
		
		double norme = moyenneVecteursMesures.distance(0, 0);
		double angle = Math.atan2(y, x);
		
		setVitLin(Math.cos(angle) * Math.min(norme, mesureMin));
		setVitRot(angle);

		//System.out.println("vitLin = " + this.vitesseLin);
		//System.out.println("vitRot = " + this.vitesseRot);
	}
	
	// Calcul du nouvel emplacement du robot
	protected void PhysicsUpdate(double delta) {
		// On commence par appliquer la rotation
		orientation += vitesseRot * delta / 1000;
		
		// On finis en appliquant la translation
		double newX = emplacement.getX() + Math.cos(orientation) * vitesseLin * delta / 1000;
		double newY = emplacement.getY() + Math.sin(orientation) * vitesseLin * delta / 1000;
		emplacement.setLocation(newX, newY);
	}
}
