package org.freeplane.export;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGeneratorContext.GraphicContextDefaults;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public abstract class ExportVectorGraphic {

	protected SVGGraphics2D fillSVGGraphics2D(final JFrame view) {

		// work around svg/pdf-Export problems when exporting with Gtk or Nimbus L&Fs
		final String previousLnF = UIManager.getLookAndFeel().getClass().getName();
		setLnF(view, UIManager.getCrossPlatformLookAndFeelClassName());

		try
		{
			final DOMImplementation impl = GenericDOMImplementation.getDOMImplementation();
			final String namespaceURI = SVGConstants.SVG_NAMESPACE_URI;
			final Document domFactory = impl.createDocument(namespaceURI, "svg", null);
			final SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(domFactory);
			ctx.setEmbeddedFontsOn(true);
			
			ctx.setExtensionHandler(new GradientExtensionHandler());
			
			final GraphicContextDefaults defaults = new GraphicContextDefaults();
			defaults.setFont(new Font("Arial", Font.PLAIN, 12));
			ctx.setGraphicContextDefaults(defaults);
			ctx.setPrecision(12);
			final SVGGraphics2D g2d = new SVGGraphics2D(ctx, false);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
			
//			view.preparePrinting();
			
//			final Rectangle innerBounds = view.getInnerBounds();
			final Rectangle innerBounds = view.getBounds();
			
			g2d.setSVGCanvasSize(new Dimension(innerBounds.width, innerBounds.height));
			g2d.translate(-innerBounds.x, -innerBounds.y);
			view.print(g2d);
			
//			view.endPrinting();
			
			return g2d;
		}
		finally
		{
			setLnF(view, previousLnF);
		}
	}

	private void setLnF(final JFrame frame, final String LnF)
	{
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(LnF);

//			Frame frame = JOptionPane.getFrameForComponent(view.getRoot().getRootPane());
			
			SwingUtilities.updateComponentTreeUI(frame);
			// this is recommended but causes the root node to be shifted to the bottom right corner :-(
			// frame.pack();
		}
		catch(Exception ex)
		{
			throw new RuntimeException("Error when changing L&F for SVG Export!", ex);
		}
	}

}
