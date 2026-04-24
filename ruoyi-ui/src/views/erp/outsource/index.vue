<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('outsource.outsourceNo')" prop="outsourceNo">
        <el-input
          v-model="queryParams.outsourceNo"
          :placeholder="$t('outsource.enterOutsourceNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('outsource.process')" prop="processId">
        <el-select v-model="queryParams.processId" :placeholder="$t('outsource.process')" clearable filterable>
          <el-option v-for="p in processOptions" :key="p.id" :label="p.processName" :value="p.id" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('outsource.supplier')" prop="supplierId">
        <el-select v-model="queryParams.supplierId" :placeholder="$t('outsource.supplier')" clearable filterable>
          <el-option v-for="s in supplierOptions" :key="s.id" :label="s.supplierName" :value="s.id" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('outsource.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('outsource.selectStatus')" clearable>
          <el-option :label="$t('outsource.pendingSend')" value="0" />
          <el-option :label="$t('outsource.sent')" value="1" />
          <el-option :label="$t('outsource.partialReceive')" value="2" />
          <el-option :label="$t('outsource.fullReceive')" value="3" />
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
          v-hasPermi="['erp:outsource:add']"
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
          v-hasPermi="['erp:outsource:edit']"
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
          v-hasPermi="['erp:outsource:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:outsource:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outsourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('outsource.outsourceNo')" align="center" prop="outsourceNo" width="140" />
      <el-table-column :label="$t('outsource.process')" align="center" prop="processName" width="120" />
      <el-table-column :label="$t('outsource.supplier')" align="center" prop="supplierName" width="140" />
      <el-table-column :label="$t('outsource.isTransfer')" align="center" prop="isTransfer" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isTransfer === 1 ? 'warning' : 'info'">
            {{ scope.row.isTransfer === 1 ? $t('status.yes') : $t('status.no') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('outsource.totalQty')" align="center" prop="totalQty" width="80" />
      <el-table-column :label="$t('outsource.confirmQty')" align="center" prop="confirmQty" width="80" />
      <el-table-column :label="$t('outsource.defectQty')" align="center" prop="defectQty" width="60" />
      <el-table-column :label="$t('outsource.status')" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '3' ? 'success' : scope.row.status === '1' ? 'warning' : scope.row.status === '2' ? 'primary' : 'info'">
            {{ scope.row.status === '0' ? $t('outsource.pendingSend') : scope.row.status === '1' ? $t('outsource.sent') : scope.row.status === '2' ? $t('outsource.partialReceive') : $t('outsource.fullReceive') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('outsource.outboundTime')" align="center" prop="outboundTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outboundTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('outsource.operation')" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:outsource:edit']">{{ $t('btn.edit') }}</el-button>
          <el-button v-if="scope.row.status === '1' || scope.row.status === '2'" size="mini" type="text"
            icon="el-icon-top-right" @click="handleReceive(scope.row)" style="color: var(--app-success-color)"
            v-hasPermi="['erp:outsource:edit']">{{ $t('btn.receive') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['erp:outsource:remove']">{{ $t('btn.delete') }}</el-button>
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

    <!-- 添加或修改外协加工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body
      :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('outsource.outsourceNo')" prop="outsourceNo">
          <el-input v-model="form.outsourceNo" :placeholder="$t('outsource.autoGenerateHint')" disabled />
        </el-form-item>
        <el-form-item :label="$t('outsource.process')" prop="processId">
          <el-select v-model="form.processId" :placeholder="$t('outsource.selectProcess')" filterable clearable style="width: 100%">
            <el-option v-for="p in processOptions" :key="p.id" :label="p.processName" :value="p.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('outsource.processName')" prop="processName">
          <el-input v-model="form.processName" :placeholder="$t('outsource.autoFillHint')" />
        </el-form-item>
        <el-form-item :label="$t('outsource.supplier')" prop="supplierId">
          <el-select v-model="form.supplierId" :placeholder="$t('outsource.selectSupplier')" filterable clearable style="width: 100%">
            <el-option v-for="s in supplierOptions" :key="s.id" :label="s.supplierName" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('outsource.supplierName')" prop="supplierName">
          <el-input v-model="form.supplierName" :placeholder="$t('outsource.autoFillHint')" />
        </el-form-item>
        <el-form-item :label="$t('outsource.isTransfer')" prop="isTransfer">
          <el-radio-group v-model="form.isTransfer">
            <el-radio :label="0">{{ $t('status.no') }}</el-radio>
            <el-radio :label="1">{{ $t('status.yes') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.isTransfer === 1" :label="$t('outsource.transferFrom')" prop="transferFrom">
          <el-input v-model="form.transferFrom" :placeholder="$t('outsource.transferFrom')" />
        </el-form-item>
        <el-form-item v-if="form.isTransfer === 1" :label="$t('outsource.transferTo')" prop="transferTo">
          <el-input v-model="form.transferTo" :placeholder="$t('outsource.transferTo')" />
        </el-form-item>
        <el-form-item :label="$t('outsource.totalQty')" prop="totalQty">
          <el-input-number v-model="form.totalQty" :min="1" :placeholder="$t('outsource.totalQty')" />
        </el-form-item>
        <el-form-item :label="$t('outsource.theoryWeight')" prop="theoryWeight">
          <el-input-number v-model="form.theoryWeight" :precision="2" :min="0" :placeholder="$t('outsource.theoryWeight')" />
        </el-form-item>
        <el-form-item :label="$t('outsource.unitPrice')" prop="unitPrice">
          <el-input-number v-model="form.unitPrice" :precision="2" :min="0" :placeholder="$t('outsource.unitPrice')" />
        </el-form-item>
        <el-form-item :label="$t('outsource.freight')" prop="freight">
          <el-input-number v-model="form.freight" :precision="2" :min="0" :placeholder="$t('outsource.freight')" />
        </el-form-item>
        <el-form-item :label="$t('outsource.status')" prop="status">
          <el-select v-model="form.status" :placeholder="$t('outsource.selectStatus')">
            <el-option :label="$t('outsource.pendingSend')" value="0" />
            <el-option :label="$t('outsource.sent')" value="1" />
            <el-option :label="$t('outsource.partialReceive')" value="2" />
            <el-option :label="$t('outsource.fullReceive')" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('outsource.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('outsource.enterContent')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 收回对话框 -->
    <el-dialog :title="$t('outsource.receiveTitle')" :visible.sync="receiveOpen" width="500px" append-to-body>
      <el-form ref="receiveForm" :model="receiveData" label-width="100px">
        <el-form-item :label="$t('outsource.outsourceNo')">
          <span>{{ receiveData.outsourceNo }}</span>
        </el-form-item>
        <el-form-item :label="$t('outsource.process')">
          <span>{{ receiveData.processName }}</span>
        </el-form-item>
        <el-form-item :label="$t('outsource.totalQty')" prop="totalQty">
          <span>{{ receiveData.totalQty }}</span>
        </el-form-item>
        <el-form-item :label="$t('outsource.receiveQty')" prop="receiveQty">
          <el-input-number v-model="receiveData.receiveQty" :min="1" :max="receiveData.totalQty - (receiveData.confirmQty || 0)" />
        </el-form-item>
        <el-form-item :label="$t('outsource.defectQty')" prop="defectQty">
          <el-input-number v-model="receiveData.defectQty" :min="0" :max="receiveData.receiveQty || 0" />
        </el-form-item>
        <el-form-item :label="$t('outsource.actualWeight')" prop="actualWeight">
          <el-input-number v-model="receiveData.actualWeight" :precision="2" :min="0" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="receiveLoading" @click="confirmReceive">{{ $t('btn.confirmReceive') }}</el-button>
        <el-button @click="receiveOpen = false">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOutsource, getOutsource, addOutsource, updateOutsource, delOutsource } from "@/api/erp/outsource";
import { listProcessDef } from "@/api/erp/processDef";
import { listSupplier } from "@/api/erp/supplier";

export default {
  name: "OutsourceOrder",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      outsourceList: [],
      title: "",
      open: false,
      submitLoading: false,
      processOptions: [],
      supplierOptions: [],
      // 收回
      receiveOpen: false,
      receiveLoading: false,
      receiveData: {
        outsourceNo: '',
        processName: '',
        totalQty: 0,
        confirmQty: 0,
        receiveQty: 0,
        defectQty: 0,
        actualWeight: 0
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        outsourceNo: null,
        processId: null,
        supplierId: null,
        status: null
      },
      form: {},
      rules: {
        outsourceNo: [
          { required: true, message: () => this.$t('validation.required'), trigger: "blur" }
        ],
        processId: [
          { required: true, message: () => this.$t('validation.required'), trigger: "change" }
        ],
        supplierId: [
          { required: true, message: () => this.$t('validation.required'), trigger: "change" }
        ],
        totalQty: [
          { required: true, message: () => this.$t('validation.required'), trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.loadProcessOptions();
    this.loadSupplierOptions();
  },
  methods: {
    getList() {
      this.loading = true;
      listOutsource(this.queryParams).then(response => {
        this.outsourceList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(() => { this.loading = false });
    },
    loadProcessOptions() {
      listProcessDef({ pageSize: 999 }).then(res => {
        this.processOptions = res.rows || [];
      }).catch(() => {});
    },
    loadSupplierOptions() {
      listSupplier({ pageSize: 999 }).then(res => {
        this.supplierOptions = res.rows || [];
      }).catch(() => {});
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: null,
        outsourceNo: null,
        processId: null,
        processName: null,
        supplierId: null,
        supplierName: null,
        isTransfer: 0,
        transferFrom: null,
        transferTo: null,
        totalQty: 0,
        theoryWeight: null,
        confirmQty: 0,
        defectQty: 0,
        actualWeight: null,
        unitPrice: null,
        totalPrice: null,
        freight: null,
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
      this.title = this.$t('outsource.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getOutsource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('outsource.editTitle');
      }).catch(() => {});
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.id != null) {
            updateOutsource(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addOutsource(this.form).then(response => {
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
        return delOutsource(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
      }).catch(() => {});
    },
    handleExport() {
      this.download('erp/outsource/export', {
        ...this.queryParams
      }, `outsource_${new Date().getTime()}.xlsx`);
    },
    handleReceive(row) {
      this.receiveData = {
        id: row.id,
        outsourceNo: row.outsourceNo,
        processName: row.processName,
        totalQty: row.totalQty,
        confirmQty: row.confirmQty || 0,
        existingDefectQty: row.defectQty || 0,
        defectQty: 0,
        receiveQty: 1,
        actualWeight: 0
      };
      this.receiveOpen = true;
    },
    confirmReceive() {
      if (!this.receiveData.receiveQty || this.receiveData.receiveQty < 1) {
        this.$message.warning(this.$t('outsource.enterReceiveQty'));
        return;
      }
      this.receiveLoading = true;
      const newConfirm = (this.receiveData.confirmQty || 0) + this.receiveData.receiveQty;
      const newDefect = (this.receiveData.existingDefectQty || 0) + this.receiveData.defectQty;
      const newStatus = newConfirm >= this.receiveData.totalQty ? '3' : '2';

      updateOutsource({
        id: this.receiveData.id,
        confirmQty: newConfirm,
        defectQty: newDefect,
        actualWeight: this.receiveData.actualWeight,
        status: newStatus
      }).then(() => {
        this.$modal.msgSuccess(this.$t('outsource.receiveSuccess'));
        this.receiveOpen = false;
        this.getList();
      }).catch(() => {
        this.$message.error(this.$t('outsource.receiveFailed'));
      }).finally(() => {
        this.receiveLoading = false;
      });
    }
  }
};
</script>
