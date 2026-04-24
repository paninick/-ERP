<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="16" class="mb16">
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num">{{ stats.totalRecords || 0 }}</div>
          <div class="stat-label">{{ $t('losscontrol.totalRecords') }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num text-danger">{{ stats.overLimitCount || 0 }}</div>
          <div class="stat-label">{{ $t('losscontrol.overLimitCount') }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num text-warning">{{ stats.pendingApprovalCount || 0 }}</div>
          <div class="stat-label">{{ $t('losscontrol.pendingApprovalCount') }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-num text-success">{{ stats.overLimitRate || '0.00' }}%</div>
          <div class="stat-label">{{ $t('losscontrol.overLimitRate') }}</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 查询栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item :label="$t('losscontrol.materialCode')">
        <el-input v-model="queryParams.materialCode" :placeholder="$t('losscontrol.materialCode')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('losscontrol.materialName')">
        <el-input v-model="queryParams.materialName" :placeholder="$t('losscontrol.materialName')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('losscontrol.isOverLimit')">
        <el-select v-model="queryParams.isOverLimit" :placeholder="$t('status.yes') + '/' + $t('status.no')" clearable style="width:90px">
          <el-option :label="$t('status.no')" value="0" />
          <el-option :label="$t('status.yes')" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('losscontrol.approvalStatus')">
        <el-select v-model="queryParams.approvalStatus" :placeholder="$t('losscontrol.approvalStatus')" clearable style="width:100px">
          <el-option :label="$t('materialConsume.noApproval')" value="0" />
          <el-option :label="$t('materialConsume.pendingApproval')" value="1" />
          <el-option :label="$t('materialConsume.approved')" value="2" />
          <el-option :label="$t('materialConsume.rejected')" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
      </el-form-item>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['erp:materialconsume:export']">{{ $t('btn.export') }}</el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column :label="$t('losscontrol.producePlanId')" prop="producePlanId" width="100" />
      <el-table-column :label="$t('losscontrol.processName')" prop="processName" min-width="100" />
      <el-table-column :label="$t('losscontrol.materialCode')" prop="materialCode" width="110" />
      <el-table-column :label="$t('losscontrol.materialName')" prop="materialName" min-width="120" />
      <el-table-column :label="$t('losscontrol.bomQty')" prop="bomQty" width="110" align="right" />
      <el-table-column :label="$t('losscontrol.actualQty')" prop="actualQty" width="90" align="right" />
      <el-table-column :label="$t('losscontrol.standardLossRate')" prop="standardLossRate" width="110" align="right">
        <template slot-scope="scope">{{ scope.row.standardLossRate }}%</template>
      </el-table-column>
      <el-table-column :label="$t('losscontrol.actualLossQty')" prop="actualLossQty" width="90" align="right">
        <template slot-scope="scope">
          <span :class="scope.row.isOverLimit === '1' ? 'text-danger' : ''">{{ scope.row.actualLossQty }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('losscontrol.limitLossQty')" prop="limitLossQty" width="90" align="right" />
      <el-table-column :label="$t('losscontrol.isOverLimit')" prop="isOverLimit" width="70" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isOverLimit === '1' ? 'danger' : 'success'" size="mini">
            {{ scope.row.isOverLimit === '1' ? $t('status.yes') : $t('status.no') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('losscontrol.approvalStatus')" prop="approvalStatus" width="90" align="center">
        <template slot-scope="scope">
          <el-tag :type="approvalTagType(scope.row.approvalStatus)" size="mini">
            {{ approvalLabel(scope.row.approvalStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('losscontrol.overLimitReason')" prop="overLimitReason" min-width="120" show-overflow-tooltip />
      <el-table-column :label="$t('system.operation')" width="120" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.approvalStatus === '1'"
            type="text" size="mini" icon="el-icon-check"
            @click="handleApprove(scope.row, true)"
            v-hasPermi="['erp:materialconsume:edit']"
          >{{ $t('losscontrol.approveBtn') }}</el-button>
          <el-button
            v-if="scope.row.approvalStatus === '1'"
            type="text" size="mini" icon="el-icon-close" style="color:#F56C6C"
            @click="handleApprove(scope.row, false)"
            v-hasPermi="['erp:materialconsume:edit']"
          >{{ $t('losscontrol.rejectBtn') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 审批备注弹窗 -->
    <el-dialog :title="approveForm.approved ? $t('losscontrol.approveTitle') : $t('losscontrol.rejectTitle')" :visible.sync="approveVisible" width="400px">
      <el-form :model="approveForm" label-width="70px" size="small">
        <el-form-item :label="$t('losscontrol.remark')">
          <el-input v-model="approveForm.remark" type="textarea" :rows="3" :placeholder="$t('losscontrol.enterRemark')" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button size="small" @click="approveVisible = false">{{ $t('btn.cancel') }}</el-button>
        <el-button :type="approveForm.approved ? 'primary' : 'danger'" size="small" :loading="submitLoading" @click="submitApprove">{{ $t('btn.confirm') }}</el-button>
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
      submitLoading: false,
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
      this.submitLoading = true;
      approveLoss(id, approved, remark).then(() => {
        this.$modal.msgSuccess(approved ? this.$t('losscontrol.approved') : this.$t('losscontrol.rejected'));
        this.approveVisible = false;
        this.getList();
        this.getStats();
      }).finally(() => { this.submitLoading = false; });
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
      const map = {
        "0": this.$t('materialConsume.noApproval'),
        "1": this.$t('materialConsume.pendingApproval'),
        "2": this.$t('materialConsume.approved'),
        "3": this.$t('materialConsume.rejected')
      };
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
