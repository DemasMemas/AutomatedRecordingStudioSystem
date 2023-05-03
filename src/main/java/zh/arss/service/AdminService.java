package zh.arss.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zh.arss.database.DatabaseHandler;
import zh.arss.dto.PurchaseForTable;
import zh.arss.entity.Arrangement;
import zh.arss.entity.Purchase;
import zh.arss.entity.Request;
import zh.arss.entity.User;

import java.util.List;
import java.util.Objects;

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
        ObservableList<Arrangement> arrangementObservableList = FXCollections.observableArrayList();
        arrangementObservableList.addAll(databaseHandler.getAllArrangement());
        return arrangementObservableList;
    }

    public ObservableList<User> getUsers() {
        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        userObservableList.addAll(databaseHandler.getAllUser());
        return userObservableList;
    }

    public ObservableList<PurchaseForTable> getPurchases() {
        ObservableList<PurchaseForTable> purchaseObservableList = FXCollections.observableArrayList();
        List<Purchase> purchases = databaseHandler.getAllPurchase();
        for (Purchase purchase : purchases) {
            PurchaseForTable purchaseForTable = new PurchaseForTable();
            purchaseForTable.setId(purchase.getId());
            purchaseForTable.setUser(databaseHandler.getUser(purchase.getIdUser()).getLogin());
            purchaseForTable.setArrangement(databaseHandler.getArrangement(purchase.getIdArrangement()).getName());

            purchaseObservableList.add(purchaseForTable);
        }
        return purchaseObservableList;
    }

    public void injectRequest(Request request) {
        editRequestService.setRequest(request);
    }
    public void injectUser(User user) {
        editUserService.setUser(user);
    }
    public void injectPurchase(PurchaseForTable purchaseForTable) {
        Purchase purchase = databaseHandler.getAllPurchase().stream().filter(p -> Objects.equals(p.getId(), purchaseForTable.getId())).findFirst().get();
        editPurchaseService.setPurchase(purchase);
    }
    public void injectArrangement(Arrangement arrangement) {
        editArrangementService.setArrangement(arrangement);
    }
}
