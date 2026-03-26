import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 现场生产日报信息 */
export interface ProductionDailyReport {
          id: number; // 日报id
          reportDate?: string | Dayjs; // 日期
          shiftId: number; // 班次ID
          operatorId: number; // 集控员（操作人）ID
          shiftLeaderId: number; // 带班主任/班长ID
          startTime: string | Dayjs; // 启车时间
          coalFeedingTime: number; // 带煤时间(分钟)
          stopTime: string | Dayjs; // 停车时间
          effectiveFeedingTime: number; // 有效带煤时间(分钟)
          faultImpactTime: number; // 故障影响时间(分钟)
          rawCoalInput: number; // 入洗煤量(吨)
          hourlyCapacity: number; // 小时处理量(吨/小时)
          blockCleanCoalOutput: number; // 块精煤产量(吨)
          fineCleanCoalOutput: number; // 末精煤产量(吨)
          gangueOutput: number; // 矸石产量(吨)
          middlingCoalOutput: number; // 中煤产量(吨)
          filterPressCycles: number; // 压滤板次
          filterPressCoalAmount: number; // 压滤煤量(吨)
          filterClothUsage: number; // 滤布使用量(张)
          dischargeRecord: string; // 放舱记录
          baffleMediumAddition: number; // 挡板添加介质量(kg)
          caoAmount: number; // CaO量(kg)
          flocculantAmount: number; // 絮凝剂(kg)
          densityMd317: number; // 317密度(kg/L)
          firstAshBlockClean: number; // 第一次块精煤灰分(%)
          firstAshFineClean: number; // 第一次末精煤灰分(%)
          firstAshMiddling: number; // 第一次中煤灰分(%)
          firstAshSlime: number; // 第一次煤泥灰分(%)
          firstAshGangue: number; // 第一次矸石灰分(%)
          secondAshBlockClean: number; // 第二次块精煤灰分(%)
          secondAshFineClean: number; // 第二次末精煤灰分(%)
          secondAshMiddling: number; // 第二次中煤灰分(%)
          secondAshSlime: number; // 第二次煤泥灰分(%)
          secondAshGangue: number; // 第二次矸石灰分(%)
          impactTimeRecord: string; // 影响时间记录详情
          assignedTasks: string; // 交办事项
          startCirculatingWaterPool: number; // 启车循环水池液位
          startCleanWaterTank: number; // 启车清水桶液位
          startFineCoalLevel: number; // 启车末煤仓位
          startGranularCoalLevel: number; // 启车粒煤仓位
          startLargeBlockLevel: number; // 启车大块仓位
          startMediumBlockLevel: number; // 启车中块仓位
          startSmallBlockLevel: number; // 启车小块仓位
          startMiddlingCoalLevel: number; // 启车中煤仓位
          startGangueLevel: number; // 启车矸石仓位
          stopCirculatingWaterPool: number; // 停车循环水池液位
          stopCleanWaterTank: number; // 停车清水桶液位
          stopFineCoalLevel: number; // 停车末煤仓位
          stopGranularCoalLevel: number; // 停车粒煤仓位
          stopLargeBlockLevel: number; // 停车大块仓位
          stopMediumBlockLevel: number; // 停车中块仓位
          stopSmallBlockLevel: number; // 停车小块仓位
          stopMiddlingCoalLevel: number; // 停车中煤仓位
          stopGangueLevel: number; // 停车矸石仓位
          remarks: string; // 备注信息
          
          // 新选煤厂字段
          // 皮带秤及产量统计字段
          beltScale201RawCoalHandoverIn?: number; // 201原煤皮带接班量(吨)
          beltScale201RawCoalHandoverOut?: number; // 201原煤皮带交班量(吨)
          beltScale201RawCoalCurrentShift?: number; // 201原煤皮带当班量(吨)
          beltScale201RawCoalMonthlyTotal?: number; // 201原煤皮带月累计(吨)
          
          beltScale701BlockCleanHandoverIn?: number; // 701块精煤皮带秤接班量(吨)
          beltScale701BlockCleanHandoverOut?: number; // 701块精煤皮带秤交班量(吨)
          beltScale701BlockCleanCurrentShift?: number; // 701块精煤皮带秤当班量(吨)
          beltScale701BlockCleanMonthlyTotal?: number; // 701块精煤皮带秤月累计(吨)
          beltScale701BlockCleanCurrentYield?: number; // 701块精煤当班产率(%)
          beltScale701BlockCleanMonthlyYield?: number; // 701块精煤月度产率(%)
          
          beltScale702FineCleanHandoverIn?: number; // 702末精煤皮带秤接班量(吨)
          beltScale702FineCleanHandoverOut?: number; // 702末精煤皮带秤交班量(吨)
          beltScale702FineCleanCurrentShift?: number; // 702末精煤皮带秤当班量(吨)
          beltScale702FineCleanMonthlyTotal?: number; // 702末精煤皮带秤月累计(吨)
          beltScale702FineCleanCurrentYield?: number; // 702末精煤当班产率(%)
          beltScale702FineCleanMonthlyYield?: number; // 702末精煤月度产率(%)
          
          filterPressSlimeHandoverIn?: number; // 压滤煤泥接班量(吨)
          filterPressSlimeHandoverOut?: number; // 压滤煤泥交班量(吨)
          filterPressSlimeCurrentShift?: number; // 压滤煤泥当班量(吨)
          filterPressSlimeMonthlyTotal?: number; // 压滤煤泥月累计(吨)
          filterPressSlimeCurrentYield?: number; // 压滤煤泥当班产率(%)
          filterPressSlimeMonthlyYield?: number; // 压滤煤泥月度产率(%)
          
          unwashedFineRawCoalHandoverIn?: number; // 未入洗末原煤秤接班量(吨)
          unwashedFineRawCoalHandoverOut?: number; // 未入洗末原煤秤交班量(吨)
          unwashedFineRawCoalCurrentShift?: number; // 未入洗末原煤秤当班量(吨)
          unwashedFineRawCoalMonthlyTotal?: number; // 未入洗末原煤秤月累计(吨)
          unwashedFineRawCoalCurrentYield?: number; // 未入洗末原煤当班产率(%)
          unwashedFineRawCoalMonthlyYield?: number; // 未入洗末原煤月度产率(%)
          
          gangue901CurrentShift?: number; // 901矸石量当班量(吨)
          gangue901MonthlyTotal?: number; // 901矸石量月累计(吨)
          gangue901CurrentYield?: number; // 901矸石当班产率(%)
          gangue901MonthlyYield?: number; // 901矸石月度产率(%)
          
          commercialCoalTotalCurrentShift?: number; // 商品煤总重当班量(吨)
          commercialCoalTotalMonthlyTotal?: number; // 商品煤总重月累计(吨)
          commercialCoalTotalCurrentYield?: number; // 商品煤总重当班产率(%)
          commercialCoalTotalMonthlyYield?: number; // 商品煤总重月度产率(%)
          
          electricityMeterHandoverIn?: number; // 电表数接班读数(kw*h)
          electricityMeterHandoverOut?: number; // 电表数交班读数(kw*h)
          electricityMeterCurrentShift?: number; // 电表数当班量(kw*h)
          electricityMeterMonthlyTotal?: number; // 电表数月累计(kw*h)
          electricityMeterCurrentYield?: number; // 电表数当班产率(%)
          electricityMeterMonthlyYield?: number; // 电表数月度产率(%)
          
          mineCleanWaterHandoverIn?: number; // 矿井清水表数接班读数(m³)
          mineCleanWaterHandoverOut?: number; // 矿井清水表数交班读数(m³)
          mineCleanWaterCurrentShift?: number; // 矿井清水表数当班量(m³)
          mineCleanWaterMonthlyTotal?: number; // 矿井清水表数月累计(m³)
          mineCleanWaterCurrentYield?: number; // 矿井清水当班产率(%)
          mineCleanWaterMonthlyYield?: number; // 矿井清水月度产率(%)
          
          // 煤质情况字段
          rawCoalTotalMoisture?: number; // 原煤全水(%)
          rawCoalAshContent?: number; // 原煤灰分(%)
          rawCoalTotalSulfur?: number; // 原煤全硫(%)
          rawCoalVolatileMatter?: number; // 原煤挥发分(%)
          rawCoalLowerHeatingValue?: number; // 原煤低位发热量(kcal/kg)
          
          siloLargeLumpsTotalMoisture?: number; // 筒仓大块全水(%)
          siloLargeLumpsAshContent?: number; // 筒仓大块灰分(%)
          siloLargeLumpsTotalSulfur?: number; // 筒仓大块全硫(%)
          siloLargeLumpsVolatileMatter?: number; // 筒仓大块挥发分(%)
          siloLargeLumpsLowerHeatingValue?: number; // 筒仓大块低位发热量(kcal/kg)
          
          silo38LumpsTotalMoisture?: number; // 筒仓三八块全水(%)
          silo38LumpsAshContent?: number; // 筒仓三八块灰分(%)
          silo38LumpsTotalSulfur?: number; // 筒仓三八块全硫(%)
          silo38LumpsVolatileMatter?: number; // 筒仓三八块挥发分(%)
          silo38LumpsLowerHeatingValue?: number; // 筒仓三八块低位发热量(kcal/kg)
          
          siloSeedCoalTotalMoisture?: number; // 筒仓籽煤全水(%)
          siloSeedCoalAshContent?: number; // 筒仓籽煤灰分(%)
          siloSeedCoalTotalSulfur?: number; // 筒仓籽煤全硫(%)
          siloSeedCoalVolatileMatter?: number; // 筒仓籽煤挥发分(%)
          siloSeedCoalLowerHeatingValue?: number; // 筒仓籽煤低位发热量(kcal/kg)
          
          siloFineCoalTotalMoisture?: number; // 筒仓沫煤全水(%)
          siloFineCoalAshContent?: number; // 筒仓沫煤灰分(%)
          siloFineCoalTotalSulfur?: number; // 筒仓沫煤全硫(%)
          siloFineCoalVolatileMatter?: number; // 筒仓沫煤挥发分(%)
          siloFineCoalLowerHeatingValue?: number; // 筒仓沫煤低位发热量(kcal/kg)
          
          slimeTotalMoisture?: number; // 煤泥全水(%)
          slimeAshContent?: number; // 煤泥灰分(%)
          slimeTotalSulfur?: number; // 煤泥全硫(%)
          slimeVolatileMatter?: number; // 煤泥挥发分(%)
          slimeLowerHeatingValue?: number; // 煤泥低位发热量(kcal/kg)
          
          gangueTotalMoisture?: number; // 矸石全水(%)
          gangueAshContent?: number; // 矸石灰分(%)
          gangueTotalSulfur?: number; // 矸石全硫(%)
          gangueVolatileMatter?: number; // 矸石挥发分(%)
          gangueLowerHeatingValue?: number; // 矸石低位发热量(kcal/kg)
          
          // 生产设置参数
          shallowTrough305DensityMin?: number; // 305浅槽密度最小值(g/cm³)
          shallowTrough305DensityMax?: number; // 305浅槽密度最大值(g/cm³)
          cyclone321Pressure?: number; // 321旋流器压力(Mpa)
          tss333DensityMin?: number; // 333TSS密度最小值(g/cm³)
          tss333DensityMax?: number; // 333TSS密度最大值(g/cm³)
          
          // 生产耗材统计
          electricityConsumptionCurrentShift?: number; // 电耗损当班量(kw*h)
          wastewaterTreatmentReplenishmentWater?: number; // 污水处理站补水量(m³)
          magnetitePowderCurrentShift?: number; // 磁铁矿粉当班量(吨)
          whiteReagentAnionicCurrentShift?: number; // 白药-阴离子当班量(kg)
          yellowReagentCationicCurrentShift?: number; // 黄药-阳离子当班量(kg)
          filterPress609StartPlates?: number; // 609压滤机开机板数
          filterPress609EndPlates?: number; // 609压滤机停机板数
          filterPress610StartPlates?: number; // 610压滤机开机板数
          filterPress610EndPlates?: number; // 610压滤机停机板数
          
          // 筒仓仓位统计
          rawCoalSiloStartupPosition?: number; // 原煤仓开机仓位(%)
          rawCoalSiloShutdownPosition?: number; // 原煤仓停机仓位(%)
          largeLumpCoalSiloStartupPosition?: number; // 大块煤仓开机仓位(%)
          largeLumpCoalSiloShutdownPosition?: number; // 大块煤仓停机仓位(%)
          lump38CoalSiloStartupPosition?: number; // 三八块煤仓开机仓位(%)
          lump38CoalSiloShutdownPosition?: number; // 三八块煤仓停机仓位(%)
          seedCoalSiloStartupPosition?: number; // 籽煤仓开机仓位(%)
          seedCoalSiloShutdownPosition?: number; // 籽煤仓停机仓位(%)
          fineCoalSiloStartupPosition?: number; // 沫煤仓开机仓位(%)
          fineCoalSiloShutdownPosition?: number; // 沫煤仓停机仓位(%)
          
          // 浓缩机阻力
          thickener501ResistanceStartup1?: number; // 501浓缩机阻力开机1(Mpa)
          thickener501ResistanceStartup2?: number; // 501浓缩机阻力开机2(Mpa)
          thickener501ResistanceStartup3?: number; // 501浓缩机阻力开机3(Mpa)
          thickener501ResistanceStartup4?: number; // 501浓缩机阻力开机4(Mpa)
          thickener501ResistanceShutdown1?: number; // 501浓缩机阻力停机1(Mpa)
          thickener501ResistanceShutdown2?: number; // 501浓缩机阻力停机2(Mpa)
          thickener501ResistanceShutdown3?: number; // 501浓缩机阻力停机3(Mpa)
          thickener501ResistanceShutdown4?: number; // 501浓缩机阻力停机4(Mpa)
          
          thickener502ResistanceStartup1?: number; // 502浓缩机阻力开机1(Mpa)
          thickener502ResistanceStartup2?: number; // 502浓缩机阻力开机2(Mpa)
          thickener502ResistanceStartup3?: number; // 502浓缩机阻力开机3(Mpa)
          thickener502ResistanceStartup4?: number; // 502浓缩机阻力开机4(Mpa)
          thickener502ResistanceShutdown1?: number; // 502浓缩机阻力停机1(Mpa)
          thickener502ResistanceShutdown2?: number; // 502浓缩机阻力停机2(Mpa)
          thickener502ResistanceShutdown3?: number; // 502浓缩机阻力停机3(Mpa)
          thickener502ResistanceShutdown4?: number; // 502浓缩机阻力停机4(Mpa)
          
          // 生产运行情况
          faultTimeHours?: number; // 故障时间(小时)
          runningTimeHours?: number; // 运行时间(小时)
          shutdownReason?: string; // 停车原因
          
  // 人员信息
  centralController?: string; // 集控员
  productionShiftLeader?: string; // 生产班长
  dispatchDirector?: string; // 调度主任
  }

/** 生产日报统计信息 */
export interface ProductionDailyReportStatistics {
  todayProduction: {
    statisticsDate: string
    todayRawCoalInput: number
    todayCleanCoalOutput: number
    todayOperatingHours: number
    todayReportCount: number
    todayMissingReportCount: number
  }
  monthlyProduction: {
    statisticsYear: number
    statisticsMonth: number
    monthlyOutput: number
    monthlyCleanCoalOutput: number
    monthlyProductionDays: number
    monthlyAverageDailyOutput: number
  }
  overview: {
    totalProductionDays: number
    yearlyPlanCompletionRate: number
    monthlyPlanCompletionRate: number
    dailyPlanCompletionRate: number
    activeStaffCount: number
    totalUserCount: number
  }
}

// 现场生产日报 API
export const ProductionDailyReportApi = {
  // 查询现场生产日报分页
  getProductionDailyReportPage: async (params: any) => {
    return await request.get({ url: `/coal/production-daily-report/page`, params })
  },

  // 查询现场生产日报详情
  getProductionDailyReport: async (id: number) => {
    return await request.get({ url: `/coal/production-daily-report/get?id=` + id })
  },

  // 新增现场生产日报
  createProductionDailyReport: async (data: ProductionDailyReport) => {
    return await request.post({ url: `/coal/production-daily-report/create`, data })
  },

  // 修改现场生产日报
  updateProductionDailyReport: async (data: ProductionDailyReport) => {
    return await request.put({ url: `/coal/production-daily-report/update`, data })
  },

  // 删除现场生产日报
  deleteProductionDailyReport: async (id: number) => {
    return await request.delete({ url: `/coal/production-daily-report/delete?id=` + id })
  },

  /** 批量删除现场生产日报 */
  deleteProductionDailyReportList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/production-daily-report/delete-list?ids=${ids.join(',')}` })
  },

  // 导出现场生产日报 Excel
  exportProductionDailyReport: async (params) => {
    return await request.download({ url: `/coal/production-daily-report/export-excel`, params })
  },

  // 获取生产日报统计信息
  getProductionDailyReportStatistics: async (): Promise<ProductionDailyReportStatistics> => {
    return await request.get({ url: `/coal/production-daily-report/statistics` })
  },
}
