package environnement.robot;

import java.awt.Point;

public class Robot {

	Point location;
	Point orientation;
	double taille;
	
	public Robot() {
		taille = 30.0d;
		location = new Point(-100, -100);
	}

	//////////////////////////////////// Accesseurs /////////////////////////////////////

	public void setLocation(int x, int y) {
		location.setLocation(x, y);
	}

	public void setOrientation(int x, int y) {
		double alpha = x - location.getX();
		double beta = y - location.getY();
		double norme = Math.sqrt(Math.pow(alpha, 2) + Math.pow(beta, 2));
		orientation.setLocation(alpha / norme, beta / norme);
	}
	
	public Point getLocation() {
		return location;
	}
	
	public double getTaille() {
		return taille;
	}
}
