<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="true">
      <el-form-item :label="$t('dataImport.importName')" prop="importName">
        <el-input v-model="queryParams.importName" :placeholder="$t('validation.enter', [$t('dataImport.importName')])" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button @click="resetQuery">{{ $t('btn.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-card>
      <el-table v-loading="loading" :data="dataImportList">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column property="importId" :label="$t('dataImport.importId')" width="80" />
        <el-table-column property="importName" :label="$t('dataImport.importName')" width="150" />
        <el-table-column property="tableName" :label="$t('dataImport.tableName')" width="150" />
        <el-table-column property="fileName" :label="$t('dataImport.fileName')" width="200" />
        <el-table-column property="totalCount" :label="$t('dataImport.totalCount')" width="100" />
        <el-table-column property="successCount" :label="$t('dataImport.success')" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.successCount }} / {{ scope.row.totalCount }}</span>
          </template>
        </el-table-column>
        <el-table-column property="failCount" :label="$t('dataImport.fail')" width="80" />
        <el-table-column property="status" :label="$t('dataImport.status')" width="80">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '1' ? 'success' : scope.row.status === '2' ? 'danger' : 'warning'">
              {{ scope.row.status === '0' ? $t('dataImport.processing') : scope.row.status === '1' ? $t('dataImport.successStatus') : $t('dataImport.failStatus') }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column property="createTime" :label="$t('system.createTime')" width="180" />
        <el-table-column :label="$t('system.operation')" class="padding-left-0">
          <template slot-scope="scope">
            <el-button link type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate(scope.row)">{{ $t('btn.edit') }}</el-button>
            <el-button link type="danger" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)">{{ $t('btn.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item :label="$t('dataImport.importName')" prop="importName">
          <el-input v-model="form.importName" :placeholder="$t('validation.enter', [$t('dataImport.importName')])" />
        </el-form-item>
        <el-form-item :label="$t('dataImport.tableName')" prop="tableName">
          <el-input v-model="form.tableName" :placeholder="$t('validation.enter', [$t('dataImport.tableName')])" />
        </el-form-item>
        <el-form-item :label="$t('dataImport.fileName')" prop="fileName">
          <el-input v-model="form.fileName" :placeholder="$t('validation.enter', [$t('dataImport.fileName')])" />
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
import { listDataImport, getDataImport, addDataImport, updateDataImport, delDataImport } from "@/api/erp/dataimport";

export default {
  name: "DataImport",
  data() {
    return {
      loading: true,
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        importName: null,
        tableName: null,
        status: null
      },
      form: {},
      dataImportList: [],
      total: 0
    };
  },
  computed: {
    rules() {
      return {
        importName: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ]
      };
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listDataImport(this.queryParams).then(response => {
        this.dataImportList = response.rows;
        this.total = response.total;
      }).finally(() => {
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.resetFormData();
    },
    resetFormData() {
      this.form = {
        importId: null,
        importName: null,
        tableName: null,
        fileName: null,
        status: null,
        remark: null
      };
      if (this.$refs.form) {
        this.$refs.form.clearValidate();
      }
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleAdd() {
      this.resetFormData();
      this.open = true;
      this.title = this.$t('dataImport.addTitle');
    },
    handleUpdate(row) {
      getDataImport(row.importId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('dataImport.editTitle');
      });
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return;
        }
        this.submitLoading = true;
        const request = this.form.importId != null
          ? updateDataImport(this.form)
          : addDataImport(this.form);
        request.then(() => {
          this.$modal.msgSuccess(this.form.importId != null ? this.$t('msg.editSuccess') : this.$t('msg.addSuccess'));
          this.open = false;
          this.getList();
        }).finally(() => { this.submitLoading = false });
      });
    },
    handleDelete(row) {
      this.$modal.confirm(this.$t('msg.deleteConfirm', [row.importId])).then(() => {
        return delDataImport([row.importId]);
      }).then(() => {
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
        this.getList();
      }).catch(() => {});
    }
  }
};
</script>
