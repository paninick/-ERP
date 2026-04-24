<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('planClothes.planId')" prop="planId">
        <el-input
          v-model="queryParams.planId"
          :placeholder="$t('validation.enter', [$t('planClothes.planId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planClothes.salesItemId')" prop="salesItemId">
        <el-input
          v-model="queryParams.salesItemId"
          :placeholder="$t('validation.enter', [$t('planClothes.salesItemId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planClothes.color')" prop="color">
        <el-input
          v-model="queryParams.color"
          :placeholder="$t('validation.enter', [$t('planClothes.color')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planClothes.size')" prop="size">
        <el-input
          v-model="queryParams.size"
          :placeholder="$t('validation.enter', [$t('planClothes.size')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planClothes.orderQuantity')" prop="orderQuantity">
        <el-input
          v-model="queryParams.orderQuantity"
          :placeholder="$t('validation.enter', [$t('planClothes.orderQuantity')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planClothes.extraQuantity')" prop="extraQuantity">
        <el-input
          v-model="queryParams.extraQuantity"
          :placeholder="$t('validation.enter', [$t('planClothes.extraQuantity')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planClothes.planQuantity')" prop="planQuantity">
        <el-input
          v-model="queryParams.planQuantity"
          :placeholder="$t('validation.enter', [$t('planClothes.planQuantity')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planClothes.inboundQuantity')" prop="inboundQuantity">
        <el-input
          v-model="queryParams.inboundQuantity"
          :placeholder="$t('validation.enter', [$t('planClothes.inboundQuantity')])"
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
          v-hasPermi="['erp:planclothes:add']"
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
          v-hasPermi="['erp:planclothes:edit']"
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
          v-hasPermi="['erp:planclothes:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:planclothes:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planclothesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column :label="$t('planClothes.planId')" align="center" prop="planId" />
      <el-table-column :label="$t('planClothes.salesItemId')" align="center" prop="salesItemId" />
      <el-table-column :label="$t('planClothes.color')" align="center" prop="color" />
      <el-table-column :label="$t('planClothes.size')" align="center" prop="size" />
      <el-table-column :label="$t('planClothes.orderQuantity')" align="center" prop="orderQuantity" />
      <el-table-column :label="$t('planClothes.extraQuantity')" align="center" prop="extraQuantity" />
      <el-table-column :label="$t('planClothes.planQuantity')" align="center" prop="planQuantity" />
      <el-table-column :label="$t('planClothes.inboundQuantity')" align="center" prop="inboundQuantity" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:planclothes:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:planclothes:remove']"
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

    <!-- 添加或修改生产计划衣服明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('planClothes.planId')" prop="planId">
          <el-input v-model="form.planId" :placeholder="$t('validation.enter', [$t('planClothes.planId')])" />
        </el-form-item>
        <el-form-item :label="$t('planClothes.salesItemId')" prop="salesItemId">
          <el-input v-model="form.salesItemId" :placeholder="$t('validation.enter', [$t('planClothes.salesItemId')])" />
        </el-form-item>
        <el-form-item :label="$t('planClothes.color')" prop="color">
          <el-input v-model="form.color" :placeholder="$t('validation.enter', [$t('planClothes.color')])" />
        </el-form-item>
        <el-form-item :label="$t('planClothes.size')" prop="size">
          <el-input v-model="form.size" :placeholder="$t('validation.enter', [$t('planClothes.size')])" />
        </el-form-item>
        <el-form-item :label="$t('planClothes.orderQuantity')" prop="orderQuantity">
          <el-input v-model="form.orderQuantity" :placeholder="$t('validation.enter', [$t('planClothes.orderQuantity')])" />
        </el-form-item>
        <el-form-item :label="$t('planClothes.extraQuantity')" prop="extraQuantity">
          <el-input v-model="form.extraQuantity" :placeholder="$t('validation.enter', [$t('planClothes.extraQuantity')])" />
        </el-form-item>
        <el-form-item :label="$t('planClothes.planQuantity')" prop="planQuantity">
          <el-input v-model="form.planQuantity" :placeholder="$t('validation.enter', [$t('planClothes.planQuantity')])" />
        </el-form-item>
        <el-form-item :label="$t('planClothes.inboundQuantity')" prop="inboundQuantity">
          <el-input v-model="form.inboundQuantity" :placeholder="$t('validation.enter', [$t('planClothes.inboundQuantity')])" />
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
import { listPlanclothes, getPlanclothes, delPlanclothes, addPlanclothes, updatePlanclothes } from "@/api/erp/planclothes"

export default {
  name: "Planclothes",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      planclothesList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planId: null,
        salesItemId: null,
        color: null,
        size: null,
        orderQuantity: null,
        extraQuantity: null,
        planQuantity: null,
        inboundQuantity: null,
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
      listPlanclothes(this.queryParams).then(response => {
        this.planclothesList = response.rows
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
        planId: null,
        salesItemId: null,
        color: null,
        size: null,
        orderQuantity: null,
        extraQuantity: null,
        planQuantity: null,
        inboundQuantity: null,
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
      this.title = this.$t('planClothes.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPlanclothes(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('planClothes.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updatePlanclothes(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addPlanclothes(this.form).then(response => {
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
      this.$modal.confirm(this.$t('planClothes.destroyConfirm', [ids])).then(function() {
        return delPlanclothes(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/planclothes/export', {
        ...this.queryParams
      }, `planclothes_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
