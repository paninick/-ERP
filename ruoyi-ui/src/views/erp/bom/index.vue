<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="审批状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="进行状态" prop="progressStatus">
        <el-select v-model="queryParams.progressStatus" placeholder="请选择进行状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_progress_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务员" prop="salesName">
        <el-input
          v-model="queryParams.salesName"
          placeholder="请输入业务员"
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
          v-hasPermi="['erp:bom:add']"
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
          v-hasPermi="['erp:bom:edit']"
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
          v-hasPermi="['erp:bom:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:bom:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="审批状态" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="进行状态" align="center" prop="progressStatus" />
      <el-table-column label="客户" align="center" prop="customerName" />
      <el-table-column label="打样类型" align="center" prop="sampleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleType"/>
        </template>
      </el-table-column>
      <el-table-column label="样品款式" align="center" prop="styleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column label="样品类型" align="center" prop="sampleCategoryType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column label="款号" align="center" prop="styleCode" />
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" />
      <el-table-column label="业务员" align="center" prop="salesName" />
      <el-table-column label="要求交期" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工艺书编号" align="center" prop="techNo" />
      <el-table-column label="打样编号" align="center" prop="sampleNo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:bom:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:bom:remove']"
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

    <!-- 添加或修改样衣BOM对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="打样类型" prop="sampleType">
          <el-select v-model="form.sampleType" placeholder="请选择打样类型">
            <el-option
              v-for="dict in dict.type.erp_sample_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="样品款式" prop="styleType">
          <el-select v-model="form.styleType" placeholder="请选择样品款式">
            <el-option
              v-for="dict in dict.type.erp_sample_style"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="样品类型" prop="sampleCategoryType">
          <el-select v-model="form.sampleCategoryType" placeholder="请选择样品类型">
            <el-option
              v-for="dict in dict.type.erp_sample_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="打样编号" prop="sampleNo">
          <el-input v-model="form.sampleNo" placeholder="请输入打样编号" />
        </el-form-item>
        <el-form-item label="大货款号" prop="bulkOrderNo">
          <el-input v-model="form.bulkOrderNo" placeholder="请输入大货款号" />
        </el-form-item>
        <el-form-item label="要求交期" prop="dueDate">
          <el-date-picker clearable
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择要求交期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="业务员" prop="salesName">
          <el-input v-model="form.salesName" placeholder="请输入业务员" />
        </el-form-item>
        <el-form-item label="审批状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="请选择审批状态">
            <el-option
              v-for="dict in dict.type.erp_sample_audit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="进行状态" prop="progressStatus">
          <el-select v-model="form.progressStatus" placeholder="请选择进行状态">
            <el-option
              v-for="dict in dict.type.erp_progress_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工艺书编号" prop="techNo">
          <el-input v-model="form.techNo" placeholder="请输入工艺书编号" />
        </el-form-item>
        <el-form-item label="审批人" prop="auditByNickName">
          <el-input v-model="form.auditByNickName" placeholder="请输入审批人" />
        </el-form-item>
        <el-form-item label="审批时间" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择审批时间">
          </el-date-picker>
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
import { listBom, getBom, delBom, addBom, updateBom } from "@/api/erp/bom"

export default {
  name: "Bom",
  dicts: ['erp_sample_style', 'erp_sample_emergency', 'erp_sample_category', 'erp_sample_audit_status', 'erp_sample_type', 'erp_progress_status'],
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
      // 样衣BOM表格数据
      bomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        auditStatus: null,
        progressStatus: null,
        salesName: null,
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
    /** 查询样衣BOM列表 */
    getList() {
      this.loading = true
      listBom(this.queryParams).then(response => {
        this.bomList = response.rows
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
        sampleNo: null,
        sampleType: null,
        customerId: null,
        customerName: null,
        styleType: null,
        sampleCategoryType: null,
        bulkOrderNo: null,
        dueDate: null,
        salesId: null,
        salesName: null,
        techNo: null,
        progressStatus: null,
        auditStatus: null,
        auditBy: null,
        auditByNickName: null,
        auditTime: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加样衣BOM"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getBom(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改样衣BOM"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBom(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除样衣BOM编号为"' + ids + '"的数据项？').then(function() {
        return delBom(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/bom/export', {
        ...this.queryParams
      }, `bom_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
