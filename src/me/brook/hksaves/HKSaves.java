package me.brook.hksaves;

import javax.swing.SwingUtilities;

public class HKSaves {
	
	private SaveManager manager;
	private Gui gui;
	
	public HKSaves() {
	}
	
	public void createManager() {
		manager = new SaveManager(this);
	}
	
	public void createGui() {
		gui = new Gui(this);
		gui.createFrame();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	HKSaves plugin = new HKSaves();
            	plugin.createManager();
            	plugin.createGui();
            }
        });

	}
	
	public SaveManager getManager() {
		return manager;
	}
	
	public Gui getGui() {
		return gui;
	}

}
