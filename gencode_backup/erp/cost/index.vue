<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="打样id" prop="noticeId">
        <el-input
          v-model="queryParams.noticeId"
          placeholder="请输入打样id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工艺书id" prop="techId">
        <el-input
          v-model="queryParams.techId"
          placeholder="请输入工艺书id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主料id" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入主料id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="颜色" prop="color">
        <el-input
          v-model="queryParams.color"
          placeholder="请输入颜色"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单耗" prop="unitConsumption">
        <el-input
          v-model="queryParams.unitConsumption"
          placeholder="请输入单耗"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="损耗方式" prop="lossType">
        <el-select v-model="queryParams.lossType" placeholder="请选择损耗方式" clearable>
          <el-option
            v-for="dict in dict.type.erp_loss_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="损耗" prop="wastage">
        <el-input
          v-model="queryParams.wastage"
          placeholder="请输入损耗"
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
          v-hasPermi="['erp:cost:add']"
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
          v-hasPermi="['erp:cost:edit']"
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
          v-hasPermi="['erp:cost:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:cost:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="costList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="打样id" align="center" prop="noticeId" />
      <el-table-column label="工艺书id" align="center" prop="techId" />
      <el-table-column label="材料类型" align="center" prop="materialType" />
      <el-table-column label="主料id" align="center" prop="materialId" />
      <el-table-column label="颜色" align="center" prop="color" />
      <el-table-column label="单耗" align="center" prop="unitConsumption" />
      <el-table-column label="损耗方式" align="center" prop="lossType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_loss_type" :value="scope.row.lossType"/>
        </template>
      </el-table-column>
      <el-table-column label="损耗" align="center" prop="wastage" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:cost:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:cost:remove']"
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

    <!-- 添加或修改工艺书单耗对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="打样id" prop="noticeId">
          <el-input v-model="form.noticeId" placeholder="请输入打样id" />
        </el-form-item>
        <el-form-item label="工艺书id" prop="techId">
          <el-input v-model="form.techId" placeholder="请输入工艺书id" />
        </el-form-item>
        <el-form-item label="主料id" prop="materialId">
          <el-input v-model="form.materialId" placeholder="请输入主料id" />
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入颜色" />
        </el-form-item>
        <el-form-item label="单耗" prop="unitConsumption">
          <el-input v-model="form.unitConsumption" placeholder="请输入单耗" />
        </el-form-item>
        <el-form-item label="损耗方式" prop="lossType">
          <el-select v-model="form.lossType" placeholder="请选择损耗方式">
            <el-option
              v-for="dict in dict.type.erp_loss_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="损耗" prop="wastage">
          <el-input v-model="form.wastage" placeholder="请输入损耗" />
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
import { listCost, getCost, delCost, addCost, updateCost } from "@/api/erp/cost"

export default {
  name: "Cost",
  dicts: ['erp_loss_type'],
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
      // 工艺书单耗表格数据
      costList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeId: null,
        techId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
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
    /** 查询工艺书单耗列表 */
    getList() {
      this.loading = true
      listCost(this.queryParams).then(response => {
        this.costList = response.rows
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
        noticeId: null,
        techId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加工艺书单耗"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCost(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改工艺书单耗"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCost(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCost(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工艺书单耗编号为"' + ids + '"的数据项？').then(function() {
        return delCost(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/cost/export', {
        ...this.queryParams
      }, `cost_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
