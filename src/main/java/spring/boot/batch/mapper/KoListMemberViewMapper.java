package spring.boot.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.KoListMemberView;

@Component
public class KoListMemberViewMapper implements RowMapper<KoListMemberView> {

	@Override
    public KoListMemberView mapRow(ResultSet rs, int rowNum) throws SQLException {
        return KoListMemberView.builder().klmId(rs.getString("KLM_ID")).klKoFullName(rs.getString("KL_KO_FULL_NAME"))
                .klKoRegNum(rs.getString("KL_KO_REG_NUM")).klmInn(rs.getString("KLM_INN"))
                .klmOgrn(rs.getString("KLM_OGRN")).perCurAccKoName(rs.getString("PER_CUR_ACC_KO_NAME"))
                .klmPercent(rs.getString("KLM_PERCENT")).klmtName(rs.getString("KLMT_NAME"))
                .perCurAccKoId(rs.getString("PER_CUR_ACC_KO_ID")).klmResidentValue(rs.getString("KLM_RESIDENT_VALUE"))
                .klmCntIso(rs.getString("KLM_CNT_ISO")).klmCntName(rs.getString("KLM_CNT_NAME"))
                .klAcceptDate(rs.getString("KL_ACCEPT_DATE")).klmAddress(rs.getString("KLM_ADDRESS")).build();
	}

}
