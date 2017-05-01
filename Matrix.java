
import java.util.Arrays;

public class Matrix {
	
	private int rows, colums;
	private float[][] data;
	
	public Matrix(int rows, int colums) {
		this.rows = rows;
		this.colums = colums;
		data = new float[rows][colums];
	}

	public Matrix(float[][] data) {
		this.data = data;
		rows = data.length;
		colums = data[0].length;
	}
	
	public void setValue(int row, int column, float value) {
		data[row][column] = value;
	}
	
	public float getValue(int row, int column) {
		return data[row][column];
	}
	
	public static Matrix multiply(Matrix m1, Matrix m2) {
		Matrix result = new Matrix(m1.rows, m2.colums);
		
		for(int i = 0; i < result.rows;i++) {
			for(int j=0;j < result.colums; j++) {
				float sum = 0;
				for(int k=0;k<m1.colums;k++) {
					sum += m1.getValue(i, k) * m2.getValue(k, j);
				}
				result.setValue(i, j, sum);
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		for(float[] arr:data) {
			s += Arrays.toString(arr) + "\n";
		}
		return s;
	}
	
	
	
	
	


}