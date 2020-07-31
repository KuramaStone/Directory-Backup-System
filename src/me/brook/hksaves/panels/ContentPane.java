package me.brook.hksaves.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ContentPane extends JPanel {

	private static final long serialVersionUID = -7102448331400513499L;

//	private Image background;

	public ContentPane() {
		this.setName(this.getName() + ".contentPane");
		this.setLayout(new BorderLayout() {
			private static final long serialVersionUID = 1L;

			/*
			 * This BorderLayout subclass maps a null constraint to CENTER. Although the
			 * reference BorderLayout also does this, some VMs throw an
			 * IllegalArgumentException.
			 */
			public void addLayoutComponent(Component comp, Object constraints) {
				if(constraints == null) {
					constraints = BorderLayout.CENTER;
				}
				super.addLayoutComponent(comp, constraints);
			}
		});
//
//		try {
//			background = ImageIO.read(new File("res/background.png"));
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
	}
	
	@Override
	protected void paintChildren(Graphics g) {
//		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		super.paintChildren(g);
	}
}
