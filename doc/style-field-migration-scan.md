# Style Field Migration Scan

- Generated at: `2026-04-23T15:19:18`
- Root: `D:\erp\RuoYi-Vue`

## Totals

- Files with style field hits: `33`
- Legacy only: `0`
- Target only: `32`
- Mixed: `0`
- Expected mixed: `1`

## Group Summary

| Group | Files | Legacy Only | Target Only | Mixed | Expected Mixed | style_no | styleNo | style_code | styleCode |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| backend-controller | 2 | 0 | 2 | 0 | 0 | 0 | 0 | 0 | 7 |
| backend-domain | 7 | 0 | 7 | 0 | 0 | 0 | 0 | 0 | 41 |
| backend-mapper-java | 1 | 0 | 1 | 0 | 0 | 0 | 0 | 1 | 3 |
| backend-mapper-xml | 7 | 0 | 7 | 0 | 0 | 0 | 0 | 37 | 64 |
| frontend-view | 8 | 0 | 8 | 0 | 0 | 0 | 0 | 0 | 36 |
| other | 3 | 0 | 3 | 0 | 0 | 0 | 0 | 0 | 18 |
| sql | 5 | 0 | 4 | 0 | 1 | 15 | 0 | 59 | 0 |

## File Hits

| Status | Group | File | style_no | styleNo | style_code | styleCode |
| --- | --- | --- | ---: | ---: | ---: | ---: |
| expected-mixed | sql | `sql/phase23_style_code_compat_migration.sql` | 15 | 0 | 39 | 0 |
| target-only | backend-controller | `ruoyi-admin/src/main/java/com/ruoyi/erp/controller/CheckController.java` | 0 | 0 | 0 | 1 |
| target-only | backend-controller | `ruoyi-admin/src/main/java/com/ruoyi/erp/controller/ProductSerialController.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/Bom.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/Check.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/ProducePlan.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/QcInspection.java` | 0 | 0 | 0 | 5 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/SalesOrder.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/SampleNotice.java` | 0 | 0 | 0 | 6 |
| target-only | backend-domain | `ruoyi-admin/src/main/java/com/ruoyi/erp/domain/SampleTech.java` | 0 | 0 | 0 | 6 |
| target-only | backend-mapper-java | `ruoyi-admin/src/main/java/com/ruoyi/erp/mapper/ProductSerialMapper.java` | 0 | 0 | 1 | 3 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/BomMapper.xml` | 0 | 0 | 6 | 10 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/CheckMapper.xml` | 0 | 0 | 5 | 12 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/ProducePlanMapper.xml` | 0 | 0 | 6 | 10 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/QcInspectionMapper.xml` | 0 | 0 | 3 | 5 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/SalesOrderMapper.xml` | 0 | 0 | 6 | 10 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/SampleNoticeMapper.xml` | 0 | 0 | 6 | 10 |
| target-only | backend-mapper-xml | `ruoyi-admin/src/main/resources/mapper/erp/SampleTechMapper.xml` | 0 | 0 | 5 | 7 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/bom/index.vue` | 0 | 0 | 0 | 1 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/check/index.vue` | 0 | 0 | 0 | 7 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/notice/index.vue` | 0 | 0 | 0 | 1 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/overview/index.vue` | 0 | 0 | 0 | 4 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/plan/index.vue` | 0 | 0 | 0 | 7 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/quality/index.vue` | 0 | 0 | 0 | 2 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/sales/index.vue` | 0 | 0 | 0 | 7 |
| target-only | frontend-view | `ruoyi-ui/src/views/erp/tech/index.vue` | 0 | 0 | 0 | 7 |
| target-only | other | `ruoyi-ui/src/api/demo/cost.js` | 0 | 0 | 0 | 2 |
| target-only | other | `ruoyi-ui/src/views/demo/order/index.vue` | 0 | 0 | 0 | 8 |
| target-only | other | `ruoyi-ui/src/views/demo/style/index.vue` | 0 | 0 | 0 | 8 |
| target-only | sql | `sql/phase15_flexible_schedule.sql` | 0 | 0 | 2 | 0 |
| target-only | sql | `sql/phase20_product_traceability.sql` | 0 | 0 | 10 | 0 |
| target-only | sql | `sql/phase22_style_no_dual_key.sql` | 0 | 0 | 6 | 0 |
| target-only | sql | `sql/sprint_d_quality_invoice.sql` | 0 | 0 | 2 | 0 |

## Suggested Batch Order

1. Migrate `legacy-only` files in low-conflict backend/frontend modules.
2. Resolve actionable `mixed` files before declaring the migration complete.
3. Keep `expected-mixed` files only when they are intentional compatibility bridges.
