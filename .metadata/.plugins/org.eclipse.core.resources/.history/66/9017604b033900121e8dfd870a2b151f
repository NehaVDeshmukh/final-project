package gui;

public class Location {

	int col;
	int row;
	double radius;

	/**
	 * Creates a new location on a HexGrid
	 * @param c the column
	 * @param r the row
	 */
	public Location(int c, int r) {
		col = c;
		row = r;
		radius = 0;
	}

	/**
	 * Creates a new location on a HexGrid
	 * @param c the column
	 * @param r the row
	 * @param radius the radius of the hexes
	 */
	public Location(int c, int r, double radius) {
		col = c;
		row = r;
		this.radius = radius;
	}

	/**
	 * Updates this location
	 * @param c
	 * @param r
	 */
	public void setLocation(int c, int r) {
		col = c;
		row = r;
	}

	/**
	 * Updates this location
	 * @param c
	 * @param r
	 * @param radius
	 */
	public void setLocation(int c, int r, double radius) {
		col = c;
		row = r;
		this.radius = radius;
	}

	/**
	 * @param r
	 *            radius of hexes of the current grid
	 * */
	public void setRadius(double r) {
		radius = r;
	}

	/**
	 * computes the pixel values of this location
	 * 
	 * @param height
	 *            the height of the current gird
	 * @return Coordinate of the current location
	 * @throws Exception
	 *             if radius is non positive
	 * */
	public static Coordinate getCoordinate(int c, int r, double radius, int height) throws Exception {
		int x, y;
		if(c%2==0)
		{
			x = (int)((3*(c/2)+1)*radius);
			y = height-(int)(((r-(c/2))*2+1)*radius);	    			
		}
		else
		{
			x = (int)((3*(c/2)+2.5)*radius);
			y = height-(int)((r-(c/2))*2*radius);
		}	    		
		if (radius <= 0)
			throw new Exception("invalid radius");
		return new Coordinate(new Location(c,r,radius), height);
	}
	
	public String toString() {
		return("(" + col + "," + row + ")");
	}
}
