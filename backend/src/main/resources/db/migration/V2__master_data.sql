INSERT INTO COM_TBL
(COM_ID, COM_CD, COM_CONTENT_1,
 COM_CRD_USR, COM_CRD_DT,
 COM_UPD_USR, COM_UPD_DT)
VALUES
('STAFF_CLS','01','正社員','admin',NOW(),'admin',NOW()),
('STAFF_CLS','02','契約社員','admin',NOW(),'admin',NOW()),
('STAFF_CLS','03','協力会社','admin',NOW(),'admin',NOW()),
('STAFF_CLS','04','個人事業主','admin',NOW(),'admin',NOW()),

('STAFF_SALESSTATUS','01','稼働中','admin',NOW(),'admin',NOW()),
('STAFF_SALESSTATUS','02','待機中','admin',NOW(),'admin',NOW()),
('STAFF_SALESSTATUS','03','休職中','admin',NOW(),'admin',NOW()),
('STAFF_SALESSTATUS','04','退職','admin',NOW(),'admin',NOW()),
('STAFF_SALESSTATUS','05','退職済','admin',NOW(),'admin',NOW());