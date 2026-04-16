<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="主料类型" prop="materialAType">
        <el-input
          v-model="queryParams.materialAType"
          placeholder="请输入主料类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="辅料类型" prop="materialBType">
        <el-input
          v-model="queryParams.materialBType"
          placeholder="请输入辅料类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工艺代码" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          placeholder="请输入工艺代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['erp:processLossMatrix:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:processLossMatrix:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:processLossMatrix:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:processLossMatrix:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processLossMatrixList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主料类型" align="center" prop="materialAType" />
      <el-table-column label="辅料类型" align="center" prop="materialBType" />
      <el-table-column label="工艺代码" align="center" prop="processCode" />
      <el-table-column label="标准损耗率" align="center" prop="standardLossRate" />
      <el-table-column label="实际平均损耗" align="center" prop="actualAvgLoss" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:processLossMatrix:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:processLossMatrix:remove']"
          >删除</el-button>
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

    <!-- 添加或修改工序损耗矩阵对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="主料类型" prop="materialAType">
          <el-input v-model="form.materialAType" placeholder="请输入主料类型" />
        </el-form-item>
        <el-form-item label="辅料类型" prop="materialBType">
          <el-input v-model="form.materialBType" placeholder="请输入辅料类型，无辅料可为空" />
        </el-form-item>
        <el-form-item label="工艺代码" prop="processCode">
          <el-input v-model="form.processCode" placeholder="请输入工艺代码" />
        </el-form-item>
        <el-form-item label="标准损耗率" prop="standardLossRate">
          <el-input-number v-model="form.standardLossRate" :precision="4" :min="0" :max="100" placeholder="标准损耗率%，如 3.5" />
        </el-form-item>
        <el-form-item label="实际平均损耗" prop="actualAvgLoss">
          <el-input-number v-model="form.actualAvgLoss" :precision="4" :min="0" :max="100" placeholder="实际平均损耗%" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listProcessLossMatrix, getProcessLossMatrix, delProcessLossMatrix, addProcessLossMatrix, updateProcessLossMatrix } from "@/api/erp/processLossMatrix"

export default {
  name: "ProcessLossMatrix",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工序损耗矩阵表格数据
      processLossMatrixList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialAType: null,
        materialBType: null,
        processCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工序损耗矩阵列表 */
    getList() {
      this.loading = true
      listProcessLossMatrix(this.queryParams).then(response => {
        this.processLossMatrixList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        materialAType: null,
        materialBType: null,
        processCode: null,
        standardLossRate: null,
        actualAvgLoss: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加工序损耗矩阵"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcessLossMatrix(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改工序损耗矩阵"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProcessLossMatrix(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProcessLossMatrix(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除工序损耗矩阵编号为"' + ids + '"的数据项？').then(function() {
        return delProcessLossMatrix(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/processLossMatrix/export', {
        ...this.queryParams
      }, `processLossMatrix_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
