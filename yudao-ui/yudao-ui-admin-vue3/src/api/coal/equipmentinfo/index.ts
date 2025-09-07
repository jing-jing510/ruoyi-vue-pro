import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 设备档案信息 */
export interface EquipmentInfo {
          id: number; // 设备ID
          equipmentCode?: string; // 设备编号
          equipmentName?: string; // 设备名称
          categoryId?: number; // 设备分类ID
          parentEquipmentId: number; // 父设备ID
          model: string; // 设备型号
          manufacturer: string; // 制造厂商
          manufactureDate: string | Dayjs; // 制造日期
          installDate: string | Dayjs; // 安装日期
          commissionDate: string | Dayjs; // 投产日期
          assetNumber: string; // 资产编号
          ratedPower: number; // 额定功率(kW)
          ratedCapacity: number; // 额定处理能力(t/h)
          weight: number; // 设备重量(t)
          dimensions: string; // 外形尺寸(长x宽x高)
          voltageLevel: string; // 电压等级
          protectionLevel: string; // 防护等级
          workshop: string; // 所属车间
          location: string; // 安装位置
          coordinateX: number; // X坐标
          coordinateY: number; // Y坐标
          equipmentStatus?: number; // 设备状态
          healthLevel?: number; // 健康等级
          importanceLevel?: number; // 重要度
          responsiblePersonId: number; // 责任人ID
          maintenancePersonId: number; // 维护人ID
          operatorPersonId: number; // 操作人ID
          qrCode: string; // 二维码内容
          qrCodeUrl: string; // 二维码图片URL
          manualUrl: string; // 说明书文件URL
          drawingUrl: string; // 图纸文件URL
          supplier: string; // 供应商
          purchaseDate: string | Dayjs; // 采购日期
          purchasePrice: number; // 采购价格
          warrantyPeriod: number; // 保修期(月)
          warrantyExpireDate: string | Dayjs; // 保修到期日期
          remark: string; // 备注
    children?: EquipmentInfo[];
  }

// 设备档案 API
export const EquipmentInfoApi = {
  // 查询设备档案列表
  getEquipmentInfoList: async (params) => {
    return await request.get({ url: `/coal/equipment-info/list`, params })
  },

  // 查询设备档案详情
  getEquipmentInfo: async (id: number) => {
    return await request.get({ url: `/coal/equipment-info/get?id=` + id })
  },

  // 新增设备档案
  createEquipmentInfo: async (data: EquipmentInfo) => {
    return await request.post({ url: `/coal/equipment-info/create`, data })
  },

  // 修改设备档案
  updateEquipmentInfo: async (data: EquipmentInfo) => {
    return await request.put({ url: `/coal/equipment-info/update`, data })
  },

  // 删除设备档案
  deleteEquipmentInfo: async (id: number) => {
    return await request.delete({ url: `/coal/equipment-info/delete?id=` + id })
  },


  // 导出设备档案 Excel
  exportEquipmentInfo: async (params) => {
    return await request.download({ url: `/coal/equipment-info/export-excel`, params })
  },
}