package zh.arss.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import zh.arss.MusicRecordStudio;
import zh.arss.service.MainService;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final MainService service = MainService.getInstance();

    @FXML
    private Button authorizeButton;

    @FXML
    private Button registerButton;

    @FXML
    private GridPane timeTableGPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showRegistrationBonusAlert();

        authorizeButton.setOnAction(event -> {
            // открыть окно с регистрацией или авторизацией
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Идёт процесс авторизации...");
            dialog.setHeaderText("Пожалуйста, введите свои данные для авторизации");
            ImageView icon = new ImageView(String.valueOf(MusicRecordStudio.class.getResource("image/icon.png")));
            icon.setFitHeight(32);
            icon.setFitWidth(32);
            dialog.setGraphic(icon);
            dialog.initOwner(authorizeButton.getScene().getWindow());

            // Установка типов кнопок
            ButtonType loginButtonType = new ButtonType("Войти", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField username = new TextField();
            username.setPromptText("Введите имя");
            PasswordField password = new PasswordField();
            password.setPromptText("Введите пароль");

            grid.add(new Label("Имя:"), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Пароль:"), 0, 1);
            grid.add(password, 1, 1);

            dialog.getDialogPane().setContent(grid);
            Platform.runLater(username::requestFocus);

            // Возвращение значений
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            if (result.isPresent()) {
                String response = service.authorization(result.get().getKey(), result.get().getValue());
                if (response.equals("success")) {
                    showCustomAlert("Успешная авторизация");
                } else {
                    showCustomErrorAlert(response);
                }
            }
            // result - значения. result.get().getKey() - первое, result.get().getValue() - второе
            // соответсвенно успешность регистрации и прочее проверяешь сам, считывая эти приколы
        });

        registerButton.setOnAction(event -> {
            // открыть окно с регистрацией или авторизацией
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Идёт процесс регистрации...");
            dialog.setHeaderText("Пожалуйста, введите свои данные для регистрации");
            ImageView icon = new ImageView(String.valueOf(MusicRecordStudio.class.getResource("image/icon.png")));
            icon.setFitHeight(32);
            icon.setFitWidth(32);
            dialog.setGraphic(icon);
            dialog.initOwner(authorizeButton.getScene().getWindow());

            // Установка типов кнопок
            ButtonType registerButtonType = new ButtonType("Зарегистрироваться", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, cancelButtonType);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField username = new TextField();
            username.setPromptText("Введите имя");
            PasswordField password = new PasswordField();
            password.setPromptText("Введите пароль");

            grid.add(new Label("Имя:"), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Пароль:"), 0, 1);
            grid.add(password, 1, 1);

            dialog.getDialogPane().setContent(grid);
            Platform.runLater(username::requestFocus);

            // Возвращение значений
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == registerButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            if (result.isPresent()) {
                String response = service.registration(result.get().getKey(), result.get().getValue());
                if (response.equals("success")) {
                    showCustomAlert("Успешная регистрация");
                } else {
                    showCustomErrorAlert(response);
                }
            }
        });

        timeTableGPane.setBackground(new Background(new BackgroundImage(new Image(String.valueOf(MusicRecordStudio.class
                .getResource("image/background.jpg"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(null, 118, true, null, 14, true),
                new BackgroundSize(735, 540, true, true, true, true))));
        timeTableGPane.add(new Label("Понедельник"), 1, 0);
        timeTableGPane.add(new Label("Вторник"), 2, 0);
        timeTableGPane.add(new Label("Среда"), 3, 0);
        timeTableGPane.add(new Label("Четверг"), 4, 0);
        timeTableGPane.add(new Label("Пятница"), 5, 0);
        timeTableGPane.add(new Label("Суббота"), 6, 0);
        timeTableGPane.add(new Label("Воскресенье"), 7, 0);

        timeTableGPane.add(new Label("8:00"), 0, 1);
        timeTableGPane.add(new Label("9:00"), 0, 2);
        timeTableGPane.add(new Label("10:00"), 0, 3);
        timeTableGPane.add(new Label("11:00"), 0, 4);
        timeTableGPane.add(new Label("12:00"), 0, 5);
        timeTableGPane.add(new Label("13:00"), 0, 6);
        timeTableGPane.add(new Label("14:00"), 0, 7);
        timeTableGPane.add(new Label("15:00"), 0, 8);
        timeTableGPane.add(new Label("16:00"), 0, 9);
        timeTableGPane.add(new Label("17:00"), 0, 10);
        timeTableGPane.add(new Label("18:00"), 0, 11);
        timeTableGPane.add(new Label("19:00"), 0, 12);
        timeTableGPane.add(new Label("20:00"), 0, 13);
        timeTableGPane.add(new Label("21:00"), 0, 14);
        timeTableGPane.add(new Label("22:00"), 0, 15);
        timeTableGPane.add(new Label("23:00"), 0, 16);
        timeTableGPane.add(new Label("Ночь"), 0, 17);

        // вот тут вставлять приколы с другой надписью
        for (int i = 1; i < 8; i++){
            for (int j = 1; j < 18; j++){
                Label tempLabel = new Label("СВОБОДНО");
                tempLabel.setStyle( "-fx-text-fill: darkgreen;");
                // тут считывать нажатия
                int finalI = i;
                int finalJ = j;
                tempLabel.setOnMouseClicked(mouseEvent -> {
                    System.out.println("HELP I`M CLICKED " + finalI + " " + finalJ);
                });
                timeTableGPane.add(tempLabel, i, j);
            }
        }

    }

    // показ сообщения о скидке при регистрации
    public void showRegistrationBonusAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Бонус при регистрации");
        alert.setHeaderText("При регистрации вы получите скидку 20% на первое сведение трека");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image(String.valueOf(MusicRecordStudio.class.getResource("image/icon.png"))));
        alert.getDialogPane().setBackground(new Background(new BackgroundImage(
                new Image(String.valueOf(MusicRecordStudio.class.getResource("image/background.jpg"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(null, 0, true, null, 0, true),
                new BackgroundSize(400, 100, true, true, true, true))));
        alert.showAndWait();
    }

    // диалоговое окно с кнопкой закрыть, оповещающее о том, что передано. Вызывай откуда надо
    public void showCustomAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        showAlert(message, alert);
    }

    // то же самое, но оповещает об ошибке, а не об успехе или чем-то ещё
    public void showCustomErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        showAlert(message, alert);
    }

    private void showAlert(String message, Alert alert) {
        alert.setTitle("Внимание");
        alert.setHeaderText(message);
        alert.getDialogPane().setBackground(new Background(new BackgroundImage(
                new Image(String.valueOf(MusicRecordStudio.class.getResource("image/background.jpg"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(null, 0, true, null, 0, true),
                new BackgroundSize(400, 100, true, true, true, true))));
        alert.showAndWait();
    }
}
