package br.com.locahouse.model.enums.usertype.generic;

import br.com.locahouse.model.enums.generic.LocaHouseEnum;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class LocaHouseUserTypeInteger<T> extends LocaHouseUserType<T> {

    protected abstract Object getValue(Integer codigo);

    @Override
    public T nullSafeGet(ResultSet rs, int value, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        if (rs.wasNull())
            return null;

        return (T) this.getValue(rs.getInt(value));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if (value == null)
            st.setNull(index, Types.INTEGER);
        else {
            if (value instanceof String)
                st.setInt(index, Integer.parseInt(((String) value).replaceAll("%", "")));
            else
                st.setInt(index, ((LocaHouseEnum<Integer>) value).getCodigo());
        }
    }

    @Override
    public int getSqlType() {
        return Types.INTEGER;
    }
}
