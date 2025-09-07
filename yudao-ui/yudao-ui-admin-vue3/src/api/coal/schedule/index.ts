import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 排班管理 (主表)信息 */
export interface Schedule {
  id: number; // 排班ID
  scheduleDate?: Date; // 排班日期
  shiftSystemId?: number; // 班制ID
  scheduleStatus?: number; // 排班状态
  isProductionDay?: boolean; // 是否生产日
  productionTarget?: number; // 当日生产目标(吨)
  scheduleType?: number; // 排班类型
  approverId?: number; // 审批人ID
  approveTime?: Date; // 审批时间
  remark?: string; // 备注
  scheduleStaffs?: ScheduleStaff[]; // 人员安排列表
}

/** 人员安排 (子表)信息 */
export interface ScheduleStaff {
          id: number; // 人员安排ID
          scheduleId?: number; // 排班ID (关联主表)
          shiftId?: number; // 班次ID (关联coal_shift_system的子节点)
          userId?: number; // 员工ID
          positionType?: number; // 岗位类型
          isLeader?: boolean; // 是否班组长
          isSubstitute?: boolean; // 是否替班
          substituteReason: string; // 替班原因
          workStatus?: number; // 工作状态
          contactPhone: string; // 联系电话
          emergencyContact: string; // 紧急联系人
          emergencyPhone: string; // 紧急联系电话
  }

// 排班管理 (主表) API
export const ScheduleApi = {
  // 查询排班管理分页
  getSchedulePage: async (params: any) => {
    return await request.get({ url: `/coal/schedule/page`, params })
  },

  // 查询排班管理详情
  getSchedule: async (id: number) => {
    return await request.get({ url: `/coal/schedule/get?id=` + id })
  },

  // 新增排班管理
  createSchedule: async (data: Schedule) => {
    return await request.post({ url: `/coal/schedule/create`, data })
  },

  // 修改排班管理
  updateSchedule: async (data: Schedule) => {
    return await request.put({ url: `/coal/schedule/update`, data })
  },

  // 删除排班管理
  deleteSchedule: async (id: number) => {
    return await request.delete({ url: `/coal/schedule/delete?id=` + id })
  },

  // 批量删除排班管理
  deleteScheduleList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/schedule/delete-list?ids=${ids.join(',')}` })
  },

  // 导出排班管理 Excel
  exportSchedule: async (params) => {
    return await request.download({ url: `/coal/schedule/export-excel`, params })
  },
}

// 人员安排 (子表) API
export const ScheduleStaffApi = {
  // 查询人员安排 (子表)分页
  getScheduleStaffPage: async (params: any) => {
    return await request.get({ url: `/coal/schedule-staff/page`, params })
  },

  // 查询人员安排 (子表)详情
  getScheduleStaff: async (id: number) => {
    return await request.get({ url: `/coal/schedule-staff/get?id=` + id })
  },

  // 新增人员安排 (子表)
  createScheduleStaff: async (data: ScheduleStaff) => {
    return await request.post({ url: `/coal/schedule-staff/create`, data })
  },

  // 修改人员安排 (子表)
  updateScheduleStaff: async (data: ScheduleStaff) => {
    return await request.put({ url: `/coal/schedule-staff/update`, data })
  },

  // 删除人员安排 (子表)
  deleteScheduleStaff: async (id: number) => {
    return await request.delete({ url: `/coal/schedule-staff/delete?id=` + id })
  },

  /** 批量删除人员安排 (子表) */
  deleteScheduleStaffList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/schedule-staff/delete-list?ids=${ids.join(',')}` })
  },

  // 导出人员安排 (子表) Excel
  exportScheduleStaff: async (params) => {
    return await request.download({ url: `/coal/schedule-staff/export-excel`, params })
  },
}
