package com.aqvdemo.application.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aqvdemo.application.objects.*;
import com.aqvdemo.application.service.*;
import com.google.gson.Gson;

@Controller
public class MainController {

	@Autowired
	CsvService csvService;

	// webapp save directory
	// private static String UPLOADED_FOLDER =
	// "/usr/apache/apache-tomcat-8.5.14/uploads/";

	// local save directory
	// private static String UPLOADED_FOLDER =
	// "C:\\Users\\iribarneg\\Documents\\workspace-sts\\addressqualityvalidation\\src\\main\\resources\\uploads\\";
	// private static String CSV_FILE;
	/*
	 * @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public String singleFileUpload(@RequestParam("file")
	 * MultipartFile file, RedirectAttributes redirectAttributes) { List<Entry>
	 * response; try {
	 * 
	 * // Get the file and save it somewhere byte[] bytes = file.getBytes(); Path
	 * path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	 * Files.write(path, bytes); CSV_FILE = UPLOADED_FOLDER +
	 * file.getOriginalFilename();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * response = parser(CSV_FILE);
	 * 
	 * Gson gson = new Gson(); String json = gson.toJson(response);
	 * 
	 * return json; }
	 */

	public List<Entry> parser(long fileid) {

		CsvFile csvFile = csvService.findById(fileid);
		String line = "";
		String csvSplitBy = ",";
		List<Entry> results = new ArrayList<Entry>();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(csvFile.getFiledata()), StandardCharsets.ISO_8859_1))) {

			int lineCount = 0;
			while ((line = br.readLine()) != null) {

				if (lineCount == 0) {
					lineCount++; // skip csv headers
				} else {
					// use comma as separator
					String[] row = line.split(csvSplitBy);

					// manage entries with pojo otherwise you lose order when sent to FE
					Entry entry = new Entry();
					entry.setFname(row[0]);
					entry.setLname(row[1]);
					entry.setCompany(row[2]);
					entry.setStreetAddress(row[3]);
					entry.setCity(row[4]);
					entry.setState(row[5]);
					entry.setZipCode(row[6]);

					results.add(entry);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String blobFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		List<Entry> response;
		byte[] bytes = null;
		CsvFile csvFile = new CsvFile();

		try {

			// Get the file and save it somewhere
			bytes = file.getBytes();
			csvFile.setName(file.getOriginalFilename());
			csvFile.setFiledata(bytes);

			csvService.saveFile(csvFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//response = byteParser(bytes);
		response = parser(csvFile.getFileid());
		
		Gson gson = new Gson();
		String json = gson.toJson(response);

		return json;
	}

	/*public List<Entry> byteParser(byte[] filedata) {

		String line = "";
		String csvSplitBy = ",";
		List<Entry> results = new ArrayList<Entry>();

		InputStream is = null;
		BufferedReader br = null;
		try {
			is = new ByteArrayInputStream(filedata);
			br = new BufferedReader(new InputStreamReader(is));

			int lineCount = 0;
			while ((line = br.readLine()) != null) {

				if (lineCount == 0) {
					lineCount++; // skip csv headers
				} else {
					// use comma as separator
					String[] row = line.split(csvSplitBy);

					// manage entries with pojo otherwise you lose order when sent to FE
					Entry entry = new Entry();
					entry.setFname(row[0]);
					entry.setLname(row[1]);
					entry.setCompany(row[2]);
					entry.setStreetAddress(row[3]);
					entry.setCity(row[4]);
					entry.setState(row[5]);
					entry.setZipCode(row[6]);

					results.add(entry);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return results;
	}*/
}