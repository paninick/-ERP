<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="外协单号" prop="outsourceNo">
        <el-input
          v-model="queryParams.outsourceNo"
          placeholder="请输入外协单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入工序名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外协厂商" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入外协厂商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="待发出" value="0" />
          <el-option label="已发出" value="1" />
          <el-option label="部分收回" value="2" />
          <el-option label="全部收回" value="3" />
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
          v-hasPermi="['erp:outsource:add']"
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
          v-hasPermi="['erp:outsource:edit']"
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
          v-hasPermi="['erp:outsource:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:outsource:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outsourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="外协单号" align="center" prop="outsourceNo" width="140" />
      <el-table-column label="工序名称" align="center" prop="processName" width="120" />
      <el-table-column label="外协厂商" align="center" prop="supplierName" width="140" />
      <el-table-column label="是否调拨" align="center" prop="isTransfer" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isTransfer === 1 ? 'warning' : 'info'">
            {{ scope.row.isTransfer === 1 ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="总件数" align="center" prop="totalQty" width="80" />
      <el-table-column label="确认收货" align="center" prop="confirmQty" width="80" />
      <el-table-column label="次品" align="center" prop="defectQty" width="60" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '3' ? 'success' : scope.row.status === '1' ? 'warning' : scope.row.status === '2' ? 'primary' : 'info'">
            {{ scope.row.status === '0' ? '待发出' : scope.row.status === '1' ? '已发出' : scope.row.status === '2' ? '部分收回' : '全部收回' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发出时间" align="center" prop="outboundTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outboundTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:outsource:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:outsource:remove']"
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

    <!-- 添加或修改外协加工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="外协单号" prop="outsourceNo">
          <el-input v-model="form.outsourceNo" placeholder="请输入外协单号" />
        </el-form-item>
        <el-form-item label="工序" prop="processId">
          <el-input v-model="form.processId" placeholder="工序ID" />
        </el-form-item>
        <el-form-item label="工序名称" prop="processName">
          <el-input v-model="form.processName" placeholder="请输入工序名称" />
        </el-form-item>
        <el-form-item label="外协厂商" prop="supplierId">
          <el-input v-model="form.supplierId" placeholder="外协厂商ID" />
        </el-form-item>
        <el-form-item label="外协厂商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入厂商名称" />
        </el-form-item>
        <el-form-item label="是否调拨" prop="isTransfer">
          <el-radio-group v-model="form.isTransfer">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.isTransfer === 1" label="调出外协" prop="transferFrom">
          <el-input v-model="form.transferFrom" placeholder="调出外协ID" />
        </el-form-item>
        <el-form-item v-if="form.isTransfer === 1" label="调入外协" prop="transferTo">
          <el-input v-model="form.transferTo" placeholder="调入外协ID" />
        </el-form-item>
        <el-form-item label="总件数" prop="totalQty">
          <el-input-number v-model="form.totalQty" :min="1" placeholder="总件数" />
        </el-form-item>
        <el-form-item label="理论重量(kg)" prop="theoryWeight">
          <el-input-number v-model="form.theoryWeight" :precision="2" :min="0" placeholder="理论重量" />
        </el-form-item>
        <el-form-item label="单位加工费" prop="unitPrice">
          <el-input-number v-model="form.unitPrice" :precision="2" :min="0" placeholder="单位加工费" />
        </el-form-item>
        <el-form-item label="运费" prop="freight">
          <el-input-number v-model="form.freight" :precision="2" :min="0" placeholder="运费" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="待发出" value="0" />
            <el-option label="已发出" value="1" />
            <el-option label="部分收回" value="2" />
            <el-option label="全部收回" value="3" />
          </el-select>
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
import { listOutsource, getOutsource, addOutsource, updateOutsource, delOutsource } from "@/api/erp/outsource";

export default {
  name: "OutsourceOrder",
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
      // 外协加工单表格数据
      outsourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        outsourceNo: null,
        processName: null,
        supplierName: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        outsourceNo: [
          { required: true, message: "外协单号不能为空", trigger: "blur" }
        ],
        processId: [
          { required: true, message: "工序ID不能为空", trigger: "blur" }
        ],
        supplierId: [
          { required: true, message: "外协厂商ID不能为空", trigger: "blur" }
        ],
        totalQty: [
          { required: true, message: "总件数不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询外协加工单列表 */
    getList() {
      this.loading = true;
      listOutsource(this.queryParams).then(response => {
        this.outsourceList = response.rows;
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
        id: null,
        outsourceNo: null,
        processId: null,
        processName: null,
        supplierId: null,
        supplierName: null,
        isTransfer: 0,
        transferFrom: null,
        transferTo: null,
        totalQty: 0,
        theoryWeight: null,
        confirmQty: 0,
        defectQty: 0,
        actualWeight: null,
        unitPrice: null,
        totalPrice: null,
        freight: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加外协加工单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getOutsource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改外协加工单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOutsource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutsource(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除外协加工单编号为"' + ids + '"的数据项？').then(function() {
        return delOutsource(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/outsource/export', {
        ...this.queryParams
      }, `outsource_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
