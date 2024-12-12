package navigator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Класс для реализации алгоритма Дейкстры.
 * Этот класс использует алгоритм Дейкстры для нахождения кратчайшего пути между двумя станциями в графе,
 * представленном матрицей смежности.
 */
public class DijkstraAlgorithm {

    // Логгер для класса
    private static final Logger logger = LogManager.getLogger(DijkstraAlgorithm.class);

    private double[][] adjacencyMatrix;

    /**
     * Конструктор для инициализации алгоритма Дейкстры.
     * @param adjacencyMatrix Матрица смежности, представляющая граф.
     */
    public DijkstraAlgorithm(double[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    /**
     * Метод для нахождения кратчайшего пути между двумя станциями.
     * Алгоритм Дейкстры используется для нахождения кратчайшего пути от начальной станции к конечной.
     * @param start Индекс начальной станции.
     * @param end Индекс конечной станции.
     * @return Список индексов станций, представляющих кратчайший путь.
     */
    public List<Integer> findShortestPath(int start, int end) {
        int n = adjacencyMatrix.length;
        double[] dist = new double[n];
        Arrays.fill(dist, Double.MAX_VALUE);
        dist[start] = 0;
        boolean[] visited = new boolean[n];
        int[] previous = new int[n];
        Arrays.fill(previous, -1);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingDouble(i -> dist[i]));
        pq.add(start);

        logger.info("Начинаем поиск кратчайшего пути от станции {} до станции {}", start, end);

        while (!pq.isEmpty()) {
            int u = pq.poll();
            logger.debug("Обрабатываем станцию: {}", u);

            if (u == end) {
                logger.info("Достигнута конечная станция: {}", u);
                break;
            }
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && adjacencyMatrix[u][v] != 0) {
                    double newDist = dist[u] + adjacencyMatrix[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        previous[v] = u;
                        pq.add(v);
                        logger.debug("Обновлено расстояние до станции {}: {}", v, newDist);
                    }
                }
            }
        }
        List<Integer> path = reconstructPath(previous, start, end);
        if (path.isEmpty()) {
            logger.warn("Путь не найден между станциями {} и {}", start, end);
        } else {
            logger.info("Путь найден между станциями {} и {}", start, end);
        }
        return path;
    }

    /**
     * Метод для восстановления пути на основе массива предыдущих станций.
     * @param previous Массив, содержащий индексы предыдущих станций на пути.
     * @param start Индекс начальной станции.
     * @param end Индекс конечной станции.
     * @return Список индексов станций, составляющих восстановленный путь.
     */
    private List<Integer> reconstructPath(int[] previous, int start, int end) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.add(at);
        }
        Collections.reverse(path);

        if (path.isEmpty()) {
            logger.warn("Не удалось восстановить путь между станциями {} и {}", start, end);
        } else {
            logger.debug("Восстановленный путь: {}", path);
        }

        return path;
    }
}
