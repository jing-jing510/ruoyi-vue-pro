package cn.iocoder.yudao.module.coal.service.qualityinspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.QualityDataPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.QualityDataSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.QualityDataStatisticsRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;
import cn.iocoder.yudao.module.coal.dal.mysql.qualityinspection.QualityDataMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.QUALITY_DATA_NOT_EXISTS;

/**
 * 质量检测数据 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class QualityDataServiceImpl implements QualityDataService {

    @Resource
    private QualityDataMapper qualityDataMapper;

    @Override
    public Long createQualityData(QualityDataSaveReqVO createReqVO) {
        // 插入
        QualityDataDO qualityData = BeanUtils.toBean(createReqVO, QualityDataDO.class);
        qualityDataMapper.insert(qualityData);

        // 返回
        return qualityData.getId();
    }

    @Override
    public void updateQualityData(QualityDataSaveReqVO updateReqVO) {
        // 校验存在
        validateQualityDataExists(updateReqVO.getId());
        // 更新
        QualityDataDO updateObj = BeanUtils.toBean(updateReqVO, QualityDataDO.class);
        qualityDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteQualityData(Long id) {
        // 校验存在
        validateQualityDataExists(id);
        // 删除
        qualityDataMapper.deleteById(id);
    }

    @Override
        public void deleteQualityDataListByIds(List<Long> ids) {
        // 删除
        qualityDataMapper.deleteByIds(ids);
        }


    private void validateQualityDataExists(Long id) {
        if (qualityDataMapper.selectById(id) == null) {
            throw exception(QUALITY_DATA_NOT_EXISTS);
        }
    }

    @Override
    public QualityDataDO getQualityData(Long id) {
        return qualityDataMapper.selectById(id);
    }

    @Override
    public PageResult<QualityDataDO> getQualityDataPage(QualityDataPageReqVO pageReqVO) {
//        return qualityDataMapper.selectPage(pageReqVO);
        return null;
    }

    @Override
    public QualityDataStatisticsRespVO getQualityDataStatistics() {
        QualityDataStatisticsRespVO statistics = new QualityDataStatisticsRespVO();
        
        // 获取总数量
        Long totalCount = qualityDataMapper.selectCount();
        statistics.setTotalCount(totalCount);
        
        // 获取合格数量
        Long qualifiedCount = qualityDataMapper.selectCount(
            new LambdaQueryWrapperX<QualityDataDO>()
                .eq(QualityDataDO::getIsQualified, 1)
        );
        statistics.setQualifiedCount(qualifiedCount);
        
        // 获取不合格数量
        Long unqualifiedCount = qualityDataMapper.selectCount(
            new LambdaQueryWrapperX<QualityDataDO>()
                .eq(QualityDataDO::getIsQualified, 0)
        );
        statistics.setUnqualifiedCount(unqualifiedCount);
        
        // 计算合格率
        if (totalCount > 0) {
            double qualifiedRate = (double) qualifiedCount / totalCount * 100;
            statistics.setQualifiedRate(Math.round(qualifiedRate * 100.0) / 100.0);
        } else {
            statistics.setQualifiedRate(0.0);
        }
        
        // 获取今日数量
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        Long todayCount = qualityDataMapper.selectCount(
            new LambdaQueryWrapperX<QualityDataDO>()
                .between(QualityDataDO::getCreateTime, todayStart, todayEnd)
        );
        statistics.setTodayCount(todayCount);
        
        // 获取本月数量
        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime monthEnd = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59);
        Long monthlyCount = qualityDataMapper.selectCount(
            new LambdaQueryWrapperX<QualityDataDO>()
                .between(QualityDataDO::getCreateTime, monthStart, monthEnd)
        );
        statistics.setMonthlyCount(monthlyCount);
        
        // 获取复检数量
        Long retestCount = qualityDataMapper.selectCount(
            new LambdaQueryWrapperX<QualityDataDO>()
                .eq(QualityDataDO::getIsRetest, 1)
        );
        statistics.setRetestCount(retestCount);
        
        return statistics;
    }

}