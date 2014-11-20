package swiatowski.piotr.bibliotekapwr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.inject.Inject;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Singleton;

import swiatowski.piotr.bibliotekapwr.db.entity.LibraryEntity;
import swiatowski.piotr.bibliotekapwr.db.table.LibraryTable;

/**
 * Created by Piotrek on 2014-11-16.
 */
@Singleton
public class LibraryDataSource {


    private SQLiteDatabase mDatabase;

    @Inject
    private LibraryDataSource(Context context) {
        mDatabase = LibraryDbHelper.getInstance(context).getDatabase();
    }

    private LibraryEntity cursorToWord(Cursor cursor) {
        String libraryLatitude, libraryLongitude, libraryName, libraryAddress, libraryBuilding,
        libraryStartTime, libraryEndTIme;


        libraryLatitude = cursor.getString(LibraryTable.ColumnID.LIBRARY_LATITIUDE);
        libraryLongitude = cursor.getString(LibraryTable.ColumnID.LIBRARY_LONGITIUDE);
        libraryName = cursor.getString(LibraryTable.ColumnID.LIBRARY_NAME);
        libraryAddress = cursor.getString(LibraryTable.ColumnID.LIBRARY_ADDRESS);
        libraryBuilding = cursor.getString(LibraryTable.ColumnID.LIBRARY_BUILDING);
        libraryStartTime = cursor.getString(LibraryTable.ColumnID.LIBRARY_START_TIME);
        libraryEndTIme = cursor.getString(LibraryTable.ColumnID.LIBRARY_CLOSE_TIME);

        return new LibraryEntity(Float.parseFloat(libraryLatitude), Float.parseFloat(libraryLongitude), libraryName, libraryAddress, libraryBuilding, libraryStartTime, libraryEndTIme);

    }

    public void insert(LibraryEntity libraryEntity) {
        ContentValues values = new ContentValues();

        // values.put(NotificationTable.Column.NOTIFICATION_ID, notificationEntity.getId() > 0 ? notificationEntity.getId() : null);
        values.put(LibraryTable.Column.LIBRARY_LATITIUDE, libraryEntity.getLatitiude()+"");
        values.put(LibraryTable.Column.LIBRARY_LONGITIUDE, libraryEntity.getLongitiure() + "");
        values.put(LibraryTable.Column.LIBRARY_NAME, libraryEntity.getName());
        values.put(LibraryTable.Column.LIBRARY_ADDRESS, libraryEntity.getAddress());
        values.put(LibraryTable.Column.LIBRARY_BUILDING, libraryEntity.getBuilding());
        values.put(LibraryTable.Column.LIBRARY_START_TIME, libraryEntity.getStartTime());
        values.put(LibraryTable.Column.LIBRARY_CLOSE_TIME, libraryEntity.getCloseTime());

        Log.d("doszlo", " insertt to ");
        mDatabase.insertWithOnConflict(LibraryTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public LibraryEntity get(int libraryId) {
        String where = String.format("%s = %d", LibraryTable.Column.LIBRARY_ID, libraryId);
        Cursor cursor = mDatabase.query(LibraryTable.TABLE_NAME, LibraryTable.ALL_COLUMNS, where, null,
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            LibraryEntity library = cursorToWord(cursor);
            cursor.close();

            return library;
        } else {
            cursor.close();
            return null;
        }
    }

    public LibraryEntity get(String name) {
        String where = String.format("%s = \"%s\"", LibraryTable.Column.LIBRARY_NAME, name);
        Cursor cursor = mDatabase.query(LibraryTable.TABLE_NAME, LibraryTable.ALL_COLUMNS, where, null,
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            LibraryEntity library = cursorToWord(cursor);
            cursor.close();

            return library;
        } else {
            cursor.close();
            return null;
        }
    }

    public List<LibraryEntity> getAll() {
        List<LibraryEntity> libraries = new LinkedList<LibraryEntity>();

        Cursor cursor = mDatabase.query(LibraryTable.TABLE_NAME, LibraryTable.ALL_COLUMNS, null, null, null, null, null);

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
        String where = String.format("%s = %d", LibraryTable.Column.LIBRARY_ID, libraryId);
        mDatabase.delete(LibraryTable.TABLE_NAME, where, null);
        mDatabase.delete(LibraryTable.TABLE_NAME, where, null);
    }

}
