package tk.ghostffilms.schoolofrock;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for the db name and version
    public static final String DATABASE_NAME = "schoolOfRock.db";
    public static final int DATABASE_VERSION = 1;

    // Initialize constants for the schoolOfRocklist table
    public static final String TABLE_SONG_VOTE_LIST = "schoolOfRockList";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_SONG = "song";
    public static final String COLUMN_LIST_VOTER = "voter";
    public static final String COLUMN_LIST_VOTE = "vote";

    /**
     * Create a Version of our database
     *
     * @param context reference to the activity that initializes a DBHandler
     * @param factory null
     */
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates Shopper database tables
     *
     * @param sqLiteDatabase reference to the shopper database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Define create statement for schoolOfRockList table and store it in a string
        String queryListCreate = "CREATE TABLE " + TABLE_SONG_VOTE_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_SONG + " TEXT, " +
                COLUMN_LIST_VOTER + " TEXT, " +
                COLUMN_LIST_VOTE + " INTEGER);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(queryListCreate);
    }

    /**
     * Creates a new Version of the SchoolOfRock database
     *
     * @param sqLiteDatabase reference to the schoolOfRockList database
     * @param oldVersion     old version of the schoolOfRockList database
     * @param newVersion     new version of the schoolOfRockList database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Define drop statement and store it in a string
        String queryListUpgrade = "DROP TABLE IF EXISTS " + TABLE_SONG_VOTE_LIST;

        // Execute the drop SQL Statement
        sqLiteDatabase.execSQL(queryListUpgrade);

        // Create Tables
        onCreate(sqLiteDatabase);
    }

    /**
     * This method gets called when the add button in the Action Bar of the CreateList
     * Activity gets clicked. It inserts a new row into the schoolOfRockList table.
     *
     * @param song  schoolOfRockList list song
     * @param voter schoolOfRockList list voter
     * @param vote  schoolOfRockList list vote
     */
    public void addSchoolOfRockVote(String song, String voter, int vote) {
        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // Initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data in to ContentValues Object
        values.put(COLUMN_LIST_SONG, song);
        values.put(COLUMN_LIST_VOTER, voter);
        values.put(COLUMN_LIST_VOTE, vote);

        // Insert data in ContentValues Object into the shoppingList table
        db.insert(TABLE_SONG_VOTE_LIST, null, values);

        // Close database reference
        db.close();
    }

    /**
     * This method gets called when the main activity is created. It will select
     * and return all of the data in the schoolOfRockList table.
     *
     * @return Cursor that contains all the data in the schoolOfRockList table
     */
    public String getSchoolOfRockVotes() {
        // get reference to the schoolOfRock database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement and store it in a string
        String queryListLikes = "SELECT * FROM " + TABLE_SONG_VOTE_LIST +
                " WHERE " + COLUMN_LIST_VOTE + " = " + 1;

        // Execute select statement and store result in a cursor
        Cursor cursorLikes = db.rawQuery(queryListLikes, null);

        // define select statement and store it in a string
        String queryListDislikes = "SELECT * FROM " + TABLE_SONG_VOTE_LIST +
                " WHERE " + COLUMN_LIST_VOTE + " = " + 0;

        // Execute select statement and store result in a cursor
        Cursor cursorDislikes = db.rawQuery(queryListDislikes, null);

        String votesToast = "Like: " + cursorLikes.getCount() +
                "\nDislikes: " + cursorDislikes.getCount();
        // execute statement and return it as a Cursor
        return votesToast;
    }

}

