import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 能源类型配置信息 */
export interface EnergyType {
          id: number; // 能源类型ID
          typeCode?: string; // 能源类型编码
          typeName?: string; // 能源类型名称
          unit?: string; // 计量单位
          unitPrice: number; // 单价(元/单位)
          isRealTime?: boolean; // 是否实时采集
          dataSource: string; // 数据来源
          collectionInterval: number; // 采集间隔(分钟)
          warningThreshold: number; // 预警阈值
          sort?: number; // 排序
          status?: number; // 状态：0禁用 1启用
          remark: string; // 备注
  }

// 能源类型配置 API
export const EnergyTypeApi = {
  // 查询能源类型配置分页
  getEnergyTypePage: async (params: any) => {
    return await request.get({ url: `/coal/energy-type/page`, params })
  },

  // 查询能源类型配置详情
  getEnergyType: async (id: number) => {
    return await request.get({ url: `/coal/energy-type/get?id=` + id })
  },

  // 新增能源类型配置
  createEnergyType: async (data: EnergyType) => {
    return await request.post({ url: `/coal/energy-type/create`, data })
  },

  // 修改能源类型配置
  updateEnergyType: async (data: EnergyType) => {
    return await request.put({ url: `/coal/energy-type/update`, data })
  },

  // 删除能源类型配置
  deleteEnergyType: async (id: number) => {
    return await request.delete({ url: `/coal/energy-type/delete?id=` + id })
  },

  /** 批量删除能源类型配置 */
  deleteEnergyTypeList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/energy-type/delete-list?ids=${ids.join(',')}` })
  },

  // 导出能源类型配置 Excel
  exportEnergyType: async (params) => {
    return await request.download({ url: `/coal/energy-type/export-excel`, params })
  },

  // 获得能源类型配置简单列表，用于下拉选择
  getSimpleEnergyTypeList: async () => {
    return await request.get({ url: `/coal/energy-type/simple-list` })
  },
}
