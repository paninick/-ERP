package com.ruoyi.flowable.service;

import java.util.List;
import com.ruoyi.flowable.domain.Form;

public interface IFormService {
    public List<Form> selectFormList(Form form);
    
    public Form selectFormById(Long id);
    
    public int insertForm(Form form);
    
    public int updateForm(Form form);
    
    public int deleteFormByIds(Long[] ids);
    
    public int deleteFormById(Long id);
}
