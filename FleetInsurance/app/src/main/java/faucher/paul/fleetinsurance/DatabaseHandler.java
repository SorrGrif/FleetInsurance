package faucher.paul.fleetinsurance;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 3;

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

    /**
     * CREATE OPERATIONS
     */

    public void addUser(Users user){
        //Creating a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values to each column
        values.put(KEY_ID, getAllUsers().size() + 1);
        values.put(KEY_NAME, user.getName());
        values.put(KEY_ADDRESS, user.getAddress());
        values.put(KEY_PHONE_NUM, user.getPhoneNum());
        values.put(KEY_CLAIM_STATUS, user.getClaimStatus());
        values.put(KEY_PLAN_STATUS, user.getPlanStatus());
        values.put(KEY_PROFILE_PIC, user.getRes());

        //Inserting records into database
        db.insert(TABLE_USER, null, values);
    }

    public void addClaim(Claims claim){
        //Creating a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        ArrayList<Claims> claimList = getAllClaims();
        //Adding values to each column
        values.put(KEY_ID, claimList.size() + 1);
        values.put(KEY_CLAIM_NAME, claim.getClaimName());
        values.put(KEY_DATE, claim.getDate());
        values.put(KEY_DESC, claim.getDesc());
        values.put(KEY_PIC, claim.getRes());

        //Inserting records into database
        db.insert(TABLE_CLAIMS, null, values);
        db.close();
    }

    /**
     * READ OPERATIONS
     */

    public Users getUser(int id){

        //Creating a readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //Creating a cursor that will store all queryed data, and be able to sort it
        Cursor cursor = db.query(TABLE_USER,
                new String[] {KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_PHONE_NUM,
                        KEY_CLAIM_STATUS, KEY_PLAN_STATUS, KEY_PROFILE_PIC},
                "= ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Users user = new Users(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return user;
    }

    public Claims getClaim(int id){

        //Creating a readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //Creating a cursor that will store all queryed data, and be able to sort it
        Cursor cursor = db.query(TABLE_CLAIMS,
                new String[] {KEY_ID, KEY_CLAIM_NAME, KEY_DATE, KEY_DESC,
                        KEY_PIC},
                "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

       Claims claim = new Claims(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4));

        return claim;
    }

    public ArrayList<Claims> getAllClaims() {
        ArrayList<Claims> claimsList = new ArrayList<Claims>();
        String selectQuery = "SELECT  * FROM " + TABLE_CLAIMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Claims claim = new Claims();
                    claim.setId(Integer.parseInt(cursor.getString(0)));
                claim.setClaimName(cursor.getString(1));
                claim.setDate(cursor.getString(2));
                claim.setDesc(cursor.getString(3));
                claim.setRes(cursor.getString(4));
                claimsList.add(claim);
            } while (cursor.moveToNext());
        }
        return claimsList;
    }

    public ArrayList<Users> getAllUsers() {
        ArrayList<Users> usersList = new ArrayList<Users>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setId(Integer.parseInt(cursor.getString(0)));
                users.setName(cursor.getString(1));
                users.setAddress(cursor.getString(2));
                users.setClaimStatus(cursor.getString(3));
                users.setPlanStatus(cursor.getString(3));
                users.setRes(cursor.getString(4));
                usersList.add(users);
            } while (cursor.moveToNext());
        }
        return usersList;
    }




    /**
     * UPDATE OPERATIONS
     */

    public int updateUser(Users user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_ADDRESS, user.getAddress());
        values.put(KEY_PHONE_NUM, user.getPhoneNum());
        values.put(KEY_CLAIM_STATUS, user.getClaimStatus());
        values.put(KEY_PLAN_STATUS, user.getPlanStatus());
        values.put(KEY_PROFILE_PIC, user.getRes());

        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public int updateClaim(Claims claim){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, claim.getClaimName());
        values.put(KEY_DATE, claim.getDate());
        values.put(KEY_DESC, claim.getDesc());
        values.put(KEY_PIC, claim.getRes());


        return db.update(TABLE_CLAIMS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(claim.getId())});
    }

    /**
     * DELETE OPERATIONS
     */

    public void deleteUser(long user_id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(user_id)});
    }

    public void deleteClaim(long claim_id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CLAIMS, KEY_ID + " = ?",
                new String[]{String.valueOf(claim_id)});
    }

    /**
     * Close the database connection
     */
    public void closeDB(){
        SQLiteDatabase db =this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }


}
