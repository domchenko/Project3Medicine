/*
 * Price
 *
 * Version 1
 */
package medicine.structure;

/**
 * Describes the price of the drug or medicine
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class Price {
    private Integer value;      // price
    private String currency;    // currency code

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
     * @return the currency code
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency code to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        String str = Integer.toString( getValue() ) + " " + getCurrency();
        return str; 
    }
}
