package com.bigmouse.put.core.backup;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.ProgramItem;
import com.bigmouse.put.core.UpdateContext;

/**
 * Backup files in program
 * @author lihaoyuan
 *
 */
public interface BackupService
{
	public void backup(UpdateContext context, ProgramItem program) throws PatchUpdateException;
}
