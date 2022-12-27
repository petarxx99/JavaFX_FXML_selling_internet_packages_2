package org.example.models.internetpackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.ValidOrInvalidData;
import org.example.util.Util;



import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InternetPackage {
    public InternetPackage() {
        reset();
    }

    public InternetPackage(String firstName, String lastName, String address,
                            int internetSpeedInMBPS, Bandwidth bandwidth, int contractDurationInMonths) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setBandwidth(bandwidth);
        setInternetSpeedInMegabytesPerSecond(internetSpeedInMBPS);
        setContractDurationInMonths(contractDurationInMonths);
    }

    public InternetPackage copy() {
        return new InternetPackage(getFirstName(),
                getLastName(),
                getAddress(),
                getInternetSpeedInMegabytesPerSecond(),
                getBandwidth(),
                getContractDurationInMonths());
    }

    private static final int INVALID_INTERNET_SPEED = -1;
    private static final int INVALID_CONTRACT_DURATION = -1;
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final IntegerProperty internetSpeedInMegabytesPerSecond = new SimpleIntegerProperty(INVALID_INTERNET_SPEED);
    private final ObjectProperty<Bandwidth> bandwidth = new SimpleObjectProperty<>(null);
    private final IntegerProperty contractDurationInMonths = new SimpleIntegerProperty(INVALID_CONTRACT_DURATION);



    public int getContractDurationInMonths() {
        return contractDurationInMonths.get();
    }

    public IntegerProperty contractDurationInMonthsProperty() {
        return contractDurationInMonths;
    }

    public void setContractDurationInMonths(int contractDurationInMonths) {
        this.contractDurationInMonths.set(contractDurationInMonths);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getInternetSpeedInMegabytesPerSecond() {
        return internetSpeedInMegabytesPerSecond.get();
    }

    public IntegerProperty internetSpeedInMegabytesPerSecondProperty() {
        return internetSpeedInMegabytesPerSecond;
    }

    public void setInternetSpeedInMegabytesPerSecond(int internetSpeedInMegabytesPerSecond) {
        this.internetSpeedInMegabytesPerSecond.set(internetSpeedInMegabytesPerSecond);
    }

    public Bandwidth getBandwidth() {
        return bandwidth.get();
    }

    public ObjectProperty<Bandwidth> bandwidthProperty() {
        return bandwidth;
    }

    public void setBandwidth(Bandwidth bandwidth) {
        this.bandwidth.set(bandwidth);
    }

    public void resetInternetSpeedInMegabytesPerSecond() {
        setInternetSpeedInMegabytesPerSecond(INVALID_INTERNET_SPEED);
    }

    public void resetContractDurationInMonths() {
        setContractDurationInMonths(INVALID_CONTRACT_DURATION);
    }


    @Override
    public String toString() {
        List<String> data = getData();
        StringBuilder sb = new StringBuilder();
        data.forEach(string -> sb.append(string + "\n"));
        return sb.toString();
    }

    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("first name: " + getFirstName());
        data.add("last name: " + getLastName());
        data.add("address: " + getAddress());
        data.add("internet speed in MBPS: " + getInternetSpeedInMegabytesPerSecond());
        data.add("bandwidth: " + getBandwidth().numberOfGygabytes() + " GB");
        data.add("contract duration: " + getContractDurationInMonths() / 12.0 + " years");

        return data;
    }

    public void reset() {
        firstName.set("");
        lastName.set("");
        address.set("");
        resetInternetSpeedInMegabytesPerSecond();
        resetBandwidth();
        resetContractDurationInMonths();
    }

    public void resetBandwidth() {
        bandwidth.set(null);
    }

    public ValidOrInvalidData isValid() {
        if (Util.stringIsEitherNullOrBlank(getFirstName())) {
            return new ValidOrInvalidData(false, "First name is invalid.");
        }
        if (Util.stringIsEitherNullOrBlank(getLastName())) {
            return new ValidOrInvalidData(false, "Last name is invalid.");
        }
        if (Util.stringIsEitherNullOrBlank(getAddress())) {
            return new ValidOrInvalidData(false, "Address is invalid.");
        }
        if (getInternetSpeedInMegabytesPerSecond() == INVALID_INTERNET_SPEED) {
            return new ValidOrInvalidData(false, "Error with internet speed.");
        }
        if (getBandwidth() == null) {
            return new ValidOrInvalidData(false, "Error with bandwidth.");
        }
        if (getContractDurationInMonths() == INVALID_CONTRACT_DURATION) {
            return new ValidOrInvalidData(false, "Error with contract duration.");
        }

        return new ValidOrInvalidData(true, null);
    }


    public boolean matchesThisValidPackage(InternetPackage validPackage) {
        if(!getFirstName().isBlank()){
            if(Util.trimmedStringsAreNOTEqual(getFirstName(), validPackage.getFirstName())) return false;
        }
        if(!getLastName().isBlank()){
            if(Util.trimmedStringsAreNOTEqual(getLastName(), validPackage.getLastName())) return false;
        }
        if(!getAddress().isBlank()){
            if(Util.trimmedStringsAreNOTEqual(getAddress(), validPackage.getAddress())) return false;
        }

        if(getBandwidth() != null){
            if(!getBandwidth().equals(validPackage.getBandwidth())) return false;
        }
        if(getContractDurationInMonths() != INVALID_CONTRACT_DURATION){
            if(getContractDurationInMonths() != validPackage.getContractDurationInMonths()) return false;
        }
        if(getInternetSpeedInMegabytesPerSecond() != INVALID_INTERNET_SPEED){
            if(getInternetSpeedInMegabytesPerSecond() != validPackage.getInternetSpeedInMegabytesPerSecond()) return false;
        }

        return true;
    }


}
