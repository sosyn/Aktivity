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
    @JoinColumn(name = "idakt", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Aktivity aktivity;

    @JoinColumn(name = "idcest", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Cesta cesta;

    @JoinColumn(name = "idoso", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Osoba osoba;

    @JoinColumn(name = "idrez", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Rezervace rezervace;

    @JoinColumn(name = "iducast", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Ucastnik ucastnik;

    @JoinColumn(name = "idzdr", referencedColumnName = "id", nullable = true, columnDefinition = "UUID")
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Zdroj zdroj;

    public Log() {
        super();
    }

    public Log(UUID id) {
        super(id);
    }

    public Log(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    /**
     * @return the aktivity
     */
    public Aktivity getAktivity() {
        return aktivity;
    }

    /**
     * @param aktivity the aktivity to set
     */
    public void setAktivity(Aktivity aktivity) {
        this.aktivity = aktivity;
    }

    /**
     * @return the cesta
     */
    public Cesta getCesta() {
        return cesta;
    }

    /**
     * @param cesta the cesta to set
     */
    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    /**
     * @return the osoba
     */
    public Osoba getOsoba() {
        return osoba;
    }

    /**
     * @param osoba the osoba to set
     */
    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    /**
     * @return the rezervace
     */
    public Rezervace getRezervace() {
        return rezervace;
    }

    /**
     * @param rezervace the rezervace to set
     */
    public void setRezervace(Rezervace rezervace) {
        this.rezervace = rezervace;
    }

    /**
     * @return the ucastnik
     */
    public Ucastnik getUcastnik() {
        return ucastnik;
    }

    /**
     * @param ucastnik the ucastnik to set
     */
    public void setUcastnik(Ucastnik ucastnik) {
        this.ucastnik = ucastnik;
    }

    /**
     * @return the zdroj
     */
    public Zdroj getZdroj() {
        return zdroj;
    }

    /**
     * @param zdroj the zdroj to set
     */
    public void setZdroj(Zdroj zdroj) {
        this.zdroj = zdroj;
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
