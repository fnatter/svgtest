package org.freeplane.export;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JFrame;

import org.apache.batik.svggen.SVGGraphics2D;

public class ExportSvg extends ExportVectorGraphic {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void export(JFrame frame, File chosenFile) {
		try {
			final SVGGraphics2D g2d = fillSVGGraphics2D(frame);
			final FileOutputStream bos = new FileOutputStream(chosenFile);
			final BufferedOutputStream bufStream = new BufferedOutputStream(bos);
			final OutputStreamWriter osw = new OutputStreamWriter(bufStream, "UTF-8");
			g2d.stream(osw);
			osw.flush();
			bos.flush();
			bos.close();
		}
		catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
