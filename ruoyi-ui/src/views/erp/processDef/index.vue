<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('processDef.processCode')" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          :placeholder="$t('validation.enter', [$t('processDef.processCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('processDef.processName')" prop="processName">
        <el-input
          v-model="queryParams.processName"
          :placeholder="$t('validation.enter', [$t('processDef.processName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('processDef.processType')" prop="processType">
        <el-input
          v-model="queryParams.processType"
          :placeholder="$t('validation.enter', [$t('processDef.processType')])"
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
          v-hasPermi="['erp:processDef:add']"
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
          v-hasPermi="['erp:processDef:edit']"
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
          v-hasPermi="['erp:processDef:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:processDef:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processDefList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('processDef.processCode')" align="center" prop="processCode" />
      <el-table-column :label="$t('processDef.processName')" align="center" prop="processName" />
      <el-table-column :label="$t('processDef.processType')" align="center" prop="processType" />
      <el-table-column :label="$t('processDef.department')" align="center" prop="department" />
      <el-table-column :label="$t('processDef.defaultPrice')" align="center" prop="defaultPrice" />
      <el-table-column :label="$t('processDef.enableOutsource')" align="center" prop="enableOutsource">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.enableOutsource"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('processDef.status')" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('processDef.sortOrder')" align="center" prop="sortOrder" />
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('processDef.isSpliceProcess')" align="center" prop="isSpliceProcess" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isSpliceProcess === '1'" type="warning" size="mini">{{ $t('processDef.splice') }}</el-tag>
          <span v-else style="color:#C0C4CC">—</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:processDef:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:processDef:remove']"
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

    <!-- 添加或修改工序定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('processDef.processCode')" prop="processCode">
          <el-input v-model="form.processCode" :placeholder="$t('validation.enter', [$t('processDef.processCode')])" />
        </el-form-item>
        <el-form-item :label="$t('processDef.processName')" prop="processName">
          <el-input v-model="form.processName" :placeholder="$t('validation.enter', [$t('processDef.processName')])" />
        </el-form-item>
        <el-form-item :label="$t('processDef.processType')" prop="processType">
          <el-input v-model="form.processType" :placeholder="$t('validation.enter', [$t('processDef.processType')])" />
        </el-form-item>
        <el-form-item :label="$t('processDef.department')" prop="department">
          <el-input v-model="form.department" :placeholder="$t('validation.enter', [$t('processDef.department')])" />
        </el-form-item>
        <el-form-item :label="$t('processDef.defaultPrice')" prop="defaultPrice">
          <el-input-number v-model="form.defaultPrice" :precision="2" :min="0" :placeholder="$t('validation.enter', [$t('processDef.defaultPrice')])" />
        </el-form-item>
        <el-form-item :label="$t('processDef.enableOutsource')" prop="enableOutsource">
          <el-radio-group v-model="form.enableOutsource">
            <el-radio :label="0">{{ $t('status.no') }}</el-radio>
            <el-radio :label="1">{{ $t('status.yes') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('processDef.needQualityCheck')" prop="needQualityCheck">
          <el-radio-group v-model="form.needQualityCheck">
            <el-radio :label="0">{{ $t('status.no') }}</el-radio>
            <el-radio :label="1">{{ $t('status.yes') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('processDef.sortOrder')" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" :placeholder="$t('processDef.sortOrder')" />
        </el-form-item>
        <el-form-item :label="$t('processDef.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">{{ $t('processDef.normal') }}</el-radio>
            <el-radio label="1">{{ $t('processDef.disabled') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('processDef.enterContent')" />
        </el-form-item>
        <el-divider content-position="left">{{ $t('processDef.spliceSop') }}</el-divider>
        <el-form-item :label="$t('processDef.isSpliceProcess')" prop="isSpliceProcess">
          <el-radio-group v-model="form.isSpliceProcess">
            <el-radio label="0">{{ $t('status.no') }}</el-radio>
            <el-radio label="1">{{ $t('status.yes') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <template v-if="form.isSpliceProcess === '1'">
          <el-form-item :label="$t('processDef.shrinkageBaseline')" prop="shrinkageBaseline">
            <el-input-number v-model="form.shrinkageBaseline" :precision="2" :min="0" :max="30" :placeholder="$t('processDef.shrinkageBaselinePlaceholder')" style="width:200px" />
          </el-form-item>
          <el-form-item :label="$t('processDef.elasticityCompensation')" prop="elasticityCompensation">
            <el-input-number v-model="form.elasticityCompensation" :precision="2" :min="0" :max="50" :placeholder="$t('processDef.elasticityCompensationPlaceholder')" style="width:200px" />
          </el-form-item>
          <el-form-item :label="$t('processDef.seamWidth')" prop="seamWidth">
            <el-input-number v-model="form.seamWidth" :precision="1" :min="0" :max="30" :placeholder="$t('processDef.seamWidthPlaceholder')" style="width:200px" />
          </el-form-item>
          <el-form-item :label="$t('processDef.spliceDirection')" prop="spliceDirection">
            <el-select v-model="form.spliceDirection" :placeholder="$t('validation.select', [$t('processDef.spliceDirection')])" clearable>
              <el-option :label="$t('processDef.warp')" value="WARP" />
              <el-option :label="$t('processDef.weft')" value="WEFT" />
              <el-option :label="$t('processDef.bias')" value="BIAS" />
              <el-option :label="$t('processDef.any')" value="ANY" />
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('processDef.fabricCompatibility')" prop="fabricCompatibility">
            <el-input v-model="form.fabricCompatibility" type="textarea" :rows="2" :placeholder="$t('processDef.fabricCompatibilityPlaceholder')" />
          </el-form-item>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProcessDef, getProcessDef, delProcessDef, addProcessDef, updateProcessDef } from "@/api/erp/processDef"

export default {
  name: "ProcessDef",
  dicts: ['sys_yes_no', 'sys_normal_disable'],
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
      // 工序定义表格数据
      processDefList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processCode: null,
        processName: null,
        processType: null,
        status: null
      },
      // 表单参数
      form: {}
    }
  },
  computed: {
    rules() {
      return {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工序定义列表 */
    getList() {
      this.loading = true
      listProcessDef(this.queryParams).then(response => {
        this.processDefList = response.rows
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
        processCode: null,
        processName: null,
        processType: null,
        department: null,
        defaultPrice: null,
        enableOutsource: 0,
        needQualityCheck: 0,
        sortOrder: 0,
        status: '0',
        remark: null,
        isSpliceProcess: '0',
        shrinkageBaseline: null,
        elasticityCompensation: null,
        seamWidth: null,
        spliceDirection: null,
        fabricCompatibility: null
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
      this.title = this.$t('processDef.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcessDef(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('processDef.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateProcessDef(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addProcessDef(this.form).then(response => {
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
      this.$modal.confirm(this.$t('processDef.deleteConfirm', [ids])).then(function() {
        return delProcessDef(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/processDef/export', {
        ...this.queryParams
      }, `processDef_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
