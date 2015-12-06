/*
 * Medicine
 *
 * Version 1
 */
package medicine.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes medicine characteristics
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class Medicine {
    private String id;      // unique identifier
    private String name;    // medicine name
    private String pharm;   // company-producer
    private PharmalogicalGroup group;   // medicine group
    private List<String> analogs;       // medicine analogs
    private List<Version> versions;     // medicine versions   

    /**
     * Default constructor 
     */
    public Medicine() {
        versions = new ArrayList<>();
        analogs = new ArrayList<>();
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the company name
     */
    public String getPharm() {
        return pharm;
    }

    /**
     * @param pharm the company name to set
     */
    public void setPharm(String pharm) {
        this.pharm = pharm;
    }

    /**
     * @return the group
     */
    public PharmalogicalGroup getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(PharmalogicalGroup group) {
        this.group = group;
    }

    /**
     * @return the analogs
     */
    public List<String> getAnalogs() {
        return analogs;
    }

    /**
     * @param analogs the analogs to set
     */
    public void setAnalogs(List<String> analogs) {
        this.analogs = analogs;
    }
    
    public void addAnalog( String analog ) {
        if ( analogs == null ) {
            analogs = new ArrayList<>();
        }
        analogs.add( analog );
    }

    /**
     * @return the versions
     */
    public List<Version> getVersions() {
        return versions;
    }

    /**
     * @param versions the versions to set
     */
    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        String str = "ID: " + id;
        str += ", name: " + name;
        str += ", pharm: " + pharm;
        str += ", group: " + group;
        if ( analogs.size() > 0 ) {
            str += "\nanalogs: ";
        }
        String strList = "";
        for ( int i = 0; i < analogs.size(); i++ ) {
            strList += ( strList.isEmpty() ? "" : ", " ) + analogs.get(i).toString();
        }
        str += strList;
        strList = "";
        for ( int i = 0; i < versions.size(); i++ ) {
            strList += ( strList.isEmpty() ? "" : "\n" ) + versions.get(i).toString();
        }
        return str + "\n" + strList;
    }
    
    
}
