<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="工艺书id" prop="techId">
        <el-input
          v-model="queryParams.techId"
          placeholder="请输入工艺书id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打样通知d" prop="noticeId">
        <el-input
          v-model="queryParams.noticeId"
          placeholder="请输入打样通知d"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性名称" prop="propertyName">
        <el-input
          v-model="queryParams.propertyName"
          placeholder="请输入属性名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值1" prop="propertyValueA">
        <el-input
          v-model="queryParams.propertyValueA"
          placeholder="请输入属性值1"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值2" prop="propertyValueB">
        <el-input
          v-model="queryParams.propertyValueB"
          placeholder="请输入属性值2"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值3" prop="propertyValueC">
        <el-input
          v-model="queryParams.propertyValueC"
          placeholder="请输入属性值3"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值4" prop="propertyValueD">
        <el-input
          v-model="queryParams.propertyValueD"
          placeholder="请输入属性值4"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值5" prop="propertyValueE">
        <el-input
          v-model="queryParams.propertyValueE"
          placeholder="请输入属性值5"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值6" prop="propertyValueF">
        <el-input
          v-model="queryParams.propertyValueF"
          placeholder="请输入属性值6"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值7" prop="propertyValueG">
        <el-input
          v-model="queryParams.propertyValueG"
          placeholder="请输入属性值7"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值8" prop="propertyValueH">
        <el-input
          v-model="queryParams.propertyValueH"
          placeholder="请输入属性值8"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值9" prop="propertyValueI">
        <el-input
          v-model="queryParams.propertyValueI"
          placeholder="请输入属性值9"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值10" prop="propertyValueJ">
        <el-input
          v-model="queryParams.propertyValueJ"
          placeholder="请输入属性值10"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值11" prop="propertyValueK">
        <el-input
          v-model="queryParams.propertyValueK"
          placeholder="请输入属性值11"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性顺序" prop="propertyOrder">
        <el-input
          v-model="queryParams.propertyOrder"
          placeholder="请输入属性顺序"
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
          v-hasPermi="['erp:techsize:add']"
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
          v-hasPermi="['erp:techsize:edit']"
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
          v-hasPermi="['erp:techsize:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:techsize:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="techsizeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="工艺书id" align="center" prop="techId" />
      <el-table-column label="打样通知d" align="center" prop="noticeId" />
      <el-table-column label="属性名称" align="center" prop="propertyName" />
      <el-table-column label="属性值1" align="center" prop="propertyValueA" />
      <el-table-column label="属性值2" align="center" prop="propertyValueB" />
      <el-table-column label="属性值3" align="center" prop="propertyValueC" />
      <el-table-column label="属性值4" align="center" prop="propertyValueD" />
      <el-table-column label="属性值5" align="center" prop="propertyValueE" />
      <el-table-column label="属性值6" align="center" prop="propertyValueF" />
      <el-table-column label="属性值7" align="center" prop="propertyValueG" />
      <el-table-column label="属性值8" align="center" prop="propertyValueH" />
      <el-table-column label="属性值9" align="center" prop="propertyValueI" />
      <el-table-column label="属性值10" align="center" prop="propertyValueJ" />
      <el-table-column label="属性值11" align="center" prop="propertyValueK" />
      <el-table-column label="属性顺序" align="center" prop="propertyOrder" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:techsize:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:techsize:remove']"
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

    <!-- 添加或修改工艺书尺寸细节对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="工艺书id" prop="techId">
          <el-input v-model="form.techId" placeholder="请输入工艺书id" />
        </el-form-item>
        <el-form-item label="打样通知d" prop="noticeId">
          <el-input v-model="form.noticeId" placeholder="请输入打样通知d" />
        </el-form-item>
        <el-form-item label="属性名称" prop="propertyName">
          <el-input v-model="form.propertyName" placeholder="请输入属性名称" />
        </el-form-item>
        <el-form-item label="属性值1" prop="propertyValueA">
          <el-input v-model="form.propertyValueA" placeholder="请输入属性值1" />
        </el-form-item>
        <el-form-item label="属性值2" prop="propertyValueB">
          <el-input v-model="form.propertyValueB" placeholder="请输入属性值2" />
        </el-form-item>
        <el-form-item label="属性值3" prop="propertyValueC">
          <el-input v-model="form.propertyValueC" placeholder="请输入属性值3" />
        </el-form-item>
        <el-form-item label="属性值4" prop="propertyValueD">
          <el-input v-model="form.propertyValueD" placeholder="请输入属性值4" />
        </el-form-item>
        <el-form-item label="属性值5" prop="propertyValueE">
          <el-input v-model="form.propertyValueE" placeholder="请输入属性值5" />
        </el-form-item>
        <el-form-item label="属性值6" prop="propertyValueF">
          <el-input v-model="form.propertyValueF" placeholder="请输入属性值6" />
        </el-form-item>
        <el-form-item label="属性值7" prop="propertyValueG">
          <el-input v-model="form.propertyValueG" placeholder="请输入属性值7" />
        </el-form-item>
        <el-form-item label="属性值8" prop="propertyValueH">
          <el-input v-model="form.propertyValueH" placeholder="请输入属性值8" />
        </el-form-item>
        <el-form-item label="属性值9" prop="propertyValueI">
          <el-input v-model="form.propertyValueI" placeholder="请输入属性值9" />
        </el-form-item>
        <el-form-item label="属性值10" prop="propertyValueJ">
          <el-input v-model="form.propertyValueJ" placeholder="请输入属性值10" />
        </el-form-item>
        <el-form-item label="属性值11" prop="propertyValueK">
          <el-input v-model="form.propertyValueK" placeholder="请输入属性值11" />
        </el-form-item>
        <el-form-item label="属性顺序" prop="propertyOrder">
          <el-input v-model="form.propertyOrder" placeholder="请输入属性顺序" />
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
import { listTechsize, getTechsize, delTechsize, addTechsize, updateTechsize } from "@/api/erp/techsize"

export default {
  name: "Techsize",
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
      // 工艺书尺寸细节表格数据
      techsizeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        techId: null,
        noticeId: null,
        propertyName: null,
        propertyValueA: null,
        propertyValueB: null,
        propertyValueC: null,
        propertyValueD: null,
        propertyValueE: null,
        propertyValueF: null,
        propertyValueG: null,
        propertyValueH: null,
        propertyValueI: null,
        propertyValueJ: null,
        propertyValueK: null,
        propertyOrder: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        techId: [
          { required: true, message: "工艺书id不能为空", trigger: "blur" }
        ],
        noticeId: [
          { required: true, message: "打样通知d不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工艺书尺寸细节列表 */
    getList() {
      this.loading = true
      listTechsize(this.queryParams).then(response => {
        this.techsizeList = response.rows
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
        techId: null,
        noticeId: null,
        propertyName: null,
        propertyValueA: null,
        propertyValueB: null,
        propertyValueC: null,
        propertyValueD: null,
        propertyValueE: null,
        propertyValueF: null,
        propertyValueG: null,
        propertyValueH: null,
        propertyValueI: null,
        propertyValueJ: null,
        propertyValueK: null,
        propertyOrder: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.title = "添加工艺书尺寸细节"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTechsize(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改工艺书尺寸细节"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateTechsize(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addTechsize(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除工艺书尺寸细节编号为"' + ids + '"的数据项？').then(function() {
        return delTechsize(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/techsize/export', {
        ...this.queryParams
      }, `techsize_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
