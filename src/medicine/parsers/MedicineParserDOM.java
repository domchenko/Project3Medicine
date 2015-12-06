/*
 * MedicineParserDOM
 *
 * Version 1
 */
package medicine.parsers;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import medicine.structure.Amount;
import medicine.structure.Certificate;
import medicine.structure.Dosage;
import medicine.structure.DrugPackage;
import medicine.structure.Medicine;
import medicine.structure.PharmalogicalGroup;
import medicine.structure.Price;
import medicine.structure.Version;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Provides loading data from xml file
 * (DOM parser)
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class MedicineParserDOM {
    
    /**
     * Load medicine items from the file
     * 
     * @param xmlPath   the file path
     * @return          a list of the drugs
     * @throws SAXException
     * @throws IOException 
     */
    public static List<Medicine> parse( String xmlPath ) throws SAXException, IOException {
        List<Medicine> list = new ArrayList<>();
        DOMParser parser = new DOMParser();
        parser.parse( xmlPath );
        
        Document doc = parser.getDocument();
        Element root = doc.getDocumentElement(); 
        
        NodeList tagList = root.getElementsByTagName( "medicine" );
        for ( int i = 0; i < tagList.getLength(); i++ ) {
            Medicine m = new Medicine();
            
            Element medicineTag = (Element) tagList.item( i );
            String attrValue = medicineTag.getAttribute( "id" ); 
            m.setId( attrValue );
            
            m.setName( getChildNodeValue( medicineTag, "name" ) );
            m.setGroup( PharmalogicalGroup.valueOf( 
                    getChildNodeValue( medicineTag, "group" ) ) );
            m.setPharm( getChildNodeValue( medicineTag, "pharm" ) );
            
            Element analogsTag = (Element) medicineTag.getElementsByTagName( "analogs" ).item(0);
            parseAnalogs( m, analogsTag );
            
            Element versionsTag = (Element) medicineTag.getElementsByTagName( "versions" ).item(0);
            parseVersions( m, versionsTag );
            
            list.add( m );
        }
        
        return list;
    } 
    
    /**
     * Returns sub node value by its name
     * 
     * @param parent    the parent element
     * @param name      the child element name
     * @return          the node value
     */
    private static String getChildNodeValue( Element parent, String name ) {
        String res = "";
        Element elem = (Element) parent.getElementsByTagName( name ).item(0);
        if ( elem != null ) {
            Text nodeData = (Text) elem.getFirstChild();
            res = nodeData.getTextContent();
        }
        return res;
    }
    
    /**
     * Returns node value
     * 
     * @param elem  the element
     * @return      the node value
     */
    private static String getNodeValue( Element elem ) {
        Text nodeData = (Text) elem.getFirstChild();
        return nodeData.getTextContent();
    }
    
    /**
     * Parsing the medicine analogs
     * 
     * @param m         the medicine item
     * @param analogs   the tag containing a list of analogues of the medicine
     */
    private static void parseAnalogs( Medicine m, Element analogs ) {
        if ( analogs == null ) {
            return;
        }
        NodeList tagList = analogs.getElementsByTagName( "analog" );
        for ( int i = 0; i < tagList.getLength(); i++ ) {
            Text nodeData = (Text) tagList.item(i).getFirstChild();
            m.addAnalog( nodeData.getTextContent() );
        }
    }
    
    /**
     * Parsing the medicine versions
     * 
     * @param m         the medicine item
     * @param versions  the tag containing a list of versions of the medicine
     */
    private static void parseVersions( Medicine m, Element versions ) {
        NodeList tagList = versions.getElementsByTagName( "version" );
        for ( int i = 0; i < tagList.getLength(); i++ ) {
            Version v = new Version();
                
            Element versionTag = (Element) tagList.item( i );
            String attrValue = versionTag.getAttribute( "type" ); 
            v.setType( attrValue );            
            
            Element certificateTag = (Element) versionTag.getElementsByTagName( "certificate" ).item(0);
            Certificate c = new Certificate();   
            attrValue = certificateTag.getAttribute( "number" ); 
            c.setNumber( attrValue );            
            attrValue = certificateTag.getAttribute( "dateFrom" );
            Date date = DateHelper.parseDate( attrValue );
            c.setDateFrom( date );            
            attrValue = certificateTag.getAttribute( "dateTo" ); 
            date = DateHelper.parseDate( attrValue );
            c.setDateTo( date );            
            // certificate/organization
            c.setOrganization( getChildNodeValue( certificateTag, "organization" ) );
            v.setCertificate( c );
            
            Element packageTag = (Element) versionTag.getElementsByTagName( "package" ).item(0);
            DrugPackage p = new DrugPackage();            
            // package/name
            p.setName( getChildNodeValue( packageTag, "packagespace:name" ) );
            // package/amount
            Element amountTag = (Element) packageTag.getElementsByTagName( "packagespace:amount" ).item(0);
            Amount a = new Amount();            
            attrValue = amountTag.getAttribute( "measure" );
            a.setMeasure( attrValue );
            a.setValue( Integer.valueOf( getNodeValue( amountTag ) ) );
            p.setAmount( a );
            // package/price
            Element priceTag = (Element) packageTag.getElementsByTagName( "packagespace:price" ).item(0);
            Price pr = new Price();
            attrValue = priceTag.getAttribute( "currency" );
            pr.setCurrency( attrValue );
            pr.setValue( Integer.valueOf( getNodeValue( priceTag ) ) );
            p.setPrice( pr );
            // add child
            v.setPack( p );
            
            Element dosageTag = (Element) versionTag.getElementsByTagName( "dosage" ).item(0);
            Dosage d = new Dosage();
            d.setNumber( Integer.parseInt( getChildNodeValue( dosageTag, "number" ) ) );
            d.setPeriodicity( Integer.parseInt( getChildNodeValue( dosageTag, "periodicity" ) ) );
            // add children
            v.setDosage( d );
            
            m.getVersions().add( v );
        }
    }
}
