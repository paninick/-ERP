<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="类型 1-供应商 2-物流公司" prop="corpType">
        <el-select v-model="queryParams.corpType" placeholder="请选择类型 1-供应商 2-物流公司" clearable>
          <el-option
            v-for="dict in dict.type.erp_corp_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="纳税人识别号" prop="socialNumber">
        <el-input
          v-model="queryParams.socialNumber"
          placeholder="请输入纳税人识别号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位名称" prop="corpName">
        <el-input
          v-model="queryParams.corpName"
          placeholder="请输入单位名称"
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
          v-hasPermi="['erp:invoice:add']"
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
          v-hasPermi="['erp:invoice:edit']"
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
          v-hasPermi="['erp:invoice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:invoice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="invoiceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="类型 1-供应商 2-物流公司" align="center" prop="corpType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_corp_type" :value="scope.row.corpType"/>
        </template>
      </el-table-column>
      <el-table-column label="纳税人识别号" align="center" prop="socialNumber" />
      <el-table-column label="单位名称" align="center" prop="corpName" />
      <el-table-column label="单位地址" align="center" prop="corpAddress" />
      <el-table-column label="联系电话" align="center" prop="corpPhone" />
      <el-table-column label="开户银行" align="center" prop="corpBank" />
      <el-table-column label="银行账号" align="center" prop="corpBankNo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:invoice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:invoice:remove']"
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

    <!-- 添加或修改公司开票信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="类型 1-供应商 2-物流公司" prop="corpType">
          <el-select v-model="form.corpType" placeholder="请选择类型 1-供应商 2-物流公司">
            <el-option
              v-for="dict in dict.type.erp_corp_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司id" prop="corpId">
          <el-input v-model="form.corpId" placeholder="请输入公司id" />
        </el-form-item>
        <el-form-item label="纳税人识别号" prop="socialNumber">
          <el-input v-model="form.socialNumber" placeholder="请输入纳税人识别号" />
        </el-form-item>
        <el-form-item label="单位名称" prop="corpName">
          <el-input v-model="form.corpName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="单位地址" prop="corpAddress">
          <el-input v-model="form.corpAddress" placeholder="请输入单位地址" />
        </el-form-item>
        <el-form-item label="联系电话" prop="corpPhone">
          <el-input v-model="form.corpPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="开户银行" prop="corpBank">
          <el-input v-model="form.corpBank" placeholder="请输入开户银行" />
        </el-form-item>
        <el-form-item label="银行账号" prop="corpBankNo">
          <el-input v-model="form.corpBankNo" placeholder="请输入银行账号" />
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
import { listInvoice, getInvoice, delInvoice, addInvoice, updateInvoice } from "@/api/erp/invoice"

export default {
  name: "Invoice",
  dicts: ['erp_corp_type'],
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
      // 公司开票信息表格数据
      invoiceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        corpType: null,
        socialNumber: null,
        corpName: null,
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
    /** 查询公司开票信息列表 */
    getList() {
      this.loading = true
      listInvoice(this.queryParams).then(response => {
        this.invoiceList = response.rows
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
        corpType: null,
        corpId: null,
        socialNumber: null,
        corpName: null,
        corpAddress: null,
        corpPhone: null,
        corpBank: null,
        corpBankNo: null,
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
      this.title = "添加公司开票信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getInvoice(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改公司开票信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInvoice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addInvoice(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除公司开票信息编号为"' + ids + '"的数据项？').then(function() {
        return delInvoice(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/invoice/export', {
        ...this.queryParams
      }, `invoice_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
