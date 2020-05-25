package carte;

import java.util.ArrayList;

public class ZoneCarte {

	public enum ElementCarte {
		INCONNU,
		RIEN,
		OBSTACLE
	}
	
	int taille;
	ArrayList<ArrayList<ElementCarte>> contenuCarte;
	
	public ZoneCarte(int taille) {
		if (taille < 1 ) {
			System.out.println("ERROR : taille de zone de carte trop petite");
			return;
		}
		
		this.taille = taille;
		
		// Initialisation du tableau 2D
		contenuCarte = new ArrayList<ArrayList<ElementCarte>>();
		for (int i = 0 ; i < taille ; i++) {
			contenuCarte.add(new ArrayList<ElementCarte>());
			for (int j = 0 ; j < taille ; j++) {
				contenuCarte.get(i).add(ElementCarte.INCONNU);
			}
		}
	}
	
	///////////////////////////////////////////// Accesseurs /////////////////////////////
	
	public void set(int x, int y, ElementCarte ec) {
		contenuCarte.get(x).set(y, ec);
	}
	
	public ElementCarte get(int x, int y) {
		return contenuCarte.get(x).get(y);
	}

}
