package org.joluj.model.exceptions;

import java.sql.SQLException;

public class SqlParseException extends SQLException {

  public SqlParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
