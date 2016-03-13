package com.github.emandm.rowcounter;
import java.util.Date;

/**
 * Created by emmamcmillan on 13/03/16.
 */
public class Count {
    /**
     * The Count class exists to store the details of a project count. It mostly contains get and set methods
     * It is yet to be fully implemented.
     */

    private int _id;
    private String _countName;
    private int _countValue;
    private int _projectId;
    private Date _dateCreated;

    public Count() {
        _id = 0;
        _countName = "";
        _countValue = 0;
        _projectId = 0;
    }

    public void setID(int ID){
        this._id = ID;
    }

    public int getID() {
        return _id;
    }

    public void setProjectId(int id){
        this._projectId = id;
    }

    public int getProjectId() {
        return this._projectId;
    }

    public void setName(String name) {
        this._countName = name;
    }

    public String getName() {
        return _countName;
    }

    public void setValue(int value) {
        this._countValue = value;
    }

    public int getValue() {
        return _countValue;
    }

    public void incrementCount() {
        this._countValue++;
    }

    public void resetCount() {
        this._countValue = 0;
    }
}