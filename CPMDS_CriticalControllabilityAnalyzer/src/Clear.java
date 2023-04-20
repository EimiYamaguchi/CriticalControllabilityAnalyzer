
public class Clear {
	public static void clear() {
		FileRead.Index_1.clear();
		FileRead.Index_2.clear();
		FileRead.Index_3.clear();
		FileRead.Index_4.clear();
		FileRead.Node_int.clear();
		FileWrite.counter = 0;
		ILPMatrix.Prep_Critical_Xindex.clear();
		ILPMatrix.Prep_Redundant_Xindex.clear();
		ILPResult.m = 0;
		ILPSolver.solfile_not_found = false;
		Preprocessing.PrepCritical_int_name.clear();
		Preprocessing.PrepRedundant_int_name.clear();
	}

}
