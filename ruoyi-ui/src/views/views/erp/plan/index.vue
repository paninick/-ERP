<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="大货款号" prop="bulkOrderNo">
        <el-input
          v-model="queryParams.bulkOrderNo"
          placeholder="请输入大货款号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打样编号" prop="sampleStyleNo">
        <el-input
          v-model="queryParams.sampleStyleNo"
          placeholder="请输入打样编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产状态" prop="produceStatus">
        <el-input
          v-model="queryParams.produceStatus"
          placeholder="请输入生产状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务员" prop="salesName">
        <el-select v-model="queryParams.salesName" placeholder="请选择业务员" clearable>
          <el-option
            v-for="dict in dict.type.erp_sales_name"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['erp:plan:add']"
        >新增生产计划</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:plan:edit']"
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
          v-hasPermi="['erp:plan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:plan:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >生产进度</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生产状态" align="center" prop="produceStatus" width="100" />
      <el-table-column label="类型" align="center" prop="type" width="80" />
      <el-table-column label="生产计划编号" align="center" prop="planNo" width="160" show-overflow-tooltip />
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" width="140" show-overflow-tooltip />
      <el-table-column label="打样款号" align="center" prop="sampleStyleNo" width="120" show-overflow-tooltip />
      <el-table-column label="客户名称" align="center" prop="customerName" width="120" show-overflow-tooltip />
      <el-table-column label="款式/品类" align="center" prop="styleCategory" width="100" show-overflow-tooltip />
      <el-table-column label="业务员" align="center" prop="salesName" width="80" />
      <el-table-column label="原料到货日期" align="center" prop="materialArrivalDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.materialArrivalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="前道日期" align="center" prop="preProcessDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.preProcessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="送检日期" align="center" prop="inspectionDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="进仓日期" align="center" prop="inBoundDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inBoundDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="船期" align="center" prop="shippingDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.shippingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="后道日期" align="center" prop="postProcessDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.postProcessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交期" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="auditStatus" width="100" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:plan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:plan:remove']"
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

    <!-- 添加或修改生产计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产状态" prop="produceStatus">
              <el-input v-model="form.produceStatus" placeholder="请输入生产状态" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型" prop="type">
              <el-input v-model="form.type" placeholder="请输入类型" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产计划编号" prop="planNo">
              <el-input v-model="form.planNo" placeholder="请输入生产计划编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="大货款号" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" placeholder="请输入大货款号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="打样款号" prop="sampleStyleNo">
              <el-input v-model="form.sampleStyleNo" placeholder="请输入打样款号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="customerName">
              <el-input v-model="form.customerName" placeholder="请输入客户名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="款式/品类" prop="styleCategory">
              <el-input v-model="form.styleCategory" placeholder="请输入款式/品类" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="业务员" prop="salesName">
              <el-input v-model="form.salesName" placeholder="请输入业务员" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="原料到货日期" prop="materialArrivalDate">
              <el-date-picker clearable
                v-model="form.materialArrivalDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择原料到货日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="前道日期" prop="preProcessDate">
              <el-date-picker clearable
                v-model="form.preProcessDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择前道日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="送检日期" prop="inspectionDate">
              <el-date-picker clearable
                v-model="form.inspectionDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择送检日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进仓日期" prop="inBoundDate">
              <el-date-picker clearable
                v-model="form.inBoundDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择进仓日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="船期" prop="shippingDate">
              <el-date-picker clearable
                v-model="form.shippingDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择船期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="后道日期" prop="postProcessDate">
              <el-date-picker clearable
                v-model="form.postProcessDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择后道日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="交期" prop="dueDate">
              <el-date-picker clearable
                v-model="form.dueDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择交期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批状态" prop="auditStatus">
              <el-input v-model="form.auditStatus" placeholder="请输入审批状态" />
            </el-form-item>
          </el-col>
        </el-row>
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
import { listPlan, getPlan, delPlan, addPlan, updatePlan } from "@/api/erp/plan"

export default {
  name: "Plan",
  dicts: [],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      planList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bulkOrderNo: null,
        sampleStyleNo: null,
        customerName: null,
        produceStatus: null,
        salesName: null
      },
      form: {},
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listPlan(this.queryParams).then(response => {
        this.planList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        produceStatus: null,
        type: null,
        planNo: null,
        bulkOrderNo: null,
        sampleStyleNo: null,
        customerName: null,
        styleCategory: null,
        salesName: null,
        materialArrivalDate: null,
        preProcessDate: null,
        inspectionDate: null,
        inBoundDate: null,
        shippingDate: null,
        postProcessDate: null,
        dueDate: null,
        auditStatus: null,
        remark: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加生产计划"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPlan(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改生产计划"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPlan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除生产计划编号为"' + ids + '"的数据项？').then(function() {
        return delPlan(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`)
    },
    handleImport() {
      this.$message.info("生产进度功能开发中...")
    }
  }
}
</script>
