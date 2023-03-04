package com.nure.kravchenko.student.reference.entity.app;

public enum Permission {

    STUDENTS_PERMISSION("students_permission"),
    WORKER_PERMISSION("worker_permission"),
    ADMIN_PERMISSION("admin_permission");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
