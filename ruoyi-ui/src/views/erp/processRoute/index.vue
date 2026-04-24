<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('processRoute.routeName')" prop="routeName">
        <el-input
          v-model="queryParams.routeName"
          :placeholder="$t('processRoute.enterRouteName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('processRoute.productType')" prop="productType">
        <el-input
          v-model="queryParams.productType"
          :placeholder="$t('processRoute.enterProductType')"
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
          v-hasPermi="['erp:processRoute:add']"
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
          v-hasPermi="['erp:processRoute:edit']"
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
          v-hasPermi="['erp:processRoute:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:processRoute:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processRouteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('processRoute.routeName')" align="center" prop="routeName" />
      <el-table-column :label="$t('processRoute.productType')" align="center" prop="productType" />
      <el-table-column :label="$t('processRoute.productCode')" align="center" prop="productCode" />
      <el-table-column :label="$t('processRoute.isDefault')" align="center" prop="isDefault">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isDefault"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('processRoute.status')" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:processRoute:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:processRoute:remove']"
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

    <!-- 添加或修改工艺路线对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('processRoute.routeName')" prop="routeName">
          <el-input v-model="form.routeName" :placeholder="$t('processRoute.enterRouteName')" />
        </el-form-item>
        <el-form-item :label="$t('processRoute.productType')" prop="productType">
          <el-input v-model="form.productType" :placeholder="$t('processRoute.enterProductType')" />
        </el-form-item>
        <el-form-item :label="$t('processRoute.productCode')" prop="productCode">
          <el-input v-model="form.productCode" :placeholder="$t('processRoute.enterProductCode')" />
        </el-form-item>
        <el-form-item :label="$t('processRoute.isDefault')" prop="isDefault">
          <el-radio-group v-model="form.isDefault">
            <el-radio :label="0">{{ $t('status.no') }}</el-radio>
            <el-radio :label="1">{{ $t('status.yes') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('processRoute.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">{{ $t('processRoute.normal') }}</el-radio>
            <el-radio label="1">{{ $t('processRoute.disabled') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('processRoute.enterContent')" />
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
import { listProcessRoute, getProcessRoute, delProcessRoute, addProcessRoute, updateProcessRoute } from "@/api/erp/processRoute"

export default {
  name: "ProcessRoute",
  dicts: ['sys_yes_no', 'sys_normal_disable'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      processRouteList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        routeName: null,
        productType: null,
        productCode: null,
        status: null
      },
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
    getList() {
      this.loading = true
      listProcessRoute(this.queryParams).then(response => {
        this.processRouteList = response.rows
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
        routeName: null,
        productType: null,
        productCode: null,
        isDefault: 0,
        status: '0',
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
      this.title = this.$t('processRoute.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcessRoute(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('processRoute.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateProcessRoute({ route: this.form, items: routeItems }).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addProcessRoute({ route: this.form, items: routeItems }).then(response => {
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
      this.$modal.confirm(this.$t('processRoute.deleteConfirm', [ids])).then(function() {
        return delProcessRoute(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/processRoute/export', {
        ...this.queryParams
      }, `processRoute_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
