package swiatowski.piotr.bibliotekapwr.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import swiatowski.piotr.bibliotekapwr.model.LibraryBook;

/**
 * Created by Piotrek on 2014-11-01.
 */
public class BookEntity implements Serializable {

    private String mInfoHref;
    private String mAuthor;
    private String mTitle;
    private String mYear;
    private List<LibraryBook> mLibraryList;

    private String mDocumentType;
    private String mInstituteName;
    private String mFullTitle;
    private String mFullYear;
    private String mISBN;


    private boolean mHasInfoData;

    public BookEntity(String documentType, String instituteName, String fullTitle, String fullYear, String isbn) {
        mDocumentType = documentType;
        mInstituteName = instituteName;
        mFullTitle = fullTitle;
        mFullYear = fullYear;
        mISBN = isbn;
        mHasInfoData = true;
    }

    public BookEntity(String infoHref, String author, String title, String year, List<LibraryBook> libraryList) {
        mInfoHref = infoHref;
        mAuthor = author;
        mTitle = title;
        mYear = year;
        mLibraryList = libraryList;
        mHasInfoData = false;
    }

    public LibraryBook getLibraryByName(String name) {

        for (LibraryBook lib : mLibraryList) {
            if (name.equals(lib.getName())) {
                return lib;
            }
        }
        return null;
    }


    public boolean getInfoData() {
        return mHasInfoData;
    }



    public void setDocumentType(String documentType) {
        mDocumentType = documentType;
    }

    public void setInstituteName(String instituteName) {
        mInstituteName = instituteName;
    }

    public void setFullTitle(String fullTitle) {
        mFullTitle = fullTitle;
    }

    public void setFullYear(String fullYear) {
        mFullYear = fullYear;
    }

    public void setISBN(String isbn) {
        mISBN = isbn;
    }

    public String getDocumentType() {
        return mDocumentType;
    }

    public String getInstituteName() {
        return mInstituteName;
    }

    public String getFullTitle() {
        return mFullTitle;
    }

    public String getFullYear() {
        return mFullYear;
    }

    public String getISBN() {
        return mISBN;
    }

    public String getInfoHref() {
        return mInfoHref;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getYear() {
        return mYear;
    }

    public List<LibraryBook> getLibraryList() {
        return mLibraryList;
    }

    public String[] getListOfLibraryName() {
        List<String> names = new ArrayList<String>();
        for (LibraryBook libraryBook : mLibraryList) {
            names.add(libraryBook.getName());
        }
        return names.toArray(new String[names.size()]);
    }

}
