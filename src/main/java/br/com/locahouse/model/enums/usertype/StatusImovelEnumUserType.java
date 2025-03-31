package br.com.locahouse.model.enums.usertype;

import br.com.locahouse.model.enums.StatusImovelEnum;
import br.com.locahouse.model.enums.usertype.generic.LocaHouseUserTypeInteger;

public class StatusImovelEnumUserType extends LocaHouseUserTypeInteger<StatusImovelEnum> {

    @Override
    protected Object getValue(Integer codigo) {
        return StatusImovelEnum.bucarEnumPeloCodigo(codigo);
    }

    @Override
    public Class<StatusImovelEnum> returnedClass() {
        return StatusImovelEnum.class;
    }
}
