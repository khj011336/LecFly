package com.LECFLY.LF.model.dao.inf.cart;


import com.LECFLY.LF.model.vo.cart.TicketVO;
import com.LECFLY.LF.model.vo.creator.KitVO;

public interface IGoodsDAO {
	KitVO getOneKitById(int id);	
	TicketVO getOneTicketById(int id);
}
