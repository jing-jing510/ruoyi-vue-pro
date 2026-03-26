import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 质量检测项目信息 */
export interface QualityItem {
          id: number; // 检测项目ID
          itemCode?: string; // 检测项目编码
          itemName?: string; // 检测项目名称
          itemType?: number; // 检测类型
          unit?: string; // 计量单位
          detectionMethod: string; // 检测方法
          equipmentRequired: string; // 所需设备
          standardValue: number; // 标准值
          maxValue: number; // 最大允许值
          minValue: number; // 最小允许值
          warningUpperLimit: number; // 预警上限
          warningLowerLimit: number; // 预警下限
          precisionDigits: number; // 精度位数
          sort?: number; // 显示顺序
          status?: number; // 状态
          remark: string; // 备注
  }

// 质量检测项目 API
export const QualityItemApi = {
  // 查询质量检测项目分页
  getQualityItemPage: async (params: any) => {
    return await request.get({ url: `/coal/quality-item/page`, params })
  },

  // 查询质量检测项目详情
  getQualityItem: async (id: number) => {
    return await request.get({ url: `/coal/quality-item/get?id=` + id })
  },

  // 新增质量检测项目
  createQualityItem: async (data: QualityItem) => {
    return await request.post({ url: `/coal/quality-item/create`, data })
  },

  // 修改质量检测项目
  updateQualityItem: async (data: QualityItem) => {
    return await request.put({ url: `/coal/quality-item/update`, data })
  },

  // 删除质量检测项目
  deleteQualityItem: async (id: number) => {
    return await request.delete({ url: `/coal/quality-item/delete?id=` + id })
  },

  /** 批量删除质量检测项目 */
  deleteQualityItemList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/quality-item/delete-list?ids=${ids.join(',')}` })
  },

  // 导出质量检测项目 Excel
  exportQualityItem: async (params) => {
    return await request.download({ url: `/coal/quality-item/export-excel`, params })
  },
}