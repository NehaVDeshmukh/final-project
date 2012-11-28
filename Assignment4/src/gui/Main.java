package gui;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;

/**
 * Creates a grid
 * 
 * @author Scarlet
 * */
public class Main {

	/*
	 * public static void main(String[] args) {
	 * 
	 * }
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		GUI g = new GUI();
		Random r = new Random();
		try {
			for (int i = 0; i < 75; i++) {
				Location l = new Location(r.nextInt(21), r.nextInt(22));
				if (g.c.world.isEmpty(l)) {
					switch (r.nextInt(2)) {
					case 0:
						g.addInhabitant(new Rock(l));
						break;
					case 1:
						g.addInhabitant(new Plant(l));
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to add inhabitants");
		}
		frame.add(g);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}