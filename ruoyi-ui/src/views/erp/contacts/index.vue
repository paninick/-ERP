<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('contacts.corpType')" prop="corpType">
        <el-select v-model="queryParams.corpType" :placeholder="$t('validation.select', [$t('contacts.corpType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_corp_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('contacts.corpId')" prop="corpId">
        <el-input
          v-model="queryParams.corpId"
          :placeholder="$t('validation.enter', [$t('contacts.corpId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('contacts.name')" prop="name">
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('validation.enter', [$t('contacts.name')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('contacts.department')" prop="department">
        <el-input
          v-model="queryParams.department"
          :placeholder="$t('validation.enter', [$t('contacts.department')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('contacts.sex')" prop="sex">
        <el-select v-model="queryParams.sex" :placeholder="$t('validation.select', [$t('contacts.sex')])" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('contacts.phone')" prop="phone">
        <el-input
          v-model="queryParams.phone"
          :placeholder="$t('validation.enter', [$t('contacts.phone')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('contacts.mail')" prop="mail">
        <el-input
          v-model="queryParams.mail"
          :placeholder="$t('validation.enter', [$t('contacts.mail')])"
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
          v-hasPermi="['erp:contacts:add']"
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
          v-hasPermi="['erp:contacts:edit']"
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
          v-hasPermi="['erp:contacts:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:contacts:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contactsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="联系人id" align="center" prop="id" />
      <el-table-column :label="$t('contacts.corpType')" align="center" prop="corpType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_corp_type" :value="scope.row.corpType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('contacts.corpId')" align="center" prop="corpId" />
      <el-table-column :label="$t('contacts.name')" align="center" prop="name" />
      <el-table-column :label="$t('contacts.department')" align="center" prop="department" />
      <el-table-column :label="$t('contacts.sex')" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('contacts.phone')" align="center" prop="phone" />
      <el-table-column :label="$t('contacts.mail')" align="center" prop="mail" />
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:contacts:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:contacts:remove']"
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

    <!-- 添加或修改公司联系人对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('contacts.corpType')" prop="corpType">
          <el-select v-model="form.corpType" :placeholder="$t('validation.select', [$t('contacts.corpType')])">
            <el-option
              v-for="dict in dict.type.erp_corp_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('contacts.corpId')" prop="corpId">
          <el-input v-model="form.corpId" :placeholder="$t('validation.enter', [$t('contacts.corpId')])" />
        </el-form-item>
        <el-form-item :label="$t('contacts.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('validation.enter', [$t('contacts.name')])" />
        </el-form-item>
        <el-form-item :label="$t('contacts.department')" prop="department">
          <el-input v-model="form.department" :placeholder="$t('validation.enter', [$t('contacts.department')])" />
        </el-form-item>
        <el-form-item :label="$t('contacts.sex')" prop="sex">
          <el-select v-model="form.sex" :placeholder="$t('validation.select', [$t('contacts.sex')])">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('contacts.phone')" prop="phone">
          <el-input v-model="form.phone" :placeholder="$t('validation.enter', [$t('contacts.phone')])" />
        </el-form-item>
        <el-form-item :label="$t('contacts.mail')" prop="mail">
          <el-input v-model="form.mail" :placeholder="$t('validation.enter', [$t('contacts.mail')])" />
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
import { listContacts, getContacts, delContacts, addContacts, updateContacts } from "@/api/erp/contacts"

export default {
  name: "Contacts",
  dicts: ['sys_user_sex', 'erp_corp_type'],
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
      // 公司联系人表格数据
      contactsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        corpType: null,
        corpId: null,
        name: null,
        department: null,
        sex: null,
        phone: null,
        mail: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询公司联系人列表 */
    getList() {
      this.loading = true
      listContacts(this.queryParams).then(response => {
        this.contactsList = response.rows
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
        corpType: null,
        corpId: null,
        name: null,
        department: null,
        sex: null,
        phone: null,
        mail: null,
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
      this.title = this.$t('contacts.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getContacts(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('contacts.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateContacts(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addContacts(this.form).then(response => {
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
        return delContacts(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/contacts/export', {
        ...this.queryParams
      }, `contacts_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
