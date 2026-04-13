package com.ruoyi.flowable.mapper;

import java.util.List;
import com.ruoyi.flowable.domain.Form;

public interface FormMapper {
    public List<Form> selectFormList(Form form);
    
    public Form selectFormById(Long id);
    
    public int insertForm(Form form);
    
    public int updateForm(Form form);
    
    public int deleteFormById(Long id);
    
    public int deleteFormByIds(Long[] ids);
}
