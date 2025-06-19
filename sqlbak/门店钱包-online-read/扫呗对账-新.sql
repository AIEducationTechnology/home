-- 扫呗对账
-- 核对总入金
SELECT SUM(settle_amt) from tb_saobei_settle_info where settle_date=DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 2 DAY),'%Y-%m-%d');
SELECT trans_out_no,SUM(trans_amt) from tb_trans_detail where trans_date=DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 2 DAY),'%Y-%m-%d') and brand_id=1000 and channel=1 GROUP BY trans_out_no;

SELECT SUM(settle_amt) from tb_saobei_settle_info where settle_date='2024-07-02';

SELECT * from tb_settlement_billing_deviation_record where bill_date='2024-07-03';

-- 资金归集
SELECT merchant_store_code,merchant_num,settle_amount,app_amount,hll_amount,hs_amount,
settle_amount-IFNULL(app_amount,0)-IFNULL(hll_amount,0)-IFNULL(hs_amount,0) AS '资金归集'
from tb_settlement_billing_deviation_record
where bill_date='2024-07-06' and merchant_store_code is not null and merchant_store_code!='';


-- 找不到门店
SELECT * from tb_settlement_billing_deviation_record where bill_date='2024-06-19' and merchant_store_code is null;
-- 确定门店
SELECT * from tb_store_config_effective where platform_code='814305812001232' and channel=0;
SELECT * from tb_store_config where store_merchant_num='865105812012025';
SELECT * from tb_store_config_effective where store_code='W001';
DELETE from tb_store_config_effective where id in(28404,28406,28408);

-- 确定金额
SELECT
	trade_number,
    terminal_no,
	sum(
		(transaction_received_amount - transaction_commission + refund_amount - refund_commission + other_transaction_received_amount - other_transaction_commission + other_refund_amount - other_refund_commission)/100
	) AS posAmount
FROM
	tb_bill_pos_applet_detail
WHERE
	brand_id = 1000
	AND bill_day = '2025-04-13'
	and trade_number = '858105812012986'
GROUP BY trade_number,terminal_no;

SELECT * from tb_allocate_bill_app where store_code='Z051' order by bill_date desc ;
SELECT * from tb_store_config where store_merchant_num='852105812042966';
select * from tb_trans_detail where channel=1 and remark='二代支付' order by trans_date desc limit 10;




