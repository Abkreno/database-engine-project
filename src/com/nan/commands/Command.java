package com.nan.commands;

import com.nan.DBAppException;
import com.nan.DBEngineException;

public interface Command {
	public void execute() throws DBAppException, DBEngineException;
}
