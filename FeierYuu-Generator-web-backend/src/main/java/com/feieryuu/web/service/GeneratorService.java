package com.feieryuu.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feieryuu.web.model.dto.generator.GeneratorQueryRequest;
import com.feieryuu.web.model.entity.Generator;
import com.feieryuu.web.model.vo.GeneratorVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author feieryuuli
 * @description 针对表【generator(帖子)】的数据库操作Service
 */
public interface GeneratorService extends IService<Generator> {

    /**
     * 校验
     *
     * @param generator
     * @param add       是否为创建校验
     */
    void validGenerator(Generator generator, boolean add);


    /**
     * 获取查询条件
     *
     * @param generatorQueryRequest
     * @return
     */
    QueryWrapper<Generator> getQueryWrapper(GeneratorQueryRequest generatorQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param generator
     * @param request
     * @return
     */
    GeneratorVO getGeneratorVO(Generator generator, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param generatorPage
     * @param request
     * @return
     */
    Page<GeneratorVO> getGeneratorVOPage(Page<Generator> generatorPage, HttpServletRequest request);
}
