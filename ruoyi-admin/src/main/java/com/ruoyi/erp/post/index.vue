<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="生产单号" prop="postNo">
        <el-input v-model="queryParams.postNo" placeholder="请输入生产单号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="关联计划" prop="planNo">
        <el-input v-model="queryParams.planNo" placeholder="请输入计划编号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="生产状态" prop="postStatus">
        <el-select v-model="queryParams.postStatus" placeholder="请选择生产状态" clearable style="width: 200px">
          <el-option v-for="dict in dict.type.erp_post_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['erp:post:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:post:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:post:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['erp:post:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="生产ID" align="center" prop="postId" width="80" />
      <el-table-column label="生产单号" align="center" prop="postNo" width="160" />
      <el-table-column label="关联计划" align="center" prop="planNo" width="160" />
      <el-table-column label="生产数量" align="center" prop="quantity" width="90" />
      <el-table-column label="已完成数" align="center" prop="completedQty" width="90" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="endTime" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产状态" align="center" prop="postStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_post_status" :value="scope.row.postStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['erp:post:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['erp:post:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改后道生产对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产单号" prop="postNo">
              <el-input v-model="form.postNo" placeholder="系统自动生成" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关联计划" prop="planId">
              <el-select v-model="form.planId" placeholder="请选择生产计划" filterable>
                <el-option v-for="item in planOptions" :key="item.planId" :label="item.planNo" :value="item.planId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产数量" prop="quantity">
              <el-input-number v-model="form.quantity" :min="1" placeholder="请输入数量" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产状态" prop="postStatus">
              <el-select v-model="form.postStatus" placeholder="请选择状态">
                <el-option v-for="dict in dict.type.erp_post_status" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker v-model="form.startTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="完成时间" prop="endTime">
              <el-date-picker v-model="form.endTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
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
import { listPost, getPost, delPost, addPost, updatePost } from "@/api/erp/post"
import { listPlan } from "@/api/erp/plan"

export default {
  name: "Post",
  dicts: ['erp_post_status'],
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
      // 后道生产表格数据
      postList: [],
      // 生产计划选项
      planOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postNo: undefined,
        planNo: undefined,
        postStatus: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planId: [
          { required: true, message: "关联计划不能为空", trigger: "change" }
        ],
        quantity: [
          { required: true, message: "生产数量不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getPlanList()
  },
  methods: {
    /** 查询后道生产列表 */
    getList() {
      this.loading = true
      listPost(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 获取生产计划列表 */
    getPlanList() {
      listPlan({ pageNum: 1, pageSize: 9999 }).then(response => {
        this.planOptions = response.rows
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
        postId: undefined,
        postNo: undefined,
        planId: undefined,
        planNo: undefined,
        quantity: undefined,
        completedQty: 0,
        startTime: undefined,
        endTime: undefined,
        postStatus: "pending",
        remark: undefined
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
      this.ids = selection.map(item => item.postId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加后道生产"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const postId = row.postId || this.ids
      getPost(postId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改后道生产"
      })
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看后道生产: " + row.postNo)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.postId != undefined) {
            updatePost(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPost(this.form).then(() => {
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
      const postIds = row.postId || this.ids
      this.$modal.confirm('是否确认删除生产单号为"' + postIds + '"的数据项？').then(function() {
        return delPost(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/post/export', {
        ...this.queryParams
      }, `post_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
