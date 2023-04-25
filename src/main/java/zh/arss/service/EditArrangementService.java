package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.entity.Arrangement;

public class EditArrangementService {
    private static final EditArrangementService EDIT_ARRANGEMENT_SERVICE = new EditArrangementService();

    private EditArrangementService() {
    }

    public static EditArrangementService getInstance() {
        return EDIT_ARRANGEMENT_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private Arrangement arrangement;

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void stickArrangement(String id, String name, String status) {
        if (Long.parseLong(id) == arrangement.getIdArrangement()) {
            databaseHandler.updateArrangement(Long.parseLong(id), name, status);
        } else {
            databaseHandler.insertArrangement(name, status);
        }
    }

    public void deleteArrangement() {
        databaseHandler.deleteArrangement(arrangement.getIdArrangement());
    }
}
