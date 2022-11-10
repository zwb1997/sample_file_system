package com.filesystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.filesystem.models.DocumentTable;

@Mapper
public interface DocumentMapper {

        public List<DocumentTable> queryDocuments(@Param("villageKey") String villageKey,
                        @Param("fileName") String fileName);

        public int insertSavedFileInfo(@Param("documents") List<DocumentTable> documents);

        public List<DocumentTable> searchFilesByUUids(@Param("fileIds") List<String> fileIds,
                        @Param("villageType") String villageType);

        public List<DocumentTable> queryFileByUUIDWithoutState(@Param("fileIds") List<String> fileId,
                        @Param("villageType") String villageType);

        public int disableCurrentFile(@Param("doc") DocumentTable doc);
}
