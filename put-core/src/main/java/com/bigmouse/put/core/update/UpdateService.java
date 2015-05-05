package com.bigmouse.put.core.update;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.ProgramItem;
import com.bigmouse.put.core.UpdateContext;

/**
 * Update files to program
 * @author lihaoyuan
 *
 */
public interface UpdateService
{
	public void update(UpdateContext context, ProgramItem program) throws PatchUpdateException;
	public void rollback(UpdateContext context, ProgramItem program) throws PatchUpdateException;
}
