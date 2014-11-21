package swiatowski.piotr.bibliotekapwr.model;

import java.io.Serializable;

/**
 * Created by Piotrek on 2014-11-02.
 */
public class Rent implements Serializable {

    private String mSignature;
    private String mStatus;
    private String mData;
    private String mHref;

    public Rent(String signature, String status, String data, String href) {
        mSignature = signature;
        mStatus = status;
        mData = data;
        mHref = href;
    }

    public String getSignature() {
        return mSignature;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getData() {
        return mData;
    }

    public String getHref() {
        return mHref;
    }
}
