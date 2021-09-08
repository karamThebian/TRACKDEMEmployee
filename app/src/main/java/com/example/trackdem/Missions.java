package com.example.trackdem;

public class Missions {
    private String MissionId;
    private String LocationFrom;
    private String LocationTo;
    private String Description;
    private String Status;
    private String DriverName;
    private String AssistantName;
    private String GPSLocation;
    private String Notes;
    private String ConfirmedShipment;
    private String Departed;
    private String Arrival;
    private String Delivered;
    private String UnitAvailable;
    private String CancelStatus;


    public Missions(String missionId, String locationFrom, String locationTo, String description, String status, String driverName, String assistantName, String GPSLocation, String notes, String confirmedShipment,String departed,String delivered, String arrival, String unitAvailable,String cancelStatus) {
        MissionId = missionId;
        LocationFrom = locationFrom;
        LocationTo = locationTo;
        Description = description;
        Status = status;
        DriverName = driverName;
        AssistantName = assistantName;
        this.GPSLocation = GPSLocation;
        Notes = notes;
        ConfirmedShipment = confirmedShipment;
        Arrival=arrival;
        Delivered=delivered;
        Departed=departed;
        UnitAvailable=unitAvailable;
        CancelStatus=cancelStatus;
    }

    public String getCancelStatus() {
        return CancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        CancelStatus = cancelStatus;
    }

    public String getDeparted() {
        return Departed;
    }

    public void setDeparted(String departed) {
        Departed = departed;
    }

    public String getArrival() {
        return Arrival;
    }

    public void setArrival(String arrival) {
        Arrival = arrival;
    }

    public String getDelivered() {
        return Delivered;
    }

    public void setDelivered(String delivered) {
        Delivered = delivered;
    }

    public String getUnitAvailable() {
        return UnitAvailable;
    }

    public void setUnitAvailable(String unitAvailable) {
        UnitAvailable = unitAvailable;
    }

    public String getMissionId() {
        return MissionId;
    }

    public void setMissionId(String missionId) {
        MissionId = missionId;
    }

    public String getLocationFrom() {
        return LocationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        LocationFrom = locationFrom;
    }

    public String getLocationTo() {
        return LocationTo;
    }

    public void setLocationTo(String locationTo) {
        LocationTo = locationTo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getAssistantName() {
        return AssistantName;
    }

    public void setAssistantName(String assistantName) {
        AssistantName = assistantName;
    }

    public String getGPSLocation() {
        return GPSLocation;
    }

    public void setGPSLocation(String GPSLocation) {
        this.GPSLocation = GPSLocation;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getConfirmedShipment() {
        return ConfirmedShipment;
    }

    public void setConfirmedShipment(String confirmedShipment) {
        ConfirmedShipment = confirmedShipment;
    }
}
