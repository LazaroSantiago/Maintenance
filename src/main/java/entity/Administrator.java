package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Administrator() {

    }

    public Administrator(Long id){
        this.id = id;
    }

}
