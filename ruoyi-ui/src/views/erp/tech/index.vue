<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="审批状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="进行状态" prop="progressStatus">
        <el-select v-model="queryParams.progressStatus" placeholder="请选择进行状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_check_progress"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打样类型" prop="sampleTypeDisplay">
        <el-select v-model="queryParams.sampleTypeDisplay" placeholder="请选择打样类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="样品款式" prop="styleType">
        <el-select v-model="queryParams.styleType" placeholder="请选择样品款式" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_style"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="样品类型" prop="sampleCategoryType">
        <el-select v-model="queryParams.sampleCategoryType" placeholder="请选择样品类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="款号" prop="styleCode">
        <el-input
          v-model="queryParams.styleCode"
          placeholder="请输入款号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="大货款号" prop="bulkOrderNo">
        <el-input
          v-model="queryParams.bulkOrderNo"
          placeholder="请输入大货款号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务员" prop="salesName">
        <el-input
          v-model="queryParams.salesName"
          placeholder="请输入业务员"
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
          v-hasPermi="['erp:sampleTech:add']"
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
          v-hasPermi="['erp:sampleTech:edit']"
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
          v-hasPermi="['erp:sampleTech:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:sampleTech:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sampleTechList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="审批状态" align="center" prop="auditStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="进行状态" align="center" prop="progressStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_check_progress" :value="scope.row.progressStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="客户" align="center" prop="customerName" />
      <el-table-column label="打样类型" align="center" prop="sampleTypeDisplay" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleTypeDisplay"/>
        </template>
      </el-table-column>
      <el-table-column label="样品款式" align="center" prop="styleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column label="样品类型" align="center" prop="sampleCategoryType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column label="款号" align="center" prop="styleCode" width="120" />
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" width="120" />
      <el-table-column label="业务员" align="center" prop="salesName" width="100" />
      <el-table-column label="要求交期" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缩水率上限" align="center" prop="shrinkageRateLimit" width="90">
        <template slot-scope="scope">
          <span v-if="scope.row.shrinkageRateLimit != null">{{ scope.row.shrinkageRateLimit }}%</span>
          <span v-else style="color:#C0C4CC">—</span>
        </template>
      </el-table-column>
      <el-table-column label="定型温度" align="center" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.settingTempMin != null || scope.row.settingTempMax != null">
            {{ scope.row.settingTempMin || '?' }}~{{ scope.row.settingTempMax || '?' }}℃
          </span>
          <span v-else style="color:#C0C4CC">—</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:sampleTech:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:sampleTech:remove']"
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

    <!-- 添加或修改工艺指示书对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批状态" prop="auditStatus">
              <el-select v-model="form.auditStatus" placeholder="请选择审批状态">
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
            <el-form-item label="进行状态" prop="progressStatus">
              <el-select v-model="form.progressStatus" placeholder="请选择进行状态">
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
            <el-form-item label="客户名称" prop="customerName">
              <el-input v-model="form.customerName" placeholder="请输入客户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="打样类型" prop="sampleTypeDisplay">
              <el-select v-model="form.sampleTypeDisplay" placeholder="请选择打样类型">
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
            <el-form-item label="样品款式" prop="styleType">
              <el-select v-model="form.styleType" placeholder="请选择样品款式">
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
            <el-form-item label="样品类型" prop="sampleCategoryType">
              <el-select v-model="form.sampleCategoryType" placeholder="请选择样品类型">
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
            <el-form-item label="款号" prop="styleCode">
              <el-input v-model="form.styleCode" placeholder="请输入款号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="大货款号" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" placeholder="请输入大货款号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务员" prop="salesName">
              <el-input v-model="form.salesName" placeholder="请输入业务员" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="要求交期" prop="dueDate">
              <el-date-picker clearable
                v-model="form.dueDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择要求交期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="left">染整阈值约束（对日JIS）</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="缩水率上限(%)" prop="shrinkageRateLimit">
              <el-input-number v-model="form.shrinkageRateLimit" :precision="2" :min="0" :max="30" placeholder="超出触发预缩" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="色差等级下限" prop="colorDifferenceGradeMin">
              <el-input-number v-model="form.colorDifferenceGradeMin" :precision="1" :min="1" :max="5" :step="0.5" placeholder="GB/T 250" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="pH值范围" prop="phRange">
              <el-input v-model="form.phRange" placeholder="如 4.0-7.5" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="定型温度下限(℃)" prop="settingTempMin">
              <el-input-number v-model="form.settingTempMin" :min="0" :max="300" placeholder="下限" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="定型温度上限(℃)" prop="settingTempMax">
              <el-input-number v-model="form.settingTempMax" :min="0" :max="300" placeholder="上限" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="水洗色牢度要求" prop="washFastnessRequirement">
              <el-select v-model="form.washFastnessRequirement" placeholder="JIS L 0844" clearable style="width:100%">
                <el-option label="5级（优）" value="5" />
                <el-option label="4-5级" value="4.5" />
                <el-option label="4级（合格）" value="4" />
                <el-option label="3-4级" value="3.5" />
                <el-option label="3级（警告）" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="摩擦色牢度要求" prop="rubFastnessRequirement">
              <el-select v-model="form.rubFastnessRequirement" placeholder="JIS L 0849" clearable style="width:100%">
                <el-option label="5级（优）" value="5" />
                <el-option label="4-5级" value="4.5" />
                <el-option label="4级（合格）" value="4" />
                <el-option label="3-4级" value="3.5" />
                <el-option label="3级（警告）" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
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
      this.title = "添加工艺指示书"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTech(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改工艺指示书"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTech(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addTech(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工艺指示书编号为"' + ids + '"的数据项？').then(function() {
        return delTech(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/sampleTech/export', {
        ...this.queryParams
      }, `sampleTech_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
