package com.nan.commands;

import java.util.Iterator;

import com.nan.DBAppException;
import com.nan.DBEngineException;

/** The Invoker class */
public class Switch {
	public static void execute(Command cmd) throws DBAppException,
			DBEngineException {
		cmd.execute();
	}

	public static Iterator executeAndReturn(SelectFromTableCommand cmd)
			throws DBAppException, DBEngineException {
		return cmd.executeAndReturn();
	}
}
