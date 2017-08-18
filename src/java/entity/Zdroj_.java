package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Zdroj.class)
public class Zdroj_ extends EntitySuperClass_ {

    public static volatile SingularAttribute<Zdroj, Integer> kapacita;
    public static volatile ListAttribute<Zdroj, Log> logList;
    public static volatile SingularAttribute<Zdroj, String> spz;
    public static volatile SingularAttribute<Zdroj, String> komentar;
    public static volatile SingularAttribute<Zdroj, Typzdroje> idtypzdr;
    public static volatile ListAttribute<Zdroj, Rezervace> rezervaceList;
    public static volatile SingularAttribute<Zdroj, Osoba> idoso;

}