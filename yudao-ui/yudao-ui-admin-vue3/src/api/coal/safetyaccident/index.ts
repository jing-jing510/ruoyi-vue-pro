import request from '@/config/axios'
import type { Dayjs } from 'dayjs'

/** 安全事故记录信息 */
export interface SafetyAccident {
  id: number
  accidentCode: string
  accidentType: number
  accidentLevel: number
  accidentTitle: string
  accidentTime: string | Dayjs
  accidentLocation: string
  weatherCondition: string
  accidentDescription: string
  accidentCause: string
  casualtiesCount: number
  economicLoss: number
  equipmentDamage: string
  environmentalImpact: string
  emergencyResponse: string
  rescueProcess: string
  reporterId: number
  reporterName: string
  reportTime: string | Dayjs
  investigatorId: number
  investigatorName: string
  investigationStartTime: string | Dayjs
  investigationEndTime: string | Dayjs
  investigationResult: string
  preventiveMeasures: string
  responsiblePersonId: number
  responsiblePersonName: string
  punishmentMeasures: string
  accidentStatus: number
  approvalStatus: number
  approverId: number
  approverName: string
  approvalTime: string | Dayjs
  approvalRemark: string
  remark: string
}

// 安全事故记录 API
export const SafetyAccidentApi = {
  // 查询安全事故记录分页
  getSafetyAccidentPage: async (params: any) => {
    return await request.get({ url: `/coal/safety-accident/page`, params })
  },

  // 查询安全事故记录详情
  getSafetyAccident: async (id: number) => {
    return await request.get({ url: `/coal/safety-accident/get?id=` + id })
  },

  // 新增安全事故记录
  createSafetyAccident: async (data: SafetyAccident) => {
    return await request.post({ url: `/coal/safety-accident/create`, data })
  },

  // 修改安全事故记录
  updateSafetyAccident: async (data: SafetyAccident) => {
    return await request.put({ url: `/coal/safety-accident/update`, data })
  },

  // 删除安全事故记录
  deleteSafetyAccident: async (id: number) => {
    return await request.delete({ url: `/coal/safety-accident/delete?id=` + id })
  },

  /** 批量删除安全事故记录 */
  deleteSafetyAccidentList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/safety-accident/delete-list?ids=${ids.join(',')}` })
  },

  // 导出安全事故记录 Excel
  exportSafetyAccident: async (params) => {
    return await request.download({ url: `/coal/safety-accident/export-excel`, params })
  },

  // 获取安全事故记录统计数据
  getSafetyAccidentStatistics: async () => {
    return await request.get({ url: `/coal/safety-accident/statistics` })
  }
}