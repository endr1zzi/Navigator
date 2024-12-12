package navigator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Исключение, выбрасываемое, когда станция не найдена в данных метро.
 * Это исключение используется в случае, если в процессе обработки данных
 * не удается найти станцию по имени.
 */
public class StationNotFoundException extends Exception {

  private static final Logger logger = LogManager.getLogger(StationNotFoundException.class);

  /**
   * Конструктор для создания исключения с сообщением.
   *
   * @param message Сообщение об ошибке, объясняющее, почему станция не найдена.
   */
  public StationNotFoundException(String message) {
    super(message);
    logger.error("Station not found: " + message);  // Логируем ошибку с сообщением
  }
}
