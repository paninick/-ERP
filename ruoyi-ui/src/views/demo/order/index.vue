<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="订单号"
          clearable
          @keyup.enter="handleQuery"
          style="margin-bottom: 10px"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleQuery" />
        </el-input>
      </el-col>
      <el-col :span="8">
        <el-input
          v-model="queryParams.customerName"
          placeholder="客户名称"
          clearable
          @keyup.enter="handleQuery"
          style="margin-bottom: 10px"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleQuery" />
        </el-input>
      </el-col>
      <el-col :span="8">
        <el-input
          v-model="queryParams.styleNo"
          placeholder="款号"
          clearable
          @keyup.enter="handleQuery"
          style="margin-bottom: 10px"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleQuery" />
        </el-input>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="6">
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
      <el-col :span="6">
        <el-button type="primary" @click="handleQuery">
          <i class="el-icon-search"></i> 查询
        </el-button>
        <el-button @click="handleReset">
          <i class="el-icon-refresh"></i> 重置
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="orderList">
      <el-table-column label="订单号" prop="orderNo" width="150" />
      <el-table-column label="客户名称" prop="customerName" width="180" />
      <el-table-column label="款号" prop="styleNo" width="120" />
      <el-table-column label="数量" prop="qty" width="100">
        <template slot-scope="scope">
          {{ scope.row.qty }} {{ scope.row.unit }}
        </template>
      </el-table-column>
      <el-table-column label="单价" prop="price" width="100">
        <template slot-scope="scope">
          {{ scope.row.price }}
        </template>
      </el-table-column>
      <el-table-column label="总金额" prop="totalAmount" width="120">
        <template slot-scope="scope">
          {{ scope.row.totalAmount }}
        </template>
      </el-table-column>
      <el-table-column label="FOB价格" prop="fobPrice" width="120">
        <template slot-scope="scope">
          {{ scope.row.fobPrice }}
        </template>
      </el-table-column>
      <el-table-column label="CIF价格" prop="cifPrice" width="120">
        <template slot-scope="scope">
          {{ scope.row.cifPrice }}
        </template>
      </el-table-column>
      <el-table-column label="CNF价格" prop="cnfPrice" width="120">
        <template slot-scope="scope">
          {{ scope.row.cnfPrice }}
        </template>
      </el-table-column>
      <el-table-column label="EXW价格" prop="exwPrice" width="120">
        <template slot-scope="scope">
          {{ scope.row.exwPrice }}
        </template>
      </el-table-column>
      <el-table-column label="交货期（天）" prop="dueDays" width="120" />
      <el-table-column label="状态" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag
            :type="getStatusType(scope.row.status)"
            size="small"
          >
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >
            查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >
            删除
          </el-button>
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

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="80%">
      <el-form
        :model="form"
        :rules="rules"
        ref="orderForm"
        label-width="100px"
      >
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="款号" prop="styleNo">
          <el-input v-model="form.styleNo" placeholder="请输入款号" />
        </el-form-item>
        <el-form-item label="款式名称" prop="styleName">
          <el-input v-model="form.styleName" placeholder="请输入款式名称" />
        </el-form-item>
        <el-form-item label="数量" prop="qty">
          <el-input-number
            v-model="form.qty"
            :min="1"
            :step="1"
            style="width: 100%"
            placeholder="请输入数量"
          />
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input-number
            v-model="form.price"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入单价"
          />
        </el-form-item>
        <el-form-item label="FOB价格" prop="fobPrice">
          <el-input-number
            v-model="form.fobPrice"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入FOB价格"
          />
        </el-form-item>
        <el-form-item label="CIF价格" prop="cifPrice">
          <el-input-number
            v-model="form.cifPrice"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入CIF价格"
          />
        </el-form-item>
        <el-form-item label="CNF价格" prop="cnfPrice">
          <el-input-number
            v-model="form.cnfPrice"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入CNF价格"
          />
        </el-form-item>
        <el-form-item label="EXW价格" prop="exwPrice">
          <el-input-number
            v-model="form.exwPrice"
            :min="0"
            :step="0.01"
            style="width: 100%"
            placeholder="请输入EXW价格"
          />
        </el-form-item>
        <el-form-item label="交货期（天）" prop="dueDays">
          <el-input-number
            v-model="form.dueDays"
            :min="1"
            :step="1"
            style="width: 100%"
            placeholder="请输入交货期（天）"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择订单状态">
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
        customerName: "",
        styleNo: "",
        status: undefined
      },
      dialogVisible: false,
      dialogTitle: "",
      form: {},
      rules: {
        orderNo: [
          { required: true, message: "请输入订单号", trigger: "blur" }
        ],
        customerName: [
          { required: true, message: "请输入客户名称", trigger: "blur" }
        ],
        styleNo: [
          { required: true, message: "请输入款号", trigger: "blur" }
        ],
        qty: [
          { required: true, message: "请输入数量", trigger: "blur" }
        ],
        price: [
          { required: true, message: "请输入单价", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      getList(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
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
        customerName: "",
        styleNo: "",
        status: undefined
      };
      this.getList();
    },
    handleView(row) {
      this.dialogTitle = "查看订单";
      this.dialogVisible = true;
      getById(row.id).then(response => {
        this.form = response;
      });
    },
    handleEdit(row) {
      this.dialogTitle = "编辑订单";
      this.dialogVisible = true;
      getById(row.id).then(response => {
        this.form = response;
      });
    },
    handleDelete(row) {
      this.$confirm("确定要删除该订单吗？", "提示", {
        type: "warning"
      }).then(() => {
        remove(row.id).then(response => {
          this.$message.success("删除成功");
          this.getList();
        });
      }).catch(() => {
        this.$message.info("已取消删除");
      });
    },
    handleSubmit() {
      this.$refs.orderForm.validate(valid => {
        if (valid) {
          if (this.form.id) {
            update(this.form).then(response => {
              this.$message.success("更新成功");
              this.dialogVisible = false;
              this.getList();
            });
          } else {
            add(this.form).then(response => {
              this.$message.success("添加成功");
              this.dialogVisible = false;
              this.getList();
            });
          }
        }
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
