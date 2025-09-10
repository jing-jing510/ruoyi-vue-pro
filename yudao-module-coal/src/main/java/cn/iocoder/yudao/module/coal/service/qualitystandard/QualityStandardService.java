package cn.iocoder.yudao.module.coal.service.qualitystandard;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.qualitystandard.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualitystandard.QualityStandardDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 质量标准 Service 接口
 *
 * @author 京京
 */
public interface QualityStandardService {

    /**
     * 创建质量标准
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQualityStandard(@Valid QualityStandardSaveReqVO createReqVO);

    /**
     * 更新质量标准
     *
     * @param updateReqVO 更新信息
     */
    void updateQualityStandard(@Valid QualityStandardSaveReqVO updateReqVO);

    /**
     * 删除质量标准
     *
     * @param id 编号
     */
    void deleteQualityStandard(Long id);

    /**
    * 批量删除质量标准
    *
    * @param ids 编号
    */
    void deleteQualityStandardListByIds(List<Long> ids);

    /**
     * 获得质量标准
     *
     * @param id 编号
     * @return 质量标准
     */
    QualityStandardDO getQualityStandard(Long id);

    /**
     * 获得质量标准分页
     *
     * @param pageReqVO 分页查询
     * @return 质量标准分页
     */
    PageResult<QualityStandardDO> getQualityStandardPage(QualityStandardPageReqVO pageReqVO);

}