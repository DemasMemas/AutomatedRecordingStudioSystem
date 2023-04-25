package zh.arss.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import zh.arss.service.EditRequestService;

public class EditRequestController extends Controller {

    @FXML
    private Button acceptButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField serviceTextField;

    private final EditRequestService service = EditRequestService.getInstance();

    @FXML
    void initialize() {
        idTextField.setText(String.valueOf(service.getRequest().getIdRequest()));
        userIdTextField.setText(String.valueOf(service.getRequest().getIdUser()));
        phoneTextField.setText(service.getRequest().getPhone());
        emailTextField.setText(service.getRequest().getEmail());
        codeTextField.setText(service.getRequest().getCode());
        dateTextField.setText(service.getRequest().getDate());
        descriptionTextField.setText(service.getRequest().getDescription());
        serviceTextField.setText(service.getRequest().getService());

        acceptButton.setOnMouseClicked(mouseEvent -> {
                    service.stickRequestInYouAss(
                            idTextField.getText(), userIdTextField.getText(), phoneTextField.getText(),
                            emailTextField.getText(), codeTextField.getText(), dateTextField.getText(),
                            serviceTextField.getText(), descriptionTextField.getText());
                    openNewWindow("musicStudioAdmin");
                }
        );
        deleteButton.setOnMouseClicked(mouseEvent -> {
            service.deleteRequest();
            openNewWindow("musicStudioAdmin");
        });
        exitButton.setOnMouseClicked(mouseEvent -> openNewWindow("musicStudioAdmin"));
    }
}
