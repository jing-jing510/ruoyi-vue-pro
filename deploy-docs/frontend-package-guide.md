# 前端打包指南

## 📋 准备工作

### 1. 环境要求
- **Node.js**: 16.0 或更高版本
- **npm**: 8.0 或更高版本
- **pnpm**: 8.6 或更高版本（推荐）

### 2. 检查项目结构
确保前端项目结构完整：
```
yudao-ui/yudao-ui-admin-vue3/
├── src/
│   ├── components/
│   ├── views/
│   ├── router/
│   ├── store/
│   └── assets/
├── public/
├── package.json
├── vite.config.ts
└── dist/ (打包后生成)
```

## 🔧 配置修改

### 1. 创建环境配置文件

#### 开发环境配置 (`.env.dev`)
```bash
# 应用标题
VITE_APP_TITLE=选煤厂生产管理系统

# 租户配置
VITE_APP_TENANT_ENABLE=true
VITE_APP_DEFAULT_LOGIN_TENANT=芋道源码

# API配置
VITE_BASE_URL=http://localhost:48080
VITE_API_URL=/admin-api
VITE_BASE_PATH=/

# 开发服务器配置
VITE_PORT=3000
VITE_OPEN=true

# 其他配置
VITE_APP_CAPTCHA_ENABLE=true
VITE_APP_DEFAULT_LOGIN_USERNAME=admin
VITE_APP_DEFAULT_LOGIN_PASSWORD=admin123
VITE_APP_DOCALERT_ENABLE=true
VITE_DROP_DEBUGGER=false
VITE_DROP_CONSOLE=false
VITE_SOURCEMAP=true
VITE_OUT_DIR=dist
```

#### 生产环境配置 (`.env.prod`)
```bash
# 应用标题
VITE_APP_TITLE=选煤厂生产管理系统

# 租户配置
VITE_APP_TENANT_ENABLE=true
VITE_APP_DEFAULT_LOGIN_TENANT=芋道源码

# API配置
VITE_BASE_URL=https://your-domain.com
VITE_API_URL=/admin-api
VITE_BASE_PATH=/

# 生产环境配置
VITE_DROP_DEBUGGER=true
VITE_DROP_CONSOLE=true
VITE_SOURCEMAP=false
VITE_OUT_DIR=dist
```

### 2. 修改vite配置（如需要）
编辑 `vite.config.ts`：
```typescript
export default ({command, mode}: ConfigEnv): UserConfig => {
    // 根据环境加载不同配置
    const env = loadEnv(mode, root)
    
    return {
        base: env.VITE_BASE_PATH,
        build: {
            outDir: env.VITE_OUT_DIR || 'dist',
            sourcemap: env.VITE_SOURCEMAP === 'true',
            minify: 'terser',
            terserOptions: {
                compress: {
                    drop_debugger: env.VITE_DROP_DEBUGGER === 'true',
                    drop_console: env.VITE_DROP_CONSOLE === 'true'
                }
            }
        }
    }
}
```

## 📦 打包步骤

### 1. 安装依赖
```bash
# 进入前端项目目录
cd yudao-ui/yudao-ui-admin-vue3

# 使用pnpm安装依赖（推荐）
pnpm install

# 或者使用npm
npm install
```

### 2. 开发环境打包
```bash
# 开发环境打包
pnpm run build:dev

# 或者
npm run build:dev
```

### 3. 生产环境打包
```bash
# 生产环境打包
pnpm run build:prod

# 或者
npm run build:prod
```

### 4. 本地环境打包
```bash
# 本地环境打包
pnpm run build:local

# 或者
npm run build:local
```

## 🚀 本地测试

### 1. 开发环境测试
```bash
# 启动开发服务器
pnpm run dev

# 或者
npm run dev

# 访问地址
# http://localhost:3000
```

### 2. 生产环境预览
```bash
# 先打包
pnpm run build:prod

# 预览生产环境
pnpm run serve:prod

# 或者
npm run serve:prod

# 访问地址
# http://localhost:4173
```

## 📁 打包输出

### 文件结构
```
dist/
├── index.html          # 主页面
├── assets/             # 静态资源
│   ├── css/           # 样式文件
│   ├── js/            # JavaScript文件
│   └── images/        # 图片资源
├── favicon.ico        # 网站图标
└── logo.gif          # Logo文件
```

### 文件大小优化
- **HTML**: 通常 < 10KB
- **CSS**: 通常 < 500KB
- **JavaScript**: 通常 < 2MB
- **图片**: 根据实际使用情况

## 🔍 常见问题

### 1. 依赖安装失败
```bash
# 清理缓存
pnpm store prune
# 或者
npm cache clean --force

# 删除node_modules重新安装
rm -rf node_modules
pnpm install
```

### 2. 打包失败
```bash
# 检查Node.js版本
node -v

# 检查pnpm版本
pnpm -v

# 清理构建缓存
rm -rf dist
rm -rf node_modules/.vite
```

### 3. 环境变量未生效
- 检查环境变量文件是否存在
- 确认环境变量名称以 `VITE_` 开头
- 重启开发服务器

### 4. 路由404问题
```typescript
// 检查router配置
// src/router/index.ts
export const router = createRouter({
  history: createWebHistory(import.meta.env.VITE_BASE_PATH),
  routes: routes
})
```

## 📋 生产环境优化

### 1. 代码分割
```typescript
// vite.config.ts
build: {
  rollupOptions: {
    output: {
      manualChunks: {
        vendor: ['vue', 'vue-router', 'pinia'],
        element: ['element-plus'],
        utils: ['axios', 'dayjs']
      }
    }
  }
}
```

### 2. 资源压缩
```typescript
// vite.config.ts
plugins: [
  viteCompression({
    verbose: true,
    disable: false,
    threshold: 10240,
    algorithm: 'gzip',
    ext: '.gz'
  })
]
```

### 3. CDN配置
```typescript
// vite.config.ts
build: {
  rollupOptions: {
    external: ['vue', 'element-plus'],
    output: {
      globals: {
        vue: 'Vue',
        'element-plus': 'ElementPlus'
      }
    }
  }
}
```

## 🔧 部署前检查

### 1. 功能测试
- [ ] 登录功能正常
- [ ] 路由跳转正常
- [ ] API接口调用正常
- [ ] 页面响应式正常
- [ ] 浏览器兼容性测试

### 2. 性能测试
- [ ] 首屏加载时间 < 3秒
- [ ] 页面切换流畅
- [ ] 资源加载正常
- [ ] 内存使用合理

### 3. 安全检查
- [ ] 敏感信息已移除
- [ ] 生产环境配置正确
- [ ] HTTPS配置（如需要）
- [ ] 跨域配置正确

## 📊 打包分析

### 1. 分析打包结果
```bash
# 安装分析工具
pnpm add -D rollup-plugin-visualizer

# 在vite.config.ts中添加插件
import { visualizer } from 'rollup-plugin-visualizer'

plugins: [
  visualizer({
    filename: 'dist/stats.html',
    open: true
  })
]
```

### 2. 优化建议
- 移除未使用的依赖
- 使用动态导入分割代码
- 压缩图片资源
- 启用Gzip压缩

## ✅ 打包检查清单

- [ ] 环境配置文件已创建
- [ ] 依赖安装成功
- [ ] 打包命令执行成功
- [ ] 生成dist目录
- [ ] 本地预览正常
- [ ] 功能测试通过
- [ ] 性能测试通过
- [ ] 生产环境配置正确

---

**注意**: 打包前请确保所有环境配置都已正确设置，并在本地环境中充分测试功能。
