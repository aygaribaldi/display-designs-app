public class Design {

    private final String id;
    private final String url;
    private final String description;

    public Design(String url, String description) {
        this(null, url, description);
    }

    public Design(String id, String url, String description) {
        this.id = id;
        this.url = url;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    // public void setId(String id) {
    // this.id = id;
    // }

    public String getUrl() {
        return url;
    }

    // public void setUrl(String url) {
    // this.url = url;
    // }

    public String getDescription() {
        return description;
    }

    // public void setDescription(String description) {
    // this.description = description;
    // }
}
