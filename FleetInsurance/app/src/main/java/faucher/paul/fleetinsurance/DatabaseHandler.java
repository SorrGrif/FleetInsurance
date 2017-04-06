package faucher.paul.fleetinsurance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "fleetInsurance";

    //Table Names
    private static final String TABLE_USER = "user";
    private static final String TABLE_CLAIMS = "claims";

    //Common Column Names
    private static final String KEY_ID = "id";

    //User Table Column Names
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE_NUM = "phone_number";
    private static final String KEY_CLAIM_STATUS = "claim_status";
    private static final String KEY_PLAN_STATUS = "plan_status";
    private static final String KEY_PROFILE_PIC = "id_profile_picture";

    //Claim Table Column Names
    private static final String KEY_CLAIM_NAME = "claim_name";
    private static final String KEY_DATE = "claim_date";
    private static final String KEY_DESC = "claim_desc";
    private static final String KEY_PIC = "id_claim_picture";

    //Creating statements to create our tables
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_ADDRESS +
            " TEXT," + KEY_PHONE_NUM + " TEXT," + KEY_CLAIM_STATUS + " TEXT," + KEY_PLAN_STATUS
            + " TEXT," + KEY_PROFILE_PIC + " TEXT)";

    private static final String CREATE_CLAIM_TABLE = "CREATE TABLE " + TABLE_CLAIMS +
            "(" + KEY_ID + " INTERGER PRIMARY KEY," + KEY_CLAIM_NAME + " TEXT," + KEY_DATE
            + " TEXT," + KEY_DESC + " TEXT," + KEY_PIC + " TEXT)";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CLAIM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLAIMS);
        onCreate(db);
    }
}
