/*
 * Version
 *
 * Version 1
 */
package medicine.structure;

/**
 * Describes medicine producing variants with theirs characteristics
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class Version {
    private String type;                // version type
    private Certificate certificate;    // medicine certificate
    private DrugPackage pack;           // medicine package
    private Dosage dosage;              

    /**
     * @return the version type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the version type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the certificate
     */
    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * @param certificate the certificate to set
     */
    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    /**
     * @return the package
     */
    public DrugPackage getPack() {
        return pack;
    }

    /**
     * @param pack the package to set
     */
    public void setPack(DrugPackage pack) {
        this.pack = pack;
    }

    /**
     * @return the dosage
     */
    public Dosage getDosage() {
        return dosage;
    }

    /**
     * @param dosage the dosage to set
     */
    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        String str = "Version: " + getType();
        if ( getCertificate() != null ) {
            str += "\n\t" + getCertificate().toString();
        }
        if ( getPack() != null ) {
            str += "\n\t" + getPack().toString();
        }
        if ( getDosage() != null ) {
            str += "\n\t" + getDosage().toString();
        }
        return str;
    }
    
    
}
