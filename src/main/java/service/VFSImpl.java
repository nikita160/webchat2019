package service;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class VFSImpl implements VFS {

    private String root;

    public VFSImpl (String root){
        this.root = root;
    }

    @Override
    public boolean isExist(String path) {
        return new File(path).exists();
    }

    @Override
    public boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        return new File(file).getAbsolutePath();
    }

    @Override
    public byte[] getBytes(String file) {
        try (InputStream io = new FileInputStream(new File(file))){
            return IOUtils.toByteArray(io);
        }
        catch (IOException e){
            System.err.println("Failed getByte");
        }
        return null;
    }


    @Override
    public Iterator<String> getIterator() {
        return new FileIterator(root);
    }
}
