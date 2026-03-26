import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 检修计划信息 */
export interface MaintenancePlan {
          id: number; // 计划ID
          planCode?: string; // 计划编号
          planName?: string; // 计划名称
          planType?: number; // 计划类型
          planStatus?: number; // 计划状态
          equipmentId?: number; // 设备ID
          equipmentName?: string; // 设备名称
          maintenanceType?: number; // 检修类型
          maintenanceLevel?: number; // 检修级别
          plannedStartTime?: string | Dayjs; // 计划开始时间
          plannedEndTime?: string | Dayjs; // 计划结束时间
          estimatedDuration?: number; // 预计工期(小时)
          responsiblePerson?: string; // 负责人
          responsibleTeam: string; // 负责班组
          maintenanceContent: string; // 检修内容
          safetyRequirements: string; // 安全要求
          requiredTools: string; // 所需工具
          requiredMaterials: string; // 所需材料
          budgetCost: number; // 预算费用
          actualCost: number; // 实际费用
          approvalStatus: number; // 审批状态
          approverId: number; // 审批人ID
          approvalTime: string | Dayjs; // 审批时间
          approvalRemark: string; // 审批备注
          remark: string; // 备注
  }

// 检修计划 API
export const MaintenancePlanApi = {
  // 查询检修计划分页
  getMaintenancePlanPage: async (params: any) => {
    return await request.get({ url: `/coal/maintenance-plan/page`, params })
  },

  // 查询检修计划详情
  getMaintenancePlan: async (id: number) => {
    return await request.get({ url: `/coal/maintenance-plan/get?id=` + id })
  },

  // 新增检修计划
  createMaintenancePlan: async (data: MaintenancePlan) => {
    return await request.post({ url: `/coal/maintenance-plan/create`, data })
  },

  // 修改检修计划
  updateMaintenancePlan: async (data: MaintenancePlan) => {
    return await request.put({ url: `/coal/maintenance-plan/update`, data })
  },

  // 删除检修计划
  deleteMaintenancePlan: async (id: number) => {
    return await request.delete({ url: `/coal/maintenance-plan/delete?id=` + id })
  },

  /** 批量删除检修计划 */
  deleteMaintenancePlanList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/maintenance-plan/delete-list?ids=${ids.join(',')}` })
  },

  // 导出检修计划 Excel
  exportMaintenancePlan: async (params) => {
    return await request.download({ url: `/coal/maintenance-plan/export-excel`, params })
  },

  // 查询检修计划列表（不分页）
  getMaintenancePlanList: async (params: any = {}) => {
    return await request.get({ url: `/coal/maintenance-plan/list`, params })
  },
}
