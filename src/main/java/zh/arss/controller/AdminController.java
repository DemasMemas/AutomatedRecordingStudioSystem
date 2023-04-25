package zh.arss.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import zh.arss.entity.Arrangement;
import zh.arss.entity.Purchase;
import zh.arss.entity.Request;
import zh.arss.entity.User;
import zh.arss.service.AdminService;

public class AdminController extends Controller {
    @FXML
    private TableColumn<Purchase, String> arrangementPurchaseTableColumn;

    @FXML
    private TableColumn<Request, String> codeRequestTableColumn;

    @FXML
    private TableColumn<Request, String> dateRequestTableColumn;

    @FXML
    private TableColumn<Request, String> descriptionRequestTableColumn;

    @FXML
    private TableColumn<Request, String> emailRequestTableColumn;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Arrangement, String> idArrangementTableColumn;

    @FXML
    private TableColumn<Purchase, String> idPurchaseTableColumn;

    @FXML
    private TableColumn<Request, String> idRequestTableColumn;

    @FXML
    private TableColumn<User, String> idUserTableColumn;

    @FXML
    private TableColumn<User, String> loginUserTableColumn;

    @FXML
    private TableColumn<Arrangement, String> nameArrangementTableColumn;

    @FXML
    private TableColumn<User, String> passwordUserTableColumn;

    @FXML
    private TableColumn<Request, String> phoneRequestTableColumn;

    @FXML
    private TableColumn<Request, String> serviceRequestTableColumn;

    @FXML
    private TableColumn<User, String> roleUserTableColumn;

    @FXML
    private TableView<Request> requestsTableView;

    @FXML
    private TableView<Arrangement> arrangementTableView;

    @FXML
    private TableView<Purchase> purchaseTableView;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<Arrangement, String> statusArrangementTableColumn;

    @FXML
    private TableColumn<Purchase, String> userPurchaseTableColumn;

    @FXML
    private TableColumn<Request, String> userRequestTableColumn;

    private final AdminService service = AdminService.getInstance();

    @FXML
    void initialize(){
        idArrangementTableColumn.setCellValueFactory(new PropertyValueFactory<>("idArrangement"));
        nameArrangementTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusArrangementTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        idPurchaseTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userPurchaseTableColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        arrangementPurchaseTableColumn.setCellValueFactory(new PropertyValueFactory<>("idArrangement"));

        idUserTableColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        loginUserTableColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordUserTableColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleUserTableColumn.setCellValueFactory(new PropertyValueFactory<>("isAdministrator"));

        idRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("idRequest"));
        userRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        dateRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        serviceRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        codeRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        emailRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        descriptionRequestTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        requestsTableView.setItems(service.getRequests());
        arrangementTableView.setItems(service.getArrangements());
        userTableView.setItems(service.getUsers());
        purchaseTableView.setItems(service.getPurchases());

        requestsTableView.setOnMouseClicked(mouseEvent -> {
            Request request = requestsTableView.getSelectionModel().getSelectedItem();
            if(request != null){
                service.injectRequest(request);
                openNewWindow("editRequest");
            }
        });
        arrangementTableView.setOnMouseClicked(mouseEvent -> {
            Arrangement arrangement = arrangementTableView.getSelectionModel().getSelectedItem();
            if(arrangement != null){
                service.injectArrangement(arrangement);
                openNewWindow("editArrangement");
            }
        });
        userTableView.setOnMouseClicked(mouseEvent -> {
            User user = userTableView.getSelectionModel().getSelectedItem();
            if(user != null){
                service.injectUser(user);
                openNewWindow("editUser");
            }
        });
        purchaseTableView.setOnMouseClicked(mouseEvent -> {
            Purchase purchase = purchaseTableView.getSelectionModel().getSelectedItem();
            if(purchase != null){
                service.injectPurchase(purchase);
                openNewWindow("editPurchase");
            }
        });

        exitButton.setOnMouseClicked(mouseEvent -> openOtherWindow("musicStudioMain", exitButton));
    }
}
