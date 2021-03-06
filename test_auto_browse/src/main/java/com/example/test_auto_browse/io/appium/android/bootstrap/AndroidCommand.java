package com.example.test_auto_browse.io.appium.android.bootstrap;

import com.example.test_auto_browse.io.appium.android.bootstrap.exceptions.CommandTypeException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This proxy embodies the command that the handlers execute.
 * 
 */
public class AndroidCommand {

  JSONObject         json;
  AndroidCommandType cmdType;

  public AndroidCommand(final String jsonStr) throws JSONException,
      CommandTypeException {
    json = new JSONObject(jsonStr);
    setType(json.getString("cmd"));
  }

  /**
   * Return the action string for this command.
   * 
   * @return String
   * @throws JSONException
   */
  public String action() throws JSONException {
    if (isElementCommand()) {
      return json.getString("action").substring(8);
    }
    return json.getString("action");
  }

  public AndroidCommandType commandType() {
    return cmdType;
  }

  /**
   * Get the {@link AndroidElement destEl} this command is to operate on (must
   * provide the "desElId" parameter).
   * 
   * @return {@link AndroidElement}
   * @throws JSONException
   */
  public AndroidElement getDestElement() throws JSONException {
    String destElId = (String) params().get("destElId");
    return AndroidElementsHash.getInstance().getElement(destElId);
  }

  /**
   * Get the {@link AndroidElement element} this command is to operate on (must
   * provide the "elementId" parameter).
   * 
   * @return {@link AndroidElement}
   * @throws JSONException
   */
  public AndroidElement getElement() throws JSONException {
    String elId = (String) params().get("elementId");
    return AndroidElementsHash.getInstance().getElement(elId);
  }

  /**
   * Returns whether or not this command is on an element (true) or device
   * (false).
   * 
   * @return boolean
   */
  public boolean isElementCommand() {
    if (cmdType == AndroidCommandType.ACTION) {
      try {
        return json.getString("action").startsWith("element:");
      } catch (final JSONException e) {
        return false;
      }
    }
    return false;
  }

  /**
   * Return a hash table of name, value pairs as arguments to the handlers
   * executing this command.
   * 
   * @return Hashtable<String, Object>
   * @throws JSONException
   */
  public Hashtable<String, Object> params() throws JSONException {
    final JSONObject paramsObj = json.getJSONObject("params");
    final Hashtable<String, Object> newParams = new Hashtable<String, Object>();
    final Iterator<?> keys = paramsObj.keys();

    while (keys.hasNext()) {
      final String param = (String) keys.next();
      newParams.put(param, paramsObj.get(param));
    }
    return newParams;
  }

  /**
   * Set the command {@link AndroidCommandType type}
   * 
   * @param stringType
   *          The string of the type (i.e. "shutdown" or "action")
   * @throws CommandTypeException
   */
  public void setType(final String stringType) throws CommandTypeException {
    if (stringType.equals("shutdown")) {
      cmdType = AndroidCommandType.SHUTDOWN;
    } else if (stringType.equals("action")) {
      cmdType = AndroidCommandType.ACTION;
    } else {
      throw new CommandTypeException("Got bad command type: " + stringType);
    }
  }
}