package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JLabel;

import server.Critter;

public class ButtonListener implements ActionListener {
	GUI gui;
	WorldCanvas world;
	
	public ButtonListener(GUI g) {
		gui = g;
		world = g.c;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(gui.zIn)) {
			zoom();
			gui.zoomedAt.setText("Zoomed in at location: " + world.world.getSelected());
		}
		else if(arg0.getSource().equals(gui.zOut)) {
			gui.c.repaint();
		}
		else if(arg0.getSource().equals(gui.time)) {
			timeStep();
			Critter cr = world.world.selectedInhabitant();
			if (cr != null) {
				System.out.println("Critter!");
				gui.size.setText("Size: " + cr.getVal(3));
				gui.complexity = new JLabel("Complexity: " + cr.getComplexity());
				gui.offense = new JLabel("Offense: " + cr.getVal(2));
				gui.defense = new JLabel("Defense: " + cr.getVal(1));
				gui.thisTag = new JLabel("Tag: " + cr.getVal(7));
				gui.posture = new JLabel("Posture: " + cr.getVal(8));
				gui.events = new JLabel("Events: " + new DecimalFormat().format(cr.getVal(6)));
			}
			world.repaint();
		}
	}
	
	private void zoom() {
//		world.zoomDraw(world.getGraphics(), world.world.selected);
	}

	private void timeStep() {
		world.world.stepTime();
	}
}
