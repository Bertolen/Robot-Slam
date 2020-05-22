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
	Point2D.Double offsetEmplacement;
	double portee;
	
	Robot robot;
	
	public CapteurProximite(Robot r) {
		robot = r;
	}
	
	public double mesure() {
		double mesure = portee;
		
		// calcul de l'emplacement et orientation globaux
		positionneGlobal();
		
		// on parcours tous les murs à partir des coins de murs
		ArrayList<Point> corners = Environnement.getDonnees().getCorners();
		for (int i = 0 ; i < corners.size() - 1 ; i++) {
			
			// Pour chaque mur on cherche son intersection avec le capteur
			Point2D.Double intersection = this.trouveIntersection(corners.get(i), corners.get(i+1));
			
			// S'il n'y a pas d'intersection on passe au mur suivant
			if (intersection == null) continue;
			
			// On garde l'intersection à la distance minimale
			double dist = emplacementGlobal.distance(intersection); 
			if (dist < mesure) {
				mesure = dist;
			}
		}
		
		return mesure;
	}

	// place le capteur dans le repère global grâce au robot.
	private void positionneGlobal() {
		// On commence par appliquer la rotation
		orientationGlobale = robot.getOrientation() + offsetOrientation;

		// On finis en appliquant la translation
		double newX = robot.getEmplacement().getX() + offsetEmplacement.getX() * Math.cos(orientationGlobale);
		double newY = robot.getEmplacement().getY() + offsetEmplacement.getY() * Math.sin(orientationGlobale);
		emplacementGlobal.setLocation(newX, newY);
	}
	
	private Point2D.Double trouveIntersection(Point C, Point D) {
		// Le mur est représenté par le segment d'extrémités A et B
		// I est le vecteur AB
		Point A = new Point((int)emplacementGlobal.getX(), (int)emplacementGlobal.getY());
		Point B = new Point((int) (A.getX() + Math.cos(orientationGlobale) * portee), (int) (A.getY() + Math.sin(orientationGlobale) * portee));
		Point2D.Double I = new Point2D.Double(B.getX() - A.getX(), B.getY() - A.getY());
		
		// Le mur est reprsésenté par le segment d'extrémités C et D
		// J est le vecteur CD
		Point2D.Double J = new Point2D.Double(D.getX() - C.getX(), D.getY() - C.getY());
		
		// Vérification que les droites ne sont pas parrallèles
		if (I.getX()*J.getY()-I.getY()*J.getX() == 0) return null;
		
		// Soit k le paramètre du point d'intersection du segment CD sur la droite AB (càd P = A + k * B)
		double k = -(A.getX()*J.getY()-C.getX()*J.getY()-J.getX()*A.getY()+J.getX()*C.getY())/(I.getX()*J.getY()-I.getY()*J.getX());
		
		// Soit m le paramètre du point d'intersection du segment AB sur la droite CD (càd P = C + m * D)
		double m = -(-I.getX()*A.getY()+I.getX()*C.getY()+I.getY()*A.getX()-I.getY()*C.getX())/(I.getX()*J.getY()-I.getY()*J.getX());
		
		// Si k et m sont entre 0 et 1 inclus alors on a intersection entre les deux segments
		if (k < 0 || m < 0 || k > 1 || m > 1) return null;
		
		// on récupère le point d'intersection grâce à k (marche aussi avec m)
		return new Point2D.Double(A.getX() + k * B.getX(), A.getY() + k * B.getY());
	}
}
