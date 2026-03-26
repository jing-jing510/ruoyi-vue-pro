import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 备件使用记录信息 */
export interface SparePartUsageRecord {
          id: number; // 使用记录ID
          sparePartId?: number; // 备件ID
          equipmentId?: number; // 设备ID
          usageType?: number; // 使用类型
          usageDate?: string | Dayjs; // 使用日期
          quantity?: number; // 使用数量
          operatorId: number; // 操作人ID
          supervisorId: number; // 监督人ID
          workTeam: string; // 作业班组
          workOrderId: number; // 工单ID(关联检修单)
          faultId: number; // 故障ID(关联故障记录)
          maintenancePlanId: number; // 维护计划ID
          oldPartCondition: number; // 旧件状态
          failureMode: string; // 失效模式
          usageDuration: number; // 使用时长(天)
          replacementReason: string; // 更换原因
          predictedLifespan: number; // 预计寿命(天)
          nextReplacementDate: string | Dayjs; // 预计下次更换日期
          performanceRating: number; // 性能评级
          unitCost: number; // 单位成本
          totalCost: number; // 总成本
          laborCost: number; // 人工成本
          downtimeCost: number; // 停机成本
          remark: string; // 备注
  }

// 备件使用记录 API
export const SparePartUsageRecordApi = {
  // 查询备件使用记录分页
  getSparePartUsageRecordPage: async (params: any) => {
    return await request.get({ url: `/coal/spare-part-usage-record/page`, params })
  },

  // 查询备件使用记录详情
  getSparePartUsageRecord: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-usage-record/get?id=` + id })
  },

  // 新增备件使用记录
  createSparePartUsageRecord: async (data: SparePartUsageRecord) => {
    return await request.post({ url: `/coal/spare-part-usage-record/create`, data })
  },

  // 修改备件使用记录
  updateSparePartUsageRecord: async (data: SparePartUsageRecord) => {
    return await request.put({ url: `/coal/spare-part-usage-record/update`, data })
  },

  // 删除备件使用记录
  deleteSparePartUsageRecord: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-usage-record/delete?id=` + id })
  },

  /** 批量删除备件使用记录 */
  deleteSparePartUsageRecordList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/spare-part-usage-record/delete-list?ids=${ids.join(',')}` })
  },

  // 导出备件使用记录 Excel
  exportSparePartUsageRecord: async (params) => {
    return await request.download({ url: `/coal/spare-part-usage-record/export-excel`, params })
  },
}