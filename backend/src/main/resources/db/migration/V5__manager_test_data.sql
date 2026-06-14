INSERT INTO MANG_TBL (
    MANAGER_CD,
    MANAGER_CHRCD,
    MANAGER_NM,
    MANAGER_CUST_ID,
    MANAGER_PNO,
    MANAGER_JOB,
    MANAGER_RMK,
    MANAGER_MAIL,
    MANAGER_CRD_USR,
    MANAGER_CRD_DT,
    MANAGER_UPD_USR,
    MANAGER_UPD_DT
)
SELECT
    M.MANAGER_CD,
    M.MANAGER_CHRCD,
    M.MANAGER_NM,
    C.CUST_ID,
    M.MANAGER_PNO,
    M.MANAGER_JOB,
    M.MANAGER_RMK,
    M.MANAGER_MAIL,
    'system',
    NOW(),
    'system',
    NOW()
FROM (
    SELECT 'M0001' MANAGER_CD, '01' MANAGER_CHRCD, '佐藤 健太' MANAGER_NM,
           'T1010000000002' CUST_INVOICE_NO, '090-1000-0001' MANAGER_PNO,
           '01' MANAGER_JOB, '営業窓口' MANAGER_RMK, 'kenta.sato@sakura-tech.example.jp' MANAGER_MAIL
    UNION ALL
    SELECT 'M0001', '02', '鈴木 美咲', 'T1010000000002', '090-1000-0002',
           '02', '契約担当', 'misaki.suzuki@sakura-tech.example.jp'
    UNION ALL
    SELECT 'M0002', '01', '高橋 翔太', 'T1010000000003', '090-1000-0003',
           '01', '営業窓口', 'shota.takahashi@jbs.example.jp'
    UNION ALL
    SELECT 'M0002', '02', '田中 彩', 'T1010000000003', '090-1000-0004',
           '03', '技術窓口', 'aya.tanaka@jbs.example.jp'
    UNION ALL
    SELECT 'M0003', '01', '伊藤 大輔', 'T1010000000004', '090-1000-0005',
           '01', '営業窓口', 'daisuke.ito@next-wave.example.jp'
    UNION ALL
    SELECT 'M0003', '02', '渡辺 優', 'T1010000000004', '090-1000-0006',
           '02', '請求担当', 'yu.watanabe@next-wave.example.jp'
    UNION ALL
    SELECT 'M0004', '01', '山本 直樹', 'T1010000000005', '090-1000-0007',
           '01', '営業窓口', 'naoki.yamamoto@hokuto-data.example.jp'
    UNION ALL
    SELECT 'M0004', '02', '中村 愛', 'T1010000000005', '090-1000-0008',
           '03', '技術窓口', 'ai.nakamura@hokuto-data.example.jp'
    UNION ALL
    SELECT 'M0005', '01', '小林 拓也', 'T1010000000006', '090-1000-0009',
           '01', '営業窓口', 'takuya.kobayashi@kansai-cloud.example.jp'
    UNION ALL
    SELECT 'M0005', '02', '加藤 玲奈', 'T1010000000006', '090-1000-0010',
           '02', '契約担当', 'rena.kato@kansai-cloud.example.jp'
    UNION ALL
    SELECT 'M0006', '01', '吉田 誠', 'T1010000000007', '090-1000-0011',
           '01', '営業窓口', 'makoto.yoshida@nagoya-soft.example.jp'
    UNION ALL
    SELECT 'M0006', '02', '松本 真由', 'T1010000000007', '090-1000-0012',
           '03', '開発担当', 'mayu.matsumoto@nagoya-soft.example.jp'
    UNION ALL
    SELECT 'M0007', '01', '井上 健一', 'T1010000000008', '090-1000-0013',
           '01', '営業窓口', 'kenichi.inoue@setouchi-infra.example.jp'
    UNION ALL
    SELECT 'M0007', '02', '清水 由美', 'T1010000000008', '090-1000-0014',
           '02', '請求担当', 'yumi.shimizu@setouchi-infra.example.jp'
    UNION ALL
    SELECT 'M0008', '01', '林 大輔', 'T1010000000009', '090-1000-0015',
           '01', '営業窓口', 'daisuke.hayashi@kyushu-dp.example.jp'
    UNION ALL
    SELECT 'M0008', '02', '斎藤 愛', 'T1010000000009', '090-1000-0016',
           '03', '技術窓口', 'ai.saito@kyushu-dp.example.jp'
    UNION ALL
    SELECT 'M0009', '01', '山田 翔', 'T1010000000010', '090-1000-0017',
           '01', '営業窓口', 'sho.yamada@mirai-ai.example.jp'
    UNION ALL
    SELECT 'M0009', '02', '佐々木 彩', 'T1010000000010', '090-1000-0018',
           '03', '研究開発担当', 'aya.sasaki@mirai-ai.example.jp'
    UNION ALL
    SELECT 'M0010', '01', '山口 健太', 'T1010000000011', '090-1000-0019',
           '01', '営業窓口', 'kenta.yamaguchi@green-network.example.jp'
    UNION ALL
    SELECT 'M0010', '02', '松田 美咲', 'T1010000000011', '090-1000-0020',
           '02', '総務担当', 'misaki.matsuda@green-network.example.jp'
    UNION ALL
    SELECT 'M0011', '01', '石川 拓海', 'T1010000000012', '090-1000-0021',
           '01', '営業窓口', 'takumi.ishikawa@chiba-test.example.jp'
    UNION ALL
    SELECT 'M0011', '02', '森 優子', 'T1010000000012', '090-1000-0022',
           '03', '品質管理担当', 'yuko.mori@chiba-test.example.jp'
    UNION ALL
    SELECT 'M0012', '01', '池田 直人', 'T1010000000013', '090-1000-0023',
           '01', '営業窓口', 'naoto.ikeda@tohoku-eng.example.jp'
    UNION ALL
    SELECT 'M0012', '02', '橋本 愛美', 'T1010000000013', '090-1000-0024',
           '02', '契約担当', 'manami.hashimoto@tohoku-eng.example.jp'
    UNION ALL
    SELECT 'M0013', '01', '阿部 健', 'T1010000000014', '090-1000-0025',
           '01', '営業窓口', 'ken.abe@ocean-pmo.example.jp'
    UNION ALL
    SELECT 'M0013', '02', '藤田 玲奈', 'T1010000000014', '090-1000-0026',
           '03', 'PMO担当', 'rena.fujita@ocean-pmo.example.jp'
    UNION ALL
    SELECT 'M0014', '01', '岡田 誠司', 'T1010000000015', '090-1000-0027',
           '01', '営業窓口', 'seiji.okada@sunrise-consult.example.jp'
    UNION ALL
    SELECT 'M0014', '02', '長谷川 彩香', 'T1010000000015', '090-1000-0028',
           '02', '管理担当', 'ayaka.hasegawa@sunrise-consult.example.jp'
    UNION ALL
    SELECT 'M0015', '01', '村上 大地', 'T1010000000002', '090-1000-0029',
           '03', 'クラウド担当', 'daichi.murakami@sakura-tech.example.jp'
    UNION ALL
    SELECT 'M0015', '02', '近藤 結衣', 'T1010000000003', '090-1000-0030',
           '03', 'システム担当', 'yui.kondo@jbs.example.jp'
) M
INNER JOIN CUST_TBL C
    ON C.CUST_INVOICE_NO = M.CUST_INVOICE_NO;
