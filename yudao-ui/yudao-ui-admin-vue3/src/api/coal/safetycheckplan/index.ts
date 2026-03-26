import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 安全检查计划信息 */
export interface SafetyCheckPlan {
          id: number; // 计划ID
          planCode?: string; // 计划编号
          planName?: string; // 计划名称
          planType?: number; // 计划类型
          checkCategoryId?: number; // 检查分类ID
          checkCycle?: number; // 检查周期
          checkFrequency?: number; // 检查频次
          responsiblePersonId?: number; // 负责人ID
          responsiblePersonName?: string; // 负责人姓名
          checkArea: string; // 检查区域
          checkContent: string; // 检查内容
          checkStandard: string; // 检查标准
          startDate?: string | Dayjs; // 开始日期
          endDate?: string | Dayjs; // 结束日期
          planStatus?: number; // 计划状态
          approvalStatus?: number; // 审批状态
          approverId: number; // 审批人ID
          approverName: string; // 审批人姓名
          approvalTime: string | Dayjs; // 审批时间
          approvalRemark: string; // 审批备注
          remark: string; // 备注
  }

// 安全检查计划 API
export const SafetyCheckPlanApi = {
  // 查询安全检查计划分页
  getSafetyCheckPlanPage: async (params: any) => {
    return await request.get({ url: `/coal/safety-check-plan/page`, params })
  },

  // 查询安全检查计划详情
  getSafetyCheckPlan: async (id: number) => {
    return await request.get({ url: `/coal/safety-check-plan/get?id=` + id })
  },

  // 新增安全检查计划
  createSafetyCheckPlan: async (data: SafetyCheckPlan) => {
    return await request.post({ url: `/coal/safety-check-plan/create`, data })
  },

  // 修改安全检查计划
  updateSafetyCheckPlan: async (data: SafetyCheckPlan) => {
    return await request.put({ url: `/coal/safety-check-plan/update`, data })
  },

  // 删除安全检查计划
  deleteSafetyCheckPlan: async (id: number) => {
    return await request.delete({ url: `/coal/safety-check-plan/delete?id=` + id })
  },

  /** 批量删除安全检查计划 */
  deleteSafetyCheckPlanList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/safety-check-plan/delete-list?ids=${ids.join(',')}` })
  },

  // 导出安全检查计划 Excel
  exportSafetyCheckPlan: async (params) => {
    return await request.download({ url: `/coal/safety-check-plan/export-excel`, params })
  },
}
