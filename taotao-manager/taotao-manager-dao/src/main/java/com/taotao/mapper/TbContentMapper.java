package com.taotao.mapper;

import com.taotao.pojo.TbContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbContentMapper {

    List<TbContent> findTbContentByPage(@Param("id") Long id,@Param("start") Integer start,@Param("limit") Integer limit);

    int findTbContentByParentIdAndCount(Long id);

    int deleteContentByCategoryId(@Param("tbContents") List<TbContent> tbContents);

    void addContent(TbContent tbContent);
}