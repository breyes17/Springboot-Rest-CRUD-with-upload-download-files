package com.springrest.demo.service;

import java.util.List;

import com.springrest.demo.entity.BasicInfo;

public interface BasicInfoService {

	public void saveBasicInfo(BasicInfo basicInfo);
	public List<BasicInfo> getAllBasicInfo();
	public BasicInfo getBasicInfoById(int id);
	public void deleteBasicInfo(int id);
}
