package environnement.controle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import environnement.Environnement;
import environnement.donnees.DEnvironnement.Objet;
import environnement.robot.Robot;
import fenetre.FCarte;

public class CEnvironnement implements MouseListener {
	
	/////////////////////////////////// Méthodes /////////////////////////////////////////
	
	public void gestionClickBoutonMur() {
		Environnement.getDonnees().setSelection(Objet.MUR);
	}
	
	public void gestionClickBoutonRobot() {
		Environnement.getDonnees().setSelection(Objet.ROBOT);
	}
	
	public void gestionClickBoutonLancer() {
		Environnement.getRobot().lancer();
	}
	
	public void gestionClickBoutonCarte() {
		new FCarte();
	}
	
	/////////////////////// Implementation MouseListener /////////////////////////////////
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// Gestion du click droit
		if(e.getButton() == e.BUTTON1) {
			switch (Environnement.getDonnees().getSelection()) {
			case MUR:
				Environnement.getDonnees().moveCorner(e.getX(), e.getY());
				Environnement.getDonnees().addCorner();
				break;
				
			case ROBOT:
				Environnement.getRobot().setLocation(e.getX(), e.getY());
				break;
			
			default:
				break;	
			}
		}
		
		// Gestion du click gauche
		if(e.getButton() == e.BUTTON3) {
			switch (Environnement.getDonnees().getSelection()) {
			case MUR:
				Environnement.getDonnees().moveCorner(e.getX(), e.getY());
				break;
				
			case ROBOT:
				Environnement.getRobot().setOrientation(e.getX(), e.getY());
				break;
			
			default:
				break;	
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
