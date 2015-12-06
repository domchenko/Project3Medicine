/*
 * MedicineParserSAX
 *
 * Version 1
 */
package medicine.parsers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import medicine.structure.Amount;
import medicine.structure.Price;
import medicine.structure.Certificate;
import medicine.structure.Dosage;
import medicine.structure.DrugPackage;
import medicine.structure.Medicine;
import medicine.structure.PharmalogicalGroup;
import medicine.structure.Version;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Provides loading data from the xml file
 * (SAX parser)
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class MedicineParserSAX extends DefaultHandler {
    List<Medicine> l = new ArrayList<>();   // medicine list
    Medicine item;                          // current medicine item
    Version version;                        // current version of the medicine
    int current;                            // tag number, allows to identify active tag
    
    /**
     * Returns all medicines
     * 
     * @return 
     */
    public List<Medicine> getList() {
        return l;
    }
    
    /**
     * Occurs when the parser meets any tag 
     * 
     * @param uri       the namespace url
     * @param localName the tag name
     * @param qName     the tag name with namespace name 
     * @param attrs     the tag attributes
     */
    @Override
    public void startElement( String uri, String localName, String qName, Attributes attrs ) {
        // qName: <namespace_name><local_name>
        switch ( qName ) {
            case "medicine": {
                item = new Medicine(); 
                item.setId( attrs.getValue( "id" ) );
                current = 0;
                break;
            }
            case "name": {
                current = 1;
                break;
            }
            case "pharm": {
                current = 2;
                break;
            }
            case "group": {
                current = 3;
                break;
            }
            case "analogs": {
                current = 0;
                break;
            }
            case "analog": {
                current = 4;
                break;
            }
            case "versions": {
                current = 0;
                break;
            }
            case "version": {
                version = new Version();
                version.setType( attrs.getValue( "type" ) );
                current = 0;
                break;
            } 
            case "certificate": {
                version.setCertificate( new Certificate() );
                version.getCertificate().setNumber( attrs.getValue( "number" ) );
                Date d = DateHelper.parseDate( attrs.getValue( "dateFrom" ) );
                if ( d != null ) {
                    version.getCertificate().setDateFrom( d );
                }
                d = DateHelper.parseDate( attrs.getValue( "dateTo" ) );
                if ( d != null ) {
                    version.getCertificate().setDateTo( d );
                }
                current = 0;
                break;
            }
            case "organization": {
                current = 5;
                break;
            }
            case "package" : { 
                version.setPack( new DrugPackage() );
                current = 0;
                break;
            }
            case "packagespace:name" : {
                current = 6;
                break;
            }
            case "packagespace:amount" : {
                version.getPack().setAmount( new Amount() );
                version.getPack().getAmount().setMeasure( attrs.getValue( "measure" ) );
                current = 7;
                break;
            }
            case "packagespace:price" : {
                version.getPack().setPrice( new Price() );
                version.getPack().getPrice().setCurrency( attrs.getValue( "currency" ) );
                current = 8;
                break;
            }
            case "dosage" : { 
                version.setDosage( new Dosage() );
                current = 0;
                break;
            }
            case "number" : { 
                current = 9;
                break;
            }
            case "periodicity" : { 
                current = 10;
                break;
            }
        }
    }
    
    /**
     * Occurs when the parser meets the text data
     * 
     * @param chars     the text content
     * @param start     the starting position
     * @param length    the ending position
     */
    @Override
    public void characters( char[] chars, int start, int length ) {
        String tagValue = new String( chars, start, length );
        switch ( current ) {
            case 1: { // medicine/name
                item.setName( tagValue );
                break;
            }
            case 2: { // ./pharm
                item.setPharm( tagValue );
                break;
            }
            case 3: { // ./group
                item.setGroup( PharmalogicalGroup.valueOf( tagValue ) );
                break;
            }
            case 4: { // ./analogs/analog
                item.addAnalog( tagValue );
                break;
            }
            case 5: { // ./versions/version/certificate/organization
                version.getCertificate().setOrganization( tagValue );
                break;
            }
            case 6: { // ././version/package/type
                version.getPack().setName( tagValue );
                break;
            }
            case 7: { // ./././package/amount
                version.getPack().getAmount().setValue( Integer.valueOf( tagValue ) );
                break;
            }
            case 8: { // ./././package/price
                version.getPack().getPrice().setValue( Integer.valueOf( tagValue ) );
                break;
            }
            case 9: { // ./././dosage/number
                version.getDosage().setNumber( Integer.valueOf( tagValue ) );
                break;
            }
            case 10: { // ./././dosage/periodicity
                version.getDosage().setPeriodicity( Integer.valueOf( tagValue ) );
                break;
            }
        }
        current = 0;
    }

    /**
     * Occurs when the parser meets tag end
     * 
     * @param uri       the namespace url
     * @param localName the tag name
     * @param qName     the tag name including namespace
     */
    @Override
    public void endElement( String uri, String localName, String qName ) {
        switch ( qName ) {
            case "medicine": {
                l.add( item );
                break;
            }
            case "version": {
                item.getVersions().add( version );
                break;
            }
        }
    }

}
