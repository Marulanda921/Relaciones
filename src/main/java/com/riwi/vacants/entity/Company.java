package com.riwi.vacants.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity (name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column (length = 40,nullable = false)
    private String name;
    @Column (length = 60,nullable = false)
    private String location;
    @Column (length = 15,nullable = false)
    private String contact;

    //@OneToMany (uno a muchos) una empresa puede tener muchas vacantes
    //mappedBy: debemos especificar donde o en que propiedad se esta mapeando la otra entidad
    //cascade.All: especificamos el tipo de cascada, All quiere decir que tendra todos los tipos de cascada
    //orphanRemoval: especifica que un objeto huerfano (sin llave foranea) sera elimimna¡¡
    @OneToMany(
        mappedBy = "company",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = false)
    @ToString.Exclude //ignoramos esta propiedad de toString
    @EqualsAndHashCode.Exclude //ignoramos las propiedades dentro de la lista
    private List<Vacant>vacants;
}
