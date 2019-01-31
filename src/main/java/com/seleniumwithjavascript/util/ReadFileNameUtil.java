package com.seleniumwithjavascript.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFileNameUtil {

	public ArrayList<String> readFilename(String sourceFolder) {
		 ArrayList<String> names = new ArrayList<String>();
		File file = new File(sourceFolder);
		String[] fileList = file.list();
		for (String name : fileList) {
			names.add(name);
		}
		return names;

	}
}
