import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 质量预警记录信息 */
export interface QualityAlert {
          id: number; // 预警记录ID
          alertCode?: string; // 预警编号
          alertType?: number; // 预警类型
          alertLevel?: number; // 预警级别
          qualityItemId?: number; // 检测项目ID
          inspectionId: number; // 关联检测记录ID
          productType?: number; // 产品类型
          measuredValue: number; // 检测值
          standardValue: number; // 标准值
          thresholdValue: number; // 预警阈值
          deviation: number; // 偏差值
          alertMessage?: string; // 预警信息
          alertTime?: string | Dayjs; // 预警时间
          alertStatus?: number; // 预警状态
          handlerId: number; // 处理人ID
          handlerName: string; // 处理人姓名
          handleTime: string | Dayjs; // 处理时间
          handleMethod: string; // 处理措施
          handleResult: string; // 处理结果
          isAutoAlert?: number; // 是否自动预警：0手动 1自动
          notificationSent?: number; // 是否已发送通知：0否 1是
          notificationTime: string | Dayjs; // 通知发送时间
          recipients?: string; // 接收人列表(逗号分隔)
          recipientIds?: number[]; // 接收人ID列表(前端使用)
          remark: string; // 备注
  }

// 质量预警记录 API
export const QualityAlertApi = {
  // 查询质量预警记录分页
  getQualityAlertPage: async (params: any) => {
    return await request.get({ url: `/coal/quality-alert/page`, params })
  },

  // 查询质量预警记录详情
  getQualityAlert: async (id: number) => {
    return await request.get({ url: `/coal/quality-alert/get?id=` + id })
  },

  // 新增质量预警记录
  createQualityAlert: async (data: QualityAlert) => {
    return await request.post({ url: `/coal/quality-alert/create`, data })
  },

  // 修改质量预警记录
  updateQualityAlert: async (data: QualityAlert) => {
    return await request.put({ url: `/coal/quality-alert/update`, data })
  },

  // 删除质量预警记录
  deleteQualityAlert: async (id: number) => {
    return await request.delete({ url: `/coal/quality-alert/delete?id=` + id })
  },

  /** 批量删除质量预警记录 */
  deleteQualityAlertList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/quality-alert/delete-list?ids=${ids.join(',')}` })
  },

  // 导出质量预警记录 Excel
  exportQualityAlert: async (params) => {
    return await request.download({ url: `/coal/quality-alert/export-excel`, params })
  },

  // 发送质量预警站内信通知
  sendQualityAlertNotification: async (alertId: number) => {
    return await request.post({ url: `/coal/quality-alert/send-notification/${alertId}` })
  },

  // 获取质量预警统计数据
  getQualityAlertStatistics: async () => {
    return await request.get({ url: `/coal/quality-alert/statistics` })
  },
}
