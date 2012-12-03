package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CritterListener implements ActionListener {

	GUI gui;

	public CritterListener(GUI g) {
		gui = g;
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource().equals(gui.wait)) {
			gui.c.world.setWait(true);
		} else if (a.getSource().equals(gui.random)) {
			gui.c.world.setWait(false);
		} else if (a.getSource().equals(gui.chooser)) {
			int action = gui.chooser.getSelectedIndex();
			gui.c.world.setAction(action);
		}
	}
}
