package com.upmr.c_localsearch;

import com.upmr.core.Solution;

public interface Neighborhood {
	public Solution run_moviment(Solution s) throws CloneNotSupportedException;
}
