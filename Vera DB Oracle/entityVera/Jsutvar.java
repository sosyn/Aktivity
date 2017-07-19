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
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Jsutvar.findAll", query = "SELECT j FROM Jsutvar j"),
    @NamedQuery(name = "Jsutvar.findByCarovykodprefix", query = "SELECT j FROM Jsutvar j WHERE j.carovykodprefix = :carovykodprefix"),
    @NamedQuery(name = "Jsutvar.findByDatin", query = "SELECT j FROM Jsutvar j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsutvar.findByDatout", query = "SELECT j FROM Jsutvar j WHERE j.datout = :datout"),
    @NamedQuery(name = "Jsutvar.findByDbschema", query = "SELECT j FROM Jsutvar j WHERE j.dbschema = :dbschema"),
    @NamedQuery(name = "Jsutvar.findByDomena", query = "SELECT j FROM Jsutvar j WHERE j.domena = :domena"),
    @NamedQuery(name = "Jsutvar.findByDplatdo", query = "SELECT j FROM Jsutvar j WHERE j.dplatdo = :dplatdo"),
    @NamedQuery(name = "Jsutvar.findByDplatod", query = "SELECT j FROM Jsutvar j WHERE j.dplatod = :dplatod"),
    @NamedQuery(name = "Jsutvar.findByExtid", query = "SELECT j FROM Jsutvar j WHERE j.extid = :extid"),
    @NamedQuery(name = "Jsutvar.findByIc", query = "SELECT j FROM Jsutvar j WHERE j.ic = :ic"),
    @NamedQuery(name = "Jsutvar.findById", query = "SELECT j FROM Jsutvar j WHERE j.id = :id"),
    @NamedQuery(name = "Jsutvar.findByIdvoj", query = "SELECT j FROM Jsutvar j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsutvar.findByNazev", query = "SELECT j FROM Jsutvar j WHERE j.nazev = :nazev"),
    @NamedQuery(name = "Jsutvar.findByOrganvm", query = "SELECT j FROM Jsutvar j WHERE j.organvm = :organvm"),
    @NamedQuery(name = "Jsutvar.findByPerid", query = "SELECT j FROM Jsutvar j WHERE j.perid = :perid"),
    @NamedQuery(name = "Jsutvar.findByUrn", query = "SELECT j FROM Jsutvar j WHERE j.urn = :urn"),
    @NamedQuery(name = "Jsutvar.findByUroven", query = "SELECT j FROM Jsutvar j WHERE j.uroven = :uroven"),
    @NamedQuery(name = "Jsutvar.findByUserin", query = "SELECT j FROM Jsutvar j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsutvar.findByVersion", query = "SELECT j FROM Jsutvar j WHERE j.version = :version"),
    @NamedQuery(name = "Jsutvar.findByZkratka", query = "SELECT j FROM Jsutvar j WHERE j.zkratka = :zkratka")})
public class Jsutvar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 8)
    @Column(length = 8)
    private String carovykodprefix;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datout;
    @Size(max = 30)
    @Column(length = 30)
    private String dbschema;
    @Size(max = 30)
    @Column(length = 30)
    private String domena;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatdo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatod;
    @Size(max = 250)
    @Column(length = 250)
    private String extid;
    @Size(max = 10)
    @Column(length = 10)
    private String ic;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long idvoj;
    @Size(max = 250)
    @Column(length = 250)
    private String nazev;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short organvm;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long perid;
    @Size(max = 50)
    @Column(length = 50)
    private String urn;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long uroven;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @Size(max = 10)
    @Column(length = 10)
    private String zkratka;
    @JoinTable(name = "JSUTVARH", joinColumns = {
        @JoinColumn(name = "PODRIZENYUTVAR", referencedColumnName = "ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "NADRIZENYUTVAR", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    private Collection<Jsutvar> jsutvarCollection;
    @ManyToMany(mappedBy = "jsutvarCollection")
    private Collection<Jsutvar> jsutvarCollection1;
    @ManyToMany(mappedBy = "jsutvarCollection")
    private Collection<Jsfcezar> jsfcezarCollection;
    @OneToMany(mappedBy = "utvar")
    private Collection<Jsutvumi> jsutvumiCollection;
    @JoinColumn(name = "ADRESA", referencedColumnName = "ID")
    @ManyToOne
    private Jsadresa adresa;
    @JoinColumn(name = "PRAVNIFORMA", referencedColumnName = "ID")
    @ManyToOne
    private Jspravniforma pravniforma;
    @JoinColumn(name = "SKUPINA", referencedColumnName = "ID")
    @ManyToOne
    private Jsskupina skupina;

    public Jsutvar() {
    }

    public Jsutvar(Long id) {
        this.id = id;
    }

    public Jsutvar(Long id, long idvoj, short organvm, long perid, long uroven, long version) {
        this.id = id;
        this.idvoj = idvoj;
        this.organvm = organvm;
        this.perid = perid;
        this.uroven = uroven;
        this.version = version;
    }

    public String getCarovykodprefix() {
        return carovykodprefix;
    }

    public void setCarovykodprefix(String carovykodprefix) {
        this.carovykodprefix = carovykodprefix;
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

    public String getDbschema() {
        return dbschema;
    }

    public void setDbschema(String dbschema) {
        this.dbschema = dbschema;
    }

    public String getDomena() {
        return domena;
    }

    public void setDomena(String domena) {
        this.domena = domena;
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

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
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

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public short getOrganvm() {
        return organvm;
    }

    public void setOrganvm(short organvm) {
        this.organvm = organvm;
    }

    public long getPerid() {
        return perid;
    }

    public void setPerid(long perid) {
        this.perid = perid;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public long getUroven() {
        return uroven;
    }

    public void setUroven(long uroven) {
        this.uroven = uroven;
    }

    public Long getUserin() {
        return userin;
    }

    public void setUserin(Long userin) {
        this.userin = userin;
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
    public Collection<Jsutvar> getJsutvarCollection1() {
        return jsutvarCollection1;
    }

    public void setJsutvarCollection1(Collection<Jsutvar> jsutvarCollection1) {
        this.jsutvarCollection1 = jsutvarCollection1;
    }

    @XmlTransient
    public Collection<Jsfcezar> getJsfcezarCollection() {
        return jsfcezarCollection;
    }

    public void setJsfcezarCollection(Collection<Jsfcezar> jsfcezarCollection) {
        this.jsfcezarCollection = jsfcezarCollection;
    }

    @XmlTransient
    public Collection<Jsutvumi> getJsutvumiCollection() {
        return jsutvumiCollection;
    }

    public void setJsutvumiCollection(Collection<Jsutvumi> jsutvumiCollection) {
        this.jsutvumiCollection = jsutvumiCollection;
    }

    public Jsadresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Jsadresa adresa) {
        this.adresa = adresa;
    }

    public Jspravniforma getPravniforma() {
        return pravniforma;
    }

    public void setPravniforma(Jspravniforma pravniforma) {
        this.pravniforma = pravniforma;
    }

    public Jsskupina getSkupina() {
        return skupina;
    }

    public void setSkupina(Jsskupina skupina) {
        this.skupina = skupina;
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
        if (!(object instanceof Jsutvar)) {
            return false;
        }
        Jsutvar other = (Jsutvar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsutvar[ id=" + id + " ]";
    }
    
}
