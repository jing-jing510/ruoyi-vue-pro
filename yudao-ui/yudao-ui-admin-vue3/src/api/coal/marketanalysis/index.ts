import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 市场分析信息 */
export interface MarketAnalysis {
          id: number; // 主键ID
          analysisDate?: string | Dayjs; // 分析日期
          reportFileUrl?: string; // 市场分析文件URL（单文件）
          imageUrls: string; // 图片URL，多个用英文逗号分隔
          remark: string; // 备注
  }

// 市场分析 API
export const MarketAnalysisApi = {
  // 查询市场分析分页
  getMarketAnalysisPage: async (params: any) => {
    return await request.get({ url: `/coal/market-analysis/page`, params })
  },

  // 查询市场分析详情
  getMarketAnalysis: async (id: number) => {
    return await request.get({ url: `/coal/market-analysis/get?id=` + id })
  },

  // 新增市场分析
  createMarketAnalysis: async (data: MarketAnalysis) => {
    return await request.post({ url: `/coal/market-analysis/create`, data })
  },

  // 修改市场分析
  updateMarketAnalysis: async (data: MarketAnalysis) => {
    return await request.put({ url: `/coal/market-analysis/update`, data })
  },

  // 删除市场分析
  deleteMarketAnalysis: async (id: number) => {
    return await request.delete({ url: `/coal/market-analysis/delete?id=` + id })
  },

  /** 批量删除市场分析 */
  deleteMarketAnalysisList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/market-analysis/delete-list?ids=${ids.join(',')}` })
  },

  // 导出市场分析 Excel
  exportMarketAnalysis: async (params) => {
    return await request.download({ url: `/coal/market-analysis/export-excel`, params })
  },
}