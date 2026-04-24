<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="业务类型" prop="bizType">
        <el-input
          v-model="queryParams.bizType"
          placeholder="请输入业务类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常编码" prop="abnormalCode">
        <el-input
          v-model="queryParams.abnormalCode"
          placeholder="请输入异常编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常标题" prop="abnormalTitle">
        <el-input
          v-model="queryParams.abnormalTitle"
          placeholder="请输入异常标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常等级" prop="abnormalLevel">
        <el-select v-model="queryParams.abnormalLevel" placeholder="请选择" clearable>
          <el-option label="低" :value="1" />
          <el-option label="中" :value="2" />
          <el-option label="高" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
          <el-option label="待处理" value="0" />
          <el-option label="处理中" value="1" />
          <el-option label="已处理" value="2" />
          <el-option label="已关闭" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否锁定" prop="lockBiz">
        <el-select v-model="queryParams.lockBiz" placeholder="请选择" clearable>
          <el-option label="不锁定" value="0" />
          <el-option label="锁定" value="1" />
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
          v-hasPermi="['erp:bizabnormal:add']"
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
          v-hasPermi="['erp:bizabnormal:edit']"
        >处理</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:bizabnormal:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:bizabnormal:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bizabnormalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="业务类型" align="center" prop="bizType" width="120" />
      <el-table-column label="业务ID" align="center" prop="bizId" width="100" />
      <el-table-column label="异常编码" align="center" prop="abnormalCode" width="120" />
      <el-table-column label="异常标题" align="center" prop="abnormalTitle" width="180" />
      <el-table-column label="等级" align="center" prop="abnormalLevel" width="60">
        <template slot-scope="scope">
          <el-tag :type="scope.row.abnormalLevel === 3 ? 'danger' : scope.row.abnormalLevel === 2 ? 'warning' : 'info'">
            {{ scope.row.abnormalLevel === 1 ? '低' : scope.row.abnormalLevel === 2 ? '中' : '高' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'danger' : scope.row.status === '1' ? 'warning' : scope.row.status === '2' ? 'success' : 'info'">
            {{ scope.row.status === '0' ? '待处理' : scope.row.status === '1' ? '处理中' : scope.row.status === '2' ? '已处理' : '已关闭' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="锁定" align="center" prop="lockBiz" width="60">
        <template slot-scope="scope">
          <el-tag :type="scope.row.lockBiz === '1' ? 'danger' : 'info'">
            {{ scope.row.lockBiz === '1' ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handleByName" width="100" />
      <el-table-column label="处理时间" align="center" prop="handleTime" width="160" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:bizabnormal:edit']"
          >处理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:bizabnormal:remove']"
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

    <!-- 添加或修改业务异常对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="业务类型" prop="bizType">
          <el-input v-model="form.bizType" placeholder="请输入业务类型" />
        </el-form-item>
        <el-form-item label="业务ID" prop="bizId">
          <el-input-number v-model="form.bizId" :min="1" placeholder="业务ID" />
        </el-form-item>
        <el-form-item label="异常编码" prop="abnormalCode">
          <el-input v-model="form.abnormalCode" placeholder="请输入异常编码" />
        </el-form-item>
        <el-form-item label="异常标题" prop="abnormalTitle">
          <el-input v-model="form.abnormalTitle" placeholder="请输入异常标题" />
        </el-form-item>
        <el-form-item label="异常描述" prop="abnormalDesc">
          <el-input v-model="form.abnormalDesc" type="textarea" placeholder="请输入异常详细描述" />
        </el-form-item>
        <el-form-item label="异常等级" prop="abnormalLevel">
          <el-select v-model="form.abnormalLevel" placeholder="请选择异常等级">
            <el-option label="低" :value="1" />
            <el-option label="中" :value="2" />
            <el-option label="高" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否锁定业务" prop="lockBiz">
          <el-select v-model="form.lockBiz" placeholder="请选择">
            <el-option label="不锁定" value="0" />
            <el-option label="锁定" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理结果" prop="handleResult" v-if="form.status !== '0'">
          <el-input v-model="form.handleResult" type="textarea" placeholder="请输入处理结果描述" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
import { listBizabnormal, getBizabnormal, addBizabnormal, updateBizabnormal, delBizabnormal } from "@/api/erp/bizabnormal";

export default {
  name: "BizAbnormalPool",
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
      // 业务异常池表格数据
      bizabnormalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bizType: null,
        abnormalCode: null,
        abnormalTitle: null,
        abnormalLevel: null,
        status: null,
        lockBiz: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bizType: [
          { required: true, message: "业务类型不能为空", trigger: "blur" }
        ],
        bizId: [
          { required: true, message: "业务ID不能为空", trigger: "blur" }
        ],
        abnormalTitle: [
          { required: true, message: "异常标题不能为空", trigger: "blur" }
        ],
        abnormalLevel: [
          { required: true, message: "异常等级不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询业务异常池列表 */
    getList() {
      this.loading = true;
      listBizabnormal(this.queryParams).then(response => {
        this.bizabnormalList = response.rows;
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
        bizType: null,
        bizId: null,
        abnormalCode: null,
        abnormalTitle: null,
        abnormalDesc: null,
        abnormalLevel: 1,
        status: "0",
        handleById: null,
        handleByName: null,
        handleTime: null,
        handleResult: null,
        lockBiz: "0",
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
      this.title = "新增业务异常";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getBizabnormal(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "处理业务异常";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.id != null) {
            updateBizabnormal(this.form).then(response => {
              this.$modal.msgSuccess("处理成功");
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addBizabnormal(this.form).then(response => {
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
        return delBizabnormal(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/bizabnormal/export', {
        ...this.queryParams
      }, `bizabnormal_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
