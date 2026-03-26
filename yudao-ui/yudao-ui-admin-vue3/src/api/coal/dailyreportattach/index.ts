import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 生产日报附件上传信息 */
export interface DailyReportAttach {
          id: number; // 主键ID
          reportDate?: string | Dayjs; // 日报日期
          reportFileUrl?: string; // 日报文件URL（单文件）
          imageUrls: string; // 图片URL，多个用英文逗号分隔
          remark: string; // 备注
  }

// 生产日报附件上传 API
export const DailyReportAttachApi = {
  // 查询生产日报附件上传分页
  getDailyReportAttachPage: async (params: any) => {
    return await request.get({ url: `/coal/daily-report-attach/page`, params })
  },

  // 查询生产日报附件上传详情
  getDailyReportAttach: async (id: number) => {
    return await request.get({ url: `/coal/daily-report-attach/get?id=` + id })
  },

  // 新增生产日报附件上传
  createDailyReportAttach: async (data: DailyReportAttach) => {
    return await request.post({ url: `/coal/daily-report-attach/create`, data })
  },

  // 修改生产日报附件上传
  updateDailyReportAttach: async (data: DailyReportAttach) => {
    return await request.put({ url: `/coal/daily-report-attach/update`, data })
  },

  // 删除生产日报附件上传
  deleteDailyReportAttach: async (id: number) => {
    return await request.delete({ url: `/coal/daily-report-attach/delete?id=` + id })
  },

  /** 批量删除生产日报附件上传 */
  deleteDailyReportAttachList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/daily-report-attach/delete-list?ids=${ids.join(',')}` })
  },

  // 导出生产日报附件上传 Excel
  exportDailyReportAttach: async (params) => {
    return await request.download({ url: `/coal/daily-report-attach/export-excel`, params })
  },
}