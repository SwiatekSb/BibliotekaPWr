package swiatowski.piotr.bibliotekapwr.model;

import java.io.Serializable;
import java.util.List;

import swiatowski.piotr.bibliotekapwr.db.entity.BookEntity;

/**
 * Created by Piotrek on 2014-11-02.
 */
public class Page implements Serializable{

    private List<BookEntity> mBookList;
    private String mNextPage;
    private String mPreviousPage;
    private boolean mIsPrevious;
    private boolean mIsNext;

    public Page(List<BookEntity> bookList, String nextPage, String previousPage, boolean isPrevious, boolean isNext) {
        mBookList = bookList;
        mNextPage = nextPage;
        mPreviousPage = previousPage;
        mIsPrevious = isPrevious;
        mIsNext = isNext;
    }

    public List<BookEntity> getBookList() {
        return mBookList;
    }

    public String getNextPage() {
        return mNextPage;
    }

    public String getPreviousPage() {
        return mPreviousPage;
    }

    public boolean isNextPage() {
        return mIsNext;
    }

    public boolean isPreviousPage() {
        return mIsPrevious;
    }

}
