import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 洗选分析报告信息 */
export interface MarketWashingReport {
          id: number; // 主键ID
          reportDate?: string | Dayjs; // 报告日期
          reportFileUrl?: string; // 洗选报告文件URL（单文件）
          imageUrls: string; // 图片URL，多个用英文逗号分隔
          remark: string; // 备注
  }

// 洗选分析报告 API
export const MarketWashingReportApi = {
  // 查询洗选分析报告分页
  getMarketWashingReportPage: async (params: any) => {
    return await request.get({ url: `/coal/market-washing-report/page`, params })
  },

  // 查询洗选分析报告详情
  getMarketWashingReport: async (id: number) => {
    return await request.get({ url: `/coal/market-washing-report/get?id=` + id })
  },

  // 新增洗选分析报告
  createMarketWashingReport: async (data: MarketWashingReport) => {
    return await request.post({ url: `/coal/market-washing-report/create`, data })
  },

  // 修改洗选分析报告
  updateMarketWashingReport: async (data: MarketWashingReport) => {
    return await request.put({ url: `/coal/market-washing-report/update`, data })
  },

  // 删除洗选分析报告
  deleteMarketWashingReport: async (id: number) => {
    return await request.delete({ url: `/coal/market-washing-report/delete?id=` + id })
  },

  /** 批量删除洗选分析报告 */
  deleteMarketWashingReportList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/market-washing-report/delete-list?ids=${ids.join(',')}` })
  },

  // 导出洗选分析报告 Excel
  exportMarketWashingReport: async (params) => {
    return await request.download({ url: `/coal/market-washing-report/export-excel`, params })
  },
}