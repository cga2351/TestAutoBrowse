package com.example.test_auto_browse.io.appium.android.bootstrap.exceptions;

import com.example.test_auto_browse.io.appium.android.bootstrap.selector.Strategy;

@SuppressWarnings("serial")
public class InvalidStrategyException extends Exception {
  /**
   * An exception that is thrown when an invalid strategy is used.
   *
   * @param msg
   *          A descriptive message describing the error.
   * @see {@link Strategy}
   */
  public InvalidStrategyException(final String msg) {
    super(msg);
  }
}
