import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;

public class SeamCarver {
	private double[][] energy;
	private int[][] RGB;
	private boolean vertical = true;
	private static final int BORDER_ENERGY = 1000;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null)
			throw new IllegalArgumentException();

		energy = new double[picture.height()][picture.width()];
		RGB = new int[picture.height()][picture.width()];

		for (int i = 0; i < picture.height(); i++) {
			for (int j = 0; j < picture.width(); j++) {
				RGB[i][j] = picture.getRGB(j, i);
			}
		}
		for (int i = 0; i < picture.height(); i++) {
			for (int j = 0; j < picture.width(); j++) {
				energy[i][j] = computeEnergy(i, j);
			}
		}

	}

	private double computeEnergy(int x, int y) {
		if (x == 0 || y == 0 || x == RGB.length - 1 || y == RGB[x].length - 1)
			return BORDER_ENERGY;

		int Rx = ((RGB[x + 1][y] >> 16) & 0xFF) - ((RGB[x - 1][y] >> 16) & 0xFF);
		int Gx = ((RGB[x + 1][y] >> 8) & 0xFF) - ((RGB[x - 1][y] >> 8) & 0xFF);
		int Bx = ((RGB[x + 1][y] >> 0) & 0xFF) - ((RGB[x - 1][y] >> 0) & 0xFF);

		int Ry = ((RGB[x][y + 1] >> 16) & 0xFF) - ((RGB[x][y - 1] >> 16) & 0xFF);
		int Gy = ((RGB[x][y + 1] >> 8) & 0xFF) - ((RGB[x][y - 1] >> 8) & 0xFF);
		int By = ((RGB[x][y + 1] >> 0) & 0xFF) - ((RGB[x][y - 1] >> 0) & 0xFF);

		double gradientX = Math.pow(Rx, 2) + Math.pow(Gx, 2) + Math.pow(Bx, 2);
		double gradientY = Math.pow(Ry, 2) + Math.pow(Gy, 2) + Math.pow(By, 2);

		return Math.sqrt(gradientX + gradientY);

	}

	// current picture
	public Picture picture() {
		// picture(weight, height)
		if(!vertical) transpose();
		Picture copy = new Picture(RGB[0].length, RGB.length);

		for (int i = 0; i < RGB.length; i++) {
			for (int j = 0; j < RGB[0].length; j++) {
				copy.setRGB(j, i, RGB[i][j]);
			}
		}
		return copy;
	}

	// transpose RGB and energy function
	private void transpose() {
		double[][] energy_transposed = new double[energy[0].length][energy.length];
		int[][] RGB_transposed = new int[RGB[0].length][RGB.length];
		for (int i = 0; i < energy[0].length; i++) {
			for (int j = 0; j < energy.length; j++) {
				energy_transposed[i][j] = energy[j][i];
				RGB_transposed[i][j] = RGB[j][i];
			}
		}
		RGB = RGB_transposed;
		energy = energy_transposed;
		vertical = !vertical;

	}

	// width of current picture
	public int width() {
		if (vertical) return energy[0].length;
		else return energy.length;
	}

	// height of current picture
	public int height() {
		if (vertical) return energy.length;
		else return energy[0].length;
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if(!vertical) {
			int temp = y;
			y = x;
			x = temp;
		}
		if (x < 0 || y < 0 || x >= energy[0].length || y >= energy.length)
			throw new IllegalArgumentException();
		return energy[y][x];

	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		if (vertical) transpose();
		
		return findSeam();
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		if (!vertical) transpose();


		return findSeam();
	}
	
	private int[] findSeam() {
		double distTo[][] = new double[energy.length][energy[0].length];
		int edgeTo[][] = new int[energy.length][energy[0].length];
		
		for(int i=0; i<energy.length; i++) {
			for(int j=0; j<energy[i].length; j++) {
				distTo[i][j] = Integer.MAX_VALUE;
				edgeTo[i][j] = -1;
			}
		}
		
		for(int i=0; i<energy[0].length; i++)  {
			distTo[0][i] = energy[0][i];
		}
		
		for(int y=0; y < energy.length - 1; y++) {
			for(int x=0; x<energy[y].length; x++) {
				relaxation(x, y, x-1, y+1, distTo, edgeTo);
				relaxation(x, y, x, y+1, distTo, edgeTo);
				relaxation(x, y, x+1, y+1, distTo, edgeTo);
				
			}
			
		}
		double minimumPath = Integer.MAX_VALUE;
		int index = -1;
		
		for(int i=0; i<distTo[0].length; i++) {
			if(distTo[distTo.length - 1][i] < minimumPath) {
				minimumPath = distTo[distTo.length - 1][i];
				index = i;
			}
		}
		
		int[] seam = new int[distTo.length];
		
		for(int y=seam.length-1; y>=0; y--) {
			seam[y] = index;
			index = edgeTo[y][index];
		}
		return seam;

		
	}
	
	private void relaxation(int x, int y, int x_neighbour, int y_neighbour, double[][] distTo, int[][] edgeTo) {
		if(x_neighbour < 0 || x_neighbour >= energy[0].length) return;
		if(distTo[y_neighbour][x_neighbour] > distTo[y][x] + energy[y_neighbour][x_neighbour]) {
			distTo[y_neighbour][x_neighbour] = distTo[y][x] + energy[y_neighbour][x_neighbour];
			edgeTo[y_neighbour][x_neighbour]  = x;
		}
	}


	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {

		if (!validate(seam, width(), height())) throw new IllegalArgumentException();
		if (vertical) transpose();
		removeSeam(seam);

	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {

		if (!validate(seam, height(), width())) throw new IllegalArgumentException();
		if (!vertical) transpose();
		removeSeam(seam);
	}

	private void removeSeam(int[] seam) {

		double[][] energy_update = new double[energy.length][energy[0].length - 1];
		int[][] RGB_update = new int[RGB.length][RGB[0].length - 1];

		for (int i = 0; i < RGB.length; i++) {
			int deleted_index = seam[i];

			int left = deleted_index - 0;
			int right = (RGB[i].length - 1) - deleted_index;

			if(left != 0) {
				System.arraycopy(RGB[i], 0, RGB_update[i], 0, left);
				System.arraycopy(energy[i], 0, energy_update[i], 0, left);
			}
			if(right != 0)  {
				System.arraycopy(RGB[i], deleted_index + 1, RGB_update[i], deleted_index, right);
				System.arraycopy(energy[i], deleted_index + 1, energy_update[i], deleted_index, right);
			}	
			
		}
		

		RGB = RGB_update;
		for(int i=0; i<seam.length; i++) {
			int deleted_index = seam[i];
			
			// deleted_index - 1 and deleted_index + 1 - energy change
			// in updated array, they correspond to deleted_index -1 and deleted_index	
			
			if(deleted_index  > 1) energy_update[i][deleted_index - 1] = computeEnergy(i, deleted_index - 1);
			if(deleted_index < energy_update[i].length) energy_update[i][deleted_index] = computeEnergy(i, deleted_index);
		}
		energy = energy_update;
		
		
	}

	private boolean validate(int[] seam, int length, int range) {
		if (seam == null) return false;
		if (range == 1 || seam.length != length) return false;

		if (seam[0] < 0 || seam[0] >= range) return false;

		for (int i = 1; i < seam.length; i++) {
			if (seam[i] < 0 || seam[i] >= range || Math.abs(seam[i] - seam[i - 1]) > 1)
				return false;
		}
		return true;

	}

	// unit testing (optional)
	public static void main(String[] args) {

	}
}
