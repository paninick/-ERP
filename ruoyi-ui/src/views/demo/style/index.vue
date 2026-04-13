<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-input
          v-model="queryParams.styleNo"
          placeholder="款号"
          clearable
          @keyup.enter="handleQuery"
          style="margin-bottom: 10px"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleQuery" />
        </el-input>
      </el-col>
      <el-col :span="8">
        <el-input
          v-model="queryParams.styleName"
          placeholder="款式名称"
          clearable
          @keyup.enter="handleQuery"
          style="margin-bottom: 10px"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleQuery" />
        </el-input>
      </el-col>
      <el-col :span="8">
        <el-select
          v-model="queryParams.category"
          placeholder="品类"
          clearable
          style="width: 100%; margin-bottom: 10px"
        >
          <el-option label="T恤" value="T恤" />
          <el-option label="短裤" value="短裤" />
          <el-option label="长裤" value="长裤" />
          <el-option label="外套" value="外套" />
          <el-option label="裙子" value="裙子" />
        </el-select>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-select
          v-model="queryParams.season"
          placeholder="季节"
          clearable
          style="width: 100%; margin-bottom: 10px"
        >
          <el-option label="春季" value="春季" />
          <el-option label="夏季" value="夏季" />
          <el-option label="秋季" value="秋季" />
          <el-option label="冬季" value="冬季" />
        </el-select>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="handleQuery">
          <i class="el-icon-search"></i> 查询
        </el-button>
        <el-button @click="handleReset">
          <i class="el-icon-refresh"></i> 重置
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="styleList">
      <el-table-column label="款号" prop="styleNo" width="120" />
      <el-table-column label="款式名称" prop="styleName" width="180" />
      <el-table-column label="品类" prop="category" width="100" />
      <el-table-column label="季节" prop="season" width="100" />
      <el-table-column label="面料费" prop="fabricCost" width="100">
        <template slot-scope="scope">
          {{ scope.row.fabricCost }}
        </template>
      </el-table-column>
      <el-table-column label="辅料费" prop="accessoryCost" width="100">
        <template slot-scope="scope">
          {{ scope.row.accessoryCost }}
        </template>
      </el-table-column>
      <el-table-column label="CMT加工费" prop="cmtCost" width="100">
        <template slot-scope="scope">
          {{ scope.row.cmtCost }}
        </template>
      </el-table-column>
      <el-table-column label="标准成本" prop="standardCost" width="120">
        <template slot-scope="scope">
          {{ scope.row.standardCost }}
        </template>
      </el-table-column>
      <el-table-column label="目标利润率" prop="profitRate" width="120">
        <template slot-scope="scope">
          {{ scope.row.profitRate }}%
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >
            查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="80%">
      <el-form
        :model="form"
        :rules="rules"
        ref="styleForm"
        label-width="100px"
      >
        <el-form-item label="款号" prop="styleNo">
          <el-input v-model="form.styleNo" placeholder="请输入款号" />
        </el-form-item>
        <el-form-item label="款式名称" prop="styleName">
          <el-input v-model="form.styleName" placeholder="请输入款式名称" />
        </el-form-item>
        <el-form-item label="品类" prop="category">
          <el-select v-model="form.category" placeholder="请选择品类">
            <el-option label="T恤" value="T恤" />
            <el-option label="短裤" value="短裤" />
            <el-option label="长裤" value="长裤" />
            <el-option label="外套" value="外套" />
            <el-option label="裙子" value="裙子" />
          </el-select>
        </el-form-item>
        <el-form-item label="季节" prop="season">
          <el-select v-model="form.season" placeholder="请选择季节">
            <el-option label="春季" value="春季" />
            <el-option label="夏季" value="夏季" />
            <el-option label="秋季" value="秋季" />
            <el-option label="冬季" value="冬季" />
          </el-select>
        </el-form-item>
        <el-form-item label="面料费" prop="fabricCost">
          <el-input-number
            v-model="form.fabricCost"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入面料费"
          />
        </el-form-item>
        <el-form-item label="辅料费" prop="accessoryCost">
          <el-input-number
            v-model="form.accessoryCost"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入辅料费"
          />
        </el-form-item>
        <el-form-item label="CMT加工费" prop="cmtCost">
          <el-input-number
            v-model="form.cmtCost"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入CMT加工费"
          />
        </el-form-item>
        <el-form-item label="目标利润率" prop="profitRate">
          <el-input-number
            v-model="form.profitRate"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入目标利润率"
          />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList, getById, add, update, remove } from "@/api/demo/style";

export default {
  name: "StyleManage",
  data() {
    return {
      loading: false,
      styleList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        styleNo: "",
        styleName: "",
        category: undefined,
        season: undefined
      },
      dialogVisible: false,
      dialogTitle: "",
      form: {},
      rules: {
        styleNo: [
          { required: true, message: "请输入款号", trigger: "blur" }
        ],
        styleName: [
          { required: true, message: "请输入款式名称", trigger: "blur" }
        ],
        category: [
          { required: true, message: "请选择品类", trigger: "change" }
        ],
        season: [
          { required: true, message: "请选择季节", trigger: "change" }
        ],
        fabricCost: [
          { required: true, message: "请输入面料费", trigger: "blur" }
        ],
        accessoryCost: [
          { required: true, message: "请输入辅料费", trigger: "blur" }
        ],
        cmtCost: [
          { required: true, message: "请输入CMT加工费", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      getList(this.queryParams).then(response => {
        this.styleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleReset() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        styleNo: "",
        styleName: "",
        category: undefined,
        season: undefined
      };
      this.getList();
    },
    handleView(row) {
      this.dialogTitle = "查看款式";
      this.dialogVisible = true;
      getById(row.id).then(response => {
        this.form = response;
      });
    },
    handleEdit(row) {
      this.dialogTitle = "编辑款式";
      this.dialogVisible = true;
      getById(row.id).then(response => {
        this.form = response;
      });
    },
    handleDelete(row) {
      this.$confirm("确定要删除该款式吗？", "提示", {
        type: "warning"
      }).then(() => {
        remove(row.id).then(response => {
          this.$message.success("删除成功");
          this.getList();
        });
      }).catch(() => {
        this.$message.info("已取消删除");
      });
    },
    handleSubmit() {
      this.$refs.styleForm.validate(valid => {
        if (valid) {
          if (this.form.id) {
            update(this.form).then(response => {
              this.$message.success("更新成功");
              this.dialogVisible = false;
              this.getList();
            });
          } else {
            add(this.form).then(response => {
              this.$message.success("添加成功");
              this.dialogVisible = false;
              this.getList();
            });
          }
        }
      });
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
