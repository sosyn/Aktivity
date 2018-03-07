/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e")
    , @NamedQuery(name = "Email.findBySmer", query = "SELECT e FROM Email e WHERE e.smer = :smer")
    , @NamedQuery(name = "Email.findByEmail", query = "SELECT e FROM Email e WHERE e.email = :email")
    , @NamedQuery(name = "Email.findByPredmet", query = "SELECT e FROM Email e WHERE e.predmet = :predmet")
    , @NamedQuery(name = "Email.findByPopis", query = "SELECT e FROM Email e WHERE e.popis = :popis")
    , @NamedQuery(name = "Email.findByPlatiod", query = "SELECT e FROM Email e WHERE e.platiod = :platiod")
    , @NamedQuery(name = "Email.findByPlatido", query = "SELECT e FROM Email e WHERE e.platido = :platido")
    , @NamedQuery(name = "Email.findByTimeinsert", query = "SELECT e FROM Email e WHERE e.timeinsert = :timeinsert")
    , @NamedQuery(name = "Email.findByTimemodify", query = "SELECT e FROM Email e WHERE e.timemodify = :timemodify")
    , @NamedQuery(name = "Email.findByUsermodify", query = "SELECT e FROM Email e WHERE e.usermodify = :usermodify")})
public class Email extends entity.EntitySuperClass {

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer smer;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String email;

    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String predmet;

    @JoinColumn(name = "idakt", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Aktivity idakt;

    @JoinColumn(name = "idcest", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cesta idcest;

    @JoinColumn(name = "idoso", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Osoba idoso;

    @JoinColumn(name = "idrez", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rezervace idrez;

    @JoinColumn(name = "iducast", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ucastnik iducast;

    public Email() {
        super();
    }

    public Email(UUID id) {
        super(id);
    }

    public Integer getSmer() {
        return smer;
    }

    public void setSmer(Integer smer) {
        this.smer = smer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    /**
     * @return the idakt
     */
    public Aktivity getIdakt() {
        return idakt;
    }

    /**
     * @param idakt the idakt to set
     */
    public void setIdakt(Aktivity idakt) {
        this.idakt = idakt;
    }

    /**
     * @return the idcest
     */
    public Cesta getIdcest() {
        return idcest;
    }

    /**
     * @param idcest the idcest to set
     */
    public void setIdcest(Cesta idcest) {
        this.idcest = idcest;
    }

    /**
     * @return the idoso
     */
    public Osoba getIdoso() {
        return idoso;
    }

    /**
     * @param idoso the idoso to set
     */
    public void setIdoso(Osoba idoso) {
        this.idoso = idoso;
    }

    /**
     * @return the idrez
     */
    public Rezervace getIdrez() {
        return idrez;
    }

    /**
     * @param idrez the idrez to set
     */
    public void setIdrez(Rezervace idrez) {
        this.idrez = idrez;
    }

    /**
     * @return the iducast
     */
    public Ucastnik getIducast() {
        return iducast;
    }

    /**
     * @param iducast the iducast to set
     */
    public void setIducast(Ucastnik iducast) {
        this.iducast = iducast;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Email[ id=" + this.getId() + " ]";
    }

}
