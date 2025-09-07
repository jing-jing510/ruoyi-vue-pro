import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 生产计划信息 */
export interface ProductionPlan {
          id: number; // 计划ID
          name?: string; // 计划名称
          parentId: number; // 父计划ID
          planType: number; // 计划类型
          planYear: number; // 计划年度
          planMonth: number; // 计划月份
          planDate: string | Dayjs; // 计划日期
          status?: number; // 计划状态
          rawCoalPlan: number; // 计划入洗原煤量(吨)
          fineCoalPlan: number; // 计划末煤产量(吨)
          granularCoalPlan: number; // 计划粒煤产量(吨)
          largeBlockCoalPlan: number; // 计划大块煤产量(吨)
          mediumBlockCoalPlan: number; // 计划中块煤产量(吨)
          smallBlockCoalPlan: number; // 计划小块煤产量(吨)
          middlingCoalPlan: number; // 计划中煤产量(吨)
          slimePlan: number; // 计划煤泥产量(吨)
          ganguePlan: number; // 计划矸石产量(吨)
          fineCoalAsh: number; // 末煤灰分(%)
          granularCoalAsh: number; // 粒煤灰分(%)
          largeBlockCoalAsh: number; // 大块煤灰分(%)
          mediumBlockCoalAsh: number; // 中块煤灰分(%)
          smallBlockCoalAsh: number; // 小块煤灰分(%)
          middlingCoalAsh: number; // 中煤灰分(%)
          creatorId: number; // 制定人ID
          approverId: number; // 审批人ID
          approveTime: string | Dayjs; // 审批时间
    children?: ProductionPlan[];
  }

// 生产计划 API
export const ProductionPlanApi = {
  // 查询生产计划列表
  getProductionPlanList: async (params) => {
    return await request.get({ url: `/coal/production-plan/list`, params })
  },

  // 查询生产计划详情
  getProductionPlan: async (id: number) => {
    return await request.get({ url: `/coal/production-plan/get?id=` + id })
  },

  // 新增生产计划
  createProductionPlan: async (data: ProductionPlan) => {
    return await request.post({ url: `/coal/production-plan/create`, data })
  },

  // 修改生产计划
  updateProductionPlan: async (data: ProductionPlan) => {
    return await request.put({ url: `/coal/production-plan/update`, data })
  },

  // 删除生产计划
  deleteProductionPlan: async (id: number) => {
    return await request.delete({ url: `/coal/production-plan/delete?id=` + id })
  },


  // 导出生产计划 Excel
  exportProductionPlan: async (params) => {
    return await request.download({ url: `/coal/production-plan/export-excel`, params })
  },

  // 一键生成年度计划
  generateYearlyPlan: async (data: ProductionPlan) => {
  return await request.post({ url: `/coal/production-plan/generate-yearly-plan`, data })
  },

  // 按年份删除生产计划
  deleteProductionPlanByYear: async (year: number) => {
  return await request.delete({ url: `/coal/production-plan/delete-by-year?year=` + year })
  },

  // 按年份物理删除生产计划
  physicalDeleteProductionPlanByYear: async (year: number) => {
  return await request.delete({ url: `/coal/production-plan/physical-delete-by-year?year=` + year })
  },
}
