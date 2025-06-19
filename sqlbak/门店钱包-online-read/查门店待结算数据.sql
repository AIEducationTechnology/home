select * from tb_store_bill where  store_code ='9027' and bill_type !='DAY';

# 查询门店结算金额
SELECT * from tb_store_bill where `status` in(1,3) and  store_code ='6718';
SELECT * from tb_store_bill where `status` in(1,3) and  store_code ='1161' and bill_amount<0;
SELECT * from tb_store_bill where `status` in(1,3) and  store_code ='7189' and bill_date between '2024-10-01' and '2024-11-01' and bill_channel='3';
select * from tb_store_config where store_code='1161';

# update tb_store_bill set bill_amount=bill_amount-0.24-0.38,remark='原有账单金额7.40，合并美团外卖2024-10-31，2024-11-01的负账单金额0.24和0.38' where id=1964607;
# update tb_store_bill set status=2,remark='负账单金额0.24，合并到饿了么2024-09-09一起分账' where id=4481428;
# update tb_store_bill set status=2,remark='负账单金额0.38，合并到饿了么2024-09-09一起分账' where id=4481429;
# update tb_allocate_bill_elm set bill_amount=bill_amount-0.24-0.38,remark='原有账单金额7.40，合并美团外卖2024-10-31，2024-11-01的负账单金额0.24和0.38' where id=160399;
# update tb_allocate_bill_mt set status=2,remark='负账单金额0.24，合并到饿了么2024-09-09一起分账' where id=538825;
# update tb_allocate_bill_mt set status=2,remark='负账单金额0.38，合并到饿了么2024-09-09一起分账' where id=538826;


SELECT * from tb_allocate_bill_elm where `status` in(1,3) and  store_code ='1050' and bill_date between '2024-10-08' and '2024-10-22';

-- 查询所有可以分的账
with stores as (
    select t1.store_code from tb_store_config t1 inner join tb_store_account t2 on t1.store_code=t2.store_code
    where t1.store_status=1 and t2.account_no is not null and t2.account_no!='' and t1.brand_id='1000'
      and t2.create_time<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m-%d')
)
select t1.store_code,sum(t1.bill_amount) aa from tb_store_bill t1,stores t2 where t1.store_code=t2.store_code
and t1.status = 1 and t1.bill_date<DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d')
group by t1.store_code order by aa desc ;

# 处理预警记录
# select id,content from tb_warning_notification where status=-1 order by create_time desc ;
# update tb_warning_notification set status=2,remark='与三方打款一致',operator='13981824848',operation_time=now(),update_time=now()
# where id in(295,285,274,273,271,269,268,262,251,242,234,226,217,176,168,158,149);

select * from tb_allocate_dispute_bill where available_amount>0 order by create_time desc;

with temp as (
    select mt_num,max(settle_date) as settle_date from tb_mt_takeout_settle_info where status='交易失败' group by mt_num
) select t1.mt_num,t1.settle_date,t1.settle_amt,t1.status from tb_mt_takeout_settle_info t1,temp t2
where t1.mt_num=t2.mt_num and t1.settle_date=t2.settle_date and t1.status='交易失败';

select * from tb_store_bill where store_code='A4ER' and status=1 order by bill_date;
select * from tb_store_config where store_code='A4ER';


