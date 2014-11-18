package swiatowski.piotr.bibliotekapwr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.LinkedList;
import java.util.List;

import swiatowski.piotr.bibliotekapwr.db.entity.NotificationEntity;
import swiatowski.piotr.bibliotekapwr.db.table.NotificationTable;

/**
 * Created by Piotrek on 2014-11-11.
 */
@Singleton
public class NotificationDataSource {

    private SQLiteDatabase mDatabase;

    @Inject
    private NotificationDataSource(Context context) {
        mDatabase = LibraryDbHelper.getInstance(context).getDatabase();
    }

    private NotificationEntity cursorToWord(Cursor cursor) {
        int notificationId;
        String notificationSignature, notificationHref, notificationRentUrl, notificationTitle;

        //notificationId = cursor.getInt(NotificationTable.ColumnID.NOTIFICATION_ID);
        notificationSignature = cursor.getString(NotificationTable.ColumnID.NOTIFICATION_SIGNATURE);
        notificationHref = cursor.getString(NotificationTable.ColumnID.NOTIFICATION_HREF);
        notificationRentUrl = cursor.getString(NotificationTable.ColumnID.NOTIFICATION_RENT_URL);
        notificationTitle = cursor.getString(NotificationTable.ColumnID.NOTIFICATION_TITLE);
        notificationId = Integer.parseInt(cursor.getString(NotificationTable.ColumnID.NOTIFICATION_ID));
        NotificationEntity entity = new NotificationEntity(notificationSignature, notificationHref, notificationRentUrl, notificationTitle);

        entity.setId(notificationId);

        return entity;
    }

    public void insert(NotificationEntity notificationEntity) {
        ContentValues values = new ContentValues();

       // values.put(NotificationTable.Column.NOTIFICATION_ID, notificationEntity.getId() > 0 ? notificationEntity.getId() : null);
        values.put(NotificationTable.Column.NOTIFICATION_SIGNATURE, notificationEntity.getSignature());
        values.put(NotificationTable.Column.NOTIFICATION_HREF, notificationEntity.getHref());
        values.put(NotificationTable.Column.NOTIFICATION_RENT_URL, notificationEntity.getRentUrl());
        values.put(NotificationTable.Column.NOTIFICATION_TITLE, notificationEntity.getTitle());

        Log.d("doszlo", " insertt to ");
        mDatabase.insertWithOnConflict(NotificationTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public NotificationEntity get(int notificationId) {
        String where = String.format("%s = %d", NotificationTable.Column.NOTIFICATION_ID, notificationId);
        Cursor cursor = mDatabase.query(NotificationTable.TABLE_NAME, NotificationTable.ALL_COLUMNS, where, null,
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            NotificationEntity notification = cursorToWord(cursor);
            cursor.close();

            return notification;
        } else {
            cursor.close();
            return null;
        }
    }

    public List<NotificationEntity> getAll() {
        List<NotificationEntity> notifications = new LinkedList<NotificationEntity>();

        Cursor cursor = mDatabase.query(NotificationTable.TABLE_NAME, NotificationTable.ALL_COLUMNS, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    notifications.add(cursorToWord(cursor));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return notifications;
    }

    public void remove(int notificationId) {
        String where = String.format("%s = %d", NotificationTable.Column.NOTIFICATION_ID, notificationId);
        mDatabase.delete(NotificationTable.TABLE_NAME, where, null);
    }

}
