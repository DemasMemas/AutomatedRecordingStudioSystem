package zh.arss.entity;

public class Arrangement {
    private long idArrangement;
    private String name;
    private String style;
    private String license;

    public long getIdArrangement() {
        return idArrangement;
    }

    public void setIdArrangement(long idArrangement) {
        this.idArrangement = idArrangement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
