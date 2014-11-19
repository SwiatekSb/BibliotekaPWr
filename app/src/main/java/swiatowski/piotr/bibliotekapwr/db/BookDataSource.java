package swiatowski.piotr.bibliotekapwr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import swiatowski.piotr.bibliotekapwr.LibraryBook;
import swiatowski.piotr.bibliotekapwr.db.entity.BookEntity;
import swiatowski.piotr.bibliotekapwr.db.table.BookTable;

/**
 * Created by Piotrek on 2014-11-18.
 */
public class BookDataSource {

    private SQLiteDatabase mDatabase;

    @Inject
    private BookDataSource(Context context) {
        mDatabase = LibraryDbHelper.getInstance(context).getDatabase();
    }

    private BookEntity cursorToWord(Cursor cursor) {
        String title, author;

        title = cursor.getString(BookTable.ColumnID.BOOK_TITLE);
        author = cursor.getString(BookTable.ColumnID.BOOK_AUTHOR);

        return new BookEntity("", author, title, "" , new ArrayList<LibraryBook>());

    }

    public void insert(BookEntity libraryEntity) {
        ContentValues values = new ContentValues();

        // values.put(NotificationTable.Column.NOTIFICATION_ID, notificationEntity.getId() > 0 ? notificationEntity.getId() : null);
        values.put(BookTable.Column.BOOK_TITLE, libraryEntity.getTitle()+"");
        values.put(BookTable.Column.BOOK_AUTHOR, libraryEntity.getAuthor() + "");

        Log.d("doszlo", " insertt to ");
        mDatabase.insertWithOnConflict(BookTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public BookEntity get(int libraryId) {
        String where = String.format("%s = %d", BookTable.Column.BOOK_ID, libraryId);
        Cursor cursor = mDatabase.query(BookTable.TABLE_NAME, BookTable.ALL_COLUMNS, where, null,
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            BookEntity library = cursorToWord(cursor);
            cursor.close();

            return library;
        } else {
            cursor.close();
            return null;
        }
    }

    public List<BookEntity> getAll() {
        List<BookEntity> libraries = new LinkedList<BookEntity>();

        Cursor cursor = mDatabase.query(BookTable.TABLE_NAME, BookTable.ALL_COLUMNS, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    libraries.add(cursorToWord(cursor));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return libraries;
    }

    public void remove(int libraryId) {
        String where = String.format("%s = %d", BookTable.Column.BOOK_ID, libraryId);
        mDatabase.delete(BookTable.TABLE_NAME, where, null);
    }
}
