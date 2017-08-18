package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Schvaleni.class)
public class Schvaleni_ extends EntitySuperClass_ {

    public static volatile SingularAttribute<Schvaleni, Typschv> idtypschv;
    public static volatile SingularAttribute<Schvaleni, String> komentar;
    public static volatile SingularAttribute<Schvaleni, Rezervace> idrez;
    public static volatile SingularAttribute<Schvaleni, Integer> stav;
    public static volatile SingularAttribute<Schvaleni, Cesta> idcest;
    public static volatile SingularAttribute<Schvaleni, Ucastnik> iducast;
    public static volatile SingularAttribute<Schvaleni, Osoba> idoso;

}