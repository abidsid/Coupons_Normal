package piyush.grocery.com.coupons;

public class CouponData {
    String title,description,imageURL,redemptionEndDate,isClipped;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRedemptionEndDate() {
        return redemptionEndDate;
    }

    public void setRedemptionEndDate(String redemptionEndDate) {
        this.redemptionEndDate = redemptionEndDate;
    }

    public String getIsClipped() {
        return isClipped;
    }

    public void setIsClipped(String isClipped) {
        this.isClipped = isClipped;
    }

    public CouponData(String s, String s1, String s2, String s3, String s4) {

        this.title=s;
        this.description=s1;
        this.imageURL=s2;
        this.redemptionEndDate=s3;
        this.isClipped=s4;

    }
}
