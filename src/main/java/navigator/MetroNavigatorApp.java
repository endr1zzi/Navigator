    package navigator;

    import javafx.application.Application;
    import javafx.geometry.Point2D;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.*;
    import javafx.scene.shape.Circle;
    import javafx.stage.Stage;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;

    import java.util.*;

    /**
     * Главный класс приложения, который предоставляет интерфейс навигации по метро с использованием JavaFX.
     * Пользователи могут выбрать начальную и конечную станцию, а также увидеть результат расчета маршрута.
     */
    public class MetroNavigatorApp extends Application {

        private static final Logger logger = LogManager.getLogger(MetroNavigatorApp.class); // Логгер для приложения
        private Pane mapPane; // Панель для отображения карты метро
        private TextField fromField; // Поле ввода для начальной станции
        private TextField toField; // Поле ввода для конечной станции

        /**
         * Запускает приложение, создавая графический интерфейс и выполняя инициализацию данных.
         * @param primaryStage Главное окно приложения.
         */
        @Override
        public void start(Stage primaryStage) {
            logger.info("Инициализация данных о метро.");

            // Инициализация данных о метро
            MetroDataInitializer metroDataInitializer = new MetroDataInitializer(72); // Пример: 72 станции
            metroDataInitializer.loadMetroData();
            Map<Integer, String> stationNames = metroDataInitializer.initializeStationNames();
            double[][] adjacencyMatrix = metroDataInitializer.getAdjacencyMatrix();

            // Кнопки и поля для ввода
            Button fromButton = new Button("Откуда");
            fromField = new TextField();
            fromField.setPromptText("Введите начальную станцию");
            fromField.setPrefWidth(170);
            Button fromClearButton = new Button("Стереть");

            Button toButton = new Button("Куда");
            toField = new TextField();
            toField.setPromptText("Введите конечную станцию");
            toField.setPrefWidth(170);
            Button toClearButton = new Button("Стереть");

            Button calculateButton = new Button("Рассчитать");

            // Размещение кнопок и полей ввода с кнопками очистки
            HBox fromBox = new HBox(10, fromButton, fromField, fromClearButton);
            HBox toBox = new HBox(10, toButton, toField, toClearButton);
            HBox calculateBox = new HBox(10, calculateButton);

            VBox buttonBox = new VBox(15, fromBox, toBox, calculateBox);
            buttonBox.setStyle("-fx-padding: 15;");

            // Основная панель
            BorderPane root = new BorderPane();
            root.setLeft(buttonBox);

            // Загрузка изображения карты метро
            logger.debug("Загрузка изображения карты метро...");
            // Загрузка изображения из ресурсов
            Image metroImage = new Image(getClass().getResource("/karta_metro.png").toExternalForm());

            ImageView imageView = new ImageView(metroImage);
            imageView.setPreserveRatio(true); // Сохранять пропорции изображения

            // Создание контейнера для наложения точек на карту
            mapPane = new Pane(imageView);
            imageView.fitWidthProperty().bind(mapPane.widthProperty());
            imageView.fitHeightProperty().bind(mapPane.heightProperty());

            // Добавляем слушателей для обновления станций при изменении размеров
            mapPane.widthProperty().addListener((obs, oldVal, newVal) -> updateStationsOnResize(metroDataInitializer, stationNames, imageView));
            mapPane.heightProperty().addListener((obs, oldVal, newVal) -> updateStationsOnResize(metroDataInitializer, stationNames, imageView));

            // Добавление станций на карту
            addStationsToMap(metroDataInitializer, stationNames, imageView);

            // Обертываем карту с точками в ScrollPane
            ScrollPane scrollPane = new ScrollPane(mapPane);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true); // Масштабировать карту до размеров окна

            root.setCenter(scrollPane);

            // Обработка нажатий кнопок
            fromButton.setOnAction(e -> {
                logger.info("Кнопка 'Откуда' нажата.");
                fromField.requestFocus();
            });
            toButton.setOnAction(e -> {
                logger.info("Кнопка 'Куда' нажата.");
                toField.requestFocus();
            });
            calculateButton.setOnAction(e -> processRoute(fromField, toField, stationNames, adjacencyMatrix));
            fromClearButton.setOnAction(e -> {
                logger.info("Кнопка 'Стереть' нажата для поля 'Откуда'.");
                fromField.clear();
            });
            toClearButton.setOnAction(e -> {
                logger.info("Кнопка 'Стереть' нажата для поля 'Куда'.");
                toField.clear();
            });

            // Настройка сцены
            Scene scene = new Scene(root, 1100, 900);
            primaryStage.setTitle("Metro Navigator");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        /**
         * Добавляет станции на карту метро, представляя их как круги с подписями.
         * @param metroDataInitializer Инициализатор данных метро.
         * @param stationNames Карта с именами станций.
         * @param imageView Представление изображения карты метро.
         */
        private void addStationsToMap(MetroDataInitializer metroDataInitializer, Map<Integer, String> stationNames, ImageView imageView) {
            Map<Integer, Point2D> stationCoordinates = metroDataInitializer.initializeStationCoordinates();
            double scaleX = imageView.getBoundsInParent().getWidth() / imageView.getImage().getWidth();
            double scaleY = imageView.getBoundsInParent().getHeight() / imageView.getImage().getHeight();

            stationCoordinates.forEach((stationId, coords) -> {
                String stationName = stationNames.get(stationId);

                double x = coords.getX() * scaleX;
                double y = coords.getY() * scaleY;

                Circle stationCircle = new Circle(x, y, 5);
                stationCircle.setStyle("-fx-fill: rgba(0,0,0,0); -fx-stroke: rgba(0,0,0,0); -fx-stroke-width: 1;");
                stationCircle.setOnMouseClicked(e -> {
                    logger.debug("Станция {} была выбрана.", stationName);
                    if (fromField.getText().isEmpty()) {
                        fromField.setText(stationName);
                    } else {
                        toField.setText(stationName);
                    }
                });

                Tooltip tooltip = new Tooltip(stationName);
                Tooltip.install(stationCircle, tooltip);

                mapPane.getChildren().add(stationCircle);
            });
        }

        /**
         * Обновляет расположение станций при изменении размера окна.
         * @param metroDataInitializer Инициализатор данных метро.
         * @param stationNames Карта с именами станций.
         * @param imageView Представление изображения карты метро.
         */
        private void updateStationsOnResize(MetroDataInitializer metroDataInitializer, Map<Integer, String> stationNames, ImageView imageView) {
            logger.info("Изменение размера карты, обновление станций.");
            mapPane.getChildren().clear();
            mapPane.getChildren().add(imageView); // Сначала добавить картинку

            addStationsToMap(metroDataInitializer, stationNames, imageView);
        }

        /**
         * Обрабатывает расчет маршрута и выводит результат.
         * @param fromField Поле ввода начальной станции.
         * @param toField Поле ввода конечной станции.
         * @param stationNames Карта с именами станций.
         * @param adjacencyMatrix Матрица смежности для метрополитена.
         */
        private void processRoute(TextField fromField, TextField toField, Map<Integer, String> stationNames, double[][] adjacencyMatrix) {
            try {
                String startStation = fromField.getText();
                String endStation = toField.getText();

                if (startStation.isEmpty() || endStation.isEmpty()) {
                    logger.warn("Один из полей пуст: startStation = {}, endStation = {}", startStation, endStation);
                    showResultDialog("Ошибка: Пожалуйста, заполните оба поля.");
                    return;
                }

                int start = getStationIndexByName(stationNames, startStation);
                int end = getStationIndexByName(stationNames, endStation);

                DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(adjacencyMatrix);
                List<Integer> path = dijkstra.findShortestPath(start, end);

                if (path.isEmpty()) {
                    logger.error("Путь не найден для маршрута: {} -> {}", startStation, endStation);
                    showResultDialog("Ошибка: Путь не найден.");
                    return;
                }

                StringBuilder pathString = new StringBuilder("Путь: ");
                double totalTime = 0;
                for (int i = 0; i < path.size(); i++) {
                    int station = path.get(i);
                    pathString.append(stationNames.get(station));
                    if (i < path.size() - 1) {
                        pathString.append(" -> ");
                        totalTime += adjacencyMatrix[station][path.get(i + 1)];
                    }
                }

                String formattedTime = String.format("%.1f", totalTime);
                pathString.append("\nВремя в пути: ").append(formattedTime).append(" минут.");
                showResultDialog(pathString.toString());
            } catch (Exception e) {
                logger.error("Ошибка при обработке маршрута.", e);
                showResultDialog("Ошибка при расчете маршрута.");
            }
        }

        /**
         * Получает индекс станции по ее имени.
         * @param stationNames Карта с именами станций.
         * @param stationName Имя станции.
         * @return Индекс станции.
         */
        private int getStationIndexByName(Map<Integer, String> stationNames, String stationName) {
            for (Map.Entry<Integer, String> entry : stationNames.entrySet()) {
                if (entry.getValue().equals(stationName)) {
                    return entry.getKey();
                }
            }
            return -1; // Станция не найдена
        }

        /**
         * Отображает диалог с результатами расчета маршрута.
         * Используется для вывода информации о найденном маршруте, его длительности и промежуточных станциях.
         *
         * @param message Сообщение для отображения в диалоге. Ожидается форматированная строка с маршрутом.
         */
        private void showResultDialog(String message) {
            logger.info("Отображение диалога с результатами маршрута.");


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат расчета маршрута");
            alert.setHeaderText("Информация о маршруте");


            TextArea textArea = new TextArea(message);
            textArea.setEditable(false);
            textArea.setWrapText(true);


            textArea.setPrefWidth(800);
            textArea.setPrefHeight(200);
            alert.getDialogPane().setContent(textArea);
            logger.debug("Сообщение для отображения в диалоге: {}", message);
            alert.showAndWait();
            logger.info("Диалог с результатами маршрута закрыт.");
        }

        /**
         * Точка входа для запуска приложения.
         * @param args Аргументы командной строки.
         */
        public static void main(String[] args) {
            launch(args);
        }
    }