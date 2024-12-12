package navigator;

import javafx.geometry.Point2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Абстрактный класс для обработки данных о метро.
 * Этот класс предоставляет методы для загрузки данных, инициализации матрицы смежности и координат станций,
 * а также логирования процесса инициализации.
 */
public abstract class MetroDataHandler {

    // Логгер для класса
    private static final Logger logger = LogManager.getLogger(MetroDataHandler.class);

    protected double[][] adjacencyMatrix;

    /**
     * Абстрактный метод для загрузки данных о метро в матрицу смежности.
     * Этот метод должен быть реализован в подклассе для загрузки специфических данных.
     */
    public abstract void loadMetroData();

    /**
     * Метод для получения матрицы смежности.
     * @return Матрица смежности, представляющая граф метро.
     */
    public double[][] getAdjacencyMatrix() {
        logger.debug("Получение матрицы смежности.");
        return adjacencyMatrix;
    }

    /**
     * Абстрактный метод для инициализации названий станций.
     * Этот метод должен быть реализован в подклассе для инициализации с учетом специфики данных.
     * @return Карта, где ключ — это индекс станции, а значение — её название.
     */
    public abstract Map<Integer, String> initializeStationNames();

    /**
     * Абстрактный метод для инициализации координат станций.
     * Этот метод должен быть реализован в подклассе для инициализации с учетом координат каждой станции.
     * @return Карта, где ключ — это индекс станции, а значение — её координаты.
     */
    public abstract Map<Integer, Point2D> initializeStationCoordinates();

    /**
     * Метод для логирования процесса инициализации данных.
     * @param dataType Тип инициализируемых данных (например, "Станции" или "Координаты").
     */
    public void logInitialization(String dataType) {
        logger.info("Инициализация данных: {}", dataType);
    }
}
