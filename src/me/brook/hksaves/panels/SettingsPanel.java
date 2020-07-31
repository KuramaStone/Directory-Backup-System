package me.brook.hksaves.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.brook.hksaves.HKSaves;

public class SettingsPanel extends CustomPanel {

	private static final long serialVersionUID = -5396098383186532097L;

	private JPanel loadSaveFrom;
	private JPanel backupIn;
	private JButton save = button("Save");
	
	private JTextField backupField, saveField;
	

	public SettingsPanel(HKSaves plugin) {
		super(plugin, Color.RED, Color.BLACK, null);
		try {
			buildComponents();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		constructBorder("Settings");
		addComponents();
	}
	
	private void buildComponents() throws IOException {
		buildBackUp();
		buildSaveFolder();
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.setGameDirectory(new File(saveField.getText()));
				manager.setBackupDirectory(new File(backupField.getText()));
			}
		});
	}
	
	private void buildSaveFolder() throws IOException {
		File saves = manager.getGameDirectory();

		loadSaveFrom = new JPanel();
		loadSaveFrom.setOpaque(false);

		JLabel label = label("HK Saves: ");
		JTextField field = new JTextField(30);
		field.setText(saves.getAbsolutePath());
		JButton choose = new JButton(new ImageIcon(ImageIO.read(plugin.getClass().getResourceAsStream("/file.png"))));

		loadSaveFrom.add(label);
		loadSaveFrom.add(field);
		loadSaveFrom.add(choose);
		saveField = field;
	}

	private void buildBackUp() throws IOException {
		File backup = manager.getBackupDirectory();
		backupIn = new JPanel();
		backupIn.setOpaque(false);

		JLabel label = label("Backup Folder: ");
		JTextField field = new JTextField(30);
		field.setText(backup.getAbsolutePath());
		JButton choose = new JButton(new ImageIcon(ImageIO.read(plugin.getClass().getResourceAsStream("/file.png"))));

		backupIn.add(label);
		backupIn.add(field);
		backupIn.add(choose);
		backupField = field;
	}

	private void addComponents() {
		GroupLayout gl = new GroupLayout(this);
		this.setLayout(gl);

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(createHorizontalGroup(gl, backupIn, new Gap(50), loadSaveFrom, save));
		gl.setVerticalGroup(createVerticalGroup(gl, backupIn, loadSaveFrom, save));
	}

}
