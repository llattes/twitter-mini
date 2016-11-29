package net.lucianolattes.exception;

public class FollowException extends Exception {

  private static final long serialVersionUID = 2L;

  public FollowException(String message) {
    super(message);
  }

  public FollowException(Throwable cause) {
    super(cause);
  }

  public FollowException(String message, Throwable cause) {
    super(message, cause);
  }

  public FollowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
