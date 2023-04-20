import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Algorithm {

	public static double[][] Adjacency_matrix;
	public static double[][] FailureProbability_matrix;
	public static double[][] NonLogFailureProbability_matrix;

	public static void main_Algorithm(String output) {


		int M = 0;
		ArrayList<String> MDS = new ArrayList<String>();
		ArrayList<String> Non_MDS = new ArrayList<String>();
		ArrayList<String> Nodes = new ArrayList<String>();
		ArrayList<String> Non_MDSandRedundant = new ArrayList<String>();

		int M_next = 0;
		ArrayList<String> MDS_next = new ArrayList<String>();
		ArrayList<String> CMDS = new ArrayList<String>();
		ArrayList<String> RMDS = new ArrayList<String>();
		ArrayList<String> IMDS = new ArrayList<String>();
		ArrayList<String> mds_x_index = new ArrayList<String>();
		ArrayList<String> Nonmds_x_index = new ArrayList<String>();

		//隣接行列を作る
		Adjacency_matrix = ILPMatrix.Adjacency_matrix_maker();
		FailureProbability_matrix = ILPMatrix.FailureProbability_matrix_maker();
		NonLogFailureProbability_matrix = ILPMatrix.NonLog_FailureProbability_matrix_maker();

		//ここで前処理を行う
		Preprocessing.critical_processing2();

		//Critical_PMDSを求める処理
		//最初のILP
		ILPMatrix.Ad_Matrix();

		MDS = ILPResult.ILP_get_result();
		M = MDS.size();
		mds_x_index = ILPResult.MDS_x_index();

		System.out.println("|PMDS|="+M);
		//System.out.println("PMDS="+MDS);

		ArrayList<String> Prep_CPMDS = new ArrayList<String>();
		ArrayList<String> Prep_RPMDS = new ArrayList<String>();

		ArrayList<String> MDS_Prep_CPMDS = new ArrayList<String>();
		ArrayList<String> Node_Prep_RPMDS = new ArrayList<String>();
		ArrayList<String> MDS_Prep_CPMDS_string = new ArrayList<String>();
		ArrayList<String> Node_Prep_RPMDS_string = new ArrayList<String>();
		Prep_CPMDS = FileRead.PrepMDS_Xindex_return(FileRead.PrepMDS_StringName_return(Preprocessing.PrepCritical_int_name));
		Prep_RPMDS = FileRead.PrepMDS_Xindex_return(FileRead.PrepMDS_StringName_return(Preprocessing.PrepRedundant_int_name));

		//エラー探し

		//MDS-前処理Criticalを求める
		for(String a : mds_x_index){
			if(Prep_CPMDS.contains(a) != true){
				MDS_Prep_CPMDS.add(a);
			}
		}

		//Node-MDS-前処理Redundantを求める
		Nodes = FileRead.PrepMDS_Xindex_return(FileRead.PrepMDS_StringName_return(FileRead.Node_int));
		for(String a : Nodes){
			if(mds_x_index.contains(a) != true && Prep_RPMDS.contains(a) != true){
				Node_Prep_RPMDS.add(a);
			}
		}
		MDS_Prep_CPMDS_string = FileRead.result_return(MDS_Prep_CPMDS);
		Node_Prep_RPMDS_string = FileRead.result_return(Node_Prep_RPMDS);



		for(int i = 0; i < MDS_Prep_CPMDS.size(); i++) {
			FileDelete.file_delete();
			ILPMatrix.CMD_Ad_Matrix(MDS_Prep_CPMDS.get(i));
			MDS_next = ILPResult.ILP_get_result();
			M_next = MDS_next.size();

			if( ILPSolver.solfile_not_found == true) {
				CMDS.add(MDS_Prep_CPMDS_string.get(i));
				ILPSolver.solfile_not_found = false;
			}

			if(M_next > M) {
				CMDS.add(MDS_Prep_CPMDS_string.get(i));
			}
			MDS_next.clear();
		}
		//終了

		//Redundant_PMDSを求める処理

		for(int i = 0; i < Node_Prep_RPMDS.size(); i++) {
			FileDelete.file_delete();
			ILPMatrix.RMD_Ad_Matrix(Node_Prep_RPMDS.get(i));
			MDS_next = ILPResult.ILP_get_result();
			M_next = MDS_next.size();

			if(M_next > M) {
				RMDS.add(Node_Prep_RPMDS_string.get(i));
			}
			MDS_next.clear();
		}

		//終了

		//出力用に情報を整理
		CMDS.addAll(FileRead.PrepMDS_StringName_return(Preprocessing.PrepCritical_int_name));
		HashSet<String> Critical = new HashSet<String>(CMDS);
		RMDS.addAll(FileRead.PrepMDS_StringName_return(Preprocessing.PrepRedundant_int_name));
		HashSet<String> Redundant = new HashSet<String>(RMDS);

		//Intermittentを求める処理
		Nodes = FileRead.PrepMDS_StringName_return(FileRead.Node_int);
		for(int s = 0; s < Nodes.size(); s++) {
			if(CMDS.contains(Nodes.get(s)) == false && RMDS.contains(Nodes.get(s)) == false ) {
				IMDS.add(Nodes.get(s));
			}
		}

		FileDelete.file_delete();





		//終了
		//System.out.println(Nodes);

		System.out.println("");
		System.out.println("--RESULT--");

		System.out.println("|CPMDS| = " + (Critical.size()));
		System.out.println("|RPMDS| = " + (Redundant.size()));
		System.out.println("|IPMDS| = " + (IMDS.size()));

		//結果テキストファイル作成
				File ResultFile = new File(output + "\\" +FileRead.FileName+"_"+ ILPMatrix.theta +"_result.txt");
				try {
					ResultFile.createNewFile();
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ResultFile)));
			        pw.println("\\Network name: "+FileRead.FileName);
			        pw.println("Node	PMDS	Category");
			        //pw.println(" CRITICAL PMDS");
			        for(int i = 0; i < CMDS.size(); i++) {
			        	pw.println(CMDS.get(i) +"	1	Critical" );
			        }
			        //pw.println(" REDUNDANT PMDS");
			        for(int i = 0; i < RMDS.size(); i++) {
			        	pw.println(RMDS.get(i)+"	0	Redundant");
			        }
			        //pw.println(" INTERMITTENT PMDS");
			        for(int i = 0; i < IMDS.size(); i++) {
			        	if(MDS.contains(IMDS.get(i))) {
			        		pw.println(IMDS.get(i)+"	1	Intermittent");
			        	}else {
			        		pw.println(IMDS.get(i)+"	0	Intermittent");
			        	}
			        }

			        pw.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}


	}

}
