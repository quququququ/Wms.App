package com.wms.newwmsapp.tool;

public class Constants {

    static String URL_Base_Update = "http://wmsserver.your56.com:8888/wmsserver/";//更新的服务器
	static String URL_Base= "http://yaslb.your56.com:8888/wmsserver/";//正式服务器

    //    	static String URL_Base= "http://192.168.1.166/wmsservice/";//测试服务器
//    static String URL_Base = "http://116.236.124.150:8000/wmsservice/";//测试服务器
//    static String URL_Base = "http://dev.your56.com:6666/wmsservice/";//新测试服务器

    public static String URL_HOST = URL_Base + "wmsrf.svc/";

    public static String URL_DOWNLOAD = URL_Base_Update + "DownLoad/newWMSApp.apk";//正式下载地址


    public static String url_AppUpdate = URL_HOST + "AppUpdate";//校验版本
    public static String url_login = URL_HOST + "verify";//登陆接口
    public static String url_GetInstockCheckForm = URL_HOST + "GetInstockCheckForm";//收货单列表
    public static String url_GetInstockDetail = URL_HOST + "GetInstockDetail";//收货明细列表
    public static String url_GetGoodsDetailByCode = URL_HOST + "GetGoodsDetailByCode";//收货明细
    public static String url_GetAllQuality = URL_HOST + "GetAllQuality";//指定货品可选的品质列表
    public static String url_GetAllUnityByGoodsCode = URL_HOST + "GetAllUnityByGoodsCode";//指定货品可选的单位列表
    public static String url_SaveAndCommitInStockCheck = URL_HOST + "SaveAndCommitInStockCheck";//收货单提交
    public static String url_GetInStockPutaway = URL_HOST + "GetInStockPutaway";//获取上架单列表
    public static String url_GetInStockPutAwayDetail = URL_HOST + "GetInStockPutAwayDetail";//获取上架单明细
    public static String url_GetPosAndArea = URL_HOST + "GetPosAndArea";//获取货位信息
    public static String url_SaveInStockPutAway = URL_HOST + "SaveInStockPutAway";//上架保存
    public static String url_InStockPutawayConfirm = URL_HOST + "InStockPutawayConfirm";//上架提交
    public static String url_InStockPutawayCancel = URL_HOST + "InStockPutawayCancel";//上架取消
    public static String url_GetOutStockPickConfirm = URL_HOST + "GetOutStockPickConfirm";//分拣确认
    public static String url_GetOutStockPickConfirmDetailByCode = URL_HOST + "GetOutStockPickConfirmDetailByCode";//分拣确认明细
    public static String url_OutStockPickConfirmUpdate = URL_HOST + "OutStockPickConfirmUpdate";//保存分拣确认明细
    public static String url_SubmitOutStockPickConfirm = URL_HOST + "SubmitOutStockPickConfirm";//提交分拣确认
    public static String url_GetOutStockConfirm = URL_HOST + "GetOutStockConfirm";//出库确认
    public static String url_GetOutStockCheckDetailByCode = URL_HOST + "GetOutStockCheckDetailByCode";//出库确认明细
    public static String url_OutStockCheckAudit = URL_HOST + "OutStockCheckAudit";//出库确认撤销
    public static String url_OutStockCheckDetailSave = URL_HOST + "OutStockCheckDetailSave";//保存出库确认
    public static String url_WavePickupConfirm = URL_HOST + "GetWavePickTaskList";//获取波次分拣单
    public static String url_WavePickupConfirmDetail = URL_HOST + "GetWavePickDetail";//获取波次分拣单明细
    public static String url_SubmitWavePickupConfirm = URL_HOST + "WavePickSubmit";//提交波次分拣单
    public static String url_GetWavePickConfirm = URL_HOST + "GetWavePickConfirm";//获取播种明细
    public static String url_WavePickConfirmSubmit = URL_HOST + "WavePickConfirmSubmit";//提交播种
    public static String url_GetOutStockCheckByWaybillOrCode = URL_HOST + "GetOutStockCheckByWaybillOrCode";//PDA出库确认
    public static String url_OutStockCheckAuditForRF = URL_HOST + "OutStockCheckAuditForRF";//PDA出库确认提交
    public static String url_GetWavePickDetailForSortNo = URL_HOST + "GetWavePickDetailForSortNo";//获取播种分拣明细
    public static String url_StockinSearch = URL_HOST + "StockinSearch";//库内查询
    public static String url_StockCheckTaskByUserCode = URL_HOST + "GetStockCheckTaskByUserCode";//点击盘点任务，进来之后 系统获取盘点任务
    public static String url_GetStockCheckTaskByUCodeAndPos = URL_HOST + "GetStockCheckTaskByUCodeAndPos";//任务进详细页面
    public static String url_GetGoodsInfoByParam = URL_HOST + "GetGoodsInfoByParam";//查询商品
    public static String url_PostAuditStockCheckTask = URL_HOST + "AuditStockCheckTask";//提交新增商品
    public static String url_GetWavePickConfirmForSowing = URL_HOST + "GetWavePickConfirmForSowing";//获取转仓播种明细
    public static String url_GetRunWavePickConfirmForSowing = URL_HOST + "RunWavePickConfirmForSowing";
    public static String url_ResetWavePickConfirmForSowing = URL_HOST + "ResetWavePickConfirmForSowing"; //重播
    public static String url_GetWavePickConfirmDetailForSowing = URL_HOST + "GetWavePickConfirmDetailForSowing"; //查询剩余
    public static String url_GetWaveOutStockPickBindPlate = URL_HOST + "WaveOutStockPickBindPlate"; //转仓装车
    public static String url_GetStockTransferGetOutGoodsPos = URL_HOST + "StockTransferGetOutGoodsPos?"; //扫描的货位
    public static String url_GetStockTransferGetOutSubmit = URL_HOST + "StockTransferGetOutSubmit"; //提交下架
    public static String url_GetStockTransferGetPutGoodsPos = URL_HOST + "StockTransferGetPutGoodsPos?"; //上架扫描货位
    public static String url_GetStockTransferPutAwayCheck = URL_HOST + "StockTransferPutAwayCheck?"; //上架扫描每个商品都要验证的接口
    public static String url_GetStockTransferPutAway = URL_HOST + "StockTransferPutAway?"; //上架扫描每个商品都要验证的接口
    public static String url_GetWavePickClaimList = URL_HOST + "GetWavePickClaimList?"; //波次分拣确认的任务
    public static String url_WavePickClaim = URL_HOST + "WavePickClaim?"; //波次认领
    public static String url_GetWavePickCompleteList = URL_HOST + "GetWavePickCompleteList?"; //已分拣任务
}
