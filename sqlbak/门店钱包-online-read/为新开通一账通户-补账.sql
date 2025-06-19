-- 补账前查询
-- 快手 channel=8
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_ks where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 16 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;

-- 抖音 channel=4
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_dy where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 4 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;

-- 美团外卖 channel=3
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_mt where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 3 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;

-- 饿了么 channel=5
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_elm where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 5 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;

-- 团购 channel=7
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_mttg where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 11 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;

-- 在线点 channel=6
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_mtzxd where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 12 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;

-- 小程序 channel=1
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_app where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 1 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;

-- 终端 channel=2
-- 待补
SELECT bill_date,sum(bill_amount) from tb_allocate_bill_terminal where deleted=0 and `status` = 1 and bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') GROUP BY bill_date order by bill_date asc;
-- 已补
SELECT billing_date,sum(amount/100) from tb_allocate_record  where allocate_channel = 17 and create_time > '2024-07-08 15:00:00' and brand_id = 1000 GROUP BY billing_date order by billing_date asc;


SELECT * FROM tb_allocate_bill_dy WHERE store_code in('A1VD','1444') AND bill_date='2024-04-13';
SELECT * FROM tb_store_config WHERE store_code in('A1VD','1444')

