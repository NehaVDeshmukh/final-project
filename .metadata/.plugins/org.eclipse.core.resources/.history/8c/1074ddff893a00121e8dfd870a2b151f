package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JLabel;

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
			gui.zoomedAt.setText("Zoomed in at location: " + world.world.selected);
		}
		else if(arg0.getSource().equals(gui.zOut)) {
			gui.c.repaint();
		}
		else if(arg0.getSource().equals(gui.time)) {
			timeStep();
			Critter cr = world.world.selectedInhabitant();
			if (cr != null) {
				System.out.println("Critter!");
				gui.size.setText("Size: " + cr.mem[3]);
				gui.complexity = new JLabel("Complexity: " + cr.complexity);
				gui.offense = new JLabel("Offense: " + cr.mem[2]);
				gui.defense = new JLabel("Defense: " + cr.mem[1]);
				gui.thisTag = new JLabel("Tag: " + cr.mem[7]);
				gui.posture = new JLabel("Posture: " + cr.mem[8]);
				gui.events = new JLabel("Events: " + new DecimalFormat().format(cr.mem[6]));
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
