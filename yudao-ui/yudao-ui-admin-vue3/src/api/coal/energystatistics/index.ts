import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 能源消耗统计信息 */
export interface EnergyStatistics {
          id: number; // 统计ID
          statisticsDate?: string | Dayjs; // 统计日期
          statisticsType?: number; // 统计类型
          energyTypeId?: number; // 能源类型ID
          totalConsumption?: number; // 总消耗量
          totalCost?: number; // 总成本(元)
          averageConsumption: number; // 平均消耗量
          peakConsumption: number; // 峰值消耗量
          valleyConsumption: number; // 谷值消耗量
          consumptionEfficiency: number; // 消耗效率(%)
          costPerUnit: number; // 单位成本(元/单位)
          comparisonWithPlan: number; // 与计划对比(%)
          comparisonWithLastPeriod: number; // 与上期对比(%)
          statisticsStatus?: number; // 统计状态
  }

// 能源消耗统计 API
export const EnergyStatisticsApi = {
  // 查询能源消耗统计分页
  getEnergyStatisticsPage: async (params: any) => {
    return await request.get({ url: `/coal/energy-statistics/page`, params })
  },

  // 查询能源消耗统计详情
  getEnergyStatistics: async (id: number) => {
    return await request.get({ url: `/coal/energy-statistics/get?id=` + id })
  },

  // 新增能源消耗统计
  createEnergyStatistics: async (data: EnergyStatistics) => {
    return await request.post({ url: `/coal/energy-statistics/create`, data })
  },

  // 修改能源消耗统计
  updateEnergyStatistics: async (data: EnergyStatistics) => {
    return await request.put({ url: `/coal/energy-statistics/update`, data })
  },

  // 删除能源消耗统计
  deleteEnergyStatistics: async (id: number) => {
    return await request.delete({ url: `/coal/energy-statistics/delete?id=` + id })
  },

  /** 批量删除能源消耗统计 */
  deleteEnergyStatisticsList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/energy-statistics/delete-list?ids=${ids.join(',')}` })
  },

  // 导出能源消耗统计 Excel
  exportEnergyStatistics: async (params) => {
    return await request.download({ url: `/coal/energy-statistics/export-excel`, params })
  },

  // 计算与计划对比百分比
  calculateComparisonWithPlan: async (energyTypeId: number, statisticsDate: string, statisticsType: number, actualValue: number) => {
    return await request.get({ 
      url: `/coal/energy-statistics/calculate-comparison-with-plan`,
      params: { energyTypeId, statisticsDate, statisticsType, actualValue }
    })
  },

  // 计算与上期对比百分比
  calculateComparisonWithLastPeriod: async (energyTypeId: number, statisticsDate: string, statisticsType: number, currentValue: number) => {
    return await request.get({ 
      url: `/coal/energy-statistics/calculate-comparison-with-last-period`,
      params: { energyTypeId, statisticsDate, statisticsType, currentValue }
    })
  },
}
