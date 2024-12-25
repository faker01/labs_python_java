package Figures;

public class Rook extends Figure{
    public Rook(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (row == row1 || col == col1){
            return true;
        }

        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return this.canMove(row, col, row1, col1);
    }

    @Override
    public int[] getPathCells(int row, int col, int row1, int col1) {
        if (row == row1){
            int n =  Math.abs(col1 - col);
            int[] cells = new int[n];
            for (int i = 0; i < n; i++) {
                cells[i] = col + (i + 1) * ((col1 - col) / Math.abs(col1 - col));
            }
            return cells;
        }
        else {
            int n =  Math.abs(row1 - row);
            int[] cells = new int[n];
            for (int i = 0; i < n; i++) {
                cells[i] = row + (i + 1) * ((row1 - row) / Math.abs(row1 - row));
            }
            return cells;
        }
    }
}
