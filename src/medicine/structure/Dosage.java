/*
 * Dosage
 *
 * Version 1
 */
package medicine.structure;

/**
 * Describes the size or frequency of the dose of a medicine or drug
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class Dosage {
    private Integer number;         // the amount of the drug
    private Integer periodicity;    // the frequency of taking the medicine

    /**
     * @return the number   the amount of the drug
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number the amount of the drug to set
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return the frequency of taking the medicine
     */
    public Integer getPeriodicity() {
        return periodicity;
    }

    /**
     * @param periodicity the frequency of taking the medicine to set
     */
    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }

    @Override
    public String toString() {
        String str = "Dosage: " 
                + getNumber() + " portions(s) "
                + getPeriodicity() + " time(s)";
        return str;
    }
    
    
}
