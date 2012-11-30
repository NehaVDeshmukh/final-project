package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import server.Location;

public class HexGridCanvas2 extends Canvas {

	int MAX_COLUMN;
	int MAX_ROW;
	int MAX_HEIGHT;
	int width, height;
	double radius;
	Point[][] p;
	int h2;
	
	HexGridCanvas2(int w, int h, int c, int r)
	{
		width = w;
		height = h;
		MAX_COLUMN = c;
		MAX_ROW = r;
		setSize(width = w, height = h);
		radius = computeRadius(w, c);
		MAX_HEIGHT = MAX_ROW - (MAX_COLUMN)/2;
		p = new Point[MAX_COLUMN][MAX_COLUMN %2==0?MAX_HEIGHT+1:MAX_HEIGHT];
		compute();
	}
	HexGridCanvas2(int w, int c, int r)
	{
		radius = computeRadius(w, c);
		if (c % 2 == 0) {
			height = (int) (2 * radius * (r + 1 - c / 2));
		} else {
			height = (int) (2 * radius * (r - c / 2));
		}
		setSize(width = w, height);
		MAX_COLUMN = c;
		MAX_ROW = r;
		MAX_HEIGHT = MAX_ROW - (MAX_COLUMN)/2;
		p = new Point[MAX_COLUMN][MAX_COLUMN %2==0?MAX_HEIGHT+1:MAX_HEIGHT];
		compute();
	}	
	/**
	 * Computes the radius of each hex if there are c columns in w pixels
	 * @param w
	 * @param c
	 * @return
	 */
	public double computeRadius(int w, int c) {
		if(c<=5)
			return 20;
		double r = 0;
		if (c % 2 == 0) {
			r = w / (3 * (c / 2) + 0.5);
		} else {
			r = w / (3 * (c / 2) + 2);
		}
		return r;
	}
	public void compute()
	{
		int x,y;
		int xarr[], yarr[];
		for(int c=0;c < MAX_COLUMN; c++)
		{
			if(MAX_COLUMN %2 ==0 && c %2 ==0)
			{
				h2 = MAX_HEIGHT+1;
			}
			else if(MAX_COLUMN %2 ==0 && c%2!=0)
			{
				h2 =MAX_HEIGHT;
			}
			else if(MAX_COLUMN %2 !=0 && c%2==0)
			{
				h2 = MAX_HEIGHT;
			}
			else
			{
				h2 = MAX_HEIGHT-1;
			}
			for(int h=0; h < h2;h++)
			{
				if (c % 2 == 0) {
					x = (int) ((3 * (c / 2) + 1) * radius);
					y = height - (int)((h*2+1)*radius);
				} else {
					x = (int) ((3 * (c / 2) + 2.5) * radius);
					y = height - (int) ((h+1)*2*radius);
				}				
				p[c][h] = new Point(x,y);
			}
		}
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		int xinc = (int) (0.5 * radius);
		int yinc = (int) (Math.sqrt(3) * 0.55 * radius);
		int xarr[], yarr[];
		for(int i=0;i<p.length;i++)
		{
			if(MAX_COLUMN %2 ==0 && i %2 ==0)
			{
				h2 = MAX_HEIGHT+1;
			}
			else if(MAX_COLUMN %2 ==0 && i%2!=0)
			{
				h2 =MAX_HEIGHT;
			}
			else if(MAX_COLUMN %2 !=0 && i%2==0)
			{
				h2 = MAX_HEIGHT;
			}
			else
			{
				h2 = MAX_HEIGHT-1;
			}
			for(int j=0;j<h2;j++)
			{
				int x = p[i][j].x;
				int y = p[i][j].y;
				xarr = new int[] { x - xinc, x + xinc, (int) (x + radius),
						x + xinc, x - xinc, (int) (x - radius) };
				yarr = new int[] { y + yinc, y + yinc, y, y - yinc, y - yinc, y };
				g.fillPolygon(xarr, yarr, 6);								
			}
		}
		
	}
	public void zoomDraw(Graphics g, Location l) {
		
	}
}

