package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import server.Location;

/**
 * Draws the hexGrid
 * 
 * @author Scarlet
 * */
public class HexGridCanvas extends Canvas {

	int col, row;
	int width, height;
	double radius;

	// first horizontal invariant (c, c/2), where c = 2n
	// second horizontal invariant (c, c/2+1), where c = 2n+1
	/**
	 * Creates a new hex grid with c rows and r columns, constrained to height h
	 * and width w pixels
	 * 
	 * @param w
	 * @param h
	 * @param c
	 * @param r
	 */
	HexGridCanvas(int w, int h, int c, int r) {
		setSize(width = w, height = h);
		col = c;
		row = r;
	}

	/**
	 * Creates a hex grid with c columns and r rows of width w pixels
	 * @param w
	 * @param c
	 * @param r
	 */
	HexGridCanvas(int w, int c, int r) {
		radius = computeRadius(w, c);
		if (c % 2 == 0) {
			height = (int) (2 * radius * (r + 1 - c / 2));
		} else {
			height = (int) (2 * radius * (r + 0.5 - c / 2));
		}
		setSize(width = w, height);
		col = c;
		row = r;
	}

	/**
	 * Computes the radius of each hex if there are c columns in w pixels
	 * @param w
	 * @param c
	 * @return
	 */
	public double computeRadius(int w, int c) {
		double r = 0;
		if (c % 2 == 0) {
			r = w / (3 * (c / 2) + 0.5);
		} else {
			r = w / (3 * (c / 2) + 2);
		}
		return r;
	}

	@Override
	public void paint(Graphics g) {
		width = getSize().width;
		height = getSize().height;

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);

		radius = computeRadius(width, col);

		int x, y;
		int xarr[], yarr[];
		int xinc = (int) (0.5 * radius);
		int yinc = (int) (Math.sqrt(3) * 0.55 * radius);
		for (int c = 0; c < col; c++) {
			for (int r = 0; r < row; r++) {
				if (c % 2 == 0) {
					x = (int) ((3 * (c / 2) + 1) * radius);
					y = height - (int) (((r - (c / 2)) * 2 + 1) * radius);
				} else {
					x = (int) ((3 * (c / 2) + 2.5) * radius);
					y = height - (int) ((r - (c / 2)) * 2 * radius);
				}
				xarr = new int[] { x - xinc, x + xinc, (int) (x + radius),
						x + xinc, x - xinc, (int) (x - radius) };
				yarr = new int[] { y + yinc, y + yinc, y, y - yinc, y - yinc, y };

				if (c + (2 * row - col) >= 2 * r
						&& ((c % 2 == 0 && r >= c / 2) || (c % 2 == 1 && r >= c / 2 + 1)))
					g.fillPolygon(xarr, yarr, 6);
			}
		}
	}

	/**
	 * Draws the grid zoomed in at location l
	 * @param g
	 * @param l
	 */
	public void zoomDraw(Graphics g, Location l) {
		width = getSize().width;
		height = getSize().height;

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);

		radius = computeRadius(width, col);

		int x, y;
		int xarr[], yarr[];
		int xinc = (int) (0.5 * radius);
		int yinc = (int) (Math.sqrt(3) * 0.55 * radius);
		int lowc = l.col - 5;
		int highc = l.col + 5;
		if (l.col >= col - 5) {
			lowc = col - 5;
			highc = col;
		} else if (l.col <= 5) {
			lowc = 0;
			highc = 10;
		}
		int lowr = l.row - 5;
		int highr = l.row + 5;
		if (l.row >= row - 5) {
			lowr = row - 5;
			highr = row;
		} else if (l.row <= 5) {
			lowr = 0;
			highr = 10;
		}
		for (int c = lowc; c < highc; c++) {
			for (int r = lowr; r < highr; r++) {
				if (c % 2 == 0) {
					x = (int) ((3 * (c / 2) + 1) * radius);
					y = height - (int) (((r - (c / 2)) * 2 + 1) * radius);
				} else {
					x = (int) ((3 * (c / 2) + 2.5) * radius);
					y = height - (int) ((r - (c / 2)) * 2 * radius);
				}
				xarr = new int[] { x - xinc, x + xinc, (int) (x + radius),
						x + xinc, x - xinc, (int) (x - radius) };
				yarr = new int[] { y + yinc, y + yinc, y, y - yinc, y - yinc, y };

				if (c + (2 * row - col) >= 2 * r
						&& ((c % 2 == 0 && r >= c / 2) || (c % 2 == 1 && r >= c / 2 + 1)))
					g.fillPolygon(xarr, yarr, 6);
			}
		}
	}
}
