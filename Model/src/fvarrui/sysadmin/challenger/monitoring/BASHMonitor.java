package fvarrui.sysadmin.challenger.monitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.ExecutionResult;

public class BASHMonitor extends ShellMonitor {
	
	private static final String SYSDIG = "/usr/bin/sysdig -c spy_users --unbuffered";
	
	private Pattern pattern = Pattern.compile("^\\s*\\d+ (\\d{1,2}:\\d{1,2}:\\d{1,2}) (\\w+)\\) (.*)$");
	private Command command;
	
	public BASHMonitor() {
		super("BashMonitor");
		this.command = new Command(SYSDIG);
	}
	
	@Override
	public void doWork() {
		try {
			
			System.out.println("ejecutando comando: " + command.getCommand());
			ExecutionResult result = command.execute(false);
			BufferedReader reader = new BufferedReader(new InputStreamReader(result.getOutputStream()));
			
			System.out.println("iniciando bucle");
			
			while (!isStopped()) {

				String line = reader.readLine();
				if (line != null) {
					System.out.println("linea: " + line);
	
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						String time = matcher.group(1);
						String username = matcher.group(2);
						String command = matcher.group(3);
						
						if (!getExcludedCommands().contains(command)) {
						
							LocalDateTime timestamp = LocalDateTime.of(LocalDate.now(), LocalTime.parse(time));
							
							Map<String, Object> data = new HashMap<>();
							data.put(COMMAND, command);
							data.put(USERNAME, username);
							data.put(TIMESTAMP, timestamp);
							notifyAll(data);
							
						}
						
					}
					
				}
				
			}
			
			System.out.println("fin del bucle");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
