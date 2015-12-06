/*
 * Certificate
 *
 * Version 1
 */
package medicine.structure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides information about the medicine registration
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class Certificate {
    private String number;  // registration number
    private Date dateFrom;  // beginning of the registration period 
    private Date dateTo;    // ending of the registration period
    private String organization;    // a company that registered this medicine 

    /**
     * @return the certificate number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the certificate number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the start date of the registration period
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the start date to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the stop date of the registration period
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the stop date to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the name of the company
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the name of the company to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat( "dd.MM.yyyy" );
        String str = "Certificate #" + getNumber() 
                + ", " + df.format( getDateFrom() ) 
                + "-" + df.format( getDateTo() );
        str += ", " + getOrganization();
        return str;
    }
    
}
