# 一账通
SELECT
    sst.num AS 门店四位编码,
	ssa.account_no AS 一账通账户,
	ssa.secondary_account AS 一账通二级账户,
	ssa.shop_id AS 门店id

FROM
	sc_shop_account  ssa LEFT JOIN sc_shop_attach sst ON ssa.shop_id = sst.shop_id
WHERE
	channel_type = 'ONE_ACCOUNT_COMMUNICATION'
	AND ssa.open_account_status =3
	AND ssa.dr = 0;
--

# 扫呗商户号
-- SELECT
-- 	ssm.merchant_no AS 扫呗商户号,
-- 	merchant_channel_no AS 渠道,
-- 	ssa.num AS 门店四位编码,
-- 	pay_attach ->"$.terminalNum" AS 终端id,
-- 	pay_attach ->"$.token" AS 终端token
-- FROM
-- 	sc_shop_merchant  ssm LEFT JOIN sc_shop_attach ssa ON ssm.shop_id = ssa.shop_id WHERE
--
-- 	ssm.dr = 0

# 	门店三方渠道映射码
SELECT
    ssa.num AS 门店四位编码,
    ssp.pos_code AS 渠道编码,
    ssp.third_party_shop_code AS 门店对应三方平台编码,
    ssp.third_party_shop_platform_code AS 平台上该门店id
FROM
    sc_shop_pos ssp LEFT JOIN sc_shop_attach ssa ON ssp.shop_id = ssa.shop_id
WHERE ssp.third_party_shop_platform_code = '9949217';
#         ssp.dr = 0 AND ssp.channel_id IN (25, 2,1,26,1310048670505684060,1211809272448902759,1872387220826508728);


SELECT * FROM sc_channel WHERE id IN (25, 2,1,26,1310048670505684060,1211809272448902759,1872387220826508728);

-- 查询门店状态 1001 未营业， 1002 试营业， 1003 营业， 1004 暂停营业， 1005 已闭店
select num,manage_status  from cloud_cube_shop.sc_shop_attach ssa where num in ('A208');

-- 查询支付宝编号
SELECT
    ssa.num AS 门店四位编码,
    ssp.third_party_shop_code AS 门店对应三方平台编码
FROM
    sc_shop_pos ssp LEFT JOIN sc_shop_attach ssa ON ssp.shop_id = ssa.shop_id
WHERE ssp.channel_id = '1211809272448902759'
and ssp.third_party_shop_code in('2024080500502007000050660294');

SELECT
    c.name '门店名称',
    c.code '门店中台编码',
    a.num '门店4位编码',
    c.status,
    s.id,
    merchant_channel_no '渠道',
    merchant_name '商户名称',
    merchant_no '商户号',
    merchant_alias '商户简称',
    pay_channel_no '经营渠道[小程序、pos、海石]'
FROM
    `sc_shop_merchant` s
        LEFT JOIN sc_shop c on s.shop_id = c.id
        LEFT JOIN sc_shop_attach a on c.id = a.shop_id
where s.dr=0 and s.tenant_id=1211809270100000000 and c.dr=0 and c.tenant_id=1211809270100000000 and a.dr=0 and a.tenant_id=1211809270100000000
  and s.merchant_channel_no='ONE_ACCOUNT_COMMUNICATION' and merchant_no='5006510003047180369';


SELECT
    ssa.num AS 门店四位编码,
#     ssp.third_party_shop_code AS 门店对应三方平台编码
ssp.*
FROM
    sc_shop_pos ssp LEFT JOIN sc_shop_attach ssa ON ssp.shop_id = ssa.shop_id
WHERE ssp.dr = 0 AND ssa.num='A65Y';

select left(name,4) from sc_shop where type = 3 and status='NORMAL';
select * from sc_shop_pos_history where shop_id = 1390575350403648602;

select * from cloud_cube_member.member_info;