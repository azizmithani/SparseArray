/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sparsearray;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Aziz Mithani
 */
public class SparseArray {

    private Object defaultValue;
    private int nRows;
    private int nColumns;
    private LinkedList rows;
    private LinkedList columns;

    public SparseArray(Object defaultValue, int nRows, int nColumns) {
        this.defaultValue = defaultValue;
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.rows = new LinkedList();
        this.columns = new LinkedList();
    }

    public Object defaultValue() {
        return this.defaultValue;
    }

    public RowIterator iterateRows() {
        return new RowIterator(rows.iterator());
    }

    public ColumnIterator iterateColumns() {
        return new ColumnIterator(columns.iterator());
    }

    public int numberOfColumns() {
        return nColumns;
    }

    public int numberOfRows() {
        return nRows;
    }

    public Object elementAt(int row, int col) throws IndexOutOfBoundsException {

        if (row > this.nRows - 1 || col > this.nColumns - 1) // -1 because its zero-based indexing
            throw new IndexOutOfBoundsException();

        RowIterator ri = this.iterateRows();
        while (ri.hasNext()) {
            ElementIterator ei = ri.next();

            if (ei.nonIteratingIndex() == row) {
                while (ei.hasNext()) {
                    MatrixElement me = ei.next();
                    if (me.columnIndex() == col)
                        return me.value();
                    else if (me.columnIndex() > col) // item not found
                        return this.defaultValue;

                } //end while (element iterator)
            } else if (ei.nonIteratingIndex() > row) // item not found
                return this.defaultValue();
        } // end while (row iterator)

        return this.defaultValue;
    }

    public void setValue(int row, int col, Object value) throws IndexOutOfBoundsException {

        if (row > this.nRows - 1 || col > this.nColumns - 1) // -1 because its zero-based indexing
            throw new IndexOutOfBoundsException();

        LinkedList currRowList, currColumnList;
        MatrixElement me = new MatrixElement(row, col, value);

        // insert  (or get) the row index
        currRowList = insertIndex(this.rows, me.rowIndex(), true);
        // insert the matrix element into the current row
        updateElement(currRowList, me, false);
        // insert (or get) the column index
        currColumnList = insertIndex(this.columns, me.columnIndex(), false);
        // insert the matrix element into the current column
        updateElement(currColumnList, me, true);
    }

    public Object[][] full(Class c) {

        Object[][] array = new Object[nRows][nColumns];
        // initialise the array
        for (int i = 0; i < array.length; i++) {
            array[i] = (array[i]);
            for (int j = 0; j < array[i].length; j++)
                array[i][j] = c.cast(this.defaultValue);
        }

        RowIterator ri = this.iterateRows();
        while (ri.hasNext()) {
            ElementIterator ei = ri.next();
            while (ei.hasNext()) {
                MatrixElement me = ei.next();
                array[me.rowIndex()][me.columnIndex()] = c.cast(me.value());
            } //end while (element iterator)
        } // end while (row iterator)

        return array;
    }

    private LinkedList insertIndex(LinkedList list, int meIdx, boolean isRowList) {
        int currIdx;
        LinkedList currList = null;

        Iterator it = list.iterator();
        int idx = -1;
        boolean found = false;
        while (it.hasNext()) {
            idx++;
            currList = (LinkedList) it.next();

            if (isRowList)
                currIdx = ((MatrixElement) currList.peek()).rowIndex();
            else
                currIdx = ((MatrixElement) currList.peek()).columnIndex();

            if (currIdx < meIdx)
                continue;
            else if (currIdx == meIdx) {
                found = true;
                break;
            }
            currList = new LinkedList();
            list.add(idx, currList);
            found = true;
            break;

        } // end while (ri.hasNext())

        // index entry wasnt found. insert it at the end
        if (!found) {
            currList = new LinkedList();
            list.addLast(currList);
        }

        return currList;
    }

    private void updateElement(LinkedList list, MatrixElement me, boolean checkRow) {
        Iterator it = list.listIterator();
        int idx = -1;
        int meIdx, currIdx;
        boolean updated = false;
        while (it.hasNext()) {
            idx++;
            MatrixElement currME = (MatrixElement) it.next();

            // get the appropriate indices
            if (checkRow) {
                meIdx = me.rowIndex();
                currIdx = currME.rowIndex();
            } else {
                meIdx = me.columnIndex();
                currIdx = currME.columnIndex();
            }

            if (currIdx < meIdx) // move ahead
                continue;
            else if (currIdx == meIdx) // replace
                list.remove(idx);

            if (!me.equals(this.defaultValue)) {
                list.add(idx, me);
            }
            updated = true;
            break;
        }

        // element not inserted yet. insert it at the end (nothing will happen for deletion
        if (!updated)
            list.addLast(me);
    }

    public SparseArray transpose() {
        SparseArray tArray = new SparseArray(this.defaultValue, nColumns, nRows);

        RowIterator ri = this.iterateRows();
        while (ri.hasNext()) {
            ElementIterator ei = ri.next();
            while (ei.hasNext()) {
                MatrixElement me = ei.next();
                tArray.setValue(me.columnIndex(), me.rowIndex(), me.value());
            } //end while (element iterator)
        } // end while (row iterator)

        return tArray;
    }
        
    @Override
    public SparseArray clone() {
        SparseArray newArray = new SparseArray(this.defaultValue, nRows, nColumns);

        RowIterator ri = this.iterateRows();
        while (ri.hasNext()) {
            ElementIterator ei = ri.next();
            while (ei.hasNext()) {
                MatrixElement me = ei.next();
                newArray.setValue(me.rowIndex(), me.columnIndex(), me.value());
            } //end while (element iterator)
        } // end while (row iterator)

        return newArray;
    }    
}
