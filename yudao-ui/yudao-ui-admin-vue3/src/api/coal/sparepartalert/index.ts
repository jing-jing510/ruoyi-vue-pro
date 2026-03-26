import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 备件预警记录信息 */
export interface SparePartAlert {
          id: number; // 预警ID
          sparePartId?: number; // 备件ID
          alertType?: number; // 预警类型
          alertLevel?: number; // 预警级别
          alertStatus?: number; // 预警状态
          alertTitle?: string; // 预警标题
          alertMessage?: string; // 预警信息
          currentStock: number; // 当前库存
          thresholdValue: number; // 阈值
          equipmentId: number; // 关联设备ID(更换提醒时)
          handlerId: number; // 处理人ID
          handleTime: string | Dayjs; // 处理时间
          handleAction: string; // 处理措施
          handleRemark: string; // 处理备注
          isSent?: boolean; // 是否已发送通知：1是 0否
          sendTime: string | Dayjs; // 发送时间
          recipients: string; // 接收人列表(逗号分隔)
  }

// 备件预警记录 API
export const SparePartAlertApi = {
  // 查询备件预警记录分页
  getSparePartAlertPage: async (params: any) => {
    return await request.get({ 
      url: `/coal/spare-part-alert/page`, 
      params,
      headers: {
        'tenant-id': '1'
      }
    })
  },

  // 查询备件预警记录详情
  getSparePartAlert: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-alert/get?id=` + id })
  },

  // 新增备件预警记录
  createSparePartAlert: async (data: SparePartAlert) => {
    return await request.post({ url: `/coal/spare-part-alert/create`, data })
  },

  // 修改备件预警记录
  updateSparePartAlert: async (data: SparePartAlert) => {
    return await request.put({ url: `/coal/spare-part-alert/update`, data })
  },

  // 删除备件预警记录
  deleteSparePartAlert: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-alert/delete?id=` + id })
  },

  /** 批量删除备件预警记录 */
  deleteSparePartAlertList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/spare-part-alert/delete-list?ids=${ids.join(',')}` })
  },

  // 导出备件预警记录 Excel
  exportSparePartAlert: async (params) => {
    return await request.download({ url: `/coal/spare-part-alert/export-excel`, params })
  },

  // 发送预警通知
  sendNotification: async (id: number) => {
    return await request.post({ 
      url: `/coal/spare-part-alert/send-notification/${id}`,
      headers: {
        'tenant-id': '1'
      }
    })
  },
}
