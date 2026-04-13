package com.ruoyi.demo.constant;

/**
 * Demo模块业务错误码
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoErrorCode {

    private DemoErrorCode() {
    }

    /**
     * 订单管理错误码 (1000-1999)
     */
    public static final int ORDER_NOT_FOUND = 1001;
    public static final int ORDER_ALREADY_EXISTS = 1002;
    public static final int ORDER_CANNOT_DELETE = 1003;
    public static final int ORDER_STATUS_INVALID = 1004;
    public static final int ORDER_FACTORY_NOT_FOUND = 1005;

    /**
     * 款式管理错误码 (2000-2999)
     */
    public static final int STYLE_NOT_FOUND = 2001;
    public static final int STYLE_ALREADY_EXISTS = 2002;
    public static final int STYLE_CANNOT_DELETE = 2003;
    public static final int STYLE_STATUS_INVALID = 2004;
    public static final int STYLE_COST_INVALID = 2005;

    /**
     * 外发管理错误码 (3000-3999)
     */
    public static final int OUTSOURCE_NOT_FOUND = 3001;
    public static final int OUTSOURCE_ALREADY_EXISTS = 3002;
    public static final int OUTSOURCE_CANNOT_DELETE = 3003;
    public static final int OUTSOURCE_STATUS_INVALID = 3004;
    public static final int OUTSOURCE_QTY_EXCEEDED = 3005;
    public static final int OUTSOURCE_EXTRA_NOT_FOUND = 3006;

    /**
     * 排程管理错误码 (4000-4999)
     */
    public static final int SCHEDULE_NOT_FOUND = 4001;
    public static final int SCHEDULE_ALREADY_EXISTS = 4002;
    public static final int SCHEDULE_CANNOT_DELETE = 4003;
    public static final int SCHEDULE_CAPACITY_EXCEEDED = 4004;
    public static final int SCHEDULE_DATE_INVALID = 4005;

    /**
     * 报工管理错误码 (5000-5999)
     */
    public static final int REPORT_NOT_FOUND = 5001;
    public static final int REPORT_ALREADY_EXISTS = 5002;
    public static final int REPORT_CANNOT_DELETE = 5003;
    public static final int REPORT_QTY_INVALID = 5004;
    public static final int REPORT_DATE_INVALID = 5005;
    public static final int REPORT_REWORK_COST_INVALID = 5006;

    /**
     * 成本管理错误码 (6000-6999)
     */
    public static final int COST_CALCULATION_ERROR = 6001;
    public static final int COST_STYLE_NOT_FOUND = 6002;
    public static final int COST_ORDER_NOT_FOUND = 6003;
    public static final int COST_RATE_INVALID = 6004;
    public static final int COST_FOB_PRICE_INVALID = 6005;
    public static final int COST_CIF_PRICE_INVALID = 6006;
    public static final int COST_CNF_PRICE_INVALID = 6007;
    public static final int COST_EXW_PRICE_INVALID = 6008;

    /**
     * 工厂管理错误码 (7000-7999)
     */
    public static final int FACTORY_NOT_FOUND = 7001;
    public static final int FACTORY_ALREADY_EXISTS = 7002;
    public static final int FACTORY_CANNOT_DELETE = 7003;
    public static final int FACTORY_STATUS_INVALID = 7004;

    /**
     * 数据验证错误码 (8000-8999)
     */
    public static final int VALIDATION_ERROR = 8001;
    public static final int VALIDATION_REQUIRED_FIELD = 8002;
    public static final int VALIDATION_INVALID_FORMAT = 8003;
    public static final int VALIDATION_INVALID_RANGE = 8004;
    public static final int VALIDATION_INVALID_LENGTH = 8005;
    public static final int VALIDATION_INVALID_DECIMAL = 8006;

    /**
     * 业务操作错误码 (9000-9999)
     */
    public static final int OPERATION_NOT_ALLOWED = 9001;
    public static final int OPERATION_DATA_LOCKED = 9002;
    public static final int OPERATION_PERMISSION_DENIED = 9003;
    public static final int OPERATION_TOO_FREQUENT = 9004;
    public static final int OPERATION_DEPENDENCY_EXISTS = 9005;

    /**
     * 获取错误码对应的默认消息
     *
     * @param errorCode 错误码
     * @return 默认错误消息
     */
    public static String getDefaultMessage(int errorCode) {
        switch (errorCode) {
            case ORDER_NOT_FOUND:
                return "订单不存在";
            case ORDER_ALREADY_EXISTS:
                return "订单已存在";
            case ORDER_CANNOT_DELETE:
                return "订单无法删除";
            case ORDER_STATUS_INVALID:
                return "订单状态无效";
            case ORDER_FACTORY_NOT_FOUND:
                return "订单所属工厂不存在";
            case STYLE_NOT_FOUND:
                return "款式不存在";
            case STYLE_ALREADY_EXISTS:
                return "款式已存在";
            case STYLE_CANNOT_DELETE:
                return "款式无法删除";
            case STYLE_STATUS_INVALID:
                return "款式状态无效";
            case STYLE_COST_INVALID:
                return "款式成本无效";
            case OUTSOURCE_NOT_FOUND:
                return "外发单不存在";
            case OUTSOURCE_ALREADY_EXISTS:
                return "外发单已存在";
            case OUTSOURCE_CANNOT_DELETE:
                return "外发单无法删除";
            case OUTSOURCE_STATUS_INVALID:
                return "外发单状态无效";
            case OUTSOURCE_QTY_EXCEEDED:
                return "外发数量超出限制";
            case OUTSOURCE_EXTRA_NOT_FOUND:
                return "补料单不存在";
            case SCHEDULE_NOT_FOUND:
                return "排程不存在";
            case SCHEDULE_ALREADY_EXISTS:
                return "排程已存在";
            case SCHEDULE_CANNOT_DELETE:
                return "排程无法删除";
            case SCHEDULE_CAPACITY_EXCEEDED:
                return "产能超出限制";
            case SCHEDULE_DATE_INVALID:
                return "排程日期无效";
            case REPORT_NOT_FOUND:
                return "报工记录不存在";
            case REPORT_ALREADY_EXISTS:
                return "报工记录已存在";
            case REPORT_CANNOT_DELETE:
                return "报工记录无法删除";
            case REPORT_QTY_INVALID:
                return "报工数量无效";
            case REPORT_DATE_INVALID:
                return "报工日期无效";
            case REPORT_REWORK_COST_INVALID:
                return "返工成本无效";
            case COST_CALCULATION_ERROR:
                return "成本计算错误";
            case COST_STYLE_NOT_FOUND:
                return "成本计算所需款式不存在";
            case COST_ORDER_NOT_FOUND:
                return "成本计算所需订单不存在";
            case COST_RATE_INVALID:
                return "利润率无效";
            case COST_FOB_PRICE_INVALID:
                return "FOB价格无效";
            case COST_CIF_PRICE_INVALID:
                return "CIF价格无效";
            case COST_CNF_PRICE_INVALID:
                return "CNF价格无效";
            case COST_EXW_PRICE_INVALID:
                return "EXW价格无效";
            case FACTORY_NOT_FOUND:
                return "工厂不存在";
            case FACTORY_ALREADY_EXISTS:
                return "工厂已存在";
            case FACTORY_CANNOT_DELETE:
                return "工厂无法删除";
            case FACTORY_STATUS_INVALID:
                return "工厂状态无效";
            case VALIDATION_ERROR:
                return "数据验证错误";
            case VALIDATION_REQUIRED_FIELD:
                return "必填字段不能为空";
            case VALIDATION_INVALID_FORMAT:
                return "数据格式无效";
            case VALIDATION_INVALID_RANGE:
                return "数据范围无效";
            case VALIDATION_INVALID_LENGTH:
                return "数据长度无效";
            case VALIDATION_INVALID_DECIMAL:
                return "小数格式无效";
            case OPERATION_NOT_ALLOWED:
                return "操作不允许";
            case OPERATION_DATA_LOCKED:
                return "数据已锁定";
            case OPERATION_PERMISSION_DENIED:
                return "权限不足";
            case OPERATION_TOO_FREQUENT:
                return "操作过于频繁";
            case OPERATION_DEPENDENCY_EXISTS:
                return "存在依赖关系";
            default:
                return "未知错误";
        }
    }
}
