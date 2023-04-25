package zh.arss.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import zh.arss.service.EditUserService;

public class EditUserController extends Controller {

    @FXML
    private Button acceptButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField roleTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Button deleteButton;

    private final EditUserService service = EditUserService.getInstance();

    @FXML
    void initialize() {
        idTextField.setText(String.valueOf(service.getUser().getIdUser()));
        loginTextField.setText(service.getUser().getLogin());
        passwordTextField.setText(service.getUser().getPassword());
        roleTextField.setText(String.valueOf(service.getUser().getIsAdministrator()));

        acceptButton.setOnMouseClicked(mouseEvent -> {
            service.stickUser(
                    idTextField.getText(), loginTextField.getText(),
                    passwordTextField.getText(), roleTextField.getText()
            );
            openNewWindow("musicStudioAdmin");
        });
        deleteButton.setOnMouseClicked(mouseEvent -> {
            service.deleteUser();
            openNewWindow("musicStudioAdmin");
        });
        exitButton.setOnMouseClicked(mouseEvent -> openNewWindow("musicStudioAdmin"));
    }
}
