package com.LECFLY.LF.service.inf.payhistory;

import java.util.List;
import java.util.Map;

import com.LECFLY.LF.model.vo.admin.PayHistoryVO;


public interface IPayHistorySVC {

	Map<String, Object> showOrderProc(int mbId, int ticName);

	Map<String, Object> insertPayHis(int mbId, String uuid, int payWay, int couponId);
	
}
