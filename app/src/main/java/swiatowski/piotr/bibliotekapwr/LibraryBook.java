package swiatowski.piotr.bibliotekapwr;

import java.io.Serializable;

/**
 * Created by Piotrek on 2014-11-01.
 */
public class LibraryBook implements Serializable{

    private String mName;
    private String mInfoBookHref;
    private int mAllBook;
    private int mRentBook;

    public LibraryBook(String name, String infoBookHref) {
        mName = name;
        mInfoBookHref = infoBookHref;
    }

    public String getName() {
        return mName;
    }

    public String getInfoBookHref() {
        return mInfoBookHref;
    }

    public int getAllBook() {
        return mAllBook;
    }

    public int getRentBook() {
        return mRentBook;
    }
}
