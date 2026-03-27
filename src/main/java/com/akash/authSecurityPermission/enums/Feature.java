package com.akash.authSecurityPermission.enums;

public enum Feature {
    // User Management
    USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE,
    // Role & Permission Management
    ROLE_MANAGE, PERMISSION_MANAGE,
    // Claim Management
    CLAIM_CREATE, CLAIM_READ, CLAIM_UPDATE, CLAIM_APPROVE, CLAIM_REJECT,
    // Timesheet
    TIMESHEET_CREATE, TIMESHEET_READ, TIMESHEET_APPROVE,
    // Leave Management
    LEAVE_APPLY, LEAVE_READ, LEAVE_APPROVE, LEAVE_REJECT,
    // Client Management
    CLIENT_CREATE, CLIENT_READ, CLIENT_UPDATE, CLIENT_DELETE,
    // Dashboard
    DASHBOARD_VIEW, REPORTS_VIEW,
    // System Config
    SYSTEM_CONFIG
}
