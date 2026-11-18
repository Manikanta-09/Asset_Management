package com.enterprize.assetmanagement.asset.model;

import java.time.Instant;

import com.enterprize.assetmanagement.department.model.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "assets")
@Getter
@Setter
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Builder 
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , length =100)
    private String name;
    @Column(name = "serial_number", nullable = false, unique = true, length = 50)
    private String serialNumber;
    @Column(name = "asset_type", nullable = false, length = 50)
    private String assetType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AssetStatus status;
    @Version
    @Column(nullable = false)
    private Long version;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
