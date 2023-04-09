package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glowworm.football.booking.dao.mapper.FtStadiumBlockMapper;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.service.stadium.IStadiumBlockService;
import org.springframework.stereotype.Service;

/**
 * @author xuyongchang
 * @date 2023-04-08 15:27
 */
@Service
public class StadiumBlockServiceImpl extends ServiceImpl<FtStadiumBlockMapper, FtStadiumBlockPo> implements IStadiumBlockService {
}
