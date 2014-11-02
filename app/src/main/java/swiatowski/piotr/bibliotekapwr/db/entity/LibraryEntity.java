package swiatowski.piotr.bibliotekapwr.db.entity;

/**
 * Created by Piotrek on 2014-11-02.
 */
public class LibraryEntity {

    private float mLatitiude;
    private float mLongitiude;

    private String mName;
    private String mShortName;
    private String mBuilding;
    private String mStartTime;
    private String mCloseTime;

    public LibraryEntity(float lat, float lon, String name, String shortName, String building, String startTime, String closeTime) {
        mLatitiude = lat;
        mLongitiude = lon;
        mName = name;
        mShortName = shortName;
        mBuilding= building;
        mStartTime = startTime;
        mCloseTime = closeTime;
    }

    public float getLatitiude() {
        return mLatitiude;
    }

    public float getLongitiure() {
        return mLongitiude;
    }

    public String getName() {
        return mName;
    }

    public String getShortName() {
        return mShortName;
    }

    public String getBuilding() {
        return mBuilding;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getCloseTime() {
        return mCloseTime;
    }

}
