import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 质量检测数据信息 */
export interface QualityData {
          id: number; // 检测数据ID
          inspectionId?: number; // 检测记录ID
          qualityItemId?: number; // 检测项目ID
          qualityItemCode?: string; // 检测项目编码
          qualityItemName?: string; // 检测项目名称
          measuredValue?: number; // 检测值
          unit?: string; // 计量单位
          standardValue: number; // 标准值
          deviation: number; // 偏差值
          deviationRate: number; // 偏差率(%)
          isQualified?: number; // 是否合格
          detectionMethod: string; // 检测方法
          detectionEquipment: string; // 检测设备
          detectionTime?: string | Dayjs; // 检测时间
          operatorId: number; // 操作员ID
          operatorName: string; // 操作员姓名
          retestCount: number; // 复检次数
          isRetest?: number; // 是否复检
          originalValue: number; // 原始检测值（复检时记录）
          remark: string; // 备注
}

/** 质量检测数据统计信息 */
export interface QualityDataStatistics {
  totalCount: number
  qualifiedCount: number
  unqualifiedCount: number
  qualifiedRate: number
  todayCount: number
  monthlyCount: number
  retestCount: number
}

/** 质量检测记录信息 */
export interface QualityInspection {
          id: number; // 检测记录ID
          inspectionCode?: string; // 检测编号
          inspectionDate?: string | Dayjs; // 检测时间
          shiftId: number; // 班次ID
          inspectorId?: number; // 检测人员ID
          inspectorName?: string; // 检测人员姓名
          productType?: number; // 产品类型
          productSpecification: string; // 产品规格
          samplingLocation?: string; // 采样地点
          samplingMethod?: number; // 采样方法
          sampleCode: string; // 样品编号
          detectionType?: number; // 检测类型
          systemLocation: string; // 系统位置
          batchNumber: string; // 批次号
          inspectionStatus?: number; // 检测状态
          reviewerId: number; // 审核人员ID
          reviewerName: string; // 审核人员姓名
          reviewTime: string | Dayjs; // 审核时间
          reviewStatus?: number; // 审核状态
          reviewRemark: string; // 审核备注
          temperature: number; // 环境温度(℃)
          humidity: number; // 环境湿度(%)
          weatherCondition: string; // 天气情况
          remark: string; // 备注信息
  }

// 质量检测记录 API
export const QualityInspectionApi = {
  // 查询质量检测记录分页
  getQualityInspectionPage: async (params: any) => {
    return await request.get({ url: `/coal/quality-inspection/page`, params })
  },

  // 查询质量检测记录详情
  getQualityInspection: async (id: number) => {
    return await request.get({ url: `/coal/quality-inspection/get?id=` + id })
  },

  // 新增质量检测记录
  createQualityInspection: async (data: QualityInspection) => {
    return await request.post({ url: `/coal/quality-inspection/create`, data })
  },

  // 修改质量检测记录
  updateQualityInspection: async (data: QualityInspection) => {
    return await request.put({ url: `/coal/quality-inspection/update`, data })
  },

  // 删除质量检测记录
  deleteQualityInspection: async (id: number) => {
    return await request.delete({ url: `/coal/quality-inspection/delete?id=` + id })
  },

  /** 批量删除质量检测记录 */
  deleteQualityInspectionList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/quality-inspection/delete-list?ids=${ids.join(',')}` })
  },

  // 导出质量检测记录 Excel
  exportQualityInspection: async (params) => {
    return await request.download({ url: `/coal/quality-inspection/export-excel`, params })
  },

// ==================== 子表（质量检测数据） ====================

  // 获得质量检测数据分页
  getQualityDataPage: async (params) => {
    return await request.get({ url: `/coal/quality-inspection/quality-data/page`, params })
  },
  // 新增质量检测数据
  createQualityData: async (data: QualityData) => {
    return await request.post({ url: `/coal/quality-inspection/quality-data/create`, data })
  },

  // 修改质量检测数据
  updateQualityData: async (data: QualityData) => {
    return await request.put({ url: `/coal/quality-inspection/quality-data/update`, data })
  },

  // 删除质量检测数据
  deleteQualityData: async (id: number) => {
    return await request.delete({ url: `/coal/quality-inspection/quality-data/delete?id=` + id })
  },

  /** 批量删除质量检测数据 */
  deleteQualityDataList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/quality-inspection/quality-data/delete-list?ids=${ids.join(',')}` })
  },

  // 获得质量检测数据
  getQualityData: async (id: number) => {
    return await request.get({ url: `/coal/quality-inspection/quality-data/get?id=` + id })
  },

  // 获得质量检测数据统计
  getQualityDataStatistics: async (): Promise<QualityDataStatistics> => {
    return await request.get({ url: `/coal/quality-data/statistics` })
  },
}
