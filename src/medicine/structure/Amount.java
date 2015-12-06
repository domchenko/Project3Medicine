/*
 * Amount
 *
 * Version 1
 */
package medicine.structure;

/**
 * Describes the amount of the drug or medicine in the package
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class Amount {
    private Integer value;  // amount
    private String measure; // measure name

    /**
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * @return the measure 
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * @param measure the measure to set
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        String str = Integer.toString( getValue() ) + " " + getMeasure();
        return str; 
    }
 
    
}
