package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Log.class)
public class Log_ extends EntitySuperClass_ {

    public static volatile SingularAttribute<Log, Zdroj> idzdr;
    public static volatile SingularAttribute<Log, Aktivity> idakt;
    public static volatile SingularAttribute<Log, Rezervace> idrez;
    public static volatile SingularAttribute<Log, Cesta> idcest;
    public static volatile SingularAttribute<Log, Ucastnik> iducast;
    public static volatile SingularAttribute<Log, Osoba> idoso;

}