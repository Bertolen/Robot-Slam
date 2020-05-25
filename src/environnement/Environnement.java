package environnement;

import carte.Carte;
import environnement.controle.CEnvironnement;
import environnement.donnees.DEnvironnement;
import environnement.panneau.PEnvironnement;
import environnement.robot.Robot;

public class Environnement {

	static DEnvironnement donneesEnv;
	static CEnvironnement controlEnv;
	static PEnvironnement vueEnv;
	static Robot robot;
	
	static public void creerEnvironnement() {
		if(donneesEnv == null) {
			donneesEnv = new DEnvironnement();
		}

		if(controlEnv == null) {
			controlEnv = new CEnvironnement();
		}

		if(vueEnv == null) {
			vueEnv = new PEnvironnement();
		}
		
		vueEnv.addMouseListener(controlEnv);

		if(robot == null) {
			robot = new Robot();
		}
	}
	
	/////////////////////////////// Accesseurs ///////////////////////////////////////
	
	static public CEnvironnement getControl() {
		return controlEnv;
	}
	
	static public DEnvironnement getDonnees() {
		return donneesEnv;
	}
	
	static public PEnvironnement getVue() {
		return vueEnv;
	}
	
	static public Robot getRobot() {
		return robot;
	}
}
