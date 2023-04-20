import java.io.File;

public class FileDelete {

	public static void file_delete() {
		File solfile = new File(FileMaker.ILP_path + "\\sol_test1.sol");
		solfile.delete();
	}

}
