import java.util.ArrayList;

public class ILPMatrix {
	//制御可能確率theta
	public static double theta ;

	//public static double parameter = (-1.0)*Math.log(1.0-theta);
	public static double parameter;

	//前処理Critical
			public static ArrayList<String> Prep_Critical_Xindex = new ArrayList<String>();
			//前処理Redundant
			public static ArrayList<String> Prep_Redundant_Xindex = new ArrayList<String>();


	public static double[][] Adjacency_matrix_maker(){
		ArrayList<double[]> input_data = new ArrayList<double[]>();
		input_data = FileRead.file_reader();
		int Matrix_size = FileRead.file_size_count();
		double Ad_Matrix[][] = new double[Matrix_size][Matrix_size];

		for(int i = 0; i < Matrix_size; i++){
			for(int s = 0; s < Matrix_size; s++){
				Ad_Matrix[i][s] = 0;
			}
		}

		for(int i = 0; i < input_data.size(); i++){
			Ad_Matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = 1;
			Ad_Matrix[(int)input_data.get(i)[1]][(int)input_data.get(i)[0]] = 1;
		}

		return Ad_Matrix;
	}

	public static double[][] FailureProbability_matrix_maker(){
		ArrayList<double[]> input_data = new ArrayList<double[]>();
		input_data = FileRead.file_reader();
		int Matrix_size = FileRead.file_size_count();
		double failure_Matrix[][] = new double[Matrix_size][Matrix_size];

		for(int i = 0; i < Matrix_size; i++){
			for(int s = 0; s < Matrix_size; s++){
				failure_Matrix[i][s] = 0;
			}
		}

		for(int i = 0; i < input_data.size(); i++){
			if(input_data.get(i)[2] == 0) {
				failure_Matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = (-1.0)*Math.log(0.0000001);
				failure_Matrix[(int)input_data.get(i)[1]][(int)input_data.get(i)[0]] = (-1.0)*Math.log(0.0000001);
			}else {
				failure_Matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = (-1.0)*Math.log(input_data.get(i)[2]);
				failure_Matrix[(int)input_data.get(i)[1]][(int)input_data.get(i)[0]] = (-1.0)*Math.log(input_data.get(i)[2]);
			}
		}
		return failure_Matrix;
	}

	public static double[][] NonLog_FailureProbability_matrix_maker(){
		ArrayList<double[]> input_data = new ArrayList<double[]>();
		input_data = FileRead.file_reader();
		int Matrix_size = FileRead.file_size_count();
		double NonLogfailure_Matrix[][] = new double[Matrix_size][Matrix_size];

		for(int i = 0; i < Matrix_size; i++){
			for(int s = 0; s < Matrix_size; s++){
				NonLogfailure_Matrix[i][s] = 0;
			}
		}

		for(int i = 0; i < input_data.size(); i++){
			NonLogfailure_Matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = input_data.get(i)[2];
			NonLogfailure_Matrix[(int)input_data.get(i)[1]][(int)input_data.get(i)[0]] = input_data.get(i)[2];
		}
		return NonLogfailure_Matrix;
	}

	public static void Ad_Matrix() {

		//prep.clear();

		//前処理の結果を加えてPMDSを求める

		//前処理Critical
		Prep_Critical_Xindex = FileRead.PrepMDS_Xindex_return(FileRead.PrepMDS_StringName_return(Preprocessing.PrepCritical_int_name));
		//前処理Redundant
		Prep_Redundant_Xindex = FileRead.PrepMDS_Xindex_return(FileRead.PrepMDS_StringName_return(Preprocessing.PrepRedundant_int_name));


		ArrayList<String> C = new ArrayList<String>();
		String[] contents = new String[Algorithm.Adjacency_matrix.length];
		for(int i = 0; i < Algorithm.Adjacency_matrix.length; i++){
			contents[i] = parameter +  " x" + (i+1) ;
			for(int t = 0; t < Algorithm.Adjacency_matrix.length; t++){
				if(Algorithm.Adjacency_matrix[t][i] == 1) {
					contents[i] += " + " +Algorithm.FailureProbability_matrix[t][i]+ " x" + (t+1);
				}


			}

		}
		for(int i = 0; i < contents.length; i++) {
			C.add(contents[i]);
		}

		FileWrite.filewrite(C);

	}

	public static void CMD_Ad_Matrix(String m_v) {

		ArrayList<String> C = new ArrayList<String>();
		String[] contents = new String[Algorithm.Adjacency_matrix.length];
		for(int i = 0; i < Algorithm.Adjacency_matrix.length; i++){
			contents[i] = parameter +  " x" + (i+1) ;
			for(int t = 0; t < Algorithm.Adjacency_matrix.length; t++){
				if(Algorithm.Adjacency_matrix[t][i] == 1) {
					contents[i] += " + " +Algorithm.FailureProbability_matrix[t][i]+ " x" + (t+1);
				}


			}

		}
		for(int i = 0; i < contents.length; i++) {
			C.add(contents[i]);
		}


		//前処理で同定されている頂点の条件を追加
		C.add(m_v + "  <= 0");

		FileWrite.filewrite_CMDS(C);

	}

	public static void RMD_Ad_Matrix(String m_v) {


		ArrayList<String> C = new ArrayList<String>();
		String[] contents = new String[Algorithm.Adjacency_matrix.length];
		for(int i = 0; i < Algorithm.Adjacency_matrix.length; i++){
			contents[i] = parameter +  " x" + (i+1) ;
			for(int t = 0; t < Algorithm.Adjacency_matrix.length; t++){
				if(Algorithm.Adjacency_matrix[t][i] == 1) {
					contents[i] += " + " +Algorithm.FailureProbability_matrix[t][i]+ " x" + (t+1);
				}


			}

		}
		for(int i = 0; i < contents.length; i++) {
			C.add(contents[i]);
		}

		C.add(m_v + "  >= 1");

		FileWrite.filewrite_CMDS(C);

	}

}
