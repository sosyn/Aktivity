/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.UUID;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author Ivo
 */
public class UUIDConverter implements Converter {

    @Override
    public Object convertObjectValueToDataValue(Object objectValue,
            Session session) {
        if (objectValue==null) {
            System.out.println("!!! UUIDConverter > objectValue=null");
        }
        return objectValue;

    }

    @Override
    public UUID convertDataValueToObjectValue(Object dataValue,
            Session session) {

        if (dataValue instanceof UUID) {
            return (UUID) dataValue;
        } else {
            if (dataValue == null) {
                System.out.println("!!! UUIDConverter > dataValue not UUID: =NULL");
            } else {
                System.out.println("!!! UUIDConverter > dataValue not UUID: " + dataValue);
            }
            return null;
        }
    }

    @Override
    public boolean isMutable() {

        return true;

    }

    @Override
    public void initialize(DatabaseMapping mapping, Session session) {

        final DatabaseField field;

        if (mapping instanceof DirectCollectionMapping) {

            // handle @ElementCollection...
            field = ((DirectCollectionMapping) mapping).getDirectField();

        } else {

            field = mapping.getField();

        }

        field.setSqlType(java.sql.Types.OTHER);

        field.setTypeName("java.util.UUID");

        field.setColumnDefinition("UUID");

    }

}
