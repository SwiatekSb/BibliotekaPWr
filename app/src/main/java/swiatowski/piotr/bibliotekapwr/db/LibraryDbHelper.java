package swiatowski.piotr.bibliotekapwr.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import swiatowski.piotr.bibliotekapwr.db.table.BookTable;
import swiatowski.piotr.bibliotekapwr.db.table.LibraryTable;
import swiatowski.piotr.bibliotekapwr.db.table.NotificationTable;

/**
 * Created by Piotrek on 2014-11-02.
 */
public class LibraryDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "libraryDb.sqlite";
    private static final int DB_VERSION = 1;
    private static LibraryDbHelper sLibraryDbHelper;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private LibraryDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;
        try {
            Log.d("doszlo", "open databa ");
            openDataBase();
        } catch (IOException e) {
            Log.e("IOE", "cant open DB", e);
        }
        mDatabase = getWritableDatabase();
    }

    public static LibraryDbHelper getInstance(Context context) {
        if (sLibraryDbHelper == null) {
            Log.d("doszlo", " geIsntand db Helepr");
            sLibraryDbHelper = new LibraryDbHelper(context);
        }

        return sLibraryDbHelper;
    }

    private boolean databaseExists() {
        File dbFile = mContext.getDatabasePath(DB_NAME);

        return dbFile.exists();
    }

    private void copyDataBase() throws IOException{
        AssetManager assets = mContext.getAssets();
        InputStream input = assets.open(DB_NAME);
        String outputPath = mContext.getDatabasePath(DB_NAME).getPath();
        OutputStream output = new FileOutputStream(outputPath);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();
    }

    private void createDataBase() throws IOException {
        boolean dbExists = databaseExists();
        SQLiteDatabase dbRead;

        if (!dbExists) {
            dbRead = getReadableDatabase();
            dbRead.close();
            copyDataBase();
        } else
            Log.d("IOE", "swiatowski.piotr.bibliotekapwr.db exists");
    }

    public void openDataBase() throws SQLiteException, IOException {
        createDataBase();

        if (mDatabase != null)
            mDatabase.close();

        mDatabase = mContext.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        Log.d("doszlo", " try create");
        NotificationTable.createTable(mDatabase);
        LibraryTable.createTable(mDatabase);
        BookTable.createTable(mDatabase);
//        WordsPackages.createTable(mDatabase);
//        Translations.createTable(mDatabase);
//        FlipBoards.createTable(mDatabase);
//        UserProfileTable.createTable(mDatabase);
    }

    @Override
    public synchronized void close() {
        if (mDatabase != null)
            mDatabase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

}
