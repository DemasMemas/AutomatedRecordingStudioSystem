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
