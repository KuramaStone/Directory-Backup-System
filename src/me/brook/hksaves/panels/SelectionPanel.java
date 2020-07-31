package me.brook.hksaves.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.brook.hksaves.HKSaves;
import me.brook.hksaves.Profile;

public class SelectionPanel extends CustomPanel {

	private static final long serialVersionUID = -5396098383186532097L;

	private JLabel label = label("Select profile:");
	private JList<String> saves;
	private JButton create = button("New");
	private JButton upload = button("Upload", Color.GREEN, new Font("Tahoma", Font.BOLD, 14));

	private SelectionEditorPanel editor;

	private boolean updateMenu = true, areYouSure = false;

	public SelectionPanel(HKSaves plugin) {
		super(plugin);
		buildComponents();
		constructBorder("Selection");
		addComponents();
	}
	
	private void buildComponents() {
		editor = new SelectionEditorPanel(plugin);
		buildSaves();

		upload.setEnabled(false);
		upload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(areYouSure) {
					manager.uploadProfile(manager.getSelectedProfile());
					upload.setText("Upload");
					areYouSure = false;
				}
				else {
					upload.setText("Are you sure?");
					areYouSure = true;
				}
			}
		});
		
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(plugin.getGui().getFrame(), "Profile name: ", "Create profile",
						JOptionPane.PLAIN_MESSAGE);
				Profile prof = new Profile(name, 0);
				manager.addProfile(prof);
			}
		});
	}

	@Override
	public void update() {
		updateMenu = false;
		// This will keep the previous selection selected if it changes
		Profile selected = manager.getSelectedProfile();
		DefaultListModel<String> model = (DefaultListModel<String>) saves.getModel();
		model.clear();
		manager.getProfiles().keySet().forEach(name -> model.addElement(name));

		if(selected != null) {
			int index;
			for(index = 0; index < model.size(); index++) {
				if(model.get(index).equals(selected.name)) {
					break;
				}
			}

			saves.setSelectedIndex(index);
		}
		updateMenu = true;

		upload.setEnabled(selected != null);
	}

	private void buildSaves() {
		DefaultListModel<String> model = new DefaultListModel<String>();

		// Load saves
		manager.getProfiles().keySet().forEach(name -> model.addElement(name));

		saves = new JList<String>(model);
		saves.setSelectedIndex(ListSelectionModel.SINGLE_SELECTION);
		saves.setLayoutOrientation(JList.VERTICAL);
		saves.setVisibleRowCount(4);
		saves.setPreferredSize(new Dimension(250, 500));

		saves.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(updateMenu) {
					String string = saves.getSelectedValue();
					manager.setSelectedProfile(manager.getProfile(string));
				}
			}

		});
	}

	private void addComponents() {
		JPanel top = new JPanel();
		top.setOpaque(false);

		JPanel left = new JPanel();
		left.setOpaque(false);

		GroupLayout gl = new GroupLayout(left);
		left.setLayout(gl);

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		JScrollPane scroll = new JScrollPane(saves);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		gl.setHorizontalGroup(createHorizontalGroup(gl, label, scroll, create, new Gap(25), upload));
		gl.setVerticalGroup(createVerticalGroup(gl, label, scroll, create, new Gap(25), upload));

		top.setLayout(new BorderLayout());
		top.add(left, BorderLayout.WEST);
		top.add(editor, BorderLayout.EAST);

		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(new InformationPanel(plugin), BorderLayout.SOUTH);
	}

}
