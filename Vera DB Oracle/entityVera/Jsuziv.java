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
    @NamedQuery(name = "Jsuziv.findAll", query = "SELECT j FROM Jsuziv j"),
    @NamedQuery(name = "Jsuziv.findByDatin", query = "SELECT j FROM Jsuziv j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsuziv.findByDatout", query = "SELECT j FROM Jsuziv j WHERE j.datout = :datout"),
    @NamedQuery(name = "Jsuziv.findByDatumnarozeni", query = "SELECT j FROM Jsuziv j WHERE j.datumnarozeni = :datumnarozeni"),
    @NamedQuery(name = "Jsuziv.findByDomena", query = "SELECT j FROM Jsuziv j WHERE j.domena = :domena"),
    @NamedQuery(name = "Jsuziv.findByDplatdo", query = "SELECT j FROM Jsuziv j WHERE j.dplatdo = :dplatdo"),
    @NamedQuery(name = "Jsuziv.findByDplatod", query = "SELECT j FROM Jsuziv j WHERE j.dplatod = :dplatod"),
    @NamedQuery(name = "Jsuziv.findByElpodpis", query = "SELECT j FROM Jsuziv j WHERE j.elpodpis = :elpodpis"),
    @NamedQuery(name = "Jsuziv.findByEmail", query = "SELECT j FROM Jsuziv j WHERE j.email = :email"),
    @NamedQuery(name = "Jsuziv.findByExternilogin", query = "SELECT j FROM Jsuziv j WHERE j.externilogin = :externilogin"),
    @NamedQuery(name = "Jsuziv.findByExterniuzivatel", query = "SELECT j FROM Jsuziv j WHERE j.externiuzivatel = :externiuzivatel"),
    @NamedQuery(name = "Jsuziv.findByExtid", query = "SELECT j FROM Jsuziv j WHERE j.extid = :extid"),
    @NamedQuery(name = "Jsuziv.findByFotka", query = "SELECT j FROM Jsuziv j WHERE j.fotka = :fotka"),
    @NamedQuery(name = "Jsuziv.findByHeslo", query = "SELECT j FROM Jsuziv j WHERE j.heslo = :heslo"),
    @NamedQuery(name = "Jsuziv.findById", query = "SELECT j FROM Jsuziv j WHERE j.id = :id"),
    @NamedQuery(name = "Jsuziv.findByIdvoj", query = "SELECT j FROM Jsuziv j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsuziv.findByJmeno", query = "SELECT j FROM Jsuziv j WHERE j.jmeno = :jmeno"),
    @NamedQuery(name = "Jsuziv.findByKatprac", query = "SELECT j FROM Jsuziv j WHERE j.katprac = :katprac"),
    @NamedQuery(name = "Jsuziv.findByLdapid", query = "SELECT j FROM Jsuziv j WHERE j.ldapid = :ldapid"),
    @NamedQuery(name = "Jsuziv.findByLogin", query = "SELECT j FROM Jsuziv j WHERE j.login = :login"),
    @NamedQuery(name = "Jsuziv.findByObecnejmeno", query = "SELECT j FROM Jsuziv j WHERE j.obecnejmeno = :obecnejmeno"),
    @NamedQuery(name = "Jsuziv.findByOznaceni", query = "SELECT j FROM Jsuziv j WHERE j.oznaceni = :oznaceni"),
    @NamedQuery(name = "Jsuziv.findByOznure", query = "SELECT j FROM Jsuziv j WHERE j.oznure = :oznure"),
    @NamedQuery(name = "Jsuziv.findByPerid", query = "SELECT j FROM Jsuziv j WHERE j.perid = :perid"),
    @NamedQuery(name = "Jsuziv.findByPrijmeni", query = "SELECT j FROM Jsuziv j WHERE j.prijmeni = :prijmeni"),
    @NamedQuery(name = "Jsuziv.findByTelefonmobilni", query = "SELECT j FROM Jsuziv j WHERE j.telefonmobilni = :telefonmobilni"),
    @NamedQuery(name = "Jsuziv.findByTitulpred", query = "SELECT j FROM Jsuziv j WHERE j.titulpred = :titulpred"),
    @NamedQuery(name = "Jsuziv.findByTitulza", query = "SELECT j FROM Jsuziv j WHERE j.titulza = :titulza"),
    @NamedQuery(name = "Jsuziv.findByUrn", query = "SELECT j FROM Jsuziv j WHERE j.urn = :urn"),
    @NamedQuery(name = "Jsuziv.findByUserin", query = "SELECT j FROM Jsuziv j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsuziv.findByVersion", query = "SELECT j FROM Jsuziv j WHERE j.version = :version")})
public class Jsuziv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datout;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumnarozeni;
    @Size(max = 64)
    @Column(length = 64)
    private String domena;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatdo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatod;
    @Size(max = 1)
    @Column(length = 1)
    private String elpodpis;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(length = 255)
    private String email;
    @Size(max = 64)
    @Column(length = 64)
    private String externilogin;
    private Long externiuzivatel;
    @Size(max = 250)
    @Column(length = 250)
    private String extid;
    @Size(max = 32)
    @Column(length = 32)
    private String fotka;
    @Size(max = 64)
    @Column(length = 64)
    private String heslo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long idvoj;
    @Size(max = 48)
    @Column(length = 48)
    private String jmeno;
    @Size(max = 4)
    @Column(length = 4)
    private String katprac;
    @Size(max = 255)
    @Column(length = 255)
    private String ldapid;
    @Size(max = 64)
    @Column(length = 64)
    private String login;
    @Size(max = 128)
    @Column(length = 128)
    private String obecnejmeno;
    @Size(max = 16)
    @Column(length = 16)
    private String oznaceni;
    @Size(max = 8)
    @Column(length = 8)
    private String oznure;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long perid;
    @Size(max = 64)
    @Column(length = 64)
    private String prijmeni;
    @Size(max = 33)
    @Column(length = 33)
    private String telefonmobilni;
    @Size(max = 48)
    @Column(length = 48)
    private String titulpred;
    @Size(max = 24)
    @Column(length = 24)
    private String titulza;
    @Size(max = 255)
    @Column(length = 255)
    private String urn;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @OneToMany(mappedBy = "uzivatel")
    private Collection<Jsfceuziv> jsfceuzivCollection;

    public Jsuziv() {
    }

    public Jsuziv(Long id) {
        this.id = id;
    }

    public Jsuziv(Long id, long idvoj, long perid, long version) {
        this.id = id;
        this.idvoj = idvoj;
        this.perid = perid;
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

    public Date getDatumnarozeni() {
        return datumnarozeni;
    }

    public void setDatumnarozeni(Date datumnarozeni) {
        this.datumnarozeni = datumnarozeni;
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

    public String getElpodpis() {
        return elpodpis;
    }

    public void setElpodpis(String elpodpis) {
        this.elpodpis = elpodpis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExternilogin() {
        return externilogin;
    }

    public void setExternilogin(String externilogin) {
        this.externilogin = externilogin;
    }

    public Long getExterniuzivatel() {
        return externiuzivatel;
    }

    public void setExterniuzivatel(Long externiuzivatel) {
        this.externiuzivatel = externiuzivatel;
    }

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
    }

    public String getFotka() {
        return fotka;
    }

    public void setFotka(String fotka) {
        this.fotka = fotka;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
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

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getKatprac() {
        return katprac;
    }

    public void setKatprac(String katprac) {
        this.katprac = katprac;
    }

    public String getLdapid() {
        return ldapid;
    }

    public void setLdapid(String ldapid) {
        this.ldapid = ldapid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getObecnejmeno() {
        return obecnejmeno;
    }

    public void setObecnejmeno(String obecnejmeno) {
        this.obecnejmeno = obecnejmeno;
    }

    public String getOznaceni() {
        return oznaceni;
    }

    public void setOznaceni(String oznaceni) {
        this.oznaceni = oznaceni;
    }

    public String getOznure() {
        return oznure;
    }

    public void setOznure(String oznure) {
        this.oznure = oznure;
    }

    public long getPerid() {
        return perid;
    }

    public void setPerid(long perid) {
        this.perid = perid;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getTelefonmobilni() {
        return telefonmobilni;
    }

    public void setTelefonmobilni(String telefonmobilni) {
        this.telefonmobilni = telefonmobilni;
    }

    public String getTitulpred() {
        return titulpred;
    }

    public void setTitulpred(String titulpred) {
        this.titulpred = titulpred;
    }

    public String getTitulza() {
        return titulza;
    }

    public void setTitulza(String titulza) {
        this.titulza = titulza;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @XmlTransient
    public Collection<Jsfceuziv> getJsfceuzivCollection() {
        return jsfceuzivCollection;
    }

    public void setJsfceuzivCollection(Collection<Jsfceuziv> jsfceuzivCollection) {
        this.jsfceuzivCollection = jsfceuzivCollection;
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
        if (!(object instanceof Jsuziv)) {
            return false;
        }
        Jsuziv other = (Jsuziv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsuziv[ id=" + id + " ]";
    }
    
}
