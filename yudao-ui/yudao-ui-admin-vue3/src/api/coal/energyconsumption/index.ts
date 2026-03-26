import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 能源消耗记录信息 */
export interface EnergyConsumption {
          id: number; // 消耗记录ID
          recordCode?: string; // 记录编号
          energyTypeId?: number; // 能源类型ID
          consumptionDate?: string | Dayjs; // 消耗日期
          shiftId: number; // 班次ID
          consumptionValue?: number; // 消耗量
          unitCost: number; // 单位成本(元/单位)
          totalCost: number; // 总成本(元)
          dataSource?: number; // 数据来源
          collectionTime: string | Dayjs; // 采集时间
          recorderId: number; // 记录人ID
          recorderName: string; // 记录人姓名
          verificationStatus?: number; // 验证状态
          verifierId: number; // 验证人ID
          verificationTime: string | Dayjs; // 验证时间
          verificationRemark: string; // 验证备注
          remark: string; // 备注
  }

// 能源消耗记录 API
export const EnergyConsumptionApi = {
  // 查询能源消耗记录分页
  getEnergyConsumptionPage: async (params: any) => {
    return await request.get({ url: `/coal/energy-consumption/page`, params })
  },

  // 查询能源消耗记录详情
  getEnergyConsumption: async (id: number) => {
    return await request.get({ url: `/coal/energy-consumption/get?id=` + id })
  },

  // 新增能源消耗记录
  createEnergyConsumption: async (data: EnergyConsumption) => {
    return await request.post({ url: `/coal/energy-consumption/create`, data })
  },

  // 修改能源消耗记录
  updateEnergyConsumption: async (data: EnergyConsumption) => {
    return await request.put({ url: `/coal/energy-consumption/update`, data })
  },

  // 删除能源消耗记录
  deleteEnergyConsumption: async (id: number) => {
    return await request.delete({ url: `/coal/energy-consumption/delete?id=` + id })
  },

  /** 批量删除能源消耗记录 */
  deleteEnergyConsumptionList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/energy-consumption/delete-list?ids=${ids.join(',')}` })
  },

  // 导出能源消耗记录 Excel
  exportEnergyConsumption: async (params) => {
    return await request.download({ url: `/coal/energy-consumption/export-excel`, params })
  },

  // 获得能源消耗记录统计信息
  getEnergyConsumptionStatistics: async () => {
    return await request.get({ url: `/coal/energy-consumption/statistics` })
  },
}
