package org.example.capstone.pojos;

/**
 * Stores all configurable risk factor multipliers and tax rates used in premium calculation
 * for home and auto insurance quotes and policies.
 */
public class RiskFactors {

    /**
     * Discount multiplier when both auto and home are insured.
     * */
    private double discountForBoth;
    private double taxRate;

    /* *
     *  HOME FACTORS
     * */

    private int homeBasePremium;
    private double homeValuePercentage;
    private int homeValueBaseLine;
    private double highLiability;
    private double lowLiability;
    private double homeOldAge;
    private double homeMidAge;
    private double homeNewAge;
    private double heatingOil;
    private double heatingWood;
    private double heatingElectric;
    private double heatingGas;
    private double heatingOther;
    private double rural;
    private double urban;


    /* *
     *  AUTO FACTORS
     * */

    private int autoBasePremium;
    private double driverYoung;
    private double driverOld;
    private double accidentsMany;
    private double accidentsFew;
    private double accidentsNone;
    private double vehicleOld;
    private double vehicleMid;
    private double vehicleNew;

    /**
     * Gets the discount multiplier when both auto and home policies are purchased.
     *
     * @return the discount multiplier.
     */
    public double getDiscountForBoth() {
        return discountForBoth;
    }

    /**
     * Sets the discount multiplier for bundling auto and home policies.
     *
     * @param discountForBoth the discount value to set.
     */
    public void setDiscountForBoth(double discountForBoth) {
        this.discountForBoth = discountForBoth;
    }

    /**
     * Gets the current tax rate applied to premiums.
     *
     * @return the tax rate.
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the tax rate to apply to premiums.
     *
     * @param taxRate the tax rate to set.
     */
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /* *
     *  HOME FACTOR METHODS
     * */

    /**
     * Gets the base premium for home insurance.
     *
     * @return the base premium.
     */
    public int getHomeBasePremium() {
        return homeBasePremium;
    }

    /**
     * Sets the base premium for home insurance.
     *
     * @param homeBasePremium the value to set.
     */
    public void setHomeBasePremium(int homeBasePremium) {
        this.homeBasePremium = homeBasePremium;
    }

    /**
     * Gets the percentage applied to home values above the baseline.
     *
     * @return the value percentage.
     */
    public double getHomeValuePercentage() {
        return homeValuePercentage;
    }

    /**
     * Gets the percentage applied to home values above the baseline.
     *
     * @return the value percentage.
     */
    public void setHomeValuePercentage(double homeValuePercentage) {
        this.homeValuePercentage = homeValuePercentage;
    }

    /**
     * Gets the value baseline threshold for applying extra charges.
     *
     * @return the baseline amount.
     */
    public int getHomeValueBaseLine() {
        return homeValueBaseLine;
    }

    /**
     * Sets the value baseline threshold.
     *
     * @param homeValueBaseLine the baseline to set.
     */
    public void setHomeValueBaseLine(int homeValueBaseLine) {
        this.homeValueBaseLine = homeValueBaseLine;
    }

    /**
     * Gets the multiplier for high liability policies.
     *
     * @return the multiplier.
     */
    public double getHighLiability() {
        return highLiability;
    }

    /**
     * Sets the multiplier for high liability policies.
     *
     * @param highLiability the multiplier to set.
     */
    public void setHighLiability(double highLiability) {
        this.highLiability = highLiability;
    }

    /**
     * Gets the multiplier for low liability policies.
     * @return the multiplier
     */
    public double getLowLiability() {
        return lowLiability;
    }

    /**
     * Sets the multiplier for low liability policies.
     * @param lowLiability the multiplier to set
     */
    public void setLowLiability(double lowLiability) {
        this.lowLiability = lowLiability;
    }

    /**
     * Gets the multiplier for old homes.
     * @return the multiplier
     */
    public double getHomeOldAge() {
        return homeOldAge;
    }

    /**
     * Sets the multiplier for old homes.
     * @param homeOldAge the multiplier to set
     */
    public void setHomeOldAge(double homeOldAge) {
        this.homeOldAge = homeOldAge;
    }

    /**
     * Gets the multiplier for mid-aged homes.
     * @return the multiplier
     */
    public double getHomeMidAge() {
        return homeMidAge;
    }

    /**
     * Sets the multiplier for mid-aged homes.
     * @param homeMidAge the multiplier to set
     */
    public void setHomeMidAge(double homeMidAge) {
        this.homeMidAge = homeMidAge;
    }

    /**
     * Gets the multiplier for new homes.
     * @return the multiplier
     */
    public double getHomeNewAge() {
        return homeNewAge;
    }

    /**
     * Sets the multiplier for new homes.
     * @param homeNewAge the multiplier to set
     */
    public void setHomeNewAge(double homeNewAge) {
        this.homeNewAge = homeNewAge;
    }

    /**
     * Gets the multiplier for homes heated with oil.
     * @return the multiplier
     */
    public double getHeatingOil() {
        return heatingOil;
    }

    /**
     * Sets the multiplier for oil-heated homes.
     * @param heatingOil the multiplier to set
     */
    public void setHeatingOil(double heatingOil) {
        this.heatingOil = heatingOil;
    }

    /**
     * Gets the multiplier for homes heated with wood.
     * @return the multiplier
     */
    public double getHeatingWood() {
        return heatingWood;
    }

    /**
     * Sets the multiplier for wood-heated homes.
     * @param heatingWood the multiplier to set
     */
    public void setHeatingWood(double heatingWood) {
        this.heatingWood = heatingWood;
    }

    /**
     * Gets the multiplier for homes heated with electricity.
     * @return the multiplier
     */
    public double getHeatingElectric() {
        return heatingElectric;
    }

    /**
     * Sets the multiplier for electrically-heated homes.
     * @param heatingElectric the multiplier to set
     */
    public void setHeatingElectric(double heatingElectric) {
        this.heatingElectric = heatingElectric;
    }

    /**
     * Gets the multiplier for homes heated with gas.
     * @return the multiplier
     */
    public double getHeatingGas() {
        return heatingGas;
    }

    /**
     * Sets the multiplier for gas-heated homes.
     * @param heatingGas the multiplier to set
     */
    public void setHeatingGas(double heatingGas) {
        this.heatingGas = heatingGas;
    }

    /**
     * Gets the multiplier for homes using other heating types.
     * @return the multiplier
     */
    public double getHeatingOther() {
        return heatingOther;
    }

    /**
     * Sets the multiplier for other heating types.
     * @param heatingOther the multiplier to set
     */
    public void setHeatingOther(double heatingOther) {
        this.heatingOther = heatingOther;
    }

    /**
     * Gets the multiplier for rural homes.
     * @return the multiplier
     */
    public double getRural() {
        return rural;
    }

    /**
     * Sets the multiplier for rural homes.
     * @param rural the multiplier to set
     */
    public void setRural(double rural) {
        this.rural = rural;
    }

    /**
     * Gets the multiplier for urban homes.
     * @return the multiplier
     */
    public double getUrban() {
        return urban;
    }

    /**
     * Sets the multiplier for urban homes.
     * @param urban the multiplier to set
     */
    public void setUrban(double urban) {
        this.urban = urban;
    }

    /* *
     *  AUTO FACTOR METHODS
     * */

    /**
     * Gets the base premium for auto insurance.
     * @return the base premium
     */
    public int getAutoBasePremium() {
        return autoBasePremium;
    }

    /**
     * Sets the base premium for auto insurance.
     * @param autoBasePremium the value to set
     */
    public void setAutoBasePremium(int autoBasePremium) {
        this.autoBasePremium = autoBasePremium;
    }

    /**
     * Gets the multiplier for young drivers.
     * @return the multiplier
     */
    public double getDriverYoung() {
        return driverYoung;
    }

    /**
     * Sets the multiplier for young drivers.
     * @param driverYoung the multiplier to set
     */
    public void setDriverYoung(double driverYoung) {
        this.driverYoung = driverYoung;
    }

    /**
     * Gets the multiplier for old drivers.
     * @return the multiplier
     */
    public double getDriverOld() {
        return driverOld;
    }

    /**
     * Sets the multiplier for old drivers.
     * @param driverOld the multiplier to set
     */
    public void setDriverOld(double driverOld) {
        this.driverOld = driverOld;
    }

    /**
     * Gets the multiplier for customers with many accidents.
     * @return the multiplier
     */
    public double getAccidentsMany() {
        return accidentsMany;
    }

    /**
     * Sets the multiplier for customers with many accidents.
     * @param accidentsMany the multiplier to set
     */
    public void setAccidentsMany(double accidentsMany) {
        this.accidentsMany = accidentsMany;
    }

    /**
     * Gets the multiplier for customers with few accidents.
     * @return the multiplier
     */
    public double getAccidentsFew() {
        return accidentsFew;
    }

    /**
     * Sets the multiplier for customers with few accidents.
     * @param accidentsFew the multiplier to set
     */
    public void setAccidentsFew(double accidentsFew) {
        this.accidentsFew = accidentsFew;
    }

    /**
     * Gets the multiplier for customers with no accident history.
     * @return the multiplier
     */
    public double getAccidentsNone() {
        return accidentsNone;
    }

    /**
     * Sets the multiplier for customers with no accident history.
     * @param accidentsNone the multiplier to set
     */
    public void setAccidentsNone(double accidentsNone) {
        this.accidentsNone = accidentsNone;
    }

    /**
     * Gets the multiplier for older vehicles.
     * @return the multiplier
     */
    public double getVehicleOld() {
        return vehicleOld;
    }

    /**
     * Sets the multiplier for older vehicles.
     * @param vehicleOld the multiplier to set
     */
    public void setVehicleOld(double vehicleOld) {
        this.vehicleOld = vehicleOld;
    }

    /**
     * Gets the multiplier for mid-aged vehicles.
     * @return the multiplier
     */
    public double getVehicleMid() {
        return vehicleMid;
    }

    /**
     * Sets the multiplier for mid-aged vehicles.
     * @param vehicleMid the multiplier to set
     */
    public void setVehicleMid(double vehicleMid) {
        this.vehicleMid = vehicleMid;
    }

    /**
     * Gets the multiplier for new vehicles.
     * @return the multiplier
     */
    public double getVehicleNew() {
        return vehicleNew;
    }

    /**
     * Sets the multiplier for new vehicles.
     * @param vehicleNew the multiplier to set
     */
    public void setVehicleNew(double vehicleNew) {
        this.vehicleNew = vehicleNew;
    }

    /**
     * Loads default values for all risk factors and premiums used in premium calculations.
     * Should be used to initialize the system with standard values.
     */
    public void loadDefaultValues () {
        this.discountForBoth = 0.9;
        this.taxRate = 0.15;

        // Home Values
        this.homeBasePremium = 500;
        this.homeValuePercentage = 0.002;
        this.homeValueBaseLine = 250000;
        this.highLiability = 1.25;
        this.lowLiability = 1;
        this.homeOldAge = 1.5;
        this.homeMidAge = 1.25;
        this.homeNewAge = 1;
        this.heatingOil = 2;
        this.heatingWood = 1.25;
        this.heatingElectric = 1;
        this.heatingGas = 1;
        this.heatingOther = 1;
        this.rural = 1.15;
        this.urban = 1;

        // Auto Values
        this.autoBasePremium = 750;
        this.driverYoung = 2;
        this.driverOld = 1;
        this.accidentsMany = 2.5;
        this.accidentsFew = 1.25;
        this.accidentsNone = 1;
        this.vehicleOld = 2;
        this.vehicleMid = 1.5;
        this.vehicleNew = 1;
    }
}
