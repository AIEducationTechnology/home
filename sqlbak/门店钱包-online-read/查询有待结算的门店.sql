
-- 门店待结算金额排序
SELECT store_code,sum(bill_amount) amount from tb_store_bill where `status` in(1,3) and bill_date<date_format(date_sub(now(),INTERVAL 3 DAY ),'%Y-%m-%d')
and store_code not in(SELECT store_code from tb_store_account where brand_id=1000 and account_no is not null and account_no!='')
group by store_code having amount>100 order by amount desc;

# 查询应该分但却没分的账
select t1.store_code,sum(t1.bill_amount) aa from tb_store_bill t1,tb_store_config t2 where t1.store_code=t2.store_code and t2.store_status=1 and t1.status in(1,3)
and exists(
    select 1 from tb_store_account where t1.store_code = store_code and account_no is not null and account_no!='' and account_disabled=1
) and t1.bill_date<date_format(date_sub(now(),INTERVAL 3 DAY ),'%Y-%m-%d') group by t1.store_code order by aa desc ;

-- 负账单
select t1.* from tb_store_bill t1,tb_store_config t2 where t1.store_code=t2.store_code and t2.store_status=1 and t1.status in(1,3)
and exists(
        select 1 from tb_store_account where t1.store_code = store_code and account_no is not null and account_no!='' and account_disabled=1
    ) and t1.bill_date<date_format(date_sub(now(),INTERVAL 3 DAY ),'%Y-%m-%d');

SELECT * from tb_store_bill where status=1 and store_code='A4ZZ';
select * from tb_elm_settle_info where settle_shop_id='510223793' and trade_type='支出' and settle_amt>1000;

select t1.* from tb_store_bill t1,tb_store_config t2 where t1.store_code=t2.store_code and t2.store_status=1 and t1.status in(1,3)
and t1.store_code='3009'
and exists(
        select 1 from tb_store_account where t1.store_code = store_code and account_no is not null and account_no!='' and account_disabled=1
    ) and t1.bill_date<date_format(date_sub(now(),INTERVAL 3 DAY ),'%Y-%m-%d');

select store_code,bill_channel,platform_code,bill_date,bill_amount,status,remark from tb_store_bill where deleted=0 and bill_amount<0 and status in(1,3) order by bill_date;

select * from tb_store_bill where store_code='A42N' and bill_amount<0;
select * from tb_allocate_bill_mt where store_code='A42N' and bill_amount<0;
select * from tb_allocate_dispute_bill where channel=3 and available_amount != 0 order by bill_date desc limit 100;

select * from tb_store_config where store_code='A3DF';
select * from tb_store_bill where store_code='A3DF' and bill_channel='4' and bill_date='2025-06-01';

-- 7265939439703033896