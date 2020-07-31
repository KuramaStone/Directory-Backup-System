package me.brook.hksaves.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import me.brook.hksaves.HKSaves;
import me.brook.hksaves.SaveManager;
import me.brook.hksaves.panels.style.ButtonDesign;

@SuppressWarnings("serial")
public abstract class CustomPanel extends JPanel {
	
	protected HKSaves plugin;
	protected SaveManager manager;

	protected Color primary, secondary, secondaryDark;

	public CustomPanel(HKSaves plugin, Color primary, Color secondary, Color secondaryDark) {
		this.plugin = plugin;
		this.primary = primary;
		this.secondary = secondary;
		this.secondaryDark = secondaryDark;
		
		manager = plugin.getManager();
		
		plugin.getGui().register(this);

		setOpaque(false);
	}

	public CustomPanel(HKSaves plugin) {
		this(plugin, null, null, null);
	}
	
	public void update() {
		
	}

	protected void constructBorder(String name) {
		TitledBorder title = new TitledBorder(name);
		title.setTitleColor(primary);
		title.setBorder(BorderFactory.createLineBorder(secondary, 3, true));
		this.setBorder(title);
	}

	public static SequentialGroup createHorizontalGroup(GroupLayout gl, Component... components) {
		SequentialGroup horGroup = gl.createSequentialGroup();
		ParallelGroup group = gl.createParallelGroup();
		for(Component component : components) {
			if(component instanceof Gap) {
				group.addGap(((Gap) component).getGap());
			}
			else {
				group.addComponent(component);
			}
		}
		horGroup.addGroup(group);
		return horGroup;
	}

	public static SequentialGroup createVerticalGroup(GroupLayout gl, Component... components) {
		SequentialGroup verGroup = gl.createSequentialGroup();

		for(Component component : components) {
			ParallelGroup group = gl.createParallelGroup(Alignment.BASELINE);
			if(component instanceof Gap) {
				group.addGap(((Gap) component).getGap());
			}
			else {
				group.addComponent(component);
			}
			verGroup.addGroup(group);
		}

		return verGroup;
	}

	public JLabel label(String string) {
		return label(string, Color.BLACK, new Font("Trajan Pro Regular", Font.BOLD, 14));
	}

	public JLabel label(String string, Color color, Font font) {
		JLabel label = new JLabel(string);
		label.setForeground(color);
		label.setFont(font);
		return label;
	}

	public JButton button(String string) {
		return button(string, Color.BLACK, new Font("Trajan Pro Regular", Font.BOLD, 14));
	}

	public JButton button(String string, Color color, Font font) {
		JButton button = new JButton(string);
		button.setUI(new ButtonDesign());
		button.setFont(font);
		button.setForeground(color);
		return button;
	}

	public JToggleButton toggleButton(String string) {
		JToggleButton button = new JToggleButton(string);
		return button;
	}

	public static class Gap extends Component {
		private int gap;

		public Gap(int gap) {
			this.gap = gap;
		}

		public int getGap() {
			return gap;
		}
	}
}
