package com.zyzh.dao;

import com.zyzh.entity.PZdinfo;
import java.util.List;

public interface PZdinfoMapper {
    int deleteByPrimaryKey(String zdCode);

    int insert(PZdinfo record);

    PZdinfo selectByPrimaryKey(String zdCode);

    List<PZdinfo> selectAll();

    int updateByPrimaryKey(PZdinfo record);
}