package zh.arss.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zh.arss.database.DatabaseHandler;
import zh.arss.entity.Arrangement;
import zh.arss.entity.Purchase;
import zh.arss.entity.Request;
import zh.arss.entity.User;

public class AdminService {
    private static final AdminService ADMIN_SERVICE = new AdminService();

    private AdminService() {
    }

    public static AdminService getInstance() {
        return ADMIN_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private final EditUserService editUserService = EditUserService.getInstance();
    private final EditArrangementService editArrangementService = EditArrangementService.getInstance();
    private final EditPurchaseService editPurchaseService = EditPurchaseService.getInstance();
    private final EditRequestService editRequestService = EditRequestService.getInstance();


    public ObservableList<Request> getRequests() {
        ObservableList<Request> requestObservableList = FXCollections.observableArrayList();
        requestObservableList.addAll(databaseHandler.getAllRequest());
        return requestObservableList;
    }

    public ObservableList<Arrangement> getArrangements() {
        ObservableList<Arrangement> requestObservableList = FXCollections.observableArrayList();
        requestObservableList.addAll(databaseHandler.getAllArrangement());
        return requestObservableList;
    }

    public ObservableList<User> getUsers() {
        ObservableList<User> requestObservableList = FXCollections.observableArrayList();
        requestObservableList.addAll(databaseHandler.getAllUser());
        return requestObservableList;
    }

    public ObservableList<Purchase> getPurchases() {
        ObservableList<Purchase> requestObservableList = FXCollections.observableArrayList();
        requestObservableList.addAll(databaseHandler.getAllPurchase());
        return requestObservableList;
    }

    public void injectRequest(Request request) {
        editRequestService.setRequest(request);
    }
    public void injectUser(User user) {
        editUserService.setUser(user);
    }
    public void injectPurchase(Purchase purchase) {
        editPurchaseService.setPurchase(purchase);
    }
    public void injectArrangement(Arrangement arrangement) {
        editArrangementService.setArrangement(arrangement);
    }
}
