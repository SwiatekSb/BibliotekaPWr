package swiatowski.piotr.bibliotekapwr.db.table;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Piotrek on 2014-11-11.
 */
public class NotificationTable {

    private NotificationTable () {}

    public static final String TABLE_NAME = "notification";

    public static final class Column {
        public static final String NOTIFICATION_ID = "notificationId";
        public static final String NOTIFICATION_SIGNATURE = "notificationSignature";
        public static final String NOTIFICATION_HREF = "notificationHref";
        public static final String NOTIFICATION_RENT_URL = "notificationRentUrl";
        public static final String NOTIFICATION_TITLE = "notificationTitle";
    }

    public static final class ColumnID {
        public static final int NOTIFICATION_ID = 0;
        public static final int NOTIFICATION_SIGNATURE  = 1;
        public static final int NOTIFICATION_HREF = 2;
        public static final int NOTIFICATION_RENT_URL = 3;
        public static final int NOTIFICATION_TITLE = 4;
    }

    public static final String[] ALL_COLUMNS = {
            Column.NOTIFICATION_ID,
            Column.NOTIFICATION_SIGNATURE,
            Column.NOTIFICATION_HREF,
            Column.NOTIFICATION_RENT_URL,
            Column.NOTIFICATION_TITLE
    };

    public static void createTable(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT)",
                TABLE_NAME,
                Column.NOTIFICATION_ID,
                Column.NOTIFICATION_SIGNATURE,
                Column.NOTIFICATION_HREF,
                Column.NOTIFICATION_RENT_URL,
                Column.NOTIFICATION_TITLE);

        db.execSQL(sql);
    }
}
