package zh.arss.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import zh.arss.service.EditPurchaseService;

public class EditPurchaseController extends Controller {

    @FXML
    private Button acceptButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField idUserTextField;

    @FXML
    private TextField idArrangementTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Button deleteButton;

    private final EditPurchaseService service = EditPurchaseService.getInstance();

    @FXML
    void initialize() {
        idTextField.setText(String.valueOf(service.getPurchase().getId()));
        idUserTextField.setText(String.valueOf(service.getPurchase().getIdUser()));
        idArrangementTextField.setText(String.valueOf(service.getPurchase().getIdArrangement()));

        acceptButton.setOnMouseClicked(mouseEvent -> {
            service.stickPurchase(
                    idTextField.getText(), idUserTextField.getText(), idArrangementTextField.getText()
            );
            openNewWindow("musicStudioAdmin");
        });
        deleteButton.setOnMouseClicked(mouseEvent -> {
            service.deletePurchase();
            openNewWindow("musicStudioAdmin");
        });
        exitButton.setOnMouseClicked(mouseEvent -> openNewWindow("musicStudioAdmin"));
    }
}
