import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 备件库存记录信息 */
export interface SparePartStock {
          id: number; // 库存记录ID
          sparePartId?: number; // 备件ID
          warehouseLocation?: string; // 仓库位置
          stockType?: number; // 库存类型
          quantity?: number; // 库存数量
          unitCost: number; // 单位成本
          totalCost: number; // 总成本
          batchNo: string; // 批次号
          productionDate: string | Dayjs; // 生产日期
          expiryDate: string | Dayjs; // 有效期至
          lastInDate: string | Dayjs; // 最后入库时间
          lastOutDate: string | Dayjs; // 最后出库时间
          remark: string; // 备注
  }

// 备件库存记录 API
export const SparePartStockApi = {
  // 查询备件库存记录分页
  getSparePartStockPage: async (params: any) => {
    return await request.get({ url: `/coal/spare-part-stock/page`, params })
  },

  // 查询备件库存记录详情
  getSparePartStock: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-stock/get?id=` + id })
  },

  // 新增备件库存记录
  createSparePartStock: async (data: SparePartStock) => {
    return await request.post({ url: `/coal/spare-part-stock/create`, data })
  },

  // 修改备件库存记录
  updateSparePartStock: async (data: SparePartStock) => {
    return await request.put({ url: `/coal/spare-part-stock/update`, data })
  },

  // 删除备件库存记录
  deleteSparePartStock: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-stock/delete?id=` + id })
  },

  /** 批量删除备件库存记录 */
  deleteSparePartStockList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/spare-part-stock/delete-list?ids=${ids.join(',')}` })
  },

  // 导出备件库存记录 Excel
  exportSparePartStock: async (params) => {
    return await request.download({ url: `/coal/spare-part-stock/export-excel`, params })
  },
}