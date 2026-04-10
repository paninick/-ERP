-- 重新创建数据库并导入完整数据
DROP DATABASE IF EXISTS `ry-vue`;
CREATE DATABASE `ry-vue` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ry-vue`;

-- 现在通过 source 命令导入完整的 SQL 文件
