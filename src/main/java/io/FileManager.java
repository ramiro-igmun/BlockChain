package io;

import java.io.FileNotFoundException;

public interface FileManager {

  void save(Object object, String path);

  Object load(String path) throws FileNotFoundException;
}
