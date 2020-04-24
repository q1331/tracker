package com.wangshangyi.tracker.dao;

import com.wangshangyi.tracker.Traceno;
import com.wangshangyi.tracker.TracenoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TracenoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    long countByExample(TracenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int deleteByExample(TracenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int insert(Traceno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int insertSelective(Traceno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    Traceno selectOneByExample(TracenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    Traceno selectOneByExampleSelective(@Param("example") TracenoExample example, @Param("selective") Traceno.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    List<Traceno> selectByExampleSelective(@Param("example") TracenoExample example, @Param("selective") Traceno.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    List<Traceno> selectByExample(TracenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    Traceno selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Traceno.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    Traceno selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Traceno record, @Param("example") TracenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Traceno record, @Param("example") TracenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Traceno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table traceNo
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Traceno record);
}