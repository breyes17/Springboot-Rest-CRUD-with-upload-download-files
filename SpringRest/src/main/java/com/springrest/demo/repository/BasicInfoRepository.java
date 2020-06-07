package com.springrest.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springrest.demo.entity.BasicInfo;

public interface BasicInfoRepository extends JpaRepository<BasicInfo, Integer> {

}
