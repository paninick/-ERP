# Style Field Migration Scan

- Generated at: `2026-04-23T13:02:36`
- Root: `D:\erp\RuoYi-Vue`

## Totals

- Files with style field hits: `33`
- Legacy only: `15`
- Target only: `14`
- Mixed: `4`

## Group Summary

| Group | Files | Legacy Only | Target Only | Mixed | style_no | styleNo | style_code | styleCode |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| backend-controller | 2 | 1 | 1 | 0 | 1 | 3 | 0 | 1 |
| backend-domain | 7 | 2 | 5 | 0 | 0 | 10 | 0 | 30 |
| backend-mapper-java | 1 | 1 | 0 | 0 | 2 | 3 | 0 | 0 |
| backend-mapper-xml | 7 | 2 | 2 | 3 | 35 | 11 | 12 | 49 |
| frontend-view | 7 | 1 | 6 | 0 | 0 | 2 | 0 | 27 |
| other | 4 | 4 | 0 | 0 | 1 | 16 | 0 | 0 |
| sql | 5 | 4 | 0 | 1 | 46 | 0 | 37 | 0 |

## File Hits

| Status | Group | File | style_no | styleNo | style_code | styleCode |
| --- | --- | --- | ---: | ---: | ---: | ---: |
| legacy-only | backend-controller | `ruoyi-admin/src/main/java/com/ruoyi/erp/controller/ProductSerialController.java` | 1 | 3 | 0 | 0 |
| legacy-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/ProducePlan.java` | 0 | 5 | 0 | 0 |
| legacy-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/QcInspection.java` | 0 | 5 | 0 | 0 |
| legacy-only | backend-mapper-java | `ruoyi-admin/src/main/java/com/ruoyi/erp/mapper/ProductSerialMapper.java` | 2 | 3 | 0 | 0 |
| legacy-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/ProducePlanMapper.xml` | 10 | 6 | 0 | 0 |
| legacy-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/QcInspectionMapper.xml` | 3 | 5 | 0 | 0 |
| legacy-only | frontend-view | `ruoyi-ui/src/views/erp/quality/index.vue` | 0 | 2 | 0 | 0 |
| legacy-only | other | `ruoyi-admin/src/main/java/com/ruoyi/erp/utils/BillNoGenerator.java` | 1 | 0 | 0 | 0 |
| legacy-only | other | `ruoyi-ui/src/api/demo/cost.js` | 0 | 2 | 0 | 0 |
| legacy-only | other | `ruoyi-ui/src/views/demo/order/index.vue` | 0 | 7 | 0 | 0 |
| legacy-only | other | `ruoyi-ui/src/views/demo/style/index.vue` | 0 | 7 | 0 | 0 |
| legacy-only | sql | `sql/phase15_flexible_schedule.sql` | 1 | 0 | 0 | 0 |
| legacy-only | sql | `sql/phase20_product_traceability.sql` | 11 | 0 | 0 | 0 |
| legacy-only | sql | `sql/phase22_style_no_dual_key.sql` | 16 | 0 | 0 | 0 |
| legacy-only | sql | `sql/sprint_d_quality_invoice.sql` | 1 | 0 | 0 | 0 |
| mixed | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/CheckMapper.xml` | 5 | 0 | 0 | 12 |
| mixed | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/SalesOrderMapper.xml` | 12 | 0 | 0 | 10 |
| mixed | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/SampleTechMapper.xml` | 5 | 0 | 0 | 7 |
| mixed | sql | `sql/phase23_style_code_compat_migration.sql` | 17 | 0 | 37 | 0 |
| target-only | backend-controller | `ruoyi-admin/src/main/java/com/ruoyi/erp/controller/CheckController.java` | 0 | 0 | 0 | 1 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/Bom.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/Check.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/SalesOrder.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/SampleNotice.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/SampleTech.java` | 0 | 0 | 0 | 6 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/BomMapper.xml` | 0 | 0 | 6 | 10 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/SampleNoticeMapper.xml` | 0 | 0 | 6 | 10 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/bom/index.vue` | 0 | 0 | 0 | 1 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/check/index.vue` | 0 | 0 | 0 | 7 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/notice/index.vue` | 0 | 0 | 0 | 1 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/overview/index.vue` | 0 | 0 | 0 | 4 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/sales/index.vue` | 0 | 0 | 0 | 7 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/tech/index.vue` | 0 | 0 | 0 | 7 |

## Suggested Batch Order

1. Migrate `legacy-only` files in low-conflict backend/frontend modules.
2. Add compatibility SQL for tables still using `style_no`.
3. Resolve `mixed` files last to avoid breaking in-flight parallel work.
