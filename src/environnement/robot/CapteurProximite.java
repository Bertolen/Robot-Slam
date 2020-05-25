package environnement.robot;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import environnement.Environnement;

public class CapteurProximite {

	double orientationGlobale;
	Point2D.Double emplacementGlobal;
	double offsetOrientation;
	double portee;
	
	Robot robot;
	
	// Point d'origine du faisceau
	Point2D.Double A;
	// Point max du faisceau
	Point2D.Double B;
	// Point d'intersection du faisceau
	Point2D.Double P;
	
	public CapteurProximite(Robot robot, double portee, double offsetOrientation) {
		this.robot = robot;
		this.offsetOrientation = offsetOrientation;
		this.portee = portee;
		
		this.emplacementGlobal = new Point2D.Double();
		this.A = new Point2D.Double();
		this.B = new Point2D.Double();
		this.P = new Point2D.Double();
	}
	
	////////////////////////////////////// Accesseurs ////////////////////////////////
	
	public Point2D.Double getPointSource() {
		return A;
	}
	
	public Point2D.Double getPointFinal() {
		return B;
	}
	
	public Point2D.Double getPointIntersection() {
		return P;
	}
	
	public double getOffsetOrientation() {
		return this.offsetOrientation;
	}
	
	public double getPortee () {
		return portee;
	}
	
	////////////////////////////////////// Méthodes //////////////////////////////////
	
	public double mesure() {
		double mesure = portee;
		
		// calcul de l'emplacement et orientation globaux
		positionneGlobal();
		P = B;
		
		// on parcours tous les murs à partir des coins de murs
		ArrayList<Point2D.Double> corners = Environnement.getDonnees().getCorners();
		for (int i = 0 ; i < corners.size() - 1 ; i++) {
			
			// Pour chaque mur on cherche son intersection avec le capteur
			Point2D.Double intersection = this.trouveIntersection(corners.get(i), corners.get(i+1));
			
			// S'il n'y a pas d'intersection on passe au mur suivant
			if (intersection == null) continue;
			
			// On garde l'intersection à la distance minimale
			double dist = emplacementGlobal.distance(intersection); 
			if (dist < mesure) {
				mesure = dist;
				P = intersection;
			}
		}
		
		return mesure;
	}

	// place le capteur dans le repère global grâce au robot.
	private void positionneGlobal() {
		// On commence par appliquer la rotation
		orientationGlobale = robot.getOrientation() + offsetOrientation;

		// On finis en appliquant la translation
		double newX = robot.getEmplacement().getX() + robot.getTaille() / 2 * Math.cos(orientationGlobale);
		double newY = robot.getEmplacement().getY() + robot.getTaille() / 2 * Math.sin(orientationGlobale);
		emplacementGlobal.setLocation(newX, newY);
		
		// Calcul de l'emplacement des extrèmités du faisceau
		A.setLocation(emplacementGlobal.getX(), emplacementGlobal.getY());
		B.setLocation(A.getX() + Math.cos(orientationGlobale) * portee, A.getY() + Math.sin(orientationGlobale) * portee);
	}
	
	private Point2D.Double trouveIntersection(Point2D.Double C, Point2D.Double D) {
		// Le faisceau du capteur est représenté par le segment d'extrémités A et B
		// I est le vecteur AB
		Point2D.Double I = new Point2D.Double(B.getX() - A.getX(), B.getY() - A.getY());
		
		// Le mur est reprsésenté par le segment d'extrémités C et D
		// J est le vecteur CD
		Point2D.Double J = new Point2D.Double(D.getX() - C.getX(), D.getY() - C.getY());
		
		// Vérification que les droites ne sont pas parrallèles
		if (I.getX()*J.getY()-I.getY()*J.getX() == 0) return null;
		
		// Soit m le paramètre du point d'intersection du segment AB sur la droite CD (càd P = C + m * J)
		double m = (I.getX() * (A.getY() - C.getY()) + I.getY() * (C.getX() - A.getX())) / (I.getX()*J.getY()-I.getY()*J.getX());
		
		// Soit k le paramètre du point d'intersection du segment CD sur la droite AB (càd P = A + k * I)
		double k = (C.getX() + m * J.getX() - A.getX()) / I.getX();
		
		// Si k et m sont entre 0 et 1 inclus alors on a intersection entre les deux segments
		if (k < 0 || m < 0 || k > 1 || m > 1) return null;
		
		// on récupère le point d'intersection grâce à k (marche aussi avec m)
		return new Point2D.Double(A.getX() + k * I.getX(), A.getY() + k * I.getY());
	}
}
