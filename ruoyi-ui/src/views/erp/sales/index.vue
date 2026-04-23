<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区（两区物理隔离） -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">
      
      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="salesNo" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.salesNo" placeholder="搜索单号" clearable @keyup.enter.native="handleQuery" style="width: 200px;" />
        </el-form-item>
        <el-form-item prop="styleNo" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.styleNo" placeholder="搜索款号" clearable @keyup.enter.native="handleQuery" style="width: 160px;" />
        </el-form-item>
        <el-form-item prop="customerId" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.customerId" placeholder="选择客户" clearable filterable remote :remote-method="filterCustomer" :loading="customerLoading" style="width: 160px;">
            <el-option v-for="item in customerOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="orderStatus" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.orderStatus" placeholder="订单状态" clearable style="width: 140px;">
            <el-option v-for="dict in dict.type.erp_order_status" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 (统一放置右上角，最高频功能外露) -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:sales:add']">新增订单</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:sales:edit']">编辑</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:sales:remove']">删除</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:sales:export']">导出</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (引入骨架屏缓解加载焦虑) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <!-- 移除边框，双击唤起极速编辑 -->
        <el-table class="biz-table" :data="salesList" @selection-change="handleSelectionChange" @row-dblclick="handleRowDblclick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="销售单号" align="center" prop="salesNo" />
          <el-table-column label="款号" align="center" prop="styleNo" width="140" />
          <el-table-column label="客户名称" align="center" prop="customerName" />
          <el-table-column label="大货款号" align="center" prop="bulkOrderNo" />
          <el-table-column label="款式/品类" align="center" prop="styleCategory" />
          
          <el-table-column label="订单状态" align="center" prop="orderStatus">
            <template slot-scope="scope">
              <!-- 双击状态时内联编辑 (示例实现) -->
              <div v-if="scope.row.isEditing">
                <el-select v-model="scope.row.orderStatus" size="mini" @change="saveInline(scope.row)">
                  <el-option v-for="dict in dict.type.erp_order_status" :key="dict.value" :label="dict.label" :value="dict.value" />
                </el-select>
              </div>
              <span v-else>{{ (dict.type.erp_order_status.find(d => d.value === scope.row.orderStatus) || {}).label || scope.row.orderStatus }}</span>
            </template>
          </el-table-column>

          <el-table-column label="交货日期" align="center" prop="dueDate" width="120">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          
          <el-table-column label="JIS标签" align="center" prop="jisLabelStatus" width="100">
            <template slot-scope="scope">
              <!-- MUJI极简状态反馈，不用刺眼的强对比Tag -->
              <span :style="{color: scope.row.jisLabelStatus === '1' ? 'var(--app-success-color)' : (scope.row.jisLabelStatus === '2' ? 'var(--app-danger-color)' : 'var(--app-text-tip)')}">
                {{ scope.row.jisLabelStatus === '1' ? '● 合规' : scope.row.jisLabelStatus === '2' ? '● 不合规' : '○ 未确认' }}
              </span>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:sales:edit']">详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改侧边抽屉或轻量弹窗 -->
    <!-- 遵循轻量化反馈原则，不再使用巨大模态框霸屏 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="销售类型" prop="salesType" required>
              <el-select v-model="form.salesType" placeholder="请选择" class="w-full" style="width: 100%">
                <el-option v-for="item in salesTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户" prop="customerId" required>
              <el-select v-model="form.customerId" placeholder="搜索客户" clearable filterable remote :remote-method="filterCustomer" :loading="customerLoading" style="width: 100%">
                <el-option v-for="item in customerOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="销售单号" prop="salesNo">
              <el-input v-model="form.salesNo" placeholder="保存时自动生成（SO-yyyyMMdd-序号）" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="款号" prop="styleNo">
              <el-input v-model="form.styleNo" placeholder="保存时自动生成（KN-YY-SS-NNN），可手动填写" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售日期" prop="salesDate" required>
              <el-date-picker clearable v-model="form.salesDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!-- 按钮 Loading 反馈，非遮罩 -->
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSales, getSales, delSales, addSales, updateSales } from "@/api/erp/sales"
import { listCustomer } from "@/api/erp/customer"
import { getToken } from "@/utils/auth"

export default {
  name: "SalesOrder",
  dicts: ['erp_order_status', 'erp_audit_status'],
  data() {
    return {
      loading: true,
      submitLoading: false, // 按钮内联 Loading
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      salesList: [],
      title: "",
      open: false,
      salesTypeOptions: [
        { value: 'sample', label: '打样单' },
        { value: 'bulk', label: '大货单' }
      ],
      customerOptions: [],
      customerLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesType: null,
        customerId: null,
        salesNo: null,
        styleNo: null,
        orderStatus: null
      },
      form: {},
      rules: {
        salesType: [{ required: true, message: "不能为空", trigger: "change" }],
        customerId: [{ required: true, message: "不能为空", trigger: "change" }],
        salesDate: [{ required: true, message: "不能为空", trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    filterCustomer(query) {
      if (!query) { this.customerOptions = []; return; }
      this.customerLoading = true;
      listCustomer({ pageNum: 1, pageSize: 20, customerName: query }).then(res => {
        this.customerOptions = res.rows.map(r => ({ value: r.id, label: r.customerName }));
        this.customerLoading = false;
      }).catch(() => { this.customerLoading = false });
    },
    getList() {
      this.loading = true;
      listSales(this.queryParams).then(res => {
        this.salesList = res.rows.map(item => ({ ...item, isEditing: false }));
        this.total = res.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    reset() {
      this.form = { id: null, salesType: null, salesNo: null, styleNo: null, salesDate: null, customerId: null };
      this.resetForm("form");
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSales(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = "订单详情";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateSales(this.form) : addSales(this.form);
          req.then(() => {
            this.$message.success("操作成功"); // 使用极简 Toast 替代全屏确认
            this.open = false;
            this.getList();
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      // 高危操作保留确认弹窗
      this.$confirm(`确定销毁编号为 ${ids} 的销售订单？`, "高危操作确认", {
        type: 'warning'
      }).then(() => delSales(ids)).then(() => {
        this.getList();
        this.$message.success("数据已销毁");
      }).catch(() => {});
    },
    handleExport() {
      this.download('erp/sales/order/export', { ...this.queryParams }, `sales_${new Date().getTime()}.xlsx`);
    },
    // 双击单元格极速编辑交互
    handleRowDblclick(row, column, event) {
      if (column.property === 'orderStatus') {
        row.isEditing = true;
      }
    },
    // 极速保存内联更改
    saveInline(row) {
      row.isEditing = false;
      updateSales(row).then(() => {
        this.$message.success('状态已更新');
      }).catch(() => {
        row.isEditing = true; // 回退
      });
    }
  }
}
</script>

<style scoped>
/* 局部样式微调，配合全局引擎 */
.biz-table >>> .el-table__row {
  cursor: pointer;
}
.biz-search-form >>> .el-form-item__content {
  line-height: 32px;
}
</style>
