/*
 * MedicineParserStAX
 *
 * Version 1
 */
package medicine.parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import medicine.structure.Amount;
import medicine.structure.Certificate;
import medicine.structure.Dosage;
import medicine.structure.DrugPackage;
import medicine.structure.Medicine;
import medicine.structure.PharmalogicalGroup;
import medicine.structure.Price;
import medicine.structure.Version;

/**
 * Provides loading data from xml file
 * (StAX parser)
 *
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class MedicineParserStAX {
    
    /**
     * Load medicine items from the file
     * 
     * @param xmlPath   the file path
     * @return          a list of the drugs  
     * @throws java.io.FileNotFoundException        
     * @throws javax.xml.stream.XMLStreamException  
     */
    public static List<Medicine> parse( String xmlPath ) throws FileNotFoundException, XMLStreamException {
        List<Medicine> list = new ArrayList<>();
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(
                new FileReader( xmlPath ) );            
        factory.setProperty( XMLInputFactory.IS_NAMESPACE_AWARE, true );

        Medicine m = null;
        Version v = null;
        int current = 0;

        while ( eventReader.hasNext() ) {
            XMLEvent event = eventReader.nextEvent();

            Attribute attr;        
            String tagName;

            switch ( event.getEventType() ){
                // Occurs when the parser meets the beginning of the tag
                case XMLStreamConstants.START_ELEMENT: {
                    StartElement startElement = event.asStartElement();
                    tagName = startElement.getName().getLocalPart();
                    String prefix = startElement.getName().getPrefix();
                    if ( !prefix.isEmpty() ) {
                        tagName = prefix + ":" + tagName;
                    }
                    switch ( tagName ) {
                        case "medicine": {
                            m = new Medicine();
                            attr = startElement.getAttributeByName( QName.valueOf( "id" ) );
                            m.setId( attr.getValue() );
                            break;
                        }
                        case "name" : {
                            current = 1;
                            break;
                        }
                        case "pharm" : {
                            current = 2;
                            break;
                        }
                        case "group" : {
                            current = 3;
                            break;
                        }
                        case "analog" : {
                            current = 4;
                            break;
                        }
                        case "version": {
                            v = new Version();
                            attr = startElement.getAttributeByName( QName.valueOf( "type" ) );
                            v.setType( attr.getValue() );
                            break;
                        }
                        case "certificate": {
                            v.setCertificate( new Certificate() );
                            attr = startElement.getAttributeByName( QName.valueOf( "number" ) );
                            v.getCertificate().setNumber( attr.getValue() );
                            attr = startElement.getAttributeByName( QName.valueOf( "dateFrom" ) );
                            Date d = DateHelper.parseDate( attr.getValue() );
                            if ( d != null ) {
                                v.getCertificate().setDateFrom( d );
                            }
                            attr = startElement.getAttributeByName( QName.valueOf( "dateTo" ) );
                            d = DateHelper.parseDate( attr.getValue() );
                            if ( d != null ) {
                                v.getCertificate().setDateTo( d );
                            }
                            break;
                        }
                        case "organization" : {
                            current = 5;
                            break;
                        }
                        case "package" : { 
                            v.setPack( new DrugPackage() );
                            break;
                        }
                        case "packagespace:name" : {
                            current = 6;
                            break;
                        }
                        case "packagespace:amount" : {
                            v.getPack().setAmount( new Amount() );
                            attr = startElement.getAttributeByName( QName.valueOf( "measure" ) );
                            v.getPack().getAmount().setMeasure( attr.getValue() );
                            current = 7;
                            break;
                        }
                        case "packagespace:price" : {
                            v.getPack().setPrice( new Price() );
                            attr = startElement.getAttributeByName( QName.valueOf( "currency" ) );
                            v.getPack().getPrice().setCurrency( attr.getValue() );
                            current = 8;
                            break;
                        }
                        case "dosage" : { 
                            v.setDosage( new Dosage() );
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
                    break;
                }
                // Occurs when the parser meets the text content
                case XMLStreamConstants.CHARACTERS: {
                    Characters characters = event.asCharacters();
                    String nodeValue = characters.getData();
                    if ( nodeValue.isEmpty() ) {
                        break;
                    }
                    switch ( current ) {
                        case 1: {
                            m.setName( nodeValue );
                            break;
                        }
                        case 2: {
                            m.setPharm( nodeValue );
                            break;
                        }
                        case 3: {
                            m.setGroup( PharmalogicalGroup.valueOf( nodeValue ) );
                            break;
                        }
                        case 4: {
                            m.addAnalog( nodeValue );
                            break;
                        }
                        case 5: {
                            v.getCertificate().setOrganization( nodeValue );
                            break;
                        }
                        case 6: {
                            v.getPack().setName( nodeValue );
                            break;
                        }
                        case 7: {
                            v.getPack().getAmount().setValue( Integer.valueOf( nodeValue ) );
                            break;
                        }
                        case 8: {
                            v.getPack().getPrice().setValue( Integer.valueOf( nodeValue ) );
                            break;
                        }
                        case 9: {
                            v.getDosage().setNumber( Integer.valueOf( nodeValue ) );
                            break;
                        }
                        case 10: {
                            v.getDosage().setPeriodicity( Integer.valueOf( nodeValue ) );
                            break;
                        }
                    }
                    current = 0;
                    break;
                }
                // Occurs when the parser meets tag end
                case XMLStreamConstants.END_ELEMENT: {
                    EndElement endElement = event.asEndElement();
                    tagName = endElement.getName().getLocalPart();
                    if ( tagName.equalsIgnoreCase( "medicine" ) ) {                            
                        list.add( m );
                        m = null;
                    }
                    if ( tagName.equalsIgnoreCase( "version" ) ) {                            
                        m.getVersions().add( v );
                        v = null;
                    }
                    break;
                }
            }
        }
        
        return list;
    }
   
}
