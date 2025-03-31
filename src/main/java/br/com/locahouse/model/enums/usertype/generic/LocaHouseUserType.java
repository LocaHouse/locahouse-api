package br.com.locahouse.model.enums.usertype.generic;

import org.hibernate.usertype.UserType;

import java.io.Serializable;

abstract class LocaHouseUserType<T> implements UserType<T> {

    @Override
    public T assemble(Serializable cached, Object owner) {
        return (T) cached;
    }

    @Override
    public T deepCopy(Object value) {
        return (T) value;
    }

    @Override
    public Serializable disassemble(Object value) {
        return (Serializable) value;
    }

    @Override
    public boolean equals(Object x, Object y) {
        if ((x == null && y != null) || (x != null && y == null))
            return false;

        if (x == y)
            return true;

        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public T replace(Object original, Object target, Object owner) {
        return (T) original;
    }
}
