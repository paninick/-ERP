<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="销售订单id" prop="salesOrderId">
        <el-input
          v-model="queryParams.salesOrderId"
          placeholder="请输入销售订单id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="材料类型" prop="materialType">
        <el-select v-model="queryParams.materialType" placeholder="请选择材料类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_material_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="主料id" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入主料id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="颜色" prop="color">
        <el-input
          v-model="queryParams.color"
          placeholder="请输入颜色"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单耗" prop="unitConsumption">
        <el-input
          v-model="queryParams.unitConsumption"
          placeholder="请输入单耗"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="损耗方式" prop="lossType">
        <el-select v-model="queryParams.lossType" placeholder="请选择损耗方式" clearable>
          <el-option
            v-for="dict in dict.type.erp_loss_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="损耗" prop="wastage">
        <el-input
          v-model="queryParams.wastage"
          placeholder="请输入损耗"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排产数量" prop="planQuantity">
        <el-input
          v-model="queryParams.planQuantity"
          placeholder="请输入排产数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求总量" prop="totalQuantity">
        <el-input
          v-model="queryParams.totalQuantity"
          placeholder="请输入需求总量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计算公式" prop="totalDesc">
        <el-input
          v-model="queryParams.totalDesc"
          placeholder="请输入计算公式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库数量" prop="inboundQuantity">
        <el-input
          v-model="queryParams.inboundQuantity"
          placeholder="请输入入库数量"
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
          v-hasPermi="['erp:salesmaterial:add']"
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
          v-hasPermi="['erp:salesmaterial:edit']"
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
          v-hasPermi="['erp:salesmaterial:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:salesmaterial:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesmaterialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="销售订单id" align="center" prop="salesOrderId" />
      <el-table-column label="材料类型" align="center" prop="materialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_material_type" :value="scope.row.materialType"/>
        </template>
      </el-table-column>
      <el-table-column label="主料id" align="center" prop="materialId" />
      <el-table-column label="颜色" align="center" prop="color" />
      <el-table-column label="单耗" align="center" prop="unitConsumption" />
      <el-table-column label="损耗方式" align="center" prop="lossType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_loss_type" :value="scope.row.lossType"/>
        </template>
      </el-table-column>
      <el-table-column label="损耗" align="center" prop="wastage" />
      <el-table-column label="排产数量" align="center" prop="planQuantity" />
      <el-table-column label="需求总量" align="center" prop="totalQuantity" />
      <el-table-column label="计算公式" align="center" prop="totalDesc" />
      <el-table-column label="入库数量" align="center" prop="inboundQuantity" />
      <el-table-column label="状态" align="center" prop="materialStatus" />
      <el-table-column label="库存状态" align="center" prop="inventoryStatus" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:salesmaterial:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:salesmaterial:remove']"
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

    <!-- 添加或修改大货订单物料明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="销售订单" prop="salesOrderId" required>
          <el-select v-model="form.salesOrderId" placeholder="请选择销售订单" clearable
            filterable clearable remote :remote-method="filterSalesOrder" loading="salesOrderLoading">
            <el-option
              v-for="item in salesOrderOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="材料类型" prop="materialType" required>
              <el-select v-model="form.materialType" placeholder="请选择材料类型">
                <el-option
                  v-for="dict in dict.type.erp_material_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主料" prop="materialId" required>
              <el-select v-model="form.materialId" placeholder="请选择主料" clearable
                filterable clearable remote :remote-method="filterMaterial" loading="materialLoading">
                <el-option
                  v-for="item in materialOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="颜色" prop="color" required>
              <el-input v-model="form.color" placeholder="请输入颜色" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单耗" prop="unitConsumption" required>
              <el-input-number v-model="form.unitConsumption" :precision="4" :min="0" placeholder="请输入单耗" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="损耗方式" prop="lossType" required>
              <el-select v-model="form.lossType" placeholder="请选择损耗方式">
                <el-option
                  v-for="dict in dict.type.erp_loss_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="损耗" prop="wastage">
              <el-input-number v-model="form.wastage" :precision="4" :min="0" placeholder="请输入损耗" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="排产数量" prop="planQuantity" required>
              <el-input-number v-model="form.planQuantity" :precision="2" :min="0" placeholder="请输入排产数量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="需求总量" prop="totalQuantity">
              <el-input-number v-model="form.totalQuantity" :precision="2" :min="0" placeholder="请输入需求总量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="计算公式" prop="totalDesc">
          <el-input v-model="form.totalDesc" placeholder="请输入计算公式" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入库数量" prop="inboundQuantity">
              <el-input-number v-model="form.inboundQuantity" :precision="2" :min="0" placeholder="请输入入库数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
import { listSalesmaterial, getSalesmaterial, delSalesmaterial, addSalesmaterial, updateSalesmaterial } from "@/api/erp/salesmaterial"
import { listSales } from "@/api/erp/sales"
import { listMaterial as listMainMaterial } from "@/api/erp/material"

export default {
  name: "Salesmaterial",
  dicts: ['erp_loss_type', 'erp_material_type'],
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
      // 大货订单物料明细表格数据
      salesmaterialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesOrderId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
        planQuantity: null,
        totalQuantity: null,
        totalDesc: null,
        inboundQuantity: null,
        materialStatus: null,
        inventoryStatus: null,
      },
      // 销售订单选项
      salesOrderOptions: [],
      salesOrderLoading: false,
      // 主料选项
      materialOptions: [],
      materialLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        salesOrderId: [
          { required: true, message: "销售订单不能为空", trigger: "change" }
        ],
        materialType: [
          { required: true, message: "材料类型不能为空", trigger: "change" }
        ],
        materialId: [
          { required: true, message: "主料不能为空", trigger: "change" }
        ],
        unitConsumption: [
          { required: true, message: "单耗不能为空", trigger: "blur" }
        ],
        lossType: [
          { required: true, message: "损耗方式不能为空", trigger: "change" }
        ],
        planQuantity: [
          { required: true, message: "排产数量不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 过滤销售订单 */
    filterSalesOrder(query) {
      if (!query) {
        this.salesOrderOptions = []
        return
      }
      this.salesOrderLoading = true
      listSales({ pageNum: 1, pageSize: 20, salesNo: query }).then(response => {
        this.salesOrderOptions = response.rows.map(r => ({
          value: r.id,
          label: r.salesNo
        }))
        this.salesOrderLoading = false
      }).catch(() => {
        this.salesOrderLoading = false
      })
    },
    /** 过滤主料 */
    filterMaterial(query) {
      if (!query) {
        this.materialOptions = []
        return
      }
      this.materialLoading = true
      listMainMaterial({ pageNum: 1, pageSize: 20, materialName: query }).then(response => {
        this.materialOptions = response.rows.map(r => ({
          value: r.id,
          label: r.materialName
        }))
        this.materialLoading = false
      }).catch(() => {
        this.materialLoading = false
      })
    },
    /** 查询大货订单物料明细列表 */
    getList() {
      this.loading = true
      listSalesmaterial(this.queryParams).then(response => {
        this.salesmaterialList = response.rows
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
        salesOrderId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
        planQuantity: null,
        totalQuantity: null,
        totalDesc: null,
        inboundQuantity: null,
        materialStatus: null,
        inventoryStatus: null,
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
      this.title = "添加大货订单物料明细"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSalesmaterial(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改大货订单物料明细"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSalesmaterial(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSalesmaterial(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除大货订单物料明细编号为"' + ids + '"的数据项？').then(function() {
        return delSalesmaterial(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/salesmaterial/export', {
        ...this.queryParams
      }, `salesmaterial_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
