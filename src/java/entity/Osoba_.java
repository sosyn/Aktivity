package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Osoba.class)
public class Osoba_ extends EntitySuperClass_ {

    public static volatile ListAttribute<Osoba, Schvaleni> schvaleniList;
    public static volatile ListAttribute<Osoba, Zdroj> zdrojList;
    public static volatile ListAttribute<Osoba, Log> logList;
    public static volatile SingularAttribute<Osoba, String> name;
    public static volatile SingularAttribute<Osoba, String> komentar;
    public static volatile SingularAttribute<Osoba, Integer> extid;
    public static volatile ListAttribute<Osoba, Cesta> cestaList;
    public static volatile SingularAttribute<Osoba, String> password;
    public static volatile ListAttribute<Osoba, Ucastnik> ucastnikList;
    public static volatile ListAttribute<Osoba, Aktivity> aktivityList;

}