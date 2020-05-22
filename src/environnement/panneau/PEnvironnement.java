package environnement.panneau;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import environnement.Environnement;
import environnement.robot.Robot;

public class PEnvironnement extends JPanel {

	int cornerSize = 10;
	
	///////////////////////////////////// Creators ///////////////////////////////////////
	
	public PEnvironnement() {
		init();
	}

	public PEnvironnement(LayoutManager layout) {
		super(layout);
		init();
	}

	public PEnvironnement(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public PEnvironnement(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}

	private void init() {
		this.setBackground(Color.black);
		
		// Initialisation du Timer pour rafraichir le panneau
		Timer timer = new Timer(50, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				repaint();
			}
		});
		timer.start();
	}
	
	//////////////////////////// Méthodes ///////////////////////////////////////////////
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Dessin des murs
		g.setColor(Color.green);
		
		ArrayList<Point> cornerArray = Environnement.getDonnees().getCorners(); 
		
		// on dessine les coins placés
		for(int i = 0 ; i < cornerArray.size() ; i++) {
			g.fillOval((int)cornerArray.get(i).getX() - cornerSize / 2, (int)cornerArray.get(i).getY() - cornerSize / 2, cornerSize, cornerSize);
		}
		
		// on dessine les murs
		for(int i = 1 ; i < cornerArray.size() ; i++) {
			g.drawLine((int)cornerArray.get(i-1).getX(), (int)cornerArray.get(i-1).getY(), (int)cornerArray.get(i).getX(), (int)cornerArray.get(i).getY());
		}
		
		// on dessine le coin en cours de positionnement
		g.fillOval((int)Environnement.getDonnees().getCurrentCorner().getX() - cornerSize / 2, (int)Environnement.getDonnees().getCurrentCorner().getY() - cornerSize / 2, cornerSize, cornerSize);
		
		//Dessin du robot
		g.setColor(Color.gray);
		Robot robot = Environnement.getRobot();
		int x = (int) (robot.getLocation().getX() - robot.getTaille() / 2);
		int y = (int) (robot.getLocation().getY() - robot.getTaille() / 2);
		g.fillOval(x, y, (int) robot.getTaille(), (int) robot.getTaille());
	}
}
