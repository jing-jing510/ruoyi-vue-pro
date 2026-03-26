import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 综合成本核算信息 */
export interface MarketCostAnalysis {
          id: number; // 主键ID
          analysisDate?: string | Dayjs; // 分析日期
          reportFileUrl?: string; // 成本核算文件URL（单文件）
          imageUrls: string; // 图片URL，多个用英文逗号分隔
          remark: string; // 备注
  }

// 综合成本核算 API
export const MarketCostAnalysisApi = {
  // 查询综合成本核算分页
  getMarketCostAnalysisPage: async (params: any) => {
    return await request.get({ url: `/coal/market-cost-analysis/page`, params })
  },

  // 查询综合成本核算详情
  getMarketCostAnalysis: async (id: number) => {
    return await request.get({ url: `/coal/market-cost-analysis/get?id=` + id })
  },

  // 新增综合成本核算
  createMarketCostAnalysis: async (data: MarketCostAnalysis) => {
    return await request.post({ url: `/coal/market-cost-analysis/create`, data })
  },

  // 修改综合成本核算
  updateMarketCostAnalysis: async (data: MarketCostAnalysis) => {
    return await request.put({ url: `/coal/market-cost-analysis/update`, data })
  },

  // 删除综合成本核算
  deleteMarketCostAnalysis: async (id: number) => {
    return await request.delete({ url: `/coal/market-cost-analysis/delete?id=` + id })
  },

  /** 批量删除综合成本核算 */
  deleteMarketCostAnalysisList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/market-cost-analysis/delete-list?ids=${ids.join(',')}` })
  },

  // 导出综合成本核算 Excel
  exportMarketCostAnalysis: async (params) => {
    return await request.download({ url: `/coal/market-cost-analysis/export-excel`, params })
  },
}