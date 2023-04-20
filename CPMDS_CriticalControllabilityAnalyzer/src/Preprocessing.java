import java.math.BigDecimal;
import java.util.ArrayList;


public class Preprocessing {

	public static ArrayList<Integer> PrepCritical_int_name = new ArrayList<Integer>();
	public static ArrayList<Integer> PrepRedundant_int_name = new ArrayList<Integer>();

	public static void critical_processing() {

		//前処理１・３をしてから２（こっちのほうが若干早い？でも多分大きくは変わらない）

		ArrayList<Integer> Nodes = new ArrayList<Integer>();
		Nodes =   FileRead.Node_int;


		//隣接リストを作成
		ArrayList<Integer> copy = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> Adjacency_List = new ArrayList<ArrayList<Integer>>();

		for(int i = 0; i < Algorithm.Adjacency_matrix.length; i++) {
			for(int s = 0; s < Algorithm.Adjacency_matrix.length; s++) {
				if(Algorithm.Adjacency_matrix[i][s] == 1) {
					copy.add(s);
				}
			}
			Adjacency_List.add(i,(ArrayList<Integer>) copy.clone());
			copy.clear();
		}

		/*以下のプロセスを全てのNodeに対して行う
		 * ・隣接頂点がある→次数が1である→そのリンクの確率Pにおいて"1-P>θ"が成立→count++
		 *   count>=2である→Critical
		 *   */

		//ArrayList<Integer> PrepCritical_int_name = new ArrayList<Integer>();
		//ArrayList<Integer> PrepRedundant_int_name = new ArrayList<Integer>();
		//Criticalの同定開始

		//

		BigDecimal theta = BigDecimal.valueOf(ILPMatrix.theta);
		BigDecimal one = BigDecimal.valueOf(1);
		BigDecimal FailureProbability;
		int flag_p1 = 0;
		for(int s = 0; s < Nodes.size(); s ++) {
			flag_p1 = 0;
			if(Adjacency_List.get(Nodes.get(s)).size() >= 2) {
				for(int t = 0; t < Adjacency_List.get(Nodes.get(s)).size(); t++) {
					if(Adjacency_List.get(Adjacency_List.get(Nodes.get(s)).get(t)).size() == 1) {

						//失敗確率の値,thetaの値などをBigDecimal型に変換する
						FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[Nodes.get(s)][Adjacency_List.get(Nodes.get(s)).get(t)]);
						if(one.subtract(FailureProbability).compareTo(theta) == 1) {
							flag_p1++;
						}
					}
				}
				if(flag_p1 >= 2) {
					PrepCritical_int_name.add(Nodes.get(s));
				}
			}
		}

		for(int s = 0; s < Nodes.size(); s ++) {
			BigDecimal prod1 = BigDecimal.valueOf(1.0);
			for(int t = 0; t < Adjacency_List.get(Nodes.get(s)).size(); t++) {
				FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[Nodes.get(s)][Adjacency_List.get(Nodes.get(s)).get(t)]);
				//prod1 *= Critical_PMDS_Algorithm.NonLogFailureProbability_matrix[Nodes.get(s)][Adjacency_List.get(Nodes.get(s)).get(t)];
				prod1 = prod1.multiply(FailureProbability);
			}
			if(one.subtract(prod1).compareTo(theta) == -1) {
				if(PrepCritical_int_name.contains(Nodes.get(s)) == false) {
					PrepCritical_int_name.add(Nodes.get(s));
				}
			}
		}

		ArrayList<Integer> PrepNonCritical_int_name = new ArrayList<Integer>();
		for(int a : Nodes){
		    if(PrepCritical_int_name.contains(a) == false){
		    	PrepNonCritical_int_name.add(a);
		    }
		}

		//Redundantの同定開始
		int Redundant_flag = 0;
		for(int s = 0; s < PrepNonCritical_int_name.size(); s++) {
			BigDecimal prod2 = BigDecimal.valueOf(1.0);
			Redundant_flag = 0;
			for(int t = 0; t < Adjacency_List.get(PrepNonCritical_int_name.get(s)).size(); t++) {
				FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[PrepNonCritical_int_name.get(s)][Adjacency_List.get(PrepNonCritical_int_name.get(s)).get(t)]);
				if(PrepCritical_int_name.contains(Adjacency_List.get(PrepNonCritical_int_name.get(s)).get(t))) {

					prod2 = prod2.multiply(FailureProbability);

				}else {
					Redundant_flag = 1;
					break;
				}
			}
			if(Redundant_flag == 0 && one.subtract(prod2).compareTo(theta) == 1) {
				PrepRedundant_int_name.add(PrepNonCritical_int_name.get(s));
			}

		}

		System.out.println("前処理Critical"+PrepCritical_int_name.size());
		//Redundantの同定終了

	}

	public static void critical_processing2() {

		//前処理１・２・３の順

		ArrayList<Integer> Nodes = new ArrayList<Integer>();
		Nodes =   FileRead.Node_int;


		//隣接リストを作成
		ArrayList<Integer> copy = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> Adjacency_List = new ArrayList<ArrayList<Integer>>();

		for(int i = 0; i < Algorithm.Adjacency_matrix.length; i++) {
			for(int s = 0; s < Algorithm.Adjacency_matrix.length; s++) {
				if(Algorithm.Adjacency_matrix[i][s] == 1) {
					copy.add(s);
				}
			}
			Adjacency_List.add(i,(ArrayList<Integer>) copy.clone());
			copy.clear();
		}


		/*以下のプロセスを全てのNodeに対して行う
		 * ・隣接頂点がある→次数が1である→そのリンクの確率Pにおいて"1-P>θ"が成立→count++
		 *   count>=2である→Critical
		 *   */
		//Criticalの同定開始

		//

		BigDecimal theta = BigDecimal.valueOf(ILPMatrix.theta);
		BigDecimal one = BigDecimal.valueOf(1);
		BigDecimal FailureProbability;

		//proposition1
		int flag_p1 = 0;
		for(int s = 0; s < Nodes.size(); s ++) {
			flag_p1 = 0;
			if(Adjacency_List.get(Nodes.get(s)).size() >= 2) {
				for(int t = 0; t < Adjacency_List.get(Nodes.get(s)).size(); t++) {
					if(Adjacency_List.get(Adjacency_List.get(Nodes.get(s)).get(t)).size() == 1) {

						//失敗確率の値,thetaの値などをBigDecimal型に変換する
						FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[Nodes.get(s)][Adjacency_List.get(Nodes.get(s)).get(t)]);
						if(one.subtract(FailureProbability).compareTo(theta) == 1) {
							flag_p1++;
						}
					}
				}
				if(flag_p1 >= 2) {
					PrepCritical_int_name.add(Nodes.get(s));
				}
			}
		}
		ArrayList<Integer> PrepNonCritical_int_name = new ArrayList<Integer>();
		for(int a : Nodes){
		    if(PrepCritical_int_name.contains(a) == false){
		    	PrepNonCritical_int_name.add(a);
		    }
		}

		//proposition2
		//Redundantの同定開始
		int Redundant_flag = 0;
		for(int s = 0; s < PrepNonCritical_int_name.size(); s++) {
			BigDecimal prod2 = BigDecimal.valueOf(1.0);
			Redundant_flag = 0;
			for(int t = 0; t < Adjacency_List.get(PrepNonCritical_int_name.get(s)).size(); t++) {
				FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[PrepNonCritical_int_name.get(s)][Adjacency_List.get(PrepNonCritical_int_name.get(s)).get(t)]);
				if(PrepCritical_int_name.contains(Adjacency_List.get(PrepNonCritical_int_name.get(s)).get(t))) {

					prod2 = prod2.multiply(FailureProbability);

				}else {
					Redundant_flag = 1;
					break;
				}
			}
			if(Redundant_flag == 0 && one.subtract(prod2).compareTo(theta) == 1) {
				PrepRedundant_int_name.add(PrepNonCritical_int_name.get(s));
			}

		}

				//Redundantの同定終了


		//proposition3
		for(int s = 0; s < Nodes.size(); s ++) {
			BigDecimal prod1 = BigDecimal.valueOf(1.0);
			for(int t = 0; t < Adjacency_List.get(Nodes.get(s)).size(); t++) {
				FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[Nodes.get(s)][Adjacency_List.get(Nodes.get(s)).get(t)]);
				//prod1 *= Critical_PMDS_Algorithm.NonLogFailureProbability_matrix[Nodes.get(s)][Adjacency_List.get(Nodes.get(s)).get(t)];
				prod1 = prod1.multiply(FailureProbability);
			}
			if(one.subtract(prod1).compareTo(theta) == -1) {
				if(PrepCritical_int_name.contains(Nodes.get(s)) == false) {
					PrepCritical_int_name.add(Nodes.get(s));
				}
			}
		}


	}


}

