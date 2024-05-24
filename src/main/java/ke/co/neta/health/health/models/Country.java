package ke.co.neta.health.health.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name="countries")
public class Country {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name",length = 100,nullable = false,unique=true)
    private String name;

    @Column(name="nicename",length = 100,nullable = false,unique=true)
    private String niceName;

    @Column(name="phonecode",length = 5,nullable = false,unique=true)
    private int phoneCode;

    @Column(name="numcode",length = 5,nullable = false,unique=true)
    private int numCode;

    @Column(name="iso",length = 2,nullable = false,unique=true)
    private String iso;

    @Column(name="iso3",length = 3,nullable = false,unique=true)
    private String iso3;

    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;

    public void setName(String name){
        this.name=name;
    }

    public void setNiceName(String niceName){
        this.niceName = niceName;
    }

    public void setPhoneCode(int phoneCode){
        this.phoneCode = phoneCode;
    }

    public void setNumCode(int numCode){
        this.numCode = numCode;
    }

    public void setIso(String iso){
        this.iso = iso;
    }

    public void setIso3(String iso3){
        this.iso3 = iso3;
    }
}
