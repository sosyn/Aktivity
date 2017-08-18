package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Cesta.class)
public class Cesta_ extends EntitySuperClass_ {

    public static volatile ListAttribute<Cesta, Schvaleni> schvaleniList;
    public static volatile SingularAttribute<Cesta, Double> zaloha;
    public static volatile ListAttribute<Cesta, Log> logList;
    public static volatile SingularAttribute<Cesta, String> komentar;
    public static volatile SingularAttribute<Cesta, Typzdroje> idtypzdr;
    public static volatile ListAttribute<Cesta, Rezervace> rezervaceList;
    public static volatile ListAttribute<Cesta, Ucastnik> ucastnikList;
    public static volatile SingularAttribute<Cesta, Osoba> idoso;

}