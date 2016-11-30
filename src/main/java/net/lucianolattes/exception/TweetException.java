package net.lucianolattes.exception;

/**
 * A <tt>TweetException</tt> is thrown if a User attempt to tweet or retweet a
 * message fails or is not allowed.
 *
 * @author lucianolattes
 */
public class TweetException extends Exception {

  private static final long serialVersionUID = 3L;

  public TweetException(String message) {
    super(message);
  }

  public TweetException(Throwable cause) {
    super(cause);
  }

  public TweetException(String message, Throwable cause) {
    super(message, cause);
  }

  public TweetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
