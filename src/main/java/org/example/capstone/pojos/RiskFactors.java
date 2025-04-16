package org.example.capstone.pojos;

public class RiskFactors {
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

    public double getDiscountForBoth() {
        return discountForBoth;
    }

    public void setDiscountForBoth(double discountForBoth) {
        this.discountForBoth = discountForBoth;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /* *
     *  HOME FACTOR METHODS
     * */

    public int getHomeBasePremium() {
        return homeBasePremium;
    }

    public void setHomeBasePremium(int homeBasePremium) {
        this.homeBasePremium = homeBasePremium;
    }

    public double getHomeValuePercentage() {
        return homeValuePercentage;
    }

    public void setHomeValuePercentage(double homeValuePercentage) {
        this.homeValuePercentage = homeValuePercentage;
    }

    public int getHomeValueBaseLine() {
        return homeValueBaseLine;
    }

    public void setHomeValueBaseLine(int homeValueBaseLine) {
        this.homeValueBaseLine = homeValueBaseLine;
    }

    public double getHighLiability() {
        return highLiability;
    }

    public void setHighLiability(double highLiability) {
        this.highLiability = highLiability;
    }

    public double getLowLiability() {
        return lowLiability;
    }

    public void setLowLiability(double lowLiability) {
        this.lowLiability = lowLiability;
    }

    public double getHomeOldAge() {
        return homeOldAge;
    }

    public void setHomeOldAge(double homeOldAge) {
        this.homeOldAge = homeOldAge;
    }

    public double getHomeMidAge() {
        return homeMidAge;
    }

    public void setHomeMidAge(double homeMidAge) {
        this.homeMidAge = homeMidAge;
    }

    public double getHomeNewAge() {
        return homeNewAge;
    }

    public void setHomeNewAge(double homeNewAge) {
        this.homeNewAge = homeNewAge;
    }

    public double getHeatingOil() {
        return heatingOil;
    }

    public void setHeatingOil(double heatingOil) {
        this.heatingOil = heatingOil;
    }

    public double getHeatingWood() {
        return heatingWood;
    }

    public void setHeatingWood(double heatingWood) {
        this.heatingWood = heatingWood;
    }

    public double getHeatingElectric() {
        return heatingElectric;
    }

    public void setHeatingElectric(double heatingElectric) {
        this.heatingElectric = heatingElectric;
    }

    public double getHeatingGas() {
        return heatingGas;
    }

    public void setHeatingGas(double heatingGas) {
        this.heatingGas = heatingGas;
    }

    public double getHeatingOther() {
        return heatingOther;
    }

    public void setHeatingOther(double heatingOther) {
        this.heatingOther = heatingOther;
    }

    public double getRural() {
        return rural;
    }

    public void setRural(double rural) {
        this.rural = rural;
    }

    public double getUrban() {
        return urban;
    }

    public void setUrban(double urban) {
        this.urban = urban;
    }

    /* *
     *  AUTO FACTOR METHODS
     * */

    public int getAutoBasePremium() {
        return autoBasePremium;
    }

    public void setAutoBasePremium(int autoBasePremium) {
        this.autoBasePremium = autoBasePremium;
    }

    public double getDriverYoung() {
        return driverYoung;
    }

    public void setDriverYoung(double driverYoung) {
        this.driverYoung = driverYoung;
    }

    public double getDriverOld() {
        return driverOld;
    }

    public void setDriverOld(double driverOld) {
        this.driverOld = driverOld;
    }

    public double getAccidentsMany() {
        return accidentsMany;
    }

    public void setAccidentsMany(double accidentsMany) {
        this.accidentsMany = accidentsMany;
    }

    public double getAccidentsFew() {
        return accidentsFew;
    }

    public void setAccidentsFew(double accidentsFew) {
        this.accidentsFew = accidentsFew;
    }

    public double getAccidentsNone() {
        return accidentsNone;
    }

    public void setAccidentsNone(double accidentsNone) {
        this.accidentsNone = accidentsNone;
    }

    public double getVehicleOld() {
        return vehicleOld;
    }

    public void setVehicleOld(double vehicleOld) {
        this.vehicleOld = vehicleOld;
    }

    public double getVehicleMid() {
        return vehicleMid;
    }

    public void setVehicleMid(double vehicleMid) {
        this.vehicleMid = vehicleMid;
    }

    public double getVehicleNew() {
        return vehicleNew;
    }

    public void setVehicleNew(double vehicleNew) {
        this.vehicleNew = vehicleNew;
    }

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
