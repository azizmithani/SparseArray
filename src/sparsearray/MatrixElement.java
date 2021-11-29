/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sparsearray;

/**
 *
 * @author Aziz Mithani
 */
public class MatrixElement {
    private int rowIndex;
    private int columnIndex;
    private Object data;
    
    public MatrixElement(int rowIndex, int columnIndex, Object data) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.data = data;
    }
    
    public int rowIndex() {
        return this.rowIndex;
    }

    public int columnIndex() {
        return this.columnIndex;
    }

    public Object value() {
        return this.data;
    }
}
