<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('bizAbnormal.bizType')" prop="bizType">
        <el-input
          v-model="queryParams.bizType"
          :placeholder="$t('validation.enter', [$t('bizAbnormal.bizType')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('bizAbnormal.abnormalCode')" prop="abnormalCode">
        <el-input
          v-model="queryParams.abnormalCode"
          :placeholder="$t('validation.enter', [$t('bizAbnormal.abnormalCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('bizAbnormal.abnormalTitle')" prop="abnormalTitle">
        <el-input
          v-model="queryParams.abnormalTitle"
          :placeholder="$t('validation.enter', [$t('bizAbnormal.abnormalTitle')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('bizAbnormal.abnormalLevel')" prop="abnormalLevel">
        <el-select v-model="queryParams.abnormalLevel" :placeholder="$t('validation.select', [''])" clearable>
          <el-option :label="$t('bizAbnormal.levelLow')" :value="1" />
          <el-option :label="$t('bizAbnormal.levelMedium')" :value="2" />
          <el-option :label="$t('bizAbnormal.levelHigh')" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('bizAbnormal.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('validation.select', [''])" clearable>
          <el-option :label="$t('bizAbnormal.statusPending')" value="0" />
          <el-option :label="$t('bizAbnormal.statusProcessing')" value="1" />
          <el-option :label="$t('bizAbnormal.statusHandled')" value="2" />
          <el-option :label="$t('bizAbnormal.statusClosed')" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('bizAbnormal.lockBiz')" prop="lockBiz">
        <el-select v-model="queryParams.lockBiz" :placeholder="$t('validation.select', [''])" clearable>
          <el-option :label="$t('bizAbnormal.lockNo')" value="0" />
          <el-option :label="$t('bizAbnormal.lockYes')" value="1" />
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
          v-hasPermi="['erp:bizabnormal:add']"
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
          v-hasPermi="['erp:bizabnormal:edit']"
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
          v-hasPermi="['erp:bizabnormal:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:bizabnormal:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bizabnormalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('bizAbnormal.bizType')" align="center" prop="bizType" width="120" />
      <el-table-column :label="$t('bizAbnormal.bizId')" align="center" prop="bizId" width="100" />
      <el-table-column :label="$t('bizAbnormal.abnormalCode')" align="center" prop="abnormalCode" width="120" />
      <el-table-column :label="$t('bizAbnormal.abnormalTitle')" align="center" prop="abnormalTitle" width="180" />
      <el-table-column :label="$t('bizAbnormal.abnormalLevel')" align="center" prop="abnormalLevel" width="60">
        <template slot-scope="scope">
          <el-tag :type="scope.row.abnormalLevel === 3 ? 'danger' : scope.row.abnormalLevel === 2 ? 'warning' : 'info'">
            {{ scope.row.abnormalLevel === 1 ? $t('bizAbnormal.levelLow') : scope.row.abnormalLevel === 2 ? $t('bizAbnormal.levelMedium') : $t('bizAbnormal.levelHigh') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('bizAbnormal.status')" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'danger' : scope.row.status === '1' ? 'warning' : scope.row.status === '2' ? 'success' : 'info'">
            {{ scope.row.status === '0' ? $t('bizAbnormal.statusPending') : scope.row.status === '1' ? $t('bizAbnormal.statusProcessing') : scope.row.status === '2' ? $t('bizAbnormal.statusHandled') : $t('bizAbnormal.statusClosed') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('bizAbnormal.lockBiz')" align="center" prop="lockBiz" width="60">
        <template slot-scope="scope">
          <el-tag :type="scope.row.lockBiz === '1' ? 'danger' : 'info'">
            {{ scope.row.lockBiz === '1' ? $t('status.yes') : $t('status.no') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('bizAbnormal.handleByName')" align="center" prop="handleByName" width="100" />
      <el-table-column :label="$t('bizAbnormal.handleTime')" align="center" prop="handleTime" width="160" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:bizabnormal:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:bizabnormal:remove']"
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

    <!-- 添加或修改业务异常对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('bizAbnormal.bizType')" prop="bizType">
          <el-input v-model="form.bizType" :placeholder="$t('validation.enter', [$t('bizAbnormal.bizType')])" />
        </el-form-item>
        <el-form-item :label="$t('bizAbnormal.bizId')" prop="bizId">
          <el-input-number v-model="form.bizId" :min="1" :placeholder="$t('bizAbnormal.bizId')" />
        </el-form-item>
        <el-form-item :label="$t('bizAbnormal.abnormalCode')" prop="abnormalCode">
          <el-input v-model="form.abnormalCode" :placeholder="$t('validation.enter', [$t('bizAbnormal.abnormalCode')])" />
        </el-form-item>
        <el-form-item :label="$t('bizAbnormal.abnormalTitle')" prop="abnormalTitle">
          <el-input v-model="form.abnormalTitle" :placeholder="$t('validation.enter', [$t('bizAbnormal.abnormalTitle')])" />
        </el-form-item>
        <el-form-item :label="$t('bizAbnormal.abnormalDesc')" prop="abnormalDesc">
          <el-input v-model="form.abnormalDesc" type="textarea" :placeholder="$t('validation.enter', [$t('bizAbnormal.abnormalDesc')])" />
        </el-form-item>
        <el-form-item :label="$t('bizAbnormal.abnormalLevel')" prop="abnormalLevel">
          <el-select v-model="form.abnormalLevel" :placeholder="$t('validation.select', [$t('bizAbnormal.abnormalLevel')])">
            <el-option :label="$t('bizAbnormal.levelLow')" :value="1" />
            <el-option :label="$t('bizAbnormal.levelMedium')" :value="2" />
            <el-option :label="$t('bizAbnormal.levelHigh')" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('bizAbnormal.lockBizLabel')" prop="lockBiz">
          <el-select v-model="form.lockBiz" :placeholder="$t('validation.select', [''])">
            <el-option :label="$t('bizAbnormal.lockNo')" value="0" />
            <el-option :label="$t('bizAbnormal.lockYes')" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('bizAbnormal.handleResult')" prop="handleResult" v-if="form.status !== '0'">
          <el-input v-model="form.handleResult" type="textarea" :placeholder="$t('validation.enter', [$t('bizAbnormal.handleResult')])" />
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
import { listBizabnormal, getBizabnormal, addBizabnormal, updateBizabnormal, delBizabnormal } from "@/api/erp/bizabnormal";

export default {
  name: "BizAbnormalPool",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      bizabnormalList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bizType: null,
        abnormalCode: null,
        abnormalTitle: null,
        abnormalLevel: null,
        status: null,
        lockBiz: null
      },
      form: {}
    };
  },
  computed: {
    rules() {
      return {
        bizType: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        bizId: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        abnormalTitle: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        abnormalLevel: [
          { required: true, message: this.$t('validation.required'), trigger: "change" }
        ]
      };
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询业务异常池列表 */
    getList() {
      this.loading = true;
      listBizabnormal(this.queryParams).then(response => {
        this.bizabnormalList = response.rows;
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
        bizType: null,
        bizId: null,
        abnormalCode: null,
        abnormalTitle: null,
        abnormalDesc: null,
        abnormalLevel: 1,
        status: "0",
        handleById: null,
        handleByName: null,
        handleTime: null,
        handleResult: null,
        lockBiz: "0",
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
      this.title = this.$t('bizAbnormal.addTitle');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getBizabnormal(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('bizAbnormal.editTitle');
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.id != null) {
            updateBizabnormal(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.operationSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addBizabnormal(this.form).then(response => {
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
        return delBizabnormal(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/bizabnormal/export', {
        ...this.queryParams
      }, `bizabnormal_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
