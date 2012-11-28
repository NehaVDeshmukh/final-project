package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import view.GridPanel;
import view.View;

public class MouseInput implements MouseInputListener{

	GridPanel gp;
	Rectangle rec;
	public MouseInput(final GridPanel gridPanel)
	{
		gp = gridPanel;
		rec = gridPanel.getBounds();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent me) {
		if(me.getPoint().x == rec.getMinX())
		{
			gp.goLeft();
		}
		else if(me.getPoint().x == rec.getMaxX())
		{
			gp.goRight();
		}
		else if(me.getPoint().y == rec.getMinY())
		{
			gp.goUp();
		}
		else if(me.getPoint().y == rec.getMaxY())
		{
			gp.goDown();					
		}					
	} 
}

