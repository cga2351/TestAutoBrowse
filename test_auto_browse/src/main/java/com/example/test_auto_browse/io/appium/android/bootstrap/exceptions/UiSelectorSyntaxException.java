package com.example.test_auto_browse.io.appium.android.bootstrap.exceptions;

import com.example.test_auto_browse.io.appium.android.bootstrap.utils.UiSelectorParser;

@SuppressWarnings("serial")
public class UiSelectorSyntaxException extends Exception {

  /**
   * An exception involving an {@link UiSelectorParser}.
   *
   * @param msg
   *          A descriptive message describing the error.
   */
  public UiSelectorSyntaxException(final String msg) {
    super(msg);
  }
}
