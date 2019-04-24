package com.kam;

import org.springframework.stereotype.Repository;


public interface Dao {
	@Select("select * from dao")
	void query();
}
