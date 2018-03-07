/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Osoba.findAll", query = "SELECT o FROM Osoba o")})
public class Osoba extends entity.EntitySuperClass {

    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String name;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String password;
    private Integer extid;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String komentar;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "osoba")
    private List<Log> logList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Zdroj> zdrojList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Schvaleni> schvaleniList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Aktivity> aktivityList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Cesta> cestaList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Ucastnik> ucastnikList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Dispecerhl> dispecerhlList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Dispecerpol> dispecerpolList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idoso")
    private List<Email> emailList;

    public Osoba() {
        super();
    }

    public Osoba(UUID id) {
        super(id);
    }

    public Osoba(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getExtid() {
        return extid;
    }

    public void setExtid(Integer extid) {
        this.extid = extid;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    public List<Zdroj> getZdrojList() {
        return zdrojList;
    }

    public void setZdrojList(List<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    public List<Schvaleni> getSchvaleniList() {
        return schvaleniList;
    }

    public void setSchvaleniList(List<Schvaleni> schvaleniList) {
        this.schvaleniList = schvaleniList;
    }

    public List<Aktivity> getAktivityList() {
        return aktivityList;
    }

    public void setAktivityList(List<Aktivity> aktivityList) {
        this.aktivityList = aktivityList;
    }

    public List<Cesta> getCestaList() {
        return cestaList;
    }

    public void setCestaList(List<Cesta> cestaList) {
        this.cestaList = cestaList;
    }

    public List<Ucastnik> getUcastnikList() {
        return ucastnikList;
    }

    public void setUcastnikList(List<Ucastnik> ucastnikList) {
        this.ucastnikList = ucastnikList;
    }

    public List<Dispecerhl> getDispecerhlList() {
        return dispecerhlList;
    }

    public void setDispecerhlList(List<Dispecerhl> dispecerhlList) {
        this.dispecerhlList = dispecerhlList;
    }

    public List<Dispecerpol> getDispecerpolList() {
        return dispecerpolList;
    }

    public void setDispecerpolList(List<Dispecerpol> dispecerpolList) {
        this.dispecerpolList = dispecerpolList;
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
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
        if (!(object instanceof Osoba)) {
            return false;
        }
        Osoba other = (Osoba) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Osoba[ id=" + getId() + " ]";
    }

}
