package me.brook.hksaves;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import me.brook.hksaves.panels.ContentPane;
import me.brook.hksaves.panels.CustomPanel;
import me.brook.hksaves.panels.SelectionPanel;
import me.brook.hksaves.panels.SettingsPanel;

public class Gui {

	private HKSaves plugin;

	private JFrame frame;

	private List<CustomPanel> panels;

	public Gui(HKSaves plugin) {
		this.plugin = plugin;
		panels = new ArrayList<>();
	}

	public void createFrame() {
		frame = new JFrame();
		frame.setTitle("Hollow Knight Save Swapper");
		frame.setSize(600, 600);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		createLayout();
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				plugin.getManager().exit();
				System.exit(0);
			}
		});
	}

	private void createLayout() {

		frame.setLayout(new BorderLayout());
		Container content = new ContentPane();
		frame.setContentPane(content);

		SelectionPanel selection = new SelectionPanel(plugin);
		SettingsPanel settings = new SettingsPanel(plugin);

		content.add(selection, BorderLayout.NORTH);
		content.add(settings, BorderLayout.SOUTH);

	}

	public JFrame getFrame() {
		return frame;
	}

	public void update() {
		panels.forEach(panel -> panel.update());
	}

	public void register(CustomPanel customPanel) {
		panels.add(customPanel);
	}

}
