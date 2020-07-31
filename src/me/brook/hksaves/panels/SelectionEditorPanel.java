package me.brook.hksaves.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import me.brook.hksaves.HKSaves;

public class SelectionEditorPanel extends CustomPanel {

	private static final long serialVersionUID = -5396098383186532097L;

	private JButton save, rename, delete;

	private boolean areYouSure = false;

	public SelectionEditorPanel(HKSaves plugin) {
		super(plugin, Color.BLACK, Color.GRAY, null);
		buildComponents();
		constructBorder("Editor");
		addComponents();
	}

	JScrollPane scroll;

	@Override
	public void update() {

		if(manager.getSelectedProfile() == null) {
			save.setEnabled(false);
			rename.setEnabled(false);
			delete.setEnabled(false);
		}
		else {
			save.setEnabled(true);
			rename.setEnabled(true);
			delete.setEnabled(true);
		}

	}

	private void buildComponents() {
		save = button("Save");
		save.setEnabled(false);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.saveProfile(manager.getSelectedProfile());
			}
		});

		rename = button("Rename");
		rename.setEnabled(false);
		rename.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(plugin.getGui().getFrame(), "Profile name: ", "Rename profile",
						JOptionPane.PLAIN_MESSAGE);
				if(name != null) {
					manager.renameProfile(manager.getSelectedProfile(), name);
				}
			}
		});

		delete = button("Delete", Color.RED, new Font("Tahoma", Font.BOLD, 14));
		delete.setEnabled(false);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!areYouSure) {
					areYouSure = true;
					delete.setText("Are you sure?");
				}
				else {
					areYouSure = false;
					manager.removeProfile(manager.getSelectedProfile());
				}
			}
		});
	}

	private void addComponents() {
		this.setLayout(new BorderLayout());

		JPanel left = new JPanel();
		left.setOpaque(false);

		GroupLayout gl = new GroupLayout(left);
		left.setLayout(gl);

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(createHorizontalGroup(gl, rename, delete));
		gl.setVerticalGroup(createVerticalGroup(gl, rename, delete));

		this.add(left, BorderLayout.WEST);
		this.add(save, BorderLayout.EAST);
	}

}
