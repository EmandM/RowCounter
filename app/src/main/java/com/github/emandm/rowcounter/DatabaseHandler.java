package com.github.emandm.rowcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emmamcmillan on 13/03/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "rowCounter";

    // Projects table name
    private static final String PROJECTS_TABLE = "projects";

    // Projects Table Columns names
    private String TAG = "Emma's application";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "project_name";
    private static final String KEY_CREATED_DATE = "created_date";


    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROJECTS_TABLE = "CREATE TABLE " + PROJECTS_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CREATED_DATE + " DATE" + ")";
        db.execSQL(CREATE_PROJECTS_TABLE);
        Log.d(TAG, "projects table created: " + db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PROJECTS_TABLE);

        // Create tables again
        onCreate(db);
    }

    public void columnNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ti = db.rawQuery("PRAGMA table_info(projects)", null);
        if ( ti.moveToFirst() ) {
            do {
                Log.d(TAG, "col: " + ti.getString(1));
            } while (ti.moveToNext());
        }
    }

    void clearAll() {
        Log.d(TAG, "Clearing...");
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(PROJECTS_TABLE, null, null);

        db.execSQL("DROP TABLE " + PROJECTS_TABLE);
    }

    public void createDatabase() {
        Log.d(TAG, "creating...");
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new project
    void addProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, project.getName());
        values.put(KEY_CREATED_DATE, dateFormat.format(project.getCreatedDate()));

        // Inserting Row
        db.insert(PROJECTS_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single project
    Project getProject(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PROJECTS_TABLE, new String[]{KEY_ID,
                        KEY_NAME, KEY_CREATED_DATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return createProject(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    // Getting All Projects
    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<Project>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROJECTS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding project to list
                projectList.add(createProject(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        // return project list
        return projectList;
    }

    // Updating single project
    public int updateProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, project.getName());
        values.put(KEY_CREATED_DATE, dateFormat.format(project.getCreatedDate()));

        // updating row
        return db.update(PROJECTS_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(project.getID()) });
    }

    // Deleting single project
    public void deleteProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROJECTS_TABLE, KEY_ID + " = ?",
                new String[]{String.valueOf(project.getID())});
        db.close();
    }


    // Getting projects Count
    public int getProjectsCount() {
        String countQuery = "SELECT  * FROM " + PROJECTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    private Project createProject(String id, String name, String date) {
        Project project = new Project();

        try {
            project.setID(Integer.parseInt(id));
            project.setName(name);
            project.setCreatedDate(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // return project
        return project;
    }
}