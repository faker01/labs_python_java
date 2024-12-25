import Figures.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Board {

    /*
    *   1. Ход фигур с учетом препятствий, и учесть что отаковать короля нельзя
        2. Функции шах и мат.
        Дополнительно:
        1. Превращение пешки в фигуру, с учетом фигур на доске.
        2. Рокеровку
    *
    *
    * */






    private char colorGame;

    public void setColorGame(char colorGame) {
        this.colorGame = colorGame;
    }

    public  char getColorGame(){
        return colorGame;
    }

    public static boolean gameState = false; // Флаг состояния игры: true - поставлен мат, false - игра продолжается

    private Figure[][] fields = new Figure[8][8];
    private ArrayList<String> takeWhite = new ArrayList(16);
    private ArrayList<String> takeBlack = new ArrayList(16);
    public ArrayList<String> getTakeBlack() {
        return takeBlack;
    }

    public ArrayList<String> getTakeWhite() {
        return takeWhite;
    }

    public void init(){
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'), new Bishop("B", 'w'),
                new Queen("Q", 'w'), new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'), new Rook("R",'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
                new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
        };



        this.fields[6] = new Figure[] {
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b')
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'), new Bishop("B", 'b'),
                new Queen("Q", 'b'), new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'), new Rook("R",'b')
        };
    }

    public String getCell(int row, int col){
        Figure figure = this.fields[row][col];
        if (figure ==null){
            return "    ";
        }
        return  " "+figure.getColor()+figure.getName()+" ";
    }
    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1 ; row --){
            System.out.print(row);
            for (int col=0; col<8; col++){
                System.out.print("|"+getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col=0; col< 8; col++){
            System.out.print("    "+col);
        }
    }

    public boolean canMoveOnBoard(Figure[][] field, Figure figure, int row, int col, int row1, int col1){
        if (!figure.canMove(row, col, row1, col1) && !figure.canAttack(row, col, row1, col1)){
            return false;
        }
        if (figure.getColor() == 'w' && figure.getName().equals("Q"))
        {
            System.out.println("1");
        }
        int[] pathCells = figure.getPathCells(row, col, row1, col1); // Получаем все клетки через которые проходит фигура

        // Проверяем наличие других фигур в этих клетках (последнюю проверяем только для своего цвета)
        for (int i = 0; i < pathCells.length / 2; i++ ){

            if (field[pathCells[2*i]][pathCells[2*i+1]] != null){
                return false;
            }
        }
        if (field[row1][col1] != null && field[row1][col1].getColor() == figure.getColor()){
            return false;
        }
        if (figure.getColor() == 'w' && figure.getName().equals("Q"))
        {
            System.out.println("2");
        }
        // Проверяем наличие шаха после хода
        Figure[][] field_copy = field.clone();
        field_copy[row][col] = null;
        field_copy[row1][col1] = figure;
        if (!isCheck(field_copy, figure.getColor())){
            if (figure.getColor() == 'w' && figure.getName().equals("Q"))
            {
                System.out.println("3");
            }
            return true;
        }
        else return false;
    }

    public boolean isCheck(Figure[][] field, char color){
        // Находим позицию короля
        int kingX = -1;
        int kingY = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = field[i][j];
                if (figure instanceof King && figure.getColor() == color) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
        }
        // Проверяем фигуры противника на возможность атаки
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = field[i][j];
                if (figure != null && figure.getColor() != color) {
                    if (canMoveOnBoard(field, figure, i, j, kingX, kingY)) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isMate(){
        // Если король не под шахом, то мата быть не может
        if (!isCheck(this.fields, getColorGame())) {
            return false;
        }

        int kingX = -1;
        int kingY = -1;
        char color = getColorGame();

        // Находим позицию короля
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];
                if (figure instanceof King && figure.getColor() == color) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
        }

        if (kingX == -1 || kingY == -1) {
            throw new IllegalStateException("Король не найден на доске");
        }

        // Проверяем, может ли король уйти из-под шаха
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Пропускаем текущую позицию

                int newX = kingX + dx;
                int newY = kingY + dy;

                if (canMoveOnBoard(this.fields, this.fields[kingX][kingY], kingX, kingY, newX, newY)) {
                    return false;
                }
            }
        }

        // Проверяем, может ли другая фигура защитить короля
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];
                if (figure != null && figure.getColor() == color && !(figure instanceof King)) {
                    for (int dx = 0; dx < 8; dx++) {
                        for (int dy = 0; dy < 8; dy++) {
                            if (dx == 0 && dy == 0) continue; // Пропускаем текущую позицию

                            int newX = i + dx;
                            int newY = j + dy;

                            if (canMoveOnBoard(this.fields, this.fields[kingX][kingY], i, j, newX, newY)) {
                                return false;
                            }
                        }
                    }

                }
            }
        }

        return true; // Ни одна из фигур не может спасти короля, мат
    }

    // Проверка, находится ли позиция на доске
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public boolean move_figure(int row, int col, int row1, int col1){
      Figure figure = this.fields[row][col];
      gameState = isMate();

      if (gameState)
      {

          return false;
      }

      if (!isValidPosition(row1, col1) || !isValidPosition(row, col)) {
          return false;
      }

      if (canMoveOnBoard(this.fields, figure, row, col, row1, col1)){
          this.fields[row1][col1] = figure;
          this.fields[row][col] = null;
          return true;
      }
        return false;
    }
}
