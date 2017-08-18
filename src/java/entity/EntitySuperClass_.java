package entity;

import java.util.Date;
import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T08:43:44")
@StaticMetamodel(EntitySuperClass.class)
public class EntitySuperClass_ { 

    public static volatile SingularAttribute<EntitySuperClass, UUID> id;
    public static volatile SingularAttribute<EntitySuperClass, Date> platiod;
    public static volatile SingularAttribute<EntitySuperClass, String> popis;
    public static volatile SingularAttribute<EntitySuperClass, Date> platido;
    public static volatile SingularAttribute<EntitySuperClass, Date> timeinsert;
    public static volatile SingularAttribute<EntitySuperClass, Date> timemodify;
    public static volatile SingularAttribute<EntitySuperClass, String> usermodify;

}