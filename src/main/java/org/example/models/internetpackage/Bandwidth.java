package org.example.models.internetpackage;

public enum Bandwidth {
    FLAT("FLAT"), GB1("1GB"), GB2("2GB"), GB5("5GB"), GB10("10GB"), GB100("100GB");

    private String toString;
    private Bandwidth(String string){
        this.toString = string;
    }
    public String numberOfGygabytes(){
        if(this.equals(FLAT)) return "FLAT";

        final int NUMBER_OF_DIGITS = this.toString().length() - "GB".length();
        return this.toString().substring(0, NUMBER_OF_DIGITS);
    }

    public String getBandwidthWithMeasurementUnit(){
        if(this.equals(FLAT)) return FLAT.toString();

        final int NUMBER_OF_DIGITS = this.toString().length() - "GB".length();
        return this.toString().substring(0, NUMBER_OF_DIGITS) + " GB";
    }

    @Override
    public String toString(){
        return toString;
    }
}
