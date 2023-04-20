import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		//user input
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Input folder path(absolute path)  > ");
	    String input = scanner.nextLine();

	    System.out.print("Result folder path(absolute path)  > ");
	    String result = scanner.nextLine();

	    System.out.print("Parameter(0 < θ < 1)  > ");
	    String pa = scanner.nextLine();
	    scanner.close();

		//parameter
		ILPMatrix.theta = Double.parseDouble(pa);
		ILPMatrix.parameter = (-1.0)*Math.log(1.0-ILPMatrix.theta);

		//input file path
		FileRead.InputFilePath = input;
		File file = new File(FileRead.InputFilePath);
		String[] filename1 = file.list();

		//ILP file path
		File fileILP = new File(result+"\\ILP");

		if (fileILP.exists()) {
		    FileMaker.ILP_path = result+"\\ILP";
		} else {
			fileILP.mkdir();
			FileMaker.ILP_path = result+"\\ILP";
		}
//		String ilp = "C:\\Users\\eimiy\\Desktop\\Example\\ILP";
//
//
//		File_make.ILP_path = ilp;

		for(int i = 0; i < filename1.length;i++) {
			//Critical_PMDS_ILPMatrix.theta = 0.3;
			System.out.println("--Start Analysis--");
			Clear.clear();
			FileRead.FileName = filename1[i];
			System.out.println(FileRead.FileName);
			long startTime = System.currentTimeMillis();

			FileMaker.FileMake();
			Algorithm.main_Algorithm(result);
			//解析時間測定終了
			long endTime = System.currentTimeMillis();
			System.out.println("Time：" + (endTime - startTime) + " ms");
			System.out.println("");


		}

	}

}
