<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('piecewage.employeeName')" prop="employeeName">
        <el-input
          v-model="queryParams.employeeName"
          :placeholder="$t('validation.enter', [$t('piecewage.employeeName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('piecewage.wageMonth')" prop="wageMonth">
        <el-date-picker
          v-model="queryParams.wageMonth"
          type="month"
          :placeholder="$t('validation.select', [$t('piecewage.wageMonth')])"
          value-format="yyyy-MM"
          clearable
          style="width: 180px">
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('piecewage.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('validation.select', [$t('piecewage.status')])" clearable>
          <el-option :label="$t('piecewage.pendingAudit')" value="0" />
          <el-option :label="$t('piecewage.audited')" value="1" />
          <el-option :label="$t('piecewage.paid')" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['erp:piecewage:add']"
        >{{ $t('btn.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:piecewage:edit']"
        >{{ $t('btn.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:piecewage:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-document"
          size="mini"
          :disabled="single"
          @click="handleViewDetail"
        >{{ $t('piecewage.detail') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:piecewage:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="piecewageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('piecewage.employeeName')" align="center" prop="employeeName" width="120" />
      <el-table-column :label="$t('piecewage.wageMonth')" align="center" prop="wageMonth" width="100" />
      <el-table-column :label="$t('piecewage.totalProcessCount')" align="center" prop="totalProcessCount" width="80" />
      <el-table-column :label="$t('piecewage.totalOkQty')" align="center" prop="totalOkQty" width="80" />
      <el-table-column :label="$t('piecewage.totalDefectQty')" align="center" prop="totalDefectQty" width="80" />
      <el-table-column :label="$t('piecewage.shouldWage')" align="center" prop="shouldWage" width="100" />
      <el-table-column :label="$t('piecewage.deductWage')" align="center" prop="deductWage" width="80" />
      <el-table-column :label="$t('piecewage.actualWage')" align="center" prop="actualWage" width="100" />
      <el-table-column :label="$t('piecewage.status')" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '2' ? 'success' : scope.row.status === '1' ? 'primary' : 'info'">
            {{ scope.row.status === '0' ? $t('piecewage.pendingAudit') : scope.row.status === '1' ? $t('piecewage.audited') : $t('piecewage.paid') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('piecewage.auditTime')" align="center" prop="auditTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:piecewage:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleViewDetail(scope.row)"
          >{{ $t('btn.detail') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:piecewage:remove']"
          >{{ $t('btn.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改计件工资汇总对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('piecewage.employeeId')" prop="employeeId">
          <el-input-number v-model="form.employeeId" :min="1" :placeholder="$t('piecewage.employeeId')" />
        </el-form-item>
        <el-form-item :label="$t('piecewage.employeeName')" prop="employeeName">
          <el-input v-model="form.employeeName" :placeholder="$t('validation.enter', [$t('piecewage.employeeName')])" />
        </el-form-item>
        <el-form-item :label="$t('piecewage.wageMonth')" prop="wageMonth">
          <el-date-picker
            v-model="form.wageMonth"
            type="month"
            :placeholder="$t('validation.select', [$t('piecewage.wageMonth')])"
            value-format="yyyy-MM"
            style="width: 100%">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('piecewage.totalOkQty')" prop="totalOkQty">
          <el-input-number v-model="form.totalOkQty" :min="0" :placeholder="$t('piecewage.totalOkQty')" />
        </el-form-item>
        <el-form-item :label="$t('piecewage.totalDefectQty')" prop="totalDefectQty">
          <el-input-number v-model="form.totalDefectQty" :min="0" :placeholder="$t('piecewage.totalDefectQty')" />
        </el-form-item>
        <el-form-item :label="$t('piecewage.shouldWage')" prop="shouldWage">
          <el-input-number v-model="form.shouldWage" :precision="2" :min="0" :placeholder="$t('piecewage.shouldWage')" />
        </el-form-item>
        <el-form-item :label="$t('piecewage.deductWage')" prop="deductWage">
          <el-input-number v-model="form.deductWage" :precision="2" :min="0" :placeholder="$t('piecewage.deductWage')" />
        </el-form-item>
        <el-form-item :label="$t('piecewage.actualWage')" prop="actualWage">
          <el-input-number v-model="form.actualWage" :precision="2" :min="0" :placeholder="$t('piecewage.actualWage')" />
        </el-form-item>
        <el-form-item :label="$t('piecewage.status')" prop="status">
          <el-select v-model="form.status" :placeholder="$t('validation.select', [$t('piecewage.status')])">
            <el-option :label="$t('piecewage.pendingAudit')" value="0" />
            <el-option :label="$t('piecewage.audited')" value="1" />
            <el-option :label="$t('piecewage.paid')" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('validation.enter', [$t('system.remark')])" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 明细对话框 -->
    <el-dialog :title="$t('piecewage.detail')" :visible.sync="detailOpen" width="80%" append-to-body>
      <el-table v-loading="detailLoading" :data="detailList">
        <el-table-column :label="$t('piecewage.processName')" align="center" prop="processName" width="120" />
        <el-table-column :label="$t('piecewage.jobId')" align="center" prop="jobId" width="80" />
        <el-table-column :label="$t('piecewage.okQty')" align="center" prop="okQty" width="80" />
        <el-table-column :label="$t('piecewage.defectQty')" align="center" prop="defectQty" width="80" />
        <el-table-column :label="$t('piecewage.processPrice')" align="center" prop="processPrice" width="80" />
        <el-table-column :label="$t('piecewage.earnedWage')" align="center" prop="shouldWage" width="80" />
        <el-table-column :label="$t('piecewage.deductWage')" align="center" prop="deductWage" width="80" />
        <el-table-column :label="$t('piecewage.actualWage')" align="center" prop="actualWage" width="90" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPiecewage, getPiecewage, addPiecewage, updatePiecewage, delPiecewage } from "@/api/erp/piecewage";
import { listPiecewagedetailByWage } from "@/api/erp/piecewagedetail";

export default {
  name: "PieceWage",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      piecewageList: [],
      title: "",
      open: false,
      submitLoading: false,
      detailOpen: false,
      detailLoading: false,
      detailList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        employeeName: null,
        wageMonth: null,
        status: null
      },
      form: {}
    };
  },
  computed: {
    rules() {
      return {
        employeeId: [
          { required: true, message: this.$t('validation.enter', [this.$t('piecewage.employeeId')]), trigger: 'blur' }
        ],
        employeeName: [
          { required: true, message: this.$t('validation.enter', [this.$t('piecewage.employeeName')]), trigger: 'blur' }
        ],
        wageMonth: [
          { required: true, message: this.$t('validation.select', [this.$t('piecewage.wageMonth')]), trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询计件工资汇总列表 */
    getList() {
      this.loading = true;
      listPiecewage(this.queryParams).then(response => {
        this.piecewageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: null,
        employeeId: null,
        employeeName: null,
        wageMonth: null,
        totalProcessCount: 0,
        totalOkQty: 0,
        totalDefectQty: 0,
        shouldWage: null,
        deductWage: null,
        actualWage: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
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
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('piecewage.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getPiecewage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('piecewage.editTitle');
      });
    },
    handleViewDetail(row) {
      const id = row.id || (this.ids.length > 0 ? this.ids[0] : null);
      if (!id) {
        this.$modal.msgWarning(this.$t('piecewage.selectRecord'));
        return;
      }
      this.detailLoading = true;
      this.detailOpen = true;
      listPiecewagedetailByWage(id).then(response => {
        this.detailList = response.data || [];
      }).catch(() => {
        this.detailList = [];
        this.$modal.msgError(this.$t('piecewage.detailLoadFailed'));
      }).finally(() => {
        this.detailLoading = false;
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updatePiecewage(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addPiecewage(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.addSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(function() {
        return delPiecewage(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
      }).catch(() => {});
    },
    handleExport() {
      this.download('erp/piecewage/export', {
        ...this.queryParams
      }, `piecewage_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
