<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区 -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">
      
      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="corpType" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.corpType" placeholder="单位类型" clearable style="width: 140px;">
            <el-option v-for="dict in dict.type.erp_corp_type" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="socialNumber" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.socialNumber" placeholder="纳税人识别号" clearable @keyup.enter.native="handleQuery" style="width: 160px;" />
        </el-form-item>
        <el-form-item prop="corpName" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.corpName" placeholder="单位名称" clearable @keyup.enter.native="handleQuery" style="width: 160px;" />
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:invoice:add']">录入开票信息</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:invoice:edit']">编辑</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:invoice:remove']">删除</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:invoice:export']">导出</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (骨架屏包装) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <el-table class="biz-table" :data="invoiceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="单位名称" align="center" prop="corpName" width="220" />
          <el-table-column label="开票类型" align="center" prop="corpType" width="100">
            <template slot-scope="scope">
              <!-- 去掉冗余背景色，用纯文本字典翻译 -->
              <span style="color: var(--app-text-secondary)">
                {{ (dict.type.erp_corp_type.find(d => d.value === scope.row.corpType) || {}).label || scope.row.corpType }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="纳税人识别号" align="center" prop="socialNumber" width="180" />
          <el-table-column label="开户银行" align="center" prop="corpBank" width="150" />
          <el-table-column label="银行账号" align="center" prop="corpBankNo" width="180" />
          <el-table-column label="联系电话" align="center" prop="corpPhone" width="140" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:invoice:edit']">详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="biz-form">
        <el-form-item label="单位类型" prop="corpType" required>
          <el-select v-model="form.corpType" placeholder="请选择类型" style="width: 100%">
            <el-option v-for="dict in dict.type.erp_corp_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单位名称" prop="corpName" required>
          <el-input v-model="form.corpName" placeholder="请输入单位全称" />
        </el-form-item>
        <el-form-item label="纳税人识别号" prop="socialNumber" required>
          <el-input v-model="form.socialNumber" placeholder="统一社会信用代码" />
        </el-form-item>
        
        <el-divider content-position="left">账户与联络信息</el-divider>

        <el-form-item label="开户银行" prop="corpBank">
          <el-input v-model="form.corpBank" placeholder="请输入开户银行" />
        </el-form-item>
        <el-form-item label="银行账号" prop="corpBankNo">
          <el-input v-model="form.corpBankNo" placeholder="请输入银行账号" />
        </el-form-item>
        <el-form-item label="单位地址" prop="corpAddress">
          <el-input v-model="form.corpAddress" placeholder="开票地址" />
        </el-form-item>
        <el-form-item label="联系电话" prop="corpPhone">
          <el-input v-model="form.corpPhone" placeholder="财务联系电话" />
        </el-form-item>
        <el-form-item label="内部备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="内部管理备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确 定</el-button>
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
      loading: true,
      submitLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      invoiceList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        corpType: null,
        socialNumber: null,
        corpName: null,
      },
      form: {},
      rules: {
        corpType: [{ required: true, message: "不能为空", trigger: "change" }],
        corpName: [{ required: true, message: "不能为空", trigger: "blur" }],
        socialNumber: [{ required: true, message: "不能为空", trigger: "blur" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true;
      listInvoice(this.queryParams).then(res => {
        this.invoiceList = res.rows;
        this.total = res.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    reset() {
      this.form = { id: null, corpType: null, corpId: null, socialNumber: null, corpName: null, corpAddress: null, corpPhone: null, corpBank: null, corpBankNo: null, remark: null };
      this.resetForm("form");
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "录入公司开票信息";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getInvoice(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = "开票信息详情";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateInvoice(this.form) : addInvoice(this.form);
          req.then(() => {
            this.$message.success("保存成功");
            this.open = false;
            this.getList();
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(`确定销毁记录 [${ids}] 吗？`, "高危操作", { type: 'warning' })
        .then(() => delInvoice(ids))
        .then(() => {
          this.getList();
          this.$message.success("数据已销毁");
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/invoice/export', { ...this.queryParams }, `invoice_${new Date().getTime()}.xlsx`);
    }
  }
}
</script>

<style scoped>
.biz-table >>> .el-table__row {
  cursor: pointer;
}
.biz-search-form >>> .el-form-item__content {
  line-height: 32px;
}
</style>
