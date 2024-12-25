package Figures;


public class Queen extends Figure{
    public Queen(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if((row == row1 && col !=col1) ||(row != row1 && col ==col1) || (Math.abs(row - row1) == Math.abs(col-col1)) ){
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
        if (Math.abs(row - row1) == Math.abs(col-col1)) {
            int n =  Math.abs(row - row1);
            int[] cells = new int[2 * n];
            for (int i = 0; i < n; i++) {
                cells[2 * i] = row + (i + 1) *((row1 - row) / Math.abs(row1 - row));
                cells[2 * i + 1] = col + (i + 1) * ((col1 - col) / Math.abs(col1 - col));
            }
            return cells;
        }
        else{
            if (row == row1){
                int n = Math.abs(col1 - col);
                int[] cells = new int[n];
                for (int i = 0; i < n; i++) {
                    cells[i] = col + (i + 1) * ((col1 - col) / Math.abs(col1 - col));
                }
                return cells;
            }
            else {
                int n = Math.abs(row1 - row);
                int[] cells = new int[n];
                for (int i = 0; i < n; i++) {
                    cells[i] = row + (i + 1) * ((row1 - row) / Math.abs(row1 - row));
                }
                return cells;
            }
        }
    }
}
