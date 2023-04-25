package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.entity.Request;

public class EditRequestService {
    private static final EditRequestService EDIT_REQUEST_SERVICE = new EditRequestService();

    private EditRequestService() {
    }

    public static EditRequestService getInstance() {
        return EDIT_REQUEST_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void stickRequestInYouAss(String id, String userId, String phone, String email,
                                     String code, String date, String serviceRequest, String description) {
        if (Long.parseLong(id) == request.getIdRequest()) {
            databaseHandler.updateRequest(Long.parseLong(id), Long.parseLong(userId), date, serviceRequest, code, email, phone, description);
        } else {
            try {
                databaseHandler.insertRequest(Long.parseLong(userId), date, serviceRequest, code, email, phone, description);
            } catch (Exception ignored) {}
        }
    }

    public void deleteRequest() {
        databaseHandler.deleteRequest(request.getIdRequest());
    }
}
