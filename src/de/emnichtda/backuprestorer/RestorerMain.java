package de.emnichtda.backuprestorer;

import java.io.File;
import java.util.Scanner;

public class RestorerMain{
    private File path;

    public static void main(String[] args){
        File cwd = new File(System.getProperty("user.dir"));
        RestorerMain instance = new RestorerMain(cwd);

        System.out.print("Do you want to restore all the files from the directory '" + cwd.getPath() + "'? [Y/n]");

        Scanner scanner = new Scanner(System.in);
        String initConfirmation = scanner.nextLine();
        scanner.close();

        if(!initConfirmation.isEmpty()&&!initConfirmation.startsWith("y")&&!initConfirmation.startsWith("Y")){
            System.out.println("Abort.");
            return;
        }

        instance.start();
    }

    public RestorerMain(File path) throws IllegalArgumentException{
        setPath(path);
    }

    public void start(){

    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) throws IllegalArgumentException {
        if(!path.isDirectory()){
            throw new IllegalArgumentException(path.getPath());
        }
        this.path = path;
    }

}
