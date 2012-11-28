package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import control.MouseInput;

public class GridPanel extends JPanel {
	JPanel gridPanel;
	JPanel rightPanel;
	int MAX_COL;
	int MAX_ROW;

	public GridPanel(int col, int row, Dimension dim) 
	{
		MAX_COL = col;
		MAX_ROW = row;
		this.setLayout(new FlowLayout());
		HexGridCanvas hgc = new HexGridCanvas(col, row, dim);
		MouseInput mi = new MouseInput(this);
		hgc.addMouseListener(mi);
		hgc.addMouseMotionListener(mi);
		gridPanel = new JPanel(new GridLayout(1,1));
		gridPanel.setPreferredSize(new Dimension((int)(dim.getWidth()*0.7), (int)(dim.getHeight()*0.9)));
		gridPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		gridPanel.add(hgc);
		this.add(gridPanel);
		hgc.repaint();
		rightPanel = new JPanel(new GridLayout(4, 1));
		rightPanel.setPreferredSize(new Dimension((int)(dim.getWidth()*0.25), (int)(dim.getHeight()*0.9)));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));	
		this.add(rightPanel);
	}
	public JPanel getGridPanel()
	{
		return gridPanel;
	}
	public void goLeft()
	{}
	public void goRight()
	{}
	public void goUp()
	{}
	public void goDown()
	{}
}
