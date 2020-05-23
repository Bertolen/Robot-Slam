package environnement.donnees;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class DEnvironnement {
	
	Point2D.Double currentCorner;
	ArrayList<Point2D.Double> cornerArray;
	Objet selection;
	
	public enum Objet {
		RIEN,
		MUR,
		ROBOT
	}
	
	public DEnvironnement() {
		
		// Initialisation du coin courant
		currentCorner = new Point2D.Double(-100,-100);
		
		// Initialisation de la liste des coins
		cornerArray = new ArrayList<>();
		
		//Aucune selection au début
		selection = Objet.RIEN;
	}

	//////////////////////////////////// Accesseurs /////////////////////////////////////

	public ArrayList<Point2D.Double> getCorners() {
		return cornerArray;
	}

	public Point2D.Double getCurrentCorner() {
		return currentCorner;
	}
	
	public Objet getSelection() {
		return selection;
	}
	
	public void setSelection(Objet s) {
		selection = s;
	}
	
	//////////////////////////// Méthodes ///////////////////////////////////////////////
	
	public void addCorner() {
		if(currentCorner.getX() > 0 && currentCorner.getY() > 0) {
			cornerArray.add((Point2D.Double) currentCorner.clone());
			currentCorner.setLocation(-100, -100);
		}
	}
	
	public void moveCorner(int x, int y) {
		currentCorner.setLocation(x, y);
	}
}
