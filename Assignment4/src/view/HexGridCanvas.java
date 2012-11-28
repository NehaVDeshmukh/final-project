package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class HexGridCanvas extends Canvas{
	Dimension dim;
	int xPercentage;
	int yPercentage;
	int radius = 30;
	int MAX_COL;
	int MAX_ROW;
	int gridWidth;
	int gridHeight;
	int panelWidth;
	int panelHeight;
	int xStart;
	int yStart;
	int xEnd;
	int yEnd;
	int xMinRangePercentage;
	int yMinRangePercentage;
	Point[][] p;
	
	public HexGridCanvas(int col, int row, Dimension dim) 
	{
		MAX_COL = col;
		MAX_ROW = row;
		p = new Point[MAX_COL][MAX_ROW];
		this.dim = dim;
		build(dim);
		this.setVisible(true);
	}
	public void build(Dimension dim)
	{
		System.out.println("building");
		panelWidth = dim.width;
		panelHeight = dim.height;
		int x,y;
		for(int c=0;c < MAX_COL; c++)
		{
			for(int r=0;r<MAX_ROW;r++)
			{
				int h = r - (c+1)/2;
				if(c%2==0)
				{
					x = (int) ((3 * (c / 2) + 1) * radius);
					y = (int)((h+1)*2+1)*radius;					
				}
				else
				{
					x = (int) ((3 * (c / 2) + 2.5) * radius);
					y = (int) ((h+1)*2*radius);					
				}
				p[c][r] = new Point(x,y);				
			}
		}
		gridWidth = p[MAX_COL-1][MAX_ROW-1].x+radius;
		gridHeight = MAX_COL%2==0?p[MAX_COL-1][MAX_ROW-1].y+2*radius:p[MAX_COL-1][MAX_ROW-1].y+radius;
		xMinRangePercentage = (100 * gridWidth) / panelWidth;
		yMinRangePercentage = (100 * gridHeight) / panelHeight;
		if(xMinRangePercentage >= 100 && yMinRangePercentage >=100)
		{
			//center it
			xStart = 0; // in pixels
			xEnd = gridWidth;
			yStart = 0; // in pixels
			yEnd = gridHeight;
		}
		else if(xMinRangePercentage >=100)
		{
			xStart = 0; // in pixels
			xEnd = gridWidth;			
		}
		else if(yMinRangePercentage >= 100)
		{
			yStart = 0; // in pixels
			yEnd = gridHeight;			
		}
		else 
		{
			xStart = ( panelWidth * xPercentage ) / 100; // in pixels
			xEnd = (panelWidth * (xPercentage+xMinRangePercentage))/100; // in pixels
			yStart = (panelHeight * yPercentage) / 100;
			yEnd = (panelWidth * (yPercentage+yMinRangePercentage))/100;
		}
	}
	public void paint(Graphics g)
	{	
		g.setColor(Color.WHITE);
		System.out.println("painting");
		int xinc = (int) (0.5 * radius);
		int yinc = (int) (Math.sqrt(3) * 0.5 * radius);
		int[] xarr;
		int[] yarr;
		for(int c=0;c<MAX_COL;c++)
		{
			for(int r=0;r<MAX_ROW;r++)
			{
				int x = p[c][r].x;
				int y = p[c][r].y;
				if(x>xStart-60 && x<xEnd+60 && y>yStart-60 && y<yEnd+60)
				{
					xarr = new int[] { x - xinc, x + xinc, (int) (x + radius),
							x + xinc, x - xinc, (int) (x - radius) };
					yarr = new int[] { y + yinc, y + yinc, y, y - yinc, y - yinc, y };
					System.out.println("painting");
					g.fillPolygon(xarr, yarr, 6);				
				}
			}
		}
		//60
	}
	public void setPercentage(int x, int y)
	{
		xPercentage = x;
		yPercentage = y;
	}
	public void incrementXPercentage()
	{
		xPercentage++;
	}
	public void incrementXPercentage(int x)
	{
		xPercentage+=x;		
	}
	public void incrementYPercentage()
	{
		yPercentage++;
	}
	public void incrementYPercentage(int y)
	{
		yPercentage+=y;
	}
}
