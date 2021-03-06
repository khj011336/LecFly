package com.LECFLY.LF.service.impl.cscenter;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LECFLY.LF.model.dao.inf.cscenter.INoticeDAO;
import com.LECFLY.LF.model.vo.cscenter.NoticeVO;
import com.LECFLY.LF.service.impl.creator.FileSVCImpl;
import com.LECFLY.LF.service.inf.cscenter.INoticeSVC;

@Service
public class NoticeSVCImpl implements INoticeSVC{
	@Autowired
	private INoticeDAO ntDao;
	@Autowired
	FileSVCImpl fileSVC;
	
	@Override
	public NoticeVO selectOneNotice(int id) {
		NoticeVO nt = ntDao.selectOneNotice(id);
		if( nt != null ) {
			if(ntDao.increaseReadCount(id))
				return nt;
		}
		return nt;
	}

	
	@Override
	public boolean increaseReadCount(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<NoticeVO> showAllNotices() {
		return ntDao.showAllNotices();
	}
	@Override
	public List<NoticeVO> showAllCreatorNotices() {
		return ntDao.showAllCreatorNotices();
	}

	@Override
	public List<NoticeVO> showAllNotices(boolean order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoticeVO> showAllNotices(int pn) {
		int offset = (pn-1) * PAGE_SIZE;
		List<NoticeVO>ntList = ntDao.showAllNotices(offset, PAGE_SIZE);
		System.out.println("ntsvc: pn "+ pn +  " notice = " + ntList.size());
		return ntList;
	}

	@Override
	public int checkMaxPageNumber() {
		int totalRecords = ntDao.checkNumberOfNotices();
		int maxPg = totalRecords / PAGE_SIZE + (totalRecords % PAGE_SIZE == 0 ? 0 : 1);
		return maxPg;
	}
	@Override
	public List<NoticeVO> showAllCreatorNotices(int pn) {
		int offset = (pn-1) * PAGE_SIZE;
		List<NoticeVO>ntList = ntDao.showAllCreatorNotices(offset, PAGE_SIZE);
		System.out.println("ntsvc: pn "+ pn +  " notice = " + ntList.size());
		return ntList;
	}

	@Override
	public int checkMaxCreatorPageNumber() {
		int totalRecords = ntDao.checkNumberOfCreatorNotices();
		int maxPg = totalRecords / PAGE_SIZE + (totalRecords % PAGE_SIZE == 0 ? 0 : 1);
		return maxPg;
	}
	
}
