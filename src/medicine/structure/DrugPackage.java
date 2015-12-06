/*
 * DrugPackage
 *
 * Version 1
 */
package medicine.structure;

/**
 * Describes the packaging/container of the medicine
 * 
 * @author Tanya Domchenko
 * @version 1, 04 Dec 2015
 */
public class DrugPackage {
    private String name;    // the name of the packaging
    private Amount amount;  // the amount of the drug in this package 
    private Price price;    // the price of the drug in this package

    /**
     * @return the name of the medicine or drug
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of medicine or drug to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the amount of the drug in the package
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * @param amount the amount of the drug to set
     */
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    /**
     * @return the price of the drug in this package
     */
    public Price getPrice() {
        return price;
    }

    /**
     * @param price the price of the drug to set
     */
    public void setPrice(Price price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        String str = "Package: " + getName();
        if ( getAmount() != null ) {
            str += "; amount: " + getAmount().toString();
        }
        if ( getPrice() != null ) {
            str += "; price: " + getPrice().toString();
        }
        return str; 
    }
}
