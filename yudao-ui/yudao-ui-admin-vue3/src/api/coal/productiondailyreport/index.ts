import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 现场生产日报信息 */
export interface ProductionDailyReport {
          id: number; // 日报id
          reportDate?: string | Dayjs; // 日期
          shiftId: number; // 班次ID
          operatorId: number; // 集控员（操作人）ID
          shiftLeaderId: number; // 带班主任/班长ID
          startTime: string | Dayjs; // 启车时间
          coalFeedingTime: number; // 带煤时间(分钟)
          stopTime: string | Dayjs; // 停车时间
          effectiveFeedingTime: number; // 有效带煤时间(分钟)
          faultImpactTime: number; // 故障影响时间(分钟)
          rawCoalInput: number; // 入洗煤量(吨)
          hourlyCapacity: number; // 小时处理量(吨/小时)
          blockCleanCoalOutput: number; // 块精煤产量(吨)
          fineCleanCoalOutput: number; // 末精煤产量(吨)
          gangueOutput: number; // 矸石产量(吨)
          middlingCoalOutput: number; // 中煤产量(吨)
          filterPressCycles: number; // 压滤板次
          filterPressCoalAmount: number; // 压滤煤量(吨)
          filterClothUsage: number; // 滤布使用量(张)
          dischargeRecord: string; // 放舱记录
          baffleMediumAddition: number; // 挡板添加介质量(kg)
          caoAmount: number; // CaO量(kg)
          flocculantAmount: number; // 絮凝剂(kg)
          densityMd317: number; // 317密度(kg/L)
          firstAshBlockClean: number; // 第一次块精煤灰分(%)
          firstAshFineClean: number; // 第一次末精煤灰分(%)
          firstAshMiddling: number; // 第一次中煤灰分(%)
          firstAshSlime: number; // 第一次煤泥灰分(%)
          firstAshGangue: number; // 第一次矸石灰分(%)
          secondAshBlockClean: number; // 第二次块精煤灰分(%)
          secondAshFineClean: number; // 第二次末精煤灰分(%)
          secondAshMiddling: number; // 第二次中煤灰分(%)
          secondAshSlime: number; // 第二次煤泥灰分(%)
          secondAshGangue: number; // 第二次矸石灰分(%)
          impactTimeRecord: string; // 影响时间记录详情
          assignedTasks: string; // 交办事项
          startCirculatingWaterPool: number; // 启车循环水池液位
          startCleanWaterTank: number; // 启车清水桶液位
          startFineCoalLevel: number; // 启车末煤仓位
          startGranularCoalLevel: number; // 启车粒煤仓位
          startLargeBlockLevel: number; // 启车大块仓位
          startMediumBlockLevel: number; // 启车中块仓位
          startSmallBlockLevel: number; // 启车小块仓位
          startMiddlingCoalLevel: number; // 启车中煤仓位
          startGangueLevel: number; // 启车矸石仓位
          stopCirculatingWaterPool: number; // 停车循环水池液位
          stopCleanWaterTank: number; // 停车清水桶液位
          stopFineCoalLevel: number; // 停车末煤仓位
          stopGranularCoalLevel: number; // 停车粒煤仓位
          stopLargeBlockLevel: number; // 停车大块仓位
          stopMediumBlockLevel: number; // 停车中块仓位
          stopSmallBlockLevel: number; // 停车小块仓位
          stopMiddlingCoalLevel: number; // 停车中煤仓位
          stopGangueLevel: number; // 停车矸石仓位
          remarks: string; // 备注信息
  }

// 现场生产日报 API
export const ProductionDailyReportApi = {
  // 查询现场生产日报分页
  getProductionDailyReportPage: async (params: any) => {
    return await request.get({ url: `/coal/production-daily-report/page`, params })
  },

  // 查询现场生产日报详情
  getProductionDailyReport: async (id: number) => {
    return await request.get({ url: `/coal/production-daily-report/get?id=` + id })
  },

  // 新增现场生产日报
  createProductionDailyReport: async (data: ProductionDailyReport) => {
    return await request.post({ url: `/coal/production-daily-report/create`, data })
  },

  // 修改现场生产日报
  updateProductionDailyReport: async (data: ProductionDailyReport) => {
    return await request.put({ url: `/coal/production-daily-report/update`, data })
  },

  // 删除现场生产日报
  deleteProductionDailyReport: async (id: number) => {
    return await request.delete({ url: `/coal/production-daily-report/delete?id=` + id })
  },

  /** 批量删除现场生产日报 */
  deleteProductionDailyReportList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/production-daily-report/delete-list?ids=${ids.join(',')}` })
  },

  // 导出现场生产日报 Excel
  exportProductionDailyReport: async (params) => {
    return await request.download({ url: `/coal/production-daily-report/export-excel`, params })
  },
}