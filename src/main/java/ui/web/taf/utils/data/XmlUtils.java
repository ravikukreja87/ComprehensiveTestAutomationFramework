package ui.web.taf.utils.data;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import ui.web.taf.utils.core.LoggingUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlUtils {

    private Document document;

    /**
     * Parses an XML string into a DOM Document.
     *
     * @param xmlContent The XML content as a string.
     */
    public void parseXml(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlContent));
            document = builder.parse(is);
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            LoggingUtils.error("Failed to parse XML content: " + e.getMessage());
            throw new RuntimeException("Failed to parse XML content", e);
        }
    }

    /**
     * Gets the text content of the first element with the given tag name.
     *
     * @param tagName The name of the tag.
     * @return The text content of the node.
     */
    public String getNodeValue(String tagName) {
        if (document == null) {
            throw new IllegalStateException("XML content has not been parsed. Call parseXml() first.");
        }
        try {
            return document.getElementsByTagName(tagName).item(0).getTextContent();
        } catch (Exception e) {
            LoggingUtils.error("Failed to get node value for tag: " + tagName + ". Error: " + e.getMessage());
            throw new RuntimeException("Failed to get node value", e);
        }
    }
}
