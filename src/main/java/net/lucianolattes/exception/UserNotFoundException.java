package net.lucianolattes.exception;

/**
 * A <tt>UserNotFoundException</tt> is thrown if the User targeted for a lookup
 * does not exist in the DB.
 *
 * @author lucianolattes
 */
public class UserNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(Throwable cause) {
    super(cause);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
