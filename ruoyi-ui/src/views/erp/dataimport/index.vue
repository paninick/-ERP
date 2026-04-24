<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="true">
      <el-form-item label="导入名称" prop="importName">
        <el-input v-model="queryParams.importName" placeholder="请输入导入名称" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-card>
      <el-table v-loading="loading" :data="dataImportList">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column property="importId" label="导入ID" width="80" />
        <el-table-column property="importName" label="导入名称" width="150" />
        <el-table-column property="tableName" label="表名" width="150" />
        <el-table-column property="fileName" label="文件名" width="200" />
        <el-table-column property="totalCount" label="总记录数" width="100" />
        <el-table-column property="successCount" label="成功" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.successCount }} / {{ scope.row.totalCount }}</span>
          </template>
        </el-table-column>
        <el-table-column property="failCount" label="失败" width="80" />
        <el-table-column property="status" label="状态" width="80">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '1' ? 'success' : scope.row.status === '2' ? 'danger' : 'warning'">
              {{ scope.row.status === '0' ? '处理中' : scope.row.status === '1' ? '成功' : '失败' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column property="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" class="padding-left-0">
          <template slot-scope="scope">
            <el-button link type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button link type="danger" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)">删除</el-button>
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
        <el-form-item label="导入名称" prop="importName">
          <el-input v-model="form.importName" placeholder="请输入导入名称" />
        </el-form-item>
        <el-form-item label="表名" prop="tableName">
          <el-input v-model="form.tableName" placeholder="请输入表名" />
        </el-form-item>
        <el-form-item label="文件名" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
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
      rules: {
        importName: [
          { required: true, message: "导入名称不能为空", trigger: "blur" }
        ]
      },
      dataImportList: [],
      total: 0
    };
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
      this.title = "添加数据导入";
    },
    handleUpdate(row) {
      getDataImport(row.importId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据导入";
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
          this.$modal.msgSuccess(this.form.importId != null ? "修改成功" : "新增成功");
          this.open = false;
          this.getList();
        }).finally(() => { this.submitLoading = false });
      });
    },
    handleDelete(row) {
      this.$modal.confirm(`是否确认删除数据导入编号为"${row.importId}"的数据项？`).then(() => {
        return delDataImport([row.importId]);
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.getList();
      }).catch(() => {});
    }
  }
};
</script>
