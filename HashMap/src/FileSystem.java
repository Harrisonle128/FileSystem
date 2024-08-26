/**
 * This file represents the structure for file system which stores a files information.
 */
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class FileSystem {
    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    
	public FileSystem() {
    	this.nameMap = new MyHashMap<>();
    	this.dateMap = new MyHashMap<>();
    }
	/**
	 * Constructor that adds file information to File System.
	 * @param inputFile (file to be read)
	 */
    public FileSystem(String inputFile) {
        // Add your code here
    	this.nameMap = new MyHashMap<>();
    	this.dateMap = new MyHashMap<>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                // Add your code here
                String filename = data[0];
                String filedirectory = data[1];
                String date = data[2];
                add(filename, filedirectory, date);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Create a file data object with a given name, directory, and modified date and
     * add it to the file system.
     * @param fileName 
     * @param directory 
     * @param modifiedDate
     * @return true if added and false if null
     */
    public boolean add(String fileName, String directory, String modifiedDate) {
    	if(fileName == null) { 
    		fileName = ""; 
    	}
    	if(directory == null) { 
    		directory = "/"; 
    	}
    	if(modifiedDate == null) { 
    		modifiedDate = "01/01/2021"; 
    	}
    	ArrayList<FileData> nameMapAList = new ArrayList<>();
    	ArrayList<FileData> dateMapAList = new ArrayList<>();
    	FileData data = new FileData(fileName, directory, modifiedDate);
    	
    	if(findFile(fileName, directory) != null) { 
    		return false; 
    	}
    	
    	nameMapAList = findFilesByName(fileName);
    	nameMapAList.add(data);
    	this.nameMap.set(fileName, nameMapAList);
    	
    	dateMapAList = findFilesByDate(modifiedDate);
    	dateMapAList.add(data);
    	this.dateMap.set(modifiedDate, dateMapAList);
    	
    	return true;
    }

    /**
     * return a file object with the provided name and directory
     * @param name of file
     * @param directory name
     * @return a file with the given name and directory
     */
    public FileData findFile(String name, String directory) {
    	if(this.nameMap.get(name) == null) {
    		return null;
    	} 
    	for(FileData data: this.nameMap.get(name))
    	{
    		if(data.name.equals(name) && data.dir.equals(directory))
    		{
    			return data;
    		}
    	}
    	return null;
    }
    
    /**
     * 
     * @return all files in a list or an empty arraylist if it's empty.
     */
    public ArrayList<String> findAllFilesName() {
    	 ArrayList<String> nameList = new ArrayList<>();

    	    if (this.nameMap.isEmpty()) {
    	        return nameList;
    	    }

    	    for (String name : this.nameMap.keys()) {
    	        nameList.add(name);
    	    }

    	    return nameList;
    }

    /**
     * returns a list of files with the same name
     * @param name of file
     * @return a list of files with the same name
     */
    public ArrayList<FileData> findFilesByName(String name) {
    	ArrayList<FileData> aList = new ArrayList<>();
    	if(this.nameMap.get(name) == null) {
    		return aList;
    	}    	

    	for(FileData data: this.nameMap.get(name))
    	{
    		aList.add(data);
    	}
    	return aList;
    }

  /**
   * Return a list of filedata with the same modified date
   * @param modifiedDate = name of modified date
   * @return list of files with the same modified date
   */
    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
    	ArrayList<FileData> fileList = new ArrayList<>();
    	if(this.dateMap.get(modifiedDate) == null) {
    		return fileList;
    	}   
    	
    	for(FileData data: this.dateMap.get(modifiedDate)){
    		fileList.add(data);
    	}
    	return fileList;
    }

   /**
    * Return a list of filedata with the given modified date in different directories
    * @param modifiedDate = name of modified date
    * @return list of files with the same modified date.
    */
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
    	ArrayList<FileData> fileList = new ArrayList<>();
    	ArrayList<FileData> dateMapList = this.dateMap.get(modifiedDate);
    	
    	if(this.dateMap.get(modifiedDate) == null) {
    		return fileList;
    	}  
    	
    	for(FileData data: dateMapList)
    	{
    		for(FileData dataFind : dateMapList)
    		{
    			if(data.name.equals(dataFind.name) && !data.dir.equals(dataFind.dir))
    			{
    				fileList.add(data);
    			}
    		}
    	}
    	return fileList;
    }

    /**
     * removes all the files with the same name
     * @param name of the file
     * @return true if successfully removed all files.
     */
    public boolean removeByName(String name) {
    	 if (!nameMap.containsKey(name)) {
    	        return false;
    	    }

    	    ArrayList<FileData> fileList = new ArrayList<>(nameMap.get(name));

    	    boolean removed = false;
    	    for (FileData fileData : fileList) {
    	        String directory = fileData.dir;
    	        if (removeFile(name, directory)) {
    	            removed = true;
    	        }
    	    }

    	    if (fileList.isEmpty()) {
    	        nameMap.remove(name);
    	    }

    	    return removed;
    }

   /**
    * Removes a file given the name and directory
    * @param name of the file
    * @param directory name
    * @return true if the specified file is successfully removed
    * and false if not.
    */
    public boolean removeFile(String name, String directory) {
    	 ArrayList<FileData> fileList = nameMap.get(name);
    	    
    	    if (fileList == null) {
    	        return false;
    	    }
    	    
    	    for (int i = 0; i < fileList.size(); i++) {
    	        FileData fileData = fileList.get(i);
    	        
    	        if (fileData.dir.equals(directory)) {
    	            fileList.remove(i);
    	            
    	            if (fileList.isEmpty()) {
    	                nameMap.remove(name);
    	            }
    	            
    	            ArrayList<FileData> dateList = dateMap.get(fileData.lastModifiedDate);
    	            
    	            if (dateList != null) {
    	                dateList.remove(fileData);
    	                
    	                if (dateList.isEmpty()) {
    	                    dateMap.remove(fileData.lastModifiedDate);
    	                }
    	            }
    	            
    	            return true;
    	        }
    	    }
    	    
    	    return false;
    }

}
