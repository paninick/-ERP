<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="流水号" prop="serialNo">
        <el-input
          v-model="queryParams.serialNo"
          placeholder="请输入流水号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单ID" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工票ID" prop="jobId">
        <el-input
          v-model="queryParams.jobId"
          placeholder="请输入工票ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="颜色" prop="colorCode">
        <el-input
          v-model="queryParams.colorCode"
          placeholder="请输入颜色编码"
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
          v-hasPermi="['erp:productSerial:add']"
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
          v-hasPermi="['erp:productSerial:edit']"
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
          v-hasPermi="['erp:productSerial:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi='["erp:productSerial:export"]'
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-scan"
          size="mini"
          @click="openScan"
        >扫码查询</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productSerialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流水号" align="center" prop="serialNo" width="180" />
      <el-table-column label="订单ID" align="center" prop="orderId" width="80" />
      <el-table-column label="工票ID" align="center" prop="jobId" width="80" />
      <el-table-column label="颜色" align="center" prop="colorCode" width="80" />
      <el-table-column label="尺码" align="center" prop="sizeCode" width="80" />
      <el-table-column label="当前工序" align="center" prop="currentProcessName" width="120" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getTagType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:productSerial:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:productSerial:remove']"
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

    <!-- 添加或修改单件流水号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="流水号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="请输入流水号" />
        </el-form-item>
        <el-form-item label="销售订单ID" prop="orderId">
          <el-input-number v-model="form.orderId" :min="1" placeholder="销售订单ID" />
        </el-form-item>
        <el-form-item label="工票ID" prop="jobId">
          <el-input-number v-model="form.jobId" :min="1" placeholder="工票ID" />
        </el-form-item>
        <el-form-item label="生产计划ID" prop="producePlanId">
          <el-input-number v-model="form.producePlanId" :min="1" placeholder="生产计划ID" />
        </el-form-item>
        <el-form-item label="颜色编码" prop="colorCode">
          <el-input v-model="form.colorCode" placeholder="请输入颜色编码" />
        </el-form-item>
        <el-form-item label="尺码编码" prop="sizeCode">
          <el-input v-model="form.sizeCode" placeholder="请输入尺码编码" />
        </el-form-item>
        <el-form-item label="当前工序名称" prop="currentProcessName">
          <el-input v-model="form.currentProcessName" placeholder="请输入当前工序名称" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">在制</el-radio>
            <el-radio label="1">已完工</el-radio>
            <el-radio label="2">已入库</el-radio>
            <el-radio label="3">已出货</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 扫码查询对话框 -->
    <el-dialog title="扫码查询" :visible.sync="scanOpen" width="40%" append-to-body>
      <el-form label-width="80px">
        <el-form-item label="流水号">
          <el-input
            v-model="scanSerialNo"
            placeholder="请扫描或输入流水号"
            clearable
            @keyup.enter.native="doScan"
          />
        </el-form-item>
      </el-form>
      <div v-if="scanResult" class="scan-result">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="流水号">{{ scanResult.serialNo }}</el-descriptions-item>
          <el-descriptions-item label="订单ID">{{ scanResult.orderId }}</el-descriptions-item>
          <el-descriptions-item label="工票ID">{{ scanResult.jobId }}</el-descriptions-item>
          <el-descriptions-item label="颜色">{{ scanResult.colorCode }}</el-descriptions-item>
          <el-descriptions-item label="尺码">{{ scanResult.sizeCode }}</el-descriptions-item>
          <el-descriptions-item label="当前工序">{{ scanResult.currentProcessName }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getTagType(scanResult.status)">{{ getStatusText(scanResult.status) }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doScan">查询</el-button>
        <el-button @click="scanOpen = false; scanResult = null">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProductSerial, getProductSerial, addProductSerial, updateProductSerial, delProductSerial, scanProductSerial } from "@/api/erp/productSerial";

export default {
  name: "ProductSerial",
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
      // 单件流水号表格数据
      productSerialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 是否显示扫码对话框
      scanOpen: false,
      // 扫码流水号
      scanSerialNo: "",
      // 扫码结果
      scanResult: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        orderId: null,
        jobId: null,
        producePlanId: null,
        colorCode: null,
        sizeCode: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serialNo: [
          { required: true, message: "流水号不能为空", trigger: "blur" }
        ],
        orderId: [
          { required: true, message: "销售订单ID不能为空", trigger: "blur" }
        ],
        jobId: [
          { required: true, message: "工票ID不能为空", trigger: "blur" }
        ],
        producePlanId: [
          { required: true, message: "生产计划ID不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getTagType(status) {
      const tagMap = {
        '0': 'warning',
        '1': 'primary',
        '2': 'success',
        '3': 'info'
      };
      return tagMap[status] || 'info';
    },
    getStatusText(status) {
      const textMap = {
        '0': '在制',
        '1': '已完工',
        '2': '已入库',
        '3': '已出货'
      };
      return textMap[status] || '-';
    },
    /** 查询单件流水号列表 */
    getList() {
      this.loading = true;
      listProductSerial(this.queryParams).then(response => {
        this.productSerialList = response.rows;
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
        serialNo: null,
        orderId: null,
        jobId: null,
        producePlanId: null,
        colorCode: null,
        sizeCode: null,
        currentProcessId: null,
        currentProcessName: null,
        status: '0',
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
      this.title = "添加单件流水号";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getProductSerial(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改单件流水号";
      });
    },
    /** 打开扫码对话框 */
    openScan() {
      this.scanSerialNo = "";
      this.scanResult = null;
      this.scanOpen = true;
    },
    /** 执行扫码查询 */
    doScan() {
      if (!this.scanSerialNo) {
        this.$modal.msgWarning("请输入流水号");
        return;
      }
      scanProductSerial(this.scanSerialNo).then(response => {
        this.scanResult = response.data || null;
        if (!this.scanResult) {
          this.$modal.msgWarning("未查询到对应产品序列号");
        }
      }).catch(() => {
        this.scanResult = null;
        this.$modal.msgError("扫码查询失败，请检查后重试");
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateProductSerial(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addProductSerial(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除单件流水号编号为"' + ids + '"的数据项？').then(function() {
        return delProductSerial(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/productSerial/export', {
        ...this.queryParams
      }, `productSerial_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
