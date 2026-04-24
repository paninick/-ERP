<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('materialConsume.producePlanId')" prop="producePlanId">
        <el-input-number
          v-model="queryParams.producePlanId"
          :placeholder="$t('materialConsume.producePlanId')"
          :min="1"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('materialConsume.materialCode')" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          :placeholder="$t('validation.enter', [$t('materialConsume.materialCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('materialConsume.materialName')" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          :placeholder="$t('validation.enter', [$t('materialConsume.materialName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('materialConsume.isOverLimit')" prop="isOverLimit">
        <el-select v-model="queryParams.isOverLimit" :placeholder="$t('validation.select', [''])" clearable>
          <el-option :label="$t('status.no')" value="0" />
          <el-option :label="$t('status.yes')" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('materialConsume.approvalStatus')" prop="approvalStatus">
        <el-select v-model="queryParams.approvalStatus" :placeholder="$t('validation.select', [''])" clearable>
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
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['erp:materialconsume:add']"
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
          v-hasPermi="['erp:materialconsume:edit']"
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
          v-hasPermi="['erp:materialconsume:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:materialconsume:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialconsumeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('materialConsume.producePlanId')" align="center" prop="producePlanId" width="100" />
      <el-table-column :label="$t('materialConsume.processName')" align="center" prop="processName" width="120" />
      <el-table-column :label="$t('materialConsume.materialCode')" align="center" prop="materialCode" width="120" />
      <el-table-column :label="$t('materialConsume.materialName')" align="center" prop="materialName" width="140" />
      <el-table-column :label="$t('materialConsume.bomQty')" align="center" prop="bomQty" width="100" />
      <el-table-column :label="$t('materialConsume.actualQty')" align="center" prop="actualQty" width="100" />
      <el-table-column :label="$t('materialConsume.standardLossRate')" align="center" prop="standardLossRate" width="100" />
      <el-table-column :label="$t('materialConsume.limitLossQty')" align="center" prop="limitLossQty" width="80" />
      <el-table-column :label="$t('materialConsume.actualLossQty')" align="center" prop="actualLossQty" width="80" />
      <el-table-column :label="$t('materialConsume.isOverLimit')" align="center" prop="isOverLimit" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isOverLimit === '1' ? 'danger' : 'info'">
            {{ scope.row.isOverLimit === '1' ? $t('status.yes') : $t('status.no') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('materialConsume.approvalStatus')" align="center" prop="approvalStatus">
        <template slot-scope="scope">
          <el-tag :type="scope.row.approvalStatus === '2' ? 'success' : scope.row.approvalStatus === '1' ? 'warning' : scope.row.approvalStatus === '3' ? 'danger' : 'info'">
            {{ scope.row.approvalStatus === '0' ? $t('materialConsume.noApprovalShort') : scope.row.approvalStatus === '1' ? $t('materialConsume.pendingApprovalShort') : scope.row.approvalStatus === '2' ? $t('materialConsume.approvedShort') : $t('materialConsume.rejectedShort') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:materialconsume:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:materialconsume:remove']"
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

    <!-- 添加或修改物料消耗对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('materialConsume.producePlanId')" prop="producePlanId">
          <el-input-number v-model="form.producePlanId" :min="1" :placeholder="$t('materialConsume.producePlanId')" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.orderId')" prop="orderId">
          <el-input-number v-model="form.orderId" :min="1" :placeholder="$t('materialConsume.orderId')" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.processId')" prop="processId">
          <el-input-number v-model="form.processId" :min="1" :placeholder="$t('materialConsume.processId')" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.processName')" prop="processName">
          <el-input v-model="form.processName" :placeholder="$t('validation.enter', [$t('materialConsume.processName')])" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.materialId')" prop="materialId">
          <el-input-number v-model="form.materialId" :min="1" :placeholder="$t('materialConsume.materialId')" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.materialCode')" prop="materialCode">
          <el-input v-model="form.materialCode" :placeholder="$t('validation.enter', [$t('materialConsume.materialCode')])" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.materialName')" prop="materialName">
          <el-input v-model="form.materialName" :placeholder="$t('validation.enter', [$t('materialConsume.materialName')])" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.bomQtyTheory')" prop="bomQty">
          <el-input-number v-model="form.bomQty" :precision="3" :min="0" :placeholder="$t('materialConsume.bomQtyTheory')" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.actualQtyLabel')" prop="actualQty">
          <el-input-number v-model="form.actualQty" :precision="3" :min="0" :placeholder="$t('materialConsume.actualQtyLabel')" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.standardLossRate')" prop="standardLossRate">
          <el-input-number v-model="form.standardLossRate" :precision="2" :min="0" max="100" :placeholder="$t('materialConsume.standardLossRate')" />
        </el-form-item>
        <el-form-item :label="$t('materialConsume.overLimitReason')" v-if="form.isOverLimit === '1'" prop="overLimitReason">
          <el-input v-model="form.overLimitReason" type="textarea" :placeholder="$t('validation.enter', [$t('materialConsume.overLimitReason')])" />
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
  </div>
</template>

<script>
import { listMaterialconsume, getMaterialconsume, addMaterialconsume, updateMaterialconsume, delMaterialconsume } from "@/api/erp/materialconsume";

export default {
  name: "ProduceMaterialConsume",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      materialconsumeList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        producePlanId: null,
        materialCode: null,
        materialName: null,
        isOverLimit: null,
        approvalStatus: null
      },
      form: {}
    };
  },
  computed: {
    rules() {
      return {
        producePlanId: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        materialId: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        bomQty: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        actualQty: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ]
      };
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询物料消耗列表 */
    getList() {
      this.loading = true;
      listMaterialconsume(this.queryParams).then(response => {
        this.materialconsumeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        producePlanId: null,
        orderId: null,
        processId: null,
        processName: null,
        materialId: null,
        materialCode: null,
        materialName: null,
        bomQty: null,
        actualQty: null,
        standardLossRate: null,
        limitLossQty: null,
        actualLossQty: null,
        isOverLimit: "0",
        overLimitReason: null,
        approvalStatus: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('materialConsume.addTitle');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getMaterialconsume(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('materialConsume.editTitle');
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.id != null) {
            updateMaterialconsume(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addMaterialconsume(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.addSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(function() {
        return delMaterialconsume(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/materialconsume/export', {
        ...this.queryParams
      }, `materialconsume_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
