package spring.boot.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class KoListMemberView {

    //KLM_ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String klmId; // Id BigDecimal
    
    @Column
    //KL_KO_FULL_NAME
    private String klKoFullName;
    
    @Column
    //KL_KO_REG_NUM
    private String klKoRegNum; //BigDecimal
    
    @Column
    //KLM_INN
    private String klmInn; //BigDecimal
    
    @Column
    //KLM_OGRN
    private String klmOgrn; //BigDecimal
    
    @Column
    //PER_CUR_ACC_KO_NAME
    private String perCurAccKoName;
    
    @Column
    //KLM_PERCENT
    private String klmPercent; //BigDecimal
    
    @Column
    //KLMT_NAME
    private String klmtName;
    
    @Column
    //PER_CUR_ACC_KO_ID
    private String perCurAccKoId; //BigDecimal
    
    @Column
    //KLM_RESIDENT_VALUE
    private String klmResidentValue; // Boolean, а в DTO - String
    
    @Column
    //KLM_CNT_ISO
    private String klmCntIso; //short or byte, Long
    
    @Column
    //KLM_CNT_NAME
    private String klmCntName;
    
    @Column
    //KL_ACCEPT_DATE
    private String klAcceptDate; //Date
    
    @Column
    //KLM_ADDRESS
    private String klmAddress;
    
}
