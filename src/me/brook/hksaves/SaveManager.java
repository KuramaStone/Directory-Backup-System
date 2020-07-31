package me.brook.hksaves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;

import org.yaml.snakeyaml.Yaml;

public class SaveManager {

	private HKSaves plugin;

	// stores the save data of each save profile by name
	private LinkedHashMap<String, Profile> profiles;

	// Files, loaded with values to save if config is non-existent
	private final File saveDirectory = new File(
			new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath() + "\\Hollow Knight\\");
	private File backupDirectory = new File(saveDirectory.getAbsolutePath() + "\\backups");

	private File hkSaveDirectory = new File(
			new File(System.getenv("LOCALAPPDATA")).getParent() + "\\LocalLow\\Team Cherry\\Hollow Knight");

	// Gui selection
	private Profile selectedSave;

	public SaveManager(HKSaves hkSaves) {
		plugin = hkSaves;
		try {
			loadConfig();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		try {
			saveConfig();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadConfig() throws Exception {
		File file = new File(new File(saveDirectory.getAbsolutePath() + "\\data\\"), "config.yml");

		Yaml yaml = new Yaml();
		if(file.exists()) {
			yaml.load(new FileReader(file));
		}
		else {
			saveConfig();
		}

		Map<String, Object> data = yaml.load(new FileReader(file));

		Map<String, String> directories = (Map<String, String>) data.get("directories");

		hkSaveDirectory = new File(directories.get("gameDirectory"));
		backupDirectory = new File(directories.get("backupDirectory"));

		LinkedHashMap<String, Profile> saves = (LinkedHashMap<String, Profile>) data.get("saves");
		this.profiles = saves;

	}

	public void saveConfig() throws Exception {
		System.out.println("Writing save data...");
		File file = new File(new File(saveDirectory.getAbsolutePath() + "\\data\\"), "config.yml");
		file.delete();
		file.createNewFile();

		Map<String, Object> data = new HashMap<String, Object>();

		// Save saves
		if(this.profiles == null || this.profiles.isEmpty()) {
			fillSaveData();
		}
		data.put("saves", profiles);

		// Save directories
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameDirectory", hkSaveDirectory.getAbsolutePath());
		map.put("backupDirectory", backupDirectory.getAbsolutePath());
		data.put("directories", map);

		Yaml yaml = new Yaml();
		if(!file.exists()) {
			file.createNewFile();
			yaml.load(new FileReader(file));
		}

		yaml.dump(data, new FileWriter(file));
	}

	private void fillSaveData() {
		this.profiles = new LinkedHashMap<>();
		this.profiles.put("casual", new Profile("casual", 0));
		this.profiles.put("speedrun", new Profile("speedrun", 0));
		this.profiles.put("practice", new Profile("practice", 0));
	}

	public Map<String, Profile> getProfiles() {
		return profiles;
	}

	public Profile getProfile(String name) {
		return profiles.get(name);
	}

	public File getGameDirectory() {
		return hkSaveDirectory;
	}

	public File getBackupDirectory() {
		return backupDirectory;
	}

	public void setBackupDirectory(File backupDirectory) {
		this.backupDirectory = backupDirectory;
		backupDirectory.mkdirs();
		plugin.getGui().update();
	}

	public void setGameDirectory(File hkSaveDirectory) {
		this.hkSaveDirectory = hkSaveDirectory;
		plugin.getGui().update();
	}

	public void setSelectedProfile(Profile selectedSave) {
		this.selectedSave = selectedSave;
		plugin.getGui().update();
	}

	public Profile getSelectedProfile() {
		return selectedSave;
	}

	public void removeProfile(Profile prof) {
		this.profiles.remove(prof.getName());
		if(selectedSave == prof) {
			setSelectedProfile(null);
		}
	}

	public void renameProfile(Profile profile, String name) {
		String oldName = profile.name;
		profile.name = name;

		// We add it so that
		LinkedHashMap<String, Profile> copy = new LinkedHashMap<String, Profile>();
		for(Entry<String, Profile> set : profiles.entrySet()) {
			if(set.getKey().equals(oldName)) {
				copy.put(name, profile);
			}
			else {
				copy.put(set.getKey(), set.getValue());
			}
		}
		this.profiles = copy;

		plugin.getGui().update();
	}

	public void saveProfile(Profile profile) {
		try {
			profile.lastSaved = System.currentTimeMillis();

			File backup = new File(backupDirectory, profile.name);
			backup.delete();

			System.out.println("Saving files...");
			saveFile(hkSaveDirectory, backup);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		plugin.getGui().update();
	}

	private void saveFile(File source, File destParent) throws IOException {

		for(File f : source.listFiles()) {
			if(!f.isDirectory()) {
				File dest = new File(destParent, f.getName());
				System.out.printf("Saving %s to %s\n", f.getAbsolutePath(), dest.getAbsolutePath());
				dest.getParentFile().mkdirs();
				dest.createNewFile();
				FileInputStream fi = new FileInputStream(f);
				FileOutputStream fo = new FileOutputStream(dest);

				byte[] buffer = new byte[1024];

				int length = 0;
				while((length = fi.read(buffer)) > 0) {
					fo.write(buffer, 0, length);
				}

				fi.close();
				fo.close();
			}
			else {
				saveFile(f, new File(destParent.getAbsolutePath() + "\\" + f.getName()));
			}
		}
	}

	public void addProfile(Profile prof) {
		this.profiles.put(prof.name, prof);
		
		plugin.getGui().update();
	}

	public void uploadProfile(Profile profile) {
		try {

			File backup = new File(backupDirectory, profile.name);
			hkSaveDirectory.delete();

			System.out.println("Uploading files...");
			saveFile(backup, hkSaveDirectory);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
