import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 备件出入库记录信息 */
export interface SparePartInventoryLog {
          id: number; // 记录ID
          sparePartId?: number; // 备件ID
          operationType?: number; // 操作类型
          operationDate?: string | Dayjs; // 操作时间
          quantity?: number; // 数量(正数入库，负数出库)
          unitPrice: number; // 单价
          totalAmount: number; // 总金额
          operatorId: number; // 操作人ID
          warehouseLocation: string; // 仓库位置
          batchNo: string; // 批次号
          equipmentId: number; // 关联设备ID(出库时)
          workOrderId: number; // 关联工单ID
          supplierId: number; // 供应商ID(入库时)
          purchaseOrderNo: string; // 采购单号
          approverId: number; // 审批人ID
          approveTime: string | Dayjs; // 审批时间
          approveStatus: number; // 审批状态
          operationReason: string; // 操作原因
          remark: string; // 备注
  }

// 备件出入库记录 API
export const SparePartInventoryLogApi = {
  // 查询备件出入库记录分页
  getSparePartInventoryLogPage: async (params: any) => {
    return await request.get({ url: `/coal/spare-part-inventory-log/page`, params })
  },

  // 查询备件出入库记录详情
  getSparePartInventoryLog: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-inventory-log/get?id=` + id })
  },

  // 新增备件出入库记录
  createSparePartInventoryLog: async (data: SparePartInventoryLog) => {
    return await request.post({ url: `/coal/spare-part-inventory-log/create`, data })
  },

  // 修改备件出入库记录
  updateSparePartInventoryLog: async (data: SparePartInventoryLog) => {
    return await request.put({ url: `/coal/spare-part-inventory-log/update`, data })
  },

  // 删除备件出入库记录
  deleteSparePartInventoryLog: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-inventory-log/delete?id=` + id })
  },

  /** 批量删除备件出入库记录 */
  deleteSparePartInventoryLogList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/spare-part-inventory-log/delete-list?ids=${ids.join(',')}` })
  },

  // 导出备件出入库记录 Excel
  exportSparePartInventoryLog: async (params) => {
    return await request.download({ url: `/coal/spare-part-inventory-log/export-excel`, params })
  },
}