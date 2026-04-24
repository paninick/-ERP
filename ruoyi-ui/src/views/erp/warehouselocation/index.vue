<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('warehouseLocation.name')" prop="name">
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('validation.enter', [$t('warehouseLocation.name')])"
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
          v-hasPermi="['erp:warehouselocation:add']"
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
          v-hasPermi="['erp:warehouselocation:edit']"
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
          v-hasPermi="['erp:warehouselocation:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:warehouselocation:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouselocationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('warehouseLocation.id')" align="center" prop="id" />
      <el-table-column :label="$t('warehouseLocation.code')" align="center" prop="code" />
      <el-table-column :label="$t('warehouseLocation.name')" align="center" prop="name" />
      <el-table-column :label="$t('warehouseLocation.warehouseAreaId')" align="center" prop="warehouseAreaName" />
      <el-table-column :label="$t('warehouseLocation.warehouseId')" align="center" prop="warehouseName" />
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:warehouselocation:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:warehouselocation:remove']"
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

    <!-- 添加或修改仓位管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('warehouseLocation.code')" prop="code">
          <el-input v-model="form.code" :placeholder="$t('validation.enter', [$t('warehouseLocation.code')])" />
        </el-form-item>
        <el-form-item :label="$t('warehouseLocation.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('validation.enter', [$t('warehouseLocation.name')])" />
        </el-form-item>
        <el-form-item :label="$t('warehouseLocation.warehouseAreaId')" prop="warehouseAreaId">
          <el-select v-model="form.warehouseAreaId" :placeholder="$t('validation.select', [$t('warehouseLocation.warehouseAreaId')])" clearable>
            <el-option
              v-for="item in warehouseAreaList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('warehouseLocation.warehouseId')" prop="warehouseId">
          <el-select v-model="form.warehouseId" :placeholder="$t('validation.select', [$t('warehouseLocation.warehouseId')])" clearable>
            <el-option
              v-for="item in warehouseList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
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
import { listWarehouselocation, getWarehouselocation, delWarehouselocation, addWarehouselocation, updateWarehouselocation } from "@/api/erp/warehouselocation"
import { listWarehouse } from "@/api/erp/warehouse"
import { listWarehousearea } from "@/api/erp/warehousearea"

export default {
  name: "Warehouselocation",
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
      // 仓位管理表格数据
      warehouselocationList: [],
      // 仓库列表
      warehouseList: [],
      // 仓库区域列表
      warehouseAreaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        warehouseAreaId: null,
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
    this.getWarehouseList()
    this.getWarehouseAreaList()
  },
  methods: {
    /** 查询仓位管理列表 */
    getList() {
      this.loading = true
      listWarehouselocation(this.queryParams).then(response => {
        this.warehouselocationList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询仓库列表 */
    getWarehouseList() {
      listWarehouse({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.warehouseList = response.rows
      })
    },
    /** 查询仓库区域列表 */
    getWarehouseAreaList() {
      listWarehousearea({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.warehouseAreaList = response.rows
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
        code: null,
        name: null,
        warehouseAreaId: null,
        warehouseId: null,
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
      this.title = this.$t('warehouseLocation.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getWarehouselocation(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('warehouseLocation.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateWarehouselocation(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addWarehouselocation(this.form).then(response => {
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
        return delWarehouselocation(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/warehouselocation/export', {
        ...this.queryParams
      }, `warehouselocation_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
