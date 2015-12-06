/*
 * DateHelper
 *
 * Version 1
 */
package medicine.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converts the formatted string to the date
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
class DateHelper {
    
    public static Date parseDate( String dateStr ) {
        Date d = null;
        if ( !dateStr.isEmpty() ) {
            DateFormat df = new SimpleDateFormat( "yyyy-mm-dd" );
            try {
                d = df.parse( dateStr );
            } catch (ParseException ex) {
            }
        }
        return d;
    }
}
