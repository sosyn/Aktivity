/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityVera;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sosyn
 */
@Entity
@Table(catalog = "", schema = "VERA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jsfcezar.findAll", query = "SELECT j FROM Jsfcezar j"),
    @NamedQuery(name = "Jsfcezar.findByDatin", query = "SELECT j FROM Jsfcezar j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsfcezar.findByDatout", query = "SELECT j FROM Jsfcezar j WHERE j.datout = :datout"),
    @NamedQuery(name = "Jsfcezar.findByDplatdo", query = "SELECT j FROM Jsfcezar j WHERE j.dplatdo = :dplatdo"),
    @NamedQuery(name = "Jsfcezar.findByDplatod", query = "SELECT j FROM Jsfcezar j WHERE j.dplatod = :dplatod"),
    @NamedQuery(name = "Jsfcezar.findByExtid", query = "SELECT j FROM Jsfcezar j WHERE j.extid = :extid"),
    @NamedQuery(name = "Jsfcezar.findByFax", query = "SELECT j FROM Jsfcezar j WHERE j.fax = :fax"),
    @NamedQuery(name = "Jsfcezar.findById", query = "SELECT j FROM Jsfcezar j WHERE j.id = :id"),
    @NamedQuery(name = "Jsfcezar.findByIdvoj", query = "SELECT j FROM Jsfcezar j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsfcezar.findByJenevid", query = "SELECT j FROM Jsfcezar j WHERE j.jenevid = :jenevid"),
    @NamedQuery(name = "Jsfcezar.findByNazev", query = "SELECT j FROM Jsfcezar j WHERE j.nazev = :nazev"),
    @NamedQuery(name = "Jsfcezar.findByOuo", query = "SELECT j FROM Jsfcezar j WHERE j.ouo = :ouo"),
    @NamedQuery(name = "Jsfcezar.findByPerid", query = "SELECT j FROM Jsfcezar j WHERE j.perid = :perid"),
    @NamedQuery(name = "Jsfcezar.findByTelefonexterni", query = "SELECT j FROM Jsfcezar j WHERE j.telefonexterni = :telefonexterni"),
    @NamedQuery(name = "Jsfcezar.findByTelefoninterni", query = "SELECT j FROM Jsfcezar j WHERE j.telefoninterni = :telefoninterni"),
    @NamedQuery(name = "Jsfcezar.findByTyp", query = "SELECT j FROM Jsfcezar j WHERE j.typ = :typ"),
    @NamedQuery(name = "Jsfcezar.findByUrn", query = "SELECT j FROM Jsfcezar j WHERE j.urn = :urn"),
    @NamedQuery(name = "Jsfcezar.findByUserin", query = "SELECT j FROM Jsfcezar j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsfcezar.findByVedouci", query = "SELECT j FROM Jsfcezar j WHERE j.vedouci = :vedouci"),
    @NamedQuery(name = "Jsfcezar.findByVersion", query = "SELECT j FROM Jsfcezar j WHERE j.version = :version"),
    @NamedQuery(name = "Jsfcezar.findByZkratka", query = "SELECT j FROM Jsfcezar j WHERE j.zkratka = :zkratka")})
public class Jsfcezar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datout;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatdo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatod;
    @Size(max = 250)
    @Column(length = 250)
    private String extid;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 64)
    @Column(length = 64)
    private String fax;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long idvoj;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short jenevid;
    @Size(max = 250)
    @Column(length = 250)
    private String nazev;
    private Short ouo;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long perid;
    @Size(max = 64)
    @Column(length = 64)
    private String telefonexterni;
    @Size(max = 64)
    @Column(length = 64)
    private String telefoninterni;
    @Size(max = 1)
    @Column(length = 1)
    private String typ;
    @Size(max = 50)
    @Column(length = 50)
    private String urn;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short vedouci;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @Size(max = 15)
    @Column(length = 15)
    private String zkratka;
    @JoinTable(name = "JSFCEUTVAR", joinColumns = {
        @JoinColumn(name = "FUNKCNIZARAZENI", referencedColumnName = "ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "UTVAR", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    private Collection<Jsutvar> jsutvarCollection;
    @OneToMany(mappedBy = "funkcnizarazeni")
    private Collection<Jsfceuziv> jsfceuzivCollection;
    @OneToMany(mappedBy = "funkcnizarazeni")
    private Collection<Jsfcemisto> jsfcemistoCollection;

    public Jsfcezar() {
    }

    public Jsfcezar(Long id) {
        this.id = id;
    }

    public Jsfcezar(Long id, long idvoj, short jenevid, long perid, short vedouci, long version) {
        this.id = id;
        this.idvoj = idvoj;
        this.jenevid = jenevid;
        this.perid = perid;
        this.vedouci = vedouci;
        this.version = version;
    }

    public Date getDatin() {
        return datin;
    }

    public void setDatin(Date datin) {
        this.datin = datin;
    }

    public Date getDatout() {
        return datout;
    }

    public void setDatout(Date datout) {
        this.datout = datout;
    }

    public Date getDplatdo() {
        return dplatdo;
    }

    public void setDplatdo(Date dplatdo) {
        this.dplatdo = dplatdo;
    }

    public Date getDplatod() {
        return dplatod;
    }

    public void setDplatod(Date dplatod) {
        this.dplatod = dplatod;
    }

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdvoj() {
        return idvoj;
    }

    public void setIdvoj(long idvoj) {
        this.idvoj = idvoj;
    }

    public short getJenevid() {
        return jenevid;
    }

    public void setJenevid(short jenevid) {
        this.jenevid = jenevid;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public Short getOuo() {
        return ouo;
    }

    public void setOuo(Short ouo) {
        this.ouo = ouo;
    }

    public long getPerid() {
        return perid;
    }

    public void setPerid(long perid) {
        this.perid = perid;
    }

    public String getTelefonexterni() {
        return telefonexterni;
    }

    public void setTelefonexterni(String telefonexterni) {
        this.telefonexterni = telefonexterni;
    }

    public String getTelefoninterni() {
        return telefoninterni;
    }

    public void setTelefoninterni(String telefoninterni) {
        this.telefoninterni = telefoninterni;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public Long getUserin() {
        return userin;
    }

    public void setUserin(Long userin) {
        this.userin = userin;
    }

    public short getVedouci() {
        return vedouci;
    }

    public void setVedouci(short vedouci) {
        this.vedouci = vedouci;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getZkratka() {
        return zkratka;
    }

    public void setZkratka(String zkratka) {
        this.zkratka = zkratka;
    }

    @XmlTransient
    public Collection<Jsutvar> getJsutvarCollection() {
        return jsutvarCollection;
    }

    public void setJsutvarCollection(Collection<Jsutvar> jsutvarCollection) {
        this.jsutvarCollection = jsutvarCollection;
    }

    @XmlTransient
    public Collection<Jsfceuziv> getJsfceuzivCollection() {
        return jsfceuzivCollection;
    }

    public void setJsfceuzivCollection(Collection<Jsfceuziv> jsfceuzivCollection) {
        this.jsfceuzivCollection = jsfceuzivCollection;
    }

    @XmlTransient
    public Collection<Jsfcemisto> getJsfcemistoCollection() {
        return jsfcemistoCollection;
    }

    public void setJsfcemistoCollection(Collection<Jsfcemisto> jsfcemistoCollection) {
        this.jsfcemistoCollection = jsfcemistoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jsfcezar)) {
            return false;
        }
        Jsfcezar other = (Jsfcezar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsfcezar[ id=" + id + " ]";
    }
    
}
