import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 报修单信息 */
export interface RepairRequest {
          id: number; // 报修单ID
          requestCode?: string; // 报修单编号
          equipmentId?: number; // 设备ID
          equipmentName?: string; // 设备名称
          equipmentLocation: string; // 设备位置
          faultType?: number; // 故障类型
          faultLevel?: number; // 故障级别
          faultDescription?: string; // 故障描述
          faultSymptoms: string; // 故障现象
          faultCause: string; // 故障原因分析
          impactAssessment: string; // 影响评估
          urgencyLevel?: number; // 紧急程度
          requestStatus?: number; // 报修状态
          reporterId?: number; // 报修人ID
          reporterName?: string; // 报修人姓名
          reporterPhone: string; // 报修人电话
          reportTime?: string | Dayjs; // 报修时间
          faultPhotos: string; // 故障照片(JSON格式)
          faultVideos: string; // 故障视频(JSON格式)
          faultAudio: string; // 故障语音(JSON格式)
          assignedPerson: string; // 指派处理人
          assignedTeam: string; // 指派班组
          assignmentTime: string | Dayjs; // 派单时间
          expectedRepairTime: string | Dayjs; // 预计修复时间
          actualStartTime: string | Dayjs; // 实际开始时间
          actualEndTime: string | Dayjs; // 实际结束时间
          repairDuration: number; // 修复耗时(小时)
          repairMethod: string; // 修复方法
          replacedParts: string; // 更换部件(JSON格式)
          repairCost: number; // 修复费用
          repairQuality: number; // 修复质量
          testResult: string; // 测试结果
          preventionMeasures: string; // 预防措施
          satisfactionRating: number; // 满意度评价
          feedbackNotes: string; // 反馈意见
          closeTime: string | Dayjs; // 关闭时间
          closeReason: string; // 关闭原因
          remark: string; // 备注
  }

// 报修单 API
export const RepairRequestApi = {
  // 查询报修单分页
  getRepairRequestPage: async (params: any) => {
    return await request.get({ url: `/coal/repair-request/page`, params })
  },

  // 查询报修单详情
  getRepairRequest: async (id: number) => {
    return await request.get({ url: `/coal/repair-request/get?id=` + id })
  },

  // 新增报修单
  createRepairRequest: async (data: RepairRequest) => {
    return await request.post({ url: `/coal/repair-request/create`, data })
  },

  // 修改报修单
  updateRepairRequest: async (data: RepairRequest) => {
    return await request.put({ url: `/coal/repair-request/update`, data })
  },

  // 删除报修单
  deleteRepairRequest: async (id: number) => {
    return await request.delete({ url: `/coal/repair-request/delete?id=` + id })
  },

  /** 批量删除报修单 */
  deleteRepairRequestList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/repair-request/delete-list?ids=${ids.join(',')}` })
  },

  // 导出报修单 Excel
  exportRepairRequest: async (params) => {
    return await request.download({ url: `/coal/repair-request/export-excel`, params })
  },
}