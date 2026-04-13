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
      <el-form-item label="主料类型 " prop="mainMaterialType">
        <el-select v-model="queryParams.mainMaterialType" placeholder="请选择主料类型 " clearable>
          <el-option
            v-for="dict in dict.type.erp_main_material_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="主料编号" prop="mainMaterialNo">
        <el-input
          v-model="queryParams.mainMaterialNo"
          placeholder="请输入主料编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供货方式" prop="supplyMethod">
        <el-select v-model="queryParams.supplyMethod" placeholder="请选择供货方式" clearable>
          <el-option
            v-for="dict in dict.type.erp_supply_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable>
          <el-option
            v-for="dict in dict.type.erp_supplier_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="主料品名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入主料品名"
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
          v-hasPermi="['erp:material:add']"
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
          v-hasPermi="['erp:material:edit']"
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
          v-hasPermi="['erp:material:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:material:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:material:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主料类型 " align="center" prop="mainMaterialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_main_material_type" :value="scope.row.mainMaterialType"/>
        </template>
      </el-table-column>
      <el-table-column label="主料编号" align="center" prop="mainMaterialNo" />
      <el-table-column label="供货方式" align="center" prop="supplyMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supply_method" :value="scope.row.supplyMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商" align="center" prop="supplierId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supplier_type" :value="scope.row.supplierId"/>
        </template>
      </el-table-column>
      <el-table-column label="主料品名" align="center" prop="name" />
      <el-table-column label="主料成分" align="center" prop="composition" />
      <el-table-column label="主料门幅" align="center" prop="width" />
      <el-table-column label="主料克重" align="center" prop="weight" />
      <el-table-column label="主料纱支" align="center" prop="yarnCount" />
      <el-table-column label="计量单位" align="center" prop="unit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_unit" :value="scope.row.unit"/>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" prop="pictrueUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.pictrueUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:material:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:material:remove']"
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

    <!-- 添加或修改主料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="主料类型 " prop="mainMaterialType">
          <el-select v-model="form.mainMaterialType" placeholder="请选择主料类型 ">
            <el-option
              v-for="dict in dict.type.erp_main_material_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="主料编号" prop="mainMaterialNo">
          <el-input v-model="form.mainMaterialNo" placeholder="请输入主料编号" />
        </el-form-item>
        <el-form-item label="供货方式" prop="supplyMethod">
          <el-select v-model="form.supplyMethod" placeholder="请选择供货方式">
            <el-option
              v-for="dict in dict.type.erp_supply_method"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商">
            <el-option
              v-for="dict in dict.type.erp_supplier_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="主料品名" prop="name">
          <el-input v-model="form.name" placeholder="请输入主料品名" />
        </el-form-item>
        <el-form-item label="主料成分" prop="composition">
          <el-input v-model="form.composition" placeholder="请输入主料成分" />
        </el-form-item>
        <el-form-item label="主料门幅" prop="width">
          <el-input v-model="form.width" placeholder="请输入主料门幅" />
        </el-form-item>
        <el-form-item label="主料克重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入主料克重" />
        </el-form-item>
        <el-form-item label="主料纱支" prop="yarnCount">
          <el-input v-model="form.yarnCount" placeholder="请输入主料纱支" />
        </el-form-item>
        <el-form-item label="计量单位" prop="unit">
          <el-select v-model="form.unit" placeholder="请选择计量单位">
            <el-option
              v-for="dict in dict.type.erp_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片" prop="pictrueUrl">
          <image-upload v-model="form.pictrueUrl"/>
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
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传Excel文件，且不超过10MB</div>
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
        title: "导入主料数据",
        url: process.env.VUE_APP_BASE_API + "/erp/material/main/importData",
        headers: { Authorization: "Bearer " + getToken() },
        data: {
          updateSupport: false
        }
      }
    }
  },
  created() {
    this.initTree()
    this.getList()
  },
  methods: {
    /** 初始化树形 */
    initTree() {
      this.materialTree = [
        {
          id: 1,
          label: '厂供',
          children: []
        },
        {
          id: 2,
          label: '客供',
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
      this.title = "添加主料"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMaterial(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改主料"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMaterial(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMaterial(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除主料编号为"' + ids + '"的数据项？').then(function() {
        return delMaterial(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/material/export', {
        ...this.queryParams
      }, `material_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
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
      this.$modal.msgError("上传失败")
    },
    /** 上传前校验 */
    beforeUpload(file) {
      const isExcel = /\.(xlsx|xls)$/i.test(file.name)
      if (!isExcel) {
        this.$modal.msgError("只能上传Excel文件!")
        return false
      }
      const isLt10M = file.size / 1024 / 1024 < 10
      if (!isLt10M) {
        this.$modal.msgError("上传文件大小不能超过 10MB!")
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
