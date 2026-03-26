import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 质量标准信息 */
export interface QualityStandard {
          id: number; // 质量标准ID
          standardCode?: string; // 标准编码
          standardName?: string; // 标准名称
          productType?: number; // 产品类型
          productSpecification: string; // 产品规格
          qualityItemId?: number; // 检测项目ID
          standardValue?: number; // 标准值
          maxValue: number; // 最大允许值
          minValue: number; // 最小允许值
          tolerance: number; // 允许公差
          standardType?: number; // 标准类型
          standardSource: string; // 标准来源（如：GB/T标准号）
          effectiveDate?: string | Dayjs; // 生效日期
          expiryDate: string | Dayjs; // 失效日期
          version: string; // 版本号
          status?: number; // 状态
          approverId: number; // 审批人ID
          approveTime: string | Dayjs; // 审批时间
          remark: string; // 备注
  }

// 质量标准 API
export const QualityStandardApi = {
  // 查询质量标准分页
  getQualityStandardPage: async (params: any) => {
    return await request.get({ url: `/coal/quality-standard/page`, params })
  },

  // 查询质量标准详情
  getQualityStandard: async (id: number) => {
    return await request.get({ url: `/coal/quality-standard/get?id=` + id })
  },

  // 新增质量标准
  createQualityStandard: async (data: QualityStandard) => {
    return await request.post({ url: `/coal/quality-standard/create`, data })
  },

  // 修改质量标准
  updateQualityStandard: async (data: QualityStandard) => {
    return await request.put({ url: `/coal/quality-standard/update`, data })
  },

  // 删除质量标准
  deleteQualityStandard: async (id: number) => {
    return await request.delete({ url: `/coal/quality-standard/delete?id=` + id })
  },

  /** 批量删除质量标准 */
  deleteQualityStandardList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/quality-standard/delete-list?ids=${ids.join(',')}` })
  },

  // 导出质量标准 Excel
  exportQualityStandard: async (params) => {
    return await request.download({ url: `/coal/quality-standard/export-excel`, params })
  },
}