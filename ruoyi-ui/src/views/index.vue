<template>
  <div class="app-container home">
    <div class="welcome-header">
      <h1>👋 欢迎回来，{{ getUserName() }}</h1>
      <p>今日 ERP 生产运营看板</p>
    </div>

    <el-row :gutter="20" class="dept-panel">
      <el-col :xs="24" :sm="12" :md="8" :lg="8" v-for="card in departmentCards" :key="card.id">
        <el-card class="dept-card" :class="card.class" @click="checkPermissionAndGo(card)">
          <div class="dept-icon">{{ card.icon }}</div>
          <div class="dept-info">
            <h3>{{ card.name }}</h3>
            <p>{{ card.desc }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header">
            <span>📊 快捷数据统计</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="4" v-for="stat in stats" :key="stat.key">
              <div class="stat-item">
                <div class="stat-number">{{ stat.value }}</div>
                <div class="stat-label">{{ stat.label }}</div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
import store from '@/store'

export default {
  name: "Index",
  data() {
    return {
      departmentCards: [
        {
          id: 'production',
          name: '生产部门',
          desc: '订单排产·工序管理·生产进度',
          icon: '🏭',
          route: '/production/order',
          permission: 'production:*',
          class: 'production'
        },
        {
          id: 'sales',
          name: '销售部门',
          desc: '客户管理·订单接单·销售统计',
          icon: '🛍️',
          route: '/sales/order',
          permission: 'sales:*',
          class: 'sales'
        },
        {
          id: 'finance',
          name: '财务部门',
          desc: '成本核算·账款管理·财务报表',
          icon: '💰',
          route: '/finance/account',
          permission: 'finance:*',
          class: 'finance'
        },
        {
          id: 'purchase',
          name: '采购部门',
          desc: '物料采购·供应商管理·采购跟踪',
          icon: '📦',
          route: '/purchase/order',
          permission: 'purchase:*',
          class: 'purchase'
        },
        {
          id: 'warehouse',
          name: '仓库部门',
          desc: '库存管理·出入库·盘点管理',
          icon: '🏬',
          route: '/warehouse/inventory',
          permission: 'warehouse:*',
          class: 'warehouse'
        }
      ],
      stats: [
        { key: 'todayOrders', label: '今日订单', value: '0' },
        { key: 'pendingProd', label: '待生产', value: '0' },
        { key: 'todayStock', label: '今日入库', value: '0' },
        { key: 'todayShip', label: '今日发货', value: '0' },
        { key: 'totalProducts', label: '总产品数', value: '0' }
      ]
    }
  },
  mounted() {
    this.loadStats()
  },
  methods: {
    getUserName() {
      return store.getters.name || '用户'
    },
    hasPermission(permission) {
      const roles = store.getters.roles
      if (!roles || roles.length === 0) return false
      if (roles.includes('admin')) return true
      const permissions = store.getters.permissions
      if (!permissions || permissions.length === 0) return true
      for (const p of permissions) {
        if (permission === '*' || p === permission || permission.endsWith('*') && p.startsWith(permission.slice(0, -1))) {
          return true
        }
      }
      return false
    },
    checkPermissionAndGo(card) {
      if (!this.hasPermission(card.permission)) {
        this.$message.warning('您没有访问 ' + card.name + ' 的权限')
        return
      }
      this.$router.push(card.route)
    },
    loadStats() {
      this.stats = [
        { key: 'todayOrders', label: '今日订单', value: '加载中...' },
        { key: 'pendingProd', label: '待生产', value: '加载中...' },
        { key: 'todayStock', label: '今日入库', value: '加载中...' },
        { key: 'todayShip', label: '今日发货', value: '加载中...' },
        { key: 'totalProducts', label: '总产品数', value: '加载中...' }
      ]
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  background: transparent;
  overflow-x: hidden;
}

.welcome-header {
  text-align: center;
  padding: 20px 0 30px;

  h1 {
    font-size: 32px;
    font-weight: 600;
    margin: 0 0 10px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }

  p {
    color: #888;
    font-size: 16px;
    margin: 0;
  }
}

.dept-panel {
  margin-top: 10px;
}

.dept-card {
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 16px;
  margin-bottom: 20px;
  overflow: hidden;

  &:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 30px rgba(0,0,0,0.15);
  }

  .dept-icon {
    font-size: 48px;
    text-align: center;
    padding: 10px 0;
  }

  .dept-info {
    text-align: center;

    h3 {
      font-size: 20px;
      font-weight: 600;
      margin: 10px 0 8px;
    }

    p {
      color: #888;
      font-size: 13px;
      margin: 0 0 10px;
    }
  }

  &.production {
    background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
    border: none;

    h3 { color: #d35400; }
  }

  &.sales {
    background: linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%);
    border: none;

    h3 { color: #8e44ad; }
  }

  &.finance {
    background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
    border: none;

    h3 { color: #f39c12; }
  }

  &.purchase {
    background: linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%);
    border: none;

    h3 { color: #27ae60; }
  }

  &.warehouse {
    background: linear-gradient(135deg, #a6c0fe 0%, #f68084 100%);
    border: none;

    h3 { color: #2980b9; }
  }
}

.stat-item {
  text-align: center;
  padding: 25px 10px;

  .stat-number {
    font-size: 32px;
    font-weight: bold;
    color: #409eff;
    margin-bottom: 8px;
  }

  .stat-label {
    font-size: 14px;
    color: #888;
  }
}
</style>
