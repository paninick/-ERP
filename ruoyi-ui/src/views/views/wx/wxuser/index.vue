<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="微信union_id" prop="unionId">
        <el-input
          v-model="queryParams.unionId"
          placeholder="请输入微信union_id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微信小程序openid" prop="minaOpenid">
        <el-input
          v-model="queryParams.minaOpenid"
          placeholder="请输入微信小程序openid"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公众号openid" prop="mpOpenid">
        <el-input
          v-model="queryParams.mpOpenid"
          placeholder="请输入公众号openid"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="1已关注，0未关注" prop="isSubscribe">
        <el-input
          v-model="queryParams.isSubscribe"
          placeholder="请输入1已关注，0未关注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
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
          v-hasPermi="['wx:wxuser:add']"
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
          v-hasPermi="['wx:wxuser:edit']"
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
          v-hasPermi="['wx:wxuser:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wx:wxuser:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxuserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="微信union_id" align="center" prop="unionId" />
      <el-table-column label="微信小程序openid" align="center" prop="minaOpenid" />
      <el-table-column label="公众号openid" align="center" prop="mpOpenid" />
      <el-table-column label="头像" align="center" prop="headImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.headImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="昵称" align="center" prop="nickname" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="手机" align="center" prop="mobile" />
      <el-table-column label="1已关注，0未关注" align="center" prop="isSubscribe" />
      <el-table-column label="关注时间" align="center" prop="subscribeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.subscribeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wx:wxuser:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wx:wxuser:remove']"
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

    <!-- 添加或修改微信用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="微信union_id" prop="unionId">
          <el-input v-model="form.unionId" placeholder="请输入微信union_id" />
        </el-form-item>
        <el-form-item label="微信小程序openid" prop="minaOpenid">
          <el-input v-model="form.minaOpenid" placeholder="请输入微信小程序openid" />
        </el-form-item>
        <el-form-item label="公众号openid" prop="mpOpenid">
          <el-input v-model="form.mpOpenid" placeholder="请输入公众号openid" />
        </el-form-item>
        <el-form-item label="头像" prop="headImage">
          <image-upload v-model="form.headImage"/>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入手机" />
        </el-form-item>
        <el-form-item label="1已关注，0未关注" prop="isSubscribe">
          <el-input v-model="form.isSubscribe" placeholder="请输入1已关注，0未关注" />
        </el-form-item>
        <el-form-item label="关注时间" prop="subscribeTime">
          <el-date-picker clearable
            v-model="form.subscribeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择关注时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listWxuser, getWxuser, delWxuser, addWxuser, updateWxuser } from "@/api/wx/wxuser"

export default {
  name: "Wxuser",
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
      // 微信用户表格数据
      wxuserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unionId: null,
        minaOpenid: null,
        mpOpenid: null,
        headImage: null,
        nickname: null,
        mobile: null,
        isSubscribe: null,
        name: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nickname: [
          { required: true, message: "昵称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询微信用户列表 */
    getList() {
      this.loading = true
      listWxuser(this.queryParams).then(response => {
        this.wxuserList = response.rows
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
        unionId: null,
        minaOpenid: null,
        mpOpenid: null,
        headImage: null,
        nickname: null,
        gender: null,
        mobile: null,
        isSubscribe: null,
        subscribeTime: null,
        name: null,
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
      this.title = "添加微信用户"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getWxuser(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改微信用户"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWxuser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWxuser(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除微信用户编号为"' + ids + '"的数据项？').then(function() {
        return delWxuser(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wx/wxuser/export', {
        ...this.queryParams
      }, `wxuser_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
