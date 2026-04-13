<template>
  <div>
    <el-form :model="queryParams" :inline="true" v-show="true">
      <el-form-item label="导入名称" prop="importName">
        <el-input v-model="queryParams.importName" placeholder="请输入导入名称" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">搜索</el-button>
        <el-button @click="reset">重置</el-button>
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

    <pagination v-show="total>0" :total="total" :page-num.sync="queryParams.pageNum" :page-row.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改对话框 -->
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
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
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单全选
      multiple: true,
      // 非单个删除
      delFlag: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        importName: null,
        tableName: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
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
        importId: null,
        importName: null,
        tableName: null,
        fileName: null,
        status: null,
        remark: null
      };
      this.$refs["form"].clearValidate();
    },
    // 查询按钮
    query() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 重置按钮
    reset() {
      this.queryParams.pageNum = 1;
      this.queryParams.importName = "";
      this.queryParams.tableName = "";
      this.queryParams.status = "";
      this.getList();
    },
    // 新增按钮
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据导入";
    },
    // 修改按钮
    handleUpdate(row) {
      getDataImport(row.importId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据导入";
      });
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.importId !== undefined) {
            updateDataImport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDataImport(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 删除按钮
    handleDelete(row) {
      this.$modal.confirm('是否确认删除数据导入编号为"' + row.importId + '"的数据项？').then(function() {
        delDataImport([row.importId]).then(response => {
          this.$modal.msgSuccess("删除成功");
          this.getList();
        });
      }).catch(() => {});
    }
  }
};
</script>
