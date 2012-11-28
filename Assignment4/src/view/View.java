package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

public class View {

	private JFrame frame;
	public GridPanel gridPanel;
	public JPanel rightPanel;
	public View()
	{
		setFrame(new JFrame("Critter World"));
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH); 		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		gridPanel = new GridPanel(20,20,dim);
		frame.setVisible(true);
		frame.add(gridPanel);
	}
	public JFrame getFrame() 
	{
		return frame;
	}
	public void setFrame(JFrame frame) 
	{
		this.frame = frame;
	}

}
