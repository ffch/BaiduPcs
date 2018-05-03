package com.cff.baidupcs.tess4j;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import net.sourceforge.tess4j.Tesseract;

public class TesseractIdentify {
	public static String scan(File imageFile) {
		String result = "404";

		try {
		Tesseract instance = new Tesseract();
		String dataPath = "tessdata/eng.traineddata";
		InputStream in = TesseractIdentify.class.getClassLoader().getResourceAsStream(dataPath);
		if (in == null) {
			in = TesseractIdentify.class.getClassLoader().getResourceAsStream("resources/" + dataPath);
		}
		File file = new File("tessdata/eng.traineddata");  
		if(!file.exists()){
			File fileParent = file.getParentFile();  
			if(!fileParent.exists()){  
			    fileParent.mkdirs();  
			}  
			Files.copy(in, file.toPath());
		}

		instance.setLanguage("eng");
		instance.setDatapath(file.getParentFile().getAbsolutePath());

			result = instance.doOCR(imageFile);
		} catch (Exception e) {
		}
		return result;
	}
}
