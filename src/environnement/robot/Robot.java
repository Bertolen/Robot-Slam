package environnement.robot;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Robot {

	Point location;
	Point2D.Double orientation;
	double taille;
	double vitesse;
	
	public Robot() {
		taille = 30.0d;
		vitesse = 30.0d;
		location = new Point(-100, -100);
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
	
	public Point getLocation() {
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
		System.out.println("Lancer le robot");
	}
}
