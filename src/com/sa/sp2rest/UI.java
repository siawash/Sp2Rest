package com.sa.sp2rest;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UI extends JFrame {

	private static final long serialVersionUID = 4564220376366502430L;

	private JLabel lblAppName = new JLabel("App Name: ");
	private JLabel lblAppPackage = new JLabel("App Package: ");
	private JLabel lblAppUrl = new JLabel("App Url: ");

	private JLabel lblIp = new JLabel("Ip: ");
	private JLabel lblPort = new JLabel("port: ");
	private JLabel lblDatabase = new JLabel("Database: ");
	private JLabel lblUsername = new JLabel("Username: ");
	private JLabel lblPassword = new JLabel("Password: ");

	private JLabel lblSpListPath = new JLabel("SpList path: ");

	private JScrollPane scrol;

	private JTextField txtAppName = new JTextField(20);
	private JTextField txtAppPackage = new JTextField(20);
	private JTextField txtAppUrl = new JTextField(20);

//	private JTextField txtIp = new JTextField(20);
//	private JTextField txtPort = new JTextField(20);
//	private JTextField txtDatabase = new JTextField(20);
//	private JTextField txtUsername = new JTextField(20);
//	private JPasswordField txtPassword = new JPasswordField(20);

	private JTextField txtSpListPath = new JTextField(45);

	private JTextArea txtResult = new JTextArea(10, 56);

	private JButton btnSelectPath = new JButton("...");
	private JButton btnExecute = new JButton("Execute");
	private JButton btnClear = new JButton("Clear");
	private JButton btnExit = new JButton("  Exit ");

	private static String ip, port, database, userName, password, appName, appPackage, appUrl, spListPath;

	private void init() {

//		txtIp.setText("172.16.16.84");
//		txtPort.setText("484");
//		txtDatabase.setText("DEV");
//
//		txtUsername.setText("azizis");
//		txtPassword.setText("sia218");

//		txtAppName.setText("SubDraft");
//		txtAppPackage.setText("dmnservice.fnrsubdraft");
//		txtAppUrl.setText("/dmnService/fnrsubdraft");
//		txtSpListPath.setText("E:\\current_projects\\splist.txt");

		txtSpListPath.setEnabled(false);
		txtSpListPath.setDisabledTextColor(Color.BLACK);
		
		txtResult.setMargin(new Insets(2, 5, 2, 2));
		txtResult.setEnabled(false);
		txtResult.setDisabledTextColor(Color.BLACK);
		Logger.setTxtResult(txtResult);
	}

	public UI() {

		super("Sp2Rest ver 1.0");

		init();

		GridBagLayout gbl = new GridBagLayout();

		JPanel container = new JPanel(gbl);

		GridBagConstraints containerConstraints = new GridBagConstraints();
		containerConstraints.insets = new Insets(2, 2, 2, 2);

		containerConstraints.gridy = 0;
		containerConstraints.gridx = 0;
		containerConstraints.gridwidth = 1;
		containerConstraints.fill = GridBagConstraints.BOTH;
		JPanel topPanel = new JPanel(gbl);
		container.add(topPanel, containerConstraints);

		topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Application"));
		containerConstraints.insets = new Insets(5, 5, 5, 5);

		containerConstraints.gridy = 0;
		containerConstraints.gridx = 0;
		topPanel.add(lblAppName, containerConstraints);

		containerConstraints.gridx = 1;
		topPanel.add(txtAppName, containerConstraints);

		containerConstraints.gridx = 2;
		topPanel.add(lblAppPackage, containerConstraints);

		containerConstraints.gridx = 3;
		topPanel.add(txtAppPackage, containerConstraints);

		containerConstraints.gridy = 1;
		containerConstraints.gridx = 0;
		topPanel.add(lblAppUrl, containerConstraints);

		containerConstraints.gridx = 1;
		containerConstraints.gridwidth = 3;
		topPanel.add(txtAppUrl, containerConstraints);
		
		
		containerConstraints.gridy = 1;
		containerConstraints.gridx = 0;
		containerConstraints.gridwidth = 1;
		JPanel middlePanel = new JPanel(gbl);
		middlePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		containerConstraints.insets = new Insets(6, 6, 6, 6);
		container.add(middlePanel, containerConstraints);

		containerConstraints.gridy = 0;
		containerConstraints.gridx = 0;
		middlePanel.add(lblSpListPath, containerConstraints);

		containerConstraints.gridx = 1;
		middlePanel.add(txtSpListPath, containerConstraints);

		containerConstraints.gridx = 2;
		containerConstraints.anchor = GridBagConstraints.EAST;
		middlePanel.add(btnSelectPath, containerConstraints);

		
		
		containerConstraints.gridy = 2;
		containerConstraints.gridx = 0;
		containerConstraints.gridwidth = 1;

		containerConstraints.anchor = GridBagConstraints.WEST;
		containerConstraints.fill = GridBagConstraints.BOTH;
		containerConstraints.insets = new Insets(4, 4, 4, 4);
		scrol = new JScrollPane(txtResult);
		txtResult.setLineWrap(true);
		container.add(scrol, containerConstraints);

		containerConstraints.gridy = 3;
		containerConstraints.gridx = 0;
		containerConstraints.gridwidth = 1;
		containerConstraints.insets = new Insets(2, 2, 2, 2);
//		containerConstraints.anchor = GridBagConstraints.WEST;
		JPanel controlPanel = new JPanel(gbl);
		container.add(controlPanel, containerConstraints);
		controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));

		containerConstraints.insets = new Insets(2, 2, 2, 2);
		containerConstraints.gridy = 0;
		containerConstraints.gridx = 0;
		containerConstraints.weightx = 1;
		controlPanel.add(btnExecute, containerConstraints);

		containerConstraints.gridx = 1;
		containerConstraints.weightx = 1;
		controlPanel.add(btnClear, containerConstraints);

		containerConstraints.gridx = 2;
//		containerConstraints.anchor = GridBagConstraints.EAST;
//		containerConstraints.fill = GridBagConstraints.HORIZONTAL;
		containerConstraints.weightx = 1;
		controlPanel.add(btnExit, containerConstraints);

		add(container);

		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				txtAppName.setText("");
				txtAppPackage.setText("");
				txtAppUrl.setText("");

				txtSpListPath.setText("");
				txtResult.setText("");
			}
		});

		btnSelectPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Choose spList file");
//			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);

			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			      txtSpListPath.setText(chooser.getSelectedFile().getAbsolutePath());
			    }
			}
			
		});
		
		btnExecute.addActionListener(new ActionListener() {			

			public void actionPerformed(ActionEvent e) {

				txtResult.setText("");

				new Thread(new Runnable() {
					public void run() {
						appName = txtAppName.getText();
						appPackage = "com.behsazan." + txtAppPackage.getText();
						appUrl = txtAppUrl.getText();

						spListPath = txtSpListPath.getText();

						Logger.setTxtResult(txtResult);

						if (!Application.evaluateSpList(spListPath)) {

							Logger.log("the splist file has bad format");
//							JOptionPane.showMessageDialog(null, "the splist file has bad format");
							Thread.currentThread().stop();
//							System.exit(0);
						}

						try {
							Application.getInstance(appName, appPackage, appUrl, spListPath);
						} catch (SQLException e) {
							Logger.log(e.getMessage());
//							JOptionPane.showMessageDialog(null, e.getMessage());
							Thread.currentThread().stop();
//							System.exit(0);
						}
					}
				}).start();
			}
		});

		pack();

		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				new UI();
			}
		}).start();
	}
}
