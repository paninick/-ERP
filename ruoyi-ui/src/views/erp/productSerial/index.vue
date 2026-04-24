<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('productSerial.serialNo')" prop="serialNo">
        <el-input
          v-model="queryParams.serialNo"
          :placeholder="$t('validation.enter', [$t('productSerial.serialNo')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('productSerial.orderId')" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          :placeholder="$t('validation.enter', [$t('productSerial.orderId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('productSerial.jobId')" prop="jobId">
        <el-input
          v-model="queryParams.jobId"
          :placeholder="$t('validation.enter', [$t('productSerial.jobId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('productSerial.colorCode')" prop="colorCode">
        <el-input
          v-model="queryParams.colorCode"
          :placeholder="$t('validation.enter', [$t('productSerial.colorCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['erp:productSerial:add']"
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
          v-hasPermi="['erp:productSerial:edit']"
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
          v-hasPermi="['erp:productSerial:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi='["erp:productSerial:export"]'
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-scan"
          size="mini"
          @click="openScan"
        >{{ $t('productSerial.scan') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productSerialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('productSerial.serialNo')" align="center" prop="serialNo" width="180" />
      <el-table-column :label="$t('productSerial.orderId')" align="center" prop="orderId" width="80" />
      <el-table-column :label="$t('productSerial.jobId')" align="center" prop="jobId" width="80" />
      <el-table-column :label="$t('productSerial.colorCode')" align="center" prop="colorCode" width="80" />
      <el-table-column :label="$t('productSerial.sizeCode')" align="center" prop="sizeCode" width="80" />
      <el-table-column :label="$t('productSerial.currentProcess')" align="center" prop="currentProcessName" width="120" />
      <el-table-column :label="$t('productSerial.status')" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getTagType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:productSerial:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:productSerial:remove']"
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

    <!-- 添加或修改单件流水号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('productSerial.serialNo')" prop="serialNo">
          <el-input v-model="form.serialNo" :placeholder="$t('validation.enter', [$t('productSerial.serialNo')])" />
        </el-form-item>
        <el-form-item :label="$t('productSerial.orderId')" prop="orderId">
          <el-input-number v-model="form.orderId" :min="1" :placeholder="$t('productSerial.orderId')" />
        </el-form-item>
        <el-form-item :label="$t('productSerial.jobId')" prop="jobId">
          <el-input-number v-model="form.jobId" :min="1" :placeholder="$t('productSerial.jobId')" />
        </el-form-item>
        <el-form-item :label="$t('productSerial.producePlanId')" prop="producePlanId">
          <el-input-number v-model="form.producePlanId" :min="1" :placeholder="$t('productSerial.producePlanId')" />
        </el-form-item>
        <el-form-item :label="$t('productSerial.colorCode')" prop="colorCode">
          <el-input v-model="form.colorCode" :placeholder="$t('validation.enter', [$t('productSerial.colorCode')])" />
        </el-form-item>
        <el-form-item :label="$t('productSerial.sizeCode')" prop="sizeCode">
          <el-input v-model="form.sizeCode" :placeholder="$t('validation.enter', [$t('productSerial.sizeCode')])" />
        </el-form-item>
        <el-form-item :label="$t('productSerial.currentProcessName')" prop="currentProcessName">
          <el-input v-model="form.currentProcessName" :placeholder="$t('validation.enter', [$t('productSerial.currentProcessName')])" />
        </el-form-item>
        <el-form-item :label="$t('productSerial.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">{{ $t('productSerial.inProduction') }}</el-radio>
            <el-radio label="1">{{ $t('productSerial.completed') }}</el-radio>
            <el-radio label="2">{{ $t('productSerial.inStock') }}</el-radio>
            <el-radio label="3">{{ $t('productSerial.shipped') }}</el-radio>
          </el-radio-group>
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

    <!-- 扫码查询对话框 -->
    <el-dialog :title="$t('productSerial.scan')" :visible.sync="scanOpen" width="40%" append-to-body>
      <el-form label-width="80px">
        <el-form-item :label="$t('productSerial.serialNo')">
          <el-input
            v-model="scanSerialNo"
            :placeholder="$t('productSerial.scanPlaceholder')"
            clearable
            @keyup.enter.native="doScan"
          />
        </el-form-item>
      </el-form>
      <div v-if="scanResult" class="scan-result">
        <el-descriptions :column="1" border>
          <el-descriptions-item :label="$t('productSerial.serialNo')">{{ scanResult.serialNo }}</el-descriptions-item>
          <el-descriptions-item :label="$t('productSerial.orderId')">{{ scanResult.orderId }}</el-descriptions-item>
          <el-descriptions-item :label="$t('productSerial.jobId')">{{ scanResult.jobId }}</el-descriptions-item>
          <el-descriptions-item :label="$t('productSerial.colorCode')">{{ scanResult.colorCode }}</el-descriptions-item>
          <el-descriptions-item :label="$t('productSerial.sizeCode')">{{ scanResult.sizeCode }}</el-descriptions-item>
          <el-descriptions-item :label="$t('productSerial.currentProcess')">{{ scanResult.currentProcessName }}</el-descriptions-item>
          <el-descriptions-item :label="$t('productSerial.status')">
            <el-tag :type="getTagType(scanResult.status)">{{ getStatusText(scanResult.status) }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doScan">{{ $t('btn.query') }}</el-button>
        <el-button @click="scanOpen = false; scanResult = null">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProductSerial, getProductSerial, addProductSerial, updateProductSerial, delProductSerial, scanProductSerial } from "@/api/erp/productSerial";

export default {
  name: "ProductSerial",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      productSerialList: [],
      title: "",
      open: false,
      submitLoading: false,
      scanOpen: false,
      scanSerialNo: "",
      scanResult: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        orderId: null,
        jobId: null,
        producePlanId: null,
        colorCode: null,
        sizeCode: null,
        status: null
      },
      form: {}
    };
  },
  computed: {
    rules() {
      return {
        serialNo: [
          { required: true, message: this.$t('validation.enter', [this.$t('productSerial.serialNo')]), trigger: "blur" }
        ],
        orderId: [
          { required: true, message: this.$t('validation.enter', [this.$t('productSerial.orderId')]), trigger: "blur" }
        ],
        jobId: [
          { required: true, message: this.$t('validation.enter', [this.$t('productSerial.jobId')]), trigger: "blur" }
        ],
        producePlanId: [
          { required: true, message: this.$t('validation.enter', [this.$t('productSerial.producePlanId')]), trigger: "blur" }
        ]
      };
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getTagType(status) {
      const tagMap = {
        '0': 'warning',
        '1': 'primary',
        '2': 'success',
        '3': 'info'
      };
      return tagMap[status] || 'info';
    },
    getStatusText(status) {
      const textMap = {
        '0': 'productSerial.inProduction',
        '1': 'productSerial.completed',
        '2': 'productSerial.inStock',
        '3': 'productSerial.shipped'
      };
      return this.$t(textMap[status] || '-');
    },
    getList() {
      this.loading = true;
      listProductSerial(this.queryParams).then(response => {
        this.productSerialList = response.rows;
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
        serialNo: null,
        orderId: null,
        jobId: null,
        producePlanId: null,
        colorCode: null,
        sizeCode: null,
        currentProcessId: null,
        currentProcessName: null,
        status: '0',
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
      this.title = this.$t('productSerial.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getProductSerial(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('productSerial.editTitle');
      });
    },
    openScan() {
      this.scanSerialNo = "";
      this.scanResult = null;
      this.scanOpen = true;
    },
    doScan() {
      if (!this.scanSerialNo) {
        this.$modal.msgWarning(this.$t('validation.enter', [this.$t('productSerial.serialNo')]));
        return;
      }
      scanProductSerial(this.scanSerialNo).then(response => {
        this.scanResult = response.data || null;
        if (!this.scanResult) {
          this.$modal.msgWarning(this.$t('productSerial.scanNoResult'));
        }
      }).catch(() => {
        this.scanResult = null;
        this.$modal.msgError(this.$t('productSerial.scanFailed'));
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateProductSerial(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addProductSerial(this.form).then(response => {
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
        return delProductSerial(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
      }).catch(() => {});
    },
    handleExport() {
      this.download('erp/productSerial/export', {
        ...this.queryParams
      }, `productSerial_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
