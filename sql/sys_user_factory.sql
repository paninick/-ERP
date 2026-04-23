-- ----------------------------
-- Table structure for sys_user_factory
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_factory`;
CREATE TABLE `sys_user_factory`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `factory_id` bigint(20) NOT NULL COMMENT '工厂ID',
  PRIMARY KEY (`user_id`, `factory_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和工厂关联表' ROW_FORMAT = Dynamic;
