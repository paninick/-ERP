package com.ruoyi.flowable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.flowable.domain.Form;
import com.ruoyi.flowable.mapper.FormMapper;
import com.ruoyi.flowable.service.IFormService;

@Service
public class FormServiceImpl implements IFormService {
    
    @Autowired
    private FormMapper formMapper;

    @Override
    public List<Form> selectFormList(Form form) {
        return formMapper.selectFormList(form);
    }

    @Override
    public Form selectFormById(Long id) {
        return formMapper.selectFormById(id);
    }

    @Override
    public int insertForm(Form form) {
        return formMapper.insertForm(form);
    }

    @Override
    public int updateForm(Form form) {
        return formMapper.updateForm(form);
    }

    @Override
    public int deleteFormByIds(Long[] ids) {
        return formMapper.deleteFormByIds(ids);
    }

    @Override
    public int deleteFormById(Long id) {
        return formMapper.deleteFormById(id);
    }
}
