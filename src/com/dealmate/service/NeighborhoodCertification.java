package com.dealmate.service;

public class NeighborhoodCertification {
    private String location;
    private String neighborhood;
    private boolean certificationStatus;

    public boolean requestCertification(String location) {
        this.location = location;
        certificationStatus = verifyLocation(location);
        if (certificationStatus) {
            this.neighborhood = location;
        }
        return certificationStatus;
    }

    public boolean verifyLocation(String location) {
        return location != null && !location.trim().isEmpty();
    }

    public void saveNeighborhood(String userId, String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
