/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.MapKeyConvert;

/**
 *
 * @author Ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l")})
public class Log extends entity.EntitySuperClass {

    // Identifikator zaznamu v tabulce
    @Column(columnDefinition = "UUID", name = "tblid")
    @Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
    @Convert("uuidConverter")
    private UUID tblid;

    private String tblname;

//    @Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
//    @MapKeyConvert(value = "uuidConverter")
    @JoinColumn(name = "idakt", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Aktivity idakt;

    @JoinColumn(name = "idcest", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Cesta idcest;

    @JoinColumn(name = "idoso", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Osoba idoso;
    @JoinColumn(name = "idrez", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Rezervace idrez;
    @JoinColumn(name = "iducast", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Ucastnik iducast;
    @JoinColumn(name = "idzdr", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Zdroj idzdr;

    public Log() {
        super();
    }

    public Log(UUID id) {
        super(id);
    }

    public Log(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public Aktivity getIdakt() {
        return idakt;
    }

    public void setIdakt(Aktivity idakt) {
        this.idakt = idakt;
    }

    public Cesta getIdcest() {
        return idcest;
    }

    public void setIdcest(Cesta idcest) {
        this.idcest = idcest;
    }

    public Osoba getIdoso() {
        return idoso;
    }

    public void setIdoso(Osoba idoso) {
        this.idoso = idoso;
    }

    public Rezervace getIdrez() {
        return idrez;
    }

    public void setIdrez(Rezervace idrez) {
        this.idrez = idrez;
    }

    public Ucastnik getIducast() {
        return iducast;
    }

    public void setIducast(Ucastnik iducast) {
        this.iducast = iducast;
    }

    public Zdroj getIdzdr() {
        return idzdr;
    }

    public void setIdzdr(Zdroj idzdr) {
        this.idzdr = idzdr;
    }

    /**
     * @return the tblname
     */
    public String getTblname() {
        return tblname;
    }

    /**
     * @param tblname the tblname to set
     */
    public void setTblname(String tblname) {
        this.tblname = tblname;
    }

    /**
     * @return the tblid
     */
    public UUID getTblid() {
        return tblid;
    }

    /**
     * @param tblid the tblid to set
     */
    public void setTblid(UUID tblid) {
        this.tblid = tblid;
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
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Log[ id=" + getId() + " ]";
    }
}
