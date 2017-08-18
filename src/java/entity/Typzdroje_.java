package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(Typzdroje.class)
public class Typzdroje_ extends EntitySuperClass_ {

    public static volatile ListAttribute<Typzdroje, Zdroj> zdrojList;
    public static volatile SingularAttribute<Typzdroje, Integer> rezervace;
    public static volatile SingularAttribute<Typzdroje, Integer> typzdr;
    public static volatile SingularAttribute<Typzdroje, Integer> cesta;
    public static volatile ListAttribute<Typzdroje, Cesta> cestaList;

}