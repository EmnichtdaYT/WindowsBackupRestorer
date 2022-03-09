package de.emnichtda.backuprestorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class RestorerMain {
    private File path;
    private File toPath;

    public static void main(String[] args) {
        File cwd = new File(System.getProperty("user.dir"));
        File toPath = new File(cwd.getAbsolutePath() + "/restore");

        RestorerMain instance = new RestorerMain(cwd, toPath);

        System.out.print("Do you want to restore all the files from the directory '" + cwd.getAbsolutePath() + "' to the directory '" + toPath.getAbsolutePath() + "' ? [Y/n]");

        Scanner scanner = new Scanner(System.in);
        String initConfirmation = scanner.nextLine();
        scanner.close();

        if (!initConfirmation.isEmpty() && !initConfirmation.startsWith("y") && !initConfirmation.startsWith("Y")) {
            System.out.println("Abort.");
            return;
        }

        instance.start();
    }

    public RestorerMain(File path, File toPath) throws IllegalArgumentException {
        setPath(path);
        setToPath(toPath);
    }

    public void start() {
        toPath.mkdirs();
        restoreFolder(path);
    }

    public void restoreFolder(File path){

        File[] files = path.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                restoreFolder(f);
            }else{
                String toWithTimestamp = toPath.getAbsolutePath() + "/" + f.getAbsolutePath().substring(getPath().getAbsolutePath().length());
                if(toWithTimestamp.contains("(") && toWithTimestamp.contains(")") && toWithTimestamp.contains("_") && toWithTimestamp.contains(" ")) {
                    int i = toWithTimestamp.lastIndexOf(")");

                    String toS = toWithTimestamp.substring(0, i - 25) + toWithTimestamp.substring(i+1);

                    File to = new File(toS);
                    to.getParentFile().mkdirs();
                    System.out.println(f.getAbsolutePath() + " zu " + to.getAbsolutePath());

                    try{
                        Files.copy(f.toPath(), to.toPath());
                        System.out.println("OK");
                    }catch(IOException exception){
                        System.out.println("Unable to copy");
                        exception.printStackTrace();
                    }
                }
            }
        }
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) throws IllegalArgumentException {
        if (path == null) {
            throw new IllegalArgumentException("Path can't be null");
        }
        if (!path.isDirectory()) {
            throw new IllegalArgumentException(path.getAbsolutePath() + " is not a directory");
        }
        this.path = path;
    }

    public File getToPath() {
        return toPath;
    }

    public void setToPath(File toPath) throws IllegalArgumentException {
        if (toPath == null) {
            throw new IllegalArgumentException("Path cant be null");
        }
        if (toPath.exists()) {
            throw new IllegalArgumentException(toPath.getAbsolutePath() + " already exists");
        }
        this.toPath = toPath;
    }
}
