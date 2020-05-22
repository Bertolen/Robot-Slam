package environnement.donnees;

import java.awt.Point;
import java.util.ArrayList;

public class DEnvironnement {
	
	Point currentCorner;
	ArrayList<Point> cornerArray;
	Objet selection;
	
	public enum Objet {
		RIEN,
		MUR,
		ROBOT
	}
	
	public DEnvironnement() {
		
		// Initialisation du coin courant
		currentCorner = new Point(-100,-100);
		
		// Initialisation de la liste des coins
		cornerArray = new ArrayList<>();
		
		//Aucune selection au début
		selection = Objet.RIEN;
	}

	//////////////////////////////////// Accesseurs /////////////////////////////////////

	public ArrayList<Point> getCorners() {
		return cornerArray;
	}

	public Point getCurrentCorner() {
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
			cornerArray.add((Point) currentCorner.clone());
			currentCorner.setLocation(-100, -100);
		}
	}
	
	public void moveCorner(int x, int y) {
		currentCorner.setLocation(x, y);
	}
}
