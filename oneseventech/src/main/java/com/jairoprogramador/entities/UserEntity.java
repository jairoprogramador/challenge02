package com.jairoprogramador.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity implements Serializable {

    public UserEntity(BigInteger id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String email;

    @Column(name = "pwd")
    private String password;

    private String name;

    private String lastName;

    private Boolean newsletters;

    @Temporal(TemporalType.DATE)
    private LocalDate createDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_user")
    private Set<RoleEntity> roles;

}
