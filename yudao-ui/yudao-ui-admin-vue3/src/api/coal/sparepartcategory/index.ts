import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 备件分类表 (树表)信息 */
export interface SparePartCategory {
          id: number; // 分类ID
          parentId?: number; // 父分类ID (0=根分类)
          categoryName?: string; // 分类名称
          categoryCode?: string; // 分类编码
          categoryLevel?: number; // 分类层级：1大类 2中类 3小类
          sort?: number; // 排序
          status?: number; // 状态：0禁用 1启用
          remark: string; // 备注
    children?: SparePartCategory[];
  }

// 备件分类表 (树表) API
export const SparePartCategoryApi = {
  // 查询备件分类表 (树表)列表
  getSparePartCategoryList: async (params) => {
    return await request.get({ url: `/coal/spare-part-category/list`, params })
  },

  // 查询备件分类表 (树表)详情
  getSparePartCategory: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-category/get?id=` + id })
  },

  // 新增备件分类表 (树表)
  createSparePartCategory: async (data: SparePartCategory) => {
    return await request.post({ url: `/coal/spare-part-category/create`, data })
  },

  // 修改备件分类表 (树表)
  updateSparePartCategory: async (data: SparePartCategory) => {
    return await request.put({ url: `/coal/spare-part-category/update`, data })
  },

  // 删除备件分类表 (树表)
  deleteSparePartCategory: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-category/delete?id=` + id })
  },


  // 导出备件分类表 (树表) Excel
  exportSparePartCategory: async (params) => {
    return await request.download({ url: `/coal/spare-part-category/export-excel`, params })
  },
}