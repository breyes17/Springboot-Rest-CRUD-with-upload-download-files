package com.springrest.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.demo.entity.BasicInfo;
import com.springrest.demo.repository.BasicInfoRepository;

@Service
public class BasicInfoServiceImpl implements BasicInfoService {

	@Autowired
	private BasicInfoRepository basicInfoRepo;
	
	@Override
	public void saveBasicInfo(BasicInfo basicInfo) {
		basicInfoRepo.save(basicInfo);
	}

	@Override
	public List<BasicInfo> getAllBasicInfo() {
		List<BasicInfo> basicInfoList = new ArrayList<>();
		basicInfoList = basicInfoRepo.findAll();
		return basicInfoList;
	}

	@Override
	public BasicInfo getBasicInfoById(int id) {
		return basicInfoRepo.findById(id).orElseThrow(() -> new RuntimeException());
	}

	@Override
	public void deleteBasicInfo(int id) {
		basicInfoRepo.deleteById(id);
	}

}
