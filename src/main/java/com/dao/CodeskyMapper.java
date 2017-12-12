package com.dao;

import com.common.MyMapper;
import com.domain.Codesky;
import com.domain.bo.CodeskyBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 */

public interface CodeskyMapper extends MyMapper<Codesky> {

    List<Codesky> getCodesky(@Param("bo") CodeskyBo bo);
}
