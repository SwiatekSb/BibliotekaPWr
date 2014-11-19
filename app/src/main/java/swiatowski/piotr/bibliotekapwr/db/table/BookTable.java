package swiatowski.piotr.bibliotekapwr.db.table;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Piotrek on 2014-11-18.
 */
public class BookTable {

    private BookTable () {}

    public static final String TABLE_NAME = "books";

    public static final class Column {
        public static final String BOOK_ID = "bookId";
        public static final String BOOK_TITLE = "bookTitle";
        public static final String BOOK_AUTHOR = "bookAuthor";
    }

    public static final class ColumnID {
        public static final int BOOK_ID = 0;
        public static final int BOOK_TITLE = 1;
        public static final int BOOK_AUTHOR = 2;
    }

    public static final String[] ALL_COLUMNS = {
            Column.BOOK_ID,
            Column.BOOK_TITLE,
            Column.BOOK_AUTHOR
    };

    public static void createTable(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s TEXT)",
                TABLE_NAME,
                Column.BOOK_ID,
                Column.BOOK_TITLE,
                Column.BOOK_AUTHOR);

        db.execSQL(sql);
    }
}
