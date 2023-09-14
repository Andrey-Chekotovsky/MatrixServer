package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Random;

@Data
public class Matrix implements Serializable {
    int[][] matrix;
    final int rows;
    final int columns;
    public Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        matrix = new int[rows][columns];
    }
    public void generate(){
        Random random = new Random(11);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = random.nextInt();
            }
        }
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int i : row) {
                sb.append(i);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

