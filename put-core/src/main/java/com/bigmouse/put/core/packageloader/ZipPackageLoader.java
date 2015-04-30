package com.bigmouse.put.core.packageloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigmouse.put.core.FileDescription;
import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.SystemConfig;
import com.bigmouse.put.core.UpdateContext;
import com.bigmouse.put.log.PutLogObserverProxy;

public class ZipPackageLoader implements PackageLoadService
{
	private transient final Logger log = new PutLogObserverProxy().proxy(LoggerFactory.getLogger(getClass()));
	
	@Override
	public void loadPackage(UpdateContext context, String packageFilePath) throws PatchUpdateException
	{
		//files in zip
		Set<String> files = null;
		String tempPath = SystemConfig.BASE_PATH + File.separator + "temp";
		
		log.info("Create temp folder and unzip package into it.");
		context.getObserver().message("Create temp folder and unzip package into it.", "INFO");

		File zipFile = new File(packageFilePath);
		File tempFolder = new File(tempPath);

		// Create temp folder for unzip
		log.info("Create temp folder: " + packageFilePath);
		context.getObserver().message("Create temp folder and unzip package into it.", "INFO");
		createTempFolder(zipFile, tempFolder);
		
		// Unzip
		log.info("Unzip package file: " + packageFilePath);
		files = unzip(zipFile, tempFolder);
		
		// Check version.put
		readVersion(context, files, tempPath);
		
		// Create version folder and build structure in it
		String version = context.getVersion();
		log.info("Create version folder and build structure in: " + SystemConfig.BASE_PATH + File.separator + version);
		buildVersionFolder(version);
		
		// Move files from temp folder to update version folder
		log.info("Move files from temp folder to update folder");
		String updateFolder = SystemConfig.BASE_PATH + File.separator + version + File.separator + "update";
		moveFiles(tempFolder, new File(updateFolder));
		log.info("All files has moved to update folder: " + updateFolder + ", " + context.getFiles().size() + " files.");
	}

	private void moveFiles(File tempFolder, File updateFolder) throws PatchUpdateException
	{
		try
		{
			FileUtils.copyDirectory(tempFolder, updateFolder);
			FileUtils.forceDelete(tempFolder);
		}
		catch (IOException e)
		{
			PatchUpdateException ex = new PatchUpdateException("Can not copy files to version folder!", e);
			log.error("Can not copy files to version folder!", e);
			throw ex;
		}
	}

	private void buildVersionFolder(String version) throws PatchUpdateException
	{
		File versionFolder = new File(SystemConfig.BASE_PATH + File.separator + version);
		File updateFolder = new File(SystemConfig.BASE_PATH + File.separator + version + File.separator + "update");
		if(versionFolder.exists())
		{
			try
			{
				FileUtils.forceDelete(versionFolder);
			}
			catch (IOException e)
			{
				PatchUpdateException ex = new PatchUpdateException("Build version folder error!", e);
				log.error("Build version folder error!", e);
				throw ex;
			}
		}
		
		versionFolder.mkdirs();
		updateFolder.mkdir();
	}

	private void readVersion(UpdateContext context, Set<String> files, String tempPath) throws PatchUpdateException
	{
		File versionFile = new File(tempPath + File.separator + "version.put");
		if(!versionFile.exists())
		{
			PatchUpdateException ex = new PatchUpdateException("Package file must has a version.put file!");
			log.error("Package file must has a version.put file!");
			throw ex;
		}
		
		// Read version.put
		try
		{
			// Read file by line
			log.debug("Read file: " + tempPath + File.separator + "version.put");
			@SuppressWarnings("unchecked")
			List<String> verLines = FileUtils.readLines(versionFile);
			if(verLines == null || verLines.size() == 0)
			{
				PatchUpdateException ex = new PatchUpdateException("Format of version.put error!");
				log.error("Format of version.put error!");
				throw ex;
			}
			
			for(String line : verLines)
			{
				// Get version text and fill context
				if(line.trim().startsWith("version:"))
				{
					String versionText = line.split(":")[1].trim();
					if(versionText.equals("")) versionText = System.currentTimeMillis() + "";
					log.debug("Got version text: " + versionText);
					context.setVersion(versionText);
				}
				
				// Get files info, build FileDescription and fill context
				if(line.trim().startsWith("A ") || line.trim().startsWith("U ") || line.trim().startsWith("D "))
				{
					String updateType = line.trim().substring(0, 1);
					String updatePath = line.trim().substring(1).trim();
					if(!updatePath.startsWith("/")) updatePath = "/" + updatePath;
					
					FileDescription fileDesc = new FileDescription();
					fileDesc.setOpt(updateType);
					fileDesc.setFilePath(updatePath);

					if(!updateType.equals("D") && !files.contains(updatePath))
					{
						PatchUpdateException ex = new PatchUpdateException("No such file: " + updatePath);
						log.error("No such file: " + updatePath);
						throw ex;
					}
					log.debug("Got file: " + line);
					
					context.getFiles().put(updatePath, fileDesc);
				}
			}
		}
		catch (IOException e)
		{
			PatchUpdateException ex = new PatchUpdateException("Can not read version.put!", e);
			log.error("Can not read version.put!", e);
			throw ex;
		}
	}

	private void createTempFolder(File zipFile, File tempFolder) throws PatchUpdateException
	{
		// Check file exist
		if(!zipFile.exists())
		{
			PatchUpdateException ex = new PatchUpdateException("Package file not exist!");
			log.error("Package file not exist!");
			throw ex;
		}
		if(!isZipFile(zipFile))
		{
			PatchUpdateException ex = new PatchUpdateException("Package file must be a zip file!");
			log.error("Package file must be a zip file!");
			throw ex;
		}
		
		// Create temp folder
		log.debug("Create temp folder, and clear it");
		try
		{
			tempFolder.mkdirs();
			FileUtils.cleanDirectory(tempFolder);
		}
		catch (IOException e)
		{
			PatchUpdateException ex = new PatchUpdateException("Can not create temp folder!", e);
			log.error("Can not create temp folder!", e);
			throw ex;
		}
	}
	
	private boolean isZipFile(File file)
	{
		boolean isZip = false;
		String fileName = file.getName();
		if(fileName.lastIndexOf(".") > -1 && fileName.lastIndexOf(".") < fileName.length() - 1)
		{
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(suffix.toUpperCase().equals("ZIP"))
			{
				isZip = true;
			}
		}
		return isZip;
	}
	
	private Set<String> unzip(File zipFile, File unzipPath) throws PatchUpdateException
	{
		Set<String> files = new HashSet<String>();
		
		ZipFile zip = null;
		InputStream is = null;
		OutputStream os = null;
		
		try
		{
			zip = new ZipFile(zipFile);
			Enumeration<ZipArchiveEntry> en = zip.getEntries();
			while(en.hasMoreElements())
			{
				ZipArchiveEntry entry = en.nextElement();
				
				// skip MAC file
				if(entry.getName().indexOf("__MACOSX") > -1) continue;
				if(entry.getName().indexOf(".DS_Store") > -1) continue;
				if(entry.getName().indexOf(".svn") > -1) continue;
				
				File unZipFile = new File(unzipPath, entry.getName());
				if(entry.isDirectory())
				{
					log.debug("Create folder:" + unZipFile.getAbsolutePath());
					unZipFile.mkdirs();
					continue;
				}
				
				if(entry.getName().toLowerCase().startsWith("files/"))
				{
					files.add(entry.getName().substring(5));
				}
				else
				{
					files.add(entry.getName());
				}
				is = zip.getInputStream(entry);
				os = new FileOutputStream(unZipFile);
				log.debug("Unzip file to " + unZipFile.getAbsolutePath());
				IOUtils.copy(is, os, 1024);
				is.close();
				os.close();
			}
			zip.close();
		}
		catch (IOException e)
		{
			PatchUpdateException ex = new PatchUpdateException("Can not unzip package!", e);
			log.error("Can not unzip package!", e);
			throw ex;
		}
		finally
		{
			if(zip != null) try
			{
				zip.close();
				if(is != null) is.close();
				if(os != null) os.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return files;
	}
}
