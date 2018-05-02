package com.cff.baidupcs;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;

public class TestOrgJline {
	public static void main(String[] args) throws Exception {
		LineReader lineReader = LineReaderBuilder.builder().completer(new StringsCompleter("abc", "def", "asdasvxc")).build();
		String line = "";
		while (line != null && !line.equals("exit") && !line.equals("quit") && !line.equals("q")) {
			line = lineReader.readLine("BdPcs->");

			System.out.println(line);
		}
	}

}
