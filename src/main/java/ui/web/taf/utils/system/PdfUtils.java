package ui.web.taf.utils.system;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import ui.web.taf.utils.core.LoggingUtils;

import java.io.File;
import java.io.IOException;

public class PdfUtils {

    /**
     * Reads the text content from a PDF file.
     *
     * @param filePath The path to the PDF file.
     * @return The extracted text from the PDF.
     */
    public static String readPdfText(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                LoggingUtils.info("Reading text from PDF: " + filePath);
                return stripper.getText(document);
            } else {
                LoggingUtils.error("PDF file is encrypted: " + filePath);
                throw new RuntimeException("Cannot read encrypted PDF file.");
            }
        } catch (IOException e) {
            LoggingUtils.error("Failed to read PDF file: " + e.getMessage());
            throw new RuntimeException("Failed to read PDF file", e);
        }
    }

    /**
     * Verifies if the expected text is present in the PDF file.
     *
     * @param filePath     The path to the PDF file.
     * @param expectedText The text to search for in the PDF.
     * @return True if the text is found, false otherwise.
     */
    public static boolean verifyTextInPdf(String filePath, String expectedText) {
        String pdfText = readPdfText(filePath);
        boolean found = pdfText.contains(expectedText);
        LoggingUtils.info("Verifying text in PDF. Found '" + expectedText + "': " + found);
        return found;
    }
}
