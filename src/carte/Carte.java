package carte;

import java.util.HashMap;

import carte.ZoneCarte.ElementCarte;

public class Carte {

	HashMap<Integer, HashMap<Integer, ZoneCarte>> zones;
	int tailleZones;
	
	public Carte(int tailleZ) {
		this.tailleZones = tailleZ;
		
		zones = new HashMap<Integer, HashMap<Integer, ZoneCarte>>();
		creerZone(0,0);
	}
	
	/////////////////////////////////////// Accesseurs ///////////////////////////////////
	
	// retourne la zone identifiée par (zx,zy)
	private ZoneCarte getZone(int zx, int zy) {

		// Si la zone x,y n'existe pas on retourne null
		if(!zoneExiste(zx,zy)) {
			return null;
		}
		
		return zones.get(zx).get(zy);
	}

	// met à jour la donnée identifiée par (dx,dy)
	public void set(int dx, int dy, ElementCarte ec) {
		int zx = dx / tailleZones;
		int zy = dy / tailleZones;
		int dx2 = coordDonnee(dx);
		int dy2 = coordDonnee(dy);
		
		// Si la zone x,y n'existe pas on la crée
		if(!zoneExiste(zx,zy)) {
			creerZone(zx, zy);
		}
		
		ZoneCarte zone = getZone(zx,zy);
		zone.set(dx2, dy2, ec);		
	}

	// retourne la donnée identifiée par (dx,dy)
	public ElementCarte get(int dx, int dy) {
		int zx = dx / tailleZones;
		int zy = dy / tailleZones;
		int dx2 = coordDonnee(dx);
		int dy2 = coordDonnee(dy);

		// Si la zone n'existe pas on retourne inconnu
		if(!zoneExiste(zx, zy)) {
			return ElementCarte.INCONNU;
		}
		
		// Si la zone existe on retourne le contenu
		return getZone(zx, zy).get(dx2, dy2);
	}
	
	/////////////////////////////////////// Méthodes ///////////////////////////////////
	
	private void creerZone(int zx, int zy) {
		// Si la colonne x n'existe pas on la crée
		if(zones.get(zx) == null) {
			zones.put(zx, new HashMap<Integer, ZoneCarte>());
		}
		
		// Si la zone x,y n'existe pas on la crée
		if(zones.get(zx).get(zy) == null) {
			zones.get(zx).put(zy, new ZoneCarte(tailleZones));
		}
	}
	
	// retourne vrai si la zone indiquée existe
	public boolean zoneExiste(int zx, int zy) {
		return (zones.get(zx) != null && zones.get(zx).get(zy) != null);
	}
	
	//retourne la coordonné d'une donnée dans une zone à partir de la coordonnée globale
	// de cette donnée
	private int coordDonnee(int cd) {
		int m = cd % tailleZones;
		if (m < 0) {
			m += tailleZones;
		}
		return m;
	}

}
