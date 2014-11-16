package swiatowski.piotr.bibliotekapwr.db.table;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Piotrek on 2014-11-02.
 */
public class LibraryTable {

    private LibraryTable () {}

    public static final String TABLE_NAME = "library";

    public static final class Column {
        public static final String LIBRARY_ID = "libraryId";
        public static final String LIBRARY_LATITIUDE = "libraryLatitiude";
        public static final String LIBRARY_LONGITIUDE = "libraryLongitiude";
        public static final String LIBRARY_NAME = "libraryName";
        public static final String LIBRARY_ADDRESS = "libraryAddress";
        public static final String LIBRARY_BUILDING = "libraryBuilding";
        public static final String LIBRARY_START_TIME = "libraryStartTime";
        public static final String LIBRARY_CLOSE_TIME = "libraryCloseTime";
    }

    public static final class ColumnID {
        public static final int LIBRARY_ID = 0;
        public static final int LIBRARY_LATITIUDE  = 1;
        public static final int LIBRARY_LONGITIUDE = 2;
        public static final int LIBRARY_NAME = 3;
        public static final int LIBRARY_ADDRESS = 4;
        public static final int LIBRARY_BUILDING = 5;
        public static final int LIBRARY_START_TIME = 6;
        public static final int LIBRARY_CLOSE_TIME = 7;
    }

    public static final String[] ALL_COLUMNS = {
            Column.LIBRARY_ID,
            Column.LIBRARY_LATITIUDE,
            Column.LIBRARY_LONGITIUDE,
            Column.LIBRARY_NAME,
            Column.LIBRARY_ADDRESS,
            Column.LIBRARY_BUILDING,
            Column.LIBRARY_START_TIME,
            Column.LIBRARY_CLOSE_TIME
    };

    public static void createTable(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT)",
                TABLE_NAME,
                Column.LIBRARY_ID,
                Column.LIBRARY_LATITIUDE,
                Column.LIBRARY_LONGITIUDE,
                Column.LIBRARY_NAME,
                Column.LIBRARY_ADDRESS,
                Column.LIBRARY_BUILDING,
                Column.LIBRARY_START_TIME,
                Column.LIBRARY_CLOSE_TIME);

        db.execSQL(sql);
    }

}

/**
 * A-1 : Lat: 51.107350  Lon:17.061911
 * D-21 Lat: 51.109756   Lon:17.058116
 * D-1:  Lat: 51.110411  Lon: 17.058133
 * D-2  Lat: 51.110196  Lon: 17.057371
 * A-3:  Lat: 51.108058  Lon: 17.064023
 * D-20: Lat: 51.110654   Lon: 17.059496
 * B-4   Lat: 51.108324  Lon: 17.065026
 * C-6  Lat: 51.108443   Lon:17.060628
 * C-3 Lat: 51.108967  Lon:17.060236
 * C-5: Lat: 51.109448   Lon: 17.058959
 * H-4 Lat: 51.108853  Lon: 17.054491
 * L-1  Lat: 51.104533   Lon: 17.054287
 * E-1 i  E-4  Lat: 51.119145  Lon: 17.054371
 *
 * http://www.latlong.net/
 *
 * http://www.biblioteka.pwr.wroc.pl/files/prv/id16/Mapa2014.pdf
 *
 */
