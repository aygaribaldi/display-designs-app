public class Design {

    private final String id;
    private String url;
    private String description;
    private final String userId;

    public Design(String url, String description, String userId) {
        this(null, url, description, userId);
    }

    public Design(String id, String url, String description, String userId) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }
}
