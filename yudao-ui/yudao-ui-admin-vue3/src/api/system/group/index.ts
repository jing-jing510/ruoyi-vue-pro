import request from '@/config/axios'

/** 单表生成用户组信息 */
export interface Group {
          id: number; // 编号
          name?: string; // 名字
          description: string; // 描述
          status?: number; // 状态
  }

// 单表生成用户组 API
export const GroupApi = {
  // 查询单表生成用户组分页
  getGroupPage: async (params: any) => {
    return await request.get({ url: `/system/group/page`, params })
  },

  // 查询单表生成用户组详情
  getGroup: async (id: number) => {
    return await request.get({ url: `/system/group/get?id=` + id })
  },

  // 新增单表生成用户组
  createGroup: async (data: Group) => {
    return await request.post({ url: `/system/group/create`, data })
  },

  // 修改单表生成用户组
  updateGroup: async (data: Group) => {
    return await request.put({ url: `/system/group/update`, data })
  },

  // 删除单表生成用户组
  deleteGroup: async (id: number) => {
    return await request.delete({ url: `/system/group/delete?id=` + id })
  },

  /** 批量删除单表生成用户组 */
  deleteGroupList: async (ids: number[]) => {
    return await request.delete({ url: `/system/group/delete-list?ids=${ids.join(',')}` })
  },

  // 导出单表生成用户组 Excel
  exportGroup: async (params) => {
    return await request.download({ url: `/system/group/export-excel`, params })
  },
}
