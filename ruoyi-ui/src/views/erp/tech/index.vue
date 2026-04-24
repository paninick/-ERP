<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('tech.auditStatus')" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" :placeholder="$t('validation.select', [$t('tech.auditStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('tech.progressStatus')" prop="progressStatus">
        <el-select v-model="queryParams.progressStatus" :placeholder="$t('validation.select', [$t('tech.progressStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_check_progress"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('tech.customerName')" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          :placeholder="$t('validation.enter', [$t('tech.customerName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('tech.sampleTypeDisplay')" prop="sampleTypeDisplay">
        <el-select v-model="queryParams.sampleTypeDisplay" :placeholder="$t('validation.select', [$t('tech.sampleTypeDisplay')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('tech.styleType')" prop="styleType">
        <el-select v-model="queryParams.styleType" :placeholder="$t('validation.select', [$t('tech.styleType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_style"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('tech.sampleCategoryType')" prop="sampleCategoryType">
        <el-select v-model="queryParams.sampleCategoryType" :placeholder="$t('validation.select', [$t('tech.sampleCategoryType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('tech.styleCode')" prop="styleCode">
        <el-input
          v-model="queryParams.styleCode"
          :placeholder="$t('validation.enter', [$t('tech.styleCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('tech.bulkOrderNo')" prop="bulkOrderNo">
        <el-input
          v-model="queryParams.bulkOrderNo"
          :placeholder="$t('validation.enter', [$t('tech.bulkOrderNo')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('tech.salesName')" prop="salesName">
        <el-input
          v-model="queryParams.salesName"
          :placeholder="$t('validation.enter', [$t('tech.salesName')])"
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
          v-hasPermi="['erp:tech:add']"
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
          v-hasPermi="['erp:tech:edit']"
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
          v-hasPermi="['erp:tech:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:tech:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sampleTechList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('tech.auditStatus')" align="center" prop="auditStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('tech.progressStatus')" align="center" prop="progressStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_check_progress" :value="scope.row.progressStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('tech.customer')" align="center" prop="customerName" />
      <el-table-column :label="$t('tech.sampleTypeDisplay')" align="center" prop="sampleTypeDisplay" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleTypeDisplay"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('tech.styleType')" align="center" prop="styleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('tech.sampleCategoryType')" align="center" prop="sampleCategoryType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('tech.styleCode')" align="center" prop="styleCode" width="120" />
      <el-table-column :label="$t('tech.bulkOrderNo')" align="center" prop="bulkOrderNo" width="120" />
      <el-table-column :label="$t('tech.salesName')" align="center" prop="salesName" width="100" />
      <el-table-column :label="$t('tech.dueDate')" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('tech.shrinkageRateLimit')" align="center" prop="shrinkageRateLimit" width="90">
        <template slot-scope="scope">
          <span v-if="scope.row.shrinkageRateLimit != null">{{ scope.row.shrinkageRateLimit }}%</span>
          <span v-else style="color:#C0C4CC">—</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('tech.settingTemp')" align="center" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.settingTempMin != null || scope.row.settingTempMax != null">
            {{ scope.row.settingTempMin || '?' }}~{{ scope.row.settingTempMax || '?' }}℃
          </span>
          <span v-else style="color:#C0C4CC">—</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:tech:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:tech:remove']"
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

    <!-- 添加或修改工艺指示书对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('tech.auditStatus')" prop="auditStatus">
              <el-select v-model="form.auditStatus" :placeholder="$t('validation.select', [$t('tech.auditStatus')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_audit_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('tech.progressStatus')" prop="progressStatus">
              <el-select v-model="form.progressStatus" :placeholder="$t('validation.select', [$t('tech.progressStatus')])">
                <el-option
                  v-for="dict in dict.type.erp_check_progress"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('tech.customerName')" prop="customerName">
              <el-input v-model="form.customerName" :placeholder="$t('validation.enter', [$t('tech.customerName')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('tech.sampleTypeDisplay')" prop="sampleTypeDisplay">
              <el-select v-model="form.sampleTypeDisplay" :placeholder="$t('validation.select', [$t('tech.sampleTypeDisplay')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('tech.styleType')" prop="styleType">
              <el-select v-model="form.styleType" :placeholder="$t('validation.select', [$t('tech.styleType')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_style"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('tech.sampleCategoryType')" prop="sampleCategoryType">
              <el-select v-model="form.sampleCategoryType" :placeholder="$t('validation.select', [$t('tech.sampleCategoryType')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_category"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('tech.styleCode')" prop="styleCode">
              <el-input v-model="form.styleCode" :placeholder="$t('validation.enter', [$t('tech.styleCode')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('tech.bulkOrderNo')" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" :placeholder="$t('validation.enter', [$t('tech.bulkOrderNo')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('tech.salesName')" prop="salesName">
              <el-input v-model="form.salesName" :placeholder="$t('validation.enter', [$t('tech.salesName')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('tech.dueDate')" prop="dueDate">
              <el-date-picker clearable
                v-model="form.dueDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('tech.dueDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('validation.enter', [$t('system.remark')])" />
        </el-form-item>
        <el-divider content-position="left">{{ $t('tech.dyeingFinishThreshold') }}</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item :label="$t('tech.shrinkageRateLimitLabel')" prop="shrinkageRateLimit">
              <el-input-number v-model="form.shrinkageRateLimit" :precision="2" :min="0" :max="30" :placeholder="$t('tech.shrinkagePlaceholder')" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="$t('tech.colorDifferenceGradeMin')" prop="colorDifferenceGradeMin">
              <el-input-number v-model="form.colorDifferenceGradeMin" :precision="1" :min="1" :max="5" :step="0.5" :placeholder="$t('tech.colorDiffPlaceholder')" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="$t('tech.phRange')" prop="phRange">
              <el-input v-model="form.phRange" :placeholder="$t('tech.phRangePlaceholder')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item :label="$t('tech.settingTempMinLabel')" prop="settingTempMin">
              <el-input-number v-model="form.settingTempMin" :min="0" :max="300" :placeholder="$t('tech.tempMinPlaceholder')" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="$t('tech.settingTempMaxLabel')" prop="settingTempMax">
              <el-input-number v-model="form.settingTempMax" :min="0" :max="300" :placeholder="$t('tech.tempMaxPlaceholder')" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="$t('tech.washFastnessRequirement')" prop="washFastnessRequirement">
              <el-select v-model="form.washFastnessRequirement" :placeholder="$t('tech.washFastnessPlaceholder')" clearable style="width:100%">
                <el-option :label="$t('tech.grade5')" value="5" />
                <el-option :label="$t('tech.grade45')" value="4.5" />
                <el-option :label="$t('tech.grade4')" value="4" />
                <el-option :label="$t('tech.grade35')" value="3.5" />
                <el-option :label="$t('tech.grade3')" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('tech.rubFastnessRequirement')" prop="rubFastnessRequirement">
              <el-select v-model="form.rubFastnessRequirement" :placeholder="$t('tech.rubFastnessPlaceholder')" clearable style="width:100%">
                <el-option :label="$t('tech.grade5')" value="5" />
                <el-option :label="$t('tech.grade45')" value="4.5" />
                <el-option :label="$t('tech.grade4')" value="4" />
                <el-option :label="$t('tech.grade35')" value="3.5" />
                <el-option :label="$t('tech.grade3')" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTech, getTech, delTech, addTech, updateTech } from "@/api/erp/tech"

export default {
  name: "Tech",
  dicts: ['erp_sample_type', 'erp_sample_style', 'erp_sample_category', 'erp_sample_audit_status', 'erp_check_progress'],
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
      // 工艺指示书表格数据
      sampleTechList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        auditStatus: null,
        progressStatus: null,
        customerName: null,
        sampleTypeDisplay: null,
        styleType: null,
        sampleCategoryType: null,
        styleCode: null,
        bulkOrderNo: null,
        salesName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工艺指示书列表 */
    getList() {
      this.loading = true
      listTech(this.queryParams).then(response => {
        this.sampleTechList = response.rows
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
        auditStatus: null,
        progressStatus: null,
        customerName: null,
        sampleTypeDisplay: null,
        styleType: null,
        sampleCategoryType: null,
        styleCode: null,
        bulkOrderNo: null,
        salesName: null,
        sampleType: null,
        sampleId: null,
        dueDate: null,
        stylePic: null,
        tagPic: null,
        tagPicRemark: null,
        pattenMarker: null,
        pattenChecker: null,
        cuttingTip: null,
        liningTip: null,
        threadTip: null,
        needleTip: null,
        sewingTip: null,
        backGarmentTip: null,
        tagHangingTip: null,
        ironingTip: null,
        fabricTip: null,
        seamSealingTip: null,
        handStitchingTip: null,
        handStitchingInspection: null,
        washingTip: null,
        auditBy: null,
        auditTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        shrinkageRateLimit: null,
        colorDifferenceGradeMin: null,
        settingTempMax: null,
        settingTempMin: null,
        washFastnessRequirement: null,
        rubFastnessRequirement: null,
        phRange: null
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
      this.title = this.$t('tech.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTech(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('tech.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateTech(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addTech(this.form).then(response => {
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
      this.$modal.confirm(this.$t('tech.deleteConfirm', [ids])).then(function() {
        return delTech(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/tech/export', {
        ...this.queryParams
      }, `tech_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
