package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Configuration File Line Reader
 * Reads in the lines of a configuration file into key/value pairs.
 * Optionally filters blank/comment lines.
 */
public class ConfigLineReader {

  public ConfigLineReader() { }
  
  public Map<String, String> parse(String inFilePath, String separator) throws FileNotFoundException {
    return parse(new File(inFilePath), separator);
  }
  
  public Map<String, String> parse(File inFile, String separator) throws FileNotFoundException {
    return parse(new FileReader(inFile), separator);
  }
  
  public Map<String, String> parse(Reader in, String separator) {
    return parse(new BufferedReader(in), Pattern.compile(separator), Pattern.compile("\\s*(#.*)?"));
  }
  
  public Map<String, String> parse(BufferedReader in, Pattern separator, Pattern ignore) {
    Map<String, String> config = new LinkedHashMap<String, String>();
    String line = null;
    do {
      // Read a line
      try { line = in.readLine(); }
      catch (IOException e) { FatalError.die("Error reading spec: %s", e); }
      // Filter out blank lines, comments and EOF
      if (line != null && !ignore.matcher(line).matches()) {
        String[] parts = separator.split(line, 2);
        if (parts.length != 2) FatalError.die("Malformed spec line: %s", line);
        config.put(parts[0].trim(), parts[1]);
      }
    } while (line != null);
    return config;
  }

}
