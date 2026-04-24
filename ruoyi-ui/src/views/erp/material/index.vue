<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="4" style="border-right: 1px solid #eee;min-height: 800px;">
        <el-tree
          :data="materialTree"
          :props="treeProps"
          node-key="id"
          default-expand-all
          :filter-node-method="filterNode"
          ref="tree"
          @node-click="handleNodeClick"
        />
      </el-col>
      <el-col :span="20">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('material.type')" prop="mainMaterialType">
        <el-select v-model="queryParams.mainMaterialType" :placeholder="$t('validation.select', [$t('material.type')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_main_material_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('material.no')" prop="mainMaterialNo">
        <el-input
          v-model="queryParams.mainMaterialNo"
          :placeholder="$t('validation.enter', [$t('material.no')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('material.supplyMethod')" prop="supplyMethod">
        <el-select v-model="queryParams.supplyMethod" :placeholder="$t('validation.select', [$t('material.supplyMethod')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_supply_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('material.supplier')" prop="supplierId">
        <el-select v-model="queryParams.supplierId" :placeholder="$t('validation.select', [$t('material.supplier')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_supplier_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('material.name')" prop="name">
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('validation.enter', [$t('material.name')])"
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
          v-hasPermi="['erp:material:add']"
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
          v-hasPermi="['erp:material:edit']"
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
          v-hasPermi="['erp:material:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:material:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:material:import']"
        >{{ $t('btn.import') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('material.type')" align="center" prop="mainMaterialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_main_material_type" :value="scope.row.mainMaterialType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('material.no')" align="center" prop="mainMaterialNo" />
      <el-table-column :label="$t('material.supplyMethod')" align="center" prop="supplyMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supply_method" :value="scope.row.supplyMethod"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('material.supplier')" align="center" prop="supplierId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supplier_type" :value="scope.row.supplierId"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('material.name')" align="center" prop="name" />
      <el-table-column :label="$t('material.composition')" align="center" prop="composition" />
      <el-table-column :label="$t('material.width')" align="center" prop="width" />
      <el-table-column :label="$t('material.weight')" align="center" prop="weight" />
      <el-table-column :label="$t('material.yarnCount')" align="center" prop="yarnCount" />
      <el-table-column :label="$t('material.unit')" align="center" prop="unit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_unit" :value="scope.row.unit"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('material.picture')" align="center" prop="pictrueUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.pictrueUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.updateTime')" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:material:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:material:remove']"
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

    <!-- 添加或修改主料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('material.type')" prop="mainMaterialType">
          <el-select v-model="form.mainMaterialType" :placeholder="$t('validation.select', [$t('material.type')])">
            <el-option
              v-for="dict in dict.type.erp_main_material_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('material.no')" prop="mainMaterialNo">
          <el-input v-model="form.mainMaterialNo" :placeholder="$t('validation.enter', [$t('material.no')])" />
        </el-form-item>
        <el-form-item :label="$t('material.supplyMethod')" prop="supplyMethod">
          <el-select v-model="form.supplyMethod" :placeholder="$t('validation.select', [$t('material.supplyMethod')])">
            <el-option
              v-for="dict in dict.type.erp_supply_method"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('material.supplier')" prop="supplierId">
          <el-select v-model="form.supplierId" :placeholder="$t('validation.select', [$t('material.supplier')])">
            <el-option
              v-for="dict in dict.type.erp_supplier_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('material.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('validation.enter', [$t('material.name')])" />
        </el-form-item>
        <el-form-item :label="$t('material.composition')" prop="composition">
          <el-input v-model="form.composition" :placeholder="$t('validation.enter', [$t('material.composition')])" />
        </el-form-item>
        <el-form-item :label="$t('material.width')" prop="width">
          <el-input v-model="form.width" :placeholder="$t('validation.enter', [$t('material.width')])" />
        </el-form-item>
        <el-form-item :label="$t('material.weight')" prop="weight">
          <el-input v-model="form.weight" :placeholder="$t('validation.enter', [$t('material.weight')])" />
        </el-form-item>
        <el-form-item :label="$t('material.yarnCount')" prop="yarnCount">
          <el-input v-model="form.yarnCount" :placeholder="$t('validation.enter', [$t('material.yarnCount')])" />
        </el-form-item>
        <el-form-item :label="$t('material.unit')" prop="unit">
          <el-select v-model="form.unit" :placeholder="$t('validation.select', [$t('material.unit')])">
            <el-option
              v-for="dict in dict.type.erp_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('material.picture')" prop="pictrueUrl">
          <image-upload v-model="form.pictrueUrl"/>
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

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        :action="upload.url"
        :headers="upload.headers"
        :data="upload.data"
        :on-change="handleChange"
        :before-upload="beforeUpload"
        :on-success="onSuccess"
        :on-error="onError"
        :limit="1"
        accept=".xlsx,.xls"
        drag
        auto-upload
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text" v-html="$t('upload.dragText')"></div>
        <div class="el-upload__tip" slot="tip">{{ $t('upload.allowedExcelShort') }}</div>
      </el-upload>
    </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listMaterial, getMaterial, delMaterial, addMaterial, updateMaterial } from "@/api/erp/material"
import { getToken } from "@/utils/auth"

export default {
  name: "Material",
  dicts: ['erp_unit', 'erp_main_material_type', 'erp_supplier_type', 'erp_supply_method'],
  data() {
    return {
      // 树形数据
      materialTree: [],
      treeProps: {
        children: 'children',
        label: 'label'
      },
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
      // 主料表格数据
      materialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mainMaterialType: null,
        mainMaterialNo: null,
        supplyMethod: null,
        supplierId: null,
        name: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 上传参数
      upload: {
        open: false,
        title: '',
        url: process.env.VUE_APP_BASE_API + "/erp/material/main/importData",
        headers: { Authorization: "Bearer " + getToken() },
        data: {
          updateSupport: false
        }
      }
    }
  },
  created() {
    this.upload.title = this.$t('material.importTitle')
    this.initTree()
    this.getList()
  },
  methods: {
    /** 初始化树形 */
    initTree() {
      this.materialTree = [
        {
          id: 1,
          label: this.$t('material.factorySupply'),
          children: []
        },
        {
          id: 2,
          label: this.$t('material.customerSupply'),
          children: []
        }
      ]
    },
    /** 查询主料列表 */
    getList() {
      this.loading = true
      listMaterial(this.queryParams).then(response => {
        this.materialList = response.rows
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
        mainMaterialType: null,
        mainMaterialNo: null,
        supplyMethod: null,
        supplierId: null,
        name: null,
        composition: null,
        width: null,
        weight: null,
        yarnCount: null,
        unit: null,
        pictrueUrl: null,
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
      this.title = this.$t('material.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMaterial(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('material.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateMaterial(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addMaterial(this.form).then(response => {
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
        return delMaterial(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/material/main/export', {
        ...this.queryParams
      }, `material_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = this.$t('material.importTitle')
      this.upload.open = true
    },
    /** 上传成功回调 */
    onSuccess(response, file, fileList) {
      if (response.code === 200) {
        this.$modal.msgSuccess(response.msg)
        this.upload.open = false
        this.getList()
      } else {
        this.$modal.msgError(response.msg)
      }
    },
    /** 上传失败回调 */
    onError(err, file, fileList) {
      this.$modal.msgError(this.$t('upload.uploadFailed'))
    },
    /** 上传前校验 */
    beforeUpload(file) {
      const isExcel = /\.(xlsx|xls)$/i.test(file.name)
      if (!isExcel) {
        this.$modal.msgError(this.$t('upload.onlyExcel'))
        return false
      }
      const isLt10M = file.size / 1024 / 1024 < 10
      if (!isLt10M) {
        this.$modal.msgError(this.$t('upload.sizeLimit'))
        return false
      }
      return true
    },
    /** 文件改变 */
    handleChange(file, fileList) {
    },
    /** 树形过滤 */
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    /** 节点点击 */
    handleNodeClick(data) {
      this.queryParams.supplyMethod = data.id
      this.queryParams.pageNum = 1
      this.getList()
    }
  }
}
</script>
