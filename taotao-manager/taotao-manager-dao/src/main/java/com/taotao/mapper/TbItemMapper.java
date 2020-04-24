package com.taotao.mapper;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCatResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface TbItemMapper {
    /**
     * 根据商品id查询tbItem表中的商品信息
     * @param itemId
     * @return
     */
    TbItem findTbItemById(Long itemId);

    /**
     * 查询tbItem表中的所有商品总数据数
     * @return
     */
    Integer findTbItemsCount();

    /**
     * 查询tbItem表，对商品进行分页展示
     * @param start 每页商品开始的索引
     * @param size  每页显示的商品信息条数
     * @return
     */
    List<TbItem> findAllTbItem(@Param("start") Integer  start, @Param("size") Integer size);

    /**
     * 根据商品id修改tbItem表中商品的信息
     * @param ids   要修改商品的id
     * @param type  修改后的商品状态
     * @param update   商品信息的更新时间
     * @return
     */
    int updateTbItem(@Param("ids") List<Long> ids,@Param("type") int type,@Param("update") Date update);

    List<TbItemCatResult> showZtree(@Param("id") Long id);

    int searchItemLikeCount(@Param("title") String title,@Param("priceMin") Integer priceMin,@Param("priceMax") Integer priceMax,@Param("cId") Long cId);

    List<TbItem> searchItemLike(@Param("start") Integer start,@Param("limit") Integer limit,@Param("title") String title,@Param("priceMin") Integer priceMin,@Param("priceMax") Integer priceMax,@Param("cId") Long cId);
}