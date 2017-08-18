package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Ucastnik.class)
public class Ucastnik_ extends EntitySuperClass_ {

    public static volatile SingularAttribute<Ucastnik, Typucast> idtypucast;
    public static volatile ListAttribute<Ucastnik, Log> logList;
    public static volatile SingularAttribute<Ucastnik, Cesta> idcest;
    public static volatile ListAttribute<Ucastnik, Schvaleni> schvList;
    public static volatile SingularAttribute<Ucastnik, Osoba> idoso;

}