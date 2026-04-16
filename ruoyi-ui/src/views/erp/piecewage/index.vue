<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="员工姓名" prop="employeeName">
        <el-input
          v-model="queryParams.employeeName"
          placeholder="请输入员工姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="汇总月份" prop="wageMonth">
        <el-date-picker
          v-model="queryParams.wageMonth"
          type="month"
          placeholder="选择月份"
          value-format="yyyy-MM"
          clearable
          style="width: 180px">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="待审核" value="0" />
          <el-option label="已审核" value="1" />
          <el-option label="已发放" value="2" />
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
          v-hasPermi="['erp:piecewage:add']"
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
          v-hasPermi="['erp:piecewage:edit']"
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
          v-hasPermi="['erp:piecewage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-document"
          size="mini"
          :disabled="single"
          @click="handleViewDetail"
        >查看明细</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:piecewage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="piecewageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工姓名" align="center" prop="employeeName" width="120" />
      <el-table-column label="汇总月份" align="center" prop="wageMonth" width="100" />
      <el-table-column label="总工序数" align="center" prop="totalProcessCount" width="80" />
      <el-table-column label="总产量" align="center" prop="totalOkQty" width="80" />
      <el-table-column label="总次品" align="center" prop="totalDefectQty" width="80" />
      <el-table-column label="应发工资" align="center" prop="shouldWage" width="100" />
      <el-table-column label="扣款" align="center" prop="deductWage" width="80" />
      <el-table-column label="实发工资" align="center" prop="actualWage" width="100" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '2' ? 'success' : scope.row.status === '1' ? 'primary' : 'info'">
            {{ scope.row.status === '0' ? '待审核' : scope.row.status === '1' ? '已审核' : '已发放' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核时间" align="center" prop="auditTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:piecewage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleViewDetail(scope.row)"
          >明细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:piecewage:remove']"
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

    <!-- 添加或修改计件工资汇总对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="员工ID" prop="employeeId">
          <el-input-number v-model="form.employeeId" :min="1" placeholder="员工ID" />
        </el-form-item>
        <el-form-item label="员工姓名" prop="employeeName">
          <el-input v-model="form.employeeName" placeholder="请输入员工姓名" />
        </el-form-item>
        <el-form-item label="汇总月份" prop="wageMonth">
          <el-date-picker
            v-model="form.wageMonth"
            type="month"
            placeholder="选择月份"
            value-format="yyyy-MM"
            style="width: 100%">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="总合格产量" prop="totalOkQty">
          <el-input-number v-model="form.totalOkQty" :min="0" placeholder="总合格产量" />
        </el-form-item>
        <el-form-item label="总次品产量" prop="totalDefectQty">
          <el-input-number v-model="form.totalDefectQty" :min="0" placeholder="总次品产量" />
        </el-form-item>
        <el-form-item label="应发工资" prop="shouldWage">
          <el-input-number v-model="form.shouldWage" :precision="2" :min="0" placeholder="应发工资" />
        </el-form-item>
        <el-form-item label="扣款金额" prop="deductWage">
          <el-input-number v-model="form.deductWage" :precision="2" :min="0" placeholder="扣款金额" />
        </el-form-item>
        <el-form-item label="实际工资" prop="actualWage">
          <el-input-number v-model="form.actualWage" :precision="2" :min="0" placeholder="实际工资" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="待审核" value="0" />
            <el-option label="已审核" value="1" />
            <el-option label="已发放" value="2" />
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

    <!-- 明细对话框 -->
    <el-dialog title="工资明细" :visible.sync="detailOpen" width="80%" append-to-body>
      <el-table v-loading="detailLoading" :data="detailList">
        <el-table-column label="工序名称" align="center" prop="processName" width="120" />
        <el-table-column label="工票ID" align="center" prop="jobId" width="80" />
        <el-table-column label="合格数量" align="center" prop="okQty" width="80" />
        <el-table-column label="次品数量" align="center" prop="defectQty" width="80" />
        <el-table-column label="工序单价" align="center" prop="processPrice" width="80" />
        <el-table-column label="应得工资" align="center" prop="shouldWage" width="80" />
        <el-table-column label="扣款" align="center" prop="deductWage" width="80" />
        <el-table-column label="实际工资" align="center" prop="actualWage" width="90" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPiecewage, getPiecewage, addPiecewage, updatePiecewage, delPiecewage } from "@/api/erp/piecewage";
import { listPiecewagedetailByWage } from "@/api/erp/piecewagedetail";

export default {
  name: "PieceWage",
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
      // 计件工资汇总表格数据
      piecewageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示明细弹出层
      detailOpen: false,
      // 明细加载
      detailLoading: false,
      // 明细数据
      detailList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        employeeName: null,
        wageMonth: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        employeeId: [
          { required: true, message: "员工ID不能为空", trigger: "blur" }
        ],
        employeeName: [
          { required: true, message: "员工姓名不能为空", trigger: "blur" }
        ],
        wageMonth: [
          { required: true, message: "汇总月份不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询计件工资汇总列表 */
    getList() {
      this.loading = true;
      listPiecewage(this.queryParams).then(response => {
        this.piecewageList = response.rows;
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
        employeeId: null,
        employeeName: null,
        wageMonth: null,
        totalProcessCount: 0,
        totalOkQty: 0,
        totalDefectQty: 0,
        shouldWage: null,
        deductWage: null,
        actualWage: null,
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
      this.title = "添加计件工资汇总";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getPiecewage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改计件工资汇总";
      });
    },
    /** 查看明细 */
    handleViewDetail(row) {
      const id = row.id || (this.ids.length > 0 ? this.ids[0] : null);
      if (!id) {
        this.$modal.msgWarning("请选择一条记录");
        return;
      }
      this.detailLoading = true;
      this.detailOpen = true;
      listPiecewagedetailByWage(id).then(response => {
        this.detailList = response.data;
        this.detailLoading = false;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePiecewage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPiecewage(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return delPiecewage(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/piecewage/export', {
        ...this.queryParams
      }, `piecewage_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
