package gui;

import java.awt.Point;

import server.Location;

public class Coordinate {

	/**
	 * x, y : coordinates in pixels radius : the radius of each hex
	 * */
	int x;
	int y;
	int radius;

	/**
	 * returns x value
	 * */
	public int getX() {
		return x;
	}

	/**
	 * returns y value
	 * */
	public Coordinate(Location loc, int height) {
		computeCoord(loc, height);
	}

	public int getY() {
		return y;
	}

	/**
	 * @param r radius of hexes of the current grid
	 * */
	public void setRadius(int r) {
		radius = r;
	}

	public void computeCoord(Location loc, int height) {
		int c = loc.col;
		int r = loc.row;
		double radius = loc.radius;
		if (c % 2 == 0) {
			x = (int) ((3 * (c / 2) + 1) * radius);
			y = height - (int) (((r - (c / 2)) * 2 + 1) * radius);
		} else {
			x = (int) ((3 * (c / 2) + 2.5) * radius);
			y = height - (int) ((r - (c / 2)) * 2 * radius);
		}
	}
	public static Location getLocation3(int x, int y, Point[][] p, int radius)
	{
		int c=0,r=0, h=0;
		Point n = new Point(x,y);
		int dist=0;
		int min_dist=(int) n.distance(p[0][0]);
		for(int i=0;i<p.length;i++)
		{
			for(int j=0;j<p[i].length;j++)
			{
				if(p[i][j]!=null)
				{
					dist = (int) n.distance(p[i][j]);
					if(dist < min_dist)
					{
						min_dist = dist;
						c = i;
						h = j;
					}	
				}
			}
		}
		r = (c+1)/2 + h;
		return new Location(c,r, radius);
	}
	// better algorithm but contains a bug
	public static Location getLocation2(int x, int y, double radius, int height)
	{
		int r = 0;
		int c = (int) (x - 0.5 * radius) / (int) (1.5 * radius);
		int xc = (int) (0.5 * radius + 1.5 * radius * c);
		int xcc = (int) (0.5 * radius + 1.5 * radius * (c + 1));			
		if(xc%2==0)
		{
			//xc
			int r1, r2;
			int dist1, dist2;
			int h = (int)( (height - y - 0.5 * radius) /(2 * radius));
			System.out.println("h = "+h);
			int yh = (int) (0.5 * radius + 2 * radius * h);
			int yhh = (int) (0.5 * radius + 2 * radius * (h+1));
			System.out.println("y : "+ (height-y) +" &yh : "+ yh +" & yhh : " +yhh);
			int yhhh=0;
			if (Math.abs(y - yh) > Math.abs(y - yhh))
			{
				h++;
				yhhh =yhh; 
			}	
			else
				yhhh = yh;
			dist1 = (xc-x)*(xc-x)+(yhhh-y)*(yhhh-y);
			System.out.println("case 1 : " +dist1+"to " +h);
			r1 = h + c / 2 -1 ;
			//xcc
			h = (int)((height - y) / (2 * radius))-1;
			yh = (int)(2 * radius * h);
			yhh =(int)(2 * radius * (h+1));
			System.out.println("y : "+ (height-y) +" &yh : "+ yh +" & yhh : " +yhh);
			if (Math.abs(y - yh) > Math.abs(y - yhh))
			{
				h++;
				yhhh = yhh;
			}
			else
				yhhh = yh;
			dist2 = (xcc-x)*(xcc-x)+(yhhh-y)*(yhhh-y);
			System.out.println("/////" +dist2+"to " +h);
			r2 = h + (c+1) /2 -1;
			if(dist1 < dist2)
				r = r1;
			else
			{
				r= r2;
				c++;
			}
		}
		else
		{
			//xc
			int r1,r2;
			int dist1, dist2;
			int h = (int)((height - y) / (2 * radius)) ;
			int yh = (int)(2 * radius * h);
			int yhh = (int)(2 * radius * (h+1));
			int yhhh=0;
			if (Math.abs(y - yh) > Math.abs(y - yhh))
			{
				h++;
				yhhh = yhh;
			}
			else
				yhhh = yh;
			dist1 = (xc-x)*(xc-x)+(yhhh-y)*(yhhh-y);
			r1 = h + (c+1) /2;
			//xcc
			h = (int) ((height - y - 0.5 * radius) /( 2* radius));
			yh = (int) (0.5 * radius + 2 * radius * h);
			yhh = (int) (0.5 * radius + 2 * radius * (h+1));
			if (Math.abs(y - yh) > Math.abs(y - yhh))
			{
				h++;
				yhhh =yhh; 
			}	
			else
				yhhh = yh;
			dist2 = (xcc-x)*(xcc-x)+(yhhh-y)*(yhhh-y);
			r2 = h + c / 2;
			if(dist1 < dist2)
				r = r1;
			else
			{
				r= r2;
				c++;
			}
			System.out.println("case 2 : "+ dist1+" ////" + dist2);
		}
		return new Location(c, r, radius);			
	}
	public static Location getLocation(int x, int y, double radius, int height) {
		int r = 0;
		int c = (int) (x - 0.5 * radius) / (int) (1.5 * radius);
		int xc = (int) (0.5 * radius + 1.5 * radius * c);
		int xcc = (int) (0.5 * radius + 1.5 * radius * (c + 1));
		if (Math.abs(x -xc) > Math.abs(x-xcc))
			c++;
		if(c%2==0)
		{
			int h = (int)( (height - y - 0.5 * radius) /(2 * radius));
			System.out.println("h = "+h);
			int yh = (int) (0.5 * radius + 2 * radius * h);
			int yhh = (int) (0.5 * radius + 2 * radius * (h+1));
			System.out.println("y : "+ (height-y) +" &yh : "+ yh +" & yhh : " +yhh);
			int yhhh=0;
			if (Math.abs(y - yh) > Math.abs(y - yhh))
			{
				h++;
				yhhh =yhh; 
			}	
			else
				yhhh = yh;
			r = h + c / 2 -1 ;

		}
		else
		{
			int h = (int)((height - y) / (2 * radius)) ;
			int yh = (int)(2 * radius * h);
			int yhh = (int)(2 * radius * (h+1));
			int yhhh=0;
			if (Math.abs(y - yh) > Math.abs(y - yhh))
			{
				h++;
				yhhh = yhh;
			}
			else
				yhhh = yh;
			r = h + (c+1) /2;
		}
		System.out.println("( " + c + " , " + r + " )");
		return new Location(c, r, radius);
	}
}
