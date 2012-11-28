package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class WorldCanvas extends HexGridCanvas2 {
	World world;
	BufferedImage critter;
	int xTrans, yTrans;
	private BufferedImage flower;
	private BufferedImage grass;
	private BufferedImage bud;
	private BufferedImage leaf;
	private BufferedImage food;

	/**
	 * Creates the default WorldCanvas
	 */
	public WorldCanvas() {
		super(700, 21, 22);
		world = new World();
		world.setRadius(radius);
		xTrans = 0;
		yTrans = 0;
		try {
			critter = ImageIO.read(new File("critter0.png"));
			flower = ImageIO.read(new File("flower.png"));
			grass = ImageIO.read(new File("grass.png"));
			bud = ImageIO.read(new File("bud.png"));
			leaf = ImageIO.read(new File("leaf.png"));
			food = ImageIO.read(new File("food.png"));
		} catch (IOException e) {
			// you are screwed
		}
	}

	public WorldCanvas(int w, int h, int c, int r) {
		super(w, h, c, r);
		world = new World();
		xTrans = 0;
		yTrans = 0;
		try {
			critter = ImageIO.read(new File("critter0.png"));
		} catch (IOException e) {
			System.out.println("critter was not found");
			// you are screwed
		}
	}

	public void translate(int x, int y) {
		xTrans += x;
		yTrans += y;
	}

	@Override
	public void paint(Graphics g) {
		g.translate(xTrans, yTrans);
		super.paint(g);
		if (world.selected != null) {
			g.setColor(new Color(255, 214, 255));
			Coordinate c;
			try {
				c = Location.getCoordinate(world.selected.col,
						world.selected.row, (int) radius, height);

				int x = c.getX();
				int y = c.getY();
				int xinc = (int) (0.5 * radius);
				int yinc = (int) (Math.sqrt(3) * 0.55 * radius);
				int[] xarr = new int[] { x - xinc, x + xinc,
						(int) (x + radius), x + xinc, x - xinc,
						(int) (x - radius) };
				int[] yarr = new int[] { y + yinc, y + yinc, y, y - yinc,
						y - yinc, y };
				g.fillPolygon(xarr, yarr, 6);
			} catch (Exception e) {
				// are we ever going to have a negative radius?
			}
		}
		for (Inhabitant i : world.inhabitants) {
			Coordinate c;
			try {
				c = Location.getCoordinate(i.getLocation().col,
						i.getLocation().row, (int) radius, height);
				if (i instanceof Critter)
					critter = ImageIO.read(new File("critter"
							+ ((Critter) i).species + ".png"));
			} catch (Exception e) {
				// we would never make a negative radius, right?
				c = new Coordinate(i.getLocation(), 0);
			}
			if (i instanceof Critter) {
				double s = ((Critter) i).mem[3];
				s *= .1;
				if (s > 1)
					s = 1;
				s += .5;
				double r = ((Critter) i).dir.getRadians();
				AffineTransform rotate = AffineTransform.getRotateInstance(r,
						critter.getWidth() / 2, critter.getHeight() / 2);
				AffineTransformOp op = new AffineTransformOp(rotate,
						AffineTransformOp.TYPE_BILINEAR);

				g.drawImage(op.filter(critter, null), c.getX()
						- (int) (.5 * s * radius), c.getY()
						- (int) (.5 * s * radius), (int) (s * radius),
						(int) (s * radius), null);
				g.setColor(Color.BLACK);
				
			} else if (i instanceof Rock) {
				g.setColor(Color.BLACK);
				g.fillOval(c.getX() - (int) (.5 * radius), c.getY()
						- (int) (.5 * radius), (int) radius, (int) radius);
			} else if (i instanceof Plant) {
				int t = ((Plant) i).age;
				if (t <= 3) {
					g.drawImage(leaf, c.getX() - (int) (.5 * radius), c.getY()
							- (int) (.5 * radius), (int) (radius),
							(int) (radius), null);
				} else if (t <= 6) {
					g.drawImage(bud, c.getX() - (int) (.5 * radius), c.getY()
							- (int) (.5 * radius), (int) (radius),
							(int) (radius), null);
				} else if (t <= 9) {
					g.drawImage(flower, c.getX() - (int) (.5 * radius),
							c.getY() - (int) (.5 * radius), (int) (radius),
							(int) (radius), null);
				} else {
					g.drawImage(grass, c.getX() - (int) (.5 * radius), c.getY()
							- (int) (.5 * radius), (int) (radius),
							(int) (radius), null);
				}
			} else if (i instanceof Food) {
				g.drawImage(food, c.getX() - (int) (.5 * radius), c.getY()
						- (int) (.5 * radius), (int) (radius), (int) (radius),
						null);
			}
		}
	}
}
