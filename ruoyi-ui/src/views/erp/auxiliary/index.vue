<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('auxiliary.materialType')" prop="auxiliaryMaterialType">
        <el-select v-model="queryParams.auxiliaryMaterialType" :placeholder="$t('auxiliary.selectMaterialType')" clearable>
          <el-option
            v-for="dict in dict.type.erp_auxiliary_material_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('auxiliary.materialNo')" prop="auxiliaryMaterialNo">
        <el-input
          v-model="queryParams.auxiliaryMaterialNo"
          :placeholder="$t('auxiliary.enterMaterialNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('auxiliary.supplierId')" prop="supplierId">
        <el-select v-model="queryParams.supplierId" :placeholder="$t('auxiliary.selectSupplier')" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('auxiliary.name')" prop="name">
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('auxiliary.enterName')"
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
          v-hasPermi="['erp:auxiliary:add']"
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
          v-hasPermi="['erp:auxiliary:edit']"
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
          v-hasPermi="['erp:auxiliary:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:auxiliary:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:auxiliary:import']"
        >{{ $t('btn.import') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="auxiliaryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column :label="$t('auxiliary.materialType')" align="center" prop="auxiliaryMaterialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_auxiliary_material_type" :value="scope.row.auxiliaryMaterialType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('auxiliary.materialNo')" align="center" prop="auxiliaryMaterialNo" />
      <el-table-column :label="$t('auxiliary.supplyMethod')" align="center" prop="supplyMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supply_method" :value="scope.row.supplyMethod"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('auxiliary.supplier')" align="center" prop="supplierName" />
      <el-table-column :label="$t('auxiliary.name')" align="center" prop="name" />
      <el-table-column :label="$t('auxiliary.substance')" align="center" prop="substance" />
      <el-table-column :label="$t('auxiliary.size')" align="center" prop="size" />
      <el-table-column :label="$t('auxiliary.unit')" align="center" prop="unit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_unit" :value="scope.row.unit"/>
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
            v-hasPermi="['erp:auxiliary:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:auxiliary:remove']"
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

    <!-- 添加或修改辅料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('auxiliary.materialType')" prop="auxiliaryMaterialType">
          <el-select v-model="form.auxiliaryMaterialType" :placeholder="$t('auxiliary.selectMaterialType')">
            <el-option
              v-for="dict in dict.type.erp_auxiliary_material_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('auxiliary.materialNo')" prop="auxiliaryMaterialNo">
          <el-input v-model="form.auxiliaryMaterialNo" :placeholder="$t('auxiliary.enterMaterialNo')" />
        </el-form-item>
        <el-form-item :label="$t('auxiliary.supplyMethod')" prop="supplyMethod">
          <el-input v-model="form.supplyMethod" :placeholder="$t('auxiliary.enterSupplyMethod')" />
        </el-form-item>
        <el-form-item :label="$t('auxiliary.supplierId')" prop="supplierId">
          <el-select v-model="form.supplierId" :placeholder="$t('auxiliary.selectSupplier')">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('auxiliary.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('auxiliary.enterName')" />
        </el-form-item>
        <el-form-item :label="$t('auxiliary.substance')" prop="substance">
          <el-input v-model="form.substance" :placeholder="$t('auxiliary.enterSubstance')" />
        </el-form-item>
        <el-form-item :label="$t('auxiliary.size')" prop="size">
          <el-input v-model="form.size" :placeholder="$t('auxiliary.enterSize')" />
        </el-form-item>
        <el-form-item :label="$t('auxiliary.unit')" prop="unit">
          <el-select v-model="form.unit" :placeholder="$t('auxiliary.selectUnit')">
            <el-option
              v-for="dict in dict.type.erp_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('auxiliary.picture')" prop="pictureUrl">
          <el-input v-model="form.pictureUrl" type="textarea" :placeholder="$t('auxiliary.enterPicture')" />
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
    <el-dialog :title="$t('auxiliary.importTitle')" :visible.sync="upload.open" width="400px" append-to-body>
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
        <div class="el-upload__text">{{ $t('upload.dragText') }}</div>
        <div class="el-upload__tip" slot="tip">{{ $t('upload.allowedExcelShort') }}</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import { listAuxiliary, getAuxiliary, delAuxiliary, addAuxiliary, updateAuxiliary } from "@/api/erp/auxiliary"
import { getToken } from "@/utils/auth"

export default {
  name: "Auxiliary",
  dicts: ['erp_unit', 'sys_user_sex', 'erp_auxiliary_material_type', 'erp_supply_method'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      auxiliaryList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        auxiliaryMaterialType: null,
        auxiliaryMaterialNo: null,
        supplierId: null,
        name: null,
      },
      form: {},
      rules: {},
      upload: {
        open: false,
        url: process.env.VUE_APP_BASE_API + "/erp/auxiliary/importData",
        headers: { Authorization: "Bearer " + getToken() },
        data: {
          updateSupport: false
        }
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询辅料列表 */
    getList() {
      this.loading = true
      listAuxiliary(this.queryParams).then(response => {
        this.auxiliaryList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        auxiliaryMaterialType: null,
        auxiliaryMaterialNo: null,
        supplyMethod: null,
        supplierId: null,
        name: null,
        substance: null,
        size: null,
        unit: null,
        pictureUrl: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = this.$t('auxiliary.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAuxiliary(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('auxiliary.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateAuxiliary(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addAuxiliary(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.addSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(function() {
        return delAuxiliary(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/auxiliary/export', {
        ...this.queryParams
      }, `auxiliary_${new Date().getTime()}.xlsx`)
    },
    handleImport() {
      this.upload.open = true
    },
    onSuccess(response, file, fileList) {
      if (response.code === 200) {
        this.$modal.msgSuccess(response.msg)
        this.upload.open = false
        this.getList()
      } else {
        this.$modal.msgError(response.msg)
      }
    },
    onError(err, file, fileList) {
      this.$modal.msgError(this.$t('upload.uploadFailed'))
    },
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
    handleChange(file, fileList) {
    }
  }
}
</script>
