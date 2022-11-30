package com.chip.be;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ReadTaskBar {
	
	public static final List<String> existingApps = new ArrayList<String>();
	
	public List<String> readOpnedApp() {
		List<String> apps = null;;
		System.out.println("Existing apps :: "+existingApps);
		try {
				apps= new ArrayList<String>();
				String line;
				String command = "powershell.exe gps|?{$_.MainWindowTitle}";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				int counter = 0;
				while ((line = input.readLine()) != null) {
					if (counter > 2) {
						String[] arr = line.replaceAll("\\s", " ").split(" ");
						String s = arr[arr.length - 1];
						if (!apps.contains(s) && !StringUtils.isEmpty(s))
							apps.add(s);
					}
					counter++;
				}
				input.close();
				apps.removeAll(existingApps);
				System.err.println("new app :: "+apps);
				if(apps!=null)
					existingApps.addAll(apps);
				if(!CollectionUtils.isEmpty(apps))
					return apps;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}

}
