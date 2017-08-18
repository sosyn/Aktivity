package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Aktivity.class)
public class Aktivity_ extends EntitySuperClass_ {

    public static volatile ListAttribute<Aktivity, Log> logList;
    public static volatile SingularAttribute<Aktivity, String> komentar;
    public static volatile ListAttribute<Aktivity, Rezervace> rezervaceList;
    public static volatile SingularAttribute<Aktivity, Typaktivity> idtypakt;
    public static volatile SingularAttribute<Aktivity, Osoba> idoso;

}