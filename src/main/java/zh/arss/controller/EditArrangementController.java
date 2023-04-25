package zh.arss.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import zh.arss.service.EditArrangementService;

public class EditArrangementController extends Controller {

    @FXML
    private Button acceptButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField statusTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Button deleteButton;

    private final EditArrangementService service = EditArrangementService.getInstance();

    @FXML
    void initialize() {
        idTextField.setText(String.valueOf(service.getArrangement().getIdArrangement()));
        nameTextField.setText(service.getArrangement().getName());
        statusTextField.setText(service.getArrangement().getStatus());

        acceptButton.setOnMouseClicked(mouseEvent -> {
            service.stickArrangement(
                    idTextField.getText(), nameTextField.getText(), statusTextField.getText()
            );
            openNewWindow("musicStudioAdmin");
        });
        deleteButton.setOnMouseClicked(mouseEvent -> {
            service.deleteArrangement();
            openNewWindow("musicStudioAdmin");
        });
        exitButton.setOnMouseClicked(mouseEvent -> openNewWindow("musicStudioAdmin"));
    }
}
