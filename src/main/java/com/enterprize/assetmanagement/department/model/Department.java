package com.enterprize.assetmanagement.department.model;

import com.enterprize.assetmanagement.asset.model.Asset;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.metamodel.model.domain.internal.AbstractIdentifiableType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name ="departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , length = 100)
    private  String name;
    @Column(nullable = false , length = 20 ,unique =true)
    private String code;

    @OneToMany(mappedBy = "department" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @Builder.Default
    private List<Asset> assets = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false , updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name="updated_at" , nullable = false)
    private Instant updatedAt;


}
