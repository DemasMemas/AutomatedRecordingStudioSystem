package zh.arss.dto;

public class PurchaseForTable {
    private Long id;
    private String user;
    private String arrangement;

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
