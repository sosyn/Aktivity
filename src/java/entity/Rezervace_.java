package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Rezervace.class)
public class Rezervace_ extends EntitySuperClass_ {

    public static volatile ListAttribute<Rezervace, Log> logList;
    public static volatile SingularAttribute<Rezervace, Zdroj> idzdr;
    public static volatile SingularAttribute<Rezervace, String> komentar;
    public static volatile SingularAttribute<Rezervace, Aktivity> idakt;
    public static volatile SingularAttribute<Rezervace, Cesta> idcest;
    public static volatile ListAttribute<Rezervace, Schvaleni> schvList;

}