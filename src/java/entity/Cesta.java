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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 *
 * @author Ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Cesta.findAll", query = "SELECT c FROM Cesta c")})
public class Cesta extends entity.EntitySuperClass {

    @Size(max = 5120)
    @Column(length = 5120)
    private String komentar;

    @Min(value = 0)
    @Max(value = 100000)
    private Double zaloha;

    @JoinColumn(name = "idoso", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Osoba idoso;

    // @Converter(name = "typZdrojeConverter", converterClass = TypZdrojeConverter.class)
    @JoinColumn(name = "idtypzdr", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Typzdroje idtypzdr;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idcest")
    private List<Schvaleni> schvaleniList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idcest")
    private List<Ucastnik> ucastnikList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idcest")
    private List<Log> logList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idcest")
    private List<Rezervace> rezervaceList;

    public Cesta() {
        super();
    }

    public Cesta(UUID id) {
        super(id);
    }

    public Cesta(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    /**
     * @return the komentar
     */
    public String getKomentar() {
        return komentar;
    }

    /**
     * @param komentar the komentar to set
     */
    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    /**
     * @return the zaloha
     */
    public Double getZaloha() {
        return zaloha;
    }

    /**
     * @param zaloha the zaloha to set
     */
    public void setZaloha(Double zaloha) {
        this.zaloha = zaloha;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    public List<Schvaleni> getSchvaleniList() {
        return schvaleniList;
    }

    public void setSchvaleniList(List<Schvaleni> schvaleniList) {
        this.schvaleniList = schvaleniList;
    }

    /**
     * @return the ucastnikList
     */
    public List<Ucastnik> getUcastnikList() {
        return ucastnikList;
    }

    /**
     * @param ucastnikList the ucastnikList to set
     */
    public void setUcastnikList(List<Ucastnik> ucastnikList) {
        this.ucastnikList = ucastnikList;
    }

    public Osoba getIdoso() {
        return idoso;
    }

    public void setIdoso(Osoba idoso) {
        this.idoso = idoso;
    }

    public Typzdroje getIdtypzdr() {
        return idtypzdr;
    }

    public void setIdtypzdr(Typzdroje idtypzdr) {
        this.idtypzdr = idtypzdr;
    }

    public List<Rezervace> getRezervaceList() {
        return rezervaceList;
    }

    public void setRezervaceList(List<Rezervace> rezervaceList) {
        this.rezervaceList = rezervaceList;
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
        if (!(object instanceof Cesta)) {
            return false;
        }
        Cesta other = (Cesta) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cesta[ id=" + getId() + " ]";
    }

}
