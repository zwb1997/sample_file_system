package com.filesystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.filesystem.models.DictionaryTable;

@Mapper
public interface DictionaryMapper {

    public List<DictionaryTable> searchCodeByName(@Param("codeName") String codeName);

    public int disableDictionary(@Param("dictionary") DictionaryTable dictionary);

    public List<DictionaryTable> queryDicByIds(@Param("ids") List<String> ids);

    public int insertNewDics(@Param("dictionaries") List<DictionaryTable> dictionaries);
}
