/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Chris
 */
public class Main {

    public static ArrayList<Step> randomsteps = new ArrayList<>();
    public static ArrayList<Step> teststeps = new ArrayList<>();
    public static ArrayList<FormattedItem> jsonsteps = new ArrayList<>();
    public static ArrayList<String> rawlines;
    public static StringBuilder htmltext = new StringBuilder();
    public static StringBuilder jsontexty = new StringBuilder();
//    public static File file;
//    public static File htmlfile;
//    public static String path = "";

    public static void main(String[] args) throws IOException {
	File file;
	File htmlfile;
	String path;
	String os = System.getProperty("os.name").toLowerCase();
	Process process;
	try {
	    if (os.indexOf("win") >= 0) {
		process = Runtime.getRuntime().exec("cmd");

	    } else if (os.indexOf("mac") >= 0) {
//	    String[] arguments = new String[] {"/usr/bin/open -a Terminal", ""
	    process = Runtime.getRuntime().exec("/usr/bin/open -a Terminal");
	    } 
	    else {
		process = Runtime.getRuntime().exec("cmd");
	    }
	    System.out.println("Enter a .csv file name to read:");
	    Scanner scanner = new Scanner(System.in);
//	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	    path = br.readLine();
	    path = scanner.nextLine();

	    file = new File(path);
	    htmlfile = new File(file.getParent() + "/htmltext.txt");
	    

	    if (file.getPath() != null && htmlfile.getPath() != null) {
		path = file.getAbsolutePath();

		jsonsteps.clear();
		teststeps.clear();
		rawlines = readCSV(new File(path));
		ArrayList<String> steplines = removeQuotes(rawlines);
		randomsteps = sortSteps(steplines);
		teststeps = randomsteps;
		convertToHTML();

		FileWriter writer = new FileWriter(htmlfile);
		writer.write(htmltext.toString());
		writer.flush();
		writer.close();
//		FileWriter stream = new FileWriter(htmlfile);
//		BufferedWriter out = new BufferedWriter(stream);
//		out.newLine();
//		out.write(htmltext.toString());

		System.out.println("File saved!");
		scanner.close();
	    }
	} catch (Exception e) {

	}

    }

    /*
	 * Method to read the data from the source CSV file, filet it, and place the result in an ArrayList<String>
     */
    public static ArrayList<String> readCSV(File path) throws IOException {
	String line = "";
	StringBuilder sb = new StringBuilder();
	ArrayList<String> rawlines = new ArrayList<String>();
	String[] lines;
	BufferedReader br = new BufferedReader(new FileReader(path));
	int i = 0;
	while ((line = br.readLine()) != null) {
	    if (line.matches("Step@Data@Expected Result")) {
		continue;
	    }
	    if (i == 0) {
		sb.append(line);
		i++;
		continue;
	    }
	    if (line.contains("@")) {
		sb.append("@" + line);
	    } else {
		sb.append("%%" + line);
	    }
	}
	lines = sb.toString().split("@");
	for (String s : lines) {
	    if (s.isBlank()) {
		s = "--";
	    }
	    rawlines.add(s);
	    i++;
	}
	br.close();
	return rawlines;
    }

    /*
	 * Method to remove quotation marks from the extracted data since those become problematic when converting the data to JSON format
     */
    public static ArrayList<String> removeQuotes(ArrayList<String> rawlines) {
	String formattedtext = "";
	ArrayList<String> formattedlines = new ArrayList<>();
	for (String s : rawlines) {
	    formattedtext = s;
	    if (s.contains("\"")) {
		formattedtext = s.replace('\"', ' ');
	    }
	    formattedlines.add(formattedtext);
	}
	return formattedlines;
    }

    /*
	 * Method that sorts the extracted data into 2 separate custom classes: Step and FormattedItem. Each Step and each FormattedItem is 
	 * then stored in a class-level ObservableList that holds its particular class type.
     */
    public static ArrayList<Step> sortSteps(ArrayList<String> rawlines) {
	int index = 0;
	int count = 0;
	Step step;
	String[] pholder = new String[3];
	for (int i = 0; i < rawlines.size(); i++) {
	    pholder[index] = rawlines.get(i);
	    if (index == 2) {
		index = 0;
		count++;
		step = new Step(String.valueOf(count), pholder[0], pholder[1], pholder[2]);
		teststeps.add(step);
		jsonsteps.add(new FormattedItem(step));
	    } else {
		index++;
	    }
	}
	return teststeps;
    }

    public static void convertToHTML() {
	StringBuilder htmlbuilder = new StringBuilder();
	htmlbuilder.append("<table style = 'font-size: 10px'>" + "\n"
		+ "<thead>" + "\n"
		+ "<tr style = 'border: 1px solid black'>" + "\n"
		+ "<th style = 'border: 1px solid black'>#</th>" + "\n"
		+ "<th style = 'border: 1px solid black'>Step</th>" + "\n"
		+ "<th style = 'border: 1px solid black'>Data</th>" + "\n"
		+ "<th style = 'border: 1px solid black'>Expected Results</th>" + "\n"
		+ "</tr>" + "\n"
		+ "</thead>" + "\n"
		+ "<tbody>");
	for (FormattedItem f : jsonsteps) {
	    htmlbuilder.append(f.htmlify());
	}
	htmlbuilder.append("</tr>" + "\n"
		+ "</tbody>" + "\n"
		+ "</table>");
	htmltext = htmlbuilder;
    }

}
