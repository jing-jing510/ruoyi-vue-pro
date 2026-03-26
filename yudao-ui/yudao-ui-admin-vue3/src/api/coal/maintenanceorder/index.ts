import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 检修单信息 */
export interface MaintenanceOrder {
          id: number; // 检修单ID
          orderCode?: string; // 检修单编号
          planId?: number; // 关联计划ID
          repairRequestId?: number; // 关联报修单ID
          equipmentId?: number; // 设备ID
          equipmentName?: string; // 设备名称
          maintenanceType?: number; // 检修类型
          maintenanceLevel?: number; // 检修级别
          orderStatus?: number; // 检修单状态
          priorityLevel?: number; // 优先级
          faultDescription?: string; // 故障描述
          maintenanceContent?: string; // 检修内容
          safetyMeasures?: string; // 安全措施
          startTime?: string | Dayjs; // 实际开始时间
          endTime?: string | Dayjs; // 实际结束时间
          actualDuration?: number; // 实际工期(小时)
          responsiblePerson?: string; // 负责人
          responsibleTeam?: string; // 负责班组
          participants?: string; // 参与人员(JSON格式)
          workEnvironment?: string; // 作业环境
          weatherCondition?: string; // 天气条件
          completionRate?: number; // 完成进度(%)
          qualityRating?: number; // 质量评级
          safetyRating?: number; // 安全评级
          maintenanceResult?: string; // 检修结果
          problemsFound?: string; // 发现问题
          improvementSuggestions?: string; // 改进建议
          nextMaintenanceDate?: string | Dayjs; // 下次检修日期
          totalCost?: number; // 总费用
          laborCost?: number; // 人工费用
          materialCost?: number; // 材料费用
          otherCost?: number; // 其他费用
          remark?: string; // 备注
  }

/** 检修项目明细信息 */
export interface MaintenanceOrderItem {
          id: number; // 明细ID
          orderId?: number; // 检修单ID
          itemName?: string; // 项目名称
          itemType?: number; // 项目类型
          itemDescription: string; // 项目描述
          workStandard: string; // 作业标准
          safetyRequirements: string; // 安全要求
          requiredTools: string; // 所需工具
          requiredMaterials: string; // 所需材料
          estimatedDuration: number; // 预计工时(小时)
          actualDuration: number; // 实际工时(小时)
          itemStatus?: number; // 项目状态
          completionTime: string | Dayjs; // 完成时间
          completionQuality: number; // 完成质量
          completionNotes: string; // 完成说明
          inspector: string; // 检查人
          inspectionTime: string | Dayjs; // 检查时间
          inspectionResult: string; // 检查结果
          remark: string; // 备注
  }


// 检修单 API
export const MaintenanceOrderApi = {
  // 查询检修单分页
  getMaintenanceOrderPage: async (params: any) => {
    return await request.get({ url: `/coal/maintenance-order/page`, params })
  },

  // 查询检修单详情
  getMaintenanceOrder: async (id: number) => {
    return await request.get({ url: `/coal/maintenance-order/get?id=` + id })
  },

  // 新增检修单
  createMaintenanceOrder: async (data: MaintenanceOrder) => {
    return await request.post({ url: `/coal/maintenance-order/create`, data })
  },

  // 修改检修单
  updateMaintenanceOrder: async (data: MaintenanceOrder) => {
    return await request.put({ url: `/coal/maintenance-order/update`, data })
  },

  // 删除检修单
  deleteMaintenanceOrder: async (id: number) => {
    return await request.delete({ url: `/coal/maintenance-order/delete?id=` + id })
  },

  /** 批量删除检修单 */
  deleteMaintenanceOrderList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/maintenance-order/delete-list?ids=${ids.join(',')}` })
  },

  // 导出检修单 Excel
  exportMaintenanceOrder: async (params) => {
    return await request.download({ url: `/coal/maintenance-order/export-excel`, params })
  },
}

// 检修项目明细 API
export const MaintenanceOrderItemApi = {
  // 查询检修项目明细分页
  getMaintenanceOrderItemPage: async (params: any) => {
    return await request.get({ url: `/coal/maintenance-order-item/page`, params })
  },

  // 查询检修项目明细详情
  getMaintenanceOrderItem: async (id: number) => {
    return await request.get({ url: `/coal/maintenance-order-item/get?id=` + id })
  },

  // 新增检修项目明细
  createMaintenanceOrderItem: async (data: MaintenanceOrderItem) => {
    return await request.post({ url: `/coal/maintenance-order-item/create`, data })
  },

  // 修改检修项目明细
  updateMaintenanceOrderItem: async (data: MaintenanceOrderItem) => {
    return await request.put({ url: `/coal/maintenance-order-item/update`, data })
  },

  // 删除检修项目明细
  deleteMaintenanceOrderItem: async (id: number) => {
    return await request.delete({ url: `/coal/maintenance-order-item/delete?id=` + id })
  },

  /** 批量删除检修项目明细 */
  deleteMaintenanceOrderItemList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/maintenance-order-item/delete-list?ids=${ids.join(',')}` })
  },

  // 导出检修项目明细 Excel
  exportMaintenanceOrderItem: async (params) => {
    return await request.download({ url: `/coal/maintenance-order-item/export-excel`, params })
  },
}

