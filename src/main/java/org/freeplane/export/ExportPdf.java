package org.freeplane.export;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFrame;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ExportPdf extends ExportVectorGraphic {
	
	private static float FONT_SCALE_FACTOR = 1.0f;

	public ExportPdf() {
	}

	public void export(final JFrame frame, File chosenFile) {
		try {
			final SVGGraphics2D g2d = fillSVGGraphics2D(frame);
			final PDFTranscoder pdfTranscoder = new PDFTranscoder();
			/*
			 * according to https: &aid=1921334&group_id=7118 Submitted By:
			 * Frank Spangenberg (f_spangenberg) Summary: Large mind maps
			 * produce invalid PDF
			 */
			pdfTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_MAX_HEIGHT, new Float(19200));
			pdfTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_MAX_WIDTH, new Float(19200));
//			pdfTranscoder.addTranscodingHint(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, 25.4f/72f/FONT_SCALE_FACTOR);
			
			/* end patch */	
			final Document doc = g2d.getDOMFactory();
			final Element rootE = doc.getDocumentElement();
			g2d.getRoot(rootE);
			final TranscoderInput input = new TranscoderInput(doc);
			final FileOutputStream ostream = new FileOutputStream(chosenFile);
			final BufferedOutputStream bufStream = new BufferedOutputStream(ostream);
			final TranscoderOutput output = new TranscoderOutput(bufStream);
			pdfTranscoder.transcode(input, output);
			ostream.flush();
			ostream.close();
		}
		catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
