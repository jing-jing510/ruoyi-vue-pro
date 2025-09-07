import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 班制与班次设置 (树表)信息 */
export interface ShiftSystem {
          id: number; // ID
          parentId?: number; // 父ID
          name?: string; // 名称 
          code: string; // 编码
          isProduction: number; // 是否生产班制
          startTime: localtime; // 开始时间 
          endTime: localtime; // 结束时间 
          shiftType: number; // 班次类型
          sort?: number; // 排序
          status?: number; // 状态：0禁用 1启用
    children?: ShiftSystem[];
  }

// 班制与班次设置 (树表) API
export const ShiftSystemApi = {
  // 查询班制与班次设置 (树表)列表
  getShiftSystemList: async (params) => {
    return await request.get({ url: `/coal/shift-system/list`, params })
  },

  // 查询班制与班次设置 (树表)详情
  getShiftSystem: async (id: number) => {
    return await request.get({ url: `/coal/shift-system/get?id=` + id })
  },

  // 新增班制与班次设置 (树表)
  createShiftSystem: async (data: ShiftSystem) => {
    return await request.post({ url: `/coal/shift-system/create`, data })
  },

  // 修改班制与班次设置 (树表)
  updateShiftSystem: async (data: ShiftSystem) => {
    return await request.put({ url: `/coal/shift-system/update`, data })
  },

  // 删除班制与班次设置 (树表)
  deleteShiftSystem: async (id: number) => {
    return await request.delete({ url: `/coal/shift-system/delete?id=` + id })
  },


  // 导出班制与班次设置 (树表) Excel
  exportShiftSystem: async (params) => {
    return await request.download({ url: `/coal/shift-system/export-excel`, params })
  },
}