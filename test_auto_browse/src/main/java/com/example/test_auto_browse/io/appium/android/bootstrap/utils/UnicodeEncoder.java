package com.example.test_auto_browse.io.appium.android.bootstrap.utils;

import com.example.test_auto_browse.utils.Logger;

import java.nio.charset.Charset;


public class UnicodeEncoder {
  private static final Charset M_UTF7 = Charset.forName("x-IMAP-mailbox-name");
  private static final Charset ASCII  = Charset.forName("US-ASCII");


  public static String encode(final String text) {
    byte[] encoded = text.getBytes(M_UTF7);
    String ret = new String(encoded, ASCII);
    if (ret.charAt(ret.length()-1) != text.charAt(text.length()-1) && !ret.endsWith("-")) {
      // in some cases there is a problem and the closing tag is not added
      // to the encoded text (for instance, with `ü`)
      Logger.debug("Closing tag missing. Adding.");
      ret = ret + "-";
    }
    return ret;
  }

  public static boolean needsEncoding(final String text) {
    char[] chars = text.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      int cp = Character.codePointAt(chars, i);
      if (cp > 0x7F || cp == '&') {
        // Selenium uses a Unicode PUA to cover certain special characters
        // see https://code.google.com/p/selenium/source/browse/java/client/src/org/openqa/selenium/Keys.java
        // these should juse be passed through as is.
        return !(cp >= 0xE000 && cp <= 0xE040);
      }
    }
    return false;
  }
}
