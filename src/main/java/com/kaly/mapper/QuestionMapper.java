package com.kaly.mapper;

import com.kaly.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,tag,description,creator,gmt_create,gmt_modified) values (#{title},#{tag},#{description},#{creator},#{gmtCreate},#{gmtModified})")
    public void create(Question question);
}
