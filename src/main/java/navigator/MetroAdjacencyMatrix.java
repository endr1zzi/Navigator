package navigator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Класс для работы с матрицей смежности метро.
 * Этот класс реализует логику поиска маршрута между двумя станциями с использованием алгоритма Дейкстры.
 */
public class MetroAdjacencyMatrix {

    // Логгер для класса
    private static final Logger logger = LogManager.getLogger(MetroAdjacencyMatrix.class);

    /**
     * Главный метод для запуска поиска маршрута.
     * Этот метод инициирует загрузку данных о метро и позволяет пользователю ввести начальную и конечную станции.
     * @param args Аргументы командной строки.
     */
    public static void start(String[] args) {
        // Создание объекта для загрузки данных о метро с 72 станциями
        logger.info("Инициализация загрузки данных о метро...");
        MetroDataHandler metroDataInitializer = new MetroDataInitializer(72);  // Пример: 72 станции
        metroDataInitializer.loadMetroData();  // Загрузка данных в матрицу смежности
        Map<Integer, String> stationNames = metroDataInitializer.initializeStationNames();  // Получение названий станций

        // Получение ввода от пользователя
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название начальной станции: ");
        String startStation = scanner.nextLine();
        System.out.print("Введите название конечной станции: ");
        String endStation = scanner.nextLine();

        // Попытка найти маршрут
        try {
            logger.debug("Пользователь ввел начальную станцию: {}", startStation);
            logger.debug("Пользователь ввел конечную станцию: {}", endStation);

            int start = getStationIndexByName(stationNames, startStation);
            int end = getStationIndexByName(stationNames, endStation);

            logger.info("Начальная станция: {}", stationNames.get(start));
            logger.info("Конечная станция: {}", stationNames.get(end));

            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(metroDataInitializer.getAdjacencyMatrix());
            List<Integer> path = dijkstra.findShortestPath(start, end);

            // Вывод маршрута
            if (path.isEmpty()) {
                logger.warn("Маршрут не найден.");
                System.out.println("Путь не найден.");
            } else {
                logger.info("Маршрут найден.");
                System.out.println("Путь:");
                for (int station : path) {
                    System.out.println(stationNames.get(station));
                    logger.debug("Прошлая станция: {}", stationNames.get(station));
                }
            }
        } catch (StationNotFoundException e) {
            logger.error("Ошибка: {}", e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для получения индекса станции по ее имени.
     * @param stationNames Карта с номерами станций и их названиями.
     * @param stationName Название станции.
     * @return Индекс станции.
     * @throws StationNotFoundException Исключение, если станция не найдена.
     */
    private static int getStationIndexByName(Map<Integer, String> stationNames, String stationName) throws StationNotFoundException {
        logger.debug("Поиск индекса станции по имени: {}", stationName);
        for (Map.Entry<Integer, String> entry : stationNames.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(stationName)) {
                return entry.getKey();
            }
        }
        logger.error("Станция с именем '{}' не найдена.", stationName);
        throw new StationNotFoundException("Ошибка в данных метро: Одна из станций не найдена.");
    }
}
