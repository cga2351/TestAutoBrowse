package com.example.test_auto_browse.io.appium.android.bootstrap.handler;

import com.example.test_auto_browse.io.appium.android.bootstrap.utils.ReflectionUtils;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommand;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommandResult;
import com.example.test_auto_browse.io.appium.android.bootstrap.CommandHandler;
import com.example.test_auto_browse.io.appium.android.bootstrap.utils.XMLHierarchy;
import org.json.JSONException;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Get page source. Return as string of XML doc
 */
public class Source extends CommandHandler {
  @Override
  public AndroidCommandResult execute(final AndroidCommand command) throws JSONException {
    ReflectionUtils.clearAccessibilityCache();

    final Document doc = (Document) XMLHierarchy.getFormattedXMLDoc();

    final TransformerFactory tf = TransformerFactory.newInstance();
    final StringWriter writer = new StringWriter();
    Transformer transformer;
    String xmlString;


    try {
      transformer = tf.newTransformer();
      transformer.transform(new DOMSource(doc), new StreamResult(writer));
      xmlString = writer.getBuffer().toString();

    } catch (final TransformerConfigurationException e) {
      e.printStackTrace();
      throw new RuntimeException("Something went terribly wrong while converting xml document to string");
    } catch (final TransformerException e) {
      return getErrorResult("Could not parse xml hierarchy to string: " + e.getMessage());
    }

    return getSuccessResult(xmlString);
  }
}
