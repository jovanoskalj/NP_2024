//package Kolokviumski_zad.zad3;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.OptionalLong;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//interface IFile {
//    String getFileName();
//
//    long getFileSize();
//
//    String getFileInfo(int indent);
//
//    void sortBySize();
//
//    long findLargestFile();
//
//
//}
//
//public class FileSystemTest {
//
//    public static Folder readFolder(Scanner sc) {
//
//        Folder folder = new Folder(sc.nextLine());
//        int totalFiles = Integer.parseInt(sc.nextLine());
//
//        for (int i = 0; i < totalFiles; i++) {
//            String line = sc.nextLine();
//
//            if (line.startsWith("0")) {
//                String fileInfo = sc.nextLine();
//                String[] parts = fileInfo.split("\\s+");
//                try {
//                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
//                } catch (FileNameExistsException e) {
//                    System.out.println(e.getMessage());
//                }
//            } else {
//                try {
//                    folder.addFile(readFolder(sc));
//                } catch (FileNameExistsException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//
//        return folder;
//    }
//
//    public static void main(String[] args) {
//
//        //file reading from input
//
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("===READING FILES FROM INPUT===");
//        FileSystem fileSystem = new FileSystem();
//        try {
//            fileSystem.addFile(readFolder(sc));
//        } catch (FileNameExistsException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("===PRINTING FILE SYSTEM INFO===");
//        System.out.println(fileSystem.toString());
//
//        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
//        fileSystem.sortBySize();
//        System.out.println(fileSystem.toString());
//
//        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
//        System.out.println(fileSystem.findLargestFile());
//
//
//    }
//}
//
//class File implements IFile, Comparable<File> {
//
//    String fileName;
//    long size;
//
//    public File(String fileName, long size) {
//        this.fileName = fileName;
//        this.size = size;
//    }
//
//    @Override
//    public String getFileName() {
//        return fileName;
//    }
//
//    @Override
//    public long getFileSize() {
//        return size;
//    }
//
//    @Override
//    public String getFileInfo(int indent) {
//        return String.format("%sFile name: %10s File size: %10s\n",
//                IndentPrinter.printIndent(indent),
//                getFileName(), getFileSize());
//
//    }
//
//    @Override
//    public void sortBySize() {
//        return;
//    }
//
//    @Override
//    public long findLargestFile() {
//        return this.size;
//    }
//
//
//    @Override
//    public int compareTo(File o) {
//        return Long.compare(this.size,o.size);
//    }
//}
//
//class IndentPrinter {
//    public static String printIndent(int indentLevel) {
//        return IntStream.range(0, indentLevel)
//                .mapToObj(i -> "\t")
//                .collect(Collectors.joining());
//    }
//}
//
//class Folder extends File implements IFile{
//
//    List<IFile> files;
//
//
//    public Folder(String fileName, long size,List<IFile> files) {
//        super(fileName, size);
//        this.files = files;
//    }
//
//    public Folder(String root) {
//        super(root);
//    }
//
//    public void addFile(IFile file) throws FileNameExistsException {
//        if(files.contains(file)){
//            throw new FileNameExistsException(file.getFileName(),
//                    this.fileName);
//        }
//    }
//    @Override
//    public String getFileName() {
//        return this.fileName;
//    }
//
//    @Override
//    public long getFileSize() {
//        return this.size;
//    }
//
//    @Override
//    public String getFileInfo(int indent) {
//        StringBuilder sb=new StringBuilder();
//        sb.append(String.format("%sFolder name: %10s Folder size: %10s\n",
//                IndentPrinter.printIndent(indent),getFileName(),getFileSize()));
//        files.stream().forEach(file->sb.append(file.getFileInfo(indent+1)));
//        return sb.toString();
//    }
//
//    @Override
//    public void sortBySize() {
//        Comparator<IFile> comparator=Comparator.comparingLong(IFile::getFileSize);
//        files.sort(comparator);
//        files.forEach(IFile::sortBySize);
////        return files.stream()
////                .sorted()
////                .forEach(i->i.sortBySize());
//    }
//
//    @Override
//    public long findLargestFile() {
//        OptionalLong largest = files.stream()
//                .mapToLong(i -> i.findLargestFile())
//                .max();
//        if(largest.isPresent())
//            return largest.getAsLong();
//        else return 0;
//    }
//}
//class FileNameExistsException extends Exception{
//    public FileNameExistsException(String name, String fileName) {
//        super(String.format("There is already a file named %s in the folder %s",name,fileName));
//    }
//}
//class FileSystem{
//    private Folder root;
//
//    public FileSystem() {
//        root=new Folder("root");
//    }
//    public void addFile (IFile file) throws FileNameExistsException {
//        root.addFile(file);
//    }
//    public long findLargestFile (){
//        return root.findLargestFile();
//    }
//    public  void sortBySize(){
//        root.sortBySize();
//    }
//
//    @Override
//    public String toString() {
//        return this.root.getFileInfo(0);
//    }
//}