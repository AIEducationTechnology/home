select id,code,id from account_statement_prod.acc_client where code ='2043';

select one_account_id,sum(amount) from account_trade_record where bill_date>='2025-04-01' and create_by='一账通' and income_type=2 group by one_account_id;

select bill_date,sum(amount) from account_trade_record where one_account_id='A6JE' and bill_date >=  '2025-03-01' and bill_date<'2025-04-01' and create_by='一账通' group by bill_date order by bill_date;
select * from account_trade_record where one_account_id='37220' and bill_date='2025-03-10' and create_by='一账通' and income_type=1;


select concat('\'',transaction_no,'\',') from account_trade_record where bill_date='2024-07-03' and type='17';
