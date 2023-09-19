import java.util.Collections;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public int size(Entry entry) {
        if(entry instanceof File) {
            File file =(File)entry;
            return file.size();
        }
        else {
            Directory directory =(Directory)entry;
            return this.sizeOfDirectory(directory);
        }

    }

    public int numberOfFiles(Directory directory) {
        return (int) directory.getListOfEntry().stream().filter(dir -> dir instanceof File).count();

    }

    private int sizeOfDirectory(Directory directory) {
        int totalSize=0;
        for(Entry e: directory.getListOfEntry()) {
            totalSize+=size(e);
        }
        return totalSize;
    }

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        Main operation = new Main();
        System.out.println("Hello and welcome!");

        Directory main = new Directory(null,"main");
        File file1 = new File(main,"file1","ABCD", 10);
        main.addlistOfEntry(file1);
        Directory dir1 = new Directory(main,"dir1");
        File file2 = new File(dir1,"file2","ABCDE", 20);
        File file3 = new File(dir1,"file3","ABC", 5);
        main.addlistOfEntry(dir1);
        dir1.addlistOfEntry(file2);
        dir1.addlistOfEntry(file3);
        System.out.println(file2.size());
        Directory dir2 = new Directory(dir1,"dir2");
        File file4 = new File(dir2,"file4","ACD", 15);
        dir2.addlistOfEntry(file4);
        dir1.addlistOfEntry(dir2);
        System.out.println(operation.sizeOfDirectory(main));
        System.out.println(operation.numberOfFiles(dir1));
        //System.out.println(dir1.size());
    }
}