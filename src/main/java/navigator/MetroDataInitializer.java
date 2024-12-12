package navigator;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Класс MetroDataInitializer отвечает за инициализацию и загрузку данных метро, включая соединения станций,
 * названия станций и координаты станций метрополитена.
 * <p>
 * Этот класс наследует  {@link MetroDataHandler} и предоставляет методы для загрузки данных метро и инициализации станции.
 */
public class MetroDataInitializer extends MetroDataHandler {
    private static final Logger logger = LogManager.getLogger(MetroDataInitializer.class);
    /**
     * Создает новый MetroDataInitializer с указанным количеством станций.
     * Инициализирует матрицу смежности для представления соединений станций.
     *
     * @param stationCount Общее количество станций метрополитена.
     */
    public MetroDataInitializer(int stationCount) {
        adjacencyMatrix = new double[stationCount][stationCount];
        logger.info("MetroDataInitializer, созданный с помощью StationCount: " + stationCount);
    }
    /**
     * Загружает данные метро, добавляя соединения между различными станциями системы метро.
     * У каждого соединения есть значение времени, представляющее время, необходимое для перемещения между станциями.
     */

    @Override
    public void loadMetroData() {
        addConnection(0, 1, 3.0);
        addConnection(1, 2, 3.0);
        addConnection(2, 3, 3.0);
        addConnection(3, 4, 3.0);
        addConnection(4, 5, 0.2);
        addConnection(4, 6, 2.0);
        addConnection(6, 7, 2.0);
        addConnection(7, 8, 3.0);
        addConnection(7, 9, 4.0);
        addConnection(9, 10, 0.2);
        addConnection(9, 11, 0.2);
        addConnection(11, 12, 0.1);
        addConnection(12, 13, 0.2);
        addConnection(12, 14, 2.0);
        addConnection(14, 15, 2.0);
        addConnection(15, 16, 2.0);
        addConnection(16, 17, 2.0);
        addConnection(17, 18, 3.0);
        addConnection(18, 19, 4.0);
        addConnection(19, 20, 3.0);
        addConnection(8, 21, 2.0);
        addConnection(21, 22, 0.2);
        addConnection(21, 13, 2.0);
        addConnection(13, 23, 1.8);
        addConnection(23, 24, 1.5);
        addConnection(24, 25, 1.7);
        addConnection(25, 26, 0.2);
        addConnection(26, 27, 0.9);
        addConnection(27, 28, 0.9);
        addConnection(8, 29, 1.9);
        addConnection(29, 30, 0.2);
        addConnection(29, 31, 1.2);
        addConnection(31, 32, 1.5);
        addConnection(32, 33, 1.9);
        addConnection(33, 34, 1.9);
        addConnection(34, 35, 2.0);
        addConnection(35, 36, 1.2);
        addConnection(36, 37, 1.4);
        addConnection(37, 38, 1.8);
        addConnection(38, 39, 1.1);
        addConnection(11, 40, 2.0);
        addConnection(40, 41, 0.1);
        addConnection(40, 42, 1.5);
        addConnection(42, 43, 1.5);
        addConnection(43, 44, 0.9);
        addConnection(44, 45, 1.4);
        addConnection(45, 46, 2.4);
        addConnection(46, 47, 1.6);
        addConnection(47, 48, 1.9);
        addConnection(48, 49, 2.0);
        addConnection(10, 50, 2.0);
        addConnection(50, 51, 1.2);
        addConnection(51, 52, 2.1);
        addConnection(52, 53, 1.1);
        addConnection(53, 54, 1.6);
        addConnection(54, 55, 1.9);
        addConnection(10, 22, 3.0);
        addConnection(22, 56, 1.2);
        addConnection(56, 57, 1.8);
        addConnection(57, 58, 1.9);
        addConnection(58, 59, 2.0);
        addConnection(59, 60, 1.9);
        addConnection(60, 61, 1.2);
        addConnection(61, 62, 2.1);
        addConnection(5, 63, 2.2);
        addConnection(63, 64, 1.4);
        addConnection(64, 65, 1.9);
        addConnection(65, 66, 1.9);
        addConnection(66, 67, 1.8);
        addConnection(5, 30, 3.0);
        addConnection(30, 41, 3.0);
        addConnection(41, 68, 1.5);
        addConnection(68, 69, 1.2);
        addConnection(69, 70, 1.9);
        addConnection(70, 71, 2.0);
        logger.debug("Данные Metro успешно загружены.");
    }
    /**
     * Добавляет соединение между двумя станциями в системе метро.
     * Соединение двустороннее с одинаковым временем в пути в обоих направлениях..
     *
     * @param station1 Первая станция.
     * @param station2 Конечная станция.
     * @param time     Время, необходимое для поездки между двумя станциями.
     */
    public void addConnection(int station1, int station2, double time) {
        logger.trace("Добавление связи между станциями " + station1 + " и станция " + station2 + " со временем " + time);
        adjacencyMatrix[station1][station2] = time;
        adjacencyMatrix[station2][station1] = time;
    }
    /**
     * Инициализирует названия станций для системы метро и регистрирует процесс.
     *
     * @return Карта, содержащая индексы станций и соответствующие им названия.
     */
    @Override
    public Map<Integer, String> initializeStationNames() {
        logger.info("Инициализация названий станций...");
        Map<Integer, String> stationNames = new HashMap<>();
        stationNames.put(0, "Улица Дыбенко");
        stationNames.put(1, "Проспект Большевиков");
        stationNames.put(2, "Ладожская");
        stationNames.put(3, "Новочеркасская");
        stationNames.put(4, "Площадь Александра Невского 2");
        stationNames.put(5, "Площадь Александра Невского 1");
        stationNames.put(6, "Лиговская");
        stationNames.put(7, "Достоевская");
        stationNames.put(8, "Владимирская");
        stationNames.put(9, "Спасская");
        stationNames.put(10, "Садовая");
        stationNames.put(11, "Сенная");
        stationNames.put(12, "Технологический институт 2");
        stationNames.put(13, "Технологический институт 1");
        stationNames.put(14, "Фрунзенская");
        stationNames.put(15, "Московские ворота");
        stationNames.put(16, "Электросила");
        stationNames.put(17, "Парк Победы");
        stationNames.put(18, "Московская");
        stationNames.put(19, "Звездная");
        stationNames.put(20, "Купчино");
        stationNames.put(21, "Пушкинская");
        stationNames.put(22, "Звенигородская");
        stationNames.put(23, "Балтийская");
        stationNames.put(24, "Нарвская");
        stationNames.put(25, "Кировский завод");
        stationNames.put(26, "Автово");
        stationNames.put(27, "Ленинский проспект");
        stationNames.put(28, "Проспект Ветеранов");
        stationNames.put(29, "Площадь Восстания");
        stationNames.put(30, "Маяковская");
        stationNames.put(31, "Чернышевская");
        stationNames.put(32, "Площадь Ленина");
        stationNames.put(33, "Выборгская");
        stationNames.put(34, "Лесная");
        stationNames.put(35, "Площадь Мужества");
        stationNames.put(36, "Политехническая");
        stationNames.put(37, "Академическая");
        stationNames.put(38, "Гражданский проспект");
        stationNames.put(39, "Девяткино");
        stationNames.put(40, "Невский проспект");
        stationNames.put(41, "Гостиный двор");
        stationNames.put(42, "Горьковская");
        stationNames.put(43, "Петроградская");
        stationNames.put(44, "Черная речка");
        stationNames.put(45, "Пионерская");
        stationNames.put(46, "Удельная");
        stationNames.put(47, "Озерки");
        stationNames.put(48, "Проспект Просвящения");
        stationNames.put(49, "Парнас");
        stationNames.put(50, "Адмиралтейская");
        stationNames.put(51, "Спортивная");
        stationNames.put(52, "Чкаловская");
        stationNames.put(53, "Крестовский остров");
        stationNames.put(54, "Старая деревня");
        stationNames.put(55, "Комендатский проспект");
        stationNames.put(56, "Обводный канал");
        stationNames.put(57, "Волковская");
        stationNames.put(58, "Бухарестская");
        stationNames.put(59, "Международная");
        stationNames.put(60, "Проспект Славы");
        stationNames.put(61, "Дунайская");
        stationNames.put(62, "Шушары");
        stationNames.put(63, "Елизаровская");
        stationNames.put(64, "Ломоносовская");
        stationNames.put(65, "Пролетарская");
        stationNames.put(66, "Обухово");
        stationNames.put(67, "Рыбацкое");
        stationNames.put(68, "Василеостровская");
        stationNames.put(69, "Приморская");
        stationNames.put(70, "Зенит");
        stationNames.put(71, "Беговая");
        logger.info("Названия станций инициализированы.");
        return stationNames;

    }
    /**
     * Инициализирует координаты станции для системы метро и регистрирует процесс.
     *
     * @return Карта, содержащая индексы станций и соответствующие им координаты (x, y).
     */
    @Override
    public Map<Integer, Point2D> initializeStationCoordinates() {
        logger.info("Инициализация координат станции...");
        Map<Integer, Point2D> stationCoordinates = new HashMap<>();
        stationCoordinates.put(0, new Point2D(870, 830)); // Улица Дыбенко
        stationCoordinates.put(1, new Point2D(870, 763)); // Проспект Большевиков
        stationCoordinates.put(2, new Point2D(870, 676)); // Ладожская
        stationCoordinates.put(3, new Point2D(870, 615)); // Новочеркасская
        stationCoordinates.put(4, new Point2D(770, 555)); // Площадь Александра Невского 2
        stationCoordinates.put(5, new Point2D(750, 555)); // Площадь Александра Невского 1
        stationCoordinates.put(6, new Point2D(685, 555)); // Лиговская
        stationCoordinates.put(7, new Point2D(615, 555)); // Достоевская
        stationCoordinates.put(8, new Point2D(600, 555)); // Владимирская
        stationCoordinates.put(9, new Point2D(440, 555)); // Спасская
        stationCoordinates.put(10, new Point2D(425, 548)); // Садовая
        stationCoordinates.put(11, new Point2D(425, 568)); // Сенная
        stationCoordinates.put(12, new Point2D(433, 675)); // Технологический институт 2
        stationCoordinates.put(13, new Point2D(420, 675)); // Технологический институт 1
        stationCoordinates.put(14, new Point2D(428, 743)); // Фрунзенская
        stationCoordinates.put(15, new Point2D(428, 782)); // Московские ворота
        stationCoordinates.put(16, new Point2D(428, 825)); // Электросила
        stationCoordinates.put(17, new Point2D(428, 863)); // Парк Победы
        stationCoordinates.put(18, new Point2D(428, 906)); // Московская
        stationCoordinates.put(19, new Point2D(428, 933)); // Звездная
        stationCoordinates.put(20, new Point2D(428, 962)); // Купчино
        stationCoordinates.put(21, new Point2D(498, 640)); // Пушкинская
        stationCoordinates.put(22, new Point2D(513, 640)); // Звенигородская
        stationCoordinates.put(23, new Point2D(293, 740)); // Балтийская
        stationCoordinates.put(24, new Point2D(212, 776)); // Нравская
        stationCoordinates.put(25, new Point2D(173, 809)); // Кировский завод
        stationCoordinates.put(26, new Point2D(172, 840)); // Автово
        stationCoordinates.put(27, new Point2D(172, 875)); // Ленинский проспект
        stationCoordinates.put(28, new Point2D(172, 919)); // Проспект Ветеранов
        stationCoordinates.put(29, new Point2D(615, 485)); // Площадь Восстания
        stationCoordinates.put(30, new Point2D(600, 485)); // Маяковская
        stationCoordinates.put(31, new Point2D(607, 432)); // Чернышевская
        stationCoordinates.put(32, new Point2D(607, 372)); // Площадь Ленина
        stationCoordinates.put(33, new Point2D(607, 329)); // Выборгоская
        stationCoordinates.put(34, new Point2D(607, 280)); // Лесная
        stationCoordinates.put(35, new Point2D(607, 217)); // Площадь Мужества
        stationCoordinates.put(36, new Point2D(607, 173)); // Политехническая
        stationCoordinates.put(37, new Point2D(607, 122)); // Академическая
        stationCoordinates.put(38, new Point2D(607, 77)); // Гражданский проспект
        stationCoordinates.put(39, new Point2D(607, 28)); // Девяткино
        stationCoordinates.put(40, new Point2D(420, 483)); // Невский проспект
        stationCoordinates.put(41, new Point2D(434, 483)); // Гостиный двор
        stationCoordinates.put(42, new Point2D(427, 400)); // Горьковская
        stationCoordinates.put(43, new Point2D(427, 321)); // Петроградская
        stationCoordinates.put(44, new Point2D(427, 238)); // Чёрная речка
        stationCoordinates.put(45, new Point2D(427, 191)); // Пионерская
        stationCoordinates.put(46, new Point2D(427, 151)); // Удельная
        stationCoordinates.put(47, new Point2D(427, 110)); // Озерки
        stationCoordinates.put(48, new Point2D(427, 66)); // Проспект Просвещния
        stationCoordinates.put(49, new Point2D(427, 28)); // Парнас
        stationCoordinates.put(50, new Point2D(388, 505)); // Адмиралтейская
        stationCoordinates.put(51, new Point2D(305, 410)); // Спортивная
        stationCoordinates.put(52, new Point2D(270, 370)); // Чкаловская
        stationCoordinates.put(53, new Point2D(242, 310)); // Крестовский остров
        stationCoordinates.put(54, new Point2D(242, 226)); // Старая деревня
        stationCoordinates.put(55, new Point2D(242, 170)); // Комендатский проспект
        stationCoordinates.put(56, new Point2D(562, 709)); // Обводный канал
        stationCoordinates.put(57, new Point2D(599, 751)); // Волковская
        stationCoordinates.put(58, new Point2D(620, 828)); // Бухаретская
        stationCoordinates.put(59, new Point2D(620, 884)); // Международная
        stationCoordinates.put(60, new Point2D(620, 937)); // Проспект Славы
        stationCoordinates.put(61, new Point2D(620, 990)); // Дунайская
        stationCoordinates.put(62, new Point2D(620, 1047)); // Шушары
        stationCoordinates.put(63, new Point2D(760, 828)); // Елизаровская
        stationCoordinates.put(64, new Point2D(760, 884)); // Ломоносовская
        stationCoordinates.put(65, new Point2D(760, 937)); // Пролетарская
        stationCoordinates.put(66, new Point2D(760, 990)); // Обухово
        stationCoordinates.put(67, new Point2D(760, 1047)); // Рыбацкое
        stationCoordinates.put(68, new Point2D(295, 483)); // Василеостровская
        stationCoordinates.put(69, new Point2D(114, 407)); // Приморская
        stationCoordinates.put(70, new Point2D(114, 301)); // Зенит
        stationCoordinates.put(71, new Point2D(114, 233)); // Беговая
        logger.info("Координаты станции инициализированы.");
        return stationCoordinates;
    }
}
