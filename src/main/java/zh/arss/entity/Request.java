package zh.arss.entity;

public class Request {
    private long idRequest;
    private long idUser;
    private String date;
    private String service;
    private String code;

    public long getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(long idRequest) {
        this.idRequest = idRequest;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
