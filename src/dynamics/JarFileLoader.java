package dynamics;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import oberonui.OberonUI;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class JarFileLoader {
	private static HashMap<String, URLClassLoader> loaded = new HashMap<>();


	public static URLClassLoader loadLibrary(String name) throws Exception {
		if (!name.endsWith(".jar")){ 
			name += ".jar";
		}

		if (loaded.containsKey(name)) {
			return loaded.get(name);
		}

		HashMap<String, String> files = FileUtils.getFilesWithExtension("./apps", ".jar");
		if (!files.containsKey(name)) {
			throw new Exception("Failed to locate a library file (.jar) with name " + name);
		}
		String path = files.get(name);

		File library_ptr = new File(path);
		URLClassLoader loader = null;
		try {
			loader = new URLClassLoader(new URL[]{library_ptr.toURI().toURL()});
			loaded.put(name, loader);
		} catch (MalformedURLException e) {
			throw new Exception("Malformed path to library file (.jar) with name " + name);
		}
		return loader;
	}

	public static Class<?> loadClass(String library, String class_name) throws Exception {
		Class<?> clz = null;
		try {
			clz = Class.forName(class_name);
		} catch (ClassNotFoundException ex) {
			URLClassLoader loader = loadLibrary(library);
			try {
				clz = loader.loadClass(class_name);
			} catch (ClassNotFoundException e) {
				throw new Exception("Class `" + class_name + "` not found in library " + library);
			}
		}
		return clz;
	}

	public static void loadAndRunJarFile(String[] args) {
		try {
			var library = args[0];
			var class_name = args[1];
			var clz = loadClass(library, class_name);
			var cons = clz.getDeclaredConstructor();
			
			var app = cons.newInstance();
			var app_args = (args.length - 2) > 0 ?  Arrays.copyOfRange(args, 2, args.length) : new String[] {};

			OberonUI
				.addTiledFrame((Window) app)
				.processArgs(app_args);
		} catch (Exception e) {
			
		}
	}

}
