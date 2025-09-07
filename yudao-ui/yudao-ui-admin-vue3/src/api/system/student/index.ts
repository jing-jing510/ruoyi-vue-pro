import request from '@/config/axios'
import type { Dayjs } from 'dayjs';

/** 学生课程信息 */
export interface StudentCourse {
          id: number; // 编号
          studentId?: number; // 学生编号
          name?: string; // 名字
          score?: number; // 分数
}

/** 学生班级信息 */
export interface StudentGrade {
          id: number; // 编号
          studentId?: number; // 学生编号
          className?: string; // 班级
          teacher?: string; // 班主任
}

/** 学生主子表测试信息 */
export interface Student {
          id: number; // 编号
          name?: string; // 名字
          birthday?: string | Dayjs; // 出生日期
          description?: string; // 简介
  }

// 学生主子表测试 API
export const StudentApi = {
  // 查询学生主子表测试分页
  getStudentPage: async (params: any) => {
    return await request.get({ url: `/system/student/page`, params })
  },

  // 查询学生主子表测试详情
  getStudent: async (id: number) => {
    return await request.get({ url: `/system/student/get?id=` + id })
  },

  // 新增学生主子表测试
  createStudent: async (data: Student) => {
    return await request.post({ url: `/system/student/create`, data })
  },

  // 修改学生主子表测试
  updateStudent: async (data: Student) => {
    return await request.put({ url: `/system/student/update`, data })
  },

  // 删除学生主子表测试
  deleteStudent: async (id: number) => {
    return await request.delete({ url: `/system/student/delete?id=` + id })
  },

  /** 批量删除学生主子表测试 */
  deleteStudentList: async (ids: number[]) => {
    return await request.delete({ url: `/system/student/delete-list?ids=${ids.join(',')}` })
  },

  // 导出学生主子表测试 Excel
  exportStudent: async (params) => {
    return await request.download({ url: `/system/student/export-excel`, params })
  },

// ==================== 子表（学生课程） ====================

  // 获得学生课程分页
  getStudentCoursePage: async (params) => {
    return await request.get({ url: `/system/student/student-course/page`, params })
  },
  // 新增学生课程
  createStudentCourse: async (data: StudentCourse) => {
    return await request.post({ url: `/system/student/student-course/create`, data })
  },

  // 修改学生课程
  updateStudentCourse: async (data: StudentCourse) => {
    return await request.put({ url: `/system/student/student-course/update`, data })
  },

  // 删除学生课程
  deleteStudentCourse: async (id: number) => {
    return await request.delete({ url: `/system/student/student-course/delete?id=` + id })
  },

  /** 批量删除学生课程 */
  deleteStudentCourseList: async (ids: number[]) => {
    return await request.delete({ url: `/system/student/student-course/delete-list?ids=${ids.join(',')}` })
  },

  // 获得学生课程
  getStudentCourse: async (id: number) => {
    return await request.get({ url: `/system/student/student-course/get?id=` + id })
  },

// ==================== 子表（学生班级） ====================

  // 获得学生班级分页
  getStudentGradePage: async (params) => {
    return await request.get({ url: `/system/student/student-grade/page`, params })
  },
  // 新增学生班级
  createStudentGrade: async (data: StudentGrade) => {
    return await request.post({ url: `/system/student/student-grade/create`, data })
  },

  // 修改学生班级
  updateStudentGrade: async (data: StudentGrade) => {
    return await request.put({ url: `/system/student/student-grade/update`, data })
  },

  // 删除学生班级
  deleteStudentGrade: async (id: number) => {
    return await request.delete({ url: `/system/student/student-grade/delete?id=` + id })
  },

  /** 批量删除学生班级 */
  deleteStudentGradeList: async (ids: number[]) => {
    return await request.delete({ url: `/system/student/student-grade/delete-list?ids=${ids.join(',')}` })
  },

  // 获得学生班级
  getStudentGrade: async (id: number) => {
    return await request.get({ url: `/system/student/student-grade/get?id=` + id })
  },
}
