<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="订单号"
          clearable
          @keyup.enter.native="handleQuery"
          style="margin-bottom: 10px"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleQuery" />
        </el-input>
      </el-col>
      <el-col :span="8">
        <el-input
          v-model="queryParams.styleCode"
          placeholder="款号"
          clearable
          @keyup.enter.native="handleQuery"
          style="margin-bottom: 10px"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleQuery" />
        </el-input>
      </el-col>
      <el-col :span="8">
        <el-select
          v-model="queryParams.status"
          placeholder="订单状态"
          clearable
          style="width: 100%; margin-bottom: 10px"
        >
          <el-option label="待处理" value="0" />
          <el-option label="已确认" value="1" />
          <el-option label="已生产" value="2" />
          <el-option label="已发货" value="3" />
          <el-option label="已完成" value="4" />
        </el-select>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-bottom: 10px">
      <el-col :span="6">
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="orderList">
      <el-table-column label="订单号" prop="orderNo" width="160" />
      <el-table-column label="工厂ID" prop="factoryId" width="100" />
      <el-table-column label="款号" prop="styleCode" width="140" />
      <el-table-column label="款式名称" prop="styleName" width="180" />
      <el-table-column label="数量" prop="qty" width="100" />
      <el-table-column label="FOB价格" prop="fobPrice" width="120" />
      <el-table-column label="CIF价格" prop="cifPrice" width="120" />
      <el-table-column label="CNF价格" prop="cnfPrice" width="120" />
      <el-table-column label="EXW价格" prop="exwPrice" width="120" />
      <el-table-column label="交期(天)" prop="dueDays" width="100" />
      <el-table-column label="状态" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)" size="small">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="80%">
      <el-form ref="orderForm" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="工厂ID" prop="factoryId">
          <el-input-number v-model="form.factoryId" :min="1" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="款号" prop="styleCode">
          <el-input v-model="form.styleCode" placeholder="请输入款号" />
        </el-form-item>
        <el-form-item label="款式名称" prop="styleName">
          <el-input v-model="form.styleName" placeholder="请输入款式名称" />
        </el-form-item>
        <el-form-item label="数量" prop="qty">
          <el-input-number v-model="form.qty" :min="1" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="FOB价格" prop="fobPrice">
          <el-input-number v-model="form.fobPrice" :min="0" :step="0.01" style="width: 100%" />
        </el-form-item>
        <el-form-item label="CIF价格" prop="cifPrice">
          <el-input-number v-model="form.cifPrice" :min="0" :step="0.01" style="width: 100%" />
        </el-form-item>
        <el-form-item label="CNF价格" prop="cnfPrice">
          <el-input-number v-model="form.cnfPrice" :min="0" :step="0.01" style="width: 100%" />
        </el-form-item>
        <el-form-item label="EXW价格" prop="exwPrice">
          <el-input-number v-model="form.exwPrice" :min="0" :step="0.01" style="width: 100%" />
        </el-form-item>
        <el-form-item label="交期(天)" prop="dueDays">
          <el-input-number v-model="form.dueDays" :min="1" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择订单状态" style="width: 100%">
            <el-option label="待处理" value="0" />
            <el-option label="已确认" value="1" />
            <el-option label="已生产" value="2" />
            <el-option label="已发货" value="3" />
            <el-option label="已完成" value="4" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList, getById, add, update, remove } from "@/api/demo/order";

function createEmptyForm() {
  return {
    id: undefined,
    factoryId: 1,
    orderNo: "",
    styleCode: "",
    styleName: "",
    qty: 1,
    fobPrice: 0,
    cifPrice: 0,
    cnfPrice: 0,
    exwPrice: 0,
    dueDays: 1,
    status: "0"
  };
}

export default {
  name: "OrderManage",
  data() {
    return {
      loading: false,
      orderList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: "",
        styleCode: "",
        status: undefined
      },
      dialogVisible: false,
      dialogTitle: "",
      form: createEmptyForm(),
      rules: {
        factoryId: [{ required: true, message: "请输入工厂ID", trigger: "blur" }],
        orderNo: [{ required: true, message: "请输入订单号", trigger: "blur" }],
        styleCode: [{ required: true, message: "请输入款号", trigger: "blur" }],
        styleName: [{ required: true, message: "请输入款式名称", trigger: "blur" }],
        qty: [{ required: true, message: "请输入数量", trigger: "blur" }],
        dueDays: [{ required: true, message: "请输入交期", trigger: "blur" }],
        status: [{ required: true, message: "请选择订单状态", trigger: "change" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      getList(this.queryParams)
        .then(response => {
          this.orderList = response.rows;
          this.total = response.total;
        })
        .finally(() => {
          this.loading = false;
        });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleReset() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        orderNo: "",
        styleCode: "",
        status: undefined
      };
      this.getList();
    },
    handleView(row) {
      this.dialogTitle = "查看订单";
      this.dialogVisible = true;
      getById(row.id).then(response => {
        this.form = Object.assign(createEmptyForm(), response);
      });
    },
    handleEdit(row) {
      this.dialogTitle = "编辑订单";
      this.dialogVisible = true;
      getById(row.id).then(response => {
        this.form = Object.assign(createEmptyForm(), response);
      });
    },
    handleDelete(row) {
      this.$confirm("确定要删除该订单吗？", "提示", { type: "warning" })
        .then(() => remove(row.id))
        .then(() => {
          this.$message.success("删除成功");
          this.getList();
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    handleSubmit() {
      this.$refs.orderForm.validate(valid => {
        if (!valid) {
          return;
        }
        const request = this.form.id ? update(this.form) : add(this.form);
        request.then(() => {
          this.$message.success(this.form.id ? "更新成功" : "新增成功");
          this.dialogVisible = false;
          this.getList();
        });
      });
    },
    getStatusText(status) {
      const statusMap = {
        "0": "待处理",
        "1": "已确认",
        "2": "已生产",
        "3": "已发货",
        "4": "已完成"
      };
      return statusMap[status] || "未知";
    },
    getStatusType(status) {
      const statusMap = {
        "0": "",
        "1": "success",
        "2": "info",
        "3": "warning",
        "4": "danger"
      };
      return statusMap[status] || "";
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
