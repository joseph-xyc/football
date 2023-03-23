package com.glowworm.football.booking.domain.account;

import com.glowworm.football.booking.dao.po.FtAccountPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author xuyongchang
 * @date 2023/3/23
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountBean extends FtAccountPo {

}
