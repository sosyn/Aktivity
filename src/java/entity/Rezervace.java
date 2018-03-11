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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

/**
 *
 * @author Ivo
 */
@Entity
@Cache(isolation = CacheIsolationType.ISOLATED )
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Rezervace.findAll", query = "SELECT r FROM Rezervace r")})
public class Rezervace extends entity.EntitySuperClass {

    @Size(max = 4196)
    @Column(length = 4196)
    private String komentar;

    @JoinColumn(name = "idakt", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private Aktivity idakt;

    @JoinColumn(name = "idcest", referencedColumnName = "id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Cesta idcest;

    @JoinColumn(name = "idzdr", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Zdroj idzdr;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "rezervace")
    private List<Log> logList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idrez")
    private List<Schvaleni> schvList;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idrez")
    private List<Email> emailList;

    public Rezervace() {
    }

    public Rezervace(UUID id) {
        super();
    }

    public Rezervace(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
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

    public Zdroj getIdzdr() {
        return idzdr;
    }

    public void setIdzdr(Zdroj idzdr) {
        this.idzdr = idzdr;
    }

    /**
     * @return the schvList
     */
    public List<Schvaleni> getSchvList() {
        return schvList;
    }

    /**
     * @param schvList the schvList to set
     */
    public void setSchvList(List<Schvaleni> schvList) {
        this.schvList = schvList;
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
        if (!(object instanceof Rezervace)) {
            return false;
        }
        Rezervace other = (Rezervace) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rezervace[ id=" + getId() + " ]";
    }
    
}
