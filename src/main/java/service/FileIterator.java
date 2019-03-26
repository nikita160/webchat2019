package service;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class FileIterator implements Iterator<String> {

    Queue<File> files = new LinkedList<>();

    public FileIterator(String path){
        files.add(new File(path));
    }


    @Override
    public boolean hasNext() {
        return !files.isEmpty();
    }


    @Override
    public String next() {
        File file = files.peek();
        if (file.isDirectory()){
            for (File subfile: file.listFiles()) {
                files.add(subfile);
            }
        }
        return files.poll().getAbsolutePath();
    }
}
