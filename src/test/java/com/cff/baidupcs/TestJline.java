package com.cff.baidupcs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jline.WindowsTerminal;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

public class TestJline {
	public static void main(String[] args) throws Exception {
		ConsoleReader reader = new ConsoleReader();
		reader.addCompleter(new StringsCompleter(new String[] { "abc", "def", "asdasvxc" }));
		String line = "";
		while (line != null && !line.equals("exit") && !line.equals("quit") && !line.equals("q")) {
			line = reader.readLine("BdPcs->");

			System.out.println(line);
		}
	}

}
