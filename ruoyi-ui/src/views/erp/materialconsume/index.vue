<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="生产计划ID" prop="producePlanId">
        <el-input-number
          v-model="queryParams.producePlanId"
          placeholder="生产计划ID"
          :min="1"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否超限额" prop="isOverLimit">
        <el-select v-model="queryParams.isOverLimit" placeholder="请选择" clearable>
          <el-option label="否" value="0" />
          <el-option label="是" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="approvalStatus">
        <el-select v-model="queryParams.approvalStatus" placeholder="请选择" clearable>
          <el-option label="无需审批" value="0" />
          <el-option label="待审批" value="1" />
          <el-option label="已批准" value="2" />
          <el-option label="已拒绝" value="3" />
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
          v-hasPermi="['erp:materialconsume:add']"
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
          v-hasPermi="['erp:materialconsume:edit']"
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
          v-hasPermi="['erp:materialconsume:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:materialconsume:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialconsumeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生产计划ID" align="center" prop="producePlanId" width="100" />
      <el-table-column label="工序名称" align="center" prop="processName" width="120" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="120" />
      <el-table-column label="物料名称" align="center" prop="materialName" width="140" />
      <el-table-column label="BOM用量" align="center" prop="bomQty" width="100" />
      <el-table-column label="实际领用" align="center" prop="actualQty" width="100" />
      <el-table-column label="标准损耗率%" align="center" prop="standardLossRate" width="100" />
      <el-table-column label="限额" align="center" prop="limitLossQty" width="80" />
      <el-table-column label="实际损耗" align="center" prop="actualLossQty" width="80" />
      <el-table-column label="超限额" align="center" prop="isOverLimit" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isOverLimit === '1' ? 'danger' : 'info'">
            {{ scope.row.isOverLimit === '1' ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="approvalStatus">
        <template slot-scope="scope">
          <el-tag :type="scope.row.approvalStatus === '2' ? 'success' : scope.row.approvalStatus === '1' ? 'warning' : scope.row.approvalStatus === '3' ? 'danger' : 'info'">
            {{ scope.row.approvalStatus === '0' ? '无需' : scope.row.approvalStatus === '1' ? '待审' : scope.row.approvalStatus === '2' ? '批准' : '拒绝' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:materialconsume:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:materialconsume:remove']"
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

    <!-- 添加或修改物料消耗对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="生产计划ID" prop="producePlanId">
          <el-input-number v-model="form.producePlanId" :min="1" placeholder="生产计划ID" />
        </el-form-item>
        <el-form-item label="订单ID" prop="orderId">
          <el-input-number v-model="form.orderId" :min="1" placeholder="订单ID" />
        </el-form-item>
        <el-form-item label="工序ID" prop="processId">
          <el-input-number v-model="form.processId" :min="1" placeholder="工序ID" />
        </el-form-item>
        <el-form-item label="工序名称" prop="processName">
          <el-input v-model="form.processName" placeholder="请输入工序名称" />
        </el-form-item>
        <el-form-item label="物料ID" prop="materialId">
          <el-input-number v-model="form.materialId" :min="1" placeholder="物料ID" />
        </el-form-item>
        <el-form-item label="物料编码" prop="materialCode">
          <el-input v-model="form.materialCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="物料名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="BOM理论用量" prop="bomQty">
          <el-input-number v-model="form.bomQty" :precision="3" :min="0" placeholder="BOM理论用量" />
        </el-form-item>
        <el-form-item label="实际领用数量" prop="actualQty">
          <el-input-number v-model="form.actualQty" :precision="3" :min="0" placeholder="实际领用数量" />
        </el-form-item>
        <el-form-item label="标准损耗率%" prop="standardLossRate">
          <el-input-number v-model="form.standardLossRate" :precision="2" :min="0" max="100" placeholder="标准损耗率%" />
        </el-form-item>
        <el-form-item label="超限额原因" v-if="form.isOverLimit === '1'" prop="overLimitReason">
          <el-input v-model="form.overLimitReason" type="textarea" placeholder="请填写超限额原因" />
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
  </div>
</template>

<script>
import { listMaterialconsume, getMaterialconsume, addMaterialconsume, updateMaterialconsume, delMaterialconsume } from "@/api/erp/materialconsume";

export default {
  name: "ProduceMaterialConsume",
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
      // 物料消耗表格数据
      materialconsumeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        producePlanId: null,
        materialCode: null,
        materialName: null,
        isOverLimit: null,
        approvalStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        producePlanId: [
          { required: true, message: "生产计划ID不能为空", trigger: "blur" }
        ],
        materialId: [
          { required: true, message: "物料ID不能为空", trigger: "blur" }
        ],
        bomQty: [
          { required: true, message: "BOM理论用量不能为空", trigger: "blur" }
        ],
        actualQty: [
          { required: true, message: "实际领用数量不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询物料消耗列表 */
    getList() {
      this.loading = true;
      listMaterialconsume(this.queryParams).then(response => {
        this.materialconsumeList = response.rows;
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
        producePlanId: null,
        orderId: null,
        processId: null,
        processName: null,
        materialId: null,
        materialCode: null,
        materialName: null,
        bomQty: null,
        actualQty: null,
        standardLossRate: null,
        limitLossQty: null,
        actualLossQty: null,
        isOverLimit: "0",
        overLimitReason: null,
        approvalStatus: null,
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
      this.title = "添加物料消耗记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getMaterialconsume(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物料消耗记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.id != null) {
            updateMaterialconsume(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addMaterialconsume(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return delMaterialconsume(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/materialconsume/export', {
        ...this.queryParams
      }, `materialconsume_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
