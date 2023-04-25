package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.entity.Purchase;

public class EditPurchaseService {
    private static final EditPurchaseService EDIT_PURCHASE_SERVICE = new EditPurchaseService();

    private EditPurchaseService() {
    }

    public static EditPurchaseService getInstance() {
        return EDIT_PURCHASE_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private Purchase purchase;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void stickPurchase(String id, String idUser, String idArrangement) {
        if (Long.parseLong(id) == purchase.getId()) {
            databaseHandler.updatePurchase(Long.parseLong(id), Long.parseLong(idUser), Long.parseLong(idArrangement));
        } else {
            databaseHandler.insertPurchase(Long.parseLong(idUser), Long.parseLong(idArrangement));
        }
    }

    public void deletePurchase() {
        databaseHandler.deletePurchase(purchase.getId());
    }
}
