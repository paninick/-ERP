<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="打样id" prop="sampleId">
        <el-input
          v-model="queryParams.sampleId"
          placeholder="请输入打样id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="要求交期" prop="dueDate">
        <el-date-picker clearable
          v-model="queryParams.dueDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择要求交期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="款式图" prop="stylePic">
        <el-input
          v-model="queryParams.stylePic"
          placeholder="请输入款式图"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订标位置示意图" prop="tagPic">
        <el-input
          v-model="queryParams.tagPic"
          placeholder="请输入订标位置示意图"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订表位置说明" prop="tagPicRemark">
        <el-input
          v-model="queryParams.tagPicRemark"
          placeholder="请输入订表位置说明"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="制版人id" prop="pattenMarker">
        <el-input
          v-model="queryParams.pattenMarker"
          placeholder="请输入制版人id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="核版人id" prop="pattenChecker">
        <el-input
          v-model="queryParams.pattenChecker"
          placeholder="请输入核版人id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批人" prop="auditBy">
        <el-input
          v-model="queryParams.auditBy"
          placeholder="请输入审批人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批时间" prop="auditTime">
        <el-date-picker clearable
          v-model="queryParams.auditTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择审批时间">
        </el-date-picker>
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
          v-hasPermi="['erp:tech:add']"
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
          v-hasPermi="['erp:tech:edit']"
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
          v-hasPermi="['erp:tech:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:tech:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="techList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="打样类型：1-服装 2-毛衣" align="center" prop="sampleType" />
      <el-table-column label="打样id" align="center" prop="sampleId" />
      <el-table-column label="要求交期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="款式图" align="center" prop="stylePic" />
      <el-table-column label="订标位置示意图" align="center" prop="tagPic" />
      <el-table-column label="订表位置说明" align="center" prop="tagPicRemark" />
      <el-table-column label="制版人id" align="center" prop="pattenMarker" />
      <el-table-column label="核版人id" align="center" prop="pattenChecker" />
      <el-table-column label="裁剪要求" align="center" prop="cuttingTip" />
      <el-table-column label="用衬要求" align="center" prop="liningTip" />
      <el-table-column label="用线要求" align="center" prop="threadTip" />
      <el-table-column label="运针要求" align="center" prop="needleTip" />
      <el-table-column label="缝制工艺说明" align="center" prop="sewingTip" />
      <el-table-column label="后套工艺说明" align="center" prop="backGarmentTip" />
      <el-table-column label="吊牌挂法" align="center" prop="tagHangingTip" />
      <el-table-column label="整烫要求" align="center" prop="ironingTip" />
      <el-table-column label="织造要求" align="center" prop="fabricTip" />
      <el-table-column label="套口要求" align="center" prop="seamSealingTip" />
      <el-table-column label="手缝要求" align="center" prop="handStitchingTip" />
      <el-table-column label="套口手缝检验" align="center" prop="handStitchingInspection" />
      <el-table-column label="水洗要求" align="center" prop="washingTip" />
      <el-table-column label="审批状态" align="center" prop="auditStatus" />
      <el-table-column label="审批人" align="center" prop="auditBy" />
      <el-table-column label="审批时间" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="制表人" align="center" prop="createBy" />
      <el-table-column label="制表时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:tech:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:tech:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="打样id" prop="sampleId">
          <el-input v-model="form.sampleId" placeholder="请输入打样id" />
        </el-form-item>
        <el-form-item label="要求交期" prop="dueDate">
          <el-date-picker clearable
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择要求交期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="款式图" prop="stylePic">
          <el-input v-model="form.stylePic" placeholder="请输入款式图" />
        </el-form-item>
        <el-form-item label="订标位置示意图" prop="tagPic">
          <el-input v-model="form.tagPic" placeholder="请输入订标位置示意图" />
        </el-form-item>
        <el-form-item label="订表位置说明" prop="tagPicRemark">
          <el-input v-model="form.tagPicRemark" placeholder="请输入订表位置说明" />
        </el-form-item>
        <el-form-item label="制版人id" prop="pattenMarker">
          <el-input v-model="form.pattenMarker" placeholder="请输入制版人id" />
        </el-form-item>
        <el-form-item label="核版人id" prop="pattenChecker">
          <el-input v-model="form.pattenChecker" placeholder="请输入核版人id" />
        </el-form-item>
        <el-form-item label="裁剪要求" prop="cuttingTip">
          <el-input v-model="form.cuttingTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="用衬要求" prop="liningTip">
          <el-input v-model="form.liningTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="用线要求" prop="threadTip">
          <el-input v-model="form.threadTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="运针要求" prop="needleTip">
          <el-input v-model="form.needleTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="缝制工艺说明" prop="sewingTip">
          <el-input v-model="form.sewingTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="后套工艺说明" prop="backGarmentTip">
          <el-input v-model="form.backGarmentTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="吊牌挂法" prop="tagHangingTip">
          <el-input v-model="form.tagHangingTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="整烫要求" prop="ironingTip">
          <el-input v-model="form.ironingTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="织造要求" prop="fabricTip">
          <el-input v-model="form.fabricTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="套口要求" prop="seamSealingTip">
          <el-input v-model="form.seamSealingTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="手缝要求" prop="handStitchingTip">
          <el-input v-model="form.handStitchingTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="套口手缝检验" prop="handStitchingInspection">
          <el-input v-model="form.handStitchingInspection" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="水洗要求" prop="washingTip">
          <el-input v-model="form.washingTip" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="审批人" prop="auditBy">
          <el-input v-model="form.auditBy" placeholder="请输入审批人" />
        </el-form-item>
        <el-form-item label="审批时间" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择审批时间">
          </el-date-picker>
        </el-form-item>
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
import { listTech, getTech, delTech, addTech, updateTech } from "@/api/erp/tech"

export default {
  name: "Tech",
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
      techList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
        auditStatus: null,
        auditBy: null,
        auditTime: null,
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
        this.techList = response.rows
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
        auditStatus: null,
        auditBy: null,
        auditTime: null,
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
      this.download('erp/tech/export', {
        ...this.queryParams
      }, `tech_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
