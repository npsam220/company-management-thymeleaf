CREATE TABLE `BANK_TBL` (
  `BANK_CD` varchar(4) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '銀行コード',
  `BANK_CHRCD` varchar(3) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '銀行子コード',
  `BANK_CLSFLG` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '銀行区分',
  `BANK_NM` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '銀行名',
  `BANK_BRANCHNM` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支店名',
  `BANK_BRANCHCD` varchar(3) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支店番号',
  `BANK_ACCOUNTNO` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '口座番号',
  `BANK_NOMINEE` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名義人',
  `BANK_DEPOSITTYPE` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '預金種別',
  `BANK_CRD_USR` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BANK_CRD_DT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BANK_UPD_USR` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BANK_UPD_DT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`BANK_CD`, `BANK_CHRCD`),
  UNIQUE KEY `UK_BANK_BRANCH` (`BANK_CD`, `BANK_BRANCHCD`)
);

INSERT INTO `BANK_TBL` (
  `BANK_CD`, `BANK_CHRCD`, `BANK_CLSFLG`, `BANK_NM`,
  `BANK_BRANCHNM`, `BANK_BRANCHCD`, `BANK_ACCOUNTNO`,
  `BANK_NOMINEE`, `BANK_DEPOSITTYPE`,
  `BANK_CRD_USR`, `BANK_UPD_USR`
) VALUES
('0001', '001', '0', 'みずほ銀行', '東京営業部', '001', '1234567', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0005', '001', '0', '三菱UFJ銀行', '本店', '001', '2345678', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0005', '002', '0', '三菱UFJ銀行', '新宿支店', '341', '3456789', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0009', '001', '0', '三井住友銀行', '本店営業部', '200', '4567890', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0010', '001', '0', 'りそな銀行', '東京営業部', '300', '5678901', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0033', '001', '0', 'PayPay銀行', 'ビジネス営業部', '005', '6789012', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0036', '001', '0', '楽天銀行', '第一営業支店', '251', '7890123', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0177', '001', '0', '福岡銀行', '本店営業部', '100', '8901234', 'キュウシュウデジタルパートナーズ（カ', '01', 'system', 'system'),
('0177', '002', '0', '福岡銀行', '天神町支店', '601', '9012345', 'キュウシュウデジタルパートナーズ（カ', '01', 'system', 'system'),
('0182', '001', '0', '肥後銀行', '本店営業部', '101', '1122334', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0190', '001', '0', '西日本シティ銀行', '本店営業部', '001', '2233445', 'カブシキガイシャサンプル', '01', 'system', 'system'),
('0190', '002', '0', '西日本シティ銀行', '博多支店', '208', '3344556', 'カブシキガイシャサンプル', '02', 'system', 'system');
