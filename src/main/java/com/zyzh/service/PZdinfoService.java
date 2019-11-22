package com.zyzh.service;

import com.zyzh.entity.PZdinfo;

import java.util.List;

public interface PZdinfoService {
    int deleteByPrimaryKey(String zdCode);

    int insert();

    PZdinfo selectByPrimaryKey(String zdCode);

    List<PZdinfo> selectAll();

    int updateByPrimaryKey(PZdinfo record);
}
