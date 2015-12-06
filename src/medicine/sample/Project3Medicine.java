/*
 * Medicine
 *
 * Version 1
 */
package medicine.sample;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import medicine.parsers.MedicineParserDOM;
import medicine.parsers.MedicineParserSAX;
import medicine.parsers.MedicineParserStAX;
import medicine.structure.Medicine;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 *
 * @author Tanya Domchenko
 */
public class Project3Medicine {
    
    /**
     * Sorting medicine by the groups names
     */
    static class SortByGroupsComparator implements Comparator<Medicine> {

        @Override
        public int compare(Medicine o1, Medicine o2) {
            int res = o1.getGroup().toString().compareToIgnoreCase( 
                    o2.getGroup().toString() );
            if ( res == 0 ) {
                return o1.getName().compareToIgnoreCase( o2.getName() );
            }
            return res;
        }        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String srcPath = "resources\\";            
        String xmlPath = srcPath + "\\medicine_data.xml";
            
        System.out.println( "Validating xml and schemes..." );
        if ( validateXmlSAX(
                xmlPath, 
                srcPath + "\\medicine.xsd",
                srcPath + "\\packagespace.xsd" ) ) {
            System.out.println( "Ok" );
        }
        else {
            System.out.println( "\nSorry, xml/xsd file is not well formed and/or valid\"" );
            return;
        }
        
        System.out.println( "\nTransforming xml to htm..." );
        System.out.println( transformToHTML( xmlPath, srcPath + "\\medicine.xsl" ) );
        
        try {
            System.out.println( "\nSAX parser:" );            
            // load xml by SAX parser
            SAXParser parserSAX = SAXParserFactory.newInstance().newSAXParser();            
            MedicineParserSAX myParserSAX = new MedicineParserSAX();            
            parserSAX.parse( srcPath + "\\medicine_data.xml", myParserSAX );
            List<Medicine> results = myParserSAX.getList();
            print( results );            
            
            System.out.println( "\n\nDOM parser:" );
            // load xml by DOM parser
            results = MedicineParserDOM.parse( srcPath + "\\medicine_data.xml" );
            print( results );
            
            System.out.println( "\n\nStAX parser:" );
            // load xml by StAX parser
            results = MedicineParserStAX.parse( srcPath + "\\medicine_data.xml" );
            print( results );
            
        } catch (ParserConfigurationException | SAXException | IOException | XMLStreamException ex) {
            System.out.println( ex.getMessage() );
        }
    }
    
    /**
     * Checks both xml file and its xsd schemes
     * 
     * @param xmlPath   xml file path
     * @param xsdPath   schemes files paths
     * @return          true if file and schemes are valid, false - formed with errors
     */
    public static boolean validateXmlSAX( String xmlPath, String ... xsdPath ) {
        Source[] schemes = new Source[ xsdPath.length ];
        for ( int i = 0; i < xsdPath.length; i++ ) {
            schemes[i] = new StreamSource( new File( xsdPath[i] ) );
        }   
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating( false ); 
        factory.setNamespaceAware( true );

        SchemaFactory schemaFactory = 
                SchemaFactory.newInstance( "http://www.w3.org/2001/XMLSchema" );
        
        // xsd
        SAXParser parser = null;
        try {
           factory.setSchema( schemaFactory.newSchema( schemes ) );
           parser = factory.newSAXParser();
        }
        catch (SAXException ex) {
            System.out.println( "Scheme validation: " + ex.getMessage() );
            return false;
        } catch (ParserConfigurationException ex) {
            System.out.println( "Configuration error: " + ex.getMessage() );
            return false;
        }
        
        // xml
        try {
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler( new ErrorHandler() {

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println( "Warning: " + exception.getMessage() );
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    System.out.println( "Error: " + exception.getMessage() );
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    System.out.println( "Fatal error: " + exception.getMessage() );
                    throw exception;
                }
            } );
            reader.parse( new InputSource( xmlPath ) );            
        } catch (SAXException ex ) {
            System.out.println( ex.getMessage() );
            return false;
        } catch (IOException ex) {
            System.out.println( "Validation error: " + ex.getMessage() );
            return false;
        }
        return true;
    }
    
    public static String transformToHTML( String xmlPath, String xslPath ) {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer(
                    new StreamSource( xslPath ) );
            String htmlPath = xmlPath.replace( ".xml", ".htm" );
            transformer.transform(
                    new StreamSource( xmlPath ),
                    new StreamResult( htmlPath ) );
            File f = new File( htmlPath );
            if ( f.length() > 0 ) {
                return htmlPath;
            }
        } catch (TransformerConfigurationException ex) {
            System.out.println( ex.getMessage() );
        } catch (TransformerException ex) {
            System.out.println( ex.getMessage() );
        }
        return "";
    }
    
    /**
     * Prints data loaded from xml file
     * @param list  medicine list
     */
    public static void print( List<Medicine> list ) {
        SortByGroupsComparator comparator = new SortByGroupsComparator();
        list.sort( comparator );
        for ( Medicine m: list ) {
            System.out.println( m.toString() );
        }
    }
}
