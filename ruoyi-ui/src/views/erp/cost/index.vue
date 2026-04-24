<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('cost.noticeId')" prop="noticeId">
        <el-input
          v-model="queryParams.noticeId"
          :placeholder="$t('validation.enter', [$t('cost.noticeId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('cost.techId')" prop="techId">
        <el-input
          v-model="queryParams.techId"
          :placeholder="$t('validation.enter', [$t('cost.techId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('cost.materialId')" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          :placeholder="$t('validation.enter', [$t('cost.materialId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('cost.color')" prop="color">
        <el-input
          v-model="queryParams.color"
          :placeholder="$t('validation.enter', [$t('cost.color')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('cost.unitConsumption')" prop="unitConsumption">
        <el-input
          v-model="queryParams.unitConsumption"
          :placeholder="$t('validation.enter', [$t('cost.unitConsumption')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('cost.lossType')" prop="lossType">
        <el-select v-model="queryParams.lossType" :placeholder="$t('validation.select', [$t('cost.lossType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_loss_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('cost.wastage')" prop="wastage">
        <el-input
          v-model="queryParams.wastage"
          :placeholder="$t('validation.enter', [$t('cost.wastage')])"
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
          v-hasPermi="['erp:cost:add']"
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
          v-hasPermi="['erp:cost:edit']"
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
          v-hasPermi="['erp:cost:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:cost:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="costList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column :label="$t('cost.noticeId')" align="center" prop="noticeId" />
      <el-table-column :label="$t('cost.techId')" align="center" prop="techId" />
      <el-table-column :label="$t('cost.materialType')" align="center" prop="materialType" />
      <el-table-column :label="$t('cost.materialId')" align="center" prop="materialId" />
      <el-table-column :label="$t('cost.color')" align="center" prop="color" />
      <el-table-column :label="$t('cost.unitConsumption')" align="center" prop="unitConsumption" />
      <el-table-column :label="$t('cost.lossType')" align="center" prop="lossType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_loss_type" :value="scope.row.lossType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('cost.wastage')" align="center" prop="wastage" />
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:cost:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:cost:remove']"
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

    <!-- 添加或修改工艺书单耗对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('cost.noticeId')" prop="noticeId">
          <el-input v-model="form.noticeId" :placeholder="$t('validation.enter', [$t('cost.noticeId')])" />
        </el-form-item>
        <el-form-item :label="$t('cost.techId')" prop="techId">
          <el-input v-model="form.techId" :placeholder="$t('validation.enter', [$t('cost.techId')])" />
        </el-form-item>
        <el-form-item :label="$t('cost.materialId')" prop="materialId">
          <el-input v-model="form.materialId" :placeholder="$t('validation.enter', [$t('cost.materialId')])" />
        </el-form-item>
        <el-form-item :label="$t('cost.color')" prop="color">
          <el-input v-model="form.color" :placeholder="$t('validation.enter', [$t('cost.color')])" />
        </el-form-item>
        <el-form-item :label="$t('cost.unitConsumption')" prop="unitConsumption">
          <el-input v-model="form.unitConsumption" :placeholder="$t('validation.enter', [$t('cost.unitConsumption')])" />
        </el-form-item>
        <el-form-item :label="$t('cost.lossType')" prop="lossType">
          <el-select v-model="form.lossType" :placeholder="$t('validation.select', [$t('cost.lossType')])">
            <el-option
              v-for="dict in dict.type.erp_loss_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('cost.wastage')" prop="wastage">
          <el-input v-model="form.wastage" :placeholder="$t('validation.enter', [$t('cost.wastage')])" />
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listCost, getCost, delCost, addCost, updateCost } from "@/api/erp/cost"

export default {
  name: "Cost",
  dicts: ['erp_loss_type'],
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
      // 工艺书单耗表格数据
      costList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeId: null,
        techId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
      },
      // 表单参数
      form: {},
    }
  },
  computed: {
    // 表单校验
    rules() {
      return {
        noticeId: [
          { required: true, message: () => this.$t('validation.required'), trigger: "blur" }
        ],
        materialId: [
          { required: true, message: () => this.$t('validation.required'), trigger: "blur" }
        ],
        unitConsumption: [
          { required: true, message: () => this.$t('validation.required'), trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工艺书单耗列表 */
    getList() {
      this.loading = true
      listCost(this.queryParams).then(response => {
        this.costList = response.rows
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
        noticeId: null,
        techId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
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
      this.title = this.$t('cost.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCost(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('cost.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateCost(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addCost(this.form).then(response => {
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
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(function() {
        return delCost(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/cost/export', {
        ...this.queryParams
      }, `cost_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
