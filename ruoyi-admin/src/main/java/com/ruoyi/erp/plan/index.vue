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
      <el-form-item label="打样通知id" prop="noticeId">
        <el-input
          v-model="queryParams.noticeId"
          placeholder="请输入打样通知id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工艺书id" prop="techId">
        <el-input
          v-model="queryParams.techId"
          placeholder="请输入工艺书id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划单编号" prop="planNo">
        <el-input
          v-model="queryParams.planNo"
          placeholder="请输入计划单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="跟单人id" prop="salesId">
        <el-input
          v-model="queryParams.salesId"
          placeholder="请输入跟单人id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="startDate">
        <el-date-picker clearable
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="领料完成时间" prop="pickedDate">
        <el-date-picker clearable
          v-model="queryParams.pickedDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择领料完成时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="完成日期" prop="completeDate">
        <el-date-picker clearable
          v-model="queryParams.completeDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择完成日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="原料到货日期" prop="materialArrivalDate">
        <el-date-picker clearable
          v-model="queryParams.materialArrivalDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择原料到货日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上线日期" prop="upDate">
        <el-date-picker clearable
          v-model="queryParams.upDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上线日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="下线日期" prop="downDate">
        <el-date-picker clearable
          v-model="queryParams.downDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择下线日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="生产方式 1-本厂 2-委外" prop="produceType">
        <el-select v-model="queryParams.produceType" placeholder="请选择生产方式 1-本厂 2-委外" clearable>
          <el-option
            v-for="dict in dict.type.erp_produce_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="工厂" prop="inFactory">
        <el-input
          v-model="queryParams.inFactory"
          placeholder="请输入工厂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="委外工厂" prop="outFactory">
        <el-input
          v-model="queryParams.outFactory"
          placeholder="请输入委外工厂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="委外时间" prop="outDate">
        <el-date-picker clearable
          v-model="queryParams.outDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择委外时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="前道日期" prop="preProcessDate">
        <el-date-picker clearable
          v-model="queryParams.preProcessDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择前道日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="送检日期" prop="inspectionDate">
        <el-date-picker clearable
          v-model="queryParams.inspectionDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择送检日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="送检公司" prop="inspectionCorp">
        <el-input
          v-model="queryParams.inspectionCorp"
          placeholder="请输入送检公司"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进仓日期" prop="inBoundDate">
        <el-date-picker clearable
          v-model="queryParams.inBoundDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择进仓日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="船期" prop="shippingDate">
        <el-date-picker clearable
          v-model="queryParams.shippingDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择船期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="后道日期" prop="postProcessDate">
        <el-date-picker clearable
          v-model="queryParams.postProcessDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择后道日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="交期" prop="dueDate">
        <el-date-picker clearable
          v-model="queryParams.dueDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择交期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="订单状态 1-新建 2-已安排生产计划 3-生产中 4-部分入库 5-已全部入库 6-部分出库 7-已完成出库 8-交付中 9-完成 10-取消" prop="planStatus">
        <el-select v-model="queryParams.planStatus" placeholder="请选择订单状态 1-新建 2-已安排生产计划 3-生产中 4-部分入库 5-已全部入库 6-部分出库 7-已完成出库 8-交付中 9-完成 10-取消" clearable>
          <el-option
            v-for="dict in dict.type.erp_plan_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="前道生产方式" prop="preProduceType">
        <el-select v-model="queryParams.preProduceType" placeholder="请选择前道生产方式" clearable>
          <el-option
            v-for="dict in dict.type.erp_produce_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="前道工厂" prop="preInFactory">
        <el-input
          v-model="queryParams.preInFactory"
          placeholder="请输入前道工厂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="前道委外工厂" prop="preOutFactory">
        <el-input
          v-model="queryParams.preOutFactory"
          placeholder="请输入前道委外工厂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="前道委外日期" prop="preOutDate">
        <el-date-picker clearable
          v-model="queryParams.preOutDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择前道委外日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="后道生产方式 1-本厂 2-委外" prop="postProduceType">
        <el-select v-model="queryParams.postProduceType" placeholder="请选择后道生产方式 1-本厂 2-委外" clearable>
          <el-option
            v-for="dict in dict.type.erp_produce_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="后道本厂" prop="postInFactory">
        <el-input
          v-model="queryParams.postInFactory"
          placeholder="请输入后道本厂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="后道委外工厂" prop="postOutFactory">
        <el-input
          v-model="queryParams.postOutFactory"
          placeholder="请输入后道委外工厂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="后道委外时间" prop="postOutDate">
        <el-date-picker clearable
          v-model="queryParams.postOutDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择后道委外时间">
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
          v-hasPermi="['erp:plan:add']"
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
          v-hasPermi="['erp:plan:edit']"
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
          v-hasPermi="['erp:plan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:plan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="销售订单id" align="center" prop="salesOrderId" />
      <el-table-column label="打样通知id" align="center" prop="noticeId" />
      <el-table-column label="工艺书id" align="center" prop="techId" />
      <el-table-column label="计划单编号" align="center" prop="planNo" />
      <el-table-column label="跟单人id" align="center" prop="salesId" />
      <el-table-column label="开始时间" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="领料完成时间" align="center" prop="pickedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pickedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成日期" align="center" prop="completeDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.completeDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="原料到货日期" align="center" prop="materialArrivalDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.materialArrivalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上线日期" align="center" prop="upDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.upDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="下线日期" align="center" prop="downDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.downDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产方式 1-本厂 2-委外" align="center" prop="produceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_produce_type" :value="scope.row.produceType"/>
        </template>
      </el-table-column>
      <el-table-column label="工厂" align="center" prop="inFactory" />
      <el-table-column label="委外工厂" align="center" prop="outFactory" />
      <el-table-column label="委外时间" align="center" prop="outDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="前道日期" align="center" prop="preProcessDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.preProcessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="送检日期" align="center" prop="inspectionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="送检公司" align="center" prop="inspectionCorp" />
      <el-table-column label="进仓日期" align="center" prop="inBoundDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inBoundDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="船期" align="center" prop="shippingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.shippingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="后道日期" align="center" prop="postProcessDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.postProcessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单状态 1-新建 2-已安排生产计划 3-生产中 4-部分入库 5-已全部入库 6-部分出库 7-已完成出库 8-交付中 9-完成 10-取消" align="center" prop="planStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_plan_status" :value="scope.row.planStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审批人" align="center" prop="auditBy" />
      <el-table-column label="审批时间" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="前道生产方式" align="center" prop="preProduceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_produce_type" :value="scope.row.preProduceType"/>
        </template>
      </el-table-column>
      <el-table-column label="前道工厂" align="center" prop="preInFactory" />
      <el-table-column label="前道委外工厂" align="center" prop="preOutFactory" />
      <el-table-column label="前道委外日期" align="center" prop="preOutDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.preOutDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="后道生产方式 1-本厂 2-委外" align="center" prop="postProduceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_produce_type" :value="scope.row.postProduceType"/>
        </template>
      </el-table-column>
      <el-table-column label="后道本厂" align="center" prop="postInFactory" />
      <el-table-column label="后道委外工厂" align="center" prop="postOutFactory" />
      <el-table-column label="后道委外时间" align="center" prop="postOutDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.postOutDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:plan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:plan:remove']"
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

    <!-- 添加或修改生产计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="销售订单id" prop="salesOrderId">
          <el-input v-model="form.salesOrderId" placeholder="请输入销售订单id" />
        </el-form-item>
        <el-form-item label="打样通知id" prop="noticeId">
          <el-input v-model="form.noticeId" placeholder="请输入打样通知id" />
        </el-form-item>
        <el-form-item label="工艺书id" prop="techId">
          <el-input v-model="form.techId" placeholder="请输入工艺书id" />
        </el-form-item>
        <el-form-item label="计划单编号" prop="planNo">
          <el-input v-model="form.planNo" placeholder="请输入计划单编号" />
        </el-form-item>
        <el-form-item label="跟单人id" prop="salesId">
          <el-input v-model="form.salesId" placeholder="请输入跟单人id" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startDate">
          <el-date-picker clearable
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="领料完成时间" prop="pickedDate">
          <el-date-picker clearable
            v-model="form.pickedDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择领料完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="完成日期" prop="completeDate">
          <el-date-picker clearable
            v-model="form.completeDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择完成日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="原料到货日期" prop="materialArrivalDate">
          <el-date-picker clearable
            v-model="form.materialArrivalDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择原料到货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上线日期" prop="upDate">
          <el-date-picker clearable
            v-model="form.upDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="下线日期" prop="downDate">
          <el-date-picker clearable
            v-model="form.downDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择下线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产方式 1-本厂 2-委外" prop="produceType">
          <el-radio-group v-model="form.produceType">
            <el-radio
              v-for="dict in dict.type.erp_produce_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="工厂" prop="inFactory">
          <el-input v-model="form.inFactory" placeholder="请输入工厂" />
        </el-form-item>
        <el-form-item label="委外工厂" prop="outFactory">
          <el-input v-model="form.outFactory" placeholder="请输入委外工厂" />
        </el-form-item>
        <el-form-item label="委外时间" prop="outDate">
          <el-date-picker clearable
            v-model="form.outDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择委外时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="前道日期" prop="preProcessDate">
          <el-date-picker clearable
            v-model="form.preProcessDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择前道日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="送检日期" prop="inspectionDate">
          <el-date-picker clearable
            v-model="form.inspectionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择送检日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="送检公司" prop="inspectionCorp">
          <el-input v-model="form.inspectionCorp" placeholder="请输入送检公司" />
        </el-form-item>
        <el-form-item label="进仓日期" prop="inBoundDate">
          <el-date-picker clearable
            v-model="form.inBoundDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择进仓日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="船期" prop="shippingDate">
          <el-date-picker clearable
            v-model="form.shippingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择船期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="后道日期" prop="postProcessDate">
          <el-date-picker clearable
            v-model="form.postProcessDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择后道日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="交期" prop="dueDate">
          <el-date-picker clearable
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择交期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单状态 1-新建 2-已安排生产计划 3-生产中 4-部分入库 5-已全部入库 6-部分出库 7-已完成出库 8-交付中 9-完成 10-取消" prop="planStatus">
          <el-select v-model="form.planStatus" placeholder="请选择订单状态 1-新建 2-已安排生产计划 3-生产中 4-部分入库 5-已全部入库 6-部分出库 7-已完成出库 8-交付中 9-完成 10-取消">
            <el-option
              v-for="dict in dict.type.erp_plan_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="请选择审批状态">
            <el-option
              v-for="dict in dict.type.erp_sample_audit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
        <el-form-item label="前道生产方式" prop="preProduceType">
          <el-radio-group v-model="form.preProduceType">
            <el-radio
              v-for="dict in dict.type.erp_produce_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="前道工厂" prop="preInFactory">
          <el-input v-model="form.preInFactory" placeholder="请输入前道工厂" />
        </el-form-item>
        <el-form-item label="前道委外工厂" prop="preOutFactory">
          <el-input v-model="form.preOutFactory" placeholder="请输入前道委外工厂" />
        </el-form-item>
        <el-form-item label="前道委外日期" prop="preOutDate">
          <el-date-picker clearable
            v-model="form.preOutDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择前道委外日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="后道生产方式 1-本厂 2-委外" prop="postProduceType">
          <el-radio-group v-model="form.postProduceType">
            <el-radio
              v-for="dict in dict.type.erp_produce_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="后道本厂" prop="postInFactory">
          <el-input v-model="form.postInFactory" placeholder="请输入后道本厂" />
        </el-form-item>
        <el-form-item label="后道委外工厂" prop="postOutFactory">
          <el-input v-model="form.postOutFactory" placeholder="请输入后道委外工厂" />
        </el-form-item>
        <el-form-item label="后道委外时间" prop="postOutDate">
          <el-date-picker clearable
            v-model="form.postOutDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择后道委外时间">
          </el-date-picker>
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
import { listPlan, getPlan, delPlan, addPlan, updatePlan } from "@/api/erp/plan"

export default {
  name: "Plan",
  dicts: ['erp_produce_type', 'erp_plan_status', 'erp_sample_audit_status'],
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
      // 生产计划表格数据
      planList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesOrderId: null,
        noticeId: null,
        techId: null,
        planNo: null,
        salesId: null,
        startDate: null,
        pickedDate: null,
        completeDate: null,
        materialArrivalDate: null,
        upDate: null,
        downDate: null,
        produceType: null,
        inFactory: null,
        outFactory: null,
        outDate: null,
        preProcessDate: null,
        inspectionDate: null,
        inspectionCorp: null,
        inBoundDate: null,
        shippingDate: null,
        postProcessDate: null,
        dueDate: null,
        planStatus: null,
        auditStatus: null,
        auditBy: null,
        auditTime: null,
        preProduceType: null,
        preInFactory: null,
        preOutFactory: null,
        preOutDate: null,
        postProduceType: null,
        postInFactory: null,
        postOutFactory: null,
        postOutDate: null
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
    /** 查询生产计划列表 */
    getList() {
      this.loading = true
      listPlan(this.queryParams).then(response => {
        this.planList = response.rows
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
        noticeId: null,
        techId: null,
        planNo: null,
        salesId: null,
        startDate: null,
        pickedDate: null,
        completeDate: null,
        materialArrivalDate: null,
        upDate: null,
        downDate: null,
        produceType: null,
        inFactory: null,
        outFactory: null,
        outDate: null,
        preProcessDate: null,
        inspectionDate: null,
        inspectionCorp: null,
        inBoundDate: null,
        shippingDate: null,
        postProcessDate: null,
        dueDate: null,
        planStatus: null,
        auditStatus: null,
        auditBy: null,
        auditTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        preProduceType: null,
        preInFactory: null,
        preOutFactory: null,
        preOutDate: null,
        postProduceType: null,
        postInFactory: null,
        postOutFactory: null,
        postOutDate: null
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
      this.title = "添加生产计划"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPlan(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改生产计划"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPlan(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除生产计划编号为"' + ids + '"的数据项？').then(function() {
        return delPlan(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
