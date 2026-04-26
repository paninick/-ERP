-- P2-G: 敏感数据加密 — 员工身份证/手机号 AES-128
-- 幂等，可重复执行
-- 应用层解密需配合 MyBatis TypeHandler (代码变更，不在本SQL范围)

SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_employee' AND COLUMN_NAME='id_card_enc');
SET @s1 = IF(@c1=0, 'ALTER TABLE t_erp_employee ADD COLUMN id_card_enc VARBINARY(128) DEFAULT NULL COMMENT ''身份证号密文(AES-128)'' AFTER id_card', 'SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_employee' AND COLUMN_NAME='phone_enc');
SET @s2 = IF(@c2=0, 'ALTER TABLE t_erp_employee ADD COLUMN phone_enc VARBINARY(128) DEFAULT NULL COMMENT ''手机号密文(AES-128)'' AFTER phone', 'SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

-- 迁移现有明文到密文（使用固定密钥，生产环境需替换）
-- SET @key = 'ErpEncryptKey2026';
-- UPDATE t_erp_employee SET id_card_enc = AES_ENCRYPT(id_card, @key) WHERE id_card IS NOT NULL AND id_card_enc IS NULL;
-- UPDATE t_erp_employee SET phone_enc = AES_ENCRYPT(phone, @key) WHERE phone IS NOT NULL AND phone_enc IS NULL;

-- TODO: 生产环境密钥从环境变量/配置中心注入，不能硬编码在SQL中
-- TODO: Java层需添加 EncryptTypeHandler (MyBatis) 实现读写自动加解密
-- TODO: 前端展示需脱敏: 身份证前6后4, 手机号前3后4

SELECT 'P2-G: 加密列已创建 (id_card_enc/phone_enc), 需代码层配合' AS result;
