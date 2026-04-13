<template>
  <!-- 导入表 -->
  <el-dialog title="导入表" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-tabs v-model="activeTab">
       <el-tab-pane label="从数据库搜索导入" name="search">
         <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
           <el-form-item label="表名称" prop="tableName">
             <el-input
               v-model="queryParams.tableName"
               placeholder="请输入表名称"
               clearable
               @keyup.enter.native="handleQuery"
             />
           </el-form-item>
           <el-form-item label="表描述" prop="tableComment">
             <el-input
               v-model="queryParams.tableComment"
               placeholder="请输入表描述"
               clearable
               @keyup.enter.native="handleQuery"
             />
           </el-form-item>
           <el-form-item>
             <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
             <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
           </el-form-item>
         </el-form>
         <el-row>
           <el-table @row-click="clickRow" ref="table" :data="dbTableList" @selection-change="handleSelectionChange" height="260px">
             <el-table-column type="selection" width="55"></el-table-column>
             <el-table-column prop="tableName" label="表名称" :show-overflow-tooltip="true"></el-table-column>
             <el-table-column prop="tableComment" label="表描述" :show-overflow-tooltip="true"></el-table-column>
             <el-table-column prop="createTime" label="创建时间"></el-table-column>
             <el-table-column prop="updateTime" label="更新时间"></el-table-column>
           </el-table>
           <pagination
             v-show="total>0"
             :total="total"
             :page.sync="queryParams.pageNum"
             :limit.sync="queryParams.pageSize"
             @pagination="getList"
           />
         </el-row>
       </el-tab-pane>
       <el-tab-pane label="手动输入表名导入" name="manual">
         <el-form :model="manualForm" ref="manualForm" size="small" label-width="100px">
           <el-form-item label="表名称" prop="tableName" required>
             <el-input
               v-model="manualForm.tableName"
               placeholder="多个表名用逗号分隔，如: t_erp_customer,t_erp_order"
               clearable
             />
           </el-form-item>
           <el-form-item label="提示">
             <div style="color: #999; font-size: 12px; line-height: 1.8;">
               • 从正式库生成代码时，开发环境数据库不存在该表，可以使用此方式手动导入<br>
               • 表名必须与正式库完全一致，导入后会读取表结构信息生成代码
             </div>
           </el-form-item>
         </el-form>
       </el-tab-pane>
       <el-tab-pane label="上传ZIP导入" name="zip">
         <el-form :model="zipForm" ref="zipForm" size="small" label-width="100px">
           <el-form-item label="ZIP文件" prop="zipFile" required>
              <el-upload
                class="zip-uploader"
                :action="uploadUrl"
                :show-file-list="false"
                :on-success="handleZipSuccess"
                :before-upload="beforeZipUpload"
                :headers="{'Authorization': 'Bearer ' + getToken()}"
                accept=".zip">
                <el-button size="small" type="primary">
                  <i class="el-icon-upload"></i> 选择正式库导出的ZIP文件
                </el-button>
              </el-upload>
            </el-form-item>
           <div v-if="zipTableNames.length > 0" class="zip-preview">
             <p><strong>检测到以下表将被导入：</strong></p>
             <el-tag v-for="name in zipTableNames" :key="name" size="small" style="margin: 5px;">{{name}}</el-tag>
           </div>
           <el-form-item label="提示">
             <div style="color: #999; font-size: 12px; line-height: 1.8;">
               • 直接上传正式库生成代码的 ZIP 文件，自动解析表名导入<br>
               • 上传后会自动提取所有表名，点击确定即可批量导入<br>
               • 适合从正式库迁移表结构到开发/测试库
             </div>
           </el-form-item>
         </el-form>
       </el-tab-pane>
     </el-tabs>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleConfirm()">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listDbTable, importTable } from "@/api/tool/gen"
import { getToken } from "@/utils/auth"
export default {
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中标签页
      activeTab: 'search',
      // 选中数组值
      tables: [],
      // 总条数
      total: 0,
      // 表数据
      dbTableList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      },
      // 手动导入表单
      manualForm: {
        tableName: ''
      },
      // ZIP导入表单
      zipForm: {
        zipFile: null
      },
      // ZIP解析出的表名
      zipTableNames: [],
      // 上传地址
      uploadUrl: process.env.VUE_APP_BASE_API + '/tool/gen/importZip'
    }
  },
  methods: {
    getToken,
    // 显示弹框
    show() {
      this.getList()
      this.visible = true
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.tables = selection.map(item => item.tableName)
    },
    // 查询表数据
    getList() {
      listDbTable(this.queryParams).then(res => {
        if (res.code === 200) {
          this.dbTableList = res.rows
          this.total = res.total
        }
      })
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
    /** 确认导入 */
    handleConfirm() {
      if (this.activeTab === 'search') {
        // 搜索导入方式
        const tableNames = this.tables.join(",")
        if (tableNames == "") {
          this.$modal.msgError("请选择要导入的表")
          return
        }
        this.doImport(tableNames)
      } else if (this.activeTab === 'manual') {
        // 手动导入方式
        const tableNames = this.manualForm.tableName.trim()
        if (tableNames === "") {
          this.$modal.msgError("请输入表名称")
          return
        }
        this.doImport(tableNames)
      } else if (this.activeTab === 'zip') {
        // ZIP导入方式
        if (this.zipTableNames.length === 0) {
          this.$modal.msgError("请先上传ZIP文件")
          return
        }
        const tableNames = this.zipTableNames.join(",")
        this.doImport(tableNames)
      }
    },
    /** 执行导入 */
    doImport(tableNames) {
      importTable({ tables: tableNames, tplWebType: 'element-ui' }).then(res => {
        this.$modal.msgSuccess(res.msg)
        if (res.code === 200) {
          this.visible = false
          this.$emit("ok")
          // 清空表单
          this.tables = []
          this.manualForm.tableName = ''
          this.zipTableNames = []
        }
      })
    },
    /** ZIP上传前检查 */
    beforeZipUpload(file) {
      const isZip = file.type === 'application/zip' || file.name.endsWith('.zip')
      if (!isZip) {
        this.$modal.msgError('只能上传 ZIP 文件!')
        return false
      }
      const isLt10M = file.size / 1024 / 1024 < 10
      if (!isLt10M) {
        this.$modal.msgError('上传文件大小不能超过 10MB!')
        return false
      }
      return true
    },
    /** ZIP上传成功处理 */
    handleZipSuccess(res) {
      if (res.code === 200) {
        this.zipTableNames = res.data
        this.$modal.msgSuccess(`检测到 ${res.data.length} 个表`)
      } else {
        this.$modal.msgError(res.msg || '解析ZIP失败')
      }
    }
  }
}
</script>

<style scoped>
.zip-preview {
  padding: 10px 0;
  p {
    margin-bottom: 10px;
  }
}

.zip-uploader >>> .el-upload {
  border: none;
  background-color: transparent;
}
</style>
