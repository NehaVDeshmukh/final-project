package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import server.AdminServer;
import server.Critter;
import server.Inhabitant;
import student.Interpreter;

public class GUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6347419676665538285L;
	WorldCanvas c;
	AdminServer s;
	JRadioButton wait, rules;
	private ButtonGroup group;
	private JPanel stats, action, window, windowOut, left, rightView,
			rightUser, rightAdmin, togglePanel, zoomAndStep, importPanel,
			loginPanel, logoutPanel, outer;
	private JLabel statsLbl;
	JLabel size, complexity, offense, defense, thisTag, posture, events,
			actionLbl, windowLbl, rateLbl;
	JButton zIn, zOut, time, newWorld, addCritter, showRule, restart,
			uploadParams, logout, uploads, downloads, login, createAccount,
			manageUsers, adminPriveledges;
	JComboBox chooser;
	private String[] actions = { "Wait", "Move forward", "Move backward",
			"Turn left", "Turn right", "Eat", "Attack", "Tag", "Grow", "Bud",
			"Mate" };
	JLabel zoomedAt;
	JFileChooser file;
	JSlider rate;

	/**
	 * Creates a new GUI V/C, with admin "original" who can log in
	 */
	public GUI(AdminServer original) {
		s = original;
		file = new JFileChooser();
		this.setLayout(new FlowLayout());
		try {
			c = new WorldCanvas(700, 500, original.maxColumn(),
					original.maxRow());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			c = new WorldCanvas();
		}
		GridListener g = new GridListener(c, this);
		c.addMouseListener(g);
		c.addMouseMotionListener(g);
		left = new JPanel();
		left.add(c);
		makeStats();
		makeAction();
		makeWindow();
		makeImport();
		makeLogin();
		rightView = new JPanel(new GridLayout(3, 1));
		rightView.setPreferredSize(new Dimension(450, 500));

		rightView.add(stats);
		rightView.add(windowOut);
		rightView.add(loginPanel);

		rightUser = new JPanel(new GridLayout(4, 1));
		rightUser.setPreferredSize(new Dimension(450, 500));

		rightUser.add(stats);
		rightUser.add(action);
		rightUser.add(windowOut);
		rightUser.add(logoutPanel);

		rightAdmin = new JPanel(new GridLayout(5, 1));
		rightAdmin.setPreferredSize(new Dimension(450, 500));

		rightAdmin.add(stats);
		rightAdmin.add(action);
		rightAdmin.add(windowOut);
		rightAdmin.add(logoutPanel);
		adminPriveledges = new JButton("Access Admin Priveledges");
		rightAdmin.add(adminPriveledges);
		adminPriveledges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame pr = new JFrame();
					JPanel stuff = new JPanel(new GridLayout(5, 1));
					restart = new JButton("Reset Simulation");
					uploadParams = new JButton("Change Parameters");
					uploads = new JButton("Turn "
							+ (s.uploadsOn() ? "off" : "on") + "uploads");
					downloads = new JButton("Turn "
							+ (s.downloadsOn() ? "off" : "on") + "downloads");
					manageUsers = new JButton("Manage Users");
					stuff.add(restart);
					stuff.add(uploadParams);
					stuff.add(uploads);
					stuff.add(downloads);
					stuff.add(manageUsers);
					pr.add(stuff);
					restart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								s.resetSim();
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
							}
						}
					});
					uploadParams.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							file.showOpenDialog(null);
						}
					});
					uploads.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								s.setCritterUploads(!(s.uploadsOn()));
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
							}
						}
					});
					downloads.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								s.setCritterDownloads(!(s.downloadsOn()));
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
							}
						}
					});
					manageUsers.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JButton approveU = new JButton(
									"Approve User Request");
							JButton makeAdmin = new JButton(
									"Make User Administrator");
							JButton removeAdmin = new JButton(
									"Remove Administrator");
							JButton denyU = new JButton("Deny User Request");
							JPanel bu = new JPanel(new GridLayout(4, 1));
							bu.add(approveU);
							bu.add(makeAdmin);
							bu.add(removeAdmin);
							bu.add(denyU);
							JFrame f = new JFrame();
							f.add(bu);
							approveU.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										s.addPlayer(JOptionPane
												.showInputDialog("Player's username:"));
									} catch (HeadlessException e1) {
										// TODO Auto-generated catch block
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
									}
								}
							});
							makeAdmin.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										s.addAdmin(JOptionPane
												.showInputDialog("Player's username:"));
									} catch (HeadlessException e1) {
										// TODO Auto-generated catch block
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
									}
								}
							});
							removeAdmin.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										s.removeAdmin(JOptionPane
												.showInputDialog("Player's username:"));
									} catch (HeadlessException e1) {
										// TODO Auto-generated catch block
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
									}
								}
							});
							denyU.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										s.rejectPlayer(JOptionPane
												.showInputDialog("Player's username:"));
									} catch (HeadlessException e1) {
										// TODO Auto-generated catch block
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
									}
								}
							});

							f.pack();
							f.setVisible(true);
						}
					});

					pr.pack();
					pr.setVisible(true);
				} catch (RemoteException e1) {
					// TODO
				}
			}
		});
		
		outer = new JPanel();

		outer.add(left);
		outer.add(rightView);

		this.add(outer);
	}

	private void makeLogin() {
		loginPanel = new JPanel(new GridLayout(2, 1));
		login = new JButton("Login");
		createAccount = new JButton("Create Account");
		loginPanel.add(login);
		loginPanel.add(createAccount);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String u = JOptionPane.showInputDialog(loginPanel, "Username:",
						"Input username");
				String p = JOptionPane.showInputDialog(loginPanel, "Username:",
						"Input username");

				try {
					if (s.getAdminServer(u, p) != null) {
						outer.remove(1);
						outer.add(rightAdmin);
					} else if (s.getPlayerServer(u, p) != null) {
						outer.remove(1);
						outer.add(rightUser);
					} else {
						outer.remove(1);
						outer.add(rightView);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
				}
			}
		});

		createAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String u = JOptionPane.showInputDialog(loginPanel, "Username:",
						"Input username");
				String p = JOptionPane.showInputDialog(loginPanel, "Username:",
						"Input username");

				try {
					JOptionPane.showMessageDialog(outer, s.requestUserAcc(u, p));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
				}
			}
		});

		logoutPanel = new JPanel();
		logout = new JButton("Logout");
		logoutPanel.add(logout);

		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				outer.remove(1);
				outer.add(rightView);
			}
		});
	}

	private void makeStats() {
		stats = new JPanel(new GridLayout(4, 2));
		statsLbl = new JLabel("Stats");
		statsLbl.setFont(new Font(statsLbl.getFont().getName(), Font.BOLD,
				statsLbl.getFont().getSize() + 1));
		size = new JLabel("Size: ");
		complexity = new JLabel("Complexity: ");
		offense = new JLabel("Offense: ");
		defense = new JLabel("Defense: ");
		thisTag = new JLabel("Tag: ");
		posture = new JLabel("Posture: ");
		events = new JLabel("Events: ");
		stats.add(statsLbl);
		stats.add(size);
		stats.add(complexity);
		stats.add(offense);
		stats.add(defense);
		stats.add(thisTag);
		stats.add(posture);
		stats.add(events);
		stats.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	}

	private void makeAction() {
		chooser = new JComboBox(actions);
		chooser.addActionListener(new CritterListener(this));

		action = new JPanel(new GridLayout(1, 3));

		actionLbl = new JLabel("Actions");
		actionLbl.setFont(statsLbl.getFont());

		action.add(actionLbl);

		action.add(chooser);

		togglePanel = new JPanel(new GridLayout(3, 1));
		group = new ButtonGroup();
		wait = new JRadioButton("Wait");
		rules = new JRadioButton("Follow Rules");
		group.add(wait);
		group.add(rules);
		wait.setSelected(true);
		togglePanel.add(new JLabel("Default critter action:"));
		togglePanel.add(wait);
		togglePanel.add(rules);
		action.add(togglePanel);
		action.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	}

	private void makeWindow() {
		windowOut = new JPanel(new GridLayout(1, 2));
		window = new JPanel(new GridLayout(4, 1));
		windowLbl = new JLabel("Window");
		windowLbl.setFont(statsLbl.getFont());
		zIn = new JButton("Zoom in");
		zOut = new JButton("Zoom out");
		time = new JButton("Step time");
		ButtonListener l = new ButtonListener(this);
		zIn.addActionListener(l);
		zOut.addActionListener(l);
		time.addActionListener(l);
		window.add(windowLbl);
		window.add(zIn);
		window.add(zOut);
		window.add(time);
		windowOut.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		zoomAndStep = new JPanel(new GridLayout(4, 1));
		zoomAndStep.add(new JLabel());
		zoomAndStep.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0,
				Color.BLACK));
		zoomedAt = new JLabel("Zoomed in at location: ");
		zoomedAt.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		rateLbl = new JLabel("Rate of simulation:");
		rateLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		zoomAndStep.add(zoomedAt);
		rate = new JSlider(0, 100);
		rate.setMajorTickSpacing(10);
		rate.setMinorTickSpacing(1);
		rate.setSnapToTicks(true);
		rate.setPaintTicks(true);
		rate.setPaintLabels(true);
		rate.setValue(0);
		rate.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				try {
					if (((JSlider) arg0.getSource()).getValue() == 0)
						s.pauseSim();
					else {
						s.setSimRate(((JSlider) arg0.getSource()).getValue());
						s.startSim();
					}
				} catch (RemoteException e) {
					// TODO
				}

			}
		});
		zoomAndStep.add(rateLbl);
		zoomAndStep.add(rate);
		zoomAndStep.setPreferredSize(new Dimension(200, 200));
		windowOut.add(window);
		windowOut.add(zoomAndStep);
	}

	public void makeImport() {
		importPanel = new JPanel(new GridLayout(3, 1));
		newWorld = new JButton("Open world");
		addCritter = new JButton("Add critter");
		showRule = new JButton("Display rule");
		newWorld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file.showDialog(null, "select");
				if (!(file.getSelectedFile() == null)) {
					try {
						c.world = Interpreter.importWorld(file
								.getSelectedFile());
						c.world.setRadius(c.radius);
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Invalid file");
					}
					c.repaint();
				}
			}
		});
		addCritter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file.showDialog(null, "select");
				if (!(file.getSelectedFile() == null)) {
					try {
						Critter cr = Interpreter.importCritter(file
								.getSelectedFile());
						String s = JOptionPane
								.showInputDialog("Location of new critter: (r,c)");
						if (s.startsWith("(")) {
							s = s.substring(1, s.length() - 1);
							String[] co = s.split(",");
							cr.setLocation(Integer.parseInt(co[0]),
									Integer.parseInt(co[1]));
							cr.getLocation().setRadius(c.radius);
							c.world.addInhabitant(cr);
						}
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Invalid file");
					}
					c.repaint();
				}
			}
		});
		showRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StringBuffer sb = new StringBuffer();
				if (c.world.selectedInhabitant() != null) {
					c.world.selectedInhabitant().getCurrentRule()
							.prettyPrint(sb);
					JOptionPane.showMessageDialog(null, sb);
				}
			}
		});

		importPanel.add(newWorld);
		importPanel.add(addCritter);
		importPanel.add(showRule);

		importPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	}

	/**
	 * adds an inhabitant to the GUI V/C
	 * 
	 * @param i
	 */
	public void addInhabitant(Inhabitant i) {
		c.world.addInhabitant(i);
		c.repaint();
	}

	public void updateStats(Critter c) {
		size.setText("Size: " + c.getVal(3));
		complexity.setText("Complexity: " + c.getComplexity());
		offense.setText("Offense: " + c.getVal(2));
		defense.setText("Defense: " + c.getVal(1));
		thisTag.setText("Tag: " + c.getVal(7));
		posture.setText("Posture: " + c.getVal(8));
		events.setText("Events: " + c.getVal(6));
	}
}
