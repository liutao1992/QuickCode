package com.example.generated.dao;

import com.example.generated.entity.ZhagXcSxtxx;
import com.github.chengyuxing.common.DataRow;
import com.github.chengyuxing.sql.Args;
import com.github.chengyuxing.sql.BakiDao;
import com.github.chengyuxing.sql.PagedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 摄像头基本信息 DAO层 - 使用Baki方式
 * 
 * @author QuickCode Generator
 * @date 2025-08-13 15:14:04
 */
@Repository
public class ZhagXcSxtxxDao {
    
    @Autowired
    private BakiDao bakiDao;
    
    /**
     * 查询所有记录
     */
    public List<ZhagXcSxtxx> findAll() {
        return bakiDao.query("&zhagXcSxtxx.findAll").entities(ZhagXcSxtxx.class);
    }
    
    /**
     * 根据ID查询记录
     */
    public ZhagXcSxtxx findById(String id) {
        return bakiDao.query("&zhagXcSxtxx.findById")
                .arg("id", id).findFirstEntity(ZhagXcSxtxx.class);
    }
    
    /**
     * 插入记录
     */
    public int insert(ZhagXcSxtxx zhagXcSxtxx) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", zhagXcSxtxx.getId());
        params.put("bh", zhagXcSxtxx.getBh());
        params.put("mc", zhagXcSxtxx.getMc());
        params.put("bmmc", zhagXcSxtxx.getBmmc());
        params.put("bmbh", zhagXcSxtxx.getBmbh());
        params.put("csmc", zhagXcSxtxx.getCsmc());
        params.put("sxtbh", zhagXcSxtxx.getSxtbh());
        params.put("sxtssmubm", zhagXcSxtxx.getSxtssmubm());
        params.put("jlbz", zhagXcSxtxx.getJlbz());
        params.put("sort", zhagXcSxtxx.getSort());
        params.put("lx", zhagXcSxtxx.getLx());
        
        return bakiDao.of("&zhagXcSxtxx.insert").execute(params).getInt(0);
    }
    
    /**
     * 更新记录
     */
    public int update(ZhagXcSxtxx zhagXcSxtxx) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", zhagXcSxtxx.getId());
        params.put("bh", zhagXcSxtxx.getBh());
        params.put("mc", zhagXcSxtxx.getMc());
        params.put("bmmc", zhagXcSxtxx.getBmmc());
        params.put("bmbh", zhagXcSxtxx.getBmbh());
        params.put("csmc", zhagXcSxtxx.getCsmc());
        params.put("sxtbh", zhagXcSxtxx.getSxtbh());
        params.put("sxtssmubm", zhagXcSxtxx.getSxtssmubm());
        params.put("jlbz", zhagXcSxtxx.getJlbz());
        params.put("sort", zhagXcSxtxx.getSort());
        params.put("lx", zhagXcSxtxx.getLx());
        
        return bakiDao.of("&zhagXcSxtxx.update").execute(params).getInt(0);
    }
    
    /**
     * 删除记录
     */
    public int deleteById(String id) {
        return bakiDao.of("&zhagXcSxtxx.deleteById").execute(Args.of(id, id)).getInt(0);
    }
    
    /**
     * 分页查询
     */
    public PagedResource<DataRow> findByPage(int page, int size) {
        return bakiDao.query("&zhagXcSxtxx.findByPage")
                .pageable(page, size).collect();
    }
    

    /**
     * 根据条件查询
     */
    public List<ZhagXcSxtxx> findByCondition(Map<String, Object> conditions) {
        StringBuilder sql = new StringBuilder("SELECT * FROM zhag_xc_sxtxx WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        
        conditions.forEach((key, value) -> {
            if (value != null) {
                sql.append(" AND ").append(key).append(" = :").append(key);
                params.put(key, value);
            }
        });
        
        return bakiDao.query(sql.toString())
                .args(params)
                .entities(ZhagXcSxtxx.class);
    }
    
    /**
     * 批量插入
     */
    public int batchInsert(List<ZhagXcSxtxx> zhagXcSxtxxList) {
        return 0;
    }
}