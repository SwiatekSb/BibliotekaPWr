package swiatowski.piotr.bibliotekapwr.db.entity;

/**
 * Created by Piotrek on 2014-11-11.
 */
public class NotificationEntity {

    private String mSignature;
    private String mHref;
    private String mRentUrl;
    private int mId;
    private String mTitle;

    public NotificationEntity(String signature, String href, String rentUrl, String title) {
        mSignature = signature;
        mHref = href;
        mRentUrl = rentUrl;
        mTitle = title;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getId() {
        return mId;
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
