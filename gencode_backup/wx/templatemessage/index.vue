<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="用户编号" prop="wxUserId">
        <el-input
          v-model="queryParams.wxUserId"
          placeholder="请输入用户编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户服务号openid" prop="openId">
        <el-input
          v-model="queryParams.openId"
          placeholder="请输入用户服务号openid"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务号消息模板id" prop="templateId">
        <el-input
          v-model="queryParams.templateId"
          placeholder="请输入服务号消息模板id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="链接地址" prop="url">
        <el-input
          v-model="queryParams.url"
          placeholder="请输入链接地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="小程序appid" prop="minaAppid">
        <el-input
          v-model="queryParams.minaAppid"
          placeholder="请输入小程序appid"
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
          v-hasPermi="['wx:templatemessage:add']"
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
          v-hasPermi="['wx:templatemessage:edit']"
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
          v-hasPermi="['wx:templatemessage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wx:templatemessage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templatemessageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="用户服务号openid" align="center" prop="openId" />
      <el-table-column label="服务号消息模板id" align="center" prop="templateId" />
      <el-table-column label="发送状态1成功，2失败" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wx:templatemessage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wx:templatemessage:remove']"
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

    <!-- 添加或修改模板消息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="用户编号" prop="wxUserId">
          <el-input v-model="form.wxUserId" placeholder="请输入用户编号" />
        </el-form-item>
        <el-form-item label="用户服务号openid" prop="openId">
          <el-input v-model="form.openId" placeholder="请输入用户服务号openid" />
        </el-form-item>
        <el-form-item label="服务号消息模板id" prop="templateId">
          <el-input v-model="form.templateId" placeholder="请输入服务号消息模板id" />
        </el-form-item>
        <el-form-item label="链接地址" prop="url">
          <el-input v-model="form.url" placeholder="请输入链接地址" />
        </el-form-item>
        <el-form-item label="小程序appid" prop="minaAppid">
          <el-input v-model="form.minaAppid" placeholder="请输入小程序appid" />
        </el-form-item>
        <el-form-item label="小程序路径" prop="pagePath">
          <el-input v-model="form.pagePath" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="模板消息参数" prop="params">
          <el-input v-model="form.params" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注信息" />
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
import { listTemplatemessage, getTemplatemessage, delTemplatemessage, addTemplatemessage, updateTemplatemessage } from "@/api/wx/templatemessage"

export default {
  name: "Templatemessage",
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
      // 模板消息表格数据
      templatemessageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wxUserId: null,
        openId: null,
        templateId: null,
        url: null,
        minaAppid: null,
        pagePath: null,
        params: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wxUserId: [
          { required: true, message: "用户编号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询模板消息列表 */
    getList() {
      this.loading = true
      listTemplatemessage(this.queryParams).then(response => {
        this.templatemessageList = response.rows
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
        wxUserId: null,
        openId: null,
        templateId: null,
        url: null,
        minaAppid: null,
        pagePath: null,
        params: null,
        status: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.title = "添加模板消息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTemplatemessage(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改模板消息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTemplatemessage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addTemplatemessage(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除模板消息编号为"' + ids + '"的数据项？').then(function() {
        return delTemplatemessage(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wx/templatemessage/export', {
        ...this.queryParams
      }, `templatemessage_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
