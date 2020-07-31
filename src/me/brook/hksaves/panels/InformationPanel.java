package me.brook.hksaves.panels;

import java.text.SimpleDateFormat;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import me.brook.hksaves.HKSaves;
import me.brook.hksaves.Profile;

public class InformationPanel extends CustomPanel {

	private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/YY hh:mma");

	private static final long serialVersionUID = -1017197113247915962L;

	private JLabel lastSaved;
	private JLabel saveLocation;

	public InformationPanel(HKSaves plugin) {
		super(plugin);
		buildComponents();
		constructBorder("Information");
		addComponents();
	}

	private void addComponents() {
		GroupLayout gl = new GroupLayout(this);
		this.setLayout(gl);

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(createHorizontalGroup(gl, lastSaved, saveLocation));
		gl.setVerticalGroup(createVerticalGroup(gl, lastSaved, saveLocation));
	}
	
	@Override
	public void update() {
		lastSaved.setText("Last Saved: " + formatDate(manager.getSelectedProfile()));
		if(manager.getSelectedProfile() == null) {
			saveLocation.setText("?");
		}
		else {
			saveLocation.setText("File: " + (manager.getBackupDirectory().getAbsolutePath() + "\\" + manager.getSelectedProfile().name));
		}
	}
	
	private void buildComponents() {
		lastSaved = label("Last Saved: " + formatDate(manager.getSelectedProfile()));
		saveLocation = label("uwu");
		if(manager.getSelectedProfile() == null) {
			saveLocation.setText("File: ?");
		}
		else {
			saveLocation.setText("File: " + (manager.getBackupDirectory().getAbsolutePath() + "\\" + manager.getSelectedProfile().name));
		}
	}

	private String formatDate(Profile selectedSave) {
		if(selectedSave == null || selectedSave.lastSaved == 0) {
			return "Never.";
		}

		return format.format(new java.util.Date(selectedSave.lastSaved));
	}

}
