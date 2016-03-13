package com.github.emandm.rowcounter;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by emmamcmillan on 13/03/16.
 */
public class Project {
    /**
     * The Contact class exists to store the details of a project.
     * It is yet to be fully implemented.
     */

    private int _id;
    private String _projectName;
    private Date _dateCreated;

    public Project() {
        _id = 0;
        _projectName = "";
        _dateCreated = new Date();
    }

    public void setID(int ID){
        this._id = ID;
    }

    public int getID() {
        return _id;
    }

    public void setName(String name) {
        this._projectName = name;
    }

    public String getName() {
        return _projectName;
    }

    public void setCreatedDate(Date date) {
        this._dateCreated = date;
    }

    public Date getCreatedDate() {
        return _dateCreated;
    }

    public int compareTo(Project contact) {
        return (this.getName()).compareTo(contact.getName());

    }


    public static Comparator<Project> NameComparator = new Comparator<Project>() {

        public int compare(Project x, Project y) {

            String one = x.getName();
            String two = y.getName();

            //ascending order
            return one.compareTo(two);
        }
    };
    public static Comparator<Project> DateComparator = new Comparator<Project>() {

        public int compare(Project x, Project y) {

            Date one = x.getCreatedDate();
            Date two = y.getCreatedDate();

            //ascending order
            return one.compareTo(two);
        }
    };
}