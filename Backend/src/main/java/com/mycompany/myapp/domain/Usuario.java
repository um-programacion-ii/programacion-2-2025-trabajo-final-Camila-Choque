package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "nombre_alumno")
    private String nombreAlumno;

    @Column(name = "descripcion_proyecto")
    private String descripcionProyecto;

    @Column(name = "jwt_token")
    private String jwtToken;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public Usuario username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Usuario password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Usuario firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Usuario lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Usuario email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreAlumno() {
        return this.nombreAlumno;
    }

    public Usuario nombreAlumno(String nombreAlumno) {
        this.setNombreAlumno(nombreAlumno);
        return this;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getDescripcionProyecto() {
        return this.descripcionProyecto;
    }

    public Usuario descripcionProyecto(String descripcionProyecto) {
        this.setDescripcionProyecto(descripcionProyecto);
        return this;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public Usuario jwtToken(String jwtToken) {
        this.setJwtToken(jwtToken);
        return this;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public LocalDate getFechaRegistro() {
        return this.fechaRegistro;
    }

    public Usuario fechaRegistro(LocalDate fechaRegistro) {
        this.setFechaRegistro(fechaRegistro);
        return this;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getName() {
        return this.name;
    }

    public Usuario name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return getId() != null && getId().equals(((Usuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", nombreAlumno='" + getNombreAlumno() + "'" +
            ", descripcionProyecto='" + getDescripcionProyecto() + "'" +
            ", jwtToken='" + getJwtToken() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
