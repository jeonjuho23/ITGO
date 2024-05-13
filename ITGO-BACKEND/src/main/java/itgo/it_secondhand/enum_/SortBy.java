package itgo.it_secondhand.enum_;

public enum SortBy {
    RECENT_POST("postTime"),
    RECENT_SEARCH("searchDate"),
    COUNT_DEVICE("viewCount"),
    PRICE("secondhandPrice"),
    VIEW("postViewCount"),
    RECENT_NOTIFICATION("time");

    private final String label;

    SortBy(String label) {
        this.label = label;
    }

    public String label(){
        return label;
    }
}
