package cn.iocoder.dashboard.modules.system.dal.mysql.dao.logger;

import cn.iocoder.dashboard.common.pojo.PageResult;
import cn.iocoder.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.dashboard.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.dashboard.modules.system.controller.logger.vo.loginlog.SysLoginLogPageReqVO;
import cn.iocoder.dashboard.modules.system.dal.mysql.dataobject.logger.SysLoginLogDO;
import cn.iocoder.dashboard.modules.system.enums.logger.SysLoginResultEnum;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLoginLogMapper extends BaseMapperX<SysLoginLogDO> {

    default PageResult<SysLoginLogDO> selectPage(SysLoginLogPageReqVO reqVO) {
        QueryWrapperX<SysLoginLogDO> query = new QueryWrapperX<SysLoginLogDO>()
                .likeIfPresent("user_ip", reqVO.getUserIp())
                .likeIfPresent("username", reqVO.getUsername())
                .betweenIfPresent("create_time", reqVO.getBeginTime(), reqVO.getEndTime());
        if (Boolean.TRUE.equals(reqVO.getStatus())) {
            query.eq("result", SysLoginResultEnum.SUCCESS.getResult());
        } else if (Boolean.FALSE.equals(reqVO.getStatus())) {
            query.gt("result", SysLoginResultEnum.SUCCESS.getResult());
        }
        query.orderByDesc("id"); // 降序
        return selectPage(reqVO, query);
    }

}