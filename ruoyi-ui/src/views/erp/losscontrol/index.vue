<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="16" class="mb16">
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num">{{ stats.totalRecords || 0 }}</div>
          <div class="stat-label">消耗记录总数</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num text-danger">{{ stats.overLimitCount || 0 }}</div>
          <div class="stat-label">超限额记录</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num text-warning">{{ stats.pendingApprovalCount || 0 }}</div>
          <div class="stat-label">待审批</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num text-success">{{ stats.overLimitRate || '0.00' }}%</div>
          <div class="stat-label">超限额率</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 查询栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="物料编码">
        <el-input v-model="queryParams.materialCode" placeholder="物料编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="物料名称">
        <el-input v-model="queryParams.materialName" placeholder="物料名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="超限额">
        <el-select v-model="queryParams.isOverLimit" placeholder="全部" clearable style="width:90px">
          <el-option label="否" value="0" />
          <el-option label="是" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态">
        <el-select v-model="queryParams.approvalStatus" placeholder="全部" clearable style="width:100px">
          <el-option label="无需审批" value="0" />
          <el-option label="待审批" value="1" />
          <el-option label="已批准" value="2" />
          <el-option label="已拒绝" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['erp:materialconsume:export']">导出</el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="生产计划ID" prop="producePlanId" width="100" />
      <el-table-column label="工序" prop="processName" min-width="100" />
      <el-table-column label="物料编码" prop="materialCode" width="110" />
      <el-table-column label="物料名称" prop="materialName" min-width="120" />
      <el-table-column label="BOM理论用量" prop="bomQty" width="110" align="right" />
      <el-table-column label="实际领用" prop="actualQty" width="90" align="right" />
      <el-table-column label="标准损耗率%" prop="standardLossRate" width="110" align="right">
        <template slot-scope="scope">{{ scope.row.standardLossRate }}%</template>
      </el-table-column>
      <el-table-column label="实际损耗" prop="actualLossQty" width="90" align="right">
        <template slot-scope="scope">
          <span :class="scope.row.isOverLimit === '1' ? 'text-danger' : ''">{{ scope.row.actualLossQty }}</span>
        </template>
      </el-table-column>
      <el-table-column label="损耗限额" prop="limitLossQty" width="90" align="right" />
      <el-table-column label="超限额" prop="isOverLimit" width="70" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isOverLimit === '1' ? 'danger' : 'success'" size="mini">
            {{ scope.row.isOverLimit === '1' ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" prop="approvalStatus" width="90" align="center">
        <template slot-scope="scope">
          <el-tag :type="approvalTagType(scope.row.approvalStatus)" size="mini">
            {{ approvalLabel(scope.row.approvalStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="超限原因" prop="overLimitReason" min-width="120" show-overflow-tooltip />
      <el-table-column label="操作" width="120" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.approvalStatus === '1'"
            type="text" size="mini" icon="el-icon-check"
            @click="handleApprove(scope.row, true)"
            v-hasPermi="['erp:materialconsume:edit']"
          >批准</el-button>
          <el-button
            v-if="scope.row.approvalStatus === '1'"
            type="text" size="mini" icon="el-icon-close" style="color:#F56C6C"
            @click="handleApprove(scope.row, false)"
            v-hasPermi="['erp:materialconsume:edit']"
          >拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 审批备注弹窗 -->
    <el-dialog :title="approveForm.approved ? '批准超领' : '拒绝超领'" :visible.sync="approveVisible" width="400px">
      <el-form :model="approveForm" label-width="70px" size="small">
        <el-form-item label="备注">
          <el-input v-model="approveForm.remark" type="textarea" :rows="3" placeholder="请输入审批备注" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button size="small" @click="approveVisible = false">取消</el-button>
        <el-button :type="approveForm.approved ? 'primary' : 'danger'" size="small" @click="submitApprove">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLossControl, getLossStats, approveLoss, exportLossControl } from "@/api/erp/losscontrol";

export default {
  name: "LossControl",
  data() {
    return {
      loading: false,
      showSearch: true,
      list: [],
      total: 0,
      stats: {},
      queryParams: { pageNum: 1, pageSize: 20, materialCode: "", materialName: "", isOverLimit: "", approvalStatus: "" },
      approveVisible: false,
      approveForm: { id: null, approved: true, remark: "" }
    };
  },
  created() {
    this.getList();
    this.getStats();
  },
  methods: {
    getList() {
      this.loading = true;
      listLossControl(this.queryParams).then(res => {
        this.list = res.rows || [];
        this.total = res.total || 0;
      }).finally(() => { this.loading = false; });
    },
    getStats() {
      getLossStats({}).then(res => { this.stats = res.data || {}; }).catch(() => {});
    },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 20, materialCode: "", materialName: "", isOverLimit: "", approvalStatus: "" };
      this.getList();
    },
    handleApprove(row, approved) {
      this.approveForm = { id: row.id, approved, remark: "" };
      this.approveVisible = true;
    },
    submitApprove() {
      const { id, approved, remark } = this.approveForm;
      approveLoss(id, approved, remark).then(() => {
        this.$modal.msgSuccess(approved ? "已批准" : "已拒绝");
        this.approveVisible = false;
        this.getList();
        this.getStats();
      });
    },
    handleExport() {
      exportLossControl(this.queryParams).then(res => {
        this.download(res);
      });
    },
    approvalTagType(s) {
      const map = { "0": "info", "1": "warning", "2": "success", "3": "danger" };
      return map[s] || "info";
    },
    approvalLabel(s) {
      const map = { "0": "无需审批", "1": "待审批", "2": "已批准", "3": "已拒绝" };
      return map[s] || s;
    }
  }
};
</script>

<style scoped>
.mb16 { margin-bottom: 16px; }
.stat-card { text-align: center; }
.stat-num { font-size: 28px; font-weight: bold; line-height: 40px; }
.stat-label { color: #909399; font-size: 13px; }
.text-danger { color: #F56C6C; }
.text-warning { color: #E6A23C; }
.text-success { color: #67C23A; }
</style>
