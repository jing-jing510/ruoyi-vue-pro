package cn.iocoder.yudao.module.coal.service.repairrequest;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo.RepairRequestPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo.RepairRequestSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.repairrequest.RepairRequestDO;
import cn.iocoder.yudao.module.coal.dal.mysql.repairrequest.RepairRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.REPAIR_REQUEST_NOT_EXISTS;

/**
 * 报修单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class RepairRequestServiceImpl implements RepairRequestService {

    @Resource
    private RepairRequestMapper repairRequestMapper;

    @Override
    public Long createRepairRequest(RepairRequestSaveReqVO createReqVO) {
        // 插入
        RepairRequestDO repairRequest = BeanUtils.toBean(createReqVO, RepairRequestDO.class);
        repairRequestMapper.insert(repairRequest);

        // 返回
        return repairRequest.getId();
    }

    @Override
    public void updateRepairRequest(RepairRequestSaveReqVO updateReqVO) {
        // 校验存在
        validateRepairRequestExists(updateReqVO.getId());
        // 更新
        RepairRequestDO updateObj = BeanUtils.toBean(updateReqVO, RepairRequestDO.class);
        repairRequestMapper.updateById(updateObj);
    }

    @Override
    public void deleteRepairRequest(Long id) {
        // 校验存在
        validateRepairRequestExists(id);
        // 删除
        repairRequestMapper.deleteById(id);
    }

    @Override
        public void deleteRepairRequestListByIds(List<Long> ids) {
        // 删除
        repairRequestMapper.deleteByIds(ids);
        }


    private void validateRepairRequestExists(Long id) {
        if (repairRequestMapper.selectById(id) == null) {
            throw exception(REPAIR_REQUEST_NOT_EXISTS);
        }
    }

    @Override
    public RepairRequestDO getRepairRequest(Long id) {
        return repairRequestMapper.selectById(id);
    }

    @Override
    public PageResult<RepairRequestDO> getRepairRequestPage(RepairRequestPageReqVO pageReqVO) {
        return repairRequestMapper.selectPage(pageReqVO);
    }

}