import java.io.File;
import java.io.IOException;

public class FileMaker {
	public static String ILP_path = "";

		public static void FileMake() {
			try{
			      File lpfile = new File(ILP_path + "\\dom_test1.lp");
			      File solfile = new File(ILP_path + "\\sol_test1.sol");

				      if (lpfile.createNewFile()){

				      }else{

				      }
				      if (solfile.createNewFile()){

					  }else{

					  }
			   }catch(IOException e){
				      System.out.println(e);
			   }
		}

}
