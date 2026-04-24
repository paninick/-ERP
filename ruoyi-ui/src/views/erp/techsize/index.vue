<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('techSize.techId')" prop="techId">
        <el-input
          v-model="queryParams.techId"
          :placeholder="$t('validation.enter', [$t('techSize.techId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.noticeId')" prop="noticeId">
        <el-input
          v-model="queryParams.noticeId"
          :placeholder="$t('validation.enter', [$t('techSize.noticeId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyName')" prop="propertyName">
        <el-input
          v-model="queryParams.propertyName"
          :placeholder="$t('validation.enter', [$t('techSize.propertyName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueA')" prop="propertyValueA">
        <el-input
          v-model="queryParams.propertyValueA"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueA')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueB')" prop="propertyValueB">
        <el-input
          v-model="queryParams.propertyValueB"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueB')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueC')" prop="propertyValueC">
        <el-input
          v-model="queryParams.propertyValueC"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueC')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueD')" prop="propertyValueD">
        <el-input
          v-model="queryParams.propertyValueD"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueD')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueE')" prop="propertyValueE">
        <el-input
          v-model="queryParams.propertyValueE"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueE')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueF')" prop="propertyValueF">
        <el-input
          v-model="queryParams.propertyValueF"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueF')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueG')" prop="propertyValueG">
        <el-input
          v-model="queryParams.propertyValueG"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueG')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueH')" prop="propertyValueH">
        <el-input
          v-model="queryParams.propertyValueH"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueH')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueI')" prop="propertyValueI">
        <el-input
          v-model="queryParams.propertyValueI"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueI')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueJ')" prop="propertyValueJ">
        <el-input
          v-model="queryParams.propertyValueJ"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueJ')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyValueK')" prop="propertyValueK">
        <el-input
          v-model="queryParams.propertyValueK"
          :placeholder="$t('validation.enter', [$t('techSize.propertyValueK')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('techSize.propertyOrder')" prop="propertyOrder">
        <el-input
          v-model="queryParams.propertyOrder"
          :placeholder="$t('validation.enter', [$t('techSize.propertyOrder')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
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
        >{{ $t('btn.add') }}</el-button>
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
        >{{ $t('btn.edit') }}</el-button>
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
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:techsize:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="techsizeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column :label="$t('techSize.techId')" align="center" prop="techId" />
      <el-table-column :label="$t('techSize.noticeId')" align="center" prop="noticeId" />
      <el-table-column :label="$t('techSize.propertyName')" align="center" prop="propertyName" />
      <el-table-column :label="$t('techSize.propertyValueA')" align="center" prop="propertyValueA" />
      <el-table-column :label="$t('techSize.propertyValueB')" align="center" prop="propertyValueB" />
      <el-table-column :label="$t('techSize.propertyValueC')" align="center" prop="propertyValueC" />
      <el-table-column :label="$t('techSize.propertyValueD')" align="center" prop="propertyValueD" />
      <el-table-column :label="$t('techSize.propertyValueE')" align="center" prop="propertyValueE" />
      <el-table-column :label="$t('techSize.propertyValueF')" align="center" prop="propertyValueF" />
      <el-table-column :label="$t('techSize.propertyValueG')" align="center" prop="propertyValueG" />
      <el-table-column :label="$t('techSize.propertyValueH')" align="center" prop="propertyValueH" />
      <el-table-column :label="$t('techSize.propertyValueI')" align="center" prop="propertyValueI" />
      <el-table-column :label="$t('techSize.propertyValueJ')" align="center" prop="propertyValueJ" />
      <el-table-column :label="$t('techSize.propertyValueK')" align="center" prop="propertyValueK" />
      <el-table-column :label="$t('techSize.propertyOrder')" align="center" prop="propertyOrder" />
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:techsize:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:techsize:remove']"
          >{{ $t('btn.delete') }}</el-button>
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
        <el-form-item :label="$t('techSize.techId')" prop="techId">
          <el-input v-model="form.techId" :placeholder="$t('validation.enter', [$t('techSize.techId')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.noticeId')" prop="noticeId">
          <el-input v-model="form.noticeId" :placeholder="$t('validation.enter', [$t('techSize.noticeId')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyName')" prop="propertyName">
          <el-input v-model="form.propertyName" :placeholder="$t('validation.enter', [$t('techSize.propertyName')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueA')" prop="propertyValueA">
          <el-input v-model="form.propertyValueA" :placeholder="$t('validation.enter', [$t('techSize.propertyValueA')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueB')" prop="propertyValueB">
          <el-input v-model="form.propertyValueB" :placeholder="$t('validation.enter', [$t('techSize.propertyValueB')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueC')" prop="propertyValueC">
          <el-input v-model="form.propertyValueC" :placeholder="$t('validation.enter', [$t('techSize.propertyValueC')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueD')" prop="propertyValueD">
          <el-input v-model="form.propertyValueD" :placeholder="$t('validation.enter', [$t('techSize.propertyValueD')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueE')" prop="propertyValueE">
          <el-input v-model="form.propertyValueE" :placeholder="$t('validation.enter', [$t('techSize.propertyValueE')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueF')" prop="propertyValueF">
          <el-input v-model="form.propertyValueF" :placeholder="$t('validation.enter', [$t('techSize.propertyValueF')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueG')" prop="propertyValueG">
          <el-input v-model="form.propertyValueG" :placeholder="$t('validation.enter', [$t('techSize.propertyValueG')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueH')" prop="propertyValueH">
          <el-input v-model="form.propertyValueH" :placeholder="$t('validation.enter', [$t('techSize.propertyValueH')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueI')" prop="propertyValueI">
          <el-input v-model="form.propertyValueI" :placeholder="$t('validation.enter', [$t('techSize.propertyValueI')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueJ')" prop="propertyValueJ">
          <el-input v-model="form.propertyValueJ" :placeholder="$t('validation.enter', [$t('techSize.propertyValueJ')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyValueK')" prop="propertyValueK">
          <el-input v-model="form.propertyValueK" :placeholder="$t('validation.enter', [$t('techSize.propertyValueK')])" />
        </el-form-item>
        <el-form-item :label="$t('techSize.propertyOrder')" prop="propertyOrder">
          <el-input v-model="form.propertyOrder" :placeholder="$t('validation.enter', [$t('techSize.propertyOrder')])" />
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('validation.enter', [$t('system.remark')])" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
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
    }
  },
  computed: {
    rules() {
      return {
        techId: [
          { required: true, message: this.$t('techSize.techIdRequired'), trigger: "blur" }
        ],
        noticeId: [
          { required: true, message: this.$t('techSize.noticeIdRequired'), trigger: "blur" }
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
      this.title = this.$t('techSize.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTechsize(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('techSize.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateTechsize(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addTechsize(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.addSuccess'))
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
      this.$modal.confirm(this.$t('techSize.deleteConfirm', [ids])).then(function() {
        return delTechsize(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
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
