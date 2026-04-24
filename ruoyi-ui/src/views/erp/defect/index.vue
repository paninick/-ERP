<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区 -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">

      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="jobId" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.jobId" :placeholder="$t('defect.jobId')" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="processName" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.processName" :placeholder="$t('defect.processName')" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="employeeName" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.employeeName" :placeholder="$t('defect.employeeName')" clearable @keyup.enter.native="handleQuery" style="width: 120px;" />
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">{{ $t('btn.query') }}</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:defect:add']">{{ $t('defect.addTitle') }}</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:defect:edit']">{{ $t('btn.edit') }}</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:defect:remove']">{{ $t('btn.delete') }}</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:defect:export']">{{ $t('btn.export') }}</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (骨架屏包装) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <el-table class="biz-table" :data="defectList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column :label="$t('defect.jobId')" align="center" prop="jobId" width="80" />
          <el-table-column :label="$t('defect.processName')" align="center" prop="processName" width="120" />
          <el-table-column :label="$t('defect.employeeName')" align="center" prop="employeeName" width="100" />

          <el-table-column :label="$t('defect.defectCategory')" align="center" prop="defectCategory" width="100">
            <template slot-scope="scope">
              <span :style="{color: ['WEAVE', 'DYE', 'SPLICE'].includes(scope.row.defectCategory) ? 'var(--app-danger-color)' : 'var(--app-warning-color)'}">
                {{ $t('defect.category' + scope.row.defectCategory.charAt(0) + scope.row.defectCategory.slice(1).toLowerCase()) || scope.row.defectCategory }}
              </span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('defect.defectLevel')" align="center" prop="defectLevel" width="80">
            <template slot-scope="scope">
              <span :style="{color: scope.row.defectLevel === 'CRITICAL' ? 'var(--app-danger-color)' : (scope.row.defectLevel === 'MAJOR' ? 'var(--app-warning-color)' : 'var(--app-text-secondary)')}">
                {{ scope.row.defectLevel === 'CRITICAL' ? '● ' + $t('defect.levelCritical') : (scope.row.defectLevel === 'MAJOR' ? '● ' + $t('defect.levelMajor') : '○ ' + $t('defect.levelMinor')) }}
              </span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('defect.defectQty')" align="center" prop="defectQty" width="80" />
          <el-table-column :label="$t('defect.isBrokenNeedle')" align="center" prop="isBrokenNeedle" width="80">
            <template slot-scope="scope">
              <span v-if="scope.row.isBrokenNeedle === '1'" style="color: var(--app-danger-color); font-weight: bold;">⚠ {{ $t('status.yes') }}</span>
              <span v-else style="color: var(--app-text-tip)">{{ $t('status.no') }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('defect.handleStatus')" align="center" width="140">
            <template slot-scope="scope">
              <span v-if="scope.row.isScrap === '1'" style="color: var(--app-danger-color)">{{ $t('defect.statusScrap') }}</span>
              <span v-else-if="scope.row.isRepair === '1'" style="color: var(--app-success-color)">{{ $t('defect.statusRepair') }}</span>
              <span v-else style="color: var(--app-text-tip)">{{ $t('defect.statusPending') }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('defect.findTime')" align="center" prop="findTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.findTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:defect:edit']">{{ $t('btn.detail') }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改次品记录弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('defect.jobId')" prop="jobId">
              <el-input-number v-model="form.jobId" :placeholder="$t('defect.enterJobId')" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('defect.processId')" prop="processId">
              <el-input-number v-model="form.processId" :placeholder="$t('defect.enterProcessId')" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('defect.processName')" prop="processName">
              <el-input v-model="form.processName" :placeholder="$t('defect.enterProcessName')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('defect.employeeNameLabel')" prop="employeeName">
              <el-input v-model="form.employeeName" :placeholder="$t('defect.enterEmployeeName')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('defect.defectQty')" prop="defectQty">
              <el-input-number v-model="form.defectQty" :placeholder="$t('defect.enterDefectQty')" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('defect.defectCategory')" prop="defectCategory">
              <el-select v-model="form.defectCategory" :placeholder="$t('validation.select', [$t('defect.defectCategory')])" style="width: 100%">
                <el-option :label="$t('defect.categoryWeave')" value="WEAVE" />
                <el-option :label="$t('defect.categoryDye')" value="DYE" />
                <el-option :label="$t('defect.categorySplice')" value="SPLICE" />
                <el-option :label="$t('defect.categorySew')" value="SEW" />
                <el-option :label="$t('defect.categoryNeedle')" value="NEEDLE" />
                <el-option :label="$t('defect.categoryPack')" value="PACK" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">{{ $t('defect.defectRatingTitle') }}</el-divider>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('defect.defectLevel')" prop="defectLevel">
              <el-select v-model="form.defectLevel" :placeholder="$t('validation.select', [$t('defect.defectLevel')])" style="width: 100%">
                <el-option :label="$t('defect.levelCritical')" value="CRITICAL" />
                <el-option :label="$t('defect.levelMajor')" value="MAJOR" />
                <el-option :label="$t('defect.levelMinor')" value="MINOR" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('defect.responsibility')" prop="responsibility">
              <el-select v-model="form.responsibility" :placeholder="$t('validation.select', [$t('defect.responsibility')])" style="width: 100%">
                <el-option :label="$t('defect.responsibilitySelf')" value="SELF" />
                <el-option :label="$t('defect.responsibilityOutsource')" value="OUTSOURCE" />
                <el-option :label="$t('defect.responsibilityMaterial')" value="MATERIAL" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('defect.isBrokenNeedle')" prop="isBrokenNeedle">
              <el-radio-group v-model="form.isBrokenNeedle">
                <el-radio label="0">{{ $t('status.no') }}</el-radio>
                <el-radio label="1"><span style="color:var(--app-danger-color);font-weight:bold;">⚠ {{ $t('defect.brokenNeedleYes') }}</span></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item :label="$t('defect.handleType')" prop="handleType">
              <el-select v-model="form.handleType" :placeholder="$t('validation.select', [$t('defect.handleType')])" style="width: 100%">
                <el-option :label="$t('defect.handleRepair')" value="REPAIR" />
                <el-option :label="$t('defect.handleDowngrade')" value="DOWNGRADE" />
                <el-option :label="$t('defect.handleScrap')" value="SCRAP" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24" v-if="form.handleType === 'SCRAP' || form.handleType === 'REPAIR'">
           <el-col :span="12" v-if="form.handleType === 'SCRAP'">
            <el-form-item :label="$t('defect.isScrap')" prop="isScrap">
              <el-switch v-model="form.isScrap" active-value="1" inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.handleType === 'REPAIR'">
            <el-form-item :label="$t('defect.isRepair')" prop="isRepair">
              <el-switch v-model="form.isRepair" active-value="1" inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item :label="$t('defect.defectReasonDesc')" prop="defectReasonDesc">
          <el-input v-model="form.defectReasonDesc" type="textarea" :placeholder="$t('defect.enterDefectReason')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDefect, getDefect, addDefect, updateDefect, delDefect } from "@/api/erp/defect";

export default {
  name: "ProduceDefect",
  data() {
    return {
      loading: true,
      submitLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      defectList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobId: null,
        processName: null,
        employeeName: null
      },
      form: {}
    };
  },
  computed: {
    rules() {
      const validatePositiveInteger = (key) => (rule, value, callback) => {
        if (value === null || value === undefined || value === "" || !Number.isInteger(Number(value)) || Number(value) <= 0) {
          callback(new Error(this.$t(key)));
          return;
        }
        callback();
      };
      return {
        jobId: [{ validator: validatePositiveInteger('defect.jobPositive'), trigger: "change" }],
        processId: [{ validator: validatePositiveInteger('defect.processPositive'), trigger: "change" }],
        defectQty: [{ validator: validatePositiveInteger('defect.qtyPositive'), trigger: "change" }]
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listDefect(this.queryParams).then(res => {
        this.defectList = res.rows;
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
      this.form = { id: null, jobId: null, processId: null, processName: null, employeeId: null, employeeName: null, defectQty: null, defectReasonCode: null, defectReasonDesc: null, isScrap: "0", isRepair: "0", findTime: null, outsourceId: null, isOutsource: "0", defectCategory: null, defectLevel: null, handleType: null, handleResult: null, responsibility: null, isBrokenNeedle: "0", needleConfirmBy: null, needleConfirmTime: null, remark: null };
      this.resetForm("form");
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('defect.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getDefect(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = this.$t('defect.editTitle');
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateDefect(this.form) : addDefect(this.form);
          req.then(() => {
            this.$message.success(this.$t('msg.operationSuccess'));
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
      this.$confirm(this.$t('defect.destroyConfirm', [ids]), this.$t('msg.deleteWarning'), { type: 'warning' })
        .then(() => delDefect(ids))
        .then(() => {
          this.getList();
          this.$message.success(this.$t('msg.dataDestroyed'));
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/defect/export', { ...this.queryParams }, `defect_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>

<style scoped>
.biz-table >>> .el-table__row {
  cursor: pointer;
}
.biz-search-form >>> .el-form-item__content {
  line-height: 32px;
}
</style>
