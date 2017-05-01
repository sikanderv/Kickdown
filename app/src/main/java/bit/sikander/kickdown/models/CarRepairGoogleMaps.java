package bit.sikander.kickdown.models;


public class CarRepairGoogleMaps {

    private String vicinity = "";
    private String icon = "";
    private String id = "";
    private String name = "";
    private String rating = "";
    private String reference = "";
    private String types = "";
    private String reviews = "";
    private String photoReference = "";
    private String dLat = "";
    private String dLan = "";
    private String thumUrl = "";
    private String openOrClose = "";

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getdLat() {
        return dLat;
    }

    public void setdLat(String dLat) {
        this.dLat = dLat;
    }

    public String getdLan() {
        return dLan;
    }

    public void setdLan(String dLan) {
        this.dLan = dLan;
    }

    public String getThumUrl() {
        return thumUrl;
    }

    public void setThumUrl(String thumUrl) {
        this.thumUrl = thumUrl;
    }

    public String getOpenOrClose() {
        return openOrClose;
    }

    public void setOpenOrClose(String openOrClose) {
        this.openOrClose = openOrClose;
    }

    @Override
    public String toString() {
        return "CarRepairGoogleMaps{" +
                "vicinity='" + vicinity + '\'' +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rating='" + rating + '\'' +
                ", reference='" + reference + '\'' +
                ", types='" + types + '\'' +
                ", reviews='" + reviews + '\'' +
                ", photoReference='" + photoReference + '\'' +
                ", dLat='" + dLat + '\'' +
                ", dLan='" + dLan + '\'' +
                ", thumUrl='" + thumUrl + '\'' +
                ", openOrClose='" + openOrClose + '\'' +
                '}';
    }
}