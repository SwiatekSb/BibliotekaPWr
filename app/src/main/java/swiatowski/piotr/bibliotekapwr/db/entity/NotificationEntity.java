package swiatowski.piotr.bibliotekapwr.db.entity;

/**
 * Created by Piotrek on 2014-11-11.
 *
 */
public class NotificationEntity {

    private String mSignature;
    private String mHref;
    private String mRentUrl;
    private String mTitle;
    private int mId;
    private int mDay;

    public NotificationEntity(String signature, String href, String rentUrl, String title) {
        mSignature = signature;
        mHref = href;
        mRentUrl = rentUrl;
        mTitle = title;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public int getDay() {
        return mDay;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSignature(String signature) {
        mSignature = signature;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public void setRentUrl(String rentUrl) {
        mRentUrl = rentUrl;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSignature() {
        return mSignature;
    }

    public String getHref() {
        return mHref;
    }

    public String getRentUrl() {
        return mRentUrl;
    }
}
