import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 备件基础信息信息 */
export interface SparePartInfo {
          id: number; // 备件ID
          sparePartCode?: string; // 备件编号
          sparePartName?: string; // 备件名称
          categoryId?: number; // 备件分类ID
          specification: string; // 规格型号
          brand: string; // 品牌
          unit: string; // 计量单位
          sparePartType?: number; // 备件类型
          material: string; // 材质
          weight: number; // 重量(kg)
          dimensions: string; // 外形尺寸
          minStock?: number; // 最低库存预警数量
          maxStock: number; // 最高库存数量
          safetyStock: number; // 安全库存数量
          currentStock?: number; // 当前库存数量
          supplier: string; // 主要供应商
          supplierPartNo: string; // 供应商零件号
          unitPrice: number; // 单价
          leadTime: number; // 采购周期(天)
          replacementCycle: number; // 建议更换周期(天)
          averageLifespan: number; // 平均使用寿命(天)
          usageFrequency: number; // 使用频率
          storageLocation: string; // 存储位置
          storageCondition: string; // 存储条件要求
          status?: number; // 状态
          isCritical?: boolean; // 是否关键备件
          imageUrl: string; // 备件图片URL
          manualUrl: string; // 说明书文件URL
          drawingUrl: string; // 图纸文件URL
          remark: string; // 备注
  }

// 备件基础信息 API
export const SparePartInfoApi = {
  // 查询备件基础信息分页
  getSparePartInfoPage: async (params: any) => {
    return await request.get({ url: `/coal/spare-part-info/page`, params })
  },

  // 查询备件基础信息详情
  getSparePartInfo: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-info/get?id=` + id })
  },

  // 新增备件基础信息
  createSparePartInfo: async (data: SparePartInfo) => {
    return await request.post({ url: `/coal/spare-part-info/create`, data })
  },

  // 修改备件基础信息
  updateSparePartInfo: async (data: SparePartInfo) => {
    return await request.put({ url: `/coal/spare-part-info/update`, data })
  },

  // 删除备件基础信息
  deleteSparePartInfo: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-info/delete?id=` + id })
  },

  /** 批量删除备件基础信息 */
  deleteSparePartInfoList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/spare-part-info/delete-list?ids=${ids.join(',')}` })
  },

  // 导出备件基础信息 Excel
  exportSparePartInfo: async (params) => {
    return await request.download({ url: `/coal/spare-part-info/export-excel`, params })
  },

  // 获得备件库存统计信息
  getStockStatistics: async () => {
    return await request.get({ 
      url: `/coal/spare-part-info/stock-statistics`,
      headers: {
        'tenant-id': '1'
      }
    })
  },

  // 获得备件使用频率分析
  getUsageFrequencyAnalysis: async () => {
    return await request.get({ 
      url: `/coal/spare-part-info/usage-frequency-analysis`,
      headers: {
        'tenant-id': '1'
      }
    })
  },

  // 获得备件简单信息列表，用于下拉选择
  getSimpleSparePartList: async () => {
    return await request.get({ url: `/coal/spare-part-info/simple-list` })
  },
}
