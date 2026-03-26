import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 能源预警记录信息 */
export interface EnergyAlert {
          id: number; // 预警ID
          alertCode?: string; // 预警编号
          energyTypeId?: number; // 能源类型ID
          alertType?: number; // 预警类型
          alertLevel?: number; // 预警级别
          alertTitle?: string; // 预警标题
          alertContent?: string; // 预警内容
          currentValue: number; // 当前值
          thresholdValue: number; // 阈值
          deviationRate: number; // 偏差率(%)
          alertTime?: string | Dayjs; // 预警时间
          alertStatus?: number; // 预警状态
          handlerId: number; // 处理人ID
          handlerName: string; // 处理人姓名
          handleTime: string | Dayjs; // 处理时间
          handleResult: string; // 处理结果
          handleRemark: string; // 处理备注
  }

// 能源预警记录 API
export const EnergyAlertApi = {
  // 查询能源预警记录分页
  getEnergyAlertPage: async (params: any) => {
    return await request.get({ url: `/coal/energy-alert/page`, params })
  },

  // 查询能源预警记录详情
  getEnergyAlert: async (id: number) => {
    return await request.get({ url: `/coal/energy-alert/get?id=` + id })
  },

  // 新增能源预警记录
  createEnergyAlert: async (data: EnergyAlert) => {
    return await request.post({ url: `/coal/energy-alert/create`, data })
  },

  // 修改能源预警记录
  updateEnergyAlert: async (data: EnergyAlert) => {
    return await request.put({ url: `/coal/energy-alert/update`, data })
  },

  // 删除能源预警记录
  deleteEnergyAlert: async (id: number) => {
    return await request.delete({ url: `/coal/energy-alert/delete?id=` + id })
  },

  /** 批量删除能源预警记录 */
  deleteEnergyAlertList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/energy-alert/delete-list?ids=${ids.join(',')}` })
  },

  // 导出能源预警记录 Excel
  exportEnergyAlert: async (params) => {
    return await request.download({ url: `/coal/energy-alert/export-excel`, params })
  },
}