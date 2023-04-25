package zh.arss.entity;

public class Purchase {
    private Long id;
    private Long idUser;
    private Long idArrangement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdArrangement() {
        return idArrangement;
    }

    public void setIdArrangement(Long idArrangement) {
        this.idArrangement = idArrangement;
    }
}
