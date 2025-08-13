package com.example.generated.service;

import com.example.generated.entity.ZhagXcSxtxx;
import com.example.generated.dao.ZhagXcSxtxxDao;
import com.github.chengyuxing.common.DataRow;
import com.github.chengyuxing.sql.PagedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 摄像头基本信息 服务层
 * 
 * @author QuickCode Generator
 * @date 2025-08-13 15:15:53
 */
@Service
public class ZhagXcSxtxxService {
    
    @Autowired
    private ZhagXcSxtxxDao zhagXcSxtxxDao;
    
    /**
     * 查询所有记录
     */
    public List<ZhagXcSxtxx> findAll() {
        return zhagXcSxtxxDao.findAll();
    }
    
    /**
     * 根据ID查询记录
     */
    public ZhagXcSxtxx findById(String id) {
        return zhagXcSxtxxDao.findById(id);
    }
    
    /**
     * 保存记录
     */
    public boolean save(ZhagXcSxtxx zhagXcSxtxx) {
        if (zhagXcSxtxx.getId() == null) {
            // 新增
            return zhagXcSxtxxDao.insert(zhagXcSxtxx) > 0;
        } else {
            // 更新
            return zhagXcSxtxxDao.update(zhagXcSxtxx) > 0;
        }
    }
    
    /**
     * 删除记录
     */
    public boolean deleteById(String id) {
        return zhagXcSxtxxDao.deleteById(id) > 0;
    }
    
    /**
     * 分页查询
     */
    public PagedResource<DataRow> findByPage(int page, int size) {
        return zhagXcSxtxxDao.findByPage(page, size);
    }
}