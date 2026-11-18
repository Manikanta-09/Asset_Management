package com.enterprize.assetmanagement.employee.model;


import com.enterprize.assetmanagement.department.model.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Audited;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="first_name", nullable = false,length = 50)
    private String firstName;

    @Column(name="last_name" , nullable = false,length = 50)
    private String lastName;

    @Column(nullable = false,unique = true,length = 20)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id",nullable = false)
    private Department department;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
