package com.akash.authSecurityPermission.enums;

/**
 * @author Akash Kumar
 */

public enum LeaveType {
    ANNUAL_LEAVE("Annual Leave"),
    MEDICAL_LEAVE("Medical Leave"),
    UNPAID_LEAVE("Unpaid Leave"),
    COMP_OFF("Compensatory Off"),
    PUBLIC_HOLIDAY("Public Holiday"),
    MATERNITY_LEAVE("Maternity Leave"),
    PATERNITY_LEAVE("Paternity Leave"),
    EMERGENCY_LEAVE("Emergency Leave");

    private final String displayName;

    LeaveType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
