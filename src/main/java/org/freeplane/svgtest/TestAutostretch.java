package org.freeplane.svgtest;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URI;
import java.net.URL;

import javax.swing.*;

import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;

public class TestAutostretch {

	private static SVGUniverse svgUniverse;
	private final static String TEST_SVG = "button_ok.svg";

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("svgSalamander Autostretch test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final URL resource = ClassLoader.getSystemResource(TEST_SVG);
        final SVGIcon testIcon = createSVGIcon(resource, 256, 256);
        final JLabel label = new JLabel(testIcon);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.getContentPane().add(label);

        frame.pack();
        frame.setVisible(true);
    }

	private static SVGIcon createSVGIcon(final URL url, final int widthPixels, final int heightPixels) {
		if (svgUniverse == null)
			svgUniverse = new SVGUniverse();

		final SVGIcon icon = new SVGIcon();
		URI svgUri;
		try {
			svgUri = svgUniverse.loadSVG(url.openStream(), url.getPath());
			icon.setSvgUniverse(svgUniverse);
			icon.setSvgURI(svgUri);
			icon.setPreferredSize(new Dimension(widthPixels, heightPixels));
			icon.setAutosize(SVGIcon.AUTOSIZE_VERT);
			icon.setAntiAlias(true);
			return icon;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
 
}
