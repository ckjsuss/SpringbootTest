package com.zyzh.service.impl;

import com.zyzh.dao.PZdinfoMapper;
import com.zyzh.entity.PZdinfo;
import com.zyzh.service.PZdinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PZdinfoServiceImpl implements PZdinfoService {
    @Autowired
    private PZdinfoMapper pZdinfoMapper;
    @Override
    public int deleteByPrimaryKey(String a) {
        pZdinfoMapper.deleteByPrimaryKey(a);
        return 0;
    }

    @Override
    public int insert() {
        PZdinfo pZdinfo = new PZdinfo();
        int a = pZdinfoMapper.insert();
        if(a>0){
            throw new RuntimeException();
        }
        pZdinfoMapper.deleteByPrimaryKey("");
        return 0;
    }

    @Override
    public PZdinfo selectByPrimaryKey(String zdCode) {
        return null;
    }

    @Override
    public List<PZdinfo> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(PZdinfo record) {
        return 0;
    }
}
