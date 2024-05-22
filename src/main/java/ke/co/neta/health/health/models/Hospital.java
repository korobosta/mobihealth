package ke.co.neta.health.health.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name="")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name",length = 100)
    private String name;

    @Column(name="email",length = 100,nullable = false)
    private String email;

    @Column(name="phone_number",length = 100,nullable = false)
    private String phoneNumber;

    @Column(name="latitude",length = 100,nullable = false)
    private String latitude;

    @Column(name="longitude",length = 100,nullable = false)
    private String longitude;

    @Column(name="pricise_location",length = 100,nullable = false)
    private String priciseLocation;

    @Column(name="location_description",length = 100,nullable = false)
    private String locationDescription;

    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;

}
