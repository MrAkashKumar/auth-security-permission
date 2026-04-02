package com.akash.authSecurityPermission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Akash Kumar
 */
@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEntity extends BaseEntity {

    @Column(nullable = false)
    private String companyName;

    @Column
    private String logoUrl;

    @Column
    private String managerName;

    @Column
    private String managerEmail;

    @Column
    private String location;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String website;

    @Column
    private String industry;

    @Column
    private String taxId;

    @Column
    private String contractNumber;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
