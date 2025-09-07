import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 设备分类表 (树表)信息 */
export interface EquipmentCategory {
          id: number; // 分类ID
          parentId?: number; // 父分类ID
          categoryName?: string; // 分类名称
          categoryCode?: string; // 分类编码
          categoryLevel?: number; // 分类层级
          sort?: number; // 排序
          status?: number; // 状态
          remark: string; // 备注
    children?: EquipmentCategory[];
  }

// 设备分类表 (树表) API
export const EquipmentCategoryApi = {
  // 查询设备分类表 (树表)列表
  getEquipmentCategoryList: async (params) => {
    return await request.get({ url: `/coal/equipment-category/list`, params })
  },

  // 查询设备分类表 (树表)详情
  getEquipmentCategory: async (id: number) => {
    return await request.get({ url: `/coal/equipment-category/get?id=` + id })
  },

  // 新增设备分类表 (树表)
  createEquipmentCategory: async (data: EquipmentCategory) => {
    return await request.post({ url: `/coal/equipment-category/create`, data })
  },

  // 修改设备分类表 (树表)
  updateEquipmentCategory: async (data: EquipmentCategory) => {
    return await request.put({ url: `/coal/equipment-category/update`, data })
  },

  // 删除设备分类表 (树表)
  deleteEquipmentCategory: async (id: number) => {
    return await request.delete({ url: `/coal/equipment-category/delete?id=` + id })
  },


  // 导出设备分类表 (树表) Excel
  exportEquipmentCategory: async (params) => {
    return await request.download({ url: `/coal/equipment-category/export-excel`, params })
  },
}