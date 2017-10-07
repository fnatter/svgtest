package org.freeplane.svgtest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freeplane.export.ExportPdf;

import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;

public class TestBatik {
	private static final int PREFERREDSIZE_HEIGHT = 128;
	private static final int PREFERREDSIZE_WIDTH = 128;
	private static SVGUniverse svgUniverse;
	private final static String TEST_SVG = "button_ok.svg";

    private static JFrame createAndShowGUI() {
        JFrame frame = new JFrame("svgSalamander Autostretch test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));

        final URL resource = ClassLoader.getSystemResource(TEST_SVG);
        
        final JPanel exampleImagesPanel = new JPanel();
        exampleImagesPanel.setLayout(new FlowLayout());
        
        exampleImagesPanel.add(new JLabel(String.format("%dx%d, AUTOSIZE_STRETCH", PREFERREDSIZE_WIDTH, PREFERREDSIZE_HEIGHT)));
        final SVGIcon testIcon1 = createSVGIcon(resource, PREFERREDSIZE_WIDTH, PREFERREDSIZE_HEIGHT,
        		SVGIcon.AUTOSIZE_STRETCH);
        final JLabel label1 = new JLabel(testIcon1);
        label1.setBorder(BorderFactory.createLineBorder(Color.black));
        exampleImagesPanel.add(label1);
        
        frame.getContentPane().add(exampleImagesPanel);

        frame.pack();
        frame.setVisible(true);
        
        return frame;
    }

	private static SVGIcon createSVGIcon(final URL url, final int widthPixels, final int heightPixels,
			final int autosize) {
		if (svgUniverse == null)
			svgUniverse = new SVGUniverse();

		final SVGIcon icon = new SVGIcon();
		URI svgUri;
		try {
			svgUri = svgUniverse.loadSVG(url.openStream(), url.getPath());
			icon.setSvgUniverse(svgUniverse);
			icon.setSvgURI(svgUri);
			icon.setPreferredSize(new Dimension(widthPixels, heightPixels));
			icon.setAutosize(autosize);
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
                final JFrame frame = createAndShowGUI();
                new ExportPdf().export(frame, new File("out.pdf"));
            }
        });
    }
 

}
